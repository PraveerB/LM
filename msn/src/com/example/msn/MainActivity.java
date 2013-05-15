package com.example.msn;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.ImageColumns;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity implements Runnable {
	
	ImageView participateBtn;
	ImageView testImageView;
	Thread thread1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        participateBtn = (ImageView) findViewById(R.id.participateBtn);
        testImageView = (ImageView) findViewById(R.id.testImageView);
        thread1 = new Thread();
        thread1.start();
        
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
    
    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
       } catch (IOException e) {
           //Log.e("Error getting bitmap", e);
       }
       return bm;
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("thread1");
		String image_url = "https://msncontest-fb.azurewebsites.net/uploads/1368185711IMG_0467.jpg";
        URL url;
		try 
		{
			url = new URL(image_url);
			
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Config.RGB_565;
			options.outWidth = 300;
			options.outHeight = 300;
			
			Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);	
			testImageView.setImageBitmap(bmp);
		} 
		catch (Exception e) 
		{	
			e.printStackTrace();
		}
	} 
}
