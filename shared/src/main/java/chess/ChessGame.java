package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor currentTeamTurn;
    ChessBoard currentChessBoard = new ChessBoard();

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentTeamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        currentTeamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if(currentChessBoard.getPiece(move.getStartPosition()) == null){
            throw new InvalidMoveException();
        }
        ChessPiece currentChessPiece = currentChessBoard.getPiece(
                move.getStartPosition());
        boolean isValidMove = false;

        if(currentChessPiece.getTeamColor() != currentTeamTurn){
            throw new InvalidMoveException();
        }
        if(move.getEndPosition().getRow() < 1 || move.getEndPosition().getRow() > 8 ||
                move.getEndPosition().getColumn() < 1 || move.getEndPosition().getColumn() > 8){
            throw new InvalidMoveException();
        }
        if(currentChessBoard.getPiece(move.getEndPosition()) != null){
            if(currentChessBoard.getPiece(move.getEndPosition()).getTeamColor() ==
                    currentChessPiece.getTeamColor()){
                throw new InvalidMoveException();
            }
        }
        Collection<ChessMove> pieceValidMoveList = new ArrayList<>();
        pieceValidMoveList = currentChessPiece.pieceMoves(
                currentChessBoard, move.getStartPosition());
        for(ChessMove currentValidMove: pieceValidMoveList){
            if(move.equals(currentValidMove)){
                isValidMove = true;
                break;
            }
        }
        if(!isValidMove){
            throw new InvalidMoveException();
        }
        if(isInCheck(currentTeamTurn)){
            currentChessBoard.removePiece(move.getStartPosition());
            currentChessBoard.addPiece(move.getEndPosition(), currentChessPiece);
            if(isInCheck(currentTeamTurn)){
                currentChessBoard.removePiece(move.getEndPosition());
                currentChessBoard.addPiece(move.getStartPosition(), currentChessPiece);
                throw new InvalidMoveException();
            }
        }else{
            currentChessBoard.removePiece(move.getStartPosition());
            currentChessBoard.addPiece(move.getEndPosition(), currentChessPiece);
        }

        if(currentTeamTurn == TeamColor.WHITE){
            currentTeamTurn = TeamColor.BLACK;
        }else{
            currentTeamTurn = TeamColor.WHITE;
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = null;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(currentChessBoard.getPiece(new ChessPosition(i+1,j+1)) != null){
                    if(currentChessBoard.getPiece(new ChessPosition(i+1,j+1)).getPieceType() == ChessPiece.PieceType.KING){
                        if(currentChessBoard.getPiece(new ChessPosition(i+1,j+1)).getTeamColor() == teamColor){
                            kingPosition = new ChessPosition(i+1,j+1);
                            break;
                        }
                    }
                }
            }
        }
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(currentChessBoard.getPiece(new ChessPosition(i+1,j+1)) != null){
                    if(currentChessBoard.getPiece(new ChessPosition(i+1,j+1)).getTeamColor() != teamColor){
                        ChessMove[] possibleCheckMoves;
                        possibleCheckMoves = currentChessBoard.getPiece(new ChessPosition(i + 1, j + 1)).
                                pieceMoves(currentChessBoard, new ChessPosition(i + 1, j + 1)).toArray(new ChessMove[0]);
                        for(int k = 0; k < possibleCheckMoves.length; k++){
                            if(possibleCheckMoves[k].getEndPosition().equals(kingPosition)){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        currentChessBoard = (ChessBoard) board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return currentChessBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return currentTeamTurn == chessGame.currentTeamTurn && Objects.equals(currentChessBoard, chessGame.currentChessBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentTeamTurn, currentChessBoard);
    }
}
