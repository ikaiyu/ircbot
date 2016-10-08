package pl.quider.standalone.irc;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.quider.standalone.irc.protocol.IrcException;
import pl.quider.standalone.irc.protocol.PircBot;
import pl.quider.standalone.irc.protocol.User;
import pl.quider.standalone.irc.dbsession.ADatabaseSession;
import pl.quider.standalone.irc.dbsession.MySqlDatabaseSession;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.services.ChannelService;
import pl.quider.standalone.irc.services.MessageService;
import pl.quider.standalone.irc.services.UserService;
import pl.quider.standalone.irc.verbs.Op;
import pl.quider.standalone.irc.verbs.Verb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.util.List;

/**
 * Created by Adrian on 27.09.2016.
 */
public class MyBot extends PircBot {

    public static final String VERB_PARAM_DELIMITER = "&%%&";
    boolean isOp;
    private Session session;
    private Verb verb;


    public MyBot(ADatabaseSession session) {
        super();
        this.session = session.getSession();

    }

    @Override
    public void setAutoNickChange(boolean autoNickChange) {
        super.setAutoNickChange(autoNickChange);
    }

    @Override
    protected void onConnect() {

    }

    @Override
    protected void onDisconnect() {
        try {
            this.connect("open.ircnet.net");
            ChannelService channelService = new ChannelService(session);
            List list = channelService.joinChannels();
            list.forEach(item -> {
                this.joinChannel((String) item);
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IrcException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onServerResponse(int code, String response) {
        switch (code) {
            case 311:
                if (this.verb != null) {
                    this.verb.execute(response);
                    this.verb = null;
                }
                break;
        }
    }

    @Override
    protected void onUserList(String channel, User[] users) {
        for (User usr : users) {
            String nick = usr.getNick();
            if (usr.isOp()) {
                this.opNick(nick);
            }
        }
    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        try {
            UserService userService = new UserService(sender, login, hostname, session);
            pl.quider.standalone.irc.model.User user = userService.getUser();
            userService.userPresent(user);
            userService.updateStats(message);

            Message msg = new Message(channel, sender, user, message);
            MessageService messageService = new MessageService(msg, this, this.session);
            messageService.executeVerb(this);
            ChannelService channelService = new ChannelService(this.session);
            channelService.updateStats(user, msg);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onJoin(String channel, String sender, String login, String hostname) {
        UserService userService = new UserService(sender, login, hostname, this.session);
        userService.joined(channel);
        pl.quider.standalone.irc.model.User user = userService.getUser();
        if (user.isOp()) {
            this.op(channel, user.getNickName());
        }
    }

    @Override
    protected void onTopic(String channel, String topic, String setBy, long date, boolean changed) {
        super.onTopic(channel, topic, setBy, date, changed);
    }

    @Override
    protected void onOp(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
        this.opNick(recipient);
    }

    /**
     * Sends WHOIS and after response finds a user in
     * database and op it
     *
     * @param recipient
     */
    private void opNick(String recipient) {
        this.verb = new Op(this);
        sendRawLine("WHOIS " + recipient);
    }

    @Override
    protected void onDeop(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
        super.onDeop(channel, sourceNick, sourceLogin, sourceHostname, recipient);
    }

    /**
     * Getter of session object
     * @return hibernate session
     */
    public Session getSession() {
        return session;
    }
}
