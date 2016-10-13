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
    public void execute(String parameter) throws Exception {
        ChannelService channelService = new ChannelService(bot.getSession());
        List<Channel> topStats = channelService.getTopStats(msg.getChannel());
        for (int i = 0; i <= 4; i++) {
            Channel channel = topStats.get(i);
            this.write(prepareLine(i,channel));
        }
        //TODO: where asking person is in table
    }

    /**
     *
     * @param i
     * @param channel
     * @return
     */
    protected String prepareLine(int i, Channel channel) {
        StringBuilder stringStat = new StringBuilder(Integer.toString(i + 1)).append(". ");
        stringStat.append(channel.getUser().getNickName());
        stringStat.append(" - ");
        stringStat.append(channel.getWordCount()).append(" słów.");
        stringStat.append(" (").append(channel.getUser().getLogin()).append("@").append(channel.getUser().getMask()).append(")");
        return  stringStat.toString();
    }

    /**
     *
     * @param stringStat
     */
    protected void write(String stringStat) {
        bot.sendMessage(msg.getChannel(), stringStat);
    }
}
