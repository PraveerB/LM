package com.example.msn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegistrationActivity extends Activity {
	ImageView submitBtn;
	EditText name;
	EditText email;
	EditText city;
	EditText contact;
	ProgressBar wait;
	Toast toast;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
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
		                //TextView welcome = (TextView) findViewById(R.id.welcome);
		                //welcome.setText("Hello " + user.getId() + "!");
		            	//System.out.println("inside not null");
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
		                ConnectionHelper con = new ConnectionHelper();
		                String res = con.saveMobileUserInfo();
		                System.out.println(res);
		                if(UserInfo.getFb_id() != null) {
		                	//String con.checkNew();
		                	if((!res.equals("new")) && UserInfo.getEmail() != null) {
		                		Intent uploadGalleryIntent = new Intent("com.example.msn.UPLOADGALLERY");
			                	startActivity(uploadGalleryIntent);
		                	}
		                }
		                else {
		                	Toast.makeText(getBaseContext(), "Something bad happened!!", Toast.LENGTH_SHORT);
		                	Intent participate = new Intent("com.example.msn.MAIN");
			            	startActivity(participate);
		                }
		                
		              }
		              else {
		            	  Toast.makeText(getBaseContext(), "Something bad happened!!", Toast.LENGTH_SHORT);
		            	  Intent participate = new Intent("com.example.msn.MAIN");
		            	  startActivity(participate);
		            	  
		              }
		            }
		          });
		        }
		      }
		    });	
		
		submitBtn = (ImageView) findViewById(R.id.submit1);
		name = (EditText) findViewById(R.id.name);
		city = (EditText) findViewById(R.id.city);
		email = (EditText) findViewById(R.id.email);
		contact = (EditText) findViewById(R.id.phone);
		submitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String uname = name.getText().toString();
				String uemail = email.getText().toString();
				String ucity = city.getText().toString();
				String ucontact = contact.getText().toString();
				//UserInfo userInfo = new UserInfo();
				System.out.println(uname.length());
				if(uname.length() == 0) {
					toast = Toast.makeText(getBaseContext(), "Please Enter Name!!", Toast.LENGTH_SHORT);
					toast.show();
				}
				else if(uemail.length() == 0) {
					toast = Toast.makeText(getBaseContext(), "Please Enter Email!!", Toast.LENGTH_SHORT);
					toast.show();
				}
				else if(ucity.length() == 0) {
					toast = Toast.makeText(getBaseContext(), "Please Enter City!!", Toast.LENGTH_SHORT);
					toast.show();
				}
				else if(ucontact.length() == 0) {
					toast = Toast.makeText(getBaseContext(), "Please Enter Mobile Number!!", Toast.LENGTH_SHORT);
					toast.show();
				}
				else if(!chkEmail(uemail)) {
					toast = Toast.makeText(getBaseContext(), "Please Enter Valid Email!!", Toast.LENGTH_SHORT);
					toast.show();
				}
				else if(ucontact.length() != 10) {
					toast = Toast.makeText(getBaseContext(), "Please Enter Valid 10 digit Mobile Number!!", Toast.LENGTH_SHORT);
					toast.show();
				}
				else {
					UserInfo.setName(uname);
					UserInfo.setEmail(uemail);
					System.out.println(UserInfo.getEmail());
					UserInfo.setCity(ucity);
					UserInfo.setContact(ucontact);
					Intent uploadIntent = new Intent("com.example.msn.UPLOADGALLERY");
					startActivity(uploadIntent);
				}
			}
		});
        }

	public boolean chkEmail(String uemail) {
		String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(uemail);
		if(m.find()) 
		 {
		  return true; }
		 else
		 {
		  return false;
		 }
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}
	

	@Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	  }

}
