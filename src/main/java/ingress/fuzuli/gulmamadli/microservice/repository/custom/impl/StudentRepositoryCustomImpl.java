package ingress.fuzuli.gulmamadli.microservice.repository.custom.impl;

import ingress.fuzuli.gulmamadli.microservice.entity.Course;
import ingress.fuzuli.gulmamadli.microservice.entity.Group;
import ingress.fuzuli.gulmamadli.microservice.entity.Student;
import ingress.fuzuli.gulmamadli.microservice.repository.custom.StudentRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentRepositoryCustomImpl implements StudentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Student> getAllStudentsNative() {

        String queryString = " select s.id as student_id, " +
                " s.first_name, " +
                " s.last_name, " +
                " c.id as course_id, " +
                " c.course_name, " +
                " sg.id as group_id, " +
                " sg.group_name " +
                " from student s " +
                " join course c on c.student_id = s.id " +
                " join student_and_group sag on s.id = sag.student_id " +
                " join student_group sg on sg.id = sag.group_id " +
                " order by s.id ";

        Query query = entityManager.createNativeQuery(queryString);

        List<Object[]> list = query.getResultList();
        if(list == null || list.size() <= 0){
            return null;
        }

        var students = list
                .stream()
                .map(o -> new Student(
                        Long.parseLong(o[0].toString()),
                        o[1].toString(),
                        o[2].toString()
                )
        ).collect(Collectors.toSet());

        students.forEach(student -> {
            var courses = list.stream()
                    .filter(o -> o[0].toString().equals(student.getId()+""))
                    .map(o -> new Course(
                            Long.parseLong(o[3].toString()),
                            o[4].toString()
                            )).collect(Collectors.toSet());
            student.setCourses(courses);

            var groups = list.stream()
                    .filter(o -> o[0].toString().equals(student.getId()+""))
                    .map(o -> new Group(
                            Long.parseLong(o[5].toString()),
                            o[6].toString()
                    )).collect(Collectors.toSet());
            student.setGroups(groups);
        });

        return students;
    }
}
