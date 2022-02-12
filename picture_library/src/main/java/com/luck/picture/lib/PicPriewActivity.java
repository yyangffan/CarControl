package com.luck.picture.lib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.photoview.PhotoView;

public class PicPriewActivity extends PictureBaseActivity {
    private PhotoView mPhotoView;


    @Override
    public int getResourceId() {
        return R.layout.activity_pic_priew;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        Intent intent = getIntent();
        String path =intent.getStringExtra("path");
        mPhotoView= findViewById(R.id.pic_priew_photo);
        Glide.with(this).load(path).into(mPhotoView);
        findViewById(R.id.left_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}