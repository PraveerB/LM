package com.example.msn;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;

public class Entry implements Serializable {
	
	int id;
	String image_location;
	byte[] bmp;
	String caption;
	int votes;
	Date date;

	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public byte[] getBmp() {
		return bmp;
	}
	public void setBmp(byte[] bmp) {
		this.bmp = bmp;
	}
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImage_location() {
		return image_location;
	}
	public void setImage_location(String image_location) {
		this.image_location = image_location;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	
	public void serialize(Context context,  String fileNaqme , ArrayList<Entry> entries)
	{
	   FileOutputStream str = null;
	   ObjectOutputStream objStr = null;
	   try {
	     str = context.openFileOutput(fileNaqme, Context.MODE_PRIVATE);
	     objStr = new ObjectOutputStream(str);

	     objStr.writeObject(entries);

	    } catch (FileNotFoundException e)
	    {
	        e.printStackTrace();
	    } catch (IOException e)
	    {
	        e.printStackTrace();
	    } finally
	    {
	        try
	        {
	            if (objStr != null) objStr.close();
	        } catch (IOException e) {}
	        try
	        {
	           if (str != null) str.close();
	        } catch (IOException e) {}
	    }
	}	
}
