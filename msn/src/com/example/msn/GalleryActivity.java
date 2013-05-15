package com.example.msn;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import android.widget.TextView;
import android.widget.Toast;

public class GalleryActivity extends Activity {
	ImageView userEntryImage;
	//LinearLayout gallerylayout;
	ImageView userEntryImageView;
	HorizontalScrollView scrollView;
	HorizontalScrollView galleryHorizontalScrollViewRecent;
	ImageView allEntry;
	ImageView recentEntry;
	ArrayList<Entry> entryList;
	ArrayList<ImageView> allImageViews;
	LinearLayout topLinearLayoutAll;
	LinearLayout topLinearLayoutRecent;
	TextView userCaption;
	TextView voteCount;
	ImageView likeBtn;
	ImageView fshareBtn;
	TextView hiddenIndex;
	int i;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		System.out.println("executeMultipartPost()");
		setContentView(R.layout.activity_gallery);
		hiddenIndex = (TextView) findViewById(R.id.hiddenIndex);
		likeBtn = (ImageView) findViewById(R.id.likeBtn);
		fshareBtn = (ImageView) findViewById(R.id.fshareBtn);
		userCaption = (TextView) findViewById(R.id.userCaption);
		voteCount = (TextView) findViewById(R.id.voteCount);
		userEntryImage = (ImageView)findViewById(R.id.userEntryImage);
		scrollView = (HorizontalScrollView) findViewById(R.id.galleryHorizontalScrollView);
		galleryHorizontalScrollViewRecent= (HorizontalScrollView)findViewById(R.id.galleryHorizontalScrollViewRecent);
		likeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String hiddenString = hiddenIndex.getText().toString();
				System.out.println(hiddenString);
				Entry entry1 = null ;
				Iterator<Entry > ir = entryList.iterator();
				while(ir.hasNext()){
					if(ir.next().getId() == Integer.parseInt(hiddenString)){
						entry1 = ir.next();
						break;
					}
				}
				ConnectionHelper con = new ConnectionHelper();
				
				String res = con.updateVote(entry1.getId());
				
