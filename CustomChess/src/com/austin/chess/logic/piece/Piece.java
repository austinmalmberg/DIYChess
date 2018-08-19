package com.austin.chess.logic.piece;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.austin.chess.logic.board.Board;

public abstract class Piece implements Moveable {

	protected Board board;
	
	protected Point location;	// update pieces with points instead of r, c
	
	protected final PieceColor color;
	protected final PieceType type;
	
	protected List<Point> attackMoves;		// 
	protected List<Point> passiveMoves;		// A list of Non-attacking moves.  i.e. vertical pawn moves (cannot capture)
	protected List<Point> moveset;		// attack + passive moves
	
	protected List<Point> validMoves;		// only possible moves
	
	public Piece(Board board, Point location, PieceColor color, PieceType type) {
		this.board = board;
		this.location = location;
		
		this.color = color;
		this.type = type;
		
		attackMoves = new ArrayList<>();
		passiveMoves = new ArrayList<>();
		moveset = new ArrayList<>();
		
		validMoves = new ArrayList<>();
	}
	
	public void setLocation(Point location) {
		this.location = location;
	}
	
	public PieceColor getColor() { return color; }
	public PieceType getType() { return type; }
	
	public Point getLocation() { return location; }
	public int r() { return location.x; }
	public int c() { return location.y; }
	
	/**
	 * 
	 * @param points a list of attacking moves
	 * @return a sublist of points ending when an enemy is encountered. The enemy is added to the list
	 */
	protected List<Point> getValidLinearAttackingMoves(List<Point> points) {
		List<Point> sublist = new ArrayList<>();
		
		for(Point point : passiveMoves) {			
			if(board.isOccupied(point)) {
				
				if(board.getPiece(point).getColor() != color)
					sublist.add(point);
					
				break;
			}
			
			sublist.add(point);
		}
		
		return sublist;
	}
	
	protected boolean isEnemy(Point point) {
		if(board.isOccupied(point))
			return board.getPiece(point).getColor() != color;
		
		return false;
	}
	
	/**
	 * @return All attacking and passive (non-attacking) moves.
	 */
	public List<Point> getMoveset() { return moveset; }
	public List<Point> getValidMoves() { return validMoves; }
	
	protected abstract void updateAttackMoves();
	protected abstract void updatePassiveMoves();
	
	/**
	 * Called when a piece moves.  Updates all possible moves.
	 */
	public void updateMoveset() {
		updateAttackMoves();
		updatePassiveMoves();
		
		moveset = new ArrayList<>(attackMoves);
		moveset.addAll(passiveMoves);
	}
	
	/**
	 * Called at the beginning of each players turn.
	 */
	public abstract void updateValidMoves();
	
	public boolean isValidMove(Point to) { return validMoves.contains(to); }
	
	/**
	 * Updates location and moveset.
	 * 
	 * @param to the new location
	 */
	public void move(Point to) {
		setLocation(to);
		
		updateMoveset();
		updateValidMoves();		// since valid moves are updated every turn, this is not needed outside of troubleshooting purposes
	}
	
	public abstract boolean offeringCheck();
	
	public List<Point> reverse(List<Point> points) {
		List<Point> rev = new ArrayList<>(points);
		Collections.reverse(rev);
		
		return rev;
	}
	
	public abstract String toString();
}
