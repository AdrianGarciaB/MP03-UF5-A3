import com.budhash.cliche.Command;
import com.budhash.cliche.Param;
import com.budhash.cliche.ShellFactory;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

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
                + "2. Mostrar número de jugadores españoles." + "\n"
                + "3. Añadir jugador." + "\n"
                + "4. Mostrar jugadores que en la temporada 04/05 tenían una media de puntos por partido superior a 10." + "\n"
                + "5. Mostrar número de partidos que han ganado los Warriors de más de 15 puntos en la temporada 05/06." + "\n"
                + "6. Lista de opciones" + "\n"
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




}