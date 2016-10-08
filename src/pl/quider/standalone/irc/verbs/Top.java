package pl.quider.standalone.irc.verbs;

import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Channel;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.services.ChannelService;

import java.util.List;

/**
 * Created by Adrian on 08.10.2016.
 */
public class Top extends Verb {

    public Top(MyBot mybot, Message msg) {
        super(mybot, msg);
    }

    @Override
    public void execute(String parameter) {
        ChannelService channelService = new ChannelService(bot.getSession());
        List<Channel> topStats = channelService.getTopStats();
        for(int i = 0; i <=4;i++) {
            StringBuilder stringStat = new StringBuilder(i + 1).append(". ");
            Channel channel = topStats.get(i);
            stringStat.append(channel.getUser().getNickName());
            stringStat.append(" - ");
            stringStat.append(channel.getWordCount()).append( "słów.");
            bot.sendMessage(msg.getChannel(), stringStat.toString());
        }
        //TODO: where asking person is in table
    }
}
