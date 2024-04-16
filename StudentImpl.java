package dao.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import dao.StudentDAO;
import entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class StudentImpl extends UnicastRemoteObject implements StudentDAO {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 5435564738130132888L;
private EntityManager em;
	
	public StudentImpl() throws RemoteException{
		em = Persistence.createEntityManagerFactory("jpa-mssql")
				.createEntityManager();
	}

	@Override
	public List<Student> findAll() throws RemoteException{
		return em.createNamedQuery("Student.findAll", Student.class).getResultList();
	}

	@Override
	public List<Student> findStudentsEnrolledInCourse(String title) throws RemoteException{
		return em.createNamedQuery("student.findStudentsEnrolledInCourse", Student.class)
				.setParameter("title", "%"+title+"%")
				.getResultList();
	}

	@Override
	public List<Student> findStudentsEnrolledInYear(int year) throws RemoteException{
		return em.createNamedQuery("Student.findByEnrollmentInYear", Student.class)
		.setParameter("year", year)
		.getResultList();
	}

	@Override
	public Map<Student, Double> findStudentGPAs(int year) throws RemoteException{
		
//		Map<Student, Double> map = new HashMap<>();
		Map<Student, Double> map = new LinkedHashMap<>();
		
		String query = "SELECT s.id, AVG(sg.grade) as avg "
				+ "FROM Student s Inner JOIN s.studentGrades sg "
				+ "where year(enrollmentDate) =: year "
				+ "GROUP BY s.id "
				+ "order by avg desc";
		List<?> list = em.createQuery(query)
				.setParameter("year", year)
				.getResultList();
		
		list.stream()
		.map(o -> (Object[])o)
		.forEach(a -> {
			int studentID = (int)a[0];
			double avg = (double)a[1];
			Student student = em.find(Student.class, studentID);
			
			map.put(student, avg);
		});
		
		return map;
	}
	@Override
	public Map<Student, Double> findStudentGPAs2(int year) throws RemoteException{
		
		Map<Student, Double> map = new TreeMap<>(
					Comparator.comparing(Student::getFirstName) //.reversed()
					.thenComparing(Student::getLastName)
				);
		
		String query = "SELECT s.id, AVG(sg.grade) as avg "
				+ "FROM Student s Inner JOIN s.studentGrades sg "
				+ "where year(enrollmentDate) =: year "
				+ "GROUP BY s.id ";
		List<?> list = em.createQuery(query)
				.setParameter("year", year)
				.getResultList();
		
		list.stream()
		.map(o -> (Object[])o)
		.forEach(a -> {
			int studentID = (int)a[0];
			double avg = (double)a[1];
			Student student = em.find(Student.class, studentID);
			
			map.put(student, avg);
		});
		
		return map;
	}

	@Override
	public Map<Student, Double> findStudentMaxGPAs(int year) throws RemoteException{
		
		Map<Student, Double> map = new HashMap<>();
		
		String query = "SELECT s.id, AVG(sg.grade) as avg "
				+ "FROM Student s Inner JOIN s.studentGrades sg "
				+ "where year(enrollmentDate) =: year "
				+ "GROUP BY s.id "
				+ "Having AVG(sg.grade) >= all("
					+ "SELECT AVG(sg.grade) "
					+ "FROM Student s Inner JOIN s.studentGrades sg "
					+ "where year(enrollmentDate) =: year "
					+ "GROUP BY s.id )";
		
		List<?> list = em.createQuery(query)
				.setParameter("year", year)
				.getResultList();
		
		list.stream()
		.map(o -> (Object[])o)
		.forEach(a -> {
			int studentID = (int)a[0];
			double avg = (double)a[1];
			Student student = em.find(Student.class, studentID);
			
			map.put(student, avg);
		});
		
		return map;
	}

	@Override
	public boolean add(Student student)  throws RemoteException{
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(student);
			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return false;
	}

}

