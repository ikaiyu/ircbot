package pl.quider.standalone.irc.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Channel;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.model.User;

import java.util.List;

/**
 * Created by Adrian on 02.10.2016.
 */
public class ChannelService {

    /**
     * Database session.
     */
    private final Session session;
    public static final String HQL = "from Channel as c where c.user = :user and c.channelName = :channel";
    public static final String STATS_HQL = "from pl.quider.standalone.irc.model.Channel as c where c.user = :user and channelName = :name";

    /**
     * Constructon sets database session.
     * @param session
     */
    public ChannelService(Session session) {
        this.session = session;
    }

    /**
     * Updates statistics of user in channel. Transaction included.
     * @param user user which sent message.
     * @param msg message which has been sent.
     */
    public void updateStats(final User user, final Message msg) {
            String channel = msg.getChannel();
            Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery(STATS_HQL);
            query.setParameter("user", user);
            query.setParameter("name", channel);
            List<Channel> resultList = query.getResultList();
                int countNewWords = getCountWordsFromMessage(msg);
            if(resultList.size()>0) {
                Channel singleResult = (Channel) query.getSingleResult();
                int wordCount = singleResult.getWordCount();
                singleResult.setWordCount(wordCount + countNewWords);
                session.save(singleResult);
                transaction.commit();
            } else {
                Channel chanInfo = new Channel();
                chanInfo.setWordCount(countNewWords);
                chanInfo.setChannelName(msg.getChannel());
                chanInfo.setUser(user);
                session.save(chanInfo);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    /**
     * Counts words which are in message sent by user.
     * @param msg message object.
     * @return count of words in message.
     */
    protected int getCountWordsFromMessage(Message msg) {
        String[] split = msg.getMessage().split(" ");
        return split.length;
    }

    /**
     *
     * @param parameter
     * @param msg
     * @return
     */
    public String getStats(String parameter, Message msg) {
        try {
            Query query = session.createQuery(HQL);
            query.setParameter("user",msg.getUser());
            query.setParameter("channel", msg.getChannel());
            Channel singleResult = (Channel) query.getSingleResult();
            return new StringBuilder("Masz napisanych ").append(singleResult.getWordCount()).append(" słów.").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Błąd odczytu! Następnym razem.";
        }
    }

    /**
     * fetches to all channels which are mentioned in table channel
     * @return list of channel names
     */
    public List joinChannels() throws Exception{
        Query query = session.createQuery("select c.channelName from Channel as c group by c.channelName");
        List resultList = query.getResultList();
        return resultList;
    }

    /**
     * Gets stats from channel
     * @param channel
     * @return
     * @throws Exception
     */
    public List<Channel> getTopStats(String channel) throws Exception {
        Query query = session.createQuery("from Channel as c where channelName = :channel order by wordCount desc").setMaxResults(5);
        query.setParameter("channel", channel);
        List resultList = query.getResultList();
        return resultList;
    }
}
