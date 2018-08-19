package com.austin.chess.logic.board;

import java.awt.Point;
import java.util.List;
import java.util.stream.Collectors;

import com.austin.chess.Ruleset;
import com.austin.chess.logic.piece.Piece;
import com.austin.chess.logic.piece.PieceColor;

public class Board {
	
	public static final int ROWS = 8;
	public static final int COLUMNS = 8;
	
	private LogicBoardInitializer initializer;
	private Ruleset ruleset;
	
	private Piece[][] board;
	private List<Piece> pieces;
	
	private RelatedPoints relatedPoints;
	
	private Graveyard graveyard;

	public Board(int boardstate, int ruleset) {
		this.ruleset = new Ruleset(ruleset);
		this.initializer = new LogicBoardInitializer(this, boardstate);
		
		board = initializer.getBoard();
		
		relatedPoints = new RelatedPoints(this);
		
		graveyard = new Graveyard(PieceColor.values());
		
		// init pieces list
		pieces = relatedPoints.allPointsOnBoardAsStream()
				.map(this::getPiece)
				.filter(piece -> piece != null)
				.collect(Collectors.toList());
		
		// update initial moves
//		pieces.stream().forEach(Piece::updateMoveset);
	}
	
	// METHODS FOR PIECES
	
	public boolean isOccupied(Point p) {
		return board[p.x][p.y] == null;
	}
	
	public boolean inBounds(Point p) {
		if(p.x < 0 || p.x >= ROWS) return false;
		if(p.y < 0 || p.y >= COLUMNS) return false;
		
		return true;
	}
	
	public boolean tileAttackedByEnemy(PieceColor friendlyColor, Point p) {
		return pieces.stream().filter(otherPiece -> 
				otherPiece.getColor() != friendlyColor &&
				otherPiece.getValidMoves().contains(p))
			.count() > 0;
	}
	
	public boolean tryMove(Point from, Point to) {
		if(getPiece(from).getValidMoves().contains(to)) {
			move(from, to);
			return true;
		}
		
		return false;
	}
	
	private void move(Point from, Point to) {
		Piece movingPiece = getPiece(from);
		Piece capturedPiece = getPiece(to);
		
		setPiece(from, null);
		setPiece(to, movingPiece);
		
		movingPiece.move(to);
		
		updateCapturedPiece(capturedPiece);
	}
	
	public void updateCapturedPiece(Piece piece) {
		if(piece == null) return;
		
		graveyard.add(piece.getColor(), piece.getType());
		pieces.remove(piece);
	}
	
	public void updateValidMoves(PieceColor color) {
		pieces.stream()
				.filter(piece -> piece.getColor() == color)
				.forEach(Piece::updateValidMoves);
	}
	
	// Getters -------------------------------------------------------------------------------------------------------

	
	public Piece getPiece(Point p) { return board[p.x][p.y]; }
	
	public RelatedPoints getRelatedPoints() { return relatedPoints; }
	
	public List<Point> getFile(int c) { return relatedPoints.getFile(c); }
	public List<Point> getRank(int r) {	return relatedPoints.getRank(r); }
	
	// Setters -------------------------------------------------------------------------------------------------------
	
	public void setPiece(Point location, Piece piece) {
		board[location.x][location.y] = piece;
	}
	
	public void print() {
		for(int r = board.length-1; r >= 0; r--) {
			for(int c = 0; c < board[r].length; c++) {
				if(board[r][c] == null)
					System.out.print("_ ");
				else
					System.out.print(board[r][c].toString() + " ");
			}
			System.out.println("   " + (r+1));
		}
		System.out.println("\na b c d e f g h ");
	}
}
