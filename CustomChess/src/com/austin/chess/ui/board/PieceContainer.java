package com.austin.chess.ui.board;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class PieceContainer extends StackPane {

	private ImageView pieceImg;
	
	public PieceContainer() {
		this(new ImageView());
	}
	
	public PieceContainer(Image image) {		
		this(new ImageView(image));
	}
	
	public PieceContainer(ImageView pieceImg) {
		this.pieceImg = pieceImg;
		
		getChildren().add(pieceImg);
	}
	
	public void dimPiece(boolean dim) {
		setOpacity(dim ? 0.5 : 1);
	}
	
	public void setImage(Image img) {
		pieceImg.setImage(img);
	}
	
	public void setImageFromPath(String path) {
		pieceImg.setImage(new Image(path));
	}
	
	public ImageView getImageView() { return pieceImg; }
	public Image getImage() { return pieceImg.getImage(); }
	
	public boolean isEmpty() { return pieceImg.getImage() == null; }
}
