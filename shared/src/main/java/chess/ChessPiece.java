package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor pieceTeamColor;
    PieceType currentPieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        pieceTeamColor = pieceColor;
        currentPieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceTeamColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return currentPieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoveList = new ArrayList<>();
        PieceType movePieceType = board.getPiece(myPosition).getPieceType();
        switch(movePieceType){
            case KING:
                return validMoveList;
            case QUEEN:
                validMoveList.addAll(calcDiagonalMoves(board,myPosition));
                validMoveList.addAll(calcStraightMoves(board,myPosition));
                return validMoveList;
            case BISHOP:
                validMoveList.addAll(calcDiagonalMoves(board,myPosition));
                return validMoveList;
            case KNIGHT:
                return validMoveList;
            case ROOK:
                validMoveList.addAll(calcStraightMoves(board,myPosition));
                return validMoveList;
            case PAWN:
                return validMoveList;
        }
        return validMoveList;
    }
    public Collection<ChessMove> calcDiagonalMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoveList = new ArrayList<>();
        int maxMoveNumber = 7;
        ArrayList<ChessMove> possibleMoveList1 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList2 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList3 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList4 = new ArrayList<>();

        for(int i = 0; i < maxMoveNumber; i++){
            possibleMoveList1.set(i, new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i),
                    null));
            possibleMoveList2.set(i, new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i),
                    null));
            possibleMoveList3.set(i, new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i),
                    null));
            possibleMoveList4.set(i, new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i),
                    null));
        }
        for(int i = 0; i < possibleMoveList1.size(); ++i){
            if(possibleMoveList1.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList1.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList1.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList1.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList1.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList1.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList1.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList1.get(i));
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < possibleMoveList2.size(); ++i){
            if(possibleMoveList2.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList2.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList2.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList2.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList2.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList2.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList2.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList2.get(i));
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < possibleMoveList3.size(); ++i){
            if(possibleMoveList3.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList3.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList3.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList3.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList3.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList3.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList3.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList3.get(i));
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < possibleMoveList4.size(); ++i){
            if(possibleMoveList4.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList4.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList4.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList4.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList4.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList4.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList4.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList4.get(i));
                    }
                    break;
                }
            }
        }
        return validMoveList;
    }
    public Collection<ChessMove> calcStraightMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoveList = new ArrayList<>();
        int maxMoveNumber = 7;
        ArrayList<ChessMove> possibleMoveList1 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList2 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList3 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList4 = new ArrayList<>();

        for(int i = 0; i < maxMoveNumber; i++){
            possibleMoveList1.set(i, new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()),
                    null));
            possibleMoveList2.set(i, new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()),
                    null));
            possibleMoveList3.set(i, new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow(), myPosition.getColumn() - i),
                    null));
            possibleMoveList4.set(i, new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow(), myPosition.getColumn() + i),
                    null));
        }
        for(int i = 0; i < possibleMoveList1.size(); ++i){
            if(possibleMoveList1.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList1.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList1.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList1.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList1.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList1.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList1.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList1.get(i));
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < possibleMoveList2.size(); ++i){
            if(possibleMoveList2.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList2.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList2.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList2.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList2.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList2.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList2.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList2.get(i));
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < possibleMoveList3.size(); ++i){
            if(possibleMoveList3.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList3.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList3.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList3.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList3.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList3.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList3.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList3.get(i));
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < possibleMoveList4.size(); ++i){
            if(possibleMoveList4.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList4.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList4.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList4.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList4.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList4.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList4.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList4.get(i));
                    }
                    break;
                }
            }
        }
        return validMoveList;
    }
    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoveList = new ArrayList<>();
        PieceType movePieceType = board.getPiece(myPosition).getPieceType();

        ArrayList<ChessMove> possibleMoveList1 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList2 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList3 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList4 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList5 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList6 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList7 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList8 = new ArrayList<>();
        int maxMoveNumber = 7;

        for(int i = 0; i < maxMoveNumber; i++){
//            possibleMoveList1.add(new ChessMove());
//            possibleMoveList1.get(i).setStartPosition(myPosition);
//            possibleMoveList2.add(new ChessMoveClass());
//            possibleMoveList2.get(i).setStartPosition(myPosition);
//            possibleMoveList3.add(new ChessMoveClass());
//            possibleMoveList3.get(i).setStartPosition(myPosition);
//            possibleMoveList4.add(new ChessMoveClass());
//            possibleMoveList4.get(i).setStartPosition(myPosition);
//            possibleMoveList5.add(new ChessMoveClass());
//            possibleMoveList5.get(i).setStartPosition(myPosition);
//            possibleMoveList6.add(new ChessMoveClass());
//            possibleMoveList6.get(i).setStartPosition(myPosition);
//            possibleMoveList7.add(new ChessMoveClass());
//            possibleMoveList7.get(i).setStartPosition(myPosition);
//            possibleMoveList8.add(new ChessMoveClass());
//            possibleMoveList8.get(i).setStartPosition(myPosition);
        }
        for(int i = 0; i < possibleMoveList1.size(); i++){
            possibleMoveList1.get(i).setEndPosition(
                    new ChessPositionClass(myPosition.getRow()+i+1,
                            myPosition.getColumn()+i+1));
            possibleMoveList2.get(i).setEndPosition(
                    new ChessPositionClass(myPosition.getRow()+i+1,
                            myPosition.getColumn()-i-1));
            possibleMoveList3.get(i).setEndPosition(
                    new ChessPositionClass(myPosition.getRow()-i-1,
                            myPosition.getColumn()+i+1));
            possibleMoveList4.get(i).setEndPosition(
                    new ChessPositionClass(myPosition.getRow()-i-1,
                            myPosition.getColumn()-i-1));
        }
        for(int i = 0; i < possibleMoveList5.size(); i++){
            possibleMoveList5.get(i).setEndPosition(
                    new ChessPositionClass(
                            myPosition.getRow()+i+1, myPosition.getColumn()));
        }
        for(int i = 0; i < possibleMoveList6.size(); i++){
            possibleMoveList6.get(i).setEndPosition(
                    new ChessPositionClass(
                            myPosition.getRow(), myPosition.getColumn()+i+1));
        }
        for(int i = 0; i < possibleMoveList7.size(); i++){
            possibleMoveList7.get(i).setEndPosition(
                    new ChessPositionClass(
                            myPosition.getRow()-i-1, myPosition.getColumn()));
        }
        for(int i = 0; i < possibleMoveList8.size(); i++){
            possibleMoveList8.get(i).setEndPosition(
                    new ChessPositionClass(
                            myPosition.getRow(), myPosition.getColumn()-i-1));
        }

        for(int i = 0; i < possibleMoveList1.size(); ++i){
            if(possibleMoveList1.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList1.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList1.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList1.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList1.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList1.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList1.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList1.get(i));
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < possibleMoveList2.size(); ++i){
            if(possibleMoveList2.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList2.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList2.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList2.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList2.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList2.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList2.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList2.get(i));
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < possibleMoveList3.size(); ++i){
            if(possibleMoveList3.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList3.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList3.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList3.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList3.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList3.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList3.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList3.get(i));
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < possibleMoveList4.size(); ++i){
            if(possibleMoveList4.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList4.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList4.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList4.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList4.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList4.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList4.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList4.get(i));
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < possibleMoveList5.size(); ++i){
            if(possibleMoveList5.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList5.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList5.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList5.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList5.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList5.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList5.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList5.get(i));
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < possibleMoveList6.size(); ++i){
            if(possibleMoveList6.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList6.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList6.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList6.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList6.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList6.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList6.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList6.get(i));
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < possibleMoveList7.size(); ++i){
            if(possibleMoveList7.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList7.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList7.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList7.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList7.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList7.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList7.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList7.get(i));
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < possibleMoveList8.size(); ++i){
            if(possibleMoveList8.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList8.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList8.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList8.get(i).getEndPosition().getColumn() <= 8){
                if(board.getPiece(possibleMoveList8.get(i).getEndPosition()) == null){
                    validMoveList.add(possibleMoveList8.get(i));
                }
                else{
                    if(this.getTeamColor() !=
                            board.getPiece(possibleMoveList8.get(i).getEndPosition()).getTeamColor()){
                        validMoveList.add(possibleMoveList8.get(i));
                    }
                    break;
                }
            }
        }
        return
    }

}
