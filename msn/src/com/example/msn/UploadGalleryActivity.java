package com.example.msn;

import java.io.ByteArrayOutputStream;

import com.facebook.*;
import com.facebook.android.Facebook;
import com.facebook.model.*;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UploadGalleryActivity extends Activity {
	ImageView uploadBtn;
	ImageView uploadChoices;
	Intent cameraIntent;
	ImageView closeBtn;
	Bitmap bmp;
	private ImageView selectFromGalleryBtn;
	private ImageView selectFromFacebookBtn;
	private ImageView showGalleryBtn;
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
		
		uploadChoices.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				faceBookGraphApiCall();
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
		
		showGalleryBtn = (ImageView) findViewById(R.id.showGalleryBtn);
		showGalleryBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent galleryIntent =new Intent("com.example.msn.GALLERY");
				startActivity(galleryIntent);
			}
		});
		
		selectFromFacebookBtn = (ImageView) findViewById(R.id.facebookSelectPhoto);
		selectFromFacebookBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//show all facebook photos
				System.out.println("show all facebook photos........");
				getFaceBookUserPhotos();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
		
		System.out.println("onActivityResult...");
		if(resultCode == RESULT_OK){
			System.out.println("Inside if.."+ requestCode);
			if( cameraData) {
				Bundle extras= data.getExtras();
				bmp = (Bitmap) extras.get("data");
				
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
	            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
	            byte[] byteArray = stream.toByteArray();
				
				UserInfo.setUploadedImage(byteArray);
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

//get facebook user data
	private void faceBookGraphApiCall(){
		Session.openActiveSession(this, true, new Session.StatusCallback() {
			
		      // callback when session changes state
		      @Override 	
		      public void call(Session session, SessionState state, Exception exception) {
		    	  		    	  
		    	System.out.println("inside Call()");
		    	  
		        if (session.isOpened()) {
		        	UserInfo.setAccessToken(session.getAccessToken());
		          // make request to the /me API
		        	System.out.println("Session open.....");
		          Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
		        	 
		            // callback after Graph API response with user object
		            @Override
		            public void onCompleted(GraphUser user, Response response) {
		              if (user != null) {
		                TextView welcome = (TextView) findViewById(R.id.welcome);
		                welcome.setText("Hello " + user.getId() + "!");
		                UserInfo.setFb_id(user.getId());
		                
		                
		                
		                
		              }
		            }
		          });
		        }
		        else{
		        	//call(session, state, exception);
		        }
		      }
		    });	
	}
	
//get facebook user photos
		private void getFaceBookUserPhotos(){
			/*private static final String[] PERMISSIONS = { "user_photos" };
			Facebook facebook = new Facebook("652414728108978");
			
			if (!facebook.isSessionValid()) {
		        facebook.authorize(this, PERMISSIONS, new LoginDialogListener());
		    }*/
			/*if (!Session.getActiveSession().getPermissions().contains("publish_stream")) {
          	  Session.NewPermissionsRequest permissionRequest = // create a NewPermissionsRequest
          	  permissionRequest.setPermissions(PUBLISH_PERMISSION_LIST);
          	  Session.getActiveSession().requestNewPublishPermissions(permissionRequest);
          	}*/
          
			
			/*Session currentSession = Session.getActiveSession();
		    if (currentSession == null || currentSession.getState().isClosed()) {
		        Session session = Session.openActiveSession(this, true, fbStatusCallback); // PROBLEM: NO PERMISSIONS YET BUT CALLBACK IS EXECUTED ON OPEN
		        currentSession = session;
		    }
		    if (currentSession != null && !currentSession.isOpened()) {
		        OpenRequest openRequest = new OpenRequest(this).setCallback(fbStatusCallback); // HERE IT IS OKAY TO EXECUTE THE CALLBACK BECAUSE WE'VE GOT THE PERMISSIONS
		        if (openRequest != null) {
		            openRequest.setDefaultAudience(SessionDefaultAudience.FRIENDS);
		            openRequest.setPermissions(Arrays.asList("friends_hometown"));
		            openRequest.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
		            currentSession.openForRead(openRequest);
		        }
		    }
			*/
			
			String url = "https://graph.facebook.com/"+UserInfo.getFb_id()+"/albums?access_token="+UserInfo.getAccessToken();
			System.out.println("url:: "+ url);
			System.out.println("getFaceBookUserPhotos..");
			Request.executePostRequestAsync(Session.getActiveSession(), url ,null ,new Request.Callback() {
			//Request req = new Request(Session.getActiveSession(), url ,null, HttpMethod.POST ,new Request.Callback() {
				@Override
				public void onCompleted(Response response) {
					//System.out.println(request);
					// TODO Auto-generated method stub
					System.out.println(response.getGraphObject().getInnerJSONObject().toString());
					System.out.println("------------------");
					System.out.println(response.getGraphObject());
				}
			
		
		});
		}
	}