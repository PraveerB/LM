package com.example.msn;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

public class UserInfo implements Serializable {

	private String firstName;
	private String middleName;
	private String lastName;
	private String username;
	private String name;
	private String email;
	private String city;
	private String contact;
	private String fb_id;
	private String gender;
	private byte[] uploadedImage;
	private String accessToken;
	private String caption;
	
	
	

	
	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getCity() {
		return city;
	}

	public String getContact() {
		return contact;
	}

	public String getFb_id() {
		return fb_id;
	}

	public String getGender() {
		return gender;
	}

	public byte[] getUploadedImage() {
		return uploadedImage;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getCaption() {
		return caption;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setFb_id(String fb_id) {
		this.fb_id = fb_id;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setUploadedImage(byte[] uploadedImage) {
		this.uploadedImage = uploadedImage;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void displayData() {
		System.out.println(this.getFb_id());
		System.out.println(this.getUsername());
		System.out.println(this.getFirstName());
		//System.out.println(getEmail());
		System.out.println(this.getLastName());
		System.out.println(this.getGender());
	}	
	
	public void serialize(Context context,  String fileNaqme)
	{
	   FileOutputStream str = null;
	   ObjectOutputStream objStr = null;
	   try {
	     str = context.openFileOutput(fileNaqme, Context.MODE_PRIVATE);
	     objStr = new ObjectOutputStream(str);

	     objStr.writeObject(this);

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