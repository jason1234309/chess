package client;

import model.GameData;
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
        serverFacade = new ServerFacade("http://localhost:" + Integer.toString(port));
        ErrorResponce clearRes = serverFacade.clearServerDataBase();
        if(clearRes.message() != null){
            throw new IOException();
        }
    }
    @BeforeEach
    public void setUp() throws URISyntaxException, IOException{
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
    @DisplayName("register 3 clients")
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
    @DisplayName("register same client twice")
    public void duplicateRegisterClientTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes2 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertEquals("Error: already taken", registerAuthRes2.message());
    }
    @Test
    @DisplayName("login client")
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
    @DisplayName("login multiple clients")
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
    @DisplayName("login in with the wrong password")
    public void wrongPasswordLoginClientTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth loginAuthRes1 = serverFacade.loginClient("toad", "lizard");
        Assertions.assertEquals("Error: unauthorized", loginAuthRes1.message());
    }
    @Test
    @DisplayName("create multiple games")
    public void createMultipleGamesTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        GameCreationResponse firstGameRes = serverFacade.createClientGame(registerAuthRes1.authToken(),
                "firstGame");
        Assertions.assertNull(firstGameRes.message());
        Assertions.assertEquals(1, firstGameRes.gameID());

        GameCreationResponse secondGameRes = serverFacade.createClientGame(registerAuthRes1.authToken(),
                "secondGame");
        Assertions.assertNull(secondGameRes.message());
        Assertions.assertEquals(2, secondGameRes.gameID());

        GameCreationResponse thirdGameRes = serverFacade.createClientGame(registerAuthRes1.authToken(),
                "thirdGame");
        Assertions.assertNull(thirdGameRes.message());
        Assertions.assertEquals(3, thirdGameRes.gameID());
    }
    @Test
    @DisplayName("create duplicate game")
    public void createDuplicateGameTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        GameCreationResponse firstGameRes = serverFacade.createClientGame(registerAuthRes1.authToken(),
                "firstGame");
        Assertions.assertNull(firstGameRes.message());
        Assertions.assertEquals(1, firstGameRes.gameID());

        GameCreationResponse secondGameRes = serverFacade.createClientGame(registerAuthRes1.authToken(),
                "firstGame");
        Assertions.assertEquals("Error: unauthorized",secondGameRes.message());
    }
    @Test
    @DisplayName("list games")
    public void listGamesTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        GameCreationResponse firstGameRes = serverFacade.createClientGame(registerAuthRes1.authToken(),
                "firstGame");
        Assertions.assertNull(firstGameRes.message());

        GameCreationResponse secondGameRes = serverFacade.createClientGame(registerAuthRes1.authToken(),
                "secondGame");
        Assertions.assertNull(secondGameRes.message());

        GameCreationResponse thirdGameRes = serverFacade.createClientGame(registerAuthRes1.authToken(),
                "thirdGame");
        Assertions.assertNull(thirdGameRes.message());

        GameListResponse listResponse = serverFacade.listServerGames(registerAuthRes1.authToken());
        Assertions.assertEquals(3, listResponse.games().size());
    }
    @Test
    @DisplayName("listGames with a null list")
    public void listGamesNullListTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        GameListResponse listResponse = serverFacade.listServerGames(registerAuthRes1.authToken());
        Assertions.assertEquals(0, listResponse.games().size());
    }
    @Test
    @DisplayName("join game both players")
    public void joinGameTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad",
                "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes2 = serverFacade.registerClient("lizard", "lizard",
                "lizard");
        Assertions.assertNull(registerAuthRes2.message());

        GameCreationResponse firstGameRes = serverFacade.createClientGame(registerAuthRes1.authToken(),
                "firstGame");
        Assertions.assertNull(firstGameRes.message());

        GameListResponse listResponse = serverFacade.listServerGames(registerAuthRes1.authToken());
        Assertions.assertNull(listResponse.message());

        ErrorResponce joinResWhitePlayer = serverFacade.joinClientToServerGame(registerAuthRes1.authToken(),
                0, "white");
        Assertions.assertNull(joinResWhitePlayer.message());

        ErrorResponce joinResBlackPlayer = serverFacade.joinClientToServerGame(registerAuthRes2.authToken(),
                0, "black");
        Assertions.assertNull(joinResBlackPlayer.message());
    }
    @Test
    @DisplayName("join clients to a full game")
    public void joinFullGameTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ResponseAuth registerAuthRes2 = serverFacade.registerClient("a", "a", "a");
        Assertions.assertNull(registerAuthRes2.message());

        GameCreationResponse firstGameRes = serverFacade.createClientGame(registerAuthRes1.authToken(),
                "firstGame");
        Assertions.assertNull(firstGameRes.message());

        GameListResponse listResponse = serverFacade.listServerGames(registerAuthRes1.authToken());
        Assertions.assertNull(listResponse.message());

        ErrorResponce gameJoinRes1 = serverFacade.joinClientToServerGame(registerAuthRes1.authToken(),
                0, "White");
        Assertions.assertNull(gameJoinRes1.message());

        ErrorResponce gameJoinRes2 = serverFacade.joinClientToServerGame(registerAuthRes1.authToken(),
                0, "White");
        Assertions.assertEquals("Error: already taken", gameJoinRes2.message());

        ErrorResponce gameJoinRes3 = serverFacade.joinClientToServerGame(registerAuthRes2.authToken(),
                0, "black");
        Assertions.assertNull(gameJoinRes3.message());

        ErrorResponce gameJoinRes4 = serverFacade.joinClientToServerGame(registerAuthRes2.authToken(),
                0, "black");
        Assertions.assertEquals("Error: already taken", gameJoinRes4.message());
    }
    @Test
    @DisplayName("logout client")
    public void logoutClientTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        ErrorResponce logoutRes1 = serverFacade.logoutClient(registerAuthRes1.authToken());
        Assertions.assertNull(logoutRes1.message());

        ResponseAuth loginAuthRes = serverFacade.loginClient("toad", "toad");
        Assertions.assertNull(loginAuthRes.message());

        ErrorResponce logoutRes2 = serverFacade.logoutClient(loginAuthRes.authToken());
        Assertions.assertNull(logoutRes2.message());
    }
    @Test
    @DisplayName("logout client with invalid auth")
    public void invalidLogoutClientTest() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message())
        ;
        ErrorResponce logoutRes1 = serverFacade.logoutClient("bad auth");
        Assertions.assertEquals("Error: unauthorized", logoutRes1.message());
    }
    @Test
    @DisplayName("test running game endpoints that require authTokens with a invalid auth")
    public void invalidAuthTests() throws URISyntaxException, IOException {
        ResponseAuth registerAuthRes1 = serverFacade.registerClient("toad", "toad", "toad");
        Assertions.assertNull(registerAuthRes1.message());

        GameCreationResponse createBadGameRes = serverFacade.createClientGame("fake", "firstGame");
        Assertions.assertEquals("Error: unauthorized", createBadGameRes.message());

        GameListResponse badListRes = serverFacade.listServerGames("badAuth");
        Assertions.assertEquals("Error: unauthorized", badListRes.message());

        ErrorResponce badJoinRes = serverFacade.joinClientToServerGame("oops", 0,
                "black");
        Assertions.assertEquals("Error: unauthorized", badJoinRes.message());
    }
}
