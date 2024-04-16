package dao.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import dao.DepartmentDAO;
import entity.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class DepartmentImpl extends UnicastRemoteObject implements DepartmentDAO{
	
/**
	 * 
	 */
	private static final long serialVersionUID = -6262287994424311129L;
private EntityManager em;
	
	public DepartmentImpl()  throws RemoteException{
		em = Persistence.createEntityManagerFactory("jpa-mssql")
				.createEntityManager();
	}

	@Override
	public List<Department> findDepartmentNotOwnerCourse() throws RemoteException{
		return em.createNamedQuery("Department.findDepartmentNotOwnerCourse", Department.class).getResultList();
	}

}
