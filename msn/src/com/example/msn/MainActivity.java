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
	ImageView gotoGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        participateBtn = (ImageView) findViewById(R.id.participateBtn);
        gotoGallery = (ImageView) findViewById(R.id.gotoGallery);
        gotoGallery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent gallery = new Intent("com.example.msn.GALLERY");
				startActivity(gallery);
			}
		});
        participateBtn.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
/*
<<<<<<< HEAD
                ConnectionHelper con = new ConnectionHelper();
                con.connectToFacebook(MainActivity.this);
                System.out.println(con.res);
                if(con.res.equals("new")) {
=======
                
>>>>>>> 5530488ed753d71ff20b8d7db05a8864c0623ad8
*/
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
