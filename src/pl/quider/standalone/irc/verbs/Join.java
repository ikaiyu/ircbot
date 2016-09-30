package pl.quider.standalone.irc.verbs;

import pl.quider.standalone.irc.MyBot;

/**
 * Created by Adrian on 30.09.2016.
 */
public class Join extends Verb {

    public Join(MyBot mybot) {
        super(mybot);
    }

    @Override
    public void execute(String parameter) {
        if(parameter.startsWith("#"))
        this.bot.joinChannel(parameter);
    }
}
