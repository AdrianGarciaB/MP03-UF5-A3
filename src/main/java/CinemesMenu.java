import com.budhash.cliche.Command;
import com.budhash.cliche.Param;
import com.budhash.cliche.ShellFactory;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) throws IOException {
        new CinemesMenu();
    }
}

public class CinemesMenu{
    protected List<Cinema> cinemesList;
    public CinemesMenu() throws IOException {
        cinemesList = JAXB.unmarshal("http://gencat.cat/llengua/cinema/cinemes.xml", Cinemes.class).getCinemas();

        ShellFactory.createConsoleShell("Opcion", opcionsMenu(), this)
                .commandLoop(); // and three.
    }

    public static String opcionsMenu(){
        return "\n\n"
                + "Menu Principal" + "\n"
                + "0. Veure les opcions del Menu" + "\n"
                + "1. Camp a buscar: id, nom, adreça, localitat, comarca, provincia." + "\n"
                + "2. Mostrar el cinema mes nou." + "\n"
                + "3. Mostrar tots els cinemes que son a Tarragona." + "\n"
                + "4. Mostrar el numero de cinemes per Provincia." + "\n"
                + "5. Mostrar la comarca amb mes cinemes" + "\n"
                + "6. Mostrar un cinema per cada provincia"
                + "exit. Para salir" + "\n";

    }

    @Command(name = "0")
    public String veureMenu(){
        return opcionsMenu();
    }

    @Command(name = "1")
    public void searchCinema(@Param(name = "camp") String text){
        cinemesList.stream()	// same as Stream.of(names)
                .filter(x -> x.getCinemaNom().contains(text))
                .forEach(System.out::println);
    }

    @Command(name = "1")
    public void searchCinemaWithParam(@Param(name = "camp") String type, @Param(name = "text") String text){
        cinemesList.stream()
                .filter(x -> {
                    switch (type) {
                        case "id": return x.getCinemaId() == Integer.parseInt(text);
                        case "adreça": return x.getCinemaAdreca().contains(text);
                        case "localitat": return x.getCinemaLocalitat().contains(text);
                        case "comarca": return x.getCinemaComarca().contains(text);
                        case "provincia": return x.getCinemaLocalitat().contains(text);
                    }
                    return false;
                })
                .sorted()
                .forEach(System.out::println);
    }

    @Command(name = "2")
    public void getLastCinemaAdded(){
        Cinema cinema = cinemesList.stream()	// same as Stream.of(names)
                // .max((x, y) -> (x.getCinemaId() < y.getCinemaId()) ? -1 : (x.getCinemaId() == y.getCinemaId()) ? 0 : 1).get();
                //.max((x, y) -> Integer.compare(x.getCinemaId(), y.getCinemaId())).get();
                .max(Comparator.comparingInt(Cinema::getCinemaId)).get();

        System.out.println(cinema);
    }

    @Command(name = "3")
    public void getCinemesOnTarragona(){
        cinemesList.stream()
                .filter(x -> x.getCinemaProvincia().equals("TARRAGONA"))
                .forEach(System.out::println);
    }

    @Command(name = "4")
    public void getNumberOfCinemasByProvince(){
        Map<String, Long> provinceCount = cinemesList.stream().collect(Collectors.groupingBy(Cinema::getCinemaProvincia, Collectors.counting()));
        System.out.println(provinceCount);
    }

    @Command(name = "5")
    public void getRegionPWithMoreCinemas(){
        Optional<Map.Entry<String, Long>> cinema =
                cinemesList.stream()
                    .collect(Collectors.groupingBy(Cinema::getCinemaComarca, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .max(Comparator.comparingLong(Map.Entry::getValue));

        System.out.println("Comarca{" +
                "comarcaNom=" + cinema.get().getKey() +
                ", numeroCinemes=" + cinema.get().getValue() + "}");
    }

    @Command(name = "6")
    public void getOneCinemaPerProvince(){

    }



}