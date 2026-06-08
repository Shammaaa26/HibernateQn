package com.demo.entity;

import java.util.List;

import jakarta.persistence.*;

public class MainApp {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("HibernateJPADemo");

        EntityManager em =
                emf.createEntityManager();

        System.out.println("\n===== STEP 1 : CREATE COURSES =====");

        em.getTransaction().begin();

        Course java =
                new Course(
                        "Java Programming",
                        "3 Months");

        Course spring =
                new Course(
                        "Spring Boot",
                        "2 Months");

        em.persist(java);
        em.persist(spring);

        em.getTransaction().commit();

        System.out.println("Courses Saved!");

        System.out.println("\n===== STEP 2 : CREATE STUDENTS =====");

        em.getTransaction().begin();

        Student s1 = new Student("John", "Doe", "john@demo.com");
        Student s2 = new Student("Jane", "Smith", "jane1@demo.com");
        Student s3 = new Student("Emma","Wilson","emm1a@demo.com");
        

        
        
        Address a1 =
        	    new Address(
        	        "MG Road",
        	        "Calicut",
        	        "673001");

        	Address a2 =
        	    new Address(
        	        "Beach Road",
        	        "Kochi",
        	        "682001");

        	Address a3 =
        	    new Address(
        	        "Town Hall Road",
        	        "Thrissur",
        	        "680001");
        	
        s1.setAddress(a1);
        s2.setAddress(a2);
        s3.setAddress(a3);
        
        Department cs = new Department("Computer Science");
        Department it = new Department("Information Technology");
        
        cs.addStudent(s1);
        cs.addStudent(s2);

        it.addStudent(s3);
        
        s1.enrollCourse(java);

        s2.enrollCourse(java);

        s3.enrollCourse(spring);
        
        em.persist(cs);
        em.persist(it);
        
        em.persist(s1);
        em.persist(s2);
        em.persist(s3);

        em.getTransaction().commit();

        System.out.println("Students Saved!");

        System.out.println("\n===== STEP 3 : DISPLAY ALL STUDENTS =====");

        List<Student> students =
                em.createQuery(
                        "SELECT s FROM Student s",
                        Student.class)
                        .getResultList();

        students.forEach(System.out::println);

        System.out.println("\n===== STEP 4 : FIND STUDENT BY ID =====");

        Student found =
                em.find(Student.class, 1);

        System.out.println(found);

        System.out.println("\n===== STEP 5 : UPDATE EMAIL =====");

        em.getTransaction().begin();

        found.setEmail("alice_new@demo.com");

        em.merge(found);

        em.getTransaction().commit();

        System.out.println("Updated Student:");
        System.out.println(found);

        System.out.println("\n===== STEP 6 : DELETE STUDENT ID 2 =====");

        em.getTransaction().begin();

        Student deleteStudent =
                em.find(Student.class, 2);

        em.remove(deleteStudent);

        em.getTransaction().commit();

        System.out.println("Student Deleted!");

        System.out.println("\n===== STEP 7 : STUDENTS IN JAVA PROGRAMMING =====");

        List<Student> javaStudents =
        		em.createQuery(
        			    "SELECT s FROM Student s JOIN s.courses c WHERE c.courseName = :name",
        			    Student.class)
                        .setParameter(
                                "name",
                                "Java Programming")
                        .getResultList();

        for (Student s : javaStudents) {

            System.out.println(
                    s.getFirstName()
                            + " | "
                            + s.getEmail());
        }

        System.out.println("\n===== STEP 8 : FINAL STUDENT LIST =====");

        List<Student> finalList =
                em.createQuery(
                        "SELECT s FROM Student s",
                        Student.class)
                        .getResultList();

        finalList.forEach(System.out::println);

        em.close();
        emf.close();

        System.out.println("\nProgram Completed Successfully!");
    }
}