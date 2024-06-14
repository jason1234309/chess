package server;

import ResponseRequest.*;
import dataaccess.DataAccessException;
import model.*;
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

    public Object ClearApplication(Request req, Response res){   // THIS IS INCOMPLETE, NO ERROR RESPONSE
        ErrorResponce clearMessage = databaseServiceObj.clearDatabases();
        res.status(200);
        return new Gson().toJson(clearMessage);
    }
    public Object Register(Request req, Response res){
            UserData newUser = serializer.fromJson(req.body(), UserData.class);
            ResponseAuth registerAuthresponse = databaseServiceObj.register(newUser);
            if(registerAuthresponse.message() == null){
                res.status(200);
                return new Gson().toJson(registerAuthresponse);
            }else{
                res.status(403);
                return new Gson().toJson(registerAuthresponse);
            }
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
