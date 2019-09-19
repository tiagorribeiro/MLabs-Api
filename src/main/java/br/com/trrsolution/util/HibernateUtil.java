package br.com.trrsolution.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.trrsolution.model.ClientEntity;
import br.com.trrsolution.model.ProductEntity;

public class HibernateUtil {
	private static final SessionFactory factory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		
		Configuration configuration = new Configuration();

		configuration.addAnnotatedClass(ClientEntity.class);
		configuration.addAnnotatedClass(ProductEntity.class);
		configuration.configure();
		
		return configuration.buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory(){
		return factory;
	}
}
