package pl.quider.standalone.irc.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.quider.standalone.irc.model.Rss;
import pl.quider.standalone.irc.model.RssEntry;

import java.util.Date;
import java.util.List;

/**
 * Created by Adrian on 15.10.2016.
 */
public class RssService {

    private final Session session;

    public RssService(Session session) {
        this.session = session;
    }

    public static final String SELECT_ALL_RSS = "select r from pl.quider.standalone.irc.model.Rss as r";
    public static final String SELECT_RSS_BY_URI = "select r from pl.quider.standalone.irc.model.RssEntry as r where r.uri = :link";

    public List<pl.quider.standalone.irc.model.Rss> getListOfFeeds() {

        Query query = session.createQuery(SELECT_ALL_RSS);
        List resultList = query.getResultList();

        return resultList;
    }

    /**
     * @param title
     * @param uri
     * @param rssParent
     */
    public void saveEntry(String title, String uri, Rss rssParent) {
        Transaction transaction = session.beginTransaction();
        Query<RssEntry> query = session.createQuery(SELECT_RSS_BY_URI, RssEntry.class);
        query.setParameter("link", uri);
        List<RssEntry> resultList = query.getResultList();
        if (resultList.size() == 0) {
            RssEntry rss = new RssEntry();
            rss.setTitle(title);
            rss.setUri(uri);
            rss.setAdded(new Date());
            rss.setRss(rssParent);
            session.save(rss);
            transaction.commit();
        }

        //otherwise do nothing
    }
}
