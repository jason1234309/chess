package server;

import ResponseRequest.*;
import chess.ChessGame;
import dataaccess.DataAccessException;
import model.*;
import spark.*;
import com.google.gson.Gson;
import service.AllServices;

public class Server {
    AllServices databaseServiceObj = new AllServices();
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
                if(registerAuthresponse.message().equals("Error: bad request")){
                    res.status(400);
                    return new Gson().toJson(registerAuthresponse);
                }else{
                    res.status(403);
                    return new Gson().toJson(registerAuthresponse);
                }

            }
    }
    public Object Login(Request req, Response res){
        UserData newUser = serializer.fromJson(req.body(), UserData.class);
        ResponseAuth LoginAuthresponse = databaseServiceObj.login(newUser);
        if(LoginAuthresponse.message() == null){
            res.status(200);
            return new Gson().toJson(LoginAuthresponse);
        }else{
            res.status(401);
            return new Gson().toJson(LoginAuthresponse);
        }
    }
    public Object Logout(Request req, Response res){
        String reqAuthToken = req.headers("authorization");
        AuthData tempAuthObj = new AuthData(null, reqAuthToken);
        ErrorResponce logoutResponce = databaseServiceObj.logout(tempAuthObj);
        if(logoutResponce.message() == null){
            res.status(200);
            return new Gson().toJson(logoutResponce);
        }else{
            res.status(401);
            return new Gson().toJson(logoutResponce);
        }
    }
    public Object ListGames(Request req, Response res){
        String reqAuthToken = req.headers("authorization");
        AuthData tempAuthObj = new AuthData(null, reqAuthToken);
        GameListResponse gameListResponse = databaseServiceObj.ListGames(tempAuthObj);
        if(gameListResponse.message() == null){
            res.status(200);
            return new Gson().toJson(gameListResponse);
        }else{
            res.status(401);
            return new Gson().toJson(gameListResponse);
        }
    }
    public Object CreateGame(Request req, Response res){
        String reqAuthToken = req.headers("authorization");
        GameNameReq gameName = serializer.fromJson(req.body(), GameNameReq.class);
        AuthData tempAuthObj = new AuthData(null, reqAuthToken);
        GameCreationResponse createGameResponce = databaseServiceObj.CreateGame(tempAuthObj, gameName.gameName());
        if(createGameResponce.message() == null){
            res.status(200);
            return new Gson().toJson(createGameResponce);
        }else{
            if(createGameResponce.message().equals("Error: bad request")){
                res.status(400);
                return new Gson().toJson(createGameResponce);
            }else{
                res.status(401);
                return new Gson().toJson(createGameResponce);
            }
        }
    }
    public Object JoinGame(Request req, Response res){
        String reqAuthToken = req.headers("authorization");
        JoinRequestBody reqBodyObj = serializer.fromJson(req.body(), JoinRequestBody.class);
        AuthData tempAuthObj = new AuthData(null, reqAuthToken);
        ErrorResponce joinGameResponce = databaseServiceObj.JoinGame(tempAuthObj, reqBodyObj.playerColor(), reqBodyObj.gameID());
        if(joinGameResponce.message() == null){
            res.status(200);
            return new Gson().toJson(joinGameResponce);
        }else{
            if(joinGameResponce.message().equals("Error: unauthorized")){
                res.status(401);
                return new Gson().toJson(joinGameResponce);
            }else if(joinGameResponce.message().equals("Error: already taken")){
                res.status(403);
                return new Gson().toJson(joinGameResponce);
            }else{
                res.status(400);
                return new Gson().toJson(joinGameResponce);
            }
        }
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
