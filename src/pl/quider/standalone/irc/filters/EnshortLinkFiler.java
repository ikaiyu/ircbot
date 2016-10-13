package pl.quider.standalone.irc.filters;

import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;

/**
 * Created by Adrian.Kozlowski on 2016-10-13.
 */
public class EnshortLinkFiler  extends Filter{
    public EnshortLinkFiler(Message msg, MyBot mybot) {
        super(msg,mybot);
    }

    @Override
    public void execute() {

    }
}
