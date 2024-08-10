package ui;


import chess.ChessGame;
import model.GameData;
import websocket.messages.*;

import java.io.PrintStream;

public class ServerMessageHandler {
    Client client;
    ChessGame lastSentGame;
    ServerMessageHandler(Client client){
        this.client = client;
        lastSentGame = null;
    }
    void notify(ServerMessage message){
        switch (message.getServerMessageType()){
            case NOTIFICATION:
                NotificationServerMessage currentNoteMessage = (NotificationServerMessage) message;
                client.printServerMessage(currentNoteMessage.message);
                break;
            case ERROR:
                ErrorServerMessage currentErrorMessage = (ErrorServerMessage) message;
                client.printServerMessage(currentErrorMessage.errorMessage);
                break;
            case LOAD_GAME:
                LoadGameServerMessage currentLoadMessage = (LoadGameServerMessage) message;
                client.updateJoinedChessGame(currentLoadMessage.game);
                break;
        }
    }
}
