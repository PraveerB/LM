package com.example.msn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity  {
	
	ImageView participateBtn;
	ImageView gallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gallery = (ImageView) findViewById(R.id.gotogGallery);
        gallery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent gallery = new Intent("com.example.msn.GALLERY");
				startActivity(gallery);
			}
		});
        participateBtn = (ImageView) findViewById(R.id.participateBtn);
        participateBtn.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
        		System.out.println("Participate Btn click");
    				Intent regIntent = new Intent("com.example.msn.REG");
    				startActivity(regIntent);
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
