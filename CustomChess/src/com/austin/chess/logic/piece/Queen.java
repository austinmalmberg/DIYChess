package com.austin.chess.logic.piece;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.austin.chess.logic.board.Board;

public class Queen extends Piece {
	
	public Queen(Board board, Point location, PieceColor color) {
		super(board, location, color, PieceType.QUEEN);
	}
	
	@Override
	protected void updateAttackMoves() {
		// straights
		attackMoves = new ArrayList<>(board.getRelatedPoints().getRank(location.x));
		attackMoves.addAll(board.getRelatedPoints().getFile(location.y));
		
		// diagonals
		attackMoves.addAll(board.getRelatedPoints().getDiagonal(location));
		attackMoves.addAll(board.getRelatedPoints().getInverseDiagonal(location));
	}

	@Override
	protected void updatePassiveMoves() {}

	@Override
	public void updateValidMoves() {
		
		List<Point> rank = board.getRelatedPoints().getRank(location.x);
		List<Point> file = board.getRelatedPoints().getFile(location.y);
		
		List<Point> direct = board.getRelatedPoints().getDiagonal(location);
		List<Point> inverse = board.getRelatedPoints().getInverseDiagonal(location);
		
	// straights
		
		// horizontal moves right of queen
		validMoves = new ArrayList<>(getValidLinearAttackingMoves(rank.stream()
					.filter(point -> point.x > location.y)
					.collect(Collectors.toList())));
		
		// horizontal moves left of queen
		validMoves.addAll(getValidLinearAttackingMoves(reverse(rank).stream()
					.filter(point -> point.x < location.y)
					.collect(Collectors.toList())));
		
		// vertical moves above queen
		validMoves.addAll(getValidLinearAttackingMoves(file.stream()
					.filter(point -> point.x > location.x)
					.collect(Collectors.toList())));
		
		// vertical moves below queen
		validMoves.addAll(getValidLinearAttackingMoves(reverse(file).stream()
					.filter(point -> point.x < location.x)
					.collect(Collectors.toList())));
		
	// diagonals
		
		// up and right of queen
		validMoves.addAll(getValidLinearAttackingMoves(direct.stream()
					.filter(point -> point.y > location.y)
					.collect(Collectors.toList())));
		
		// down and left of queen
		validMoves.addAll(getValidLinearAttackingMoves(reverse(direct).stream()
					.filter(point -> point.y < location.y)
					.collect(Collectors.toList())));
		
		// up and left of queen
		validMoves.addAll(getValidLinearAttackingMoves(inverse.stream()
					.filter(point -> point.y > location.y)
					.collect(Collectors.toList())));
		
		// down and right of queen
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
	public String toString() { return color == PieceColor.WHITE ? "Q" : "O"; }
}
