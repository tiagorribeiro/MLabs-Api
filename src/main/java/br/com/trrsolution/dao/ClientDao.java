package br.com.trrsolution.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.trrsolution.model.ClientEntity;
import br.com.trrsolution.util.HibernateUtil;

public class ClientDao {

	/**
	 * Metodo que Salva o cliente na base de dados.
	 * 
	 * @param ClientEntity Object a ser salvo com Nome e Email. 
	 * @return Object ClientEntity salvo.
	 */
	public ClientEntity save(ClientEntity client) {

		Session session;
		Transaction transaction = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.save(client);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

		return client;
	}
	
	
	/**
	 * Metodo que busca o cliente na base de dados por EMAIL
	 * 
	 * @param ClientEntity Object com Nome e Email. 
	 * @return List<ClientEntity> Objects salvos baseados no email..
	 */
	public List<ClientEntity> findClient(ClientEntity client) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			return session.createQuery("from ClientEntity c where c.email like '" + client.getEmail() + "'",
					ClientEntity.class).getResultList();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return new ArrayList<ClientEntity>();
	}
	
	/**
	 * Metodo que deleta o cliente na base de dados.
	 * 
	 * @param ClientEntity Object com Nome e Email. 
	 * 
	 */
	public void delete(ClientEntity client) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.getTransaction();
			tx.begin();
			session.delete(client);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	/**
	 * Metodo que altera o cliente na base de dados.
	 * 
	 * @param ClientEntity Object com Nome e Email. 
	 */
	public void update(ClientEntity client) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			session.update(client);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
