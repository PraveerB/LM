package com.example.msn;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

public class LoadImageFromInternetTask extends AsyncTask<HashMap<Entry, ImageView>, Void, Bitmap> {

	static LruCache<String, Entry> mMemoryCache;
    ImageView imageView = null;
    Entry entry = null;
    static int cacheKey =0;
    @Override
    protected Bitmap doInBackground(HashMap<Entry, ImageView>... imageViews ) {
    	Iterator<java.util.Map.Entry<Entry, ImageView>> ir = imageViews[0].entrySet().iterator();
    	Map.Entry pairs = ir.next();
        this.imageView = (ImageView) pairs.getValue();
        this.entry = (Entry) pairs.getKey();
        return download_Image((String)imageView.getTag());
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
        //String url = (String) imageView.getTag();
        //System.out.println("result:: "+result);
        if(entry != null){
        	entry.setBmp(result);
        }
        //System.out.println("cacheKey"+ Integer.toString(cacheKey++));
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Entry>(cacheSize){
                @Override
                protected int sizeOf(String key, Entry entry) {
                    // The cache size will be measured in bytes rather than number of items.
                    return entry.getBmp().getByteCount();
                }
            };
        
        if(entry!= null){
        	addBitmapToMemoryCache(Integer.toString(cacheKey++), entry);
            //System.out.println("getAllBitmapFromMemCache:::::" + getAllBitmapFromMemCache().get(Integer.toString(entry.getId())).getId());
        }
        
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
			//System.out.println("Async task..........");
			bmp = BitmapFactory.decodeStream(ulrn.openConnection().getInputStream(), null, options);
			//System.out.println("bmp"+ bmp);
			//Log.e("Tag",""+imageView.getTag());
            if (null != bmp)
                return bmp;

            }
        catch(Exception e){}
        return bmp;
    }
    
//Save In Cache
    
    public void addBitmapToMemoryCache(String key, Entry entry) {
    	//System.out.println("Key ::"+ key);
    	//System.out.println("Entry ::"+ entry);
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, entry);
        }
    }

    public Entry getBitmapFromMemCache(String key) {
    	//System.out.println("mMemoryCache :: "+mMemoryCache.get(key));
        return mMemoryCache.get(key);
    }
    
    public LruCache<String , Entry> getAllBitmapFromMemCache() {
    	System.out.println("mMemoryCache :: "+mMemoryCache);
    	if(mMemoryCache != null){
    		return mMemoryCache;
    	}
    	else{
    		return null;
    	}
        
    }
    
}
