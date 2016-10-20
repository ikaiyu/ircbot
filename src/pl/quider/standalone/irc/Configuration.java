package pl.quider.standalone.irc;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.procedure.ParameterMisuseException;
import pl.quider.standalone.irc.exceptions.NotConfiguredException;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Adrian on 27.09.2016.
 */
public class Configuration {


    public static Configuration instance;
    private Properties properties;

    private Configuration() {

    }

    public static Configuration getInstance(){
        if (instance == null){
            instance = new Configuration();
        }
        return instance;
    }

    /**
     * Loads config file and saves it to map.
     * @param pathToConfigFile
     * @throws IOException
     * @throws ParameterMisuseException
     */
    public void loadConfig(String pathToConfigFile) throws IOException, ParameterMisuseException, NotConfiguredException {
        properties = this.openFile(pathToConfigFile);
    }

    /**
     *
     * @param pathToConfigFile
     * @return
     */
    protected Properties openFile(String pathToConfigFile) throws IOException, NotConfiguredException {
        Properties properties = new Properties();
        File file = new File(pathToConfigFile);
        if(!file.exists()){
            createPropertiesFile(file, properties);
        }
        InputStream resourceAsStream = new FileInputStream(file);
        properties.load(resourceAsStream);
        return properties;
    }

    /**
     * Generates configuration file
     * @param file
     * @param properties
     * @throws IOException
     * @throws NotConfiguredException
     */
    protected void createPropertiesFile(File file, Properties properties) throws IOException, NotConfiguredException {
        boolean newFile = file.createNewFile();
        properties.put(ConfigurationKeysContants.NICK,"Adirael");
        properties.put(ConfigurationKeysContants.SERVER1,"open.ircnet.net");
        properties.put(ConfigurationKeysContants.ALT_NICK,"_Adirael_");
        properties.put(ConfigurationKeysContants.LOGIN,"Adirael");
        properties.put(ConfigurationKeysContants.JOIN_CHANNEL,"#adirael_chan");
//        properties.put("","");
//        properties.put("","");
//        properties.put("","");
//        properties.put("","");
//        properties.put("","");
//        properties.put("","");
        properties.store(new FileWriter(file), "");
        throw new NotConfiguredException("Application is not configured. Now appeared config file. Try it");
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
