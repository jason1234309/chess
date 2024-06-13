package server;

import dataaccess.DataAccessException;
import spark.*;
import com.google.gson.Gson;
import service.GameService;

public class Server {
    GameService databaseServiceObj = new GameService();
    Gson serializer = new Gson();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::ClearApplication);

        Spark.post("/user", this::Register);
        Spark.post("/session", this::Login);
        Spark.delete("/session", this::Logout);

        Spark.get("/game", this::ListGames);
        Spark.post("/game", this::CreateGame);
        Spark.put("/game", this::JoinGame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public Object ClearApplication(Request req, Response res){
        databaseServiceObj.clearUsers();
        databaseServiceObj.clearGames();
        return "not implemented";
    }
    public Object Register(Request req, Response res){
        try{
            databaseServiceObj.register(req.body());
        }
        catch(DataAccessException e){
            return "error";
        }
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

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
