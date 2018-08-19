package com.austin.chess.logic.piece;

import java.awt.Point;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.austin.chess.logic.board.Board;

public class Pawn extends Piece {

	// this determines which vertical direction the piece moves (up or down).  A new system would need to be implemented for more than two players.
	private int direction_mod;
	
	public Pawn(Board board, Point location, PieceColor color) {
		super(board, location, color, PieceType.PAWN);
		
		direction_mod = color == PieceColor.WHITE ? 1 : -1;
	}
	
	@Override
	protected void updateAttackMoves() {
		// forward one and to either side
		attackMoves = board.getRelatedPoints().getSurroundingPoints(1).stream()
				.filter(point -> point.x == location.x + 1*direction_mod && point.y != location.y)
				.collect(Collectors.toList());
	}

	@Override
	protected void updatePassiveMoves() {
		passiveMoves = IntStream.range(1, getNumberOfVerticalMoves()+1)
				.mapToObj(i -> new Point(location.x + i*direction_mod, location.y))
				.collect(Collectors.toList());
	}
	
	@Override
	public void updateValidMoves() {
		validMoves.clear();
		
		for(Point point : passiveMoves) {
			if(board.isOccupied(point))
				break;
			
			validMoves.add(point);
		}
				
		validMoves.addAll(attackMoves.stream()
				.filter(board::isOccupied)
				.filter(this::isEnemy)
				.collect(Collectors.toList()));
	}
	
	@Override
	public boolean offeringCheck() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private int getNumberOfVerticalMoves() {
		if((color == PieceColor.WHITE && location.x == 1) ||
				(color == PieceColor.BLACK && location.x == Board.ROWS - 2))
			return 2;
		
		return 1;
	}
	
	@Override
	public String toString() { return color == PieceColor.WHITE ? "i" : "j"; }
}
