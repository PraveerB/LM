package com.example.msn;

import com.facebook.*;
import com.facebook.model.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity  {
	
	ImageView participateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        participateBtn = (ImageView) findViewById(R.id.participateBtn);
        connectToFacebook();
        participateBtn.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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
    
    private void connectToFacebook(){
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
		                UserInfo.setFb_id(user.getId());
		                System.out.println("UserInfo ::: "+UserInfo.getFb_id());
		                //welcome.setText(user.toString());
		              }
		            }
		          });
		        }
		        else{
		        	//call(session, state, exception);
		        }
		      }
		    });	

    }
}
