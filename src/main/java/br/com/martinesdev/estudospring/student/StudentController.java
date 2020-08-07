package br.com.martinesdev.estudospring.student;

import java.awt.IllegalComponentStateException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

	private static final List<Student> STUDENTS = Arrays.asList(
			new Student(1, "James Hunt"),
			new Student(2, "Lucas MM"),
			new Student(3, "Maria") );

	@GetMapping("/{studentId}")
	public Student getStudent(
		@PathVariable("studentId") Integer studentId,
		Principal principal
	) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println( auth.getAuthorities() );
		System.out.println( auth.getName() );
				 
		return STUDENTS
				.stream()
				.filter( student -> studentId.equals(student.getStudentId()) )
				.findFirst()
				.orElseThrow(() -> 
					new IllegalComponentStateException("Student " + studentId + " dont exists"));
	}
	@GetMapping("")
	public List<Student> getStudents() {

		return STUDENTS;
	}
}
