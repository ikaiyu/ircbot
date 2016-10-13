package pl.quider.standalone.irc.filters;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

/**
 * Created by Adrian.Kozlowski on 2016-10-13.
 */
public class EnshortLinkFiler extends Filter {

    public static String url = "http://xurl.pl/api/v1/56a1d83fb78777992fd0eca25ff85d29/shorturl/create/url/";

    public EnshortLinkFiler(Message msg, MyBot mybot) {
        super(msg, mybot);
    }

    /**
     * Reads xmls:
     * <?xml version="1.0" encoding="UTF-8" ?>
     * <message client="92.60.129.178" time="1476366442">
     *  <response>
     *      <code>200</code>
     *      <data>
     *          <url_id>626119</url_id>
     *          <short_url>http://xurl.pl/fdjN</short_url>
     *          <short_url_part>fdjN</short_url_part>
     *          <original_url>http://openweathermap.org/appid</original_url>
     *          <date_time>2016-10-13 15:47:22</date_time>
     *      </data>
     *  </response>
     * </message>
     */
    @Override
    public void execute() {
        try {
            URL theUrl = getUrl();
            InputStream is = getInputStream(theUrl);
            Element root = getElement(is);

            final String s = nodeValue(root);
            if(s != null){
                this.writeMessage(s);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param s
     */
    protected void writeMessage(String s) {
        bot.sendMessage(
                msg.getChannel(), s
        );
    }

    /**
     *
     * @param root
     * @return
     */
    protected String nodeValue(Element root) {
        final NodeList response = root.getElementsByTagName("response");
        final int responseCount = response.getLength();
        int i = 0;
        if (responseCount > 0) {
            do {
                final Node item = response.item(i);
                if(item.getNodeName().equals("data")){
                    final NodeList childNodes = item.getChildNodes();
                    final Node shortUrl = childNodes.item(1);
                    return shortUrl.getNodeValue();
                }
                ++i;
            } while (i <= responseCount);
        }
        return null;
    }

    /**
     *
     * @param is
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    protected Element getElement(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(is);
        return document.getDocumentElement();
    }

    /**
     *
     * @param theUrl
     * @return
     * @throws IOException
     */
    protected InputStream getInputStream(URL theUrl) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) theUrl.openConnection();
        urlConnection.setRequestMethod("GET");
        return urlConnection.getInputStream();
    }

    /**
     *
     * @return
     * @throws MalformedURLException
     */
    protected URL getUrl() throws MalformedURLException {
        URL longUrl = new URL(msg.getMessage());
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(msg.getMessage().getBytes());
        String s = new String(encode);

        return new URL(url + s + ".xml");
    }
}
