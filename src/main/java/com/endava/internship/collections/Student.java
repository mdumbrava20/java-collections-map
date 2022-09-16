package com.endava.internship.collections;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * The class that defines the element that will be contained by your collection
 */
@Getter
@Setter
public class Student implements Comparable<Student> //TODO consider implementing any interfaces necessary for your collection
{
    private String name;

    private LocalDate dateOfBirth;

    private String details;

    public Student(String name, LocalDate dateOfBirth, String details) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return name.equals(student.name) && dateOfBirth.equals(student.dateOfBirth) && details.equals(student.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth, details);
    }

    @Override
    public int compareTo(Student student) {
        int compareByName = name.compareTo(student.name);
        if (compareByName != 0) {
            return -compareByName;
        }
        return -dateOfBirth.compareTo(student.dateOfBirth);
    }

    @Override
    public String toString() {
        return "Student{" + "name='" + name + '\'' + ", " + "dateOfBirth=" + dateOfBirth + ", details='" + details + '\'' + '}';
    }
        /*
    TODO consider overriding any methods for this object to function properly within a collection:
        1. A student is considered unique by a combination of their name and dateOfBirth
        2. Student names are sorted alphabetically, if two students have the same name, then the older one is
        placed before the younger student in an ordered student list.
    */
}
