package com.austin.chess.ui.board;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.austin.chess.logic.board.Board;
import com.austin.chess.logic.piece.Piece;
import com.austin.chess.logic.piece.PieceColor;
import com.austin.chess.logic.piece.PieceType;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class ChessBoard extends FlowPane {
	
	// change board constants to these constants
	public static final int ROWS = 8;
	public static final int COLUMNS = 8;
	
	private Board board;
	
	private Map<Point, Tile> tileMap;
	private boolean flipped;
	
	private Map<PieceType, Map<PieceColor, String>> pieceResourceMap;
	
	private Tile workingTile;
	private ImageView workingPieceImage;
	
	public static final Color[] TILE_COLORS = {Color.BLANCHEDALMOND, Color.GRAY}; 

	public ChessBoard(int boardstate, int ruleset) {
		super();
		
		setPrefSize(COLUMNS * Tile.WIDTH + COLUMNS, ROWS * Tile.HEIGHT + ROWS);
		
		board = new Board(boardstate, ruleset);

		tileMap = new HashMap<>();
		flipped = false;
		
		initializePieceResourceMap();
		
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLUMNS; c++) {
				Point location = new Point(ROWS-1 - r, c);
				
				// create tile
				Tile tile = new Tile(this, location, TILE_COLORS[(r+c) % 2]);
				tileMap.put(location, tile);
				
				// add piece to tile (if applicable)
				Piece piece = board.getPiece(location);
				if(piece != null) {	
					String imagePath = pieceResourceMap.get(piece.getType()).get(piece.getColor());
					tile.setPiece(imagePath);
				}
				
				getChildren().add(tile);
			}
		}
		
		setOnMouseDragged(this::handleMouseDrag);
		setOnMouseClicked(this::handleMouseClick);
		setOnMousePressed(this::handleMousePress);
		setOnMouseReleased(this::handleMouseRelease);
	}
	
	private void handleMouseDrag(MouseEvent e) {
		snapPieceImageToCursor(e);
	}
	
	private void handleMouseClick(MouseEvent e) {
	}
	
	private void handleMousePress(MouseEvent e) {
		// highlight valid moves
		workingTile = getTile(e);
		Piece workingPiece = board.getPiece(workingTile.getPosition());
		highlightValidMoves(workingPiece, true);
		
		// snap piece to cursor
		workingPieceImage = workingTile.getPieceImage();
//		if(!getChildren().contains(workingPieceImage))
//			getChildren().add(workingPieceImage);	// add a copy
		
		snapPieceImageToCursor(e);
	}
	
	private void handleMouseRelease(MouseEvent e) {
		
		// remove highlights from workingTile's valid moves
		if(workingTile != null)
			highlightValidMoves(board.getPiece(workingTile.getPosition()), false);
		
		workingTile = null;
		
		
		workingPieceImage.setTranslateX(0);
		workingPieceImage.setTranslateY(0);
		
		workingPieceImage = null;
	}
	
	private void snapPieceImageToCursor(MouseEvent e) {
		if(workingTile == null) {
			System.out.println("working tile is null");
			return;
		}
		
		// mouse location - center of piece
		double dx = e.getX() - (workingTile.getBoundsInParent().getMinX() + workingTile.getWidth() / 2);
		double dy = e.getY() - (workingTile.getBoundsInParent().getMinY() + workingTile.getHeight() / 2);
		
		workingPieceImage.setTranslateX(dx);
		workingPieceImage.setTranslateY(dy);
	}
	
	private void highlightValidMoves(Piece piece, boolean highlight) {
		if(piece == null) return;
		
		piece.getValidMoves().stream().map(tileMap::get).forEach(tile -> tile.setHighlight(highlight));
	}
	
	private Piece getPiece(Point point) {
		return board.getPiece(point);
	}
	
	private Piece getPiece(Tile tile) {
		return board.getPiece(tile.getPosition());
	}
	
	private Tile getTile(MouseEvent e) {
		return tileMap.get(getPointRelativeToOrienatation(e.getY(), e.getX()));
	}
	
	private Point getPointRelativeToOrienatation(double y, double x) {
		if(flipped)
			return new Point((int) y/Tile.HEIGHT, (int) x/Tile.WIDTH);
		
		return new Point(COLUMNS-1 - (int) y/Tile.HEIGHT, (int) x/Tile.WIDTH);
	}
	
	private void movePiece(Point from, Point to) {
		if(board.tryMove(from, to)) {
			tileMap.get(to).setPiece(tileMap.get(from).popPiece());
		}
		
		// add captured piece to UI
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
