package com.austin.chess.logic.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.austin.chess.logic.piece.PieceColor;
import com.austin.chess.logic.piece.PieceType;

public class Graveyard {

	private Map<PieceColor, List<PieceType>> graveyard;
	
	public Graveyard(PieceColor[] players) {
		graveyard = new HashMap<>();
		
		Arrays.stream(players).forEach(playerColor -> {
			graveyard.put(playerColor, new ArrayList<>());
		});
	}
	
	public void add(PieceColor color, PieceType type) {
		graveyard.get(color).add(type);
	}
	
	public List<PieceType> getPieces(PieceColor color) {
		return graveyard.get(color);
	}
}
