package foodwise.service;

import foodwise.dao.StudentDAO;
import foodwise.exception.InvalidInputException;
import foodwise.model.Student;

import java.util.List;

public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService() {
        studentDAO=new StudentDAO();
    }


    public boolean addStudent(String name, int age, String groupName)
            throws InvalidInputException {

        validateStudent(name,age,groupName);

        Student student = new Student(
                0,
                name,
                "",
                "",
                age,
                groupName
        );

        return studentDAO.addStudent(student);
    }


    public List<Student> getAllStudents() {

        return studentDAO.getAllStudents();
    }


    public boolean updateStudent(
            int studentId,
            String name,
            int age,
            String groupName)
            throws InvalidInputException {

        validateStudent(name, age, groupName);

        Student student=new Student(
                studentId,
                name,
                "",
                "",
                age,
                groupName
        );

        return studentDAO.updateStudent(student);
    }


    public boolean deleteStudent(int studentId)
            throws InvalidInputException {

        if (studentId<=0) {

            throw new InvalidInputException(
                    "Student ID should be greater than 0."
            );
        }

        return studentDAO.deleteStudent(studentId);
    }


    private void validateStudent(
            String name,
            int age,
            String groupName)
            throws InvalidInputException {

        if (name==null || name.trim().isEmpty()) {

            throw new InvalidInputException(
                    "Student name cannot be empty."
            );
        }

        if (age<5 || age>100) {

            throw new InvalidInputException(
                    "Please enter valid age."
            );
        }

        if (groupName==null || groupName.trim().isEmpty()) {

            throw new InvalidInputException(
                    "Group name can't be empty."
            );
        }
    }
}