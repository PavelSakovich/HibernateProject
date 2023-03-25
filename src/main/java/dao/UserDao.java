package dao;

import lombok.extern.java.Log;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

@Log
public class UserDao {

    public void createTable() {
        try {
            HibernateUtil.getSessionFactory();
            log.info("---Таблица создана---");
        } catch (HibernateException e) {
            log.info("---Error---");
        }
    }

    public User getUserById(int id) {
        try {
            return HibernateUtil.getSessionFactory().openSession().get(User.class, id);
        } catch (HibernateException e) {
            log.info("---Error---");
            return new User();
        }
    }

    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User ", User.class);
            log.info("---Пользователь получен---");
            return query.list();
        } catch (HibernateException e) {
            log.info("---Error---");
            return new ArrayList<>();
        }
    }


    public void addUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            log.info("---Пользователь добавлен---");
        } catch (HibernateException e) {
            log.info("---Error---");
        }
    }

    public void updateUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            log.info("---Пользователь обновлен---");
        } catch (HibernateException e) {
            log.info("---Error---");
        }
    }

    public void deleteUserById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
            log.info("---Пользователь удален---");
        } catch (HibernateException e) {
            log.info("---Error---");
        }
    }
    public void deleteUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
            log.info("---Пользователь удален---");
        } catch (HibernateException e) {
            log.info("---Error---");
        }
    }

    public void deleteAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<User> query = session.createQuery("FROM User", User.class);
            for (User user : query.list()) {
                session.remove(user);
            }
            transaction.commit();
            log.info("---Все пользователи удалены---");
        } catch (HibernateException e) {
            log.info("---Error---");
        }
    }
}
