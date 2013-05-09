package com.example.msn;

import java.sql.Blob;

import android.graphics.Bitmap;

public class UserInfo {
	private static String name;
	private static String email;
	private static String city;
	private static String contact;
	private static String fb_id;
	private static Bitmap uploadedImage;
	public static String getName() {
		return name;
	}
	public static String getFb_id() {
		return fb_id;
	}
	public static void setFb_id(String fb_id) {
		UserInfo.fb_id = fb_id;
	}
	public static Bitmap getUploadedImage() {
		return uploadedImage;
	}
	public static void setUploadedImage(Bitmap uploadedImage) {
		UserInfo.uploadedImage = uploadedImage;
	}
	public static void setName(String name) {
		UserInfo.name = name;
	}
	public static String getEmail() {
		return email;
	}
	public static void setEmail(String email) {
		UserInfo.email = email;
	}
	public static String getCity() {
		return city;
	}
	public static void setCity(String city) {
		UserInfo.city = city;
	}
	public static String getContact() {
		return contact;
	}
	public static void setContact(String contact) {
		UserInfo.contact = contact;
	}
	
	
}
