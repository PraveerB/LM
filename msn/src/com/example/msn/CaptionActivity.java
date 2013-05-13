package com.example.msn;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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
				executeMultipartPost();
				Intent doneIntent = new Intent("com.example.msn.DONE");
				startActivity(doneIntent);
			}
		});
		
		
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.caption, menu);
		return true;
	}
	
    public void executeMultipartPost(){
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy); 
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			BitmapDrawable drawable = (BitmapDrawable) uploadedImage.getDrawable();
			Bitmap bitmap = drawable.getBitmap();
			bitmap.compress(CompressFormat.JPEG, 50, bos);
			byte[] data = bos.toByteArray();
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://msncontest-fb.azurewebsites.net/index.php/site/getMobileData");
			String fileName = String.format("File_%d.png",new Date().getTime());
			ByteArrayBody bab = new ByteArrayBody(data, fileName);
			
			// File file= new File("/mnt/sdcard/forest.png");
			// FileBody bin = new FileBody(file);
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			
//Parameters to send to server
			reqEntity.addPart("file", bab);
			reqEntity.addPart("name", new StringBody(UserInfo.getName()));
			reqEntity.addPart("city", new StringBody(UserInfo.getCity()));
			reqEntity.addPart("phone", new StringBody(UserInfo.getContact()));
			reqEntity.addPart("email", new StringBody(UserInfo.getEmail()));
			reqEntity.addPart("uid", new StringBody(UserInfo.getFb_id()));
			
			
			postRequest.setEntity(reqEntity);
			int timeoutConnection = 60000;
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,timeoutConnection);
			int timeoutSocket = 60000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpResponse response = httpClient.execute(postRequest);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
			response.getEntity().getContent(), "UTF-8"));
			String sResponse;
 
			StringBuilder s = new StringBuilder();
 
			while ((sResponse = reader.readLine()) != null) {

				s = s.append(sResponse);
 
			}
 
			System.out.println("Response: " + s);
 
		} catch (Exception e) {
			System.out.println("Exception");
			// handle exception here
			e.printStackTrace();
			// Log.e(e.getClass().getName(), e.getMessage());
		}
 
	}


}
