package ingress.fuzuli.gulmamadli.microservice.repository;

import ingress.fuzuli.gulmamadli.microservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
