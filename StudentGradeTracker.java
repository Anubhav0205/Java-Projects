import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> students = new ArrayList<>();
        ArrayList<Double> grades = new ArrayList<>();
        
        int numberOfStudents = 0;
        
        // Take the number of students as input with exception handling
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter the number of students: ");
                numberOfStudents = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        
        // Collect name and grade for each student with exception handling
        for (int i = 0; i < numberOfStudents; i++) {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();
            double grade = -1;
            boolean validGradeInput = false;
            while (!validGradeInput) {
                try {
                    System.out.print("Enter grade for " + name + " (0-100): ");
                    grade = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline left-over
                    if (grade >= 0 && grade <= 100) {
                        validGradeInput = true;
                    } else {
                        System.out.println("Invalid grade. Please enter a value between 0 and 100.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid grade (numeric value).");
                    scanner.nextLine(); // Clear the invalid input
                }
            }
            students.add(name);
            grades.add(grade);
        }

        boolean continueProgram = true;
        while (continueProgram) {
            int choice = 0;
            boolean validChoice = false;
            while (!validChoice) {
                try {
                    System.out.println("\nWould you like to:");
                    System.out.println("1. Add another student");
                    System.out.println("2. Display all students and their grades");
                    System.out.println("3. Display results (average, highest, lowest)");
                    System.out.println("4. End the program");
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    if (choice >= 1 && choice <= 4) {
                        validChoice = true;
                    } else {
                        System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter 1, 2, 3, or 4.");
                    scanner.nextLine(); // Clear the invalid input
                }
            }
            
            switch (choice) {
                case 1:
                    // Add another student with exception handling
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    double grade = -1;
                    boolean validGradeInput = false;
                    
                    while (!validGradeInput) {
                        try {
                            System.out.print("Enter grade for " + name + " (0-100): ");
                            grade = scanner.nextDouble();
                            scanner.nextLine(); // Consume newline left-over
                            if (grade >= 0 && grade <= 100) {
                                validGradeInput = true;
                            } else {
                                System.out.println("Invalid grade. Please enter a value between 0 and 100.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid grade (numeric value).");
                            scanner.nextLine(); // Clear the invalid input
                        }
                    }
                    students.add(name);
                    grades.add(grade);
                    break;
                
                case 2:
                    // Display all students and their grades
                    System.out.println("\nStudent List:");
                    for (int i = 0; i < students.size(); i++) {
                        System.out.println("Student: " + students.get(i) + ", Grade: " + grades.get(i));
                    }
                    break;
                
                case 3:
                    // Calculate total, highest, and lowest grades
                    if (grades.size() > 0) {
                        double total = 0;
                        double highest = grades.get(0);
                        double lowest = grades.get(0);
                        
                        for (double gradeValue : grades) {
                            total += gradeValue;
                            if (gradeValue > highest) highest = gradeValue;
                            if (gradeValue < lowest) lowest = gradeValue;
                        }
                        
                        double average = total / grades.size();
                        
                        // Display the results
                        System.out.println("\nResults:");
                        System.out.println("Average Grade: " + average);
                        System.out.println("Highest Grade: " + highest);
                        System.out.println("Lowest Grade: " + lowest);
                    } else {
                        System.out.println("No grades available to calculate results.");
                    }
                    break;
                
                case 4:
                    // End the program
                    System.out.println("Program ended. Goodbye!");
                    continueProgram = false;
                    break;
                
                default:
                    System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
                    break;
            }
        }
        scanner.close();
    }
}