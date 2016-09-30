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

    public MessageService(Message msg, MyBot myBot, Session session) {
        this.mybot = myBot;
        this.msg = msg;
        this.session = session;
    }

    public void executeCommand(final MyBot bot) {
        this.saveMessage();
        if (isUserCallingMe()) {
            try {
                Pattern pattern = Pattern.compile("^((!bot) ([a-z]+) (.+))$");
                Matcher matcher = pattern.matcher(msg.getMessage());
                String verb = matcher.group(3);
                String parameter = matcher.group(4);
                verb = verb.substring(0, 1).toUpperCase() + verb.substring(1).toLowerCase();
                Class<Verb> aClass = (Class<Verb>) Class.forName("pl.quider.standalone.irc.verbs." + verb);

                Constructor<Verb> constructor = aClass.getConstructor(MyBot.class);
                Verb verbInstance = constructor.newInstance(bot);
                verbInstance.execute(parameter);

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

    private void saveMessage() {
        try {
            Transaction transaction = session.beginTransaction();
            session.save(msg);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * checks if user call bot
     *
     * @return
     */
    public boolean isUserCallingMe() {
        return this.msg.getMessage().startsWith("!bot ");
    }

}
