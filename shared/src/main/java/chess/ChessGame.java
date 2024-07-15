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
    TeamColor currentTeamTurn = TeamColor.WHITE;
    ChessBoard currentChessBoard = new ChessBoard();

    public ChessGame() {
        currentChessBoard.resetBoard();
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
        // check is the startPosition has a piece
        if(currentChessBoard.getPiece(startPosition) == null){
            return null;
        }
        if(startPosition.getRow() < 1 || startPosition.getRow() > 8 ||
                startPosition.getColumn() < 1 || startPosition.getColumn() > 8){
            return null;
        }
        ChessPiece startingPiece = currentChessBoard.getPiece(startPosition);
        TeamColor startingTeamColor = startingPiece.getTeamColor();
        Collection<ChessMove> possiblePieceMoves = startingPiece.pieceMoves(currentChessBoard, startPosition);
        Collection<ChessMove> validMoveList = new ArrayList<>();

        for(ChessMove possibleMove: possiblePieceMoves){
            ChessPiece replacedPieceVar;
            if(currentChessBoard.getPiece(possibleMove.getEndPosition()) != null){
                replacedPieceVar = currentChessBoard.getPiece(possibleMove.getEndPosition());
            }else{
                replacedPieceVar = null;
            }
            currentChessBoard.addPiece(possibleMove.getEndPosition(), startingPiece);
            currentChessBoard.addPiece(startPosition, null);
            if(!isInCheck(startingTeamColor)){
                validMoveList.add(possibleMove);
            }
            currentChessBoard.addPiece(possibleMove.getEndPosition(), replacedPieceVar);
            currentChessBoard.addPiece(possibleMove.startingPoint, startingPiece);
        }
        return validMoveList;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> validMoveList = new ArrayList<>();

        if(currentChessBoard.getPiece(move.getStartPosition()) == null){
            throw new InvalidMoveException();
        }
        if(currentChessBoard.getPiece(move.getStartPosition()).getTeamColor() != currentTeamTurn){
            throw new InvalidMoveException();
        }
        validMoveList = this.validMoves(move.getStartPosition());
        if(validMoveList == null){
            throw new InvalidMoveException();
        }
        boolean foundValidMove = false;
        for(ChessMove currentMove: validMoveList){
            ChessPiece promotedPiece;
            if(move.equals(currentMove)){
                foundValidMove = true;
                if(move.getPromotionPiece() != null){
                    promotedPiece =
                            new ChessPiece(currentChessBoard.getPiece(
                                    move.getStartPosition()).getTeamColor(), move.getPromotionPiece());
                }else{
                    promotedPiece = currentChessBoard.getPiece(move.getStartPosition());
                }
                currentChessBoard.addPiece(move.getEndPosition(), promotedPiece);
                currentChessBoard.addPiece(move.getStartPosition(), null);
                if(currentTeamTurn == TeamColor.WHITE){
                    currentTeamTurn = TeamColor.BLACK;
                }else{
                    currentTeamTurn = TeamColor.WHITE;
                }
                break;
            }
        }
        if(!foundValidMove){
            throw new InvalidMoveException();
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
        outerLoop:
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(currentChessBoard.getPiece(new ChessPosition(i+1,j+1)) != null){
                    if(currentChessBoard.getPiece(
                            new ChessPosition(i+1,j+1)).getPieceType() ==
                            ChessPiece.PieceType.KING){
                        if(currentChessBoard.getPiece(
                                new ChessPosition(i+1,j+1)).getTeamColor() == teamColor){
                            kingPosition = new ChessPosition(i+1,j+1);
                            break outerLoop;
                        }
                    }
                }
            }
        }
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(currentChessBoard.getPiece(new ChessPosition(i+1,j+1)) != null){
                    if(currentChessBoard.getPiece(
                            new ChessPosition(i+1,j+1)).getTeamColor() != teamColor){
                        ChessMove[] possibleCheckMoves;
                        possibleCheckMoves = currentChessBoard.getPiece(
                                new ChessPosition(i + 1, j + 1)).
                                pieceMoves(currentChessBoard,
                                        new ChessPosition(i + 1, j + 1)).toArray(
                                                new ChessMove[0]);
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
        // if the player is not in check they are not in checkmate
        if(!isInCheck(teamColor)){
            return false;
        }

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                ChessPosition currentStartingPosition = new ChessPosition(i+1,j+1);
                if(currentChessBoard.getPiece(currentStartingPosition) != null){
                    if(currentChessBoard.getPiece(currentStartingPosition).getTeamColor() == teamColor){
                        Collection<ChessMove> possibleCurrentMoves =
                                this.validMoves(currentStartingPosition);
                        if(possibleCurrentMoves.size() > 0){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // if the player is in check they are not in stalemate
        if(isInCheck(teamColor)){
            return false;
        }

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                ChessPosition currentStartingPosition = new ChessPosition(i+1,j+1);
                if(currentChessBoard.getPiece(currentStartingPosition) != null){
                    if(currentChessBoard.getPiece(currentStartingPosition).getTeamColor() == teamColor){
                        Collection<ChessMove> possibleCurrentMoves =
                                this.validMoves(currentStartingPosition);
                        if(possibleCurrentMoves.size() > 0){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
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
        return currentTeamTurn == chessGame.currentTeamTurn &&
                Objects.equals(currentChessBoard, chessGame.currentChessBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentChessBoard);
    }
}