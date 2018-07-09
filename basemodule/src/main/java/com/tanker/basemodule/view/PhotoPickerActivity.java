package com.tanker.basemodule.view;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.R;
import com.tanker.basemodule.adapter.PhotoGridAdapter;
import com.tanker.basemodule.adapter.PopupDirectoryListAdapter;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.event.OnItemCheckListener;
import com.tanker.basemodule.event.OnPhotoClickListener;
import com.tanker.basemodule.model.ImageBean;
import com.tanker.basemodule.model.Photo;
import com.tanker.basemodule.model.PhotoDirectory;
import com.tanker.basemodule.utils.AndroidLifecycleUtils;
import com.tanker.basemodule.utils.ExifUtils;
import com.tanker.basemodule.utils.GPSUtils;
import com.tanker.basemodule.utils.ImageCaptureManager;
import com.tanker.basemodule.utils.MediaStoreHelper;
import com.tanker.basemodule.utils.PermissionsConstant;
import com.tanker.basemodule.utils.PermissionsUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.tanker.basemodule.utils.MediaStoreHelper.INDEX_ALL_PHOTOS;
import static com.tanker.basemodule.view.PhotoPicker.DEFAULT_COLUMN_NUMBER;
import static com.tanker.basemodule.view.PhotoPicker.DEFAULT_MAX_COUNT;
import static com.tanker.basemodule.view.PhotoPicker.EXTRA_GRID_COLUMN;
import static com.tanker.basemodule.view.PhotoPicker.EXTRA_GROUP_POSITION;
import static com.tanker.basemodule.view.PhotoPicker.EXTRA_MAX_COUNT;
import static com.tanker.basemodule.view.PhotoPicker.EXTRA_ORIGINAL_PHOTOS;
import static com.tanker.basemodule.view.PhotoPicker.EXTRA_PREVIEW_ENABLED;
import static com.tanker.basemodule.view.PhotoPicker.EXTRA_SHOW_CAMERA;
import static com.tanker.basemodule.view.PhotoPicker.EXTRA_SHOW_GIF;
import static com.tanker.basemodule.view.PhotoPicker.KEY_SELECTED_PHOTOS;

public class PhotoPickerActivity extends BaseActivity {

    private int maxCount = DEFAULT_MAX_COUNT;

    private ImageCaptureManager captureManager;
    private PhotoGridAdapter photoGridAdapter;

    private PopupDirectoryListAdapter listAdapter;
    //所有photos的路径
    private List<PhotoDirectory> directories;
    //传入的已选照片
    private ArrayList<ImageBean> originalPhotos;

    private int SCROLL_THRESHOLD = 30;
    //目录弹出框的一次最多显示的目录数目
    public static int COUNT_MAX = 4;
    private ListPopupWindow listPopupWindow;
    private RequestManager mGlideRequestManager;

    private int groupPosition = 0;

    private CustomToolbar toolbar;
    private String newPicPath;
    public Location currentLocation;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private RecyclerView recyclerView;

