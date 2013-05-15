package com.example.msn;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

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
		/*
		Session.openActiveSession(this, true, new Session.StatusCallback() {
			  
		      // callback when session changes state
		      @Override 	
		      public void call(Session session, SessionState state, Exception exception) {
		        if (session.isOpened()) {
		          // make request to the /me API
		          Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
		        	 
		            // callback after Graph API response with user object
		            @Override
		            public void onCompleted(GraphUser user, Response response) {
		              if (user != null) {
		                TextView welcome = (TextView) findViewById(R.id.welcome);
		                welcome.setText("Hello " + user.getId() + "!");
		                UserInfo.setFb_id(user.getId());
		                //welcome.setText(user.toString());
		              }
		            }
		          });
		        }
		        else{
		        	//call(session, state, exception);
		        }
		      }
		    });	
		*/
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
				bmp = (Bitmap) extras.get("data");
				
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
	            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
	            byte[] byteArray = stream.toByteArray();
				
	            System.out.println("Camera.........");
				System.out.println(byteArray);
	            System.out.println(UserInfo.getUploadedImage());
	            
				UserInfo.setUploadedImage(byteArray);
	            System.out.println(UserInfo.getUploadedImage());

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
	            /*
	            HttpURLConnection connection = null;
	            DataOutputStream outputStream = null;
	            DataInputStream inputStream = null;

	            String pathToOurFile = filePath;
	            String urlServer = "http://msncontest-fb.azurewebsites.net/index.php/site/getMobileData";
	            String lineEnd = "\r\n";
	            String twoHyphens = "--";
	            String boundary =  "*****";

	            int bytesRead, bytesAvailable, bufferSize;
	            byte[] buffer;
	            int maxBufferSize = 1*1024*1024;

	            try
	            {
	            FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );

	            URL url = new URL(urlServer);
	            connection = (HttpURLConnection) url.openConnection();

	            // Allow Inputs & Outputs
	            connection.setDoInput(true);
	            connection.setDoOutput(true);
	            connection.setUseCaches(false);

	            // Enable POST method
	            connection.setRequestMethod("POST");

	            connection.setRequestProperty("Connection", "Keep-Alive");
	            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

	            outputStream = new DataOutputStream( connection.getOutputStream() );
	            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
	            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathToOurFile +"\"" + lineEnd);
	            outputStream.writeBytes(lineEnd);

	            bytesAvailable = fileInputStream.available();
	            bufferSize = Math.min(bytesAvailable, maxBufferSize);
	            buffer = new byte[bufferSize];

	            // Read file
	            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

	            while (bytesRead > 0)
	            {
	            outputStream.write(buffer, 0, bufferSize);
	            bytesAvailable = fileInputStream.available();
	            bufferSize = Math.min(bytesAvailable, maxBufferSize);
	            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
	            }

	            outputStream.writeBytes(lineEnd);
	            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

	            // Responses from the server (code and message)
	            int serverResponseCode = connection.getResponseCode();
	            String serverResponseMessage = connection.getResponseMessage();
	            System.out.println(serverResponseCode);
	            System.out.println(serverResponseMessage);
	            fileInputStream.close();
	            outputStream.flush();
	            outputStream.close();
	            }
	            catch (Exception ex)
	            {
	            //Exception handling
	            }*/
	            bmp = BitmapFactory.decodeFile(filePath);
	            
	            ByteArrayOutputStream stream = new ByteArrayOutputStream();
	            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
	            byte[] byteArray = stream.toByteArray();
	            
				UserInfo.setUploadedImage(byteArray);
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
