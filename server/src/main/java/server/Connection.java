package server;

import org.eclipse.jetty.websocket.api.Session;
import java.io.IOException;

public class Connection {
    public String clientAuth;
    public Session session;

    public Connection(String clientAuth, Session session) {
        this.clientAuth = clientAuth;
        this.session = session;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}
