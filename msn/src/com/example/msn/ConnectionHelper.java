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

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

class ConnectionHelper {
	String res = "test";
	void connectToFacebook(Activity activity) {
		Session.openActiveSession(activity, true, new Session.StatusCallback() {
			  
		      // callback when session changes state
		      @Override 	
		      public void call(Session session, SessionState state, Exception exception) {
		        if (session.isOpened()) {
		        	System.out.println("session Opened" + session.isOpened());
		          // make request to the /me API
		          Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
		        	 
		            // callback after Graph API response with user object
		            @Override
		            public void onCompleted(GraphUser user, Response response) {
		            	//System.out.println("session Opened");
		              if (user != null) {
		                //TextView welcome = (TextView) findViewById(R.id.welcome);
		                //welcome.setText("Hello " + user.getId() + "!");
		            	System.out.println("inside not null");
		                UserInfo.setFb_id(user.getId());
		                UserInfo.setFirstName(user.getFirstName());
		                if(user.getMiddleName() != null)
		                	UserInfo.setMiddleName(user.getMiddleName());
		                if(user.getLastName() != null)
		                	UserInfo.setLastName(user.getLastName());
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
		      }
		    });	
    }
	
	
	
	String saveMobileUserInfo() {
		StringBuilder s = new StringBuilder("test1");
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy); 
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://msncontest-fb.azurewebsites.net/index.php/site/saveMobileUserInfo");
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			
//Parameters to send to server
			reqEntity.addPart("first_name", new StringBody(UserInfo.getFirstName()));
			reqEntity.addPart("fb_id", new StringBody(UserInfo.getFb_id()));
			reqEntity.addPart("username", new StringBody(UserInfo.getUsername()));
			//reqEntity.addPart("middle_name", new StringBody(UserInfo.getMiddle_name()));
			reqEntity.addPart("last_name", new StringBody(UserInfo.getLastName()));
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
 
			s = new StringBuilder();
 
			while ((sResponse = reader.readLine()) != null) {
				s = s.append(sResponse);
			}
			//System.out.println("Response: " + s);
		} 
		catch (Exception e) {
			System.out.println("Exception");
			// handle exception here
			e.printStackTrace();
			// Log.e(e.getClass().getName(), e.getMessage());
		}
		return s.toString();
	}
	
	String updateVote(int entryId) {
		StringBuilder s = new StringBuilder("test1");
		try {
			if (UserInfo.getFb_id() == null) {
				return "You Need to Login.";
			}
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy); 
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://msncontest-fb.azurewebsites.net/index.php/site/updateMobileVote");
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			
//Parameters to send to server
			reqEntity.addPart("entry_id", new StringBody(((Integer)(entryId)).toString()));
			reqEntity.addPart("fb_id", new StringBody(UserInfo.getFb_id()));
			
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
 
			s = new StringBuilder();
 
			while ((sResponse = reader.readLine()) != null) {
				s = s.append(sResponse);
			}
		} 
		catch (Exception e) {
			System.out.println("Exception");
			// handle exception here
			e.printStackTrace();
			// Log.e(e.getClass().getName(), e.getMessage());
		}
		return s.toString();
	}
	
	public void executeMultipartPost(){
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy); 
			
			byte[] data = UserInfo.getUploadedImage();
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://msncontest-fb.azurewebsites.net/index.php/site/getMobileData");
			String fileName = "File.png";
			ByteArrayBody bab = new ByteArrayBody(data, fileName);
			
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			
//Parameters to send to server
			reqEntity.addPart("file", bab);
			if(UserInfo.getFb_id() != null)
				reqEntity.addPart("uid", new StringBody(UserInfo.getFb_id()));
			if(UserInfo.getCaption() != null)
				reqEntity.addPart("caption", new StringBody(UserInfo.getCaption()));
			if(UserInfo.getEmail() != null)
				reqEntity.addPart("email", new StringBody(UserInfo.getEmail()));
			if(UserInfo.getContact() != null)
				reqEntity.addPart("mobile", new StringBody(UserInfo.getContact()));
			if(UserInfo.getCity() != null)
				reqEntity.addPart("city", new StringBody(UserInfo.getCity()));
			if(UserInfo.getName() != null)
				reqEntity.addPart("name", new StringBody(UserInfo.getName()));
			
			
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
		} catch (Exception e) {
			System.out.println("Exception");
			e.printStackTrace();
			// Log.e(e.getClass().getName(), e.getMessage());
		}
 
	}
	
/*	
	private void publishStory() {
	    Session session = Session.getActiveSession();

	    if (session != null){

	        // Check for publish permissions    
	        List<String> permissions = session.getPermissions();
	        if (!isSubsetOf(PERMISSIONS, permissions)) {
	            pendingPublishReauthorization = true;
	            Session.NewPermissionsRequest newPermissionsRequest = new Session
	                    .NewPermissionsRequest(this, PERMISSIONS);
	        session.requestNewPublishPermissions(newPermissionsRequest);
	            return;
	        }

	        Bundle postParams = new Bundle();
	        postParams.putString("name", "Facebook SDK for Android");
	        postParams.putString("caption", "Build great social apps and get more installs.");
	        postParams.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
	        postParams.putString("link", "https://developers.facebook.com/android");
	        postParams.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

	        Request.Callback callback= new Request.Callback() {
	            public void onCompleted(Response response) {
	                JSONObject graphResponse = response
	                                           .getGraphObject()
	                                           .getInnerJSONObject();
	                String postId = null;
	                try {
	                    postId = graphResponse.getString("id");
	                } catch (JSONException e) {
	                    Log.i(TAG,
	                        "JSON error "+ e.getMessage());
	                }
	                FacebookRequestError error = response.getError();
	                if (error != null) {
	                    Toast.makeText(getActivity()
	                         .getApplicationContext(),
	                         error.getErrorMessage(),
	                         Toast.LENGTH_SHORT).show();
	                    } else {
	                        Toast.makeText(getActivity()
	                             .getApplicationContext(), 
	                             postId,
	                             Toast.LENGTH_LONG).show();
	                }
	            }
	        };

	        Request request = new Request(session, "me/feed", postParams, 
	                              HttpMethod.POST, callback);

	        RequestAsyncTask task = new RequestAsyncTask(request);
	        task.execute();
	    }

	}
	*/
	
	
}
