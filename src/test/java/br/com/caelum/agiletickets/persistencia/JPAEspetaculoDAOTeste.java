package br.com.caelum.agiletickets.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JPAEspetaculoDAOTeste {

	private static EntityManagerFactory emf;
	private static EntityManager entityManager;
	private static EntityTransaction transaction;
	
	@BeforeClass
	public static void beforeClass() {
		
		emf = Persistence.createEntityManagerFactory("teste");
		entityManager = emf.createEntityManager();
		
		transaction = entityManager.getTransaction();
	}
	
	@AfterClass
	public static void afterClass() {
		
		transaction.rollback();
		
	}
	
	@Test
	public void testaConsulta() throws Exception {
		
		transaction.begin();
		
	}
	
}
