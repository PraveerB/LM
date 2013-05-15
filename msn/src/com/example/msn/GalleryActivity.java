package com.example.msn;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

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
	ImageView allEntry;
	ImageView recentEntry;
	ArrayList<Entry> entryList;
	ArrayList<ImageView> allImageViews;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		System.out.println("executeMultipartPost()");
		setContentView(R.layout.activity_gallery);
		userEntryImage = (ImageView)findViewById(R.id.userEntryImage);
		scrollView = (HorizontalScrollView) findViewById(R.id.galleryHorizontalScrollView);
		entryList = new ArrayList<Entry>();
		executeMultipartPost();
		allEntry = (ImageView) findViewById(R.id.allImages);
		recentEntry = (ImageView) findViewById(R.id.recentImages);
		allEntry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				allEntry.setImageResource(R.drawable.allentries_click);
				recentEntry.setImageResource(R.drawable.recententries);
				loadAllAndRecentImages("all");
			}
		});
		
		recentEntry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				allEntry.setImageResource(R.drawable.allentries);
				recentEntry.setImageResource(R.drawable.recententries_click);
				loadAllAndRecentImages("recent");
			}
		});
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
				HttpPost postRequest = new HttpPost("https://msncontest-fb.azurewebsites.net/index.php/site/getGalleryData");
				
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
					json_response = json_response.append(sResponse);
				}
				 LinearLayout topLinearLayout = new LinearLayout(this);
				 topLinearLayout.setOrientation(LinearLayout.HORIZONTAL); 
				JSONArray jsonArray = new JSONArray(json_response.toString());
				
				userEntryImage.setTag("https://msncontest-fb.azurewebsites.net/uploads/" + jsonArray.getJSONObject(0).get("image_location").toString());
				
				HashMap<Entry, ImageView> firstImg = new HashMap<Entry, ImageView>();
				firstImg.put(null, userEntryImage);
				
            	new LoadImageFromInternetTask().execute(firstImg);
            	
				
				for (int i = 0; i < jsonArray.length(); i++) {
						final ImageView imageView = new ImageView (this);
						HashMap<Entry , ImageView> entryImageView = new HashMap<Entry, ImageView>();
						imageView.setId(i);
						JSONObject joObject = jsonArray.getJSONObject(i);
						final Entry entry = new Entry();

						entry.setId(Integer.parseInt(joObject.get("id").toString()));
						entry.setVotes(Integer.parseInt(joObject.get("votes").toString()));
						entry.setCaption(joObject.get("caption").toString());
						entry.setImage_location(joObject.get("image_location").toString());
						//Date date;
						DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
						
						entry.setDate((Date)formatter.parse(joObject.get("create_time").toString()));
						//entry.setName(joObject.get("fb_user_id").toString());
						
						entryList.add(entry);
						
						final String image_url;
						image_url = "https://msncontest-fb.azurewebsites.net/uploads/" + joObject.get("image_location").toString();
						imageView.setId(i);
						imageView.setTag(image_url);
						
						entryImageView.put(entry, imageView);
						new LoadImageFromInternetTask().execute(entryImageView);
						
						imageView.setOnClickListener(new OnClickListener()
			            {

			                @Override
			                public void onClick(View v)
			                {
			                	HashMap<Entry, ImageView> hm = new HashMap<Entry, ImageView>();
			                	hm.put(null, userEntryImage);
			                    // TODO Auto-generated method stub
			                	userEntryImage.setTag(image_url);
								new LoadImageFromInternetTask().execute(hm);
			                    //Log.e("Tag",""+imageView.getTag());
			                }
			            });
						
						topLinearLayout.addView(imageView);
					}
				scrollView.addView(topLinearLayout);
			} catch (Exception e) {
				System.out.println("Exception");
				e.printStackTrace();
			}
		}

	private void loadAllAndRecentImages(String name){
		Collections.sort(entryList, new Comparator<Entry>() {
			   public int compare(Entry o1, Entry o2) {
				   
			      Date a = o1.getDate();
			      Date b = o2.getDate();
			     if (a.after(b)) 
			        return -1;
			      else if (a.equals(b)) // it's equals
			         return 0;
			      else
			         return 1;
			   }
			});
		int count = 0;
		Iterator<Entry> entry = entryList.iterator();
		while(entry.hasNext()){
			if(name == "recent"){
				if(count++ < 10){
					System.out.println(entry.next().getVotes());
				}
				
			}
			else{
				System.out.println(entry.next().getId());
			}
			
		}
	}
}
