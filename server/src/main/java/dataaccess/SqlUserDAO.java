package dataaccess;

import model.UserData;
import java.sql.SQLException;

public class SqlUserDAO implements UserDAO{
    public SqlUserDAO()throws DataAccessException{
        // calls the database creation method and makes the sql create table statement for the user table
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
        // creates a connection to the database and creates the user table if it doesn't exist
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createTableStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s",
                    ex.getMessage()));
        }
    }
    @Override
    public void clearUserDataBase() throws DataAccessException {
        // connects to the database and clears all data from the user table
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
        // connects to the database and inserts a new user into the user table
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
        // connects to the database and queries the user table for a specific user
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
