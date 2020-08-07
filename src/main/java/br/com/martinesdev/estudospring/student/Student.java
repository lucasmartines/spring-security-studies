package br.com.martinesdev.estudospring.student;

public class Student {
	private final String studentName;
	private final Integer studentId;

	public Student(Integer studentId, String studentName) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
	}
	
	public Integer getStudentId() {
		return studentId;
	}
	
	public String getStudentName() {
		return studentName;
	}

	@Override
	public String toString() {
		return "Student [studentName=" + studentName + ", studentId=" + studentId + "]";
	}
	
}
