package pl.quider.standalone.irc.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.quider.standalone.irc.exceptions.NoLoginException;
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

    /**
     * Creates new object of class.
     * @param sender
     * @param login
     * @param hostname
     * @param session
     */
    public UserService(String sender, String login, String hostname, Session session) {
        this.nick =sender;
        this.login= login;
        this.host=hostname;
        this.session=session;
    }

     /**
     * Gets user from database or creates new.
     * @return
     */
    public User getUser() throws NoLoginException {
        if(this.getLogin() == null ||this.getLogin().isEmpty()){
            throw new NoLoginException();
        }
        if(!this.userExists()){
            this.createNewUser();
        }
        return this.fetchUser();
    }

    private User fetchUser() {
        return user;
    }

    /**
     * Creates new user in database and sets it to propery.
     */
    public void createNewUser() {
        try {
            user = new User();
            user.setNickName(getNick());
            user.setLogin(getLogin());
            user.setMask(getHost());
            Transaction transaction = session.getTransaction();
            if(transaction == null || !transaction.isActive())
                transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Checks if user exists in database.
     * @return user object if exists. Otherwise null.
     */
    public boolean userExists() {
        Query query = session.createQuery(QUERY);
        query.setParameter("host", getHost());
        query.setParameter("login", getLogin());
        List<User> resultList = query.getResultList();
        if(resultList.size()>0) {
            this.user = resultList.get(0);
        }
        return user!=null;
    }

    /**
     * Gets nick which was given in constructor
     */
    public String getNick() {
        return nick;
    }

    /**
     * Gets login whcih was given in constructor
     */
    public String getLogin() {
        return login;
    }

    /**
     * Gets host whcih was given in constructor
     */
    public String getHost() {
        return host;
    }

    /**
     * Actions which supposed to be performed when someone joins to channel
     * like set presence
     * @param channel String channel name i.e: #channel
     */
    public void joined(String channel) throws NoLoginException {
        User user = this.getUser();
        this.userPresent(user);
    }

    /**
     * Sets latest presence of user 
     * @param user
     */
    public void userPresent(User user) {
        try {

            Transaction transaction = session.getTransaction();
            if(transaction== null || !transaction.isActive()){
                transaction = session.beginTransaction();
            }
            user.setLastSeen(new Date());
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void opUser() throws NoLoginException {
        Transaction transaction = session.getTransaction();
        if(!transaction.isActive()){
            transaction.begin();
        }
        getUser();
        this.user.setOp(true);
        session.save(this.user);
        transaction.commit();
    }

    public Date seen(String nickName){
        Query query = session.createQuery("from pl.quider.standalone.irc.model.User as u where u.nick = :nick order by lastSeen desc");
        query.setParameter("nick", nickName);
        return null;
    }

    public void getStats() {

    }

    public void updateStats(String message) {

    }
}
