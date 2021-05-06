package com.beepermd.model;

public class AppointmentModel {

	private String fname = "";
	private String lname;
	private String gender;
	private String dob;
	private String cellphone;
	private String email;
	private static int payment_amount;

	public static int getPayment_amount() {
		return payment_amount;
	}

	public static void setPayment_amount(int payment_amount) {
		AppointmentModel.payment_amount = payment_amount;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
