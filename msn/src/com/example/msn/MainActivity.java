package com.example.msn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class MainActivity extends Activity  {
	
	ImageView participateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        participateBtn = (ImageView) findViewById(R.id.participateBtn);
        participateBtn.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

                ConnectionHelper con = new ConnectionHelper();
                con.connectToFacebook(MainActivity.this);
                System.out.println(con.res);
                if(con.res.equals("new")) {
    				Intent regIntent = new Intent("com.example.msn.REG");
    				startActivity(regIntent);
                }
                else {
                	Intent uploadGalleryIntent = new Intent("com.example.msn.UPLOADGALLERY");
    				startActivity(uploadGalleryIntent);
                }
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
