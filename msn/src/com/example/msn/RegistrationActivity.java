package com.example.msn;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegistrationActivity extends Activity {
	ImageView submitBtn;
	EditText name;
	EditText email;
	EditText city;
	EditText contact;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		submitBtn = (ImageView) findViewById(R.id.submit1);
		name = (EditText) findViewById(R.id.name);
		city = (EditText) findViewById(R.id.city);
		email = (EditText) findViewById(R.id.email);
		contact = (EditText) findViewById(R.id.phone);
		
		UserInfo.setName(name.getText().toString());
		UserInfo.setEmail(email.getText().toString());
		UserInfo.setCity(city.getText().toString());
		UserInfo.setContact(contact.getText().toString());
		
		submitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent uploadIntent = new Intent("com.example.msn.UPLOADGALLERY");
				startActivity(uploadIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

}
