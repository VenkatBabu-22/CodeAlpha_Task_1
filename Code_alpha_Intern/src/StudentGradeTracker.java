import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {

    static class Student {
        private String name;
        private double grade;

        public Student(String name, double grade) {
            this.name = name;
            this.grade = grade;
        }

        public String getName() {
            return name;
        }

        public double getGrade() {
            return grade;
        }
    }

    private ArrayList<Student> students;
    private Scanner scanner;

    public StudentGradeTracker() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("===== Student Grade Tracker =====");

        while (true) {
            System.out.println("\n1. Add Student\n2. Show Summary\n3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear input buffer

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> showSummary();
                case 3 -> {
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter grade (0–100): ");
        double grade = scanner.nextDouble();
        scanner.nextLine();

        students.add(new Student(name, grade));
        System.out.println("Student added.");
    }

    private void showSummary() {
        if (students.isEmpty()) {
            System.out.println("No student data available.");
            return;
        }

        double total = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;

        System.out.println("\n--- Student Summary Report ---");
        for (Student student : students) {
            double grade = student.getGrade();
            total += grade;
            highest = Math.max(highest, grade);
            lowest = Math.min(lowest, grade);
            System.out.println("Name: " + student.getName() + ", Grade: " + grade);
        }

        double average = total / students.size();
        System.out.printf("\nAverage Grade: %.2f\n", average);
        System.out.printf("Highest Grade: %.2f\n", highest);
        System.out.printf("Lowest Grade: %.2f\n", lowest);
    }

    public static void main(String[] args) {
        StudentGradeTracker tracker = new StudentGradeTracker();
        tracker.run();
    }
}
