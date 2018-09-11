package com.austin.chess.ui.board;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.austin.chess.logic.board.Board;
import com.austin.chess.logic.piece.Piece;
import com.austin.chess.logic.piece.PieceColor;
import com.austin.chess.logic.piece.PieceType;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class InteractiveChessBoard extends FlowPane {
	
	// change board constants to these constants
	public static final int ROWS = 8;
	public static final int COLUMNS = 8;
	
	private Board board;
	
	private Map<PieceType, Map<PieceColor, String>> pieceResourceMap;
	
	private Map<Point, Tile> tileMap;
	private boolean flipped;
	
	private Tile selectedTile;
	private PieceContainer selectedPiece;
	
	// horizontal movement
	private double mouse_x;
	private double mouse_dx;
	
	// vertical movement
	private double mouse_y;
	private double mouse_dy;
	
	public static final Color[] TILE_COLORS = {Color.BLANCHEDALMOND, Color.GRAY}; 

	public InteractiveChessBoard(int boardstate, int ruleset) {
		super();
		
		setPrefSize(COLUMNS * Tile.WIDTH, ROWS * Tile.HEIGHT);
		
		board = new Board(boardstate, ruleset);

		initializePieceResourceMap();
		
		tileMap = new HashMap<>();
		flipped = false;
		
		initializeUserInterface();
		
		selectedPiece = new PieceContainer();
		getChildren().add(selectedPiece);
		
		setOnMouseDragged(this::handleMouseDragged);
		setOnMouseClicked(this::handleMouseClicked);
		setOnMousePressed(this::handleMousePressed);
		setOnMouseReleased(this::handleMouseReleased);
	}
	
	private void initializeUserInterface() {
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLUMNS; c++) {
				// location is equal to (row, column)
				Point location = new Point(ROWS-1 - r, c);
				
				// create tile
				Tile tile = new Tile(this, location, TILE_COLORS[(r+c) % 2]);
//				tile.setTranslateX(c * Tile.WIDTH);
//				tile.setTranslateY(r * Tile.HEIGHT);
				
				// add piece to tile (if applicable)
				Piece piece = board.getPiece(location);
				if(piece != null) {	
					String imagePath = pieceResourceMap.get(piece.getType()).get(piece.getColor());
					tile.setPieceImage(imagePath);
				}
				
				tileMap.put(location, tile);
				getChildren().add(tile);
			}
		}
	}
	
	private void handleMouseDragged(MouseEvent e) {
		
		// if selected piece
		
			// update selected.translate
		
	}
	
	private void handleMouseClicked(MouseEvent e) {

	}
	
	private void handleMousePressed(MouseEvent e) {
		
		if(selectedTile == null) {
			selectedTile = getTile(e);
			selectedPiece.setImage(selectedTile.getPieceContainer().getImage());
			
			selectedTile.dimPiece(true);
			
			snapPieceToCursor(e);
		}
		
		// if no piece is selected
		
			// selected piece = copy of piece image at point
			// snap selected piece to mouse
		
			// dim piece image at tile
			
		
		
		// if selected piece
		
			
		
		
		
		
		
//		// highlight valid moves
//		workingTile = getTile(e);
//		Piece workingPiece = board.getPiece(workingTile.getPosition());
//		highlightValidMoves(workingPiece, true);
//		
//		// snap piece to cursor
//		workingPieceImage = workingTile.getPieceImage();
////		if(!getChildren().contains(workingPieceImage))
////			getChildren().add(workingPieceImage);	// add a copy
	}
	
	private void handleMouseReleased(MouseEvent e) {
		
		selectedTile = null;
		
		
			selectedPiece.setImage(null);
		
		// remove selected piece from pane
		
		// if selected piece
		
			// if mouse is released in a valid move
		
				// move the piece
		
			// otherwise
		
				// restore piece transparency

				// set selected to null
		
		
		
		
		
		
		
		
		
		
		
		
//		// remove highlights from workingTile's valid moves
//		if(workingTile != null)
//			highlightValidMoves(board.getPiece(workingTile.getPosition()), false);
//		
//		workingTile = null;
//		
//		
//		workingPieceImage.setTranslateX(0);
//		workingPieceImage.setTranslateY(0);
//		
//		workingPieceImage = null;
	}
	
	private void snapPieceToCursor(MouseEvent e) {
		if(selectedTile == null) {
			System.out.println("working tile is null");
			return;
		}
		
		// mouse location - center of piece
		
		// FIX THIS
		double dx = (e.getSceneX() - (selectedTile.getBoundsInParent().getMinX()) + selectedTile.getWidth() / 2);
		double dy = (e.getSceneY() - (selectedTile.getBoundsInParent().getMinY()) + selectedTile.getHeight() / 2);
		
		selectedPiece.setTranslateX(dx);
		selectedPiece.setTranslateY(dy);
	}
	
	private void highlightValidMoves(Piece piece, boolean highlight) {
		if(piece == null) return;
		
		piece.getValidMoves().stream().map(tileMap::get).forEach(tile -> tile.setHighlight(highlight));
	}
	
	private Piece getPieceFromBoard(Point point) {
		return board.getPiece(point);
	}
	
	private Piece getPieceFromBoard(Tile tile) {
		return board.getPiece(tile.getPosition());
	}
	
	private Tile getTile(MouseEvent e) {
		return tileMap.get(getPointRelativeToOrienatation(e.getY(), e.getX()));
	}
	
	private Tile getTile(Point point) {
		return tileMap.get(point);
	}
	
	private Point getPointRelativeToOrienatation(double y, double x) {
		if(flipped)
			return new Point((int) y/Tile.HEIGHT, (int) x/Tile.WIDTH);
		
		return new Point(COLUMNS-1 - (int) y/Tile.HEIGHT, (int) x/Tile.WIDTH);
	}
	
	private void handlePieceMoved(Point from, Point to) {
		if(board.tryMove(from, to)) {
			Tile origTile = getTile(from);
			Tile newTile = getTile(to);
			
			PieceContainer capturedPiece = newTile.getPieceContainer(); 
			
			newTile.setPieceContainer(origTile.getPieceContainer());
			origTile.setPieceContainer(null);
			
			// if captured piece
				// update graveyard
				// add captured piece to UI
		}
	}
	
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
}
