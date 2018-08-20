package com.austin.chess.ui.board;

import java.awt.Point;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
	
	public static final int WIDTH = 70;
	public static final int HEIGHT = 70;

	private Point position;
	private Color color;
	
	private Rectangle background;
	private ImageView piece;
	
	public Tile(ChessBoard board, Point position, Color color) {
		this.position = position;
		this.color = color;
		
		background = new Rectangle(WIDTH, HEIGHT);
		background.setStroke(color);
		background.setFill(color);
		
		piece = new ImageView();
		
		getChildren().add(background);
		getChildren().add(piece);
	}
	
	public void setHighlight(boolean highlight) {
		background.setStroke(highlight ? Color.RED : color);
	}
	
	public void setPiece(String imagePath) {
		if(imagePath.isEmpty()) return;

		piece.setImage(new Image(imagePath));
	}
	
	public void setPiece(ImageView piece) { this.piece = piece; }
	
	public Point getPosition() { return position; }
	public ImageView getPieceImage() { return piece; }
	
	public ImageView popPiece() {
		ImageView temp = piece;
		piece.setImage(null);
		return temp;
	}
}
