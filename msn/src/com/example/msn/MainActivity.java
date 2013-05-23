package com.example.msn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

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
	static UserInfo userInfo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.msn", 
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        */
        setContentView(R.layout.activity_main);
        //Toast.makeText(getBaseContext(), "Testing.........", Toast.LENGTH_SHORT).show();
        FileInputStream fis;
		try {
			fis = getBaseContext().openFileInput("userInfo");
			//System.out.println("File null::: "+fis == null);
			if(fis != null){
				ObjectInputStream is = new ObjectInputStream(fis);
		    	userInfo =  (UserInfo) is.readObject();
		    	//userInfo.setFb_id(null);
		    	System.out.println("Saved email ::: "+userInfo.getEmail());
			}
			else{
				System.out.println("File not found...");
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("FileNotFoundException ... ");
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("StreamCorruptedException  ... ");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException  ... ");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("ClassNotFoundException Exception ... ");
			e.printStackTrace();
		}
    	
        
        
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