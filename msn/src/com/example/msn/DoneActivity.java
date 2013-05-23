package com.example.msn;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class DoneActivity extends Activity {
	ImageView galleryBtn;
	ImageView uploadedImagePreviewDone;
	UserInfo userInfo = MainActivity.userInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_done);
		if(userInfo == null ){
			userInfo = new UserInfo();
		}
		 
		uploadedImagePreviewDone = (ImageView)findViewById(R.id.uploadedImagePreviewDone);
		byte[] imageByteArray = userInfo.getUploadedImage();
		uploadedImagePreviewDone.setImageBitmap(BitmapFactory.decodeByteArray(imageByteArray  , 0, imageByteArray.length));
		
		galleryBtn=(ImageView)findViewById(R.id.viewGallery);
		galleryBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent galleryIntent = new Intent("com.example.msn.GALLERY");
				startActivity(galleryIntent);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.done, menu);
		return true;
	}
}