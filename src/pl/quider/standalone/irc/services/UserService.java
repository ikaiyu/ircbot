package pl.quider.standalone.irc.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.query.Query;
import pl.quider.standalone.irc.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Adrian on 29.09.2016.
 */
public class UserService {

    private static String QUERY = "from pl.quider.standalone.irc.model.User as u where u.mask = :host and u.login = :login";

    private String nick;
    private String login;
    private String host;
    private Session session;
    private User user;

    public UserService(String sender, String login, String hostname, Session session) {
        this.nick =sender;
        this.login= login;
        this.host=hostname;
        this.session=session;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        if(!this.userExists()){
            this.createNewUser();
        }
        return this.fetchUser();
    }

    private User fetchUser() {
        return user;
    }

    /**
     *
     */
    private void createNewUser() {
        try {
            User user = new User();
            user.setNick(getNick());
            user.setMask(getHost());
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    private boolean userExists() {
        Query query = session.createQuery(QUERY);
        query.setParameter("host", getHost());
        query.setParameter("login", getLogin());
        List<User> resultList = query.getResultList();
        if(resultList.size()>0) {
            this.user = resultList.get(0);
        }
        return user!=null;
    }

    public String getNick() {
        return nick;
    }

    public String getLogin() {
        return login;
    }

    public String getHost() {
        return host;
    }

    /**
     *
     * @param channel
     */
    public void joined(String channel) {
        User user = this.getUser();
        this.userPresent(user);
    }

    /**
     *
     * @param user
     */
    public void userPresent(User user) {
        try {
            Transaction transaction = session.beginTransaction();
            user.setLastSeen(new Date());
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
