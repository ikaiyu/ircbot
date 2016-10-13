package pl.quider.standalone.irc.verbs;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

/**
 * Created by Adrian on 12.10.2016.
 */
public class PogodaTest {
    @Mock
    MyBot bot;
    @Mock
    Message msg;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExecute() throws Exception {
        bot = mock(MyBot.class);
        Pogoda pogoda = spy(new Pogoda(bot, msg));
        doNothing().when(pogoda).sendMessage(any());

        pogoda.execute("Poznań"+MyBot.VERB_PARAM_DELIMITER);
    }

}