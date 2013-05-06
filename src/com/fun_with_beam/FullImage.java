package com.fun_with_beam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class FullImage extends Activity {
	 /** Called when the activity is first created. */
	Bitmap bitmap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen);
        Intent intent = getIntent();
        bitmap = (Bitmap) intent.getExtras().get("image");
        ImageView imageView = (ImageView) findViewById(R.id.imageviewFull);
        imageView.setImageBitmap(bitmap);
   
    }

}
