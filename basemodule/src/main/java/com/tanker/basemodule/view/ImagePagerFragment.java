package com.tanker.basemodule.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tanker.basemodule.R;
import com.tanker.basemodule.adapter.PhotoPagerAdapter;
import com.tanker.basemodule.model.ImageBean;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerFragment extends Fragment {

    public final static String ARG_PATH = "PATHS";
    public final static String ARG_CURRENT_ITEM = "ARG_CURRENT_ITEM";

    private ArrayList<ImageBean> paths;
    private ViewPager mViewPager;
    private PhotoPagerAdapter mPagerAdapter;

    private int currentItem = 0;
    private String waterMark;

    public static ImagePagerFragment newInstance(ArrayList<ImageBean> paths, int currentItem) {

        ImagePagerFragment f = new ImagePagerFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PATH, paths);
        args.putInt(ARG_CURRENT_ITEM, currentItem);

        f.setArguments(args);

        return f;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof PhotoPickerActivity) {
            PhotoPickerActivity photoPickerActivity = (PhotoPickerActivity) getActivity();
            photoPickerActivity.updateTitleDoneItem();
        }
    }

    public void setPhotos(List<ImageBean> paths, int currentItem) {
        this.paths.clear();
        this.paths.addAll(paths);
        this.currentItem = currentItem;

        mViewPager.setCurrentItem(currentItem);
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    public void setPhotos(List<ImageBean> paths, int currentItem, String waterMark) {
        this.waterMark = waterMark;
        if (mPagerAdapter != null) {
            mPagerAdapter.setWaterMark(waterMark);
        }
        setPhotos(paths, currentItem);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        paths = new ArrayList<>();

        Bundle bundle = getArguments();

        if (bundle != null) {
            paths = bundle.getParcelableArrayList(ARG_PATH);
            currentItem = bundle.getInt(ARG_CURRENT_ITEM);
        }

        mPagerAdapter = new PhotoPagerAdapter(Glide.with(this), paths, waterMark);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.picker_picker_fragment_image_pager, container, false);

        mViewPager = rootView.findViewById(R.id.vp_photos);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentItem);
        mViewPager.setOffscreenPageLimit(5);

        return rootView;
    }


    public ViewPager getViewPager() {
        return mViewPager;
    }


    public ArrayList<ImageBean> getPaths() {
        return paths;
    }


    public ArrayList<ImageBean> getCurrentPath() {
        ArrayList<ImageBean> list = new ArrayList<>();
        int position = mViewPager.getCurrentItem();
        if (paths != null && paths.size() > position) {
            list.add(paths.get(position));
        }
        return list;
    }


    public int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        paths.clear();
        paths = null;

        if (mViewPager != null) {
            mViewPager.setAdapter(null);
        }
    }
}
