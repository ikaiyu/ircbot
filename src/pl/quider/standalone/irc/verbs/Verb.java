package pl.quider.standalone.irc.verbs;

import pl.quider.standalone.irc.MyBot;

/**
 * Created by Adrian on 30.09.2016.
 */
public abstract class Verb {

    protected final MyBot bot;

    public Verb(MyBot mybot) {
        this.bot = mybot;
    }

    public abstract void execute(String parameter);
}
