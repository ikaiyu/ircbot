package pl.quider.standalone.irc.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.stubbing.OngoingStubbing;
import pl.quider.standalone.irc.model.User;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Adrian.Kozlowski on 2016-10-07.
 */
public class UserServiceTest {

    @Mock
    private Session session;
    @Mock
    private UserService userService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        userService = spy(new UserService("nick", "login","hostname", session));

    }

    @Test
    public void getExistingUser() throws Exception {
        Query mockQuery = mock(Query.class);
        when(session.createQuery(any(String.class))).thenReturn(mockQuery);
        when(session.beginTransaction()).thenReturn(mock(Transaction.class));
        ArrayList arrayList = new ArrayList();
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setLastSeen(new Date());
        mockUser.setLogin("login");
        mockUser.setMask("hostname");
        mockUser.setNickName("nick");
        mockUser.setOp(true);
        arrayList.add(mockUser);
        when(mockQuery.getResultList()).thenReturn(arrayList);
        try {
            User existingUser = userService.getUser();
            assertNotNull(existingUser);
            assertEquals(mockUser, existingUser);
            assertNotNull(existingUser.getLogin());
            assertNotNull(existingUser.getMask());
            assertNotNull(existingUser.getNickName());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getNewUser() throws Exception {
        when(session.beginTransaction()).thenReturn(mock(Transaction.class));
        try {
            doReturn(false).when(userService).userExists();
            User newUser = userService.getUser();
            assertNotNull(newUser);
            assertNotNull(newUser.getLogin());
            assertNotNull(newUser.getMask());
            assertNotNull(newUser.getNickName());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void joined() throws Exception {
        fail();
    }

    @Test
    public void userPresent() throws Exception {
        fail();
    }

    @Test
    public void opUser() throws Exception {
        fail();
    }

    @Test
    public void getStats() throws Exception {
        fail();
    }

    @Test
    public void updateStats() throws Exception {
        fail();
    }

}