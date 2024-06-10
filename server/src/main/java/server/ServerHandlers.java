package server;

import service.GameService;

public class ServerHandlers {
    GameService databaseServiceObj = new GameService();

    public void ClearApplication(){
        databaseServiceObj.clearUsers();
        databaseServiceObj.clearGames();
    }
}
