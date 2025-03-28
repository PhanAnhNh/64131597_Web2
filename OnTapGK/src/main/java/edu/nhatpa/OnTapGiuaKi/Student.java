package edu.nhatpa.OnTapGiuaKi;

public class Student {
	private static int count = 1;
    private int stt;
    private String name;
    private String studentId;
    private double score;

    public Student(String name, String studentId, double score) {
        this.stt = count++;
        this.name = name;
        this.studentId = studentId;
        this.score = score;
    }

    public int getStt() {
        return stt;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public double getScore() {
        return score;
    }
    
    public void setName(String name) {
		this.name = name;
	}
    
    public void setScore(double score) {
		this.score = score;
	}
}
