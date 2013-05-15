package com.example.msn;

public class UserInfo {
	private static String first_name;
	private static String middle_name;
	private static String last_name;
	private static String username;
	private static String name;
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		UserInfo.name = name;
	}
	private static String email;
	private static String city;
	private static String contact;
	private static String fb_id;
	private static String gender;
	public static String getGender() {
		return gender;
	}
	public static void setGender(String gender) {
		UserInfo.gender = gender;
	}
	private static byte[] uploadedImage;
	private static String accessToken;
	
	public static String getAccessToken() {
		return accessToken;
	}
	public static void setAccessToken(String accessToken) {
		UserInfo.accessToken = accessToken;
	}
	public static String getFirstName() {
		return first_name;
	}
	public static String getFb_id() {
		return fb_id;
	}
	public static void setFb_id(String fb_id) {
		UserInfo.fb_id = fb_id;
	}
	public static byte[] getUploadedImage() {
		return uploadedImage;
	}
	public static void setUploadedImage(byte[] uploadedImage) {
		UserInfo.uploadedImage = uploadedImage;
	}
	public static void setFirst_name(String first_name) {
		UserInfo.first_name = first_name;
	}
	public static String getMiddle_name() {
		return middle_name;
	}
	public static void setMiddle_name(String middle_name) {
		UserInfo.middle_name = middle_name;
	}
	public static String getLast_name() {
		return last_name;
	}
	public static void setLast_name(String last_name) {
		UserInfo.last_name = last_name;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		UserInfo.username = username;
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
	public static void displayData() {
		System.out.println(getFb_id());
		System.out.println(getUsername());
		System.out.println(getFirstName());
		System.out.println(getMiddle_name());
		System.out.println(getLast_name());
		System.out.println(getGender());
	}
}
