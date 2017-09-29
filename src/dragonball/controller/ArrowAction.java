package dragonball.controller;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ArrowAction extends AbstractAction {
	private String cmd;
	private GameGUI x;

    public ArrowAction(String cmd,GameGUI x) {
    	this.x=x;
        this.cmd = cmd;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (cmd.equalsIgnoreCase("LeftArrow")) {
        	x.disableCellIcon(x.getGame().getWorld().getPlayerRow(), x.getGame().getWorld()
					.getPlayerColumn());
			x.getGame().getWorld().moveLeft();
			x.setCellIcon(x.getGame().getWorld().getPlayerRow(), x.getGame().getWorld()
					.getPlayerColumn());
        } else if (cmd.equalsIgnoreCase("RightArrow")) {
        	x.disableCellIcon(x.getGame().getWorld().getPlayerRow(), x.getGame().getWorld()
					.getPlayerColumn());
			x.getGame().getWorld().moveRight();
			x.setCellIcon(x.getGame().getWorld().getPlayerRow(), x.getGame().getWorld()
					.getPlayerColumn());
        } else if (cmd.equalsIgnoreCase("UpArrow")) {
        	x.disableCellIcon(x.getGame().getWorld().getPlayerRow(), x.getGame().getWorld()
					.getPlayerColumn());
			x.getGame().getWorld().moveUp();
			x.setCellIcon(x.getGame().getWorld().getPlayerRow(), x.getGame().getWorld()
					.getPlayerColumn());
        } else if (cmd.equalsIgnoreCase("DownArrow")) {
        	x.disableCellIcon(x.getGame().getWorld().getPlayerRow(), x.getGame().getWorld()
					.getPlayerColumn());
			x.getGame().getWorld().moveDown();
			x.setCellIcon(x.getGame().getWorld().getPlayerRow(), x.getGame().getWorld()
					.getPlayerColumn());
        }
    }

}
