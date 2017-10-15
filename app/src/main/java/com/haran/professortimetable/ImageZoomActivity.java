package com.haran.professortimetable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Created by haran on 25-Sep-17.
 */

public class ImageZoomActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        String url = getIntent().getStringExtra("url");
        if(url == null){
            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        ImageViewTouch imageViewTouch = (ImageViewTouch) findViewById(R.id.zoom_view);
        imageViewTouch.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .into((ImageView) findViewById(R.id.zoom_view));
    }
}
