package pl.quider.standalone.irc;

import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.procedure.ParameterMisuseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adrian on 27.09.2016.
 */
public class Configuration {

    Map<String, String> params;

    public Configuration() {
        params = new HashMap<String, String>();
    }

    /**
     * Loads config file and saves it to map.
     * @param pathToConfigFile
     * @throws IOException
     * @throws ParameterMisuseException
     */
    public void loadConfig(String pathToConfigFile) throws IOException, ParameterMisuseException {
        BufferedReader reader = this.openFile(pathToConfigFile);
        this.readParams(reader);
    }

    /**
     * TODO
     * @param pathToConfigFile
     * @return
     */
    private BufferedReader openFile(String pathToConfigFile) {
        throw new NotYetImplementedException();
    }

    /**
     * Saves params from bufferedReader to map
     * @param bufferedReader reader of file
     * @throws IOException
     */
    private void readParams(BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (this.isCommentedOut(line)) {
                continue;
            }
            String[] split = line.split("=");
            if (split.length != 2) {
                throw new ParameterMisuseException("Valid config is: <key> = <value of key> per line.");
            }
            this.params.put(split[0].trim(), split[1].trim());
        }
    }

    /**
     * returns all keys
     * @return
     */
    public Collection getAllKeys(){
        return this.params.keySet();
    }

    /**
     * Cheks if key exists. If not throws exception.
     * @param key
     * @return
     * @throws RuntimeException
     */
    public String getValue(String key) throws RuntimeException{
        if(this.params.containsKey(key)) {
            return this.params.get(key);
        } else {
            throw new RuntimeException("No such key");
        }
    }

    /**
     * Not implemented yet
     * TODO
     *
     * @param line
     * @return
     */
    private boolean isCommentedOut(String line) {
        return false;
    }
}
