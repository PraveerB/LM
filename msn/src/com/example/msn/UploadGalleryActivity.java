package com.example.msn;

import java.io.ByteArrayOutputStream;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class UploadGalleryActivity extends Activity {
	ImageView uploadBtn;
	ImageView uploadChoices;
	Intent cameraIntent;
	ImageView closeBtn;
	Bitmap bmp;
	ImageView galleryBtn;
	private ImageView selectFromGalleryBtn;
	boolean galleryData = false;
	boolean cameraData = false;
	RelativeLayout popupLayout;
	UserInfo userInfo = MainActivity.userInfo ;
	@SuppressLint("NewApi")
	@Override
	public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
		// TODO Auto-generated method stub
		super.startActivityForResult(intent, requestCode, options);
		if(requestCode == RESULT_OK) {
			//System.out.println("Inside if...");
			//Bundle data = intent.getExtras();
			//bmp = (Bitmap) data.get("data");
			//uploadedImage.setImageBitmap(bmp);
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uploadgallery);
		if(userInfo == null){
			userInfo = new UserInfo();
		}
		
		uploadBtn = (ImageView) findViewById(R.id.camera);
		popupLayout = (RelativeLayout) findViewById(R.id.popupLayout);
		uploadChoices = (ImageView) findViewById(R.id.uploadBtn);
		galleryBtn = (ImageView) findViewById(R.id.showGalleryBtn);
		
		galleryBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent showGallery = new Intent("com.example.msn.GALLERY");
				startActivity(showGallery);
			}
		});
		uploadChoices.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupLayout.setVisibility(0);
			}
		});
		uploadBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    System.out.println("AAAAAAAAAAAAAAAAAA");
			    cameraData =true;
			    cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			    startActivityForResult(cameraIntent, 1);
			    //setContentView(R.layout.activity_main);
			    
			}
		});
		closeBtn = (ImageView) findViewById(R.id.close);
		closeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// hide options
				popupLayout.setVisibility(View.INVISIBLE);
			}
		});
		
		selectFromGalleryBtn = (ImageView) findViewById(R.id.phonegallery);
		selectFromGalleryBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectFromGallery();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
		
		System.out.println("onActivityResult...");
		if(resultCode == RESULT_OK){
			System.out.println("Inside if.."+ requestCode);
			if( cameraData) {
				Bundle extras= data.getExtras();
				//bmp = (Bitmap) extras.get("data");
				
				Bitmap bmpHold = (Bitmap) extras.get("data");
				
				bmp = Bitmap.createScaledBitmap(bmpHold,(int)(bmpHold.getWidth()*0.6), (int)(bmpHold.getHeight()*0.6), true);
				
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
	            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
	            byte[] byteArray = stream.toByteArray();
				
	            //System.out.println("Camera.........");
				//System.out.println(byteArray);
	            //System.out.println(userInfo.getUploadedImage());
	            
	            userInfo.setUploadedImage(byteArray);
	            //System.out.println(userInfo.getUploadedImage());

				Intent captionIntent = new Intent("com.example.msn.CAPTION");
				startActivity(captionIntent);
			}
			else if(galleryData) {
				//galery processing
				System.out.println("gallery processing");
				
				Uri selectedImage = data.getData();
	            String[] filePathColumn = {MediaStore.Images.Media.DATA};
	            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
	            cursor.moveToFirst();
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String filePath = cursor.getString(columnIndex);
	            cursor.close();
	            //bmp = BitmapFactory.decodeFile(filePath);
				Bitmap bmpHold = BitmapFactory.decodeFile(filePath);
				
				bmp = Bitmap.createScaledBitmap(bmpHold,(int)(bmpHold.getWidth()*0.6), (int)(bmpHold.getHeight()*0.6), true);

	            
	            ByteArrayOutputStream stream = new ByteArrayOutputStream();
	            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
	            byte[] byteArray = stream.toByteArray();
	            
	            userInfo.setUploadedImage(byteArray);
				Intent captionIntent = new Intent("com.example.msn.CAPTION");
				startActivity(captionIntent);
			}
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gallery, menu);
		return true;
	}

	private void selectFromGallery() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		galleryData = true;
		startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
		
		
	}
}