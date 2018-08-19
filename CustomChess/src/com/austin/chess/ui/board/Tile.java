package com.austin.chess.ui.board;

import java.awt.Point;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
	
	public static final int WIDTH = 80;
	public static final int HEIGHT = 80;

	private Point position;
	
	private Rectangle background;
	private ImageView piece;
	
	public Tile(ChessBoard board, Point position, Color color) {
		this.position = position;
		
		background = new Rectangle(WIDTH, HEIGHT);
		background.setFill(color);
		
		piece = new ImageView();
		
		getChildren().add(background);
		getChildren().add(piece);
		
		setOnMouseClicked(this::handleTileClick);
		piece.setOnMouseClicked(this::handlePieceClick);
	}
	
	public void setPieceImage(String imagePath) {
		if(imagePath.isEmpty()) return;
		
		piece.setImage(new Image(imagePath, 70, 70, false, true));
	}
	
	private void handleTileClick(MouseEvent e) {
		
	}
	
	private void handlePieceClick(MouseEvent e) {
		
	}
	
	public Point getPosition() { return position; }
	public ImageView getPieceImage() { return piece; }
}
