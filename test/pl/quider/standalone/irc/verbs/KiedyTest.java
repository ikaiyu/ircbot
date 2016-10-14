package pl.quider.standalone.irc.verbs;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;

import static org.junit.Assert.*;

/**
 * Created by Adrian on 14.10.2016.
 */
public class KiedyTest {
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

    }

}