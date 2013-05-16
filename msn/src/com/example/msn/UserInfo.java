package com.example.msn;

public class UserInfo {

	private static String firstName;
	private static String middleName;
	private static String lastName;
	private static String username;
	private static String name;
	private static String email;
	private static String city;
	private static String contact;
	private static String fb_id;
	private static String gender;
	private static byte[] uploadedImage;
	private static String accessToken;
	private static String caption;
	
	
	public static String getCaption() {
		return caption;
	}



	public static void setCaption(String caption) {
		UserInfo.caption = caption;
	}



	public static String getFirstName() {
		return firstName;
	}



	public static void setFirstName(String firstName) {
		UserInfo.firstName = firstName;
	}



	public static String getMiddleName() {
		return middleName;
	}



	public static void setMiddleName(String middleName) {
		UserInfo.middleName = middleName;
	}



	public static String getLastName() {
		return lastName;
	}



	public static void setLastName(String lastName) {
		UserInfo.lastName = lastName;
	}



	public static String getUsername() {
		return username;
	}



	public static void setUsername(String username) {
		UserInfo.username = username;
	}



	public static String getName() {
		return name;
	}



	public static void setName(String name) {
		UserInfo.name = name;
	}



	public static String getEmail() {
		
		return email;
	}



	public static void setEmail(String email) {
		//System.out.println(email);
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



	public static String getFb_id() {
		return fb_id;
	}



	public static void setFb_id(String fb_id) {
		UserInfo.fb_id = fb_id;
	}



	public static String getGender() {
		return gender;
	}



	public static void setGender(String gender) {
		UserInfo.gender = gender;
	}



	public static byte[] getUploadedImage() {
		return uploadedImage;
	}



	public static void setUploadedImage(byte[] uploadedImage) {
		UserInfo.uploadedImage = uploadedImage;
	}



	public static String getAccessToken() {
		return accessToken;
	}



	public static void setAccessToken(String accessToken) {
		UserInfo.accessToken = accessToken;
	}
	

	
	public static void displayData() {
		System.out.println(getFb_id());
		System.out.println(getUsername());
		System.out.println(getFirstName());
		//System.out.println(getEmail());
		System.out.println(getLastName());
		System.out.println(getGender());
	}
}
