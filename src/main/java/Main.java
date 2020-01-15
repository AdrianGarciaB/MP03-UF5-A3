import com.budhash.cliche.ShellFactory;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        CinemasResponse cinemesResponse = new CinemasResponse();
        List<Cinema> cinemesList = cinemesResponse.parse("http://gencat.cat/llengua/cinema/cinemes.xml").getCinemas();

        Scanner in = new Scanner(System.in);
        ShellFactory.createConsoleShell("hello", "", new CinemesMenu())
                .commandLoop(); // and three.

    }
}
