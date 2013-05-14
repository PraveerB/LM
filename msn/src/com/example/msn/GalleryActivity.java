package com.example.msn;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GalleryActivity extends Activity {
	ImageView userEntryImage;
	LinearLayout gallerylayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gallerylayout = (LinearLayout) findViewById(R.id.galleryLayout);
		System.out.println("executeMultipartPost()");
		executeMultipartPost();
		setContentView(R.layout.activity_gallery);
		userEntryImage = (ImageView) findViewById(R.id.userEntryImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gallery, menu);
		return true;
	}

	public StringBuilder executeMultipartPost(){
		 StringBuilder json_response = new StringBuilder();
			try {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy); 
				
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost postRequest = new HttpPost("http://msncontest-fb.azurewebsites.net/index.php/site/getGalleryData");
				
				int timeoutConnection = 60000;
				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParameters,timeoutConnection);
				int timeoutSocket = 60000;
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
				HttpConnectionParams.setTcpNoDelay(httpParameters, true);
				HttpResponse response = httpClient.execute(postRequest);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
				String sResponse;
				while ((sResponse = reader.readLine()) != null) {
					System.out.println(sResponse);
					json_response = json_response.append(sResponse);
				}
				JSONArray jsonArray = new JSONArray(json_response.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject joObject = jsonArray.getJSONObject(i);
						String image_url = "https://msncontest-fb.azurewebsites.net/uploads/" + joObject.get("image_location").toString();
						//URL url = new URL(image_url);
						//URI uri = url.toURI();
						System.out.println(image_url);
						//userEntryImage.
						//System.out.println(getImageBitmap(image_url));
						//userEntryImage.setImageBitmap(BitmapFactory.decodeStream((InputStream)new URL(image_url).getContent()));
						//userEntryImage.setImageResource(R.id.participateBtn);
						break;
					}
			} catch (Exception e) {
				System.out.println("Exception");
				// handle exception here
				e.printStackTrace();
				// Log.e(e.getClass().getName(), e.getMessage());
			}
			return json_response;
		}
	
		private Bitmap getImageBitmap(String url) {
	        Bitmap bm = null;
	        try {
	            URL aURL = new URL(url);
	            URLConnection conn = aURL.openConnection();
	            conn.connect();
	            InputStream is = conn.getInputStream();
	            BufferedInputStream bis = new BufferedInputStream(is);
	            bm = BitmapFactory.decodeStream(bis);
	            bis.close();
	            is.close();
	       } catch (IOException e) {
	           //Log.e("Error getting bitmap", e);
	       }
	       return bm;
	    } 
	
}
