package pl.quider.standalone.irc.verbs;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.*;

/**
 * Created by Adrian.Kozlowski on 2016-10-07.
 */
public class JoinTest {

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
        Join join = spy(new Join(bot, msg));

//        doAnswer(invocation -> {
//            String[] arguments = (String[]) invocation.getArguments();
//            if(arguments.length>1){throw new Exception();}
//            if (!arguments[0].equals("#channel")) {
//                throw new Exception("wrong channel name");
//            }
//            return "";
//        }).when(bot).joinChannel(matches("#channel"));

        String parameter = "#channel" + MyBot.VERB_PARAM_DELIMITER;

        join.execute(parameter);
    }

}