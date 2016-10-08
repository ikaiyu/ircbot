package pl.quider.standalone.irc.verbs;

import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;

/**
 * Created by Adrian on 30.09.2016.
 */
public class Join extends Verb {

    public Join(MyBot mybot, Message msg) {
        super(mybot, msg);
    }

    @Override
    public void execute(String parameter) {
        String[] split = parameter.split(MyBot.VERB_PARAM_DELIMITER);
        for (String channel : split) {
            if (channel.startsWith("#")) {
                this.bot.joinChannel(channel);
            } else {
                this.bot.joinChannel("#"+channel);
            }
        }
    }
}