				if(res.equals("Thanks for your like.")) {
					int newVotes = (Integer.parseInt(voteCount.getText().toString()) + 1);
					voteCount.setText(((Integer)newVotes).toString());
					entry1.setVotes(newVotes);
				}
				System.out.println(res);
				Toast.makeText(getBaseContext(), res, Toast.LENGTH_SHORT).show();
			}
		});
		entryList = new ArrayList<Entry>();
		
		executeMultipartPost();
		allEntry = (ImageView) findViewById(R.id.allImages);
		
		recentEntry = (ImageView) findViewById(R.id.recentImages);
		allEntry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				galleryHorizontalScrollViewRecent.setVisibility(View.VISIBLE);
				scrollView.setVisibility(View.INVISIBLE);
				allEntry.setImageResource(R.drawable.recententries);
				recentEntry.setImageResource(R.drawable.allentries_click);
				//loadAllAndRecentImages("all");
				
			}
		});
		
		recentEntry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				galleryHorizontalScrollViewRecent.setVisibility(View.INVISIBLE);
				scrollView.setVisibility(View.VISIBLE);
				allEntry.setImageResource(R.drawable.recententries_click);
				recentEntry.setImageResource(R.drawable.allentries);
				//loadAllAndRecentImages("recent");
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
				
				topLinearLayoutAll = new LinearLayout(this);
				topLinearLayoutAll.setOrientation(LinearLayout.HORIZONTAL); 
				topLinearLayoutRecent= new LinearLayout(this);
				topLinearLayoutRecent.setOrientation(LinearLayout.HORIZONTAL); 
				
				while ((sResponse = reader.readLine()) != null) {
					json_response = json_response.append(sResponse);
				}
				 
				JSONArray jsonArray = new JSONArray(json_response.toString());
				
				userEntryImage.setTag("https://msncontest-fb.azurewebsites.net/uploads/" + jsonArray.getJSONObject(0).get("image_location").toString());
				
				HashMap<Entry, ImageView> firstImg = new HashMap<Entry, ImageView>();
				firstImg.put(null, userEntryImage);
				userCaption.setText(jsonArray.getJSONObject(0).getString("caption"));
				voteCount.setText(jsonArray.getJSONObject(0).getString("votes"));
            	new LoadImageFromInternetTask().execute(firstImg);
            	
				for (i = 0; i < jsonArray.length(); i++) {
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
						DateFormat formatter = new SimpleDateFormat("yyyy-dd-mm");
						
						entry.setDate((Date)formatter.parse(joObject.get("create_time").toString()));
						//entry.setName(joObject.get("fb_user_id").toString());
						
						entryList.add(entry);
						
						final String image_url;
						image_url = "https://msncontest-fb.azurewebsites.net/uploads/" + joObject.get("image_location").toString();
						imageView.setId(i);
						imageView.setTag(image_url);
						
						entryImageView.put(entry, imageView);
						new LoadImageFromInternetTask().execute(entryImageView);
						
						imageView.setOnClickListener(new OnClickListener() {
			                @Override
			                public void onClick(View v)
			                {
			                	HashMap<Entry, ImageView> hm = new HashMap<Entry, ImageView>();
			                	hm.put(null, userEntryImage);
			                    // TODO Auto-generated method stub
			                	userEntryImage.setTag(image_url);
								new LoadImageFromInternetTask().execute(hm);
								userCaption.setText(entry.getCaption());
								voteCount.setText(((Integer)entry.getVotes()).toString());
								hiddenIndex.setText(((Integer)(entry.getId())).toString());
								
								System.out.println("hiddenIndex Click ::: "+hiddenIndex.getText());
								
			                    //Log.e("Tag",""+imageView.getTag());
			                }
			            });
						if(i < 5){
							ImageView recentImg =new ImageView(this);
							recentImg.setTag(image_url);
							
							HashMap<Entry, ImageView> hm = new HashMap<Entry, ImageView>();
		                	hm.put(null, recentImg);
							new LoadImageFromInternetTask().execute(hm);
							recentImg.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									HashMap<Entry, ImageView> hm = new HashMap<Entry, ImageView>();
				                	hm.put(null, userEntryImage);
				                    // TODO Auto-generated method stub
				                	userEntryImage.setTag(image_url);
									new LoadImageFromInternetTask().execute(hm);
									userCaption.setText(entry.getCaption());
									voteCount.setText(((Integer)entry.getVotes()).toString());
									hiddenIndex.setText(((Integer)(entry.getId())).toString());
								}
							});
							
							
							/*ByteArrayOutputStream bos = new ByteArrayOutputStream();
							BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
							Bitmap bitmap = drawable.getBitmap();
							bitmap.compress(CompressFormat.JPEG, 50, bos);
							recentImg.setImageBitmap(bitmap);*/

							
							topLinearLayoutRecent.addView(recentImg);
							topLinearLayoutAll.addView(imageView);
							recentImg.getLayoutParams().width = 100;
							recentImg.getLayoutParams().height = 40;
						}
						else{
							topLinearLayoutAll.addView(imageView);
						}
						imageView.getLayoutParams().width = 100;
						imageView.getLayoutParams().height = 40;
						
						
					}
				scrollView.addView(topLinearLayoutAll);
				galleryHorizontalScrollViewRecent.addView(topLinearLayoutRecent);
			} catch (Exception e) {
				System.out.println("Exception");
				e.printStackTrace();
			}
		}

/*	private void loadAllAndRecentImages(String name){
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
		scrollView.removeAllViewsInLayout();
		topLinearLayout.removeAllViewsInLayout();
		if(name == "recent"){
			while(entry.hasNext()){
				if(count++ < 5) {
					final ImageView imageView = new ImageView (this);
					imageView.setImageBitmap(entry.next().getBmp());
					topLinearLayout.addView(imageView);
				}
			}
			
		}
		else{
			while(entry.hasNext()){
				final ImageView imageView = new ImageView (this);
				imageView.setImageBitmap(entry.next().getBmp());
				topLinearLayout.addView(imageView);
			}
			//scrollView.addView(topLinearLayout);
		}
		scrollView.addView(topLinearLayout);
	}*/
}
