package pl.quider.standalone.irc.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.quider.standalone.irc.model.Channel;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.model.User;

/**
 * Created by Adrian on 02.10.2016.
 */
public class ChannelService {

    /**
     * Database session.
     */
    private final Session session;

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
        Query<Channel> query = session.createQuery("from Channel as c where c.user = :user and channelName = :name", Channel.class);
        query.setParameter("user", user);
        query.setParameter("name", channel);
        Channel singleResult = query.getSingleResult();
        int wordCount = singleResult.getWordCount();
        int countNewWords = getCountWordsFromMessage(msg);
        singleResult.setWordCount(wordCount + countNewWords);
        session.save(singleResult);
        transaction.commit();
    }

    /**
     * Counts words which are in message sent by user.
     * @param msg message object.
     * @return count of words in message.
     */
    private int getCountWordsFromMessage(Message msg) {
        String[] split = msg.getMessage().split(" ");
        return split.length;
    }
}
