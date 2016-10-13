package pl.quider.standalone.irc.filters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;

import java.net.MalformedURLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by Adrian on 13.10.2016.
 */
public class EnshortLinkFilerTest {

    @Mock
    private MyBot bot;

    @Mock
    private Message msg;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute() throws Exception {
        doReturn("https://www.facebook.com/permalink.php?story_fbid=1163794463694781&id=100001927144775&notif_t=like&notif_id=1476383712553461").when(msg).getMessage();
        EnshortLinkFiler filer = spy(new EnshortLinkFiler(msg,bot));
        doNothing().when(filer).writeMessage(anyString());
        doCallRealMethod().when(filer).getInputStream(any());
        //doThrow(MalformedURLException.class).when(filer).getInputStream(any());

        filer.execute();
       // filer.execute();
    }

}