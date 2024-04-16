package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import entity.Course;
import entity.OnlineCourse;

public interface CourseDAO extends Remote{
	public boolean add(Course course) throws RemoteException;
	public boolean update(Course course) throws RemoteException;
//	public boolean delete(int id) ;
	public Course findById(int id) throws RemoteException;
	public List<Course> findAll() throws RemoteException;
	public List<Course> findBytitle(String title) throws RemoteException; //Tim tuong doi
	public Course findBytitle2(String title) throws RemoteException; //Tim tuyet doi
	
	public Map<Course, Long> getCourseAndCountStudent() throws RemoteException;
	public Map<OnlineCourse, Long> getCourseAndCountStudent2() throws RemoteException;
	
}
