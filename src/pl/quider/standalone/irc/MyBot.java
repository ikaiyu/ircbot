package pl.quider.standalone.irc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import pl.quider.standalone.irc.dbsession.ADatabaseSession;
import pl.quider.standalone.irc.exceptions.NoLoginException;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.protocol.PircBot;
import pl.quider.standalone.irc.protocol.ReplyConstants;
import pl.quider.standalone.irc.protocol.User;
import pl.quider.standalone.irc.services.ChannelService;
import pl.quider.standalone.irc.services.MessageService;
import pl.quider.standalone.irc.services.UserService;
import pl.quider.standalone.irc.verbs.Op;
import pl.quider.standalone.irc.verbs.Rss;
import pl.quider.standalone.irc.verbs.Verb;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Adrian on 27.09.2016.
 */
public class MyBot extends PircBot {
    private static final Logger LOG = LogManager.getLogger("Mybot");
    public static final String VERB_PARAM_DELIMITER = "&%%&";

    boolean isOp;
    private Session session;
    private Verb verb;
    private Timer timer;


    public MyBot(ADatabaseSession session) {
        super();
        Configuration instance = Configuration.getInstance();
        this.setProperties(instance);
        this.session = session.getSession();
        timer = new Timer();
        if(instance.getValue(ConfigurationKeysContants.RSS_ENABLED).equals("1")) {
            LOG.debug("Config: "+ConfigurationKeysContants.RSS_ENABLED+"="+instance.getValue(ConfigurationKeysContants.RSS_ENABLED));
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        Rss rss = new Rss(MyBot.this, null);
                        rss.execute(null);
                    } catch (Exception e) {
                        LOG.error(e.getMessage(), e);
                    }
                }
            }, 120000,120000);
        }
    }

    protected void setProperties(Configuration instance) {
        this.setName(instance.getValue(ConfigurationKeysContants.LOGIN));
        this.setNick(instance.getValue(ConfigurationKeysContants.NICK));
        this.setLogin(instance.getValue(ConfigurationKeysContants.LOGIN));
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
            Configuration instance = Configuration.getInstance();
            this.connect(instance.getValue(ConfigurationKeysContants.SERVER1));
            ChannelService channelService = new ChannelService(session);
            List list = channelService.joinChannels();
            list.forEach(item -> {
                LOG.info("Try to join channel "+item);
                this.joinChannel((String) item);
            });
        } catch (Exception e) {
           LOG.error(e.getMessage(), e);
        }
    }

    @Override
    protected void onServerResponse(int code, String response) {
        LOG.debug("server responsed: "+code+ " "+ response);
        switch (code) {
            case ReplyConstants.RPL_WHOISUSER:
                LOG.debug("verb:" +this.verb.getClass().getName());
                if (this.verb != null) {
                    try {
                        this.verb.execute(response);
                    } catch (Exception e) {
                        LOG.error(e.getMessage(), e);
                    }
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

        } catch (Exception e) {
           LOG.error(e.getMessage(), e);
        }

    }

    @Override
    protected void onJoin(String channel, String sender, String login, String hostname) {
        UserService userService = new UserService(sender, login, hostname, this.session);
        try {
            userService.joined(channel);
            pl.quider.standalone.irc.model.User user = userService.getUser();
            if (user.isOp()) {
                this.op(channel, user.getNickName());
            }
        } catch (NoLoginException e) {
            LOG.error(e.getMessage(), e);
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
     *
     * @return hibernate session
     */
    public Session getSession() {
        return session;
    }
}
