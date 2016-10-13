package pl.quider.standalone.irc.filters;

import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import pl.quider.standalone.irc.MyBot;
import pl.quider.standalone.irc.model.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.List;

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
            String url = getElement(is);
            this.writeMessage(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
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
     * @param is
     * @return
     * @throws SAXException
     * @throws IOException
     */
    protected String getElement(InputStream is) throws SAXException, IOException, DocumentException {
        SAXReader reader = new SAXReader();
        org.dom4j.Document document = reader.read(is);
        Node node = document.selectSingleNode("//message/response/data/short_url");

        return node.getText();
    }

    /**
     *
     * @param theUrl
     * @return
     * @throws IOException
     */
    protected InputStream getInputStream(URL theUrl) throws IOException, DocumentException {
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
