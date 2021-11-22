package ingress.fuzuli.gulmamadli.microservice;

import ingress.fuzuli.gulmamadli.microservice.entity.Course;
import ingress.fuzuli.gulmamadli.microservice.entity.Group;
import ingress.fuzuli.gulmamadli.microservice.entity.Student;
import ingress.fuzuli.gulmamadli.microservice.repository.CourseRepository;
import ingress.fuzuli.gulmamadli.microservice.repository.GroupRepository;
import ingress.fuzuli.gulmamadli.microservice.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class MicroServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Override
	public void run(String... args) throws Exception {

		var courses1 = Set.of(
				new Course("course-1"),
				new Course("course-2")
		);
		var courses2 = Set.of(
				new Course("course-3"),
				new Course("course-4")
		);
		var courses3 = Set.of(
				new Course("course-5"),
				new Course("course-6")
		);

		var groups = Set.of(
				new Group("group-1"),
				new Group("group-2"),
				new Group("group-3"),
				new Group("group-4"),
				new Group("group-5")
		);

		var students = Set.of(
				new Student("name-1", "surname-1", courses1, groups),
				new Student("name-2", "surname-2", courses2, groups),
				new Student("name-3", "surname-3", courses3, groups)
		);

		courses1.forEach(course -> course.setStudent(new ArrayList<>(students).get(0)));
		courses2.forEach(course -> course.setStudent(new ArrayList<>(students).get(1)));
		courses3.forEach(course -> course.setStudent(new ArrayList<>(students).get(2)));

		studentRepository.saveAll(students);

		//join fetch
//		var s1 = studentRepository.getAllStudentsWithJoinFetch();

		//native with custom repository
		var s2 = studentRepository.getAllStudentsNative();
		System.out.println("a");
	}
}
