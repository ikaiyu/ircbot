package pl.quider.standalone.irc.dbsession;

import com.fasterxml.classmate.AnnotationConfiguration;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import pl.quider.standalone.irc.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Adrian on 27.09.2016.
 */
public class MySqlDatabaseSession extends ADatabaseSession {

    protected MySqlDatabaseSession() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(ChannelHistory.class);
        configuration.addAnnotatedClass(Message.class);
        configuration.addAnnotatedClass(Channel.class);
        configuration.addAnnotatedClass(Rss.class);
        configuration.addAnnotatedClass(RssEntry.class);

        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());

        if (session == null) {
            session = sessionFactory.openSession();
        }
    }

    public static ADatabaseSession create() throws HibernateException {

        if (instance == null) {
            instance = new MySqlDatabaseSession();
        }
        return instance;
    }


}
