package foodwise.model;

public class Student extends User {
    private int age;
    private String groupName;

    public Student() {
        super();
    }

    public Student(int userId, String name, String username, String password,
                   int age, String groupName) {
        super(userId, name, username, password);
        this.age= age;
        this.groupName= groupName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age= age;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName= groupName;
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }

    @Override
    public String toString() {
        return "Student{" +
                "userId =" + getUserId() +
                ", name ='" + getName() + '\'' +
                ", age =" + age +
                ", groupName ='" + groupName + '\'' +
                '}';
    }
}
