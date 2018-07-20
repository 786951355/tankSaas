package com.tanker.homemodule.view;

import android.content.Intent;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.widget.TextView;

import com.tanker.basemodule.adapter.PhotoWithDeleteAdapter;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.event.RecyclerItemClickListener;
import com.tanker.basemodule.model.ImageBean;
import com.tanker.basemodule.view.PhotoPicker;
import com.tanker.basemodule.view.PhotoPreview;
import com.tanker.homemodule.R;
import com.tanker.homemodule.presenter.FaultAddPresenter;

import java.util.ArrayList;
import java.util.List;

public class FaultAddActivity extends BaseActivity<FaultAddPresenter> implements PhotoWithDeleteAdapter.OnDeleteItemListener {
    private PhotoWithDeleteAdapter adapter;
    private RecyclerView rvPicture;
    private ArrayList<ImageBean> images = new ArrayList<>();

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_label_address)).setText(Html.fromHtml(getString(R.string.homemodule_label_address)));
        ((TextView) findViewById(R.id.tv_label_fault)).setText(Html.fromHtml(getString(R.string.homemodule_label_fault)));
        ((TextView) findViewById(R.id.tv_label_time)).setText(Html.fromHtml(getString(R.string.homemodule_label_fault_time)));

        images.add(new ImageBean(PhotoWithDeleteAdapter.TYPE_ADD));
        adapter = new PhotoWithDeleteAdapter(this, images, this);
        rvPicture = findViewById(R.id.rv_picture);
        rvPicture.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        rvPicture.setAdapter(adapter);

        rvPicture.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    if (adapter.getItemViewType(position) == PhotoWithDeleteAdapter.TYPE_ADD) {
                        PhotoPicker.builder()
                                .setPhotoCount(PhotoWithDeleteAdapter.MAX)
                                .setShowCamera(true)
                                .setPreviewEnabled(false)
                                .setSelected(images)
                                .start(FaultAddActivity.this);
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            List<ImageBean> paths = null;
            if (data != null) {
                paths = data.getParcelableArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            images.clear();
            if (images != null) {
                images.addAll(paths);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.homemodule_activity_fault_add;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setToolbarVisible(true).setTitle(getString(R.string.homemodule_title_fault_add));
    }

    @Override
    public void delete(int position) {

    }
}
