package pl.quider.standalone.irc.services;

import jdk.nashorn.internal.runtime.regexp.RegExp;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.verbs.Verb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Adrian on 29.09.2016.
 */
public class MessageService {

    private MyBot mybot;
    private Message msg;
    private Session session;

    /**
     * Creates object with parameters
     * @param msg
     * @param myBot
     * @param session
     */
    public MessageService(Message msg, MyBot myBot, Session session) {
        this.mybot = myBot;
        this.msg = msg;
        this.session = session;
    }

    /**
     * Executes verbs which are in message. For example when user write:
     * !bot join #channel
     * then Join verb class is being called.
     * @param bot Bot object class.
     */
    public void executeVerb(final MyBot bot) {
        this.saveMessage();
        if (isUserCallingMe()) {
            if (!msg.getUser().getLogin().equals("quider"))
                return;
            try {
                String[] split = msg.getMessage().split(" ");
                String verb = split[1];

                StringBuilder parameter = new StringBuilder();
                if (split.length >= 3){
                    for(int i = 2; i<= split.length -1; i++) {
                        parameter.append(split[i]).append(MyBot.VERB_PARAM_DELIMITER);
                    }
                }

                verb = verb.substring(0, 1).toUpperCase() + verb.substring(1).toLowerCase();
                Class<Verb> aClass = (Class<Verb>) Class.forName("pl.quider.standalone.irc.verbs." + verb);

                Constructor<Verb> constructor = aClass.getConstructor(MyBot.class, Message.class);
                Verb verbInstance = constructor.newInstance(bot, msg);
                verbInstance.execute(parameter.toString());

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Saves message to database.
     */
    private void saveMessage() {
        try {
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
            }
            session.save(msg);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * checks if user call bot
     *
     * @return true or false
     */
    public boolean isUserCallingMe() {
        return this.msg.getMessage().startsWith("!bot ");
    }

}
