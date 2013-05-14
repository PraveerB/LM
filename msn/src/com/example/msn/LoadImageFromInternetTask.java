package com.example.msn;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class LoadImageFromInternetTask extends AsyncTask<ImageView, Void, Bitmap> {

    ImageView imageView = null;

    @Override
    protected Bitmap doInBackground(ImageView... imageViews ) {
        this.imageView = imageViews[0];
        return download_Image((String)imageView.getTag());
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }

    private Bitmap download_Image(String url) {

        Bitmap bmp =null;
        try{
            URL ulrn = new URL(url);
            /*HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is = con.getInputStream();
            bmp = BitmapFactory.decodeStream(is);*/
            
            BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Config.RGB_565;
			options.outWidth = 100;
			options.outHeight = 100 ;
			//options.inPreferredConfig = Config.RGB_565;
			System.out.println("Async task..........");
			bmp = BitmapFactory.decodeStream(ulrn.openConnection().getInputStream(), null, options);
			System.out.println("bmp"+ bmp);
			Log.e("Tag",""+imageView.getTag());
            if (null != bmp)
                return bmp;

            }
        catch(Exception e){}
        return bmp;
    }
}
