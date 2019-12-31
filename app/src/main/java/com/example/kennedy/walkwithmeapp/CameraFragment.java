package com.example.kennedy.walkwithmeapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class CameraFragment extends android.support.v4.app.Fragment{

    FloatingActionButton fabCam;
    ImageView image;
    Bitmap bm;
    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_camera, container, false);
         fabCam = v.findViewById(R.id.fabCamera);


        fabCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
//                startActivityForResult(intent,0);
            }
        });

        return v;


    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        bm = ((BitmapDrawable)image.getDrawable()).getBitmap();
        image.setImageBitmap(bm);
    }
}
