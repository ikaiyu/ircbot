package pl.quider.standalone.irc.verbs;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.dbsession.ADatabaseSession;
import pl.quider.standalone.irc.dbsession.MySqlDatabaseSession;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.services.RssService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        Session session = doReturn(mock(Session.class)).when(bot).getSession();
        String[] listChannel = new String[]{"#chan1", "#chan2"};
        doReturn(listChannel).when(bot).getChannels();
        RssService rssService = mock(RssService.class);
        doReturn(prepareListOfFeeds()).when(rssService).getListOfFeeds();

        Rss rss = spy(new Rss(bot, null));
        doReturn(session).when(rss).createServiceObject();

        doNothing().when(rss).sendMessage(any(), any());
        rss.execute(null);

        doReturn(new String[]{}).when(bot).getChannels();
        rss.execute(null);
    }

    private List<pl.quider.standalone.irc.model.Rss> prepareListOfFeeds() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new pl.quider.standalone.irc.model.Rss());
        arrayList.add(new pl.quider.standalone.irc.model.Rss());
        arrayList.add(new pl.quider.standalone.irc.model.Rss());
        arrayList.add(new pl.quider.standalone.irc.model.Rss());
        return arrayList;
    }


}