package client;

import java.rmi.Naming;

import dao.CourseDAO;
import dao.DepartmentDAO;
import dao.StudentDAO;

public class Client {
	private static final String URL = "rmi://H92M17:7878/";
	public static void main(String[] args) {
		try {
			CourseDAO courseDAO = (CourseDAO) Naming.lookup(URL + "courseDAO");
			StudentDAO studentDAO = (StudentDAO) Naming.lookup(URL + "studentDAO");
			DepartmentDAO departmentDAO = (DepartmentDAO) Naming.lookup(URL + "departmentDAO");
			
			
			departmentDAO.findDepartmentNotOwnerCourse().forEach(x -> System.out.println(x));
			
			studentDAO.findAll().forEach(x -> System.out.println(x));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
