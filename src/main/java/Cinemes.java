import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "dataroot")
@XmlAccessorType(XmlAccessType.PROPERTY)
class Cinemes {
    @XmlElement(name = "CINEMES")
    private List<Cinema> cinemas;

    public List<Cinema> getCinemas() {
        return cinemas;
    }
}

class Cinema {
    @XmlElement(name = "CINEID")
    private int cinemaId;

    @XmlElement(name = "CINENOM")
    private String cinemaNom;

    @XmlElement(name = "CINEADRECA")
    private String cinemaAdreca;

    @XmlElement(name = "LOCALITAT")
    private String cinemaLocalitat;

    @XmlElement(name = "COMARCA")
    private String cinemaComarca;

    @XmlElement(name = "PROVINCIA")
    private String cinemaProvincia;

    public int getCinemaId() {
        return cinemaId;
    }

    public String getCinemaAdreca() {
        return cinemaAdreca;
    }

    public String getCinemaLocalitat() {
        return cinemaLocalitat;
    }

    public String getCinemaNom() {
        return cinemaNom;
    }

    public String getCinemaComarca() {return cinemaComarca;}

    public String getCinemaProvincia() {
        return cinemaProvincia;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "cinemaId=" + cinemaId +
                ", cinemaNom='" + cinemaNom + '\'' +
                ", cinemaAdreca='" + cinemaAdreca + '\'' +
                ", cinemaLocalitat='" + cinemaLocalitat + '\'' +
                ", cinemaComarca='" + cinemaComarca + '\'' +
                ", cinemaProvincia='" + cinemaProvincia + '\'' +
                '}';
    }
}
