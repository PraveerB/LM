package com.example.msn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegistrationActivity extends Activity {
	ImageView submitBtn;
	EditText name;
	EditText email;
	EditText city;
	EditText contact;
	Toast toast;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
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

}
