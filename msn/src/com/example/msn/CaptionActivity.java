package com.example.msn;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class CaptionActivity extends Activity {
	ImageView changeBtn;
	ImageView submitBtn;
	EditText caption;
	String captionText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_caption);
		changeBtn = (ImageView) findViewById(R.id.changeBtn);
		submitBtn = (ImageView) findViewById(R.id.captionSubmit);
		caption = (EditText) findViewById(R.id.caption);
		captionText = caption.getText().toString();
		changeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent uploadGalleryIntent = new Intent("com.example.msn.UPLOADGALLERY");
				startActivity(uploadGalleryIntent);
			}
		});
		submitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// save data
				Intent doneIntent = new Intent("com.example.msn.DONE");
				startActivity(doneIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.caption, menu);
		return true;
	}

}
