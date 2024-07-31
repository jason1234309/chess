import ui.Client;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Client clientObj = new Client("http://localhost:8080");
        clientObj.run();
    }
}