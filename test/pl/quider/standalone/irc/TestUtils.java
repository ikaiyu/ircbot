package pl.quider.standalone.irc;

import pl.quider.standalone.irc.model.User;

import java.util.Date;

/**
 * Created by Adrian on 07.10.2016.
 */
public class TestUtils {

    public static User prepareUserMock() {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setLastSeen(new Date());
        mockUser.setLogin("login");
        mockUser.setMask("hostname");
        mockUser.setNickName("nick");
        mockUser.setOp(true);
        return mockUser;
    }
}
