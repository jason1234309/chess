package ui;

import model.AuthData;
import model.GameData;
import responserequest.ErrorResponce;
import responserequest.GameCreationResponse;
import responserequest.GameListResponse;
import responserequest.ResponseAuth;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    ServerFacade serverFacadeObj;
    DrawChessBoard drawChessBoardObj;
    AuthData validAuthData;
    public Client(String baseURL){
        serverFacadeObj = new ServerFacade(baseURL);
        drawChessBoardObj = new DrawChessBoard();
    }
    public void run() throws IOException, URISyntaxException {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        System.out.print(EscapeSequences.WHITE_KING);
        System.out.print("Welcome to 240 chess. Type help to get started");
        System.out.println(EscapeSequences.BLACK_KING);
        boolean isLoggedIn = false;
        boolean isExitProgram = false;
        while(true){
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] userArgs = line.split(" ");
            if(!isLoggedIn){
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
                        break;
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
                switch (userArgs[0]) {
                    case "create":
                        GameCreationResponse createResponse = serverFacadeObj.createClientGame(validAuthData, userArgs[1]);
                        if(createResponse.message() == null){
                            System.out.println("created game succefully: gameid is " + createResponse.gameID().toString());
                        }else{
                            System.out.println("failed to create game: " + createResponse.message());
                        }
                        break;
                    case "list":
                        GameListResponse listResponse = serverFacadeObj.listServerGames(validAuthData);
                        if(listResponse.message() == null){
                            System.out.println("Game list found");
                            int currentGameIndex = 1;
                            for(GameData currentGame: listResponse.games()){
                                System.out.println(currentGameIndex
                                        + ". Game Name:" + currentGame.getGameName()+
                                        " White Player:" + currentGame.getWhiteUsername() +
                                        " Black Player" + currentGame.getBlackUsername());
                                currentGameIndex++;
                            }
                        }else{
                            System.out.println("failed to find game list: " + listResponse.message());
                        }
                        break;
                    case "join":
                        ErrorResponce joinResponse = serverFacadeObj.joinClientToServerGame(validAuthData, Integer.parseInt(userArgs[1]), userArgs[2]);
                        if(joinResponse.message() == null){
                            System.out.println("Joined game");
                        }else{
                            System.out.println("failed to join game: " + joinResponse.message());
                        }
                        break;
                    case "observe":
                        GameData requestedGame = serverFacadeObj.observeServerGame(Integer.parseInt(userArgs[1]));
                        if(requestedGame != null){
                            System.out.println("showing game");
                            DrawChessBoard.drawChessBoard(out, requestedGame.getChessGame().getBoard());
                        }else{
                            System.out.println("failed to find game");
                        }
                        break;
                    case "logout":
                        ErrorResponce logoutResponse = serverFacadeObj.logoutClient(validAuthData);
                        if(logoutResponse.message() == null){
                            isLoggedIn = false;
                            System.out.println("logged out successfully");
                        }else{
                            System.out.println("failed to logout: " + logoutResponse.message());
                        }
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
