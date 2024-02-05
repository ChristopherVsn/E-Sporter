package ui;

import javax.swing.ImageIcon;

public class ButtonTableType {
	
	private ImageIcon icon;
	private ButtonTableActionListener action;
	private int size;
	private String command;
	
	public ButtonTableType(ImageIcon icon, int size, ButtonTableActionListener action) {
		this.icon = icon;
		this.size = size;
		this.action = action;
	}
	
	public ImageIcon getIcon() {
		return this.icon;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public ButtonTableActionListener getAction() {
		return this.action;
	}
	
	public String getActionCommand() {
		return this.command;
	}
	
	public void setActionCommand(String command) {
		this.command = command;
	}
}
