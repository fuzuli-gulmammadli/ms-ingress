package ingress.fuzuli.gulmamadli.microservice.repository.custom;

import ingress.fuzuli.gulmamadli.microservice.entity.Student;

import java.util.Set;

public interface StudentRepositoryCustom {
    Set<Student> getAllStudentsNative();
}
