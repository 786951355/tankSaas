package com.tanker.mainmodule.debug;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tanker.basemodule.model.ImageBean;
import com.tanker.basemodule.model.PictureInfo;
import com.tanker.basemodule.utils.ImageUtils;
import com.tanker.basemodule.utils.PictureUtils;
import com.tanker.basemodule.utils.WaterMarkUtils;
import com.tanker.basemodule.view.PhotoPicker;
import com.tanker.mainmodule.R;

import java.util.List;

/**
 * Created by ronny on 2018/3/21.
 */

public class TestMainActivity extends Activity {

    private ImageView testImg;
    private Button btn;
    private String temPath= "/storage/emulated/0/DCIM/Camera/temp.jpg";
    private String TAG = "fdf";
    private String addressLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testmain);
        btn = findViewById(R.id.btn);
        testImg = findViewById(R.id.iv_test);
        btn.setOnClickListener(v -> PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .start(TestMainActivity.this));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == PhotoPicker.REQUEST_CODE)) {
            List<ImageBean> photos = null;
            if (data != null) {
                photos = data.getParcelableArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            if (photos != null && photos.size() > 0) {
                ImageUtils.compressImg(photos.get(0).getLocalPath(), temPath,800);
                Bitmap sourceBitmap = ImageUtils.readBitmapFromFile(temPath);
                PictureInfo picInfo = PictureUtils.getPicInfo(photos.get(0).getLocalPath());
                Bitmap waterMaskLeftBottom= WaterMarkUtils.drawWatermark(sourceBitmap,picInfo,"运单号：Y45454354356");
                testImg.setImageBitmap(waterMaskLeftBottom);
            }
        }
    }









}
