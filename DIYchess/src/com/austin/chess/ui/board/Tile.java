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
	
	private PieceContainer pieceContainer;
//	private ImageView piece;
	
	public Tile(InteractiveChessBoard board, Point position, Color color) {
		this.position = position;
		this.color = color;
		
		background = new Rectangle(WIDTH, HEIGHT);
		background.setFill(color);
		
		pieceContainer = new PieceContainer();
//		piece = new ImageView();
		
		getChildren().add(background);
//		getChildren().add(piece);
		getChildren().add(pieceContainer);
	}
	
	public void setHighlight(boolean highlight) {
		background.setStroke(highlight ? Color.RED : color);
	}
	
	public void dimPiece(boolean b) {
		pieceContainer.dimPiece(b);
	}
	
	public void setPieceImage(String path) {
		pieceContainer.setImageFromPath(path);
	}
	
	public void setPieceImage(Image img) {
		pieceContainer.setImage(img);
	}
	
	public void setPieceContainer(PieceContainer container) {
		pieceContainer = container;
	}
	
	public Point getPosition() { return position; }
	public PieceContainer getPieceContainer() { return pieceContainer; }
	
	public PieceContainer swapPiece(PieceContainer newContainer) {
		PieceContainer temp = pieceContainer;
		pieceContainer = newContainer;
		return temp;
	}
}
