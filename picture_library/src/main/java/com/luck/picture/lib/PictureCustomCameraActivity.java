package com.luck.picture.lib;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.picture.lib.adapter.PictcusAdapter;
import com.luck.picture.lib.camera.CustomCameraType;
import com.luck.picture.lib.camera.CustomCameraView;
import com.luck.picture.lib.camera.listener.CameraListener;
import com.luck.picture.lib.camera.view.CaptureLayout;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.dialog.PictureCustomDialog;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnPermissionDialogOptionCallback;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.tools.BitmapUtils;
import com.luck.picture.lib.tools.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author：luck
 * @date：2020-01-04 14:05
 * @describe：Custom photos and videos
 */
public class PictureCustomCameraActivity extends PictureSelectorCameraEmptyActivity {
    private final static String TAG = PictureCustomCameraActivity.class.getSimpleName();

    private CustomCameraView mCameraView;
    protected boolean isEnterSetting;
    private ImageView mimg_take;
    private RecyclerView mrecy_cam;
    private TextView mTextWaterMarker,mTextWatShow;
    private List<LocalMedia> mCusImgvs;
    private PictcusAdapter mPictcusAdapter;
    private int now_count = 0;
    private String mWatermark;
    private Bitmap mBit_waterMarkers;

    @Override
    public boolean isImmersive() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mWatermark = intent.getStringExtra("watermark");
        mCusImgvs = intent.getParcelableArrayListExtra("data");
        setContentView(R.layout.picture_custom_camera);
        mCameraView = findViewById(R.id.cameraView);
        mimg_take = findViewById(R.id.pic_cus_takpic);
        mrecy_cam = findViewById(R.id.pic_cus_recy);
        mTextWaterMarker = findViewById(R.id.pic_cus_watermarker);
        mTextWatShow = findViewById(R.id.pic_cus_watermarkershow);
        initView();
        requestCamera();
    }

    private void requestCamera() {
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // 验证相机权限和麦克风权限
            if (PermissionChecker.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                if (config.buttonFeatures == CustomCameraType.BUTTON_STATE_ONLY_CAPTURE) {
                    mCameraView.initCamera();
                } else {
                    if (PermissionChecker.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                        mCameraView.initCamera();
                    } else {
                        PermissionChecker.requestPermissions(this,
                                new String[]{Manifest.permission.RECORD_AUDIO}, PictureConfig.APPLY_RECORD_AUDIO_PERMISSIONS_CODE);
                    }
                }
            } else {
                PermissionChecker.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, PictureConfig.APPLY_CAMERA_PERMISSIONS_CODE);
            }
        } else {
            PermissionChecker.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 这里只针对权限被手动拒绝后进入设置页面重新获取权限后的操作
        if (isEnterSetting) {
            if (PermissionChecker.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (PermissionChecker.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                    if (config.buttonFeatures == CustomCameraType.BUTTON_STATE_ONLY_CAPTURE) {
                        mCameraView.initCamera();
                    } else {
                        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                            mCameraView.initCamera();
                        } else {
                            PermissionChecker.requestPermissions(this,
                                    new String[]{Manifest.permission.RECORD_AUDIO}, PictureConfig.APPLY_RECORD_AUDIO_PERMISSIONS_CODE);
                        }
                    }
                } else {
                    showPermissionsDialog(false, new String[]{Manifest.permission.CAMERA}, getString(R.string.picture_camera));
                }
            } else {
                showPermissionsDialog(false, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, getString(R.string.picture_jurisdiction));
            }
            isEnterSetting = false;
        }
    }

    /**
     * 初始化控件
     */
    protected void initView() {
        //设置水印
        mTextWaterMarker.setText(mWatermark);
        mTextWatShow.setText(mWatermark);
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                mBit_waterMarkers = getBit();
            }
        }.start();
        //设置数据展示
        mPictcusAdapter = new PictcusAdapter(this,mCusImgvs);
        LinearLayoutManager linearLayoutManager  =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mrecy_cam.setLayoutManager(linearLayoutManager);
        mrecy_cam.setAdapter(mPictcusAdapter);
        mPictcusAdapter.setOnItemClickListener(new PictcusAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos) {
                mrecy_cam.scrollToPosition(pos);
                now_count = pos;
            }
        });

        mCameraView.setShowTip(false);//不展示提示信息
        // 视频最大拍摄时长
        if (config.recordVideoSecond > 0) {
            mCameraView.setRecordVideoMaxTime(config.recordVideoSecond);
        }
        // 视频最小拍摄时长
        if (config.recordVideoMinSecond > 0) {
            mCameraView.setRecordVideoMinTime(config.recordVideoMinSecond);
        }
        // 设置拍照时loading色值
        mCameraView.setCaptureLoadingColor(config.captureLoadingColor);
        // 获取录制按钮
        CaptureLayout captureLayout = mCameraView.getCaptureLayout();
        if (captureLayout != null) {
            captureLayout.setButtonFeatures(config.buttonFeatures);
        }
        // 拍照预览
        mCameraView.setImageCallbackListener((url, imageView) -> {
            if (config != null && PictureSelectionConfig.imageEngine != null) {
                PictureSelectionConfig.imageEngine.loadImage(getContext(), url, imageView);
            }
        });
        // 设置拍照或拍视频回调监听
        mCameraView.setCameraListener(new CameraListener() {
            @Override
            public void onPictureSuccess(@NonNull String url) {
                config.cameraMimeType = PictureMimeType.ofImage();
                Intent intent = new Intent();
                intent.putExtra(PictureConfig.EXTRA_MEDIA_PATH, url);
                intent.putExtra(PictureConfig.EXTRA_CONFIG, config);
                if (config.camera) {
                    dispatchHandleCamera(intent);
                } else {
                    setResult(RESULT_OK, intent);
                    onBackPressed();
                }
            }

            @Override
            public void onRecordSuccess(@NonNull String url) {
                config.cameraMimeType = PictureMimeType.ofVideo();
                Intent intent = new Intent();
                intent.putExtra(PictureConfig.EXTRA_MEDIA_PATH, url);
                intent.putExtra(PictureConfig.EXTRA_CONFIG, config);
                if (config.camera) {
                    dispatchHandleCamera(intent);
                } else {
                    setResult(RESULT_OK, intent);
                    onBackPressed();
                }
            }

            @Override
            public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                Log.i(TAG, "onError: " + message);
            }
        });

        //左边按钮点击事件
