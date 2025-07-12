package com.swaraj.student_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
public class Student {

    //defining fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int ID;

    @Column(name = "first_name")
    @NotBlank(message = "Name is required")
    private String Name;

    @Column(name = "age")
    @Min(value = 1, message = "Age must be positive")
    private int Age;

    @Column(name = "email")
    private String Email;

    //defining constructors(keeping one extra just in case)
    public Student()
    {

    }

    //no id since it will be generated automatically
    public Student(String Name, int Age, String Email)
    {
        this.Name = Name;
        this.Age = Age;
        this.Email = Email;
    }

    //define getters and setters


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    //define toString

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Age=" + Age +
                ", Email='" + Email + '\'' +
                '}';
    }
}