package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import model.AuthData;
import model.GameData;
import responserequest.ErrorResponce;
import responserequest.GameCreationResponse;
import responserequest.GameListResponse;
import responserequest.ResponseAuth;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Client {
    ServerFacade serverFacadeObj;
    WebSocketFacade clientSocketObj;
    ServerMessageHandler clientServerMessageHandler;
    AuthData validAuthData;
    String baseURL;
    PrintStream out;
    ChessGame.TeamColor joinedPLayerColor;
    ChessGame joinedChessGame;
    int joinedChessGameID;
    private ArrayList<GameData> lastReceivedGameList = new ArrayList<>();
    public Client(String baseURL){
        this.baseURL = baseURL;
        serverFacadeObj = new ServerFacade(baseURL);
        clientServerMessageHandler = new ServerMessageHandler(this);
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    }
    // this function runs the client and has the loop that receives user input and calls
    // appropriate helper functions
    public void run() throws IOException, URISyntaxException {
        // print out the starting header into the terminal
        System.out.print(EscapeSequences.WHITE_KING);
        System.out.print("Welcome to 240 chess. Type help to get started");
        System.out.println(EscapeSequences.BLACK_KING);
        boolean isLoggedIn = false;
        boolean isInGame = false;
        boolean isExitProgram = false;
        // the main loop that receives user input and calls appropriate helper functions
        while(true){
            // get the user input
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] userArgs = line.split(" ");
            // this is the first phase where the user has not logged in
            // tracked with the isLoggedIn boolean
            if(!isLoggedIn){
                switch (userArgs[0]) {
                    case "register":
                        try{
                            // checks to see if the user put in the correct number of args
                            if(userArgs.length != 4){
                                System.out.println("invalid number of arguments");
                                break;
                            }
                            ResponseAuth registerResponseAuth = serverFacadeObj.registerClient(
                                    userArgs[1],userArgs[2],userArgs[3]);
                            // if registration was successful then the authToken is saved for later use
                            if(registerResponseAuth.message() == null){
                                validAuthData = new AuthData(registerResponseAuth.username(),
                                        registerResponseAuth.authToken());
                                isLoggedIn = true;
                                System.out.println("logged in as " + validAuthData.getUsername());
                            }else{
                                System.out.println("failed to register");
                            }
                            break;
                        }catch(Exception ex) {
                            System.out.println("invalid arguments types");
                            break;
                        }
                    case "login":
                        try{
                            // checks to see if the user put in the correct number of args
                            if(userArgs.length != 3){
                                System.out.println("invalid number of arguments");
                                break;
                            }
                            ResponseAuth loginResponseAuth = serverFacadeObj.loginClient(userArgs[1],userArgs[2]);
                            // if the login was successful then the authToken is saved for later use
                            if(loginResponseAuth.message() == null){
                                validAuthData = new AuthData(loginResponseAuth.username(), loginResponseAuth.authToken());
                                isLoggedIn = true;
                                System.out.println("logged in as " + validAuthData.getUsername());
                            }else{
                                System.out.println("failed to login");
                            }
                            break;
                        }catch(Exception ex){
                            System.out.println("invalid arguments types");
                            break;
                        }
                    case "quit":
                        // uses isExitProgram boolean to exit the main while loop and stop the client
                        isExitProgram = true;
                        break;
                    case "help":
                        printPreLoginHelp();
                        break;
                    default:
                        System.out.println("invalid command");
                        break;
                }
            }else{
                if(!isInGame){
                    // this is the first phase where the user has logged in
                    // tracked with the isLoggedIn boolean
                    switch (userArgs[0]) {
                        // checks to see if the user put in the correct number of args
                        case "create":
                            try{
                                if(userArgs.length != 2){
                                    System.out.println("invalid number of arguments");
                                    break;
                                }
                                GameCreationResponse createResponse = serverFacadeObj.createClientGame(
                                        validAuthData.getAuthToken(), userArgs[1]);
                                if(createResponse.message() == null){
                                    System.out.println("created game successfully");
                                }else{
                                    System.out.println("failed to create game\n" + createResponse.message());
                                }
                                break;
                            }catch(Exception ex){
                                System.out.println("invalid arguments types");
                                break;
                            }
                        case "list":
                            try{
                                GameListResponse listResponse = serverFacadeObj.listServerGames(validAuthData.getAuthToken());
                                if(listResponse.message() == null){
                                    System.out.println("Game list found");
                                    lastReceivedGameList.clear();
                                    lastReceivedGameList.addAll(listResponse.games());
                                    int currentGameIndex = 1;
                                    // prints all games returned to the console
                                    for(GameData currentGame: listResponse.games()){
                                        System.out.println(currentGameIndex
                                                + ". Game Name: " + currentGame.getGameName()+
                                                "  White Player: " + currentGame.getWhiteUsername() +
                                                "  Black Player: " + currentGame.getBlackUsername());
                                        currentGameIndex++;
                                    }
                                }else{
                                    System.out.println("failed to find game list\n" + listResponse.message());
                                }
                                break;
                            }catch(Exception ex){
                                System.out.println("invalid arguments types");
                                break;
                            }
                        case "join":
                            try{
                                // checks to see if the user put in the correct number of args
                                if(userArgs.length != 3){
                                    System.out.println("invalid number of arguments");
                                    break;
                                }
                                if(lastReceivedGameList.size() <= Integer.parseInt(userArgs[1])-1){
                                    System.out.println("Invalid gameNumber");
                                    break;
                                }
                                int gameListIndex = Integer.parseInt(userArgs[1])-1;
                                if(gameListIndex < 0 || lastReceivedGameList.size() <= gameListIndex){
                                    System.out.println("Invalid gameNumber");
                                    break;
                                }
                                int joinGameID = lastReceivedGameList.get(gameListIndex).getGameID();

                                ErrorResponce joinResponse = serverFacadeObj.joinClientToServerGame(
                                        validAuthData.getAuthToken(), joinGameID, userArgs[2]);
                                if(joinResponse.message() == null){
                                    System.out.println("Joined game");
                                    if((userArgs[2].toUpperCase()).equals("WHITE")){
                                        joinedPLayerColor = ChessGame.TeamColor.WHITE;
                                    }else if((userArgs[2].toUpperCase()).equals("BLACK")){
                                        joinedPLayerColor = ChessGame.TeamColor.BLACK;
                                    }else{
                                        System.out.println("invalid player color");
                                        break;
                                    }
                                    joinedChessGameID = joinGameID;
                                    UserGameCommand connectionCommand = new UserGameCommand(
                                            UserGameCommand.CommandType.CONNECT, validAuthData.getAuthToken(), joinGameID);
                                    clientSocketObj = new WebSocketFacade(baseURL, clientServerMessageHandler);
                                    clientSocketObj.connectClient(connectionCommand);
                                    isInGame = true;
                                }else{
                                    System.out.println("failed to join game\n" + joinResponse.message());
                                }
                                break;
                            }catch(Exception ex){
                                System.out.println("invalid arguments types");
                                break;
                            }
                        case "observe":
                            try{
                                // checks to see if the user put in the correct number of args
                                if(userArgs.length != 2){
                                    System.out.println("invalid number of arguments");
                                    break;
                                }
                                int gameListIndex = Integer.parseInt(userArgs[1])-1;
                                if(gameListIndex < 0 || lastReceivedGameList.size() <= gameListIndex){
                                    System.out.println("Invalid gameNumber");
                                    break;
                                }
                                joinedChessGameID = lastReceivedGameList.get(gameListIndex).getGameID();
                                UserGameCommand connectionCommand = new UserGameCommand(
                                        UserGameCommand.CommandType.CONNECT, validAuthData.getAuthToken(), joinedChessGameID);
                                clientSocketObj = new WebSocketFacade(baseURL, clientServerMessageHandler);
                                clientSocketObj.connectClient(connectionCommand);
                                joinedPLayerColor = null;
                                isInGame = true;
                            }catch(Exception ex){
                                System.out.println("invalid arguments types");
                                break;
                            }
                        case "logout":
                            ErrorResponce logoutResponse = serverFacadeObj.logoutClient(validAuthData.getAuthToken());
                            if(logoutResponse.message() == null){
                                isLoggedIn = false;
                                System.out.println("logged out successfully");
                            }else{
                                System.out.println("failed to logout\n" + logoutResponse.message());
                            }
                            break;
                        case "quit":
                            // uses isExitProgram boolean to exit the main while loop and stop the client
                            isExitProgram = true;
                            break;
                        case "help":
                            printPostLoginHelp();
                            break;
                        default:
                            System.out.println("invalid command");
                            break;
                    }
                }else{
                    switch(userArgs[0]){
                        case "makeMove":
                            try{
                                // checks to see if the user put in the correct number of args
                                if(userArgs.length != 4){
                                    System.out.println("invalid number of arguments");
                                    break;
                                }
                                if(joinedPLayerColor == null){
                                    System.out.println("You are observing the game, you can not make a move");
                                    break;
                                }
                                if(joinedChessGame.isGameHasEnded()){
                                    System.out.println("the game is over, you can not make a move");
                                    break;
                                }
                                // implement the make move client side
                                char letterIndex1 = userArgs[1].charAt(0);
                                int convertedLetterNum1 = switch (letterIndex1) {
                                    case 'a' -> 1;
                                    case 'b' -> 2;
                                    case 'c' -> 3;
                                    case 'd' -> 4;
                                    case 'e' -> 5;
                                    case 'f' -> 6;
                                    case 'g' -> 7;
                                    case 'h' -> 8;
                                    default -> -1;
                                };
                                if(convertedLetterNum1 == -1){
                                    System.out.println("invalid letter Index");
                                    break;
                                }
                                int numberIndex1 = Integer.parseInt(userArgs[1].substring(1));
                                if(numberIndex1 > 8 | numberIndex1 < 1){
                                    System.out.println("invalid number Index");
                                    break;
                                }
                                char letterIndex2 = userArgs[1].charAt(0);
                                int convertedLetterNum2 = switch (letterIndex2) {
                                    case 'a' -> 1;
                                    case 'b' -> 2;
                                    case 'c' -> 3;
                                    case 'd' -> 4;
                                    case 'e' -> 5;
                                    case 'f' -> 6;
                                    case 'g' -> 7;
                                    case 'h' -> 8;
                                    default -> -1;
                                };
                                if(convertedLetterNum2 == -1){
                                    System.out.println("invalid letter Index");
                                    break;
                                }
                                int numberIndex2 = Integer.parseInt(userArgs[1].substring(1));
                                if(numberIndex2 > 8 | numberIndex2 < 1){
                                    System.out.println("invalid number Index");
                                    break;
                                }
                                ChessPiece.PieceType promotionPieceType;
                                switch(userArgs[3].toUpperCase()){
                                    case "R":
                                        promotionPieceType = ChessPiece.PieceType.ROOK;
                                        break;
                                    case "N":
                                        promotionPieceType = ChessPiece.PieceType.KNIGHT;
                                        break;
                                    case "B":
                                        promotionPieceType = ChessPiece.PieceType.BISHOP;
                                        break;
                                    case "Q":
                                        promotionPieceType = ChessPiece.PieceType.QUEEN;
                                        break;
                                    default:
                                        promotionPieceType = null;
                                        break;
                                }
                                MakeMoveCommand makeMove = new MakeMoveCommand(UserGameCommand.CommandType.MAKE_MOVE,
                                        validAuthData.getAuthToken(), joinedChessGameID,
                                        new ChessMove(new ChessPosition(numberIndex1,convertedLetterNum1),new ChessPosition(numberIndex2, convertedLetterNum2), promotionPieceType));
                                clientSocketObj.makeMoveClient(makeMove);
                                break;
                            }catch(Exception ex){
                                System.out.println("invalid arguments types");
                                break;
                            }
                        case "redrawBoard":
                            if(joinedPLayerColor == ChessGame.TeamColor.BLACK){
                                System.out.println("redrawing game");
                                DrawChessBoard.drawChessBoard(out, "BLACK",
                                        null, null,
                                        joinedChessGame.getBoard());
                                System.out.print(EscapeSequences.RESET_BG_COLOR);
                                System.out.print("\n");
                            }else{
                                System.out.println("redrawing game");
                                DrawChessBoard.drawChessBoard(out, "WHITE",
                                        null, null,
                                        joinedChessGame.getBoard());
                                System.out.print(EscapeSequences.RESET_BG_COLOR);
                                System.out.print("\n");
                            }
                            break;
                        case "legalMoves":
                            if(userArgs.length != 2){
                                System.out.println("invalid number of arguments");
                                break;
                            }
                            char letterIndex = userArgs[1].charAt(0);
                            int convertedLetterNum = switch (letterIndex) {
                                case 'a' -> 1;
                                case 'b' -> 2;
                                case 'c' -> 3;
                                case 'd' -> 4;
                                case 'e' -> 5;
                                case 'f' -> 6;
                                case 'g' -> 7;
                                case 'h' -> 8;
                                default -> -1;
                            };
                            if(convertedLetterNum == -1){
                                System.out.println("invalid letter Index");
                                break;
                            }
                            int numberIndex = Integer.parseInt(userArgs[1].substring(1));
                            if(numberIndex > 8 | numberIndex < 1){
                                System.out.println("invalid number Index");
                                break;
                            }

                            Collection<ChessMove> validMoveList = joinedChessGame.
                                    validMoves(new ChessPosition(numberIndex, convertedLetterNum));
                            Set<ChessPosition> endPositionsSet = new HashSet<>();
                            ChessPosition pieceStartingPos = null;
                            for(ChessMove currentMove: validMoveList){
                                pieceStartingPos = currentMove.getStartPosition();
                                endPositionsSet.add(currentMove.getEndPosition());
                            }
                            if(joinedPLayerColor == ChessGame.TeamColor.BLACK){
                                DrawChessBoard.drawChessBoard(out, "BLACK",
                                        pieceStartingPos, endPositionsSet,
                                        joinedChessGame.getBoard());
                            }else{
                                DrawChessBoard.drawChessBoard(out, "WHITE",
                                        pieceStartingPos, endPositionsSet,
                                        joinedChessGame.getBoard());
                            }
                            System.out.print(EscapeSequences.RESET_BG_COLOR);
                            System.out.print("\n");
                            break;
                        case "resign":
                            System.out.println("are you sure you want to resign? Y ? N");
                            line = scanner.nextLine();
                            userArgs = line.split(" ");
                            if(userArgs[0].equals("Y") | userArgs[0].equals("y")){
                                System.out.println("resigning from game");
                                joinedChessGame.setGameHasEnded(true);
                                UserGameCommand resignCommand = new UserGameCommand(UserGameCommand.CommandType.RESIGN, validAuthData.getAuthToken(), joinedChessGameID);
                                clientSocketObj.resignClient(resignCommand);
                                System.out.println("you have been defeated");
                                // game over stuff

                            }
                            break;
                        case "leave":
                            isInGame = false;
                            UserGameCommand leaveCommand = new UserGameCommand(UserGameCommand.CommandType.LEAVE, validAuthData.getAuthToken(), joinedChessGameID);
                            clientSocketObj.leaveGame(leaveCommand);
                            break;
                        case "help":
                            printGamePlayHelp();
                            break;
                        default:
                            System.out.println("invalid command");
                            break;
                    }
                }
            }
            // checks the isExitProgram boolean to see if the user asked to break out of the main while loop
            // and close the program
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
                join <gameNumber> [WHITE|BLACK] - join the chess game with the game id as the color specified
                observe <gameNumber> - watch the game specified by the gameID
                logout - logout of the chess server and returns to the prelogin ui
                quit - close the chess client
                help - displays the help message to see what options are available
                """);
    }

    public void printGamePlayHelp(){
        System.out.println("""
                makeMove <originalPiecePosition> <newPiecePosition> <promotionPieceLetter> - makes the move for your turn. 
                    the piecePositions are the letter for the square followed by the number for the square
                redrawBoard - redraws the chess board
                legalMoves <piecePosition> - highlights the valid moves of the piece specified
                resign - allows the player to resign the chess game, will ask if user wants to resign
                leave - leaves the game and returns to the postlogin ui
                help - displays the help message to see what options are available
                """);
    }
    public void updateJoinedChessGame(ChessGame sentChessGame){
        joinedChessGame = sentChessGame;
        if(joinedPLayerColor == ChessGame.TeamColor.BLACK){
            System.out.println("redrawing game");
            DrawChessBoard.drawChessBoard(out, "BLACK",
                    null, null,
                    joinedChessGame.getBoard());
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print("\n");
        }else{
            System.out.println("redrawing game");
            DrawChessBoard.drawChessBoard(out, "WHITE",
                    null, null,
                    joinedChessGame.getBoard());
            System.out.print(EscapeSequences.RESET_BG_COLOR);
            System.out.print("\n");
        }
    }
    public void printServerMessage(String message){
        System.out.println(message);
    }

}
