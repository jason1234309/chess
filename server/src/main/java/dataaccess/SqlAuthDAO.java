package dataaccess;

import model.AuthData;
import java.sql.SQLException;

public class SqlAuthDAO implements AuthDAO{
    public SqlAuthDAO()throws DataAccessException{
        // calls the database creation method and makes the sql create table statement for the auth table
        DatabaseManager.createDatabase();
        final String[] createTableStatements = {
                """
            CREATE TABLE IF NOT EXISTS  auth (
            `authToken` varchar(256) NOT NULL,
            `username` varchar(256) NOT NULL,
            PRIMARY KEY (`authToken`)
            )
            """
        };
        // creates a connection to the database and creates the auth table if it doesn't exist
        try (var conn = DatabaseManager.getConnection()) {
            for (var createTableStatement : createTableStatements) {
                try (var preparedStatement = conn.prepareStatement(createTableStatement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
    @Override
    public void clearAuthDataBase() throws DataAccessException{
        // connects to the database and clears all data from the auth table
        var clearDataBaseStatement = "DELETE from auth";
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement(clearDataBaseStatement)){
                preparedStatement.executeUpdate();
            }
        }catch(DataAccessException | SQLException ex){
            throw new DataAccessException(String.format("Unable to clear data: %s", ex.getMessage()));
        }
    }

    @Override
    public void createAuth(String username, String authToken) throws DataAccessException {
        // connects to the database and inserts a new auth into the auth table
        var insertStatement = "INSERT INTO auth (authToken, username) VALUES (?,?)";
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement(insertStatement)){
                preparedStatement.setString(1, authToken);
                preparedStatement.setString(2, username);
                preparedStatement.executeUpdate();
            }
        }catch(DataAccessException | SQLException ex){
            throw new DataAccessException(String.format("Unable to create auth: %s", ex.getMessage()));
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        // connects to the database and queries the auth table for a specific authToken
        try (var conn = DatabaseManager.getConnection()) {
            String queryStatement = "SELECT * FROM auth WHERE authToken=?";
            try (var preparedStatement = conn.prepareStatement(queryStatement)) {
                preparedStatement.setString(1,authToken);
                // if the authToken is found it is turned into a authData object and returned
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String returnedUserName = resultSet.getString("username");
                        String returnedAuthToken = resultSet.getString("authToken");
                        return new AuthData(returnedUserName, returnedAuthToken);
                    }
                }
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(String.format("Unable to read data: %s", ex.getMessage()));
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        // connects to the database and deletes a specific auth from the auth table
        var deleteStatement = "Delete from auth where authToken=?";
        try(var conn = DatabaseManager.getConnection()){
            try(var preparedStatement = conn.prepareStatement(deleteStatement)){
                preparedStatement.setString(1, authToken);
                preparedStatement.executeUpdate();
            }
        }catch(DataAccessException | SQLException ex){
            throw new DataAccessException(String.format("Unable to delete auth: %s", ex.getMessage()));
        }
    }
}
