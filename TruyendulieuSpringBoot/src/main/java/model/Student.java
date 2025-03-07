package model;

public class Student {
	private String mssv;
	private String Hoten;
	private String gioitinh;
	private int namsinh;
	
	public Student(String mssv, String gioitinh, int namsinh, String Hoten) {
		// TODO Auto-generated constructor stub
		this.mssv = mssv;
		this.Hoten = Hoten;
		this.gioitinh = gioitinh;
		this.namsinh = namsinh;
	}
	
	public String getGioitinh() {
		return gioitinh;
	}
	public void setGioitinh(String gioitinh) {
		this.gioitinh = gioitinh;
	}
	
	public String getHoten() {
		return Hoten;
	}
	
	public void setHoten(String hoten) {
		this.Hoten = hoten;
	}
	
	public String getMssv() {
		return mssv;
	}
	
	public void setMssv(String mssv) {
		this.mssv = mssv;
	}
	
	public int getNamsinh() {
		return namsinh;
	}
	
	public void setNamsinh(int namsinh) {
		this.namsinh = namsinh;
	}
}