//        mCameraView.setOnClickListener(new ClickListener() {
//            @Override
//            public void onClick() {
//                onBackPressed();
//            }
//        });
        mimg_take.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mCameraView.goTakePicture();
            }
        });
    }

    @Override
    public void onPicResult(List<LocalMedia> images) {
        super.onPicResult(images);
        mCameraView.resetState();
        String pic_path = images.get(0).getPath();
        LocalMedia localMedia = mCusImgvs.get(now_count);
        localMedia.setPath(pic_path);
        mPictcusAdapter.notifyItemChanged(now_count);
        if (PictureSelectionConfig.listener != null) {
            PictureSelectionConfig.listener.onCusResult(images,now_count);
        }
        if(now_count<mCusImgvs.size()-1){
            now_count+=1;
        }
        mrecy_cam.scrollToPosition(now_count);
        togeBit(BitmapFactory.decodeFile(pic_path),new File(pic_path));
        Log.e(TAG, "onPicResult: 拍照结束"+pic_path );

    }


    /*
     * 图片整合
     * */
    private void togeBit(Bitmap bitmap, File file) {
/*        if (!bitmap.isMutable()) {
            //设置图片为背景为透明
            bitmap = bitmap.copy(Bitmap.Config.ARGB_4444, true);
            int degree = BitmapUtils.readPictureDegree(this,file.getPath());
            bitmap = BitmapUtils.rotatingImage(bitmap,degree);
        }
        Paint paint = new Paint();
        paint.setARGB(255, 210, 177, 240);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(mBit_waterMarkers, 0, 0, paint);//叠加新图b2
        canvas.save();
        canvas.restore();
        BitmapUtils.saveBitmapFile(bitmap, file);//保存图片到本地*/
//        Bitmap bitmap_result = ImageUtil.drawTextToLeftTop(this,bitmap,mWatermark,36,R.color.ucrop_color_active_controls_color,10,10);
        Bitmap bitmap_result = ImageUtil.createWaterMaskBitmap(bitmap,mBit_waterMarkers,10,10);
        BitmapUtils.saveBitmapFile(bitmap_result, file);//保存图片到本地
    }

    /*获取水印图片的Bitmap*/
    private Bitmap getBit() {
        //打开图片的缓存
        mTextWaterMarker.setDrawingCacheEnabled(true);
        //图片的大小 固定的语句
        mTextWaterMarker.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //将位置传给view
        mTextWaterMarker.layout(0, 0, mTextWaterMarker.getMeasuredWidth(), mTextWaterMarker.getMeasuredHeight());
        //转化为bitmap文件
        Bitmap bitmap = mTextWaterMarker.getDrawingCache();
        return bitmap;
    }

    @Override
    public void onBackPressed() {
        if (config != null && config.camera && PictureSelectionConfig.listener != null) {
            PictureSelectionConfig.listener.onCancel();
        }
        exit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            mCameraView.onCancelMedia();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE:
                // 存储权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PermissionChecker.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA}, PictureConfig.APPLY_CAMERA_PERMISSIONS_CODE);
                } else {
                    showPermissionsDialog(true, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, getString(R.string.picture_jurisdiction));
                }
                break;
            case PictureConfig.APPLY_CAMERA_PERMISSIONS_CODE:
                // 相机权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (PermissionChecker.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                        mCameraView.initCamera();
                    } else {
                        PermissionChecker.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PictureConfig.APPLY_RECORD_AUDIO_PERMISSIONS_CODE);
                    }
                } else {
                    showPermissionsDialog(true, new String[]{Manifest.permission.CAMERA}, getString(R.string.picture_camera));
                }
                break;
            case PictureConfig.APPLY_RECORD_AUDIO_PERMISSIONS_CODE:
                // 录音权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCameraView.initCamera();
                } else {
                    showPermissionsDialog(false, new String[]{Manifest.permission.RECORD_AUDIO}, getString(R.string.picture_audio));
                }
                break;
        }
    }

    @Override
    protected void showPermissionsDialog(boolean isCamera, String[] permissions, String errorMsg) {
        if (isFinishing()) {
            return;
        }
        if (PictureSelectionConfig.onPermissionsObtainCallback != null) {
            PictureSelectionConfig.onPermissionsObtainCallback.onPermissionsIntercept(getContext(),
                    isCamera, permissions, errorMsg, new OnPermissionDialogOptionCallback() {

                        @Override
                        public void onCancel() {
                            if (PictureSelectionConfig.listener != null) {
                                PictureSelectionConfig.listener.onCancel();
                            }
                            exit();
                        }

                        @Override
                        public void onSetting() {
                            isEnterSetting = true;
                        }
                    });
            return;
        }
        PictureCustomDialog dialog = new PictureCustomDialog(getContext(), R.layout.picture_wind_base_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button btn_commit = dialog.findViewById(R.id.btn_commit);
        btn_commit.setText(getString(R.string.picture_go_setting));
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextView tv_content = dialog.findViewById(R.id.tv_content);
        tvTitle.setText(getString(R.string.picture_prompt));
        tv_content.setText(errorMsg);
        btn_cancel.setOnClickListener(v -> {
            if (!isFinishing()) {
                dialog.dismiss();
            }
            if (PictureSelectionConfig.listener != null) {
                PictureSelectionConfig.listener.onCancel();
            }
            exit();
        });
        btn_commit.setOnClickListener(v -> {
            if (!isFinishing()) {
                dialog.dismiss();
            }
            PermissionChecker.launchAppDetailsSettings(getContext());
            isEnterSetting = true;
        });
        dialog.show();
    }
}
