package com.example.app_last;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.imgloader.R;
/*
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}*/
import androidx.annotation.NonNull;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends Activity implements CvCameraViewListener2 {

    private static final String TAG = "OCVSample::Activity";
    private Mat mRgba;
    private Mat mGray;
    private CameraBridgeViewBase mOpenCvCameraView;


        private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                switch (status) {
                    case LoaderCallbackInterface.SUCCESS: {
                        Log.i(TAG, "OpenCV loaded successfully");
                        mOpenCvCameraView.enableView();
                        mOpenCvCameraView.setOnTouchListener((View.OnTouchListener) MainActivity.this);
                    }
                    break;
                    default: {
                        super.onManagerConnected(status);
                    }
                    break;
                }
            }
        };






            public MainActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate( savedInstanceState );
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        setContentView(R.layout.tutorial1_surface_view);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById ( R.id.tutorial1_activity_java_surface_view );

        mOpenCvCameraView.setVisibility( SurfaceView.VISIBLE );
        mOpenCvCameraView.setCvCameraViewListener(this);
    }

        @Override
        public void onPause()
        {
            super.onPause();
            if (mOpenCvCameraView != null)
                mOpenCvCameraView.disableView();
        }

        @Override
        public void onResume()
        {
            super.onResume();
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this,
                    mLoaderCallback);
        }

        public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }




        public void onCameraViewStarted(int width, int height) {
        mGray = new Mat();
        mRgba = new Mat();
    }

        public void onCameraViewStopped() {
    }

        public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        Imgproc.cvtColor(mRgba, mGray, Imgproc.COLOR_BGRA2GRAY);
        return mGray;
    }


    }