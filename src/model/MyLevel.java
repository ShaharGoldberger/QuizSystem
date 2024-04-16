package model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class MyLevel extends Label implements Observer{
	
	public MyLevel() {
		super();
		setText("Using Observe");
	}
	
	

	@Override
	public void update() {
		setVisible(true);
		setText("Iterator created by myArrayList class!!!");
		setMaxWidth(Double.MAX_VALUE);
		setAlignment(Pos.BASELINE_CENTER);
		setPadding(new Insets(10));
		setStyle("-fx-font-size: 25px; -fx-text-fill: green;");
		
	}

	
	
	

}
