package com.endava.internship.collections;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentMapTest {

    private StudentMap studentMap;

    private final static Integer AGE1 = 21;

    private final static Integer AGE2 = 22;

    private final static Integer AGE3 = 23;

    @BeforeEach
    void setUp() {
        studentMap = new StudentMap();
    }

    @Test
    void putNewInstanceTest() {
        Student newStudent = createStudent();

        Integer result = studentMap.put(newStudent, AGE1);

        assertAll(
                () -> assertThat(studentMap).containsEntry(newStudent, AGE1),
                () -> assertThat(studentMap.containsValue(AGE1)).isTrue(),
                () -> assertThat(studentMap).hasSize(1),
                () -> assertThat(studentMap.get(newStudent)).isEqualTo(AGE1),
                () -> assertThat(result).isNull()
        );
    }

    @Test
    void putExistingInstanceWithSameValueTest() {
        Student student = createStudent();
        Student sameStudent = createStudent();

        Integer firstInsert = studentMap.put(student, AGE1);
        Integer secondInsert = studentMap.put(sameStudent, AGE1);

        assertAll(
                () -> assertThat(studentMap).containsEntry(student, AGE1),
                () -> assertThat(studentMap).containsValue(AGE1),
                () -> assertThat(studentMap).hasSize(1),
                () -> assertThat(studentMap.get(student)).isEqualTo(AGE1),
                () -> assertThat(studentMap.get(sameStudent)).isEqualTo(AGE1),
                () -> assertThat(firstInsert).isNull(),
                () -> assertThat(secondInsert).isEqualTo(AGE1)
        );
    }

    @Test
    void putExistingInstanceWithDifferentValueTest() {
        Student student = createStudent();
        Student sameStudent = createStudent();

        Integer sameStudentAge = 30;

        Integer firstInsert = studentMap.put(student, AGE1);
        Integer secondInsert = studentMap.put(sameStudent, sameStudentAge);

        assertAll(
                () -> assertThat(studentMap).containsEntry(student, sameStudentAge),
                () -> assertThat(studentMap).containsEntry(sameStudent, sameStudentAge),
                () -> assertThat(studentMap).doesNotContainEntry(student, AGE1),
                () -> assertThat(studentMap).hasSize(1),
                () -> assertThat(studentMap.get(student)).isEqualTo(sameStudentAge),
                () -> assertThat(firstInsert).isNull(),
                () -> assertThat(secondInsert).isEqualTo(AGE1)
        );
    }

    @Test
    void sizeWithNoExistingInstancesTest() {
        int size = studentMap.size();

        assertAll(
                () -> assertThat(size).isZero(),
                () -> assertThat(studentMap).isEmpty()
        );
    }

    @Test
    void sizeWithOneInstanceTest() {
        Student student = createStudent();
        addSingleStudent();

        int size = studentMap.size();

        assertAll(
                () -> assertThat(studentMap).containsEntry(student, AGE1),
                () -> assertThat(size).isEqualTo(1)
        );
    }

    @Test
    void sizeWithSameInstanceSameValueInsertTest() {
        Student sameStudent = createStudent();
        addSingleStudent();

        int sizeBefore = studentMap.size();

        studentMap.put(sameStudent, AGE1);
        int sizeAfter = studentMap.size();

        assertAll(
                () -> assertThat(sizeBefore).isEqualTo(1),
                () -> assertThat(sizeBefore).isEqualTo(sizeAfter)
        );
    }

    @Test
    void sizeWhenDeletingInstanceTest() {
        List<Student> students = populateStudentList();

        studentMap.put(students.get(0), AGE1);
        studentMap.put(students.get(1), AGE2);
        studentMap.put(students.get(2), AGE3);

        int sizeBefore = studentMap.size();

        studentMap.remove(students.get(1));

        int sizeAfter = studentMap.size();

        assertAll(
                () -> assertThat(studentMap).doesNotContainEntry(students.get(1), AGE2),
                () -> assertThat(sizeBefore).isEqualTo(3),
                () -> assertThat(sizeAfter).isEqualTo(2)
        );
    }

    @Test
    void sizeWhenClearingTreeMapTest() {
        Student student1 = new Student("Ben",
                LocalDate.of(2001, 7, 18),
                "Some student details");
        Student student2 = new Student("Clint",
                LocalDate.of(2002, 8, 10),
                "Student details");
        Student student3 = new Student("Alex",
                LocalDate.of(2003, 4, 23),
                "Details");

        studentMap.put(student1, AGE1);
        studentMap.put(student2, AGE2);
        studentMap.put(student3, AGE3);

        int sizeBefore = studentMap.size();

        studentMap.clear();

        int sizeAfter = studentMap.size();

        assertAll(
                () -> assertThat(sizeBefore).isEqualTo(3),
                () -> assertThat(studentMap).isEmpty(),
                () -> assertThat(sizeAfter).isZero()
        );
    }

    @Test
    void isEmptyWhenNoInstancesTest() {
        boolean isEmpty = studentMap.isEmpty();

        assertAll(
                () -> assertThat(studentMap).isEmpty(),
                () -> assertThat(isEmpty).isTrue()
        );
    }

    @Test
    void isEmptyWithInstancesTest() {
        Student student = createStudent();

        studentMap.put(student, AGE1);
        boolean isEmpty = studentMap.isEmpty();

        assertAll(
                () -> assertThat(studentMap).containsEntry(student, AGE1),
                () -> assertThat(isEmpty).isFalse()
        );
    }

    @Test
    void containsKeyExistingInstanceTest() {
        List<Student> students = populateStudentList();

        studentMap.put(students.get(0), AGE1);
        studentMap.put(students.get(1), AGE2);
        studentMap.put(students.get(2), AGE3);

        boolean containsKey = studentMap.containsKey(students.get(1));

        assertAll(
                () -> assertThat(studentMap).containsEntry(students.get(1), AGE2),
                () -> assertThat(containsKey).isTrue()
        );

    }

    @Test
    void containsKeyNonExistingInstanceTest() {
        List<Student> students = populateStudentList();
        Student nonExistingStudent = createStudent();
        Integer age4 = 24;

        studentMap.put(students.get(0), AGE1);
        studentMap.put(students.get(1), AGE2);
        studentMap.put(students.get(2), AGE3);


        boolean containsKey = studentMap.containsKey(nonExistingStudent);

        assertAll(
                () -> assertThat(studentMap).doesNotContainEntry(nonExistingStudent, age4),
                () -> assertThat(containsKey).isFalse()
        );
    }

    @Test
    void containsKeyWhenMapIsEmptyTest() {
        Student student = createStudent();

        boolean containsKey = studentMap.containsKey(student);

        assertAll(
                () -> assertThat(studentMap).isEmpty(),
                () -> assertFalse(containsKey)
        );
    }

    @Test
    void containsValueWhenMapIsEmptyTest() {

        boolean containsValue = studentMap.containsValue(AGE1);

        assertAll(
                () -> assertThat(studentMap).isEmpty(),
                () -> assertFalse(containsValue)
        );
    }

    @Test
    void containsValueWhenMapIsNotHavingSpecifiedValueTest() {
        List<Student> students = populateStudentList();

        studentMap.put(students.get(0), AGE1);
        studentMap.put(students.get(1), AGE2);
        studentMap.put(students.get(2), AGE3);

        boolean containsValue = studentMap.containsValue(24);

        assertAll(
                () -> assertThat(studentMap).isNotEmpty(),
                () -> assertFalse(containsValue)
        );
    }

    @Test
    void containsValueWhenMapIsHavingSpecifiedValueTest() {
        List<Student> students = populateStudentList();

        studentMap.put(students.get(0), AGE1);
        studentMap.put(students.get(1), AGE2);
        studentMap.put(students.get(2), AGE3);

        boolean containsValue = studentMap.containsValue(AGE2);

        assertAll(
                () -> assertThat(studentMap).isNotEmpty(),
                () -> assertTrue(containsValue)
        );
    }

    @Test
    void getWhenMapIsEmptyTest() {
        Student student = createStudent();

        Integer getResult = studentMap.get(student);

        assertAll(
                () -> assertThat(studentMap).isEmpty(),
                () -> assertNull(getResult)
        );
    }

    @Test
    void getWhenMapDoesNotHaveSpecifiedInstanceTest() {
        List<Student> students = populateStudentList();
        Student nonExistingStudent = createStudent();
        Integer age4 = 24;

        studentMap.put(students.get(0), AGE1);
        studentMap.put(students.get(1), AGE2);
        studentMap.put(students.get(2), AGE3);

        Integer getResult = studentMap.get(nonExistingStudent);

        assertAll(
                () -> assertThat(studentMap).isNotEmpty(),
                () -> assertThat(studentMap).doesNotContainEntry(nonExistingStudent, age4),
                () -> assertNull(getResult)
        );
    }

    @Test
    void getWhenMapContainedSpecifiedInstanceTest() {
        List<Student> students = populateStudentList();

        studentMap.put(students.get(0), AGE1);
        studentMap.put(students.get(1), AGE2);
        studentMap.put(students.get(2), AGE3);

        Integer getResult = studentMap.get(students.get(1));

        assertAll(
                () -> assertThat(studentMap).isNotEmpty(),
                () -> assertThat(getResult).isEqualTo(AGE2)
        );
    }

    @Test
    void removeWhenMapIsEmptyTest() {
        Student student = createStudent();

        Integer removeResult = studentMap.remove(student);

        assertNull(removeResult);
    }

    @Test
    void removeWhenMapIsWithOneInstanceTest() {
        Student student = createStudent();

        studentMap.put(student, AGE1);

        Integer removeResult = studentMap.remove(student);
        boolean contains = studentMap.containsKey(student);

        assertAll(
                () -> assertThat(studentMap).isEmpty(),
                () -> assertEquals(0, studentMap.size()),
                () -> assertFalse(contains),
                () -> assertEquals(AGE1, removeResult)
        );
    }

    @Test
    void removeWhenInsertedInstanceHasNoChild() {
        List<Student> students = populateStudentList();
        List<Integer> ages = getStudentsAge();

        for (int i = 0; i < students.size(); i++) {
            studentMap.put(students.get(i), ages.get(i));
        }

        Student noChildNode = students.get(7);
        Integer noChildNodeValue = ages.get(7);

        Integer removeResult = studentMap.remove(noChildNode);

        assertAll(
                () -> assertFalse(studentMap.containsKey(noChildNode)),
                () -> assertEquals(7, studentMap.size()),
                () -> assertEquals(noChildNodeValue, removeResult)
        );
    }

    @Test
    void removeWhenInsertedInstanceHasOneChildTest() {
        List<Student> students = populateStudentList();
        List<Integer> ages = getStudentsAge();

        for (int i = 0; i < students.size(); i++) {
            studentMap.put(students.get(i), ages.get(i));
        }

        Student oneChildNode = students.get(6);
        Student childNode = students.get(7);
        Integer oneChildNodeValue = ages.get(6);

        Integer removeResult = studentMap.remove(oneChildNode);

        assertAll(
                () -> assertFalse(studentMap.containsKey(oneChildNode)),
                () -> assertEquals(7, studentMap.size()),
                () -> assertTrue(studentMap.containsKey(childNode)),
                () -> assertEquals(oneChildNodeValue, removeResult)
        );
    }

    @Test
    void removeWhenInsertedInstanceHasTwoLeavesTest() {
        List<Student> students = populateStudentList();
        List<Integer> ages = getStudentsAge();

        for (int i = 0; i < students.size(); i++) {
            studentMap.put(students.get(i), ages.get(i));
        }

        Student twoLeavesNode = students.get(4);
        Student leftLeaf = students.get(5);
        Student rightLeaf = students.get(6);
        Integer twoLeavesNodeValue = ages.get(4);

        Integer removeResult = studentMap.remove(twoLeavesNode);

        assertAll(
                () -> assertFalse(studentMap.containsKey(twoLeavesNode)),
                () -> assertEquals(7, studentMap.size()),
                () -> assertTrue(studentMap.containsKey(leftLeaf)),
                () -> assertTrue(studentMap.containsKey(rightLeaf)),
                () -> assertEquals(twoLeavesNodeValue, removeResult)
        );

    }

    @Test
    void putAllWhenMapIsEmptyTest() {
        List<Student> students = populateStudentList();
        List<Integer> ages = getStudentsAge();
        Map<Student, Integer> newMap = new HashMap<>();
        for (int i = 0; i < students.size(); i++) {
            newMap.put(students.get(i), ages.get(i));
        }

        studentMap.putAll(newMap);

        assertAll(
                () -> assertThat(studentMap).isNotEmpty(),
                () -> assertTrue(studentMap.containsKey(students.get(0))),
                () -> assertEquals(8, studentMap.size())
        );
    }

    @Test
    void putAllWhenMapIsNotEmptyTest() {
        List<Student> students = populateStudentList();
        List<Integer> ages = getStudentsAge();
        studentMap.put(students.get(0), ages.get(0));
        studentMap.put(students.get(1), ages.get(1));
        studentMap.put(students.get(2), ages.get(2));
        Map<Student, Integer> newMap = new HashMap<>();
        for (int i = 3; i < students.size(); i++) {
            newMap.put(students.get(i), ages.get(i));
        }

        int sizeBefore = studentMap.size();
        studentMap.putAll(newMap);

        assertAll(
                () -> assertFalse(studentMap.isEmpty()),
                () -> assertEquals(3, sizeBefore),
                () -> assertTrue(studentMap.containsKey(students.get(3))),
                () -> assertEquals(8, studentMap.size())
        );
    }

    @Test
    void putAllWhenNewMapIsEmptyTest() {
        Student student = createStudent();
        studentMap.put(student, AGE1);
        Map<Student, Integer> newMap = new HashMap<>();

        studentMap.putAll(newMap);

        assertAll(
                () -> assertFalse(studentMap.isEmpty()),
                () -> assertTrue(studentMap.containsKey(student)),
                () -> assertEquals(1, studentMap.size())
        );
    }

    @Test
    void putAllWhenNewMapHasSameKeysTest() {
        List<Student> students = populateStudentList();
        List<Integer> ages = getStudentsAge();
        for (int i = 0; i < 3; i++) {
            studentMap.put(students.get(i), ages.get(i));
        }
        Map<Student, Integer> newMap = new HashMap<>();
        newMap.put(students.get(1), ages.get(1));
        newMap.put(students.get(2), ages.get(2));

        studentMap.putAll(newMap);

        assertAll(
                () -> assertTrue(studentMap.containsKey(students.get(1))),
                () -> assertEquals(3, studentMap.size())
        );
    }

    @Test
    void clearWhenMapIsEmptyTest() {
        studentMap.clear();
        assertThat(studentMap).isEmpty();
    }

    @Test
    void clearMapWithOneInstanceTest() {
        addSingleStudent();

        studentMap.clear();

        assertThat(studentMap).isEmpty();
    }

    @Test
    void clearMapWithMultipleInstancesTest() {
        List<Student> students = populateStudentList();

        studentMap.put(students.get(0), AGE1);
        studentMap.put(students.get(1), AGE2);
        studentMap.put(students.get(2), AGE3);

        studentMap.clear();

        assertThat(studentMap).isEmpty();
    }

    @Test
    void keySetWhenMapIsEmptyTest() {
        Set<Student> keySet = studentMap.keySet();

        assertEquals(0, keySet.size());
    }

    @Test
    void keySetWhenMapIsNotEmptyTest() {
        List<Student> students = populateStudentList();
        List<Integer> ages = getStudentsAge();

        for (int i = 0; i < students.size(); i++) {
            studentMap.put(students.get(i), ages.get(i));
        }

        Set<Student> keySet = studentMap.keySet();

        assertAll(
                () -> assertFalse(studentMap.isEmpty()),
                () -> assertEquals(8, studentMap.size()),
                () -> assertEquals(keySet.size(), studentMap.size()),
                () -> assertThat(keySet).containsAll(students)
        );
    }

    @Test
    void valuesWhenMapIsEmptyTest() {
        Collection<Integer> values = studentMap.values();

        assertAll(
                () -> assertTrue(studentMap.isEmpty()),
                () -> assertThat(values).isEmpty()
        );
    }

    @Test
    void valueWhenMapIsNotEmptyTest() {
        List<Student> students = populateStudentList();
        List<Integer> ages = getStudentsAge();
        for (int i = 0; i < students.size(); i++) {
            studentMap.put(students.get(i), ages.get(i));
        }

        Collection<Integer> values = studentMap.values();

        assertAll(
                () -> assertFalse(studentMap.isEmpty()),
                () -> assertEquals(8, studentMap.size()),
                () -> assertEquals(studentMap.size(), values.size()),
                () -> assertThat(values).containsAll(ages)
        );
    }

    @Test
    void valueWhenMapContainsMoreOfSameValuesTest() {
        List<Student> students = populateStudentList();
        List<Integer> ages = getStudentsAge();
        for (int i = 0; i < students.size(); i++) {
            studentMap.put(students.get(i), ages.get(i));
        }
        Student studentSameAge = createStudent();
        Integer age = ages.get(0);
        studentMap.put(studentSameAge, age);

        Collection<Integer> values = studentMap.values();

        assertAll(
                () -> assertEquals(9, studentMap.size()),
                () -> assertEquals(studentMap.size(), values.size()),
                () -> assertThat(values).containsAll(ages)
        );

    }

    private Student createStudent() {
        return new Student("Michael",
                LocalDate.of(2001, 7, 18),
                "Some student details");
    }

    private List<Student> populateStudentList() {
        Student student1 = new Student("Dean",
                LocalDate.of(2001, 7, 18),
                "Some student details");
        Student student2 = new Student("Ben",
                LocalDate.of(2002, 8, 10),
                "Student details");
        Student student3 = new Student("Alex",
                LocalDate.of(2003, 4, 23),
                "Details");
        Student student4 = new Student("Clint",
                LocalDate.of(2004, 9, 24),
                "Details");
        Student student5 = new Student("Frank",
                LocalDate.of(2006, 10, 25),
                "Frank's Details");
        Student student6 = new Student("Eric",
                LocalDate.of(2006, 11, 26),
                "Eric's details");
        Student student7 = new Student("George",
                LocalDate.of(2007, 12, 27),
                "George's details");
        Student student8 = new Student("Harry",
                LocalDate.of(2008, 1, 28),
                "Harry's details");


        return Arrays.asList(student1, student2, student3,
                student4, student5, student6, student7, student8);
    }

    private void addSingleStudent() {
        Student student = createStudent();
        studentMap.put(student, AGE1);
    }

    private List<Integer> getStudentsAge() {
        return Arrays.asList(20, 21, 22, 23, 24, 25, 26, 27);
    }
}