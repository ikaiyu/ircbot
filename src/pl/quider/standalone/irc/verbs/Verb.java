package pl.quider.standalone.irc.verbs;

import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;

/**
 * Created by Adrian on 30.09.2016.
 */
public abstract class Verb {

    protected final MyBot bot;
    protected final Message msg;

    public Verb(MyBot mybot, Message msg) {
        this.bot = mybot;
        this.msg = msg;
    }

    public abstract void execute(String parameter);
}
