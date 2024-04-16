package server;

import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;

import dao.CourseDAO;
import dao.DepartmentDAO;
import dao.StudentDAO;
import dao.impl.CourseImpl;
import dao.impl.DepartmentImpl;
import dao.impl.StudentImpl;

public class Server {
	private static final String URL = "rmi://H92M17:7878/";

	public static void main(String[] args) {
		try {
			
			Context context = new InitialContext();
			
			CourseDAO courseDAO = new CourseImpl(); // Remote Object
			StudentDAO studentDAO = new StudentImpl(); // Remote Object
			DepartmentDAO departmentDAO = new DepartmentImpl(); // Remote Object
			
			LocateRegistry.createRegistry(7878);
			
			context.bind(URL + "studentDAO", studentDAO);
			context.bind(URL + "courseDAO", courseDAO);
			context.bind(URL + "departmentDAO", departmentDAO);
			
			System.out.println("Server is running..."	);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
