package library.deepaklibraryportal.Service;

import library.deepaklibraryportal.Model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    public Student saveStudent(Student student) throws Exception;

    public void updateStudent (Student student) throws Exception;
    public List<Student> getAllStudents();

    public Optional<Student> login(String emailId, String password) throws Exception;

    public boolean canGraduate(int id);
}
