import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CinemasResponse {
    public static void main(String[] args) throws JAXBException, IOException {
        URL url = new URL("http://gencat.cat/llengua/cinema/cinemes.xml");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.addRequestProperty("User-Agent", "Mozilla/4.76");
        InputStream is = http.getInputStream();

        JAXBContext jaxbContext = JAXBContext.newInstance(Cinemes.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Cinemes cinemes = (Cinemes) jaxbUnmarshaller.unmarshal(is);
        System.out.println(cinemes);

    }
}
