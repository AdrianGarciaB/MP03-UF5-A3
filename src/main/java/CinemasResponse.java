import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CinemasResponse {

    public Cinemes parse(String url1) {
        InputStream is = null;
        URL url = null;
        try {
             url = new URL(url1);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.addRequestProperty("User-Agent", "Mozilla/4.76");
            is = http.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }


            return JAXB.unmarshal(url, Cinemes.class);

    }
}
