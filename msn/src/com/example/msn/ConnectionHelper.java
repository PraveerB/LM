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
import android.os.StrictMode;

import com.facebook.*;
import com.facebook.model.*;

class ConnectionHelper {
	String res;
	void connectToFacebook(Activity activity) {
		Session.openActiveSession(activity, true, new Session.StatusCallback() {
			  
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
		                //TextView welcome = (TextView) findViewById(R.id.welcome);
		                //welcome.setText("Hello " + user.getId() + "!");
		            	System.out.println("inside not null");
		                UserInfo.setFb_id(user.getId());
		                UserInfo.setFirst_name(user.getFirstName());
		                if(user.getMiddleName() != null)
		                	UserInfo.setMiddle_name(user.getMiddleName());
		                if(user.getLastName() != null)
		                	UserInfo.setLast_name(user.getLastName());
		                if(user.getProperty("gender") != null)
		                	UserInfo.setGender((String) user.getProperty("gender"));
		                if(user.getUsername() != null)
		                	UserInfo.setUsername(user.getUsername());
		                else
		                UserInfo.setUsername(user.getId());	
		                UserInfo.displayData();
		                //welcome.setText(user.toString());
		                res = saveMobileUserInfo();
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
	String saveMobileUserInfo() {
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy); 
			/*ByteArrayOutputStream bos = new ByteArrayOutputStream();
			BitmapDrawable drawable = (BitmapDrawable) uploadedImage.getDrawable();
			Bitmap bitmap = drawable.getBitmap();
			bitmap.compress(CompressFormat.JPEG, 50, bos);
			byte[] data = bos.toByteArray();*/
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://msncontest-fb.azurewebsites.net/index.php/site/saveMobileUserInfo");
			//String fileName = String.format("File_%d.png",new Date().getTime());
			
			// File file= new File("/mnt/sdcard/forest.png");
			// FileBody bin = new FileBody(file);
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			
//Parameters to send to server
			reqEntity.addPart("first_name", new StringBody(UserInfo.getFirstName()));
			reqEntity.addPart("fb_id", new StringBody(UserInfo.getFb_id()));
			reqEntity.addPart("username", new StringBody(UserInfo.getUsername()));
			//reqEntity.addPart("middle_name", new StringBody(UserInfo.getMiddle_name()));
			reqEntity.addPart("last_name", new StringBody(UserInfo.getLast_name()));
			reqEntity.addPart("gender", new StringBody(UserInfo.getGender()));
			//reqEntity.addPart("uid", new StringBody(UserInfo.getFb_id()));
			
			System.out.println("ReqEntity: " + reqEntity);
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
			return s.toString();
 
		} catch (Exception e) {
			System.out.println("Exception");
			// handle exception here
			e.printStackTrace();
			// Log.e(e.getClass().getName(), e.getMessage());
		}
		return null;
	}
	
	
}
