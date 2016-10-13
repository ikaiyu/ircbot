package pl.quider.standalone.irc.filters;

import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;

import java.util.function.Consumer;

/**
 * Created by Adrian.Kozlowski on 2016-10-13.
 */
public abstract class Filter{
    protected  Message msg;
    protected  MyBot bot;

    public Filter(Message msg, MyBot mybot) {
        this.bot = mybot;
        this.msg = msg;
    }

    public abstract void execute();
}
