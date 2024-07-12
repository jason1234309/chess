package dataaccess;
import com.google.gson.Gson;
import model.UserData;

import javax.xml.crypto.Data;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SqlUserDAO implements UserDAO{
    public SqlUserDAO()throws DataAccessException{
        DatabaseManager.createDatabase();
        final String[] createTableStatements = {
                """
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
            )
            """
        };
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createTableStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
    @Override
    public void clearUserDataBase() throws DataAccessException {
        var clearDataBaseStatement = "DELETE from user";
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement(clearDataBaseStatement)){
                preparedStatement.executeUpdate();
            }
        }catch(DataAccessException | SQLException ex){
            throw new DataAccessException(String.format("Unable to clear data: %s", ex.getMessage()));
        }
    }

    @Override
    public void createUser(String username, String password, String email) throws DataAccessException {
        var insertStatement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement(insertStatement)){
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);
                preparedStatement.executeUpdate();
            }
        }catch(DataAccessException | SQLException ex){
            if(ex.getMessage().startsWith("Duplicate entry")){
                throw new DataAccessException("Error: already taken");
            }else{
                throw new DataAccessException(String.format("Unable to create User: %s", ex.getMessage()));
            }
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String queryStatement = "SELECT * FROM user WHERE username=?";
            try (var preparedStatement = conn.prepareStatement(queryStatement)) {
                preparedStatement.setString(1,username);
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String returnedUserName = resultSet.getString("username");
                        String returnedPassword = resultSet.getString("password");
                        String returnedEmail = resultSet.getString("email");
                        return new UserData(returnedUserName, returnedPassword, returnedEmail);
                    }
                }
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(String.format("Unable to read data: %s", ex.getMessage()));
        }
        return null;
    }
}
