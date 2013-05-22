package com.example.msn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity  {
	
	ImageView participateBtn;
	ImageView gallery;
	RelativeLayout popUpTNC;
	ImageView closePopUpTNC;
	boolean isParticipate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        gallery = (ImageView) findViewById(R.id.gotogGallery);
        
        popUpTNC = (RelativeLayout)findViewById(R.id.popUpTNC);
        closePopUpTNC = (ImageView)findViewById(R.id.closeTNC);
        closePopUpTNC.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				popUpTNC.setVisibility(View.INVISIBLE);
				if(isParticipate){
					ConnectionHelper con = new ConnectionHelper();
	        		if(con.isNetworkConnected(getBaseContext())){
	        			Intent regIntent = new Intent("com.example.msn.REG");
	    				startActivity(regIntent);
	        		}
	        		else{
	        			Toast.makeText(getBaseContext(), "To Perticipate you have to connect to internet.!!", Toast.LENGTH_SHORT).show();
	        		}
					
				}
				else{
					Intent gallery = new Intent("com.example.msn.GALLERY");
					startActivity(gallery);
				}
				
			}
		});
        
        
        gallery.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isParticipate = false;
				popUpTNC.setVisibility(View.VISIBLE);
				
			}
		});
        participateBtn = (ImageView) findViewById(R.id.participateBtn);
        participateBtn.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
        		isParticipate = true;
        		popUpTNC.setVisibility(View.VISIBLE);
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
