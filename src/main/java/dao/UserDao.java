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
    private Transaction transaction;

    public void createTable() { //Создание таблицы пользователей и таблицы адресов пользователей

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
        List<User> userList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User ", User.class);
            log.info("---Пользователь получен---");
            userList = query.list();
            return userList;
        } catch (HibernateException e) {
            log.info("---Error---");
            return userList;
        }
    }


    public void addUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            log.info("---Пользователь добавлен---");
        } catch (HibernateException e) {
            transaction.commit();
            log.info("---Error---");
        }
    }

    public void updateUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            log.info("---Пользователь обновлен---");
        } catch (HibernateException e) {
            transaction.commit();
            log.info("---Error---");
        }
    }

    public void deleteUserById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
            log.info("---Пользователь удален---");
        } catch (HibernateException e) {
            transaction.commit();
            log.info("---Error---");
        }
    }

    public void deleteUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
            log.info("---Пользователь удален---");
        } catch (HibernateException e) {
            transaction.commit();
            log.info("---Error---");
        }
    }

    public void deleteAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
            log.info("---Все пользователи удалены---");
        } catch (HibernateException e) {
            transaction.commit();
            log.info("---Error---");
        }
    }

    public List<User> getUsersByNumberHouse(int house) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("select user from UserAddress where house = :param", User.class);
            query.setParameter("param", house);
            log.info("---Список пользователей получен---");
            return query.list();
        } catch (HibernateException e) {
            log.info("---Error---");
            return new ArrayList<User>();
        }
    }
    public void deleteUserWithAddressById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
            log.info("---Пользователь c адресом удален---");
        } catch (HibernateException e) {
            transaction.commit();
            log.info("---Error---");
        }
    }

}

