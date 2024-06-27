package mod_8;

import java.util.Objects;

public class Student implements Comparable<Student> {
    private String name;
    private String address;
    private double GPA;
    private String photoPath; // Path to the student's photo

    public Student(String name, String address, double GPA, String photoPath) {
        this.name = name;
        this.address = address;
        this.GPA = GPA;
        this.photoPath = photoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public int compareTo(Student other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Address: " + address + ", GPA: " + GPA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Double.compare(student.GPA, GPA) == 0 &&
                Objects.equals(name, student.name) &&
                Objects.equals(address, student.address) &&
                Objects.equals(photoPath, student.photoPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, GPA, photoPath);
    }
}
