package ui;

import com.google.gson.Gson;
import websocket.messages.ServerMessage;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class webSocketFacade extends Endpoint {
    public final Session session;
    NotificationHandler notificationHandler;


    public webSocketFacade(String url, NotificationHandler notificationHandler) throws URISyntaxException, DeploymentException, IOException {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
                    notificationHandler.notify(notification);
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

    }
    public void connectClient(){

    }

    public void makeMoveClient(){

    }

    public void resignClient(){

    }

    public void leaveGame(){

    }

}
