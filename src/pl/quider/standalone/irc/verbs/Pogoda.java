package pl.quider.standalone.irc.verbs;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Adrian on 12.10.2016.
 */
public class Pogoda extends Verb {
    private static final String TEMPERATURE = "temperature";
    private static final String VALUE = "value";
    private static final String PRESSURE = "pressure";
    private static final String HUMIDITY = "humidity";
    public static final String POST = "POST";

    private static String API_CODE = "&APPID=4c1606b8ad9cc95f39e300113634e47f&mode=xml";
    private static String URL = "http://api.openweathermap.org/data/2.5/forecast/city?q=";
    private URL url;

    public Pogoda(MyBot mybot, Message msg) throws MalformedURLException {
        super(mybot, msg);

    }

    @Override
    public void execute(String parameter) throws Exception {
        String[] split = parameter.split(MyBot.VERB_PARAM_DELIMITER);
        if (split.length == 1) {
            try {
                InputStream is = getInputStream(split);

                NodeList childNodes = getNodeList(is);
                StringBuilder sbMessage = getStringBuilder(childNodes);

                String s = sbMessage.toString();
                this.sendMessage(s);
                sendMessage(s);
            } catch (IOException e) {
                sendMessage("Em... Coś nie halo...");
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                sendMessage("Konfiguracja nie bangla");
                e.printStackTrace();
            } catch (SAXException e) {
                sendMessage("Xml z webserwisu jest do bani");
                e.printStackTrace();
            } catch (DOMException e) {
                sendMessage("Rozjechało się parsowanie");
                e.printStackTrace();
            }
        }
    }

    private InputStream getInputStream(String[] split) throws IOException {
        this.url = new URL(URL + split[0] + API_CODE);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod(POST);
        return request.getInputStream();
    }

    private StringBuilder getStringBuilder(NodeList childNodes) {
        StringBuilder sbMessage= new StringBuilder();

        for (int i = 0; i <= childNodes.getLength() - 1; i++) {
            Node item = childNodes.item(i);
            if (item.getNodeName().equals(TEMPERATURE)) {
                Node value = item.getAttributes().getNamedItem(VALUE);
                sbMessage.append("Temp: ").append(value.getNodeValue()).append("*C, ");
            } else if (item.getNodeName().equals(PRESSURE)) {
                Node value = item.getAttributes().getNamedItem(VALUE);
                sbMessage.append("Ciśnienie: ").append(value.getNodeValue()).append("hPa, ");
            } else if (item.getNodeName().equals(HUMIDITY)) {
                Node value = item.getAttributes().getNamedItem(VALUE);
                sbMessage.append("Wilgotność: ").append(value.getNodeValue()).append("%.");
            }
        }
        return sbMessage;
    }

    private NodeList getNodeList(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(is);
        Element root = document.getDocumentElement();
        NodeList forecast = root.getElementsByTagName("forecast");
        Node firstChild = forecast.item(0).getFirstChild();
        return firstChild.getChildNodes();
    }

    /**
     *
     * @param message
     */
    protected void sendMessage(String message) {
        bot.sendMessage(msg.getChannel(), message);
    }


}
