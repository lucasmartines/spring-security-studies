package br.com.martinesdev.estudospring.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/students")
public class StudemtManagmentController 
{

	private static final List<Student> STUDENTS = Arrays.asList(
			new Student(1, "James Hunt"),
			new Student(2, "Lucas MM"),
			new Student(3, "Maria") );
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
	public List<Student> getAllStudents(){
		return STUDENTS;
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('student:write')")
	public void registerNewStudent(@RequestBody Student student) {
		System.out.println("register");
		System.out.println( student.toString() );
	}
	
	@DeleteMapping("{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void deleteStudent(@PathVariable("studentId")Integer studentId) {
		System.out.println("delete");
		System.out.println( studentId );
	}
	
	@PutMapping("{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void updateStudent(@PathVariable("studentId")Integer studentId, @RequestBody Student student) {
		System.out.println("update");
		System.out.println( String.format(" %s %s", student.toString() , studentId.toString() ));
	}
}
