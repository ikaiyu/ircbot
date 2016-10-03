package pl.quider.standalone.irc.verbs;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.model.User;
import pl.quider.standalone.irc.services.ChannelService;
import pl.quider.standalone.irc.services.UserService;

/**
 * Created by Adrian on 01.10.2016.
 */
public class Stats extends Verb {
    public Stats(MyBot myBot, Message msg) {
        super(myBot, msg);
    }

    @Override
    public void execute(String parameter) {
        Session session = bot.getSession();
        ChannelService channelService = new ChannelService(session);
        String stats = channelService.getStats(parameter, msg);
        this.bot.sendMessage(msg.getChannel(), stats);
    }
}
