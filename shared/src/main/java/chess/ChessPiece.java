package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor teamColor;
    ChessPiece.PieceType currentPieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        teamColor = pieceColor;
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

        return teamColor;
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
        Collection<ChessMove> validMoveList = new ArrayList();
        ChessPiece currentPiece = board.getPiece(myPosition);
        ChessPiece.PieceType myCurrentPieceType = currentPiece.getPieceType();
        switch(myCurrentPieceType){
            case KING:
                validMoveList.addAll(calcKingMoves(board, myPosition));
                return validMoveList;
            case QUEEN:
                validMoveList.addAll(calcDiagonalMoves(board, myPosition));
                validMoveList.addAll(calcStraightMoves(board, myPosition));
                return validMoveList;
            case BISHOP:
                validMoveList.addAll(calcDiagonalMoves(board, myPosition));
                return validMoveList;
            case KNIGHT:
                validMoveList.addAll(calcKnightMoves(board, myPosition));
                return validMoveList;
            case ROOK:
                validMoveList.addAll(calcStraightMoves(board, myPosition));
                return validMoveList;
            case PAWN:
                validMoveList.addAll(calcPawnMoves(board, myPosition));
                return validMoveList;
        }
        return null;
    }
    private Collection<ChessMove> calcKingMoves(ChessBoard board, ChessPosition currentPosition){
        ArrayList<ChessMove> validMoveList = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList = new ArrayList<>();

        if(board.getPiece(new ChessPosition(currentPosition.getRow() -1, currentPosition.getColumn())) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow() -1, currentPosition.getColumn())).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow() +1, currentPosition.getColumn())) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow() +1, currentPosition.getColumn())).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()-1)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()-1), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()-1)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()-1), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()+1)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()+1), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()+1)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()+1), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow() -1, currentPosition.getColumn()-1)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()-1), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow() -1, currentPosition.getColumn()-1)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()-1), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow() -1, currentPosition.getColumn()+1)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()+1), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow() -1, currentPosition.getColumn()+1)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()+1), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow() +1, currentPosition.getColumn()-1)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()-1), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow() +1, currentPosition.getColumn()-1)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()-1), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow() +1, currentPosition.getColumn()+1)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()+1), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow() +1, currentPosition.getColumn()+1)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()+1), null));
        }

        for(int i = 0; i < possibleMoveList.size(); i++){
            if(possibleMoveList.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList.get(i).getEndPosition().getColumn() <= 8) {
                validMoveList.add(possibleMoveList.get(i));
            }
        }

        return validMoveList;
    }
    private Collection<ChessMove> calcKnightMoves(ChessBoard board, ChessPosition currentPosition){
        ArrayList<ChessMove> validMoveList = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList = new ArrayList<>();

        if(board.getPiece(new ChessPosition(currentPosition.getRow() -1, currentPosition.getColumn()-2)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()-2), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow() -1, currentPosition.getColumn()-2)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()-2), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow() -1, currentPosition.getColumn()+2)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()+2), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow() -1, currentPosition.getColumn()+2)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()+2), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()-2)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()-2), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()-2)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()-2), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()+2)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()+2), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()+2)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()+2), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow() -2, currentPosition.getColumn()-1)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-2, currentPosition.getColumn()-1), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow() -2, currentPosition.getColumn()-1)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-2, currentPosition.getColumn()-1), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow() -2, currentPosition.getColumn()+1)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-2, currentPosition.getColumn()+1), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow() -2, currentPosition.getColumn()+1)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-2, currentPosition.getColumn()+1), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow() +2, currentPosition.getColumn()-1)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+2, currentPosition.getColumn()-1), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow() +2, currentPosition.getColumn()-1)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+2, currentPosition.getColumn()-1), null));
        }
        if(board.getPiece(new ChessPosition(currentPosition.getRow() +2, currentPosition.getColumn()+1)) == null){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+2, currentPosition.getColumn()+1), null));
        }
        else if(board.getPiece(new ChessPosition(currentPosition.getRow() +2, currentPosition.getColumn()+1)).getTeamColor() != this.getTeamColor()){
            possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+2, currentPosition.getColumn()+1), null));
        }

        for(int i = 0; i < possibleMoveList.size(); i++){
            if(possibleMoveList.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList.get(i).getEndPosition().getColumn() <= 8) {
                validMoveList.add(possibleMoveList.get(i));
            }
        }

        return validMoveList;
    }
    private Collection<ChessMove> calcDiagonalMoves(ChessBoard board, ChessPosition currentPosition){
        ArrayList<ChessMove> validMoveList = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList1 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList2 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList3 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList4 = new ArrayList<>();
        int maxMoveSize = 7;

        for(int i = 1; i < maxMoveSize+1; i++){
            if(currentPosition.getRow() -i >= 1 &&
                    currentPosition.getRow() -i <= 8 &&
                    currentPosition.getColumn() -i >= 1 &&
                    currentPosition.getColumn() -i <= 8) {
                possibleMoveList1.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-i, currentPosition.getColumn()-i), null));
            }
            if(currentPosition.getRow() -i >= 1 &&
                    currentPosition.getRow() -i <= 8 &&
                    currentPosition.getColumn() +i >= 1 &&
                    currentPosition.getColumn() +i <= 8) {
                possibleMoveList2.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-i, currentPosition.getColumn()+i), null));
            }
            if(currentPosition.getRow() +i >= 1 &&
                    currentPosition.getRow() +i <= 8 &&
                    currentPosition.getColumn() -i >= 1 &&
                    currentPosition.getColumn() -i <= 8) {
                possibleMoveList3.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+i, currentPosition.getColumn()-i), null));
            }
            if(currentPosition.getRow() +i >= 1 &&
                    currentPosition.getRow() +i <= 8 &&
                    currentPosition.getColumn() +i >= 1 &&
                    currentPosition.getColumn() +i <= 8) {
                possibleMoveList4.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+i, currentPosition.getColumn()+i), null));
            }
        }

        for(int j = 0; j < possibleMoveList1.size(); j++){
            if(board.getPiece(possibleMoveList1.get(j).getEndPosition()) == null){
                validMoveList.add(possibleMoveList1.get(j));
            }
            else{
                if(board.getPiece(possibleMoveList1.get(j).getEndPosition()).getTeamColor() != this.getTeamColor()){
                    validMoveList.add(possibleMoveList1.get(j));
                }
                break;
            }
        }

        for(int k = 0; k < possibleMoveList2.size(); k++){
            if(board.getPiece(possibleMoveList2.get(k).getEndPosition()) == null){
                validMoveList.add(possibleMoveList2.get(k));
            }
            else{
                if(board.getPiece(possibleMoveList2.get(k).getEndPosition()).getTeamColor() != this.getTeamColor()){
                    validMoveList.add(possibleMoveList2.get(k));
                }
                break;
            }
        }

        for(int m = 0; m < possibleMoveList3.size(); m++){
            if(board.getPiece(possibleMoveList3.get(m).getEndPosition()) == null){
                validMoveList.add(possibleMoveList3.get(m));
            }
            else{
                if(board.getPiece(possibleMoveList3.get(m).getEndPosition()).getTeamColor() != this.getTeamColor()){
                    validMoveList.add(possibleMoveList3.get(m));
                }
                break;
            }
        }

        for(int n = 0; n < possibleMoveList4.size(); n++){
            if(board.getPiece(possibleMoveList4.get(n).getEndPosition()) == null){
                validMoveList.add(possibleMoveList4.get(n));
            }
            else{
                if(board.getPiece(possibleMoveList4.get(n).getEndPosition()).getTeamColor() != this.getTeamColor()){
                    validMoveList.add(possibleMoveList4.get(n));
                }
                break;
            }
        }

        return validMoveList;
    }
    private Collection<ChessMove> calcStraightMoves(ChessBoard board, ChessPosition currentPosition){
        ArrayList<ChessMove> validMoveList = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList1 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList2 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList3 = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList4 = new ArrayList<>();
        int maxMoveSize = 7;

        for(int i = 1; i < maxMoveSize+1; i++){
            if(currentPosition.getRow() -i >= 1 &&
                    currentPosition.getRow() -i <= 8 &&
                    currentPosition.getColumn() >= 1 &&
                    currentPosition.getColumn() <= 8) {
                possibleMoveList1.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-i, currentPosition.getColumn()), null));
            }
            if(currentPosition.getRow() +i >= 1 &&
                    currentPosition.getRow() +i <= 8 &&
                    currentPosition.getColumn() >= 1 &&
                    currentPosition.getColumn() <= 8) {
                possibleMoveList2.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+i, currentPosition.getColumn()), null));
            }
            if(currentPosition.getRow() >= 1 &&
                    currentPosition.getRow() <= 8 &&
                    currentPosition.getColumn() -i >= 1 &&
                    currentPosition.getColumn() -i <= 8) {
                possibleMoveList3.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()-i), null));
            }
            if(currentPosition.getRow() >= 1 &&
                    currentPosition.getRow() <= 8 &&
                    currentPosition.getColumn() +i >= 1 &&
                    currentPosition.getColumn() +i <= 8) {
                possibleMoveList4.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()+i), null));
            }
        }

        for(int j = 0; j < possibleMoveList1.size(); j++){
            if(board.getPiece(possibleMoveList1.get(j).getEndPosition()) == null){
                validMoveList.add(possibleMoveList1.get(j));
            }
            else{
                if(board.getPiece(possibleMoveList1.get(j).getEndPosition()).getTeamColor() != this.getTeamColor()){
                    validMoveList.add(possibleMoveList1.get(j));
                }
                break;
            }
        }

        for(int k = 0; k < possibleMoveList2.size(); k++){
            if(board.getPiece(possibleMoveList2.get(k).getEndPosition()) == null){
                validMoveList.add(possibleMoveList2.get(k));
            }
            else{
                if(board.getPiece(possibleMoveList2.get(k).getEndPosition()).getTeamColor() != this.getTeamColor()){
                    validMoveList.add(possibleMoveList2.get(k));
                }
                break;
            }
        }

        for(int m = 0; m < possibleMoveList3.size(); m++){
            if(board.getPiece(possibleMoveList3.get(m).getEndPosition()) == null){
                validMoveList.add(possibleMoveList3.get(m));
            }
            else{
                if(board.getPiece(possibleMoveList3.get(m).getEndPosition()).getTeamColor() != this.getTeamColor()){
                    validMoveList.add(possibleMoveList3.get(m));
                }
                break;
            }
        }

        for(int n = 0; n < possibleMoveList4.size(); n++){
            if(board.getPiece(possibleMoveList4.get(n).getEndPosition()) == null){
                validMoveList.add(possibleMoveList4.get(n));
            }
            else{
                if(board.getPiece(possibleMoveList4.get(n).getEndPosition()).getTeamColor() != this.getTeamColor()){
                    validMoveList.add(possibleMoveList4.get(n));
                }
                break;
            }
        }
        return validMoveList;
    }
    private Collection<ChessMove> calcPawnMoves(ChessBoard board, ChessPosition currentPosition){
        ArrayList<ChessMove> validMoveList = new ArrayList<>();
        ArrayList<ChessMove> possibleMoveList = new ArrayList<>();
        ChessPiece pawnPiece = board.getPiece(currentPosition);

        if(pawnPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
            if(currentPosition.getRow() == 2){
                if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn())) == null){
                    possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()), null));
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()+2,currentPosition.getColumn())) == null){
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+2,currentPosition.getColumn()), null));
                    }
                }
                if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()-1)) != null){
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()-1)).getTeamColor() != this.getTeamColor()) {
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() -1), null));
                    }
                }
                if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()+1)) != null){
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()+1)).getTeamColor() != this.getTeamColor()) {
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1), null));
                    }
                }
            }else if(currentPosition.getRow()+1 == 8){
                if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn())) == null){
                    possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()), PieceType.QUEEN));
                    possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()), PieceType.BISHOP));
                    possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()), PieceType.KNIGHT));
                    possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()), PieceType.ROOK));
                }
                if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()-1)) != null){
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()-1)).getTeamColor() != this.getTeamColor()) {
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() - 1), PieceType.QUEEN));
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() - 1), PieceType.BISHOP));
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() - 1), PieceType.KNIGHT));
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() - 1), PieceType.ROOK));
                    }
                }
                if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()+1)) != null){
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()+1)).getTeamColor() != this.getTeamColor()){
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()+1), PieceType.QUEEN));
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()+1), PieceType.BISHOP));
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()+1), PieceType.KNIGHT));
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()+1), PieceType.ROOK));
                    }
                }
            }else{
                if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn())) == null){
                    possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()), null));
                }
                if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()-1)) != null){
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()-1)).getTeamColor() != this.getTeamColor()) {
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() - 1), null));
                    }
                }
                if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()+1)) != null){
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()+1,currentPosition.getColumn()+1)).getTeamColor() != this.getTeamColor()) {
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn() + 1), null));
                    }
                }
            }
        }
        else{
            if(currentPosition.getRow() == 7){
                if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn())) == null){
                    possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()), null));
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()-2,currentPosition.getColumn())) == null){
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-2,currentPosition.getColumn()), null));
                    }
                }
                if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()-1)) != null){
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()-1)).getTeamColor() != this.getTeamColor()) {
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() -1, currentPosition.getColumn() -1), null));
                    }
                }
                if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()+1)) != null){
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()+1)).getTeamColor() != this.getTeamColor()) {
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() -1, currentPosition.getColumn() + 1), null));
                    }
                }
            }else if(currentPosition.getRow()-1 == 1){
                if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn())) == null){
                    possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()), PieceType.QUEEN));
                    possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()), PieceType.BISHOP));
                    possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()), PieceType.KNIGHT));
                    possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()), PieceType.ROOK));
                }
                if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()-1)) != null){
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()-1)).getTeamColor() != this.getTeamColor()) {
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() - 1, currentPosition.getColumn() - 1), PieceType.QUEEN));
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() - 1, currentPosition.getColumn() - 1), PieceType.BISHOP));
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() - 1, currentPosition.getColumn() - 1), PieceType.KNIGHT));
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() - 1, currentPosition.getColumn() - 1), PieceType.ROOK));
                    }
                }
                if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()+1)) != null){
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()+1)).getTeamColor() != this.getTeamColor()){
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()+1), PieceType.QUEEN));
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()+1), PieceType.BISHOP));
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()+1), PieceType.KNIGHT));
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()+1), PieceType.ROOK));
                    }
                }
            }else{
                if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn())) == null){
                    possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()), null));
                }
                if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()-1)) != null){
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()-1)).getTeamColor() != this.getTeamColor()) {
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() - 1, currentPosition.getColumn() - 1), null));
                    }
                }
                if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()+1)) != null){
                    if(board.getPiece(new ChessPosition(currentPosition.getRow()-1,currentPosition.getColumn()+1)).getTeamColor() != this.getTeamColor()) {
                        possibleMoveList.add(new ChessMove(currentPosition, new ChessPosition(currentPosition.getRow() - 1, currentPosition.getColumn() + 1), null));
                    }
                }
            }
        }

        for(int i = 0; i < possibleMoveList.size(); i++){
            if(possibleMoveList.get(i).getEndPosition().getRow() >= 1 &&
                    possibleMoveList.get(i).getEndPosition().getRow() <= 8 &&
                    possibleMoveList.get(i).getEndPosition().getColumn() >= 1 &&
                    possibleMoveList.get(i).getEndPosition().getColumn() <= 8) {
                validMoveList.add(possibleMoveList.get(i));
            }
        }
        return validMoveList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return teamColor == that.teamColor && currentPieceType == that.currentPieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, currentPieceType);
    }
}
