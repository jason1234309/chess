package dataaccess;

import model.AuthData;
import model.UserData;

import java.sql.SQLException;

public class SqlAuthDAO implements AuthDAO{
    public SqlAuthDAO()throws DataAccessException{
        DatabaseManager.createDatabase();
        final String dropTableStatement = "DROP TABLE IF EXISTS auth";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(dropTableStatement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
        final String[] createTableStatements = {
                """
            CREATE TABLE IF NOT EXISTS  auth (
            `authToken` varchar(256) NOT NULL,
            `username` varchar(256) NOT NULL,              
            PRIMARY KEY (`authToken`)
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
    public void clearAuthDataBase() throws DataAccessException{
        var statement = "DELETE from auth";
        try(var conn = DatabaseManager.getConnection()){
            try(var ps = conn.prepareStatement(statement)){
                ps.executeUpdate();
            }
        }catch(DataAccessException | SQLException ex){
            throw new DataAccessException(String.format("Unable to clear data: %s", ex.getMessage()));
        }
    }

    @Override
    public void createAuth(String username, String authToken) throws DataAccessException {
        var statement = "INSERT INTO auth (authToken, username) VALUES (?,?)";
        try(var conn = DatabaseManager.getConnection()){
            try(var ps = conn.prepareStatement(statement)){
                ps.setString(1, authToken);
                ps.setString(2, username);
                ps.executeUpdate();
            }
        }catch(DataAccessException | SQLException ex){
            throw new DataAccessException(String.format("Unable to create auth: %s", ex.getMessage()));
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT * FROM auth WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1,authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String returnedUserName = rs.getString("username");
                        String returnedAuthToken = rs.getString("authToken");
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
        var statement = "Delete from auth where authToken=?";
        try(var conn = DatabaseManager.getConnection()){
            try(var ps = conn.prepareStatement(statement)){
                ps.setString(1, authToken);
                ps.executeUpdate();
            }
        }catch(DataAccessException | SQLException ex){
            throw new DataAccessException(String.format("Unable to delete auth: %s", ex.getMessage()));
        }
    }
}
