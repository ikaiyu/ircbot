package pl.quider.standalone.irc.services;

import org.hibernate.Session;
import org.hibernate.query.Query;

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

    public List<pl.quider.standalone.irc.model.Rss> getListOfFeeds() {

        Query query = session.createQuery(SELECT_ALL_RSS);
        List resultList = query.getResultList();

        return resultList;
    }
}
