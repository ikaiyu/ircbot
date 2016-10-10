package pl.quider.standalone.irc.verbs;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.dbsession.ADatabaseSession;
import pl.quider.standalone.irc.dbsession.MySqlDatabaseSession;
import pl.quider.standalone.irc.model.Message;

import static java.lang.System.out;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Adrian on 10.10.2016.
 */
public class TopTest {
    @Mock
    private MyBot bot;
    @Mock
    private Message msg;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute() throws Exception {
        ADatabaseSession aDatabaseSession = MySqlDatabaseSession.create();
        Session session = aDatabaseSession.getSession();
        doReturn(session).when(bot).getSession();
//        doNothing().when(bot).sendMessage(anyString(),anyString());
        doReturn("#channel").when(msg).getChannel();

        Top top = spy(new Top(bot, msg));
        doNothing().when(top).write(any());
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                for (Object argument:arguments) {
                    out.println(argument);
                }
                return null;
            }
        }).when(top).write(anyString());
        top.execute("parameter");

    }

}