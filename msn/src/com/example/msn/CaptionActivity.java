package com.example.msn;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CaptionActivity extends Activity {
	ImageView changeBtn;
	ImageView submitBtn;
	EditText caption;
	String captionText;
	ImageView uploadedImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_caption);
		uploadedImage = (ImageView) findViewById(R.id.uploadedImageCaption);
		
		byte[] imageByteArray = UserInfo.getUploadedImage();
		
		uploadedImage.setImageBitmap(BitmapFactory.decodeByteArray(imageByteArray  , 0, imageByteArray.length));
		
		changeBtn = (ImageView) findViewById(R.id.changeBtn);
		submitBtn = (ImageView) findViewById(R.id.captionSubmit);
		caption = (EditText) findViewById(R.id.caption);
		captionText = caption.getText().toString();
		changeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent uploadGalleryIntent = new Intent("com.example.msn.UPLOADGALLERY");
				startActivity(uploadGalleryIntent);
			}
		});
		submitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// save data
				if((caption.getText().toString()).length() == 0) {
					Toast.makeText(getBaseContext(), "Please Enter Caption!!", Toast.LENGTH_SHORT).show();
				}
				else {
					UserInfo.setCaption(caption.getText().toString());
					ConnectionHelper con = new ConnectionHelper();
	 				con.executeMultipartPost();
					Intent doneIntent = new Intent("com.example.msn.DONE");
					startActivity(doneIntent);
				}
			}
		});
		
		
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.caption, menu);
		return true;
	}
	
    


}
