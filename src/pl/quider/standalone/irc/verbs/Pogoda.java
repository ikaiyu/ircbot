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
                this.url = new URL(URL + split[0] + API_CODE);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.setRequestMethod("POST");
                InputStream is = request.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                BufferedReader br = new BufferedReader(new InputStreamReader(bis));
                String inputLine = "";
                StringBuffer sb = new StringBuffer();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(is);
                Element root = document.getDocumentElement();

                NodeList forecast = root.getElementsByTagName("forecast");
                Node firstChild = forecast.item(0).getFirstChild();
                NodeList childNodes = firstChild.getChildNodes();
                StringBuilder sbMessage= new StringBuilder();
                for (int i = 0; i <= childNodes.getLength() - 1; i++) {
                    Node item = childNodes.item(i);
                    if (item.getNodeName().equals("temperature")) {
                        Node value = item.getAttributes().getNamedItem("value");
                        sbMessage.append("Temp: ").append(value.getNodeValue()).append("*C, ");
                    } else if (item.getNodeName().equals("pressure")) {
                        Node value = item.getAttributes().getNamedItem("value");
                        sbMessage.append("Ciśnienie: ").append(value.getNodeValue()).append("hPa, ");
                    } else if (item.getNodeName().equals("humidity")) {
                        Node value = item.getAttributes().getNamedItem("value");
                        sbMessage.append("Wilgotność: ").append(value.getNodeValue()).append("%.");
                    }
                }

                String s = sbMessage.toString();
                bot.sendMessage(msg.getChannel(),s);
            } catch (IOException e) {
                bot.sendMessage(msg.getChannel(),"Em... Coś nie halo...");
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (DOMException e) {
                e.printStackTrace();
            }
        }
    }
}
