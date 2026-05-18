// Problem 9: School Student Grade Management System

interface Evaluable {
    String computeGrade();
}

abstract class Person {
    private String personID;
    private String name;
    private String email;
    public static int totalStudents = 0;

    public Person(String personID, String name, String email) {
        this.personID = personID;
        this.name = name;
        this.email = email;
    }

    public String getPersonID() { return personID; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public abstract void displayInfo();
}

abstract class Student extends Person implements Evaluable {
    private String studentID;
    private String program;
    private double[] marks;

    public Student(String personID, String name, String email, String studentID, String program, double[] marks) {
        super(personID, name, email);
        this.studentID = studentID;
        this.program = program;
        this.marks = marks;
        totalStudents++;
    }

    public String getStudentID() { return studentID; }
    public String getProgram() { return program; }

    public double getAverage() {
        double sum = 0;
        for (double m : marks) sum += m;
        return sum / marks.length;
    }

    public abstract String computeGrade();
}

class UndergraduateStudent extends Student {
    private int yearOfStudy;

    public UndergraduateStudent(String personID, String name, String email, String studentID, String program,
                                double[] marks, int yearOfStudy) {
        super(personID, name, email, studentID, program, marks);
        this.yearOfStudy = yearOfStudy;
    }

    public String computeGrade() {
        UGGradePolicy p = new UGGradePolicy();
        return p.assign(getAverage());
    }

    public void displayInfo() {
        System.out.println("=== Undergraduate Student ===");
        System.out.println("ID: " + getPersonID() + " | Name: " + getName() + " | Email: " + getEmail());
        System.out.println("Roll: " + getStudentID() + " | Program: " + getProgram() + " | Year: " + yearOfStudy);
        UGGradePolicy p = new UGGradePolicy();
        System.out.println("Average: " + String.format("%.2f", getAverage()) + " | Grade (UGGradePolicy): " + p.assign(getAverage()));
    }

    public static class UGGradePolicy {
        public String assign(double average) {
            if (average >= 80) return "A";
            if (average >= 70) return "B";
            if (average >= 60) return "C";
            if (average >= 50) return "D";
            return "F";
        }
    }
}

class GraduateStudent extends Student {
    private String thesisTitle;
    private String supervisor;

    public GraduateStudent(String personID, String name, String email, String studentID, String program,
                           double[] marks, String thesisTitle, String supervisor) {
        super(personID, name, email, studentID, program, marks);
        this.thesisTitle = thesisTitle;
        this.supervisor = supervisor;
    }

    public String computeGrade() {
        GradGradePolicy p = new GradGradePolicy();
        return p.assign(getAverage());
    }

    public void displayInfo() {
        System.out.println();
        System.out.println("=== Graduate Student ===");
        System.out.println("ID: " + getPersonID() + " | Name: " + getName() + " | Email: " + getEmail());
        System.out.println("Roll: " + getStudentID() + " | Program: " + getProgram());
        System.out.println("Thesis: " + thesisTitle + " | Supervisor: " + supervisor);
        GradGradePolicy p = new GradGradePolicy();
        System.out.println("Average: " + String.format("%.2f", getAverage()) + " | Grade (GradGradePolicy): " + p.assign(getAverage()));
    }

    public static class GradGradePolicy {
        public String assign(double average) {
            if (average >= 85) return "A";
            if (average >= 75) return "B";
            if (average >= 65) return "C";
            return "F";
        }
    }
}

class GradeCalculator {
    public String evaluate(double average) {
        if (average >= 85) return "A+";
        if (average >= 80) return "A";
        if (average >= 70) return "B";
        if (average >= 60) return "C";
        return "F";
    }
    public String evaluate(double average, double bonus) {
        return evaluate(average + bonus);
    }
}

public class SchoolSystem {
    public static void main(String[] args) {
        double[] m1 = {75, 80, 90};
        double[] m2 = {85, 88, 92};

        UndergraduateStudent ug = new UndergraduateStudent("UG01", "Karim", "karim@uni.edu",
                "S001", "CSE", m1, 3);

        GraduateStudent grad = new GraduateStudent("GR01", "Nila", "nila@uni.edu",
                "S002", "MS-CSE", m2, "AI Ethics", "Dr. Smith");

        ug.displayInfo();
        System.out.println("Grade: " + ug.computeGrade());

        grad.displayInfo();
        System.out.println("Grade: " + grad.computeGrade());

        GradeCalculator gc = new GradeCalculator();
        System.out.println();
        System.out.println("evaluate(81.67)     : " + gc.evaluate(81.67));
        System.out.println("evaluate(81.67,5.0) : " + gc.evaluate(81.67, 5.0));

        System.out.println("Total Students: " + Person.totalStudents);
    }
}
