package ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.AuthData;
import responserequest.ResponseAuth;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    ServerFacade serverFacadeObj;
    AuthData validAuthData;
    public Client(String baseURL){
        serverFacadeObj = new ServerFacade(baseURL);
    }
    public void run() throws IOException, URISyntaxException {
        System.out.println(EscapeSequences.WHITE_KING +
                "Welcome to 240 chess. Type help to get started" +
                EscapeSequences.BLACK_KING);
        boolean isLoggedIn = false;
        boolean isExitProgram = false;
        while(true){
            if(!isLoggedIn){
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                String[] userArgs = line.split(" ");
                switch (userArgs[0]) {
                    case "register":
                        ResponseAuth registerResponseAuth = serverFacadeObj.registerClient(userArgs[1],userArgs[2],userArgs[3]);
                        if(registerResponseAuth.message() == null){
                            validAuthData = new AuthData(registerResponseAuth.username(), registerResponseAuth.authToken());
                            isLoggedIn = true;
                            System.out.println("logged in as " + validAuthData.getUsername());
                        }else{
                            System.out.println("failed to register");
                        }
                    case "login":
                        ResponseAuth loginResponseAuth = serverFacadeObj.loginClient(userArgs[1],userArgs[2]);
                        if(loginResponseAuth.message() == null){
                            validAuthData = new AuthData(loginResponseAuth.username(), loginResponseAuth.authToken());
                            isLoggedIn = true;
                            System.out.println("logged in as " + validAuthData.getUsername());
                        }else{
                            System.out.println("failed to login");
                        }
                        break;
                    case "quit":
                        isExitProgram = true;
                        break;
                    case "help":
                        printPreLoginHelp();
                        break;
                }
            }else{
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                String[] userArgs = line.split(" ");
                switch (userArgs[0]) {
                    case "create":
                        break;
                    case "list":
                        break;
                    case "join":
                        break;
                    case "observe":
                        break;
                    case "logout":
                        break;
                    case "quit":
                        isExitProgram = true;
                        break;
                    case "help":
                        printPostLoginHelp();
                        break;
                }
            }
            if(isExitProgram){
                break;
            }
        }
    }

    public void printPreLoginHelp(){
        System.out.println("""
                register <username> <password> <email> - create an account on the chess server
                login <username> <password> - log into your account on the chess server
                quit - close the chess client
                help - displays the help message to see what options are available
                """);
    }

    public void printPostLoginHelp(){
        System.out.println("""
                create <gameName> - create a new chess game
                list - list all current chess games
                join <gameID> [WHITE|BLACK] - join the chess game with the game id as the color specified
                observe <gameID> - watch the game specified by the gameID
                logout - logout of the chess server
                quit - close the chess client
                help - displays the help message to see what options are available
                """);
    }
}
