package server;

import com.google.gson.Gson;
import service.GameService;
import spark.*;

public class ServerHandlers {
    GameService databaseServiceObj = new GameService();
    Gson serializer = new Gson();

    public Object ClearApplication(Request req, Response res){
        databaseServiceObj.clearUsers();
        databaseServiceObj.clearGames();
        return "not implemented";
    }
    public Object Register(Request req, Response res){
        return "not implemented";
    }
    public Object Login(Request req, Response res){
        return "not implemented";
    }
    public Object Logout(Request req, Response res){
        return "not implemented";
    }
    public Object ListGames(Request req, Response res){
        return "not implemented";
    }
    public Object CreateGame(Request req, Response res){
        return "not implemented";
    }
    public Object JoinGame(Request req, Response res){
        return "not implemented";
    }
}
