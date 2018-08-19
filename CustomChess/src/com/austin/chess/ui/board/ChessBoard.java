package com.austin.chess.ui.board;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.austin.chess.logic.board.Board;
import com.austin.chess.logic.piece.Piece;
import com.austin.chess.logic.piece.PieceColor;
import com.austin.chess.logic.piece.PieceType;

import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class ChessBoard extends FlowPane {
	
	// change board constants to these constants
	public static final int ROWS = 8;
	public static final int COLUMNS = 8;
	
	private Board board;
	
	// there's an easier way to do these things
	private Map<Node, Tile> pieceImageMap;
	private Map<PieceType, Map<PieceColor, String>> pieceResourceMap;
	
	public static final Color[] TILE_COLORS = {Color.BLANCHEDALMOND, Color.GRAY}; 

	public ChessBoard(int boardstate, int ruleset) {
		super();
		
		setPrefSize(COLUMNS * Tile.WIDTH, ROWS * Tile.HEIGHT);		
		
		board = new Board(boardstate, ruleset);
		
		pieceImageMap = new HashMap<>();
		
		initializePieceResourceMap();
		
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLUMNS; c++) {
				Point location = new Point(ROWS-1 - r, c);
				Tile t = new Tile(this, location, TILE_COLORS[(r+c) % 2]);
				
				getChildren().add(t);
				
				
				
				Piece piece = board.getPiece(location);
				
				if(piece != null) {
					String imagePath = pieceResourceMap.get(piece.type()).get(piece.color());
					t.setPieceImage(imagePath);
					
					pieceImageMap.put(t.getPieceImage(), t);
				}
			}
		}
		
		setOnMouseDragged(this::handleMouseDrag);
		setOnMouseClicked(this::handleMouseClick);
		setOnMousePressed(this::handleMousePress);
		setOnMouseReleased(this::handleMouseRelease);
	}
	
	// maybe create class to initialize images?
	@SuppressWarnings("serial")
	public void initializePieceResourceMap() {
		pieceResourceMap = new HashMap<PieceType, Map<PieceColor, String>>() {
			{
				put(PieceType.ROOK, new HashMap<PieceColor, String>() {
					{
						put(PieceColor.WHITE, "file:resources/Chess_rlt60.png");
						put(PieceColor.BLACK, "file:resources/Chess_rdt60.png");
					}
				});
				
				put(PieceType.KNIGHT, new HashMap<PieceColor, String>() {
					{
						put(PieceColor.WHITE, "file:resources/Chess_nlt60.png");
						put(PieceColor.BLACK, "file:resources/Chess_ndt60.png");
					}
				});
				
				put(PieceType.BISHOP, new HashMap<PieceColor, String>() {
					{
						put(PieceColor.WHITE, "file:resources/Chess_blt60.png");
						put(PieceColor.BLACK, "file:resources/Chess_bdt60.png");
					}
				});
				
				put(PieceType.QUEEN, new HashMap<PieceColor, String>() {
					{
						put(PieceColor.WHITE, "file:resources/Chess_qlt60.png");
						put(PieceColor.BLACK, "file:resources/Chess_qdt60.png");
					}
				});
				
				put(PieceType.KING, new HashMap<PieceColor, String>() {
					{
						put(PieceColor.WHITE, "file:resources/Chess_klt60.png");
						put(PieceColor.BLACK, "file:resources/Chess_kdt60.png");
					}
				});
				
				put(PieceType.PAWN, new HashMap<PieceColor, String>() {
					{
						put(PieceColor.WHITE, "file:resources/Chess_plt60.png");
						put(PieceColor.BLACK, "file:resources/Chess_pdt60.png");
					}
				});
			}
		};
	}
	
	public void handleMouseDrag(MouseEvent e) {
		
	}
	
	public void handleMouseClick(MouseEvent e) {
		EventTarget target = e.getTarget();
		// piece clicked
		if(pieceImageMap.containsKey(target))
			System.out.println(pieceImageMap.get(target).getPosition());		
	}
	
	public void handleMousePress(MouseEvent e) {
		// snap piece image to cursor
	}
	
	public void handleMouseRelease(MouseEvent e) {
		
	}
}