    @Override
    protected void initView() {
        boolean showCamera = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        boolean showGif = getIntent().getBooleanExtra(EXTRA_SHOW_GIF, false);
        boolean previewEnable = getIntent().getBooleanExtra(EXTRA_PREVIEW_ENABLED, false);
        groupPosition = getIntent().getIntExtra(EXTRA_GROUP_POSITION, 0);

        maxCount = getIntent().getIntExtra(EXTRA_MAX_COUNT, DEFAULT_MAX_COUNT);
        int columnNumber = getIntent().getIntExtra(EXTRA_GRID_COLUMN, DEFAULT_COLUMN_NUMBER);
        originalPhotos = getIntent().getParcelableArrayListExtra(EXTRA_ORIGINAL_PHOTOS);

        if (maxCount > 1) {
            toolbar.setRightText(getString(R.string.picker_done_with_count, originalPhotos.size(), maxCount));
        } else {
            toolbar.setRightText(getString(R.string.picker_done));
        }

        mGlideRequestManager = Glide.with(this);
        directories = new ArrayList<>();
        photoGridAdapter = new PhotoGridAdapter(this, mGlideRequestManager, directories, originalPhotos, columnNumber);
        photoGridAdapter.setShowCamera(showCamera);
        photoGridAdapter.setPreviewEnable(previewEnable);
        listAdapter = new PopupDirectoryListAdapter(mGlideRequestManager, directories);
        Bundle mediaStoreArgs = new Bundle();
        mediaStoreArgs.putBoolean(EXTRA_SHOW_GIF, showGif);
        MediaStoreHelper.getPhotoDirs(this, mediaStoreArgs,
                new MediaStoreHelper.PhotosResultCallback() {
                    @Override
                    public void onResultCallback(List<PhotoDirectory> dirs) {
                        directories.clear();
                        directories.addAll(dirs);
                        photoGridAdapter.notifyDataSetChanged();
                        listAdapter.notifyDataSetChanged();
                        adjustHeight();
                    }
                });

        captureManager = new ImageCaptureManager(this);

        listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setWidth(ListPopupWindow.MATCH_PARENT);
        listPopupWindow.setAnchorView(toolbar.getToolbar());
        listPopupWindow.setAdapter(listAdapter);
        listPopupWindow.setModal(true);
        listPopupWindow.setDropDownGravity(Gravity.BOTTOM);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listPopupWindow.dismiss();

                PhotoDirectory directory = directories.get(position);

                toolbar.getTitle().setText(directory.getName());

                photoGridAdapter.setCurrentDirectoryIndex(position);
                photoGridAdapter.notifyDataSetChanged();
            }
        });

        listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toolbar.getTitle().setSelected(false);
            }
        });

        photoGridAdapter.setOnPhotoClickListener(new OnPhotoClickListener() {
            @Override
            public void onClick(View v, int position, boolean showCamera) {
                final int index = showCamera ? position - 1 : position;
                ArrayList<ImageBean> photos = photoGridAdapter.getCurrentPhotoPaths();
                // 列表图片点击操作
            }
        });

        photoGridAdapter.setOnItemCheckListener(new OnItemCheckListener() {
            @Override
            public boolean onItemCheck(int position, Photo path, int selectedItemCount) {
                if (selectedItemCount > maxCount) {
                    showMessage(String.format(getResources().getString(R.string.picker_over_max_count_tips), maxCount));
                    return false;
                }
                toolbar.getRightText().setEnabled(selectedItemCount > 0);
                if (maxCount > 1) {
                    toolbar.setRightText(getString(R.string.picker_done_with_count, selectedItemCount, maxCount));
                } else {
                    toolbar.setRightText(getString(R.string.picker_done));
                }
                return true;
            }
        });

        photoGridAdapter.setOnCameraClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!PermissionsUtils.checkCameraPermission(PhotoPickerActivity.this)) return;
                    if (!PermissionsUtils.checkWriteStoragePermission(PhotoPickerActivity.this))
                        return;
                }
                openCamera();
            }
        });

        recyclerView = findViewById(R.id.rv_photos);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(columnNumber, OrientationHelper.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(photoGridAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) > SCROLL_THRESHOLD) {
                    mGlideRequestManager.pauseRequests();
                } else {
                    resumeRequestsIfNotDestroyed();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    resumeRequestsIfNotDestroyed();
                }
            }
        });
    }

    public void updateTitleDoneItem() {
        List<ImageBean> photos = getPhotoGridAdapter().getSelectedPhotos();
        int size = photos == null ? 0 : photos.size();
        toolbar.getRightText().setEnabled(size > 0);
        if (maxCount > 1) {
            toolbar.setRightText(getString(R.string.picker_done_with_count, size, maxCount));
        } else {
            toolbar.setRightText(getString(R.string.picker_done));
        }
    }

    /**
     * Overriding this method allows us to run our exit animation first, then exiting
     * the activity when it complete.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public int getContentView() {
        return R.layout.picker_activity_photo_picker;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        toolbar = rToolbar;
        toolbar.setLeftIconVisible(true);
        toolbar.setTitle("所有照片");

        toolbar.getTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listPopupWindow.isShowing()) {
                    listPopupWindow.dismiss();
                } else {
                    adjustHeight();
                    listPopupWindow.show();
                    toolbar.getTitle().setSelected(true);
                }
            }
        });
        toolbar.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                long time;
                Log.d("sglei-time", String.valueOf(time = System.currentTimeMillis()));
                ArrayList<ImageBean> selectedPhotos = null;
                selectedPhotos = getPhotoGridAdapter().getSelectedPhotoPaths();
                Log.d("sglei-time", String.valueOf(System.currentTimeMillis()-time));
                if (selectedPhotos != null && selectedPhotos.size() > 0) {
                    intent.putParcelableArrayListExtra(KEY_SELECTED_PHOTOS, selectedPhotos);
                    intent.putExtra(EXTRA_GROUP_POSITION, groupPosition);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                Log.d("sglei-time", String.valueOf(System.currentTimeMillis()-time));
            }
        });
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.picker_photo_pull_selector);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        toolbar.getTitle().setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTitleDoneItem();
    }


    @Override
    protected void initData() {
        //获取地理位置管理器
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation=location;
                Logger.d("刷新经纬度"+location.getLatitude()+"纬度"+location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        initLocation();
    }

    private void openCamera() {
        try {
            Intent intent = captureManager.dispatchTakePictureIntent();
            startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
            newPicPath = (String) intent.getExtras().get(AppConstants.THE_REAL_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            Log.e("PhotoPickerFragment", "No Activity Found to handle Intent", e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImageCaptureManager.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            if (newPicPath == null) {
                return;
            }
            Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(newPicPath)));
            sendBroadcast(localIntent);
            if (TextUtils.isEmpty(GPSUtils.parseAddress(newPicPath))) {
                if (currentLocation!=null){
                    ExifUtils.updateLocationInfoToPic(newPicPath,currentLocation);
                }
            }

            if (captureManager == null) {
                captureManager = new ImageCaptureManager(this);
            }

            captureManager.galleryAddPic();
            if (directories.size() > 0) {
                String path = captureManager.getCurrentPhotoPath();
                PhotoDirectory directory = directories.get(INDEX_ALL_PHOTOS);
                directory.getPhotos().add(INDEX_ALL_PHOTOS, new Photo(path.hashCode(), path));
                directory.setCoverPath(path);
                photoGridAdapter.notifyDataSetChanged();
                // 默认选中拍照后的第一张图片
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PhotoGridAdapter.PhotoViewHolder photoViewHolder = (PhotoGridAdapter.PhotoViewHolder) recyclerView.findViewHolderForAdapterPosition(1);
                        if (photoViewHolder != null) {
                            photoViewHolder.autoClick();
                        }
                    }
                }, 300);
            }
        }
    }

    public void initLocation() {
        String locationProvider = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return ;
        }

        //获取所有可用的位置提供器
        List<String> providers = mLocationManager.getProviders(true);
        if (providers==null||providers.size()==0){
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return ;
        }
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
            currentLocation = mLocationManager.getLastKnownLocation(locationProvider);
        }
//        GPS定位时间比较长，故放弃该策略
//        if (currentlocation==null&&providers.contains(LocationManager.GPS_PROVIDER)) {
//            //如果是GPS
//            locationProvider = LocationManager.GPS_PROVIDER;
//            currentlocation = mLocationManager.getLastKnownLocation(locationProvider);
//        }


        if (currentLocation != null) {
            //不为空,显示地理位置经纬度
            Logger.i("TAG", "经度" + currentLocation.getLongitude() + "纬度" + currentLocation.getLatitude());
        }

        //监视地理位置变化
        if (locationProvider!=null){
            mLocationManager.requestLocationUpdates(locationProvider, 0, 0, mLocationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case PermissionsConstant.REQUEST_CAMERA:
                case PermissionsConstant.REQUEST_EXTERNAL_WRITE:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!PermissionsUtils.checkCameraPermission(PhotoPickerActivity.this))
                            return;
                        if (!PermissionsUtils.checkWriteStoragePermission(PhotoPickerActivity.this))
                            return;
                    }
                    openCamera();
                    break;
            }
        }
    }

    public PhotoGridAdapter getPhotoGridAdapter() {
        return photoGridAdapter;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        captureManager.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    public ArrayList<ImageBean> getSelectedPhotoPaths() {
        return photoGridAdapter.getSelectedPhotoPaths();
    }

    public void adjustHeight() {
        if (listAdapter == null) return;
        int count = listAdapter.getCount();
        count = count < COUNT_MAX ? count : COUNT_MAX;
        if (listPopupWindow != null) {
            listPopupWindow.setHeight(count * getResources().getDimensionPixelOffset(R.dimen.picker_item_directory_height));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationManager.removeUpdates(mLocationListener);
        if (directories == null) {
            return;
        }

        for (PhotoDirectory directory : directories) {
            directory.getPhotoPaths().clear();
            directory.getPhotos().clear();
            directory.setPhotos(null);
        }
        directories.clear();
        directories = null;
    }

    private void resumeRequestsIfNotDestroyed() {
        if (!AndroidLifecycleUtils.canLoadImage(this)) {
            return;
        }

        mGlideRequestManager.resumeRequests();
    }

}
