package pl.quider.standalone.irc.verbs;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.dbsession.ADatabaseSession;
import pl.quider.standalone.irc.dbsession.MySqlDatabaseSession;
import pl.quider.standalone.irc.model.Message;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 * Created by Adrian on 14.10.2016.
 */
public class RssTest {

    @Mock
    MyBot bot;
    @Mock
    Message msg;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute() throws Exception {
        ADatabaseSession aDatabaseSession = MySqlDatabaseSession.create();
        Session session = aDatabaseSession.getSession();
        doReturn(session).when(bot).getSession();

        Rss rss = spy(new Rss(bot, msg));
        rss.execute("");
    }

}