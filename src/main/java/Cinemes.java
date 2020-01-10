import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "dataroot")
@XmlAccessorType(XmlAccessType.PROPERTY)
class Cinemes {
    @XmlElement(name = "CINEMES")
    public List<Cinema> cinemas;
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

    @XmlElement(name = "PROVINCIA")
    private String cinemaProvincia;
}
