package dao;

import exception.MyTransactionException;
import lombok.extern.slf4j.Slf4j;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserDao {
    private Transaction transaction;

    public void createTable() throws MyTransactionException { //Создание таблицы пользователей и таблицы адресов пользователей

        try {
            HibernateUtil.getSessionFactory();
            log.info("---Таблица создана---");
        } catch (HibernateException e) {
            log.error("---Error---");
            throw new MyTransactionException("Транзакция не выполнена");
        }
    }

    public User getUserById(int id) throws MyTransactionException {
        try {
            return HibernateUtil.getSessionFactory().openSession().get(User.class, id);
        } catch (HibernateException e) {
            log.error("---Error---");
            throw new MyTransactionException("Транзакция не выполнена");
        }
    }

    public List<User> getAllUsers() throws MyTransactionException {
        List<User> userList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User ", User.class);
            log.info("---Пользователь получен---");
            userList = query.list();
            return userList;
        } catch (HibernateException e) {
            log.error("---Error---");
            throw new MyTransactionException("Транзакция не выполнена");
        }
    }


    public void addUser(User user) throws MyTransactionException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            log.info("---Пользователь добавлен---");
        } catch (HibernateException e) {
            transaction.rollback();
            log.error("---Error---");
            throw new MyTransactionException("Транзакция не выполнена");
        }
    }

    public void updateUser(User user) throws MyTransactionException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            log.info("---Пользователь обновлен---");
        } catch (HibernateException e) {
            transaction.rollback();
            log.error("---Error---");
            throw new MyTransactionException("Транзакция не выполнена");
        }
    }

    public void deleteUserById(int id) throws MyTransactionException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
            log.info("---Пользователь удален---");
        } catch (HibernateException e) {
            transaction.rollback();
            log.error("---Error---");
            throw new MyTransactionException("Транзакция не выполнена");
        }
    }

    public void deleteUser(User user) throws MyTransactionException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
            log.info("---Пользователь удален---");
        } catch (HibernateException e) {
            transaction.rollback();
            log.error("---Error---");
            throw new MyTransactionException("Транзакция не выполнена");
        }
    }

    public void deleteAllUsers() throws MyTransactionException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
            log.info("---Все пользователи удалены---");
        } catch (HibernateException e) {
            transaction.rollback();
            log.error("---Error---");
            throw new MyTransactionException("Транзакция не выполнена");
        }
    }

    public List<User> getUsersByNumberHouse(int house) throws MyTransactionException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("select user from UserAddress where house = :param", User.class);
            query.setParameter("param", house);
            log.info("---Список пользователей получен---");
            return query.list();
        } catch (HibernateException e) {
            log.error("---Error---");
            throw new MyTransactionException("Транзакция не выполнена");
        }
    }

    public void deleteUserWithAddressById(int id) throws MyTransactionException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
            log.info("---Пользователь c адресом удален---");
        } catch (HibernateException e) {
            transaction.rollback();
            log.error("---Error---");
            throw new MyTransactionException("Транзакция не выполнена");
        }
    }

}

