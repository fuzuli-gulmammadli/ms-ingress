package ingress.fuzuli.gulmamadli.microservice;

import ingress.fuzuli.gulmamadli.microservice.entity.BankAccount;
import ingress.fuzuli.gulmamadli.microservice.repository.BankAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@SpringBootApplication
public class MicroServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceApplication.class, args);
	}

	@Autowired
	BankAccountRepository bankAccountRepository;

	@Autowired
	EntityManagerFactory entityManagerFactory;

	public void transferWithAnnotation() {
		transferWithAnnotation(20.0);
	}

	public void customTransactionHandler() {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			em.getTransaction().begin();
			transfer(10.0, em);
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			if(em.getTransaction() != null && em.getTransaction().isActive()) {
				em.getTransaction().commit();
			}
		}
	}

	public void transfer(Double amount, EntityManager em) throws Exception {
		BankAccount source = em.find(BankAccount.class, 1l);
		BankAccount target = em.find(BankAccount.class, 2l);

		source.setBalance(source.getBalance() - amount);
		if (true) {
			throw new RuntimeException();
		}
		target.setBalance(target.getBalance() + amount);
	}

	public void transferWithAnnotation(Double amount) {
		BankAccount source = bankAccountRepository.findById(1L).get();
		BankAccount target = bankAccountRepository.findById(2L).get();
		source.setBalance(source.getBalance() - amount);
		target.setBalance(target.getBalance() + amount);

		bankAccountRepository.save(source);
		bankAccountRepository.save(target);
		System.out.println("After save source");
		throw new RuntimeException();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		customTransactionHandler();
	}

//
//	@Autowired
//	private StudentRepository studentRepository;
//
//	@Autowired
//	private GroupRepository groupRepository;
//
//	@Autowired
//	private CourseRepository courseRepository;
//
//	@Override
//	public void run(String... args) throws Exception {
//
//		var courses1 = Set.of(
//				new Course("course-1"),
//				new Course("course-2")
//		);
//		var courses2 = Set.of(
//				new Course("course-3"),
//				new Course("course-4")
//		);
//		var courses3 = Set.of(
//				new Course("course-5"),
//				new Course("course-6")
//		);
//
//		var groups = Set.of(
//				new Group("group-1"),
//				new Group("group-2"),
//				new Group("group-3"),
//				new Group("group-4"),
//				new Group("group-5")
//		);
//
//		var students = Set.of(
//				new Student("name-1", "surname-1", courses1, groups),
//				new Student("name-2", "surname-2", courses2, groups),
//				new Student("name-3", "surname-3", courses3, groups)
//		);
//
//		courses1.forEach(course -> course.setStudent(new ArrayList<>(students).get(0)));
//		courses2.forEach(course -> course.setStudent(new ArrayList<>(students).get(1)));
//		courses3.forEach(course -> course.setStudent(new ArrayList<>(students).get(2)));
//
//		studentRepository.saveAll(students);
//
//		//join fetch
////		var s1 = studentRepository.getAllStudentsWithJoinFetch();
//
//		//native with custom repository
//		var s2 = studentRepository.getAllStudentsNative();
//		System.out.println("a");

}
