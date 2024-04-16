package dao.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import dao.CourseDAO;
import entity.Course;
import entity.OnlineCourse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class CourseImpl extends UnicastRemoteObject implements CourseDAO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2119817685450710146L;
	private EntityManager em;
	
	public CourseImpl() throws RemoteException{
		em = Persistence.createEntityManagerFactory("jpa-mssql")
				.createEntityManager();
	}

	@Override
	public boolean add(Course course) throws RemoteException{
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
			em.persist(course);
			
			tx.commit();
			
			return true;
		}catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean update(Course course) throws RemoteException{
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
			em.merge(course);
			
			tx.commit();
			
			return true;
		}catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean delete(int id) {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Course course = em.find(Course.class, id);
			em.remove(course);
			
			tx.commit();
			
			return true;
		}catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Course findById(int id)  throws RemoteException{
		return em.find(Course.class, id);
	}

	@Override
	public List<Course> findAll() throws RemoteException{
		return em.createNamedQuery("Course.findAll", Course.class).getResultList();
	}

	@Override
	public List<Course> findBytitle(String title) throws RemoteException{
		return em.createNamedQuery("Course.findByTitle", Course.class)
				.setParameter("title", "%"+title+"%")
				.getResultList();
	}

	@Override
	public Course findBytitle2(String title) throws RemoteException{
		return em.createQuery("select c from Course c where c.title = :title", Course.class)
				.setParameter("title", title)
//				.getSingleResult();
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null);
	}

	@Override
	public Map<Course, Long> getCourseAndCountStudent() throws RemoteException{
		
		Map<Course, Long> map = new LinkedHashMap<>();
		
		String query = "select s.course.id, count(*) as n from  StudentGrade s group by s.course.id order by n desc";
		List<?> list = em.createQuery(query).getResultList();
		
		list.stream()
		.map(o -> (Object[]) o)
		.forEach(a -> {
			int courseId = (int) a[0];
			Course course = em.find(Course.class, courseId);
			long count = (long) a[1];
			
			map.put(course, count);
		});
		
		return map;
	}
	
	@Override
	public Map<OnlineCourse, Long> getCourseAndCountStudent2() throws RemoteException{ //Sort by course title
		
		Map<OnlineCourse, Long> map = new TreeMap<>(
					Comparator.comparing(Course::getCredits).reversed()
					.thenComparing(Course::getTitle)
				);
		
		String query = "select s.course.id, count(*) as n from  StudentGrade s group by s.course.id ";
		List<?> list = em.createQuery(query).getResultList();
		
		list.stream()
		.map(o -> (Object[]) o)
		.forEach(a -> {
			int courseId = (int) a[0];
			OnlineCourse course = em.find(OnlineCourse.class, courseId);
			long count = (long) a[1];
			if(course != null)
				map.put(course, count);
		});
		
		return map;
	}

}
