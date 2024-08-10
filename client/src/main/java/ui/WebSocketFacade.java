package ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorServerMessage;
import websocket.messages.LoadGameServerMessage;
import websocket.messages.NotificationServerMessage;
import websocket.messages.ServerMessage;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {
    public final Session session;
    ServerMessageHandler serverMessageHandler;


    public WebSocketFacade(String url, ServerMessageHandler serverMessageHandler) throws URISyntaxException, DeploymentException, IOException {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.serverMessageHandler = serverMessageHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch(serverMessage.getServerMessageType()){
                        case NOTIFICATION -> {
                            NotificationServerMessage noteMessage = new Gson().fromJson(message, NotificationServerMessage.class);
                            serverMessageHandler.notify(noteMessage);
                        }
                        case ERROR -> {
                            ErrorServerMessage errorMessage = new Gson().fromJson(message, ErrorServerMessage.class);
                            serverMessageHandler.notify(errorMessage);
                        }
                        case LOAD_GAME -> {
                            Gson serializer = new GsonBuilder().enableComplexMapKeySerialization().create();
                            LoadGameServerMessage loadMessage = serializer.fromJson(message, LoadGameServerMessage.class);
                            serverMessageHandler.notify(loadMessage);
                        }
                    }
                }
            });
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {

    }
    @Override
    public void onClose(Session session, CloseReason closeReason) {

    }

    @Override
    public void onError(Session session, Throwable thr) {
        System.out.println(thr.getMessage());
    }

    public void connectClient(UserGameCommand command) throws IOException {
        this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }

    public void makeMoveClient(MakeMoveCommand command)throws IOException{
        this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }

    public void resignClient(UserGameCommand command)throws IOException{
        this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }

    public void leaveGame(UserGameCommand command)throws IOException{
        this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }
}
