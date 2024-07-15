package server;

import responserequest.*;
import model.*;
import spark.*;
import com.google.gson.Gson;
import service.AllServices;

public class Server {
    AllServices databaseServiceObj = new AllServices();
    Gson serializer = new Gson();

    // runs all spark functions needed to start and run the server
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // the request handlers functions are registered here
        Spark.delete("/db", this::clearApplication);
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public Object clearApplication(Request req, Response res){
        // handles clear requests and returns appropriate response
        ErrorResponce clearMessage = databaseServiceObj.clearDatabases();
        if(clearMessage.message() == null){
            res.status(200);
        }else{
            res.status(500);
        }
        return new Gson().toJson(clearMessage);
    }
    public Object register(Request req, Response res){
        // handles register requests and returns appropriate response
            UserData newUser = serializer.fromJson(req.body(), UserData.class);
            ResponseAuth registerAuthresponse = databaseServiceObj.register(newUser);
            if(registerAuthresponse.message() == null){
                res.status(200);
                return new Gson().toJson(registerAuthresponse);
            }else{
                if(registerAuthresponse.message().equals("Error: bad request")){
                    res.status(400);
                    return new Gson().toJson(registerAuthresponse);
                }else if(registerAuthresponse.message().equals("Error: already taken")){
                    res.status(403);
                    return new Gson().toJson(registerAuthresponse);
                }else{
                    res.status(500);
                    return new Gson().toJson(registerAuthresponse);
                }

            }
    }
    public Object login(Request req, Response res){
        // handles login requests and returns appropriate response
        UserData newUser = serializer.fromJson(req.body(), UserData.class);
        ResponseAuth loginAuthresponse = databaseServiceObj.login(newUser);
        if(loginAuthresponse.message() == null){
            res.status(200);
            return new Gson().toJson(loginAuthresponse);
        }else if(loginAuthresponse.message().equals("Error: unauthorized")){
            res.status(401);
            return new Gson().toJson(loginAuthresponse);
        }else{
            res.status(500);
            return new Gson().toJson(loginAuthresponse);
        }
    }
    public Object logout(Request req, Response res){
        // handles logout requests and returns appropriate response
        String reqAuthToken = req.headers("authorization");
        AuthData tempAuthObj = new AuthData(null, reqAuthToken);
        ErrorResponce logoutResponse = databaseServiceObj.logout(tempAuthObj);
        if(logoutResponse.message() == null){
            res.status(200);
            return new Gson().toJson(logoutResponse);
        }else if(logoutResponse.message().equals("Error: unauthorized")){
            res.status(401);
            return new Gson().toJson(logoutResponse);
        }else{
            res.status(500);
            return new Gson().toJson(logoutResponse);
        }
    }
    public Object listGames(Request req, Response res){
        // handles list game requests and returns appropriate response
        String reqAuthToken = req.headers("authorization");
        AuthData tempAuthObj = new AuthData(null, reqAuthToken);
        GameListResponse gameListResponse = databaseServiceObj.listGames(tempAuthObj);
        if(gameListResponse.message() == null){
            res.status(200);
            return new Gson().toJson(gameListResponse);
        }else if(gameListResponse.message().equals("Error: unauthorized")){
            res.status(401);
            return new Gson().toJson(gameListResponse);
        }else{
            res.status(500);
            return new Gson().toJson(gameListResponse);
        }
    }
    public Object createGame(Request req, Response res){
        // handles create game requests and returns appropriate response
        String reqAuthToken = req.headers("authorization");
        GameNameReq gameName = serializer.fromJson(req.body(), GameNameReq.class);
        AuthData tempAuthObj = new AuthData(null, reqAuthToken);
        GameCreationResponse createGameResponse = databaseServiceObj.createGame(tempAuthObj, gameName.gameName());
        if(createGameResponse.message() == null){
            res.status(200);
            return new Gson().toJson(createGameResponse);
        }else{
            if(createGameResponse.message().equals("Error: bad request")){
                res.status(400);
                return new Gson().toJson(createGameResponse);
            }else if(createGameResponse.message().equals("Error: unauthorized")){
                res.status(401);
                return new Gson().toJson(createGameResponse);
            }else{
                res.status(500);
                return new Gson().toJson(createGameResponse);
            }
        }
    }
    public Object joinGame(Request req, Response res){
        // handles join game requests and returns appropriate response
        String reqAuthToken = req.headers("authorization");
        JoinRequestBody reqBodyObj = serializer.fromJson(req.body(), JoinRequestBody.class);
        AuthData tempAuthObj = new AuthData(null, reqAuthToken);
        ErrorResponce joinGameResponse = databaseServiceObj.joinGame(tempAuthObj, reqBodyObj.playerColor(), reqBodyObj.gameID());
        if(joinGameResponse.message() == null){
            res.status(200);
            return new Gson().toJson(joinGameResponse);
        }else{
            switch (joinGameResponse.message()) {
                case "Error: bad request" -> {
                    res.status(400);
                    return new Gson().toJson(joinGameResponse);
                }
                case "Error: unauthorized" -> {
                    res.status(401);
                    return new Gson().toJson(joinGameResponse);
                }
                case "Error: already taken" -> {
                    res.status(403);
                    return new Gson().toJson(joinGameResponse);
                }
                default -> {
                    res.status(500);
                    return new Gson().toJson(joinGameResponse);
                }
            }
        }
    }
    // stops the server from running
    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
