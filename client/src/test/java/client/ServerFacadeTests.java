package client;

import model.*;
import responserequest.*;
import org.junit.jupiter.api.*;
import responserequest.ResponseAuth;
import server.Server;
import ui.ServerFacade;

import java.io.IOException;
import java.net.URISyntaxException;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    public ServerFacadeTests() throws URISyntaxException, IOException {
    }

    @BeforeAll
    public static void init() throws URISyntaxException, IOException {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade("http/localhost:" + Integer.toString(port));
        ErrorResponce clearRes = serverFacade.clearServerDataBase();
        if(clearRes.message() != null){
            throw new IOException();
        }
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerClientsTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad",
                "toad");
        Assertions.assertNull(registerAuthRes1.message());
        Assertions.assertTrue(registerAuthRes1.authToken().length() > 10);
        ResponseAuth registerAuthRes2 = serverFacade.registerClient("lizard", "lizard",
                "lizard");
        Assertions.assertNull(registerAuthRes2.message());
        Assertions.assertTrue(registerAuthRes2.authToken().length() > 10);
        ResponseAuth registerAuthRes3 = serverFacade.registerClient("fire hawk", "fire hawk",
                "fire hawk");
        Assertions.assertNull(registerAuthRes3.message());
        Assertions.assertTrue(registerAuthRes3.authToken().length() > 10);
    }
    @Test
    public void duplicateRegisterClientTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes2 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertEquals("Error: already taken", registerAuthRes2.message());
    }
    @Test
    public void loginClientTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth loginAuthRes1 = serverFacade.loginClient("toad", "toad");
        Assertions.assertNull(loginAuthRes1.message());
        Assertions.assertTrue(loginAuthRes1.authToken().length() > 10);
        ResponseAuth loginAuthRes2 = serverFacade.loginClient("toad", "toad");
        Assertions.assertNull(loginAuthRes2.message());
        Assertions.assertTrue(loginAuthRes2.authToken().length() > 10);
        Assertions.assertNotEquals(loginAuthRes1.authToken(), loginAuthRes2.authToken());
    }
    @Test
    public void loginMultipleClientsTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad",
                "toad");
        Assertions.assertNull(registerAuthRes1.message());
        ResponseAuth registerAuthRes2 = serverFacade.registerClient("lizard", "lizard",
                "lizard");
        Assertions.assertNull(registerAuthRes2.message());
        ResponseAuth registerAuthRes3 = serverFacade.registerClient("fire hawk", "fire hawk",
                "fire hawk");
        Assertions.assertNull(registerAuthRes3.message());

        ResponseAuth loginAuthRes1 = serverFacade.loginClient("toad", "toad");
        Assertions.assertNull(loginAuthRes1.message());
        Assertions.assertTrue(loginAuthRes1.authToken().length() > 10);
        ResponseAuth loginAuthRes2 = serverFacade.loginClient("lizard", "lizard");
        Assertions.assertNull(loginAuthRes2.message());
        Assertions.assertTrue(loginAuthRes2.authToken().length() > 10);
        ResponseAuth loginAuthRes3 = serverFacade.loginClient("fire hawk", "fire hawk");
        Assertions.assertNull(loginAuthRes3.message());
        Assertions.assertTrue(loginAuthRes3.authToken().length() > 10);
        Assertions.assertNotEquals(loginAuthRes1.authToken(), loginAuthRes2.authToken());
        Assertions.assertNotEquals(loginAuthRes1.authToken(), loginAuthRes3.authToken());
        Assertions.assertNotEquals(loginAuthRes2.authToken(), loginAuthRes3.authToken());
    }
    @Test
    public void wrongPasswordLoginClientTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth loginAuthRes1 = serverFacade.loginClient("toad", "lizard");
        Assertions.assertEquals("Error: unauthorized", loginAuthRes1.message());
    }
    @Test
    public void posCreateGameTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes.message());
        Assertions.assertTrue(registerAuthRes.authToken().length() > 10);
    }
    @Test
    public void negCreateGameTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes.message());
        Assertions.assertTrue(registerAuthRes.authToken().length() > 10);
    }
    @Test
    public void posListGamesTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes.message());
        Assertions.assertTrue(registerAuthRes.authToken().length() > 10);
    }
    @Test
    public void negListGamesTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes.message());
        Assertions.assertTrue(registerAuthRes.authToken().length() > 10);
    }
    @Test
    public void posJoinGameTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes.message());
        Assertions.assertTrue(registerAuthRes.authToken().length() > 10);
    }
    @Test
    public void negJoinGameTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes.message());
        Assertions.assertTrue(registerAuthRes.authToken().length() > 10);
    }
    @Test
    public void posObserveGameTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes.message());
        Assertions.assertTrue(registerAuthRes.authToken().length() > 10);
    }
    @Test
    public void negObserverGameTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes.message());
        Assertions.assertTrue(registerAuthRes.authToken().length() > 10);
    }
    @Test
    public void posLogoutClientTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes.message());
        Assertions.assertTrue(registerAuthRes.authToken().length() > 10);
    }
    @Test
    public void negLogoutClientTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes.message());
        Assertions.assertTrue(registerAuthRes.authToken().length() > 10);
    }

}
