package pl.quider.standalone.irc.services;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.TestUtils;
import pl.quider.standalone.irc.model.Message;
import pl.quider.standalone.irc.model.User;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 * Created by Adrian.Kozlowski on 2016-10-07.
 */
public class MessageServiceTest {
    @Spy
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
        Message message = new Message("#channel", "@nick", user, "join #channel");

        this.messageService = spy( new MessageService(message,bot,session));
        doReturn(false).doReturn(true).when(this.messageService).isUserCallingMe();

        this.messageService.executeVerb(bot);
    }

    @Test
    public void isUserCallingMe() throws Exception {
        fail();
    }

}