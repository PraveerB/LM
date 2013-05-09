package com.example.msn;

import android.opengl.Visibility;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class UploadGalleryActivity extends Activity {
	ImageView uploadBtn;
	//ImageView uploadedImage;
	ImageView uploadChoices;
	Intent cameraIntent;
	ImageView closeBtn;
	Bitmap bmp;
	private ImageView selectFromGalleryBtn;
	final static int cameraData = 0; 
	RelativeLayout popupLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("startActivityForResult");
		setContentView(R.layout.activity_uploadgallery);
		uploadBtn = (ImageView) findViewById(R.id.camera);
		popupLayout = (RelativeLayout) findViewById(R.id.popupLayout);
		uploadChoices = (ImageView) findViewById(R.id.uploadBtn);
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
			    cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			    startActivityForResult(cameraIntent, cameraData);
			    //setContentView(R.layout.activity_main);
			    
			}
		});
		//uploadedImage = (ImageView) findViewById(R.id.uploadedImage);
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
		if(resultCode == RESULT_OK){
			Bundle extras= data.getExtras();
			bmp = (Bitmap) extras.get("data");
			//uploadedImage = (ImageView) findViewById(R.id.uploadedImagePreview);
			//uploadedImage.setImageBitmap(bmp);
			Intent captionIntent = new Intent("com.example.msn.CAPTION");
			startActivity(captionIntent);
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
		int PICK_IMAGE = 0;
		startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE );
	}
	
	
//Save in Gallery
	/*Uri saveMediaEntry(String imagePath,String title,String description,long dateTaken,int orientation,Location loc) {
			ContentValues v = new ContentValues();
			String temp = "image/jpeg";
			v.put(Images.Media.TITLE, title);
			v.put(Images.Media.DISPLAY_NAME, displayName);
			v.put(Images.Media.DESCRIPTION, description);
			v.put(Images.Media.DATE_ADDED, dateTaken);
			v.put(Images.Media.DATE_TAKEN, dateTaken);
			v.put(Images.Media.DATE_MODIFIED, dateTaken) ;
			v.put(Images.Media.MIME_TYPE, temp);
			v.put(Images.Media.ORIENTATION, orientation);
			File f = new File(imagePath) ;
			File parent = f.getParentFile() ;
			String path = parent.toString().toLowerCase() ;
			String name = parent.getName().toLowerCase() ;
			v.put(Images.ImageColumns.BUCKET_ID, path.hashCode());
			v.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, name);
			v.put(Images.Media.SIZE,f.length()) ;
			f = null ;
			//Object targ_loc= ;
			if( targ_loc != null ) {
			v.put(Images.Media.LATITUDE, loc.getLatitude());
			v.put(Images.Media.LONGITUDE, loc.getLongitude());
			}
			v.put("_data" ,imagePath) ;
			ContentResolver c = getContentResolver() ;
			return c.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, v);
	}*/
}
