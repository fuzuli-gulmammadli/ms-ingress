package ingress.fuzuli.gulmamadli.microservice.repository;

import ingress.fuzuli.gulmamadli.microservice.entity.Student;
import ingress.fuzuli.gulmamadli.microservice.repository.custom.StudentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom {
    @Query("select s " +
            " from Student s " +
            " join fetch s.groups g " +
            " join fetch s.courses c ")
    Set<Student> getAllStudentsWithJoinFetch();


}
