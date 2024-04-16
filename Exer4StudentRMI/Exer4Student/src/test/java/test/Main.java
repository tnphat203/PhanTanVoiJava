/*
 * @ (#) Main.java	1.0	Apr 1, 2024
 * 
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package test;

import java.rmi.RemoteException;
import java.util.List;

import dao.CourseDAO;
import dao.DepartmentDAO;
import dao.StudentDAO;
import dao.impl.CourseImpl;
import dao.impl.DepartmentImpl;
import dao.impl.StudentImpl;
import entity.Course;

public class Main {

	public static void main(String[] args) throws RemoteException {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-mssql");
//		EntityManager em = emf.createEntityManager();
//		
//		EntityTransaction tx = em.getTransaction();
//		
//		tx.begin();
//		tx.commit();
		
		CourseDAO courseDAO = new CourseImpl();
		StudentDAO studentDAO = new StudentImpl();
		DepartmentDAO departmentDAO = new DepartmentImpl();
		
		departmentDAO.findDepartmentNotOwnerCourse().forEach(x -> System.out.println(x));
		
//		courseDAO.getCourseAndCountStudent2()
//		.entrySet()
//		.forEach(entry -> {
//			System.out.println("Course: " + entry.getKey());
//			System.out.println("Number of students: " + entry.getValue());
//		});
		
//		List<Course> courses = courseDAO.findBytitle("ph");
//		courses.forEach(x -> System.out.println(x));
		
//		Course course = courseDAO.findBytitle2("Physics");
//		System.out.println(course);
		
//		studentDAO.findStudentsEnrolledInCourse("po").forEach(x -> System.out.println(x));
//		studentDAO.findStudentsEnrolledInYear(2005).forEach(x -> System.out.println(x));
	}
}
