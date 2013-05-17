package com.example.msn;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.facebook.Session;
import com.facebook.Request;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;

public class GalleryActivity extends Activity {
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
    private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
    private boolean pendingPublishReauthorization = false;
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
	ImageView forward;
	ImageView backward;
	boolean flag = false;
	int layoutRecentWidth;
	int layoutAllWidth;
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
		forward = (ImageView) findViewById(R.id.forward);
		backward = (ImageView) findViewById(R.id.backward);
		forward.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(flag) {

					layoutRecentWidth = topLinearLayoutRecent.getWidth();
					int currentPosX = galleryHorizontalScrollViewRecent.getScrollX();
					int currentPosY = galleryHorizontalScrollViewRecent.getScrollY();
					if(currentPosX < (layoutRecentWidth-40)) {
						galleryHorizontalScrollViewRecent.scrollTo((currentPosX+10), currentPosY);
					}
				} else {
					layoutAllWidth = topLinearLayoutAll.getWidth();
					int currentPosX = scrollView.getScrollX();
					int currentPosY = scrollView.getScrollY();
					if(currentPosX < (layoutAllWidth-40)) {
						scrollView.scrollTo((currentPosX+40), currentPosY);
					}
				}
			}
		});
		backward.setOnClickListener(new OnClickListener(
				) {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(flag) {
					int currentPosX = galleryHorizontalScrollViewRecent.getScrollX();
					int currentPosY = galleryHorizontalScrollViewRecent.getScrollY();
					if(currentPosX > 40) {
						galleryHorizontalScrollViewRecent.scrollTo((currentPosX-10), currentPosY);
					}
				} else {
					int currentPosX = scrollView.getScrollX();
					int currentPosY = scrollView.getScrollY();
					if(currentPosX > 40) {
						scrollView.scrollTo((currentPosX-40), currentPosY);
					}
				}
			}
		});
		likeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String hiddenString = (hiddenIndex.getText()).toString();
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
		fshareBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				publishStory(GalleryActivity.this);
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
				flag = true;
				
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
				flag = false;
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
				hiddenIndex.setText(((Integer)(jsonArray.getJSONObject(0).getInt("id"))).toString());
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
							
							topLinearLayoutRecent.addView(recentImg);
							topLinearLayoutAll.addView(imageView);
							recentImg.getLayoutParams().width = 120;
							recentImg.getLayoutParams().height = 40;
						}
						else{
							topLinearLayoutAll.addView(imageView);
						}
						imageView.getLayoutParams().width = 120;
						imageView.getLayoutParams().height = 40;
						
						
					}
				scrollView.addView(topLinearLayoutAll);
				galleryHorizontalScrollViewRecent.addView(topLinearLayoutRecent);
			} catch (Exception e) {
				System.out.println("Exception");
				e.printStackTrace();
			}
		}
	private void publishStory(final Activity activity) {
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
	                }
	                FacebookRequestError error = response.getError();
	                if (error != null) {
	                    Toast.makeText(activity,
	                         error.getErrorMessage(),
	                         Toast.LENGTH_SHORT).show();
	                    } else {
	                        Toast.makeText(activity, 
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
	
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}
}
