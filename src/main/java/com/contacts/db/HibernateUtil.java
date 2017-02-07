package com.contacts.db;

import com.contacts.model.Contact;
import com.contacts.model.User;
import com.contacts.model.UserSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.AnnotationConfiguration;

import javax.servlet.http.HttpServletRequest;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new AnnotationConfiguration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

    public static User getLoggedUser(HttpServletRequest request) {
        Session session = getSessionFactory().openSession();
        Integer userSessionId = (Integer)request.getSession().getAttribute("loggedIn");
        Query query = session.createQuery("from UserSession where sessionId = :id");
        query.setParameter("id", userSessionId);
        UserSession userSession = (UserSession) query.uniqueResult();
        User user = userSession.getUser();
        shutdown();

        return user;
    }



    public static void save(Object object) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        HibernateUtil.shutdown();
    }

    public static void removeContact(Integer id) {
        Session session = getSessionFactory().openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("delete Contact where id = :contact_id");
        query.setParameter("contact_id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        shutdown();
    }

    public static Contact getContact(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Contact where id = :contact_id");
        query.setParameter("contact_id", id);
        Contact contact = (Contact)query.uniqueResult();
        if (contact == null)
            contact = new Contact();
        HibernateUtil.shutdown();

        return contact;
    }

    public static void editContact(Contact contact) {
        Session session = getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(contact);
        session.getTransaction().commit();
        shutdown();
    }

    public static User checkUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(String.format("from User where login='%s' and password='%s'",user.getLogin(), user.getPassword()));
        User userFromDB = (User) query.uniqueResult();

        return userFromDB;
    }
}
