package pl.quider.standalone.irc.dbsession;

import com.fasterxml.classmate.AnnotationConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import pl.quider.standalone.irc.model.Channel;
import pl.quider.standalone.irc.model.ChannelHistory;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Adrian on 27.09.2016.
 */
public class MySqlDatabaseSession extends ADatabaseSession {

    protected MySqlDatabaseSession(){
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(ChannelHistory.class);
            configuration.addAnnotatedClass(Message.class);
            configuration.addAnnotatedClass(Channel.class);

            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());

            if(session==null) {
                session = sessionFactory.openSession();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ADatabaseSession create() {

        if (instance == null) {
            instance = new MySqlDatabaseSession();
        }
        return instance;
    }


}
