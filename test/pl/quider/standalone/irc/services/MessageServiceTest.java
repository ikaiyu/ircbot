package pl.quider.standalone.irc.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.TestUtils;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.model.User;
import pl.quider.standalone.irc.verbs.Verb;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Adrian.Kozlowski on 2016-10-07.
 */
public class MessageServiceTest {
    @Mock
    private MessageService messageService;
    @Mock
    private Session session;
    @Mock
    private MyBot bot;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void executeVerb() throws Exception {
        User user = TestUtils.prepareUserMock();
        Message message = new Message("#channel", "quider", user, "bot! join #interia");
        when(session.beginTransaction()).thenReturn(mock(Transaction.class));
        this.messageService = spy( new MessageService(message,bot,session));
        doReturn(true).doReturn(false).doThrow(Exception.class).when(this.messageService).isUserCallingMe();
        Verb mockVerb = mock(Verb.class);
        doReturn(mockVerb).when(this.messageService).factorVerb(any());

        doNothing().when(mockVerb).execute(anyString());
        try {
            //true
            this.messageService.executeVerb(bot);

            //false
            this.messageService.executeVerb(bot);

        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        } catch (InstantiationException e) {
            fail(e.getMessage());
        } catch (ClassNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void isUserCallingMe() throws Exception {
        fail();
    }

}