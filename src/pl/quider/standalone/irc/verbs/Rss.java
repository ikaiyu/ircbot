package pl.quider.standalone.irc.verbs;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.services.RssService;

import java.net.URL;
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
        //TODO: check if parameters are null and run all links
        RssService rssResvice = new RssService(bot.getSession());
        List<pl.quider.standalone.irc.model.Rss> feeds = rssResvice.getListOfFeeds();
        SyndFeedInput input = new SyndFeedInput();
        //TODO: streamloop of channels we are in and stream all feeds. Multithreading?
        for (pl.quider.standalone.irc.model.Rss rss : feeds) {
            SyndFeed feed = input.build(new XmlReader(new URL(rss.getUri())));
            for (Object syndEntry : feed.getEntries()) {
                SyndEntryImpl entry = (SyndEntryImpl) syndEntry;
                entry.getTitle();
                entry.getUri();

            }
        }


    }
}
