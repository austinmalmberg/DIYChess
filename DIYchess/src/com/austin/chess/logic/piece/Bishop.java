package com.austin.chess.logic.piece;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.austin.chess.logic.board.Board;

public class Bishop extends Piece {
	
	public Bishop(Board board, Point location, PieceColor color) {
		super(board, location, color, PieceType.BISHOP);
	}
	
	@Override
	protected void updateAttackMoves() {
		attackMoves = new ArrayList<>(board.getRelatedPoints().getDiagonal(location));
		attackMoves.addAll(board.getRelatedPoints().getInverseDiagonal(location));
	}

	@Override
	protected void updatePassiveMoves() {}
	
	@Override
	public void updateValidMoves() {

		List<Point> direct = board.getRelatedPoints().getDiagonal(location);
		List<Point> inverse = board.getRelatedPoints().getInverseDiagonal(location);
		
		// up and right of bishop
		validMoves = new ArrayList<>(getValidLinearAttackingMoves(direct.stream()
					.filter(point -> point.y > location.y)
					.collect(Collectors.toList())));
		
		// points down and left of bishop
		validMoves.addAll(getValidLinearAttackingMoves(reverse(direct).stream()
					.filter(point -> point.y < location.y)
					.collect(Collectors.toList())));
		
		// points up and left of bishop
		validMoves.addAll(getValidLinearAttackingMoves(inverse.stream()
					.filter(point -> point.y > location.y)
					.collect(Collectors.toList())));
		
		// points down and right of bishop
		validMoves.addAll(getValidLinearAttackingMoves(reverse(inverse).stream()
					.filter(point -> point.y < location.y)
					.collect(Collectors.toList())));
	}
	
	@Override
	public boolean offeringCheck() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() { return color == PieceColor.WHITE ? "B" : "E"; }
}
