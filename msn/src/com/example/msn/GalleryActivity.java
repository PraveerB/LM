package com.example.msn;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.view.Menu;

public class GalleryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("executeMultipartPost()");
		executeMultipartPost();
		setContentView(R.layout.activity_gallery);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gallery, menu);
		return true;
	}

	public StringBuilder executeMultipartPost(){
		 StringBuilder s = new StringBuilder();
			try {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy); 
				
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost postRequest = new HttpPost("http://192.168.1.143:3000/offers/test_gallery_android");
				
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
					s = s.append(sResponse);
					
				}
				System.out.println("Response: " + s);
			} catch (Exception e) {
				System.out.println("Exception");
				// handle exception here
				e.printStackTrace();
				// Log.e(e.getClass().getName(), e.getMessage());
			}
			return s;
		}
	
}
