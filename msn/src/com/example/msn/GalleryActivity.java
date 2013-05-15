package com.example.msn;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GalleryActivity extends Activity {
	ImageView userEntryImage;
	LinearLayout gallerylayout;
	ImageView userEntryImageView;
	HorizontalScrollView scrollView;
	ArrayList<Entry> entryList;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		System.out.println("executeMultipartPost()");
		setContentView(R.layout.activity_gallery);
		userEntryImage = (ImageView)findViewById(R.id.userEntryImage);
		scrollView = (HorizontalScrollView) findViewById(R.id.galleryHorizontalScrollView);
		//userEntryImage.setBackgroundColor(10);
		entryList = new ArrayList<Entry>();
		executeMultipartPost();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gallery, menu);
		return true;
	}

	public void executeMultipartPost(){
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
					//System.out.println(sResponse);
					json_response = json_response.append(sResponse);
				}
				 LinearLayout topLinearLayout = new LinearLayout(this);
				 topLinearLayout.setOrientation(LinearLayout.HORIZONTAL); 
				JSONArray jsonArray = new JSONArray(json_response.toString());
				
				//userEntryImage.setTag("https://apps51.likemyworld.com/ChaddiBuddy/app/assets/images/btn-gallery2.png");
				//new LoadImageFromInternetTask().execute(userEntryImage);
				
				for (int i = 0; i < jsonArray.length(); i++) {
					
						//URL url;
						final ImageView imageView = new ImageView (this);
			            //imageView.setTag(i);
						JSONObject joObject = jsonArray.getJSONObject(i);
						final Entry entry = new Entry();

						/*
						String image_url = "https://msncontest-fb.azurewebsites.net/uploads/" + joObject.get("image_location").toString();
						//URL url = new URL(image_url);
						//URI uri = url.toURI();
						System.out.println(image_url);
						//userEntryImage.
						//System.out.println(getImageBitmap(image_url));
						//userEntryImage.setImageBitmap(BitmapFactory.decodeStream((InputStream)new URL(image_url).getContent()));
						//userEntryImage.setImageResource(R.id.participateBtn);
						break;*/

						//Entry entry = new Entry();
						
						entry.setId(Integer.parseInt(joObject.get("id").toString()));
						entry.setVotes(Integer.parseInt(joObject.get("votes").toString()));
						entry.setCaption(joObject.get("caption").toString());
						entry.setImage_location(joObject.get("image_location").toString());
						//entry.setName(joObject.get("fb_user_id").toString());
						entryList.add(entry);
						final String image_url;
						//String image_url = "https://msncontest-fb.azurewebsites.net/uploads/" + joObject.get("image_location").toString();
						if(i % 2 == 0){
							image_url  = "https://apps51.likemyworld.com/ChaddiBuddy/app/assets/images/btn-gallery2.png";
						}
						else{
							image_url  = "https://apps51.likemyworld.com/ChaddiBuddy/app/assets/images/btn-home.png";
						}
						
						imageView.setId(i);
						imageView.setTag(image_url);
						
						
						new LoadImageFromInternetTask().execute(imageView);
						imageView.setOnClickListener(new OnClickListener()
			            {

			                @Override
			                public void onClick(View v)
			                {
			                    // TODO Auto-generated method stub
			                	System.out.println("Onclick---------");
			                	//userEntryImage.setTag(entry.getImage_location());
			                	userEntryImage.setTag(image_url);
								new LoadImageFromInternetTask().execute(userEntryImage);
			                	//new LoadImageFromInternetTask().execute(userEntryImage);
			                	//userEntryImage.
			                    Log.e("Tag",""+imageView.getTag());
			                }
			            });
						//userEntryImage.setTag(jsonArray.getJSONObject(0));
						//new LoadImageFromInternetTask().execute(userEntryImage);
						topLinearLayout.addView(imageView);
					}
				//userEntryImage.setTag(jsonArray.getJSONObject(0));
				scrollView.addView(topLinearLayout);
				System.out.println("Out.........");
				//new LoadImageFromInternetTask().execute(userEntryImage);
			} catch (Exception e) {
				System.out.println("Exception");
				e.printStackTrace();
			}
			//return json_response;
		}
}
