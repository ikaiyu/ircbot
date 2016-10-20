package pl.quider.standalone.irc;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

/**
 * Created by Adrian.Kozlowski on 2016-10-07.
 */
public class ConfigurationTest {

    private Configuration config;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        config = spy(Configuration.getInstance());
    }

    @Test
    public void loadConfig() throws Exception {
        config.loadConfig("./testResources/configuration.properties");
        Collection allKeys = config.getAllKeys();
        String server1 = config.getValue("server1");
        assertNotNull(allKeys);
        assertNotNull(server1);
        try {
            String madeUpKey = config.getValue("madeUpKey");
            fail();
        } catch (RuntimeException e) {

        }
    }



}