package pl.quider.standalone.irc;

import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.procedure.ParameterMisuseException;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Adrian on 27.09.2016.
 */
public class Configuration {


    private Properties properties;

    public Configuration() {
    }

    /**
     * Loads config file and saves it to map.
     * @param pathToConfigFile
     * @throws IOException
     * @throws ParameterMisuseException
     */
    public void loadConfig(String pathToConfigFile) throws IOException, ParameterMisuseException {
        properties = this.openFile(pathToConfigFile);
    }

    /**
     * TODO
     * @param pathToConfigFile
     * @return
     */
    protected Properties openFile(String pathToConfigFile) throws IOException {
        Properties properties = new Properties();
        File file = new File(pathToConfigFile);
        InputStream resourceAsStream = new FileInputStream(file);
        properties.load(resourceAsStream);
        return properties;
    }

       /**
     * returns all keys
     * @return
     */
    public Collection getAllKeys(){
        return this.properties.keySet();
    }

    /**
     * Cheks if key exists. If not throws exception.
     * @param key
     * @return
     * @throws RuntimeException
     */
    public String getValue(String key) throws RuntimeException{
        if(this.properties.containsKey(key)) {
            return (String) this.properties.get(key);
        } else {
            throw new RuntimeException("No such key");
        }
    }
}
