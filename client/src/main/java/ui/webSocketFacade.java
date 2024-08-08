package ui;

import com.google.gson.Gson;
import responserequest.ErrorResponce;

import javax.websocket.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

public class webSocketFacade extends Endpoint {
    private final URI serverUri;
    public final Session session;


    public webSocketFacade(String url) throws URISyntaxException, DeploymentException, IOException {
        serverUrl = url;
        URI uri = new URI("ws://localhost:8080/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                System.out.println(message);
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
