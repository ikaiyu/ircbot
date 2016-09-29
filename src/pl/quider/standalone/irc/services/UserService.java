package pl.quider.standalone.irc.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.query.Query;
import pl.quider.standalone.irc.model.User;

import java.util.List;

/**
 * Created by Adrian on 29.09.2016.
 */
public class UserService {

    private static String QUERY = "from pl.quider.standalone.irc.model.User as u where u.mask = :host";

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

    public User getUser() {
        if(!this.userExists()){
            this.createNewUser();
        }

        return this.fetchUser();
    }

    private User fetchUser() {

        return null;
    }

    private void createNewUser() {
        User user = new User();
        user.setNick(getNick());
        user.setMask(getLogin()+"@"+getHost());
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
    }

    private boolean userExists() {
        Query query = session.createQuery(QUERY);
        query.setParameter("host", getHost());
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
}
