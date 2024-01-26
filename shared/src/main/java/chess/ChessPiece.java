package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 * not a big fan of adding comments
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
                validMoveList.addAll(calcKingMoves(board,myPosition));
                return validMoveList;
            case QUEEN:
                validMoveList.addAll(calcDiagonalMoves(board,myPosition));
                validMoveList.addAll(calcStraightMoves(board,myPosition));
                return validMoveList;
            case BISHOP:
                validMoveList.addAll(calcDiagonalMoves(board,myPosition));
                return validMoveList;
            case KNIGHT:
                validMoveList.addAll(calcKnightMoves(board, myPosition));
                return validMoveList;
            case ROOK:
                validMoveList.addAll(calcStraightMoves(board,myPosition));
                return validMoveList;
            case PAWN:
                validMoveList.addAll(calcPawnMoves(board, myPosition));
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

        for(int i = 1; i < maxMoveNumber; i++){
            possibleMoveList1.add(new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i),
                    null));
            possibleMoveList2.add(new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i),
                    null));
            possibleMoveList3.add(new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i),
                    null));
            possibleMoveList4.add(new ChessMove(myPosition,
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

        for(int i = 1; i < maxMoveNumber; i++){
            possibleMoveList1.add(new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()),
                    null));
            possibleMoveList2.add(new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()),
                    null));
            possibleMoveList3.add(new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow(), myPosition.getColumn() - i),
                    null));
            possibleMoveList4.add(new ChessMove(myPosition,
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
    public Collection<ChessMove> calcKnightMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoveList = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList = new ArrayList<>();
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn()-1),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()-2),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn()+1),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()+2),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn()+1),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()+2),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn()-1),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()-2),
                null));
        for(int i = 0; i < possibleMoveList.size(); i++) {
            if (possibleMoveList.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList.get(i).getEndPosition().getColumn() <= 8) {
                if (board.getPiece(possibleMoveList.get(i).getEndPosition()) == null) {
                    validMoveList.add(possibleMoveList.get(i));
                } else {
                    if (this.getTeamColor() !=
                            board.getPiece(possibleMoveList.get(i).getEndPosition()).getTeamColor()) {
                        validMoveList.add(possibleMoveList.get(i));
                    }
                }
            }
        }
        return validMoveList;
    }
    public Collection<ChessMove> calcKingMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoveList = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList = new ArrayList<>();

        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()-1),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()-1),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()+1),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),
                null));
        possibleMoveList.add(new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()+1),
                null));
        for(int i = 0; i < possibleMoveList.size(); i++) {
            if (possibleMoveList.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList.get(i).getEndPosition().getColumn() <= 8) {
                if (board.getPiece(possibleMoveList.get(i).getEndPosition()) == null) {
                    validMoveList.add(possibleMoveList.get(i));
                } else {
                    if (this.getTeamColor() !=
                            board.getPiece(possibleMoveList.get(i).getEndPosition()).getTeamColor()) {
                        validMoveList.add(possibleMoveList.get(i));
                    }
                }
            }
        }
        return validMoveList;
    }
    public Collection<ChessMove> calcPawnMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoveList = new ArrayList<>();
        if(board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
            if(myPosition.getRow() == 2) {
                if (board.getPiece(new ChessPosition(myPosition.getRow()+1,
                        myPosition.getColumn())) == null) {
                    validMoveList.add(new ChessMove(myPosition,
                            new ChessPosition(myPosition.getRow()+1,
                                    myPosition.getColumn()),null));
                    if (board.getPiece(new ChessPosition(myPosition.getRow()+2,
                            myPosition.getColumn())) == null) {
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()+2,
                                        myPosition.getColumn()),null));
                    }
                }
                if(board.getPiece(new ChessPosition(myPosition.getRow()+1,
                        myPosition.getColumn() - 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow()+1,
                            myPosition.getColumn() - 1)).getTeamColor()
                            != this.getTeamColor()){
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()+1,
                                        myPosition.getColumn() - 1), null));
                    }
                }
                if(board.getPiece(new ChessPosition(myPosition.getRow()+1,
                        myPosition.getColumn() + 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow()+1,
                            myPosition.getColumn() + 1)).getTeamColor()
                            != this.getTeamColor()){
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()+1,
                                        myPosition.getColumn() + 1), null));
                    }
                }
            }
            else if(myPosition.getRow()+1 == 8){
                if (board.getPiece(new ChessPosition(myPosition.getRow()+1,
                        myPosition.getColumn())) == null) {
                    validMoveList.add(new ChessMove(myPosition,
                            new ChessPosition(myPosition.getRow()+1,
                                myPosition.getColumn()),PieceType.QUEEN));
                    validMoveList.add(new ChessMove(myPosition,
                            new ChessPosition(myPosition.getRow()+1,
                                    myPosition.getColumn()),PieceType.BISHOP));
                    validMoveList.add(new ChessMove(myPosition,
                            new ChessPosition(myPosition.getRow()+1,
                                    myPosition.getColumn()),PieceType.KNIGHT));
                    validMoveList.add(new ChessMove(myPosition,
                            new ChessPosition(myPosition.getRow()+1,
                                    myPosition.getColumn()),PieceType.ROOK));
                }
                if(board.getPiece(new ChessPosition(myPosition.getRow()+1,
                        myPosition.getColumn() - 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow()+1,
                            myPosition.getColumn() - 1)).getTeamColor()
                            != this.getTeamColor()){
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()+1,
                                        myPosition.getColumn() - 1),PieceType.QUEEN));
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()+1,
                                        myPosition.getColumn() - 1),PieceType.BISHOP));
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()+1,
                                        myPosition.getColumn() - 1),PieceType.KNIGHT));
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()+1,
                                        myPosition.getColumn() - 1),PieceType.ROOK));
                    }
                }
                if(board.getPiece(new ChessPosition(myPosition.getRow()+1,
                        myPosition.getColumn() + 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow()+1,
                            myPosition.getColumn() + 1)).getTeamColor()
                            != this.getTeamColor()){
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()+1,
                                        myPosition.getColumn() + 1),PieceType.QUEEN));
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()+1,
                                        myPosition.getColumn() + 1),PieceType.BISHOP));
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()+1,
                                        myPosition.getColumn() + 1),PieceType.KNIGHT));
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()+1,
                                        myPosition.getColumn() + 1),PieceType.ROOK));
                    }
                }
            }
            else{
                if (board.getPiece(new ChessPosition(myPosition.getRow()+1,
                        myPosition.getColumn())) == null) {
                    validMoveList.add(new ChessMove(myPosition,
                            new ChessPosition(myPosition.getRow()+1,
                                    myPosition.getColumn()),null));
                }
                if(board.getPiece(new ChessPosition(myPosition.getRow()+1,
                        myPosition.getColumn() - 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow()+1,
                            myPosition.getColumn() - 1)).getTeamColor()
                            != this.getTeamColor()){
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()+1,
                                        myPosition.getColumn() - 1), null));
                    }
                }
                if(board.getPiece(new ChessPosition(myPosition.getRow()+1,
                        myPosition.getColumn() + 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow()+1,
                            myPosition.getColumn() + 1)).getTeamColor()
                            != this.getTeamColor()){
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()+1,
                                        myPosition.getColumn() + 1), null));
                    }
                }
            }
        }
        else if(board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
            if(myPosition.getRow() == 7) {
                if (board.getPiece(new ChessPosition(myPosition.getRow()-1,
                        myPosition.getColumn())) == null) {
                    validMoveList.add(new ChessMove(myPosition,
                            new ChessPosition(myPosition.getRow()-1,
                                    myPosition.getColumn()),null));
                    if (board.getPiece(new ChessPosition(myPosition.getRow()-2,
                            myPosition.getColumn())) == null) {
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()-2,
                                        myPosition.getColumn()),null));
                    }
                }
                if(board.getPiece(new ChessPosition(myPosition.getRow()-1,
                        myPosition.getColumn() - 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow()-1,
                            myPosition.getColumn() - 1)).getTeamColor()
                            != this.getTeamColor()){
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()-1,
                                        myPosition.getColumn() - 1), null));
                    }
                }
                if(board.getPiece(new ChessPosition(myPosition.getRow()-1,
                        myPosition.getColumn() + 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow()-1,
                            myPosition.getColumn() + 1)).getTeamColor()
                            != this.getTeamColor()){
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()-1,
                                        myPosition.getColumn()+1), null));
                    }
                }
            }
            else if(myPosition.getRow()-1 == 1){
                if (board.getPiece(new ChessPosition(myPosition.getRow()-1,
                        myPosition.getColumn())) == null) {
                    validMoveList.add(new ChessMove(myPosition,
                            new ChessPosition(myPosition.getRow()-1,
                                    myPosition.getColumn()),PieceType.QUEEN));
                    validMoveList.add(new ChessMove(myPosition,
                            new ChessPosition(myPosition.getRow()-1,
                                    myPosition.getColumn()),PieceType.BISHOP));
                    validMoveList.add(new ChessMove(myPosition,
                            new ChessPosition(myPosition.getRow()-1,
                                    myPosition.getColumn()),PieceType.KNIGHT));
                    validMoveList.add(new ChessMove(myPosition,
                            new ChessPosition(myPosition.getRow()-1,
                                    myPosition.getColumn()),PieceType.ROOK));
                }
                if(board.getPiece(new ChessPosition(myPosition.getRow()-1,
                        myPosition.getColumn() - 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow()-1,
                            myPosition.getColumn() - 1)).getTeamColor()
                            != this.getTeamColor()){
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()-1,
                                        myPosition.getColumn() - 1),PieceType.QUEEN));
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()-1,
                                        myPosition.getColumn() - 1),PieceType.BISHOP));
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()-1,
                                        myPosition.getColumn() - 1),PieceType.KNIGHT));
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()-1,
                                        myPosition.getColumn() - 1),PieceType.ROOK));
                    }
                }
                if(board.getPiece(new ChessPosition(myPosition.getRow()-1,
                        myPosition.getColumn()+1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow()-1,
                            myPosition.getColumn()+1)).getTeamColor()
                            != this.getTeamColor()){
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()-1,
                                        myPosition.getColumn()+1),PieceType.QUEEN));
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()-1,
                                        myPosition.getColumn()+1),PieceType.BISHOP));
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()-1,
                                        myPosition.getColumn()+1),PieceType.KNIGHT));
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()-1,
                                        myPosition.getColumn()+1),PieceType.ROOK));
                    }
                }
            }
            else{
                if (board.getPiece(new ChessPosition(myPosition.getRow()-1,
                        myPosition.getColumn())) == null) {
                    validMoveList.add(new ChessMove(myPosition,
                            new ChessPosition(myPosition.getRow()-1,
                                    myPosition.getColumn()),null));
                }
                if(board.getPiece(new ChessPosition(myPosition.getRow()-1,
                        myPosition.getColumn() - 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow()-1,
                            myPosition.getColumn() - 1)).getTeamColor()
                            != this.getTeamColor()){
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()-1,
                                        myPosition.getColumn() - 1), null));
                    }
                }
                if(board.getPiece(new ChessPosition(myPosition.getRow()-1,
                        myPosition.getColumn()+1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow()-1,
                            myPosition.getColumn()+1)).getTeamColor()
                            != this.getTeamColor()){
                        validMoveList.add(new ChessMove(myPosition,
                                new ChessPosition(myPosition.getRow()-1,
                                        myPosition.getColumn()+1), null));
                    }
                }
            }
        }
        return validMoveList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceTeamColor == that.pieceTeamColor && currentPieceType == that.currentPieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceTeamColor, currentPieceType);
    }
}
