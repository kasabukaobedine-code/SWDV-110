package ListManager;
public class Student {
    private String name;
    private String id;
    private double gpa;

    public Student(String name, String id, double gpa) {
        this.name = name;
        this.id = id;
        setGpa(gpa);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        if (gpa >= 0.0 && gpa <= 4.0) {
            this.gpa = gpa;
        } else {
            System.out.println("Invalid GPA. Must be between 0.0 and 4.0.");
        }
    }

    public String getStudent() {
        return name + ", " + id + ", " + gpa;
    }

    public static void main(String[] args) {
        Student s1 = new Student("Alice", "S1001", 3.8);
        Student s2 = new Student("Bob", "S1002", 3.2);
        Student s3 = new Student("Charlie", "S1003", 3.9);

        System.out.println(s1.getStudent());
        System.out.println(s2.getStudent());
        System.out.println(s3.getStudent());
    }
}
