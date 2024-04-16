package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MyButton extends Button implements Observer{

	private boolean active = false;

	
	public MyButton() {
		super(); 
	}
	
	public void activate() {
		this.active = true;
	}

	public boolean isActivated() {
		return this.active;
	}

	@Override
	public void update() {
		setDisable(false);
		setVisible(true);
		Font font = Font.font("Courier New", FontWeight.BOLD, 20);
		setFont(font);
		setText("button");
	}
	
	
	
	

	
	

}
