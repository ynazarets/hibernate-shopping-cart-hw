package mate.academy.dao.impl;

import com.mysql.cj.xdevapi.SessionFactory;
import mate.academy.dao.TicketDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Ticket;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class TicketDaoImpl implements TicketDao {

    @Override
    public Ticket add(Ticket ticket) {
        SessionFactory sessionFactory = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(ticket);
            transaction.commit();
            if (transaction != null) {
                transaction.rollback();
            }
        } catch (Exception e) {
            throw new DataProcessingException("Cannot save ticket", e);
        }
        return ticket;
    }
}
