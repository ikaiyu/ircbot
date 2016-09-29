package pl.quider.standalone.irc.dbsession;

import org.hibernate.Session;

import javax.persistence.EntityManager;

/**
 * Created by Adrian on 27.09.2016.
 */
public abstract class ADatabaseSession {
    protected static ADatabaseSession instance = null;
    protected static Session session;
    protected  ADatabaseSession(){

    }

    public Session getSession() {
        return session;
    }

    public static ADatabaseSession create(){
        return null;
    }
}
