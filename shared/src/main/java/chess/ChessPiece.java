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
                validMoveList.addAll(calcKnightMoves(board, myPosition));
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
    public Collection<ChessMove> calcKnightMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoveList = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList = new ArrayList<>();
        possibleMoveList.set(0,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn()-1),
                null));
        possibleMoveList.set(1,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()-2),
                null));
        possibleMoveList.set(2,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn()+1),
                null));
        possibleMoveList.set(3,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()+2),
                null));
        possibleMoveList.set(4,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn()+1),
                null));
        possibleMoveList.set(5,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()+2),
                null));
        possibleMoveList.set(6,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn()-1),
                null));
        possibleMoveList.set(7,new ChessMove(myPosition,
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
    public Collection<ChessMove> calcPawnMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoveList = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList = new ArrayList<>();
        boolean isFirstMove = true;
        if(board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
            if(myPosition.getColumn() == 2){
                possibleMoveList.set(0, new ChessMove(myPosition,
                        new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),
                        null));
                possibleMoveList.set(1, new ChessMove(myPosition,
                        new ChessPosition(myPosition.getRow(), myPosition.getColumn()+2),
                        null));
            }else{
                possibleMoveList.set(0, new ChessMove(myPosition,
                        new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),
                        null));
            }
            if((board.getPiece(new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1))
             != null) || (board.getPiece(new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1))
                    .getTeamColor() != this.getTeamColor())){
            }
        }
        else if(board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK){

        }
        possibleMoveList.set(0,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()),
                null));
        possibleMoveList.set(1,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()-1),
                null));
        possibleMoveList.set(2,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1),
                null));
        possibleMoveList.set(3,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()-1),
                null));
        possibleMoveList.set(4,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()),
                null));
        possibleMoveList.set(5,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()+1),
                null));
        possibleMoveList.set(6,new ChessMove(myPosition,
                new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1),
                null));
        possibleMoveList.set(7,new ChessMove(myPosition,
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
    public Collection<ChessMove> calcKingMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoveList = new ArrayList<>();
        ChessMove forwardMove = new ChessMoveClass();
        ChessMove positiveDiagonalMove = new ChessMoveClass();
        ChessMove negativeDiagonalMove = new ChessMoveClass();
        boolean isFirstMove = true;

        // as there are only 3 options, depending on if the piece is a white or black piece
        // they can be set individually and checked for valid state individually
        if(this.getTeamColor() == ChessGame.TeamColor.WHITE){
            forwardMove.setStartPosition(myPosition);
            forwardMove.setEndPosition(new ChessPositionClass(
                    myPosition.getRow()+1, myPosition.getColumn()));
            if(board.getPiece(forwardMove.getEndPosition()) == null &&
                    forwardMove.getEndPosition().getRow() >= 1 &&
                    forwardMove.getEndPosition().getRow() <= 8 &&
                    forwardMove.getEndPosition().getColumn() >= 1 &&
                    forwardMove.getEndPosition().getColumn() <= 8){
                if(forwardMove.getStartPosition().getRow() == 2){
                    ChessMove doubleForwardMove = new ChessMoveClass();
                    doubleForwardMove.setStartPosition(myPosition);
                    doubleForwardMove.setEndPosition(new ChessPositionClass(
                            myPosition.getRow()+2, myPosition.getColumn()));
                    if(board.getPiece(doubleForwardMove.getEndPosition()) == null){
                        validMoveList.add(doubleForwardMove);
                    }
                }
                //if the pawn has reached the other side then it adds the promotion moves
                if(forwardMove.getEndPosition().getRow() == 8){
                    validMoveList.add(new ChessMoveClass(
                            forwardMove.getStartPosition(),
                            forwardMove.getEndPosition(), PieceType.QUEEN));
                    validMoveList.add(new ChessMoveClass(
                            forwardMove.getStartPosition(),
                            forwardMove.getEndPosition(), PieceType.BISHOP));
                    validMoveList.add(new ChessMoveClass(
                            forwardMove.getStartPosition(),
                            forwardMove.getEndPosition(), PieceType.KNIGHT));
                    validMoveList.add(new ChessMoveClass(
                            forwardMove.getStartPosition(),
                            forwardMove.getEndPosition(), PieceType.ROOK));
                }
                else{
                    validMoveList.add(forwardMove);
                }
            }

            positiveDiagonalMove.setStartPosition(myPosition);
            positiveDiagonalMove.setEndPosition(new ChessPositionClass(
                    myPosition.getRow()+1, myPosition.getColumn()+1));
            if(board.getPiece(positiveDiagonalMove.getEndPosition()) != null &&
                    this.getTeamColor() != board.getPiece(
                            positiveDiagonalMove.getEndPosition()).getTeamColor() &&
                    positiveDiagonalMove.getEndPosition().getRow() >= 1 &&
                    positiveDiagonalMove.getEndPosition().getRow() <= 8 &&
                    positiveDiagonalMove.getEndPosition().getColumn() >= 1 &&
                    positiveDiagonalMove.getEndPosition().getColumn() <= 8){
                //if the pawn has reached the other side then it adds the promotion moves
                if(positiveDiagonalMove.getEndPosition().getRow() == 8){
                    validMoveList.add(new ChessMoveClass(
                            positiveDiagonalMove.getStartPosition(),
                            positiveDiagonalMove.getEndPosition(), PieceType.QUEEN));
                    validMoveList.add(new ChessMoveClass(
                            positiveDiagonalMove.getStartPosition(),
                            positiveDiagonalMove.getEndPosition(), PieceType.BISHOP));
                    validMoveList.add(new ChessMoveClass(
                            positiveDiagonalMove.getStartPosition(),
                            positiveDiagonalMove.getEndPosition(), PieceType.KNIGHT));
                    validMoveList.add(new ChessMoveClass(
                            positiveDiagonalMove.getStartPosition(),
                            positiveDiagonalMove.getEndPosition(), PieceType.ROOK));
                }
                else{
                    validMoveList.add(positiveDiagonalMove);
                }
            }


            negativeDiagonalMove.setStartPosition(myPosition);
            negativeDiagonalMove.setEndPosition(new ChessPositionClass(
                    myPosition.getRow()+1, myPosition.getColumn()-1));
            if(board.getPiece(negativeDiagonalMove.getEndPosition()) != null &&
                    this.getTeamColor() != board.getPiece(
                            negativeDiagonalMove.getEndPosition()).getTeamColor() &&
                    negativeDiagonalMove.getEndPosition().getRow() >= 1 &&
                    negativeDiagonalMove.getEndPosition().getRow() <= 8 &&
                    negativeDiagonalMove.getEndPosition().getColumn() >= 1 &&
                    negativeDiagonalMove.getEndPosition().getColumn() <= 8){
                //if the pawn has reached the other side then it adds the promotion moves
                if(negativeDiagonalMove.getEndPosition().getRow() == 8){
                    validMoveList.add(new ChessMoveClass(
                            negativeDiagonalMove.getStartPosition(),
                            negativeDiagonalMove.getEndPosition(), PieceType.QUEEN));
                    validMoveList.add(new ChessMoveClass(
                            negativeDiagonalMove.getStartPosition(),
                            negativeDiagonalMove.getEndPosition(), PieceType.BISHOP));
                    validMoveList.add(new ChessMoveClass(
                            negativeDiagonalMove.getStartPosition(),
                            negativeDiagonalMove.getEndPosition(), PieceType.KNIGHT));
                    validMoveList.add(new ChessMove(
                            negativeDiagonalMove.getStartPosition(),
                            negativeDiagonalMove.getEndPosition(), PieceType.ROOK));
                }
                else{
                    validMoveList.add(negativeDiagonalMove);
                }
            }
        }
        else{
            forwardMove.setStartPosition(myPosition);
            forwardMove.setEndPosition(new ChessPosition(
                    myPosition.getRow()-1, myPosition.getColumn()));
            if(board.getPiece(forwardMove.getEndPosition()) == null &&
                    forwardMove.getEndPosition().getRow() >= 1 &&
                    forwardMove.getEndPosition().getRow() <= 8 &&
                    forwardMove.getEndPosition().getColumn() >= 1 &&
                    forwardMove.getEndPosition().getColumn() <= 8){
                if(forwardMove.getStartPosition().getRow() == 7){
                    ChessMove doubleForwardMove = new ChessMove();
                    doubleForwardMove.setStartPosition(myPosition);
                    doubleForwardMove.setEndPosition(new ChessPosition(
                            myPosition.getRow()-2, myPosition.getColumn()));
                    if(board.getPiece(doubleForwardMove.getEndPosition()) == null){
                        validMoveList.add(doubleForwardMove);
                    }
                }
                //if the pawn has reached the other side then it adds the promotion moves
                if(forwardMove.getEndPosition().getRow() == 1){
                    validMoveList.add(new ChessMove(
                            forwardMove.getStartPosition(),
                            forwardMove.getEndPosition(), PieceType.QUEEN));
                    validMoveList.add(new ChessMove(
                            forwardMove.getStartPosition(),
                            forwardMove.getEndPosition(), PieceType.BISHOP));
                    validMoveList.add(new ChessMove(
                            forwardMove.getStartPosition(),
                            forwardMove.getEndPosition(), PieceType.KNIGHT));
                    validMoveList.add(new ChessMove(
                            forwardMove.getStartPosition(),
                            forwardMove.getEndPosition(), PieceType.ROOK));
                }
                else{
                    validMoveList.add(forwardMove);
                }
            }

            positiveDiagonalMove.setStartPosition(myPosition);
            positiveDiagonalMove.setEndPosition(new ChessPosition(
                    myPosition.getRow()-1, myPosition.getColumn()+1));
            if(board.getPiece(positiveDiagonalMove.getEndPosition()) != null &&
                    this.getTeamColor() != board.getPiece(
                            positiveDiagonalMove.getEndPosition()).getTeamColor() &&
                    positiveDiagonalMove.getEndPosition().getRow() >= 1 &&
                    positiveDiagonalMove.getEndPosition().getRow() <= 8 &&
                    positiveDiagonalMove.getEndPosition().getColumn() >= 1 &&
                    positiveDiagonalMove.getEndPosition().getColumn() <= 8){
                if(positiveDiagonalMove.getEndPosition().getRow() == 1){
                    validMoveList.add(new ChessMove(
                            positiveDiagonalMove.getStartPosition(),
                            positiveDiagonalMove.getEndPosition(), PieceType.QUEEN));
                    validMoveList.add(new ChessMove(
                            positiveDiagonalMove.getStartPosition(),
                            positiveDiagonalMove.getEndPosition(), PieceType.BISHOP));
                    validMoveList.add(new ChessMove(
                            positiveDiagonalMove.getStartPosition(),
                            positiveDiagonalMove.getEndPosition(), PieceType.KNIGHT));
                    validMoveList.add(new ChessMove(
                            positiveDiagonalMove.getStartPosition(),
                            positiveDiagonalMove.getEndPosition(), PieceType.ROOK));
                }
                else{
                    validMoveList.add(positiveDiagonalMove);
                }
            }


            negativeDiagonalMove.setStartPosition(myPosition);
            negativeDiagonalMove.setEndPosition(new ChessPosition(
                    myPosition.getRow()-1, myPosition.getColumn()-1));
            if(board.getPiece(negativeDiagonalMove.getEndPosition()) != null &&
                    this.getTeamColor() != board.getPiece(
                            negativeDiagonalMove.getEndPosition()).getTeamColor() &&
                    negativeDiagonalMove.getEndPosition().getRow() >= 1 &&
                    negativeDiagonalMove.getEndPosition().getRow() <= 8 &&
                    negativeDiagonalMove.getEndPosition().getColumn() >= 1 &&
                    negativeDiagonalMove.getEndPosition().getColumn() <= 8){
                if(negativeDiagonalMove.getEndPosition().getRow() == 1){
                    validMoveList.add(new ChessMove(
                            negativeDiagonalMove.getStartPosition(),
                            negativeDiagonalMove.getEndPosition(), PieceType.QUEEN));
                    validMoveList.add(new ChessMove(
                            negativeDiagonalMove.getStartPosition(),
                            negativeDiagonalMove.getEndPosition(), PieceType.BISHOP));
                    validMoveList.add(new ChessMove(
                            negativeDiagonalMove.getStartPosition(),
                            negativeDiagonalMove.getEndPosition(), PieceType.KNIGHT));
                    validMoveList.add(new ChessMove(
                            negativeDiagonalMove.getStartPosition(),
                            negativeDiagonalMove.getEndPosition(), PieceType.ROOK));
                }
                else{
                    validMoveList.add(negativeDiagonalMove);
                }
            }
        }
        return validMoveList;
    }
}
