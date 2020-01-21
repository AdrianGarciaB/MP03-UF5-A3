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
                + "1. Camp a buscar: id, nom, adreça, localitat, comarca, provincia. (Per defecte busca pel nom del Cinema)" + "\n"
                + "2. Mostrar el cinema mes nou." + "\n"
                + "3. Mostrar tots els cinemes que son a Tarragona." + "\n"
                + "4. Mostrar el numero de cinemes per Provincia." + "\n"
                + "5. Mostrar la comarca amb mes cinemes" + "\n"
                + "6. Mostrar un cinema per cada provincia" + "\n"
                + "7. Busqueda amb multiples parametres" + "\n"
                + "exit. Para salir" + "\n";

    }

    @Command(name = "0")
    public String veureMenu(){
        return opcionsMenu();
    }

    @Command(name = "1")
    // Ordre de exemple: 1 nomCinema
    public void searchCinema(@Param(name = "camp") String text){
        cinemesList.stream()	// same as Stream.of(names)
                .filter(x -> x.getCinemaNom().contains(text))
                .forEach(System.out::println);
    }

    @Command(name = "1")
    // Ordre de exemple: 1 provincia nomProvincia
    public void searchCinemaWithParam(@Param(name = "camp") String type, @Param(name = "text") String text){
        cinemesList.stream()
                .filter(x -> {
                    switch (type) {
                        case "id": return x.getCinemaId() == Integer.parseInt(text);
                        case "nom": return x.getCinemaNom().contains(text);
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
    // Introdueix a la consola un 2 per fer l'us
    public void getLastCinemaAdded(){
        Cinema cinema = cinemesList.stream()	// same as Stream.of(names)
                // .max((x, y) -> (x.getCinemaId() < y.getCinemaId()) ? -1 : (x.getCinemaId() == y.getCinemaId()) ? 0 : 1).get();
                //.max((x, y) -> Integer.compare(x.getCinemaId(), y.getCinemaId())).get();
                .max(Comparator.comparingInt(Cinema::getCinemaId)).orElse(null);

        System.out.println(cinema);
    }

    @Command(name = "3")
    // Introdueix a la consola un 3 per fer l'us
    public void getCinemesOnTarragona(){
        cinemesList.stream()
                .filter(x -> x.getCinemaProvincia().equals("TARRAGONA"))
                .forEach(System.out::println);
    }

    @Command(name = "4")
    // Introdueix a la consola un 4 per fer l'us
    public void getNumberOfCinemasByProvince(){
        Map<String, Long> provinceCount = cinemesList.stream().collect(Collectors.groupingBy(Cinema::getCinemaProvincia, Collectors.counting()));
        System.out.println(provinceCount);
    }

    @Command(name = "5")
    // Introdueix a la consola un 5 per fer l'us
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
    // Introdueix a la consola un 6 per fer l'us
    public void getOneCinemaPerRegion(){
        Map<String, List<Cinema>> cinemes =
                cinemesList.stream()
                        .filter(cinema -> !cinema.getCinemaComarca().equals("--"))
                        .collect(Collectors.groupingBy(Cinema::getCinemaComarca));

        cinemes.forEach((s, cinemas) -> System.out.print("Comarca " + s + "\nNom del cinema: " + cinemas.get(0).getCinemaNom() + "\n\n"));
    }

    @Command(name = "7")
    // Exemple del ordre: 7 nom:Teatre:provincia:"BARCELONA":localitat:"Barberà del Vallès"
    public void getCinemasWithMultipleSearh(@Param(name = "params") String params){
        String[] values = params.split(":");

        HashMap<String, String> listvalues = new HashMap<>();

        for (int i = 0; i < values.length; i+=2) {
            listvalues.put(values[i], values[i+1]);
        }
         cinemesList.stream()
                 .filter(x -> {if (listvalues.containsKey("id")) return x.getCinemaId() == Integer.parseInt(listvalues.get("id")); return true;})
                 .filter(x -> {if (listvalues.containsKey("nom")) return x.getCinemaNom().contains(listvalues.get("nom")); return true;})
                 .filter(x -> {if (listvalues.containsKey("adreça")) return x.getCinemaAdreca().contains(listvalues.get("adreça")); return true;})
                 .filter(x -> {if (listvalues.containsKey("localitat")) return x.getCinemaLocalitat().contains(listvalues.get("localitat")); return true;})
                 .filter(x -> {if (listvalues.containsKey("comarca")) return x.getCinemaComarca().contains(listvalues.get("comarca")); return true;})
                 .filter(x -> {if (listvalues.containsKey("provincia")) return x.getCinemaProvincia().contains(listvalues.get("provincia")); return true;})
                .forEach(System.out::println);
    }
}