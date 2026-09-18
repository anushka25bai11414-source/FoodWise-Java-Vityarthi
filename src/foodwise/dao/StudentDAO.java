package foodwise.dao;

import foodwise.model.Student;
import foodwise.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO 
{

    // ADD STUDENT
    public boolean addStudent(Student student) {

        String sql= """
                INSERT INTO students (name, age, group_name)
                VALUES (?,?,?)
                """;

        try (Connection connection= DatabaseManager.getConnection();
             PreparedStatement statement= connection.prepareStatement(sql)) {

            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setString(3, student.getGroupName());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("error adding student: " + e.getMessage());
            return false;
        }
    }


    // GET ALL STUDENTS
    public List<Student> getAllStudents() {

        List<Student> students= new ArrayList<>();

        String sql= "SELECT * FROM students ORDER BY student_id";

        try (Connection connection= DatabaseManager.getConnection();
             Statement statement= connection.createStatement();
             ResultSet resultSet= statement.executeQuery(sql)) {

            while (resultSet.next()) {

                Student student= new Student();

                student.setUserId(resultSet.getInt("student_id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                student.setGroupName(resultSet.getString("group_name"));

                students.add(student);
            }

        } catch (SQLException e) {

            System.out.println(
            "error retrieving students: "+e.getMessage()
            );
        }

        return students;
    }


    // UPDATE STUDENT
    public boolean updateStudent(Student student) {

        String sql = """
                UPDATE students
                SET name =?, age =?, group_name =?
                WHERE student_id =?
                """;

        try 
        (Connection connection= DatabaseManager.getConnection();
            PreparedStatement statement= connection.prepareStatement(sql)) {

            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setString(3, student.getGroupName());
            statement.setInt(4, student.getUserId());

            return statement.executeUpdate() > 0;

        } catch 
        (SQLException e) {

            System.out.println(
            "Error updating student: " + e.getMessage()
            );

            return false;
        }
    }


    // DELETE STUDENT
    public boolean deleteStudent(int studentId) {

        String sql= "DELETE FROM students WHERE student_id = ?";

        try 
        (Connection connection=DatabaseManager.getConnection();
             PreparedStatement statement=connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            return statement.executeUpdate()>0;

        } catch (SQLException e) {

            System.out.println(
                "error deleting student: " + e.getMessage()
            );

            return false;
        }
    }
}