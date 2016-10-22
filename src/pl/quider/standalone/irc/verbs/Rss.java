package pl.quider.standalone.irc.verbs;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.services.RssService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Adrian on 14.10.2016.
 */
public class Rss extends Verb {

    public Rss(MyBot mybot, Message msg) {
        super(mybot, msg);
    }

    @Override
    public void execute(String parameter) throws Exception {
        if (msg == null && parameter == null) {
            Arrays.stream(bot.getChannels()).sorted().forEach(this::sentToChannels);
        }
    }

    private void sentToChannels(String channel) {
        try {
            SyndFeedInput input = new SyndFeedInput();
            RssService rssResvice = createServiceObject();
            List<pl.quider.standalone.irc.model.Rss> feeds = rssResvice.getListOfFeeds();
            for (pl.quider.standalone.irc.model.Rss rss : feeds) {
                SyndFeed feed = null;
                feed = input.build(new XmlReader(new URL(rss.getUri())));
                for (Object syndEntry : feed.getEntries()) {
                    SyndEntryImpl entry = (SyndEntryImpl) syndEntry;
                    rssResvice.saveEntry(entry.getTitle(),entry.getUri(),rss);
                    this.sendMessage(channel, entry.getUri());
                }
            }
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected RssService createServiceObject(){
        RssService rssResvice = new RssService(bot.getSession());
        return  rssResvice;
    }

    protected void sendMessage(String channel, String uri) {
        bot.sendMessage(channel, uri.toString());
    }
}
