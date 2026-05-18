// Problem 5: University Course Registration System

interface Schedulable {
    void generateSchedule();
}

abstract class Course implements Schedulable {
    private String courseCode;
    private String courseTitle;
    private int creditHours;
    private String instructorName;
    private int seatLimit;
    private int enrolledCount;
    public static int totalCourses = 0;

    public Course(String courseCode, String courseTitle, int creditHours, String instructorName, int seatLimit) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
        this.instructorName = instructorName;
        this.seatLimit = seatLimit;
        this.enrolledCount = 0;
        totalCourses++;
    }

    public String getCourseCode() { return courseCode; }
    public String getCourseTitle() { return courseTitle; }
    public int getCreditHours() { return creditHours; }
    public String getInstructorName() { return instructorName; }
    public int getSeatLimit() { return seatLimit; }
    public int getEnrolledCount() { return enrolledCount; }
    public int getAvailableSeats() { return seatLimit - enrolledCount; }

    public void registerStudent() {
        if (enrolledCount < seatLimit) {
            enrolledCount++;
            System.out.println("Student registered. (" + enrolledCount + "/" + seatLimit + ")");
        }
    }

    public abstract int contactHours();
    public abstract void displayDetails();

    public static class TimeSlot {
        public String day;
        public String startTime;
        public String endTime;

        public TimeSlot(String day, String startTime, String endTime) {
            this.day = day;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public void display() {
            System.out.println("Time Slot: " + day + " | " + startTime + " - " + endTime);
            System.out.println();
        }
    }
}

class TheoryCourse extends Course {
    private int lectureHrsPerWeek;

    public TheoryCourse(String courseCode, String courseTitle, int creditHours, String instructorName,
                        int seatLimit, int lectureHrsPerWeek) {
        super(courseCode, courseTitle, creditHours, instructorName, seatLimit);
        this.lectureHrsPerWeek = lectureHrsPerWeek;
    }

    public int getLectureHrsPerWeek() { return lectureHrsPerWeek; }

    public int contactHours() { return lectureHrsPerWeek; }

    public void generateSchedule() {
        System.out.println("Schedule: " + getCourseCode() + " | Mon-Wed-Fri | " + lectureHrsPerWeek + " hrs/day | Lecture Hall");
    }

    public void displayDetails() {
        System.out.println("=== Theory Course Details ===");
        System.out.println("Course Code : " + getCourseCode());
        System.out.println("Title       : " + getCourseTitle());
        System.out.println("Credit Hours: " + getCreditHours());
        System.out.println("Instructor  : " + getInstructorName());
        System.out.println("Lecture/week: " + lectureHrsPerWeek + " hours");
    }

    public class Enrollment {
        public String studentId;
        public String enrollmentDate;

        public Enrollment(String studentId, String enrollmentDate) {
            this.studentId = studentId;
            this.enrollmentDate = enrollmentDate;
        }

        public void printConfirmation() {
            System.out.println();
            System.out.println("--- Enrollment Confirmation ---");
            System.out.println("Student : " + studentId);
            System.out.println("Course  : " + getCourseCode() + " - " + getCourseTitle());
            System.out.println("Date    : " + enrollmentDate);
            System.out.println();
        }
    }
}

class AdvancedTheoryCourse extends TheoryCourse {
    private String prerequisiteCourse;
    private boolean hasResearchComponent;

    public AdvancedTheoryCourse(String courseCode, String courseTitle, int creditHours, String instructorName,
                                int seatLimit, int lectureHrsPerWeek, String prerequisiteCourse, boolean hasResearchComponent) {
        super(courseCode, courseTitle, creditHours, instructorName, seatLimit, lectureHrsPerWeek);
        this.prerequisiteCourse = prerequisiteCourse;
        this.hasResearchComponent = hasResearchComponent;
    }

    public int contactHours() {
        return getLectureHrsPerWeek() + (hasResearchComponent ? 2 : 0);
    }

    public void generateSchedule() {
        System.out.println("Schedule: " + getCourseCode() + " | Mon-Wed-Fri | " + getLectureHrsPerWeek() + "+2 hrs/day | Lecture Hall + Research Lab");
    }

    public void displayDetails() {
        System.out.println("=== Advanced Theory Course Details ===");
        System.out.println("Course Code : " + getCourseCode());
        System.out.println("Title       : " + getCourseTitle());
        System.out.println("Credit Hours: " + getCreditHours());
        System.out.println("Instructor  : " + getInstructorName());
        System.out.println("Prerequisite: " + prerequisiteCourse);
        System.out.println("Research    : " + (hasResearchComponent ? "Yes" : "No"));
        System.out.println("Lecture/week: " + getLectureHrsPerWeek() + " hours");
        System.out.println();
    }
}

class LabCourse extends Course {
    private int labHrsPerWeek;
    private String equipment;

    public LabCourse(String courseCode, String courseTitle, int creditHours, String instructorName,
                     int seatLimit, int labHrsPerWeek, String equipment) {
        super(courseCode, courseTitle, creditHours, instructorName, seatLimit);
        this.labHrsPerWeek = labHrsPerWeek;
        this.equipment = equipment;
    }

    public int contactHours() { return labHrsPerWeek; }

    public void generateSchedule() {
        System.out.println("Schedule: " + getCourseCode() + " | Tue-Thu | " + labHrsPerWeek + " hrs/day | Lab (" + equipment + ")");
    }

    public void displayDetails() {
        System.out.println("=== Lab Course Details ===");
        System.out.println("Course Code : " + getCourseCode());
        System.out.println("Title       : " + getCourseTitle());
        System.out.println("Credit Hours: " + getCreditHours());
        System.out.println("Instructor  : " + getInstructorName());
        System.out.println("Lab hrs/week: " + labHrsPerWeek);
        System.out.println("Equipment   : " + equipment);
    }
}

public class UniversitySystem {
    public static void main(String[] args) {
        Course.TimeSlot ts = new Course.TimeSlot("Monday", "09:00", "12:00");
        ts.display();

        AdvancedTheoryCourse atc = new AdvancedTheoryCourse("CSE101", "Advanced OOP", 3, "Dr. Rahman",
                30, 3, "CSE101", true);

        LabCourse lc = new LabCourse("CSE101L", "OOP Lab", 1, "Dr. Rahman",
                20, 2, "PC, JDK, IDE");

        atc.displayDetails();
        atc.registerStudent();
        atc.registerStudent();
        atc.generateSchedule();

        System.out.println("Contact hrs/week : " + atc.contactHours());
        System.out.println("Available Seats : " + atc.getAvailableSeats());

        TheoryCourse.Enrollment enr = atc.new Enrollment("STU-001", "2025-06-01");
        enr.printConfirmation();

        lc.displayDetails();
        lc.generateSchedule();

        System.out.println("Total Courses: " + Course.totalCourses);
    }
}
