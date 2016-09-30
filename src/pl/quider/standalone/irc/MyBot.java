package pl.quider.standalone.irc;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;
import pl.quider.standalone.irc.dbsession.ADatabaseSession;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.services.MessageService;
import pl.quider.standalone.irc.services.UserService;
import pl.quider.standalone.irc.verbs.Op;
import pl.quider.standalone.irc.verbs.Verb;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;

/**
 * Created by Adrian on 27.09.2016.
 */
public class MyBot extends PircBot {

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
        super.onConnect();
    }

    @Override
    protected void onDisconnect() {
        super.onDisconnect();
    }

    @Override
    protected void onServerResponse(int code, String response) {
        switch (code){
            case 311 :
                if(this.verb != null) {
                    this.verb.execute(response);
                    this.verb = null;
                }
                break;
        }
    }

    @Override
    protected void onUserList(String channel, User[] users) {
        super.onUserList(channel, users);
    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        Transaction transaction = session.getTransaction();
        if(!transaction.isActive())
            transaction = session.beginTransaction();
        super.onMessage(channel, sender, login, hostname, message);
        UserService userService = new UserService(sender,login,hostname, session);
        pl.quider.standalone.irc.model.User user = userService.getUser();
        userService.userPresent(user);
        Message msg = new Message(channel, sender, user, message);
        MessageService messageService = new MessageService(msg,this, this.session);
        messageService.executeCommand(this);
        transaction.commit();
    }

    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message) {
        super.onPrivateMessage(sender, login, hostname, message);
    }

    @Override
    protected void onAction(String sender, String login, String hostname, String target, String action) {
        super.onAction(sender, login, hostname, target, action);
    }

    @Override
    protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) {
        super.onNotice(sourceNick, sourceLogin, sourceHostname, target, notice);
    }

    @Override
    protected void onJoin(String channel, String sender, String login, String hostname) {
        UserService userService = new UserService(sender, login, hostname, this.session);
        userService.joined(channel);
        pl.quider.standalone.irc.model.User user = userService.getUser();
        if(user.isOp()){
            this.op(channel, user.getNickName());
        }
    }

    @Override
    protected void onPart(String channel, String sender, String login, String hostname) {
        super.onPart(channel, sender, login, hostname);
    }

    @Override
    protected void onNickChange(String oldNick, String login, String hostname, String newNick) {
        super.onNickChange(oldNick, login, hostname, newNick);
    }

    @Override
    protected void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
        super.onKick(channel, kickerNick, kickerLogin, kickerHostname, recipientNick, reason);
    }

    @Override
    protected void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason) {
        super.onQuit(sourceNick, sourceLogin, sourceHostname, reason);
    }

    @Override
    protected void onTopic(String channel, String topic) {
        super.onTopic(channel, topic);
    }

    @Override
    protected void onTopic(String channel, String topic, String setBy, long date, boolean changed) {
        super.onTopic(channel, topic, setBy, date, changed);
    }

    @Override
    protected void onChannelInfo(String channel, int userCount, String topic) {
        super.onChannelInfo(channel, userCount, topic);
    }

    @Override
    protected void onMode(String channel, String sourceNick, String sourceLogin, String sourceHostname, String mode) {
        super.onMode(channel, sourceNick, sourceLogin, sourceHostname, mode);
    }

    @Override
    protected void onUserMode(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String mode) {
        super.onUserMode(targetNick, sourceNick, sourceLogin, sourceHostname, mode);
    }

    @Override
    protected void onOp(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
        this.verb = new Op(this);
        sendRawLine("WHOIS "+recipient);
    }

    @Override
    protected void onDeop(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
        super.onDeop(channel, sourceNick, sourceLogin, sourceHostname, recipient);
    }

    @Override
    public String getNick() {
        return super.getNick();
    }

    @Override
    public int[] longToIp(long address) {
        return super.longToIp(address);
    }

    @Override
    public long ipToLong(byte[] address) {
        return super.ipToLong(address);
    }

    @Override
    public void setEncoding(String charset) throws UnsupportedEncodingException {
        super.setEncoding(charset);
    }

    @Override
    public String getEncoding() {
        return super.getEncoding();
    }

    @Override
    public InetAddress getInetAddress() {
        return super.getInetAddress();
    }

    @Override
    public void setDccInetAddress(InetAddress dccInetAddress) {
        super.setDccInetAddress(dccInetAddress);
    }

    @Override
    public InetAddress getDccInetAddress() {
        return super.getDccInetAddress();
    }

    @Override
    public int[] getDccPorts() {
        return super.getDccPorts();
    }

    @Override
    public void setDccPorts(int[] ports) {
        super.setDccPorts(ports);
    }

    public Session getSession() {
        return session;
    }
}
