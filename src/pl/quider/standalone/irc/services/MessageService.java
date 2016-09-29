package pl.quider.standalone.irc.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;

/**
 * Created by Adrian on 29.09.2016.
 */
public class MessageService {

    private MyBot mybot;
    private Message msg;
    private Session session;

    public MessageService(Message msg, MyBot myBot, Session session) {
         this.mybot = myBot;
         this.msg = msg;
         this.session = session;
    }

    public void executeCommand() {
        this.saveMessage();
    }

    private void saveMessage() {
        try {
            Transaction transaction = session.beginTransaction();
            session.save(msg);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
