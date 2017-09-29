package dragonball.controller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.tools.JavaCompiler;

import dragonball.model.attack.Attack;
import dragonball.model.attack.PhysicalAttack;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.SuperSaiyan;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleEventType;
import dragonball.model.battle.BattleOpponent;
import dragonball.model.cell.Collectible;
import dragonball.model.cell.FoeCell;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Frieza;
import dragonball.model.character.fighter.Majin;
import dragonball.model.character.fighter.Namekian;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.exceptions.DuplicateAttackException;
import dragonball.model.exceptions.MapIndexOutOfBoundsException;
import dragonball.model.exceptions.MaximumAttacksLearnedException;
import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.NotASaiyanException;
import dragonball.model.exceptions.NotEnoughAbilityPointsException;
import dragonball.model.exceptions.NotEnoughKiException;
import dragonball.model.exceptions.NotEnoughSenzuBeansException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;
import dragonball.model.game.GameListener;
import dragonball.model.game.GameState;
import dragonball.model.world.World;
import dragonball.view.DragonballView;

public class GameGUI implements GameListener, ActionListener, Serializable {
	private Game game;
	private DragonballView GameView;
	private CardLayout panels;
	private JPanel fightersPanel;
	private DragonWish wishChosen;
	private JProgressBar foeHealth;
	private JProgressBar foeKi;
	private JProgressBar foeStamina;
	private JProgressBar meHealth;
	private JProgressBar meStamina;
	private JProgressBar meKi;
	private Battle battle;
	private NonPlayableFighter foe1;
	private PlayableFighter me;

	public GameGUI() throws IOException {
		game = new Game();
		GameView = new DragonballView();
		game.setListener(this);
		for (JButton btn : GameView.getBtns())
			btn.addActionListener(this);
		panels = (CardLayout) GameView.getPanels().getLayout();
		panels.show(GameView.getPanels(), "menu");

		InputMap im = GameView.getWorldView().getInputMap(
				JPanel.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = GameView.getWorldView().getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RightArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UpArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DownArrow");

		am.put("RightArrow", new ArrowAction("RightArrow", this));
		am.put("LeftArrow", new ArrowAction("LeftArrow", this));
		am.put("UpArrow", new ArrowAction("UpArrow", this));
		am.put("DownArrow", new ArrowAction("DownArrow", this));
	}

	public void actionPerformed(ActionEvent e) {
		JButton x = (JButton) e.getSource();
		if (x == GameView.getNewGame())
			panels.show(GameView.getPanels(), "newPlayer");
		else if (x == GameView.getLoadGame()) {
			try {
				game.load("yahia");
			} catch (IOException | ClassNotFoundException s) {
				int result = JOptionPane.showConfirmDialog(GameView,
						"No saved games.\nDo you want to start a new game?",
						"No saved games", JOptionPane.YES_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					panels.show(GameView.getPanels(), "newPlayer");
					return;
				}

			}
			panels.show(GameView.getPanels(), "worldView");
			GameView.getStats().setVisible(true);
			updateStats();
			

		} else if (x == GameView.getChosePlayer()) {
			if (GameView.getEnterName().getText().length() != 0) {
				game.getPlayer().setName(GameView.getEnterName().getText());
				panels.show(GameView.getPanels(), "chooseFighter");
			} else
				JOptionPane.showMessageDialog(GameView,
						"Please enter a player name", "Error",
						JOptionPane.ERROR_MESSAGE);
		} else if (x == GameView.getEarthling()) {
			String s = (String) JOptionPane
					.showInputDialog("Please enter fighter name");
			if (s != null && !(s.equals(""))) {
				game.getPlayer().createFighter('E', s);
				panels.show(GameView.getPanels(), "worldView");
				GameView.getStats().setVisible(true);
				updateStats();

			} else {
				JOptionPane.showMessageDialog(GameView,
						"Please enter a fighter name", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (x == GameView.getMajin()) {
			String s = (String) JOptionPane
					.showInputDialog("Please enter fighter name");
			if (s != null && !(s.equals(""))) {
				game.getPlayer().createFighter('M', s);
				panels.show(GameView.getPanels(), "worldView");
				GameView.getStats().setVisible(true);
				updateStats();
			} else {
				JOptionPane.showMessageDialog(GameView,
						"Please enter a fighter name", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (x == GameView.getFrieza()) {
			String s = (String) JOptionPane
					.showInputDialog("Please enter fighter name");
			if (s != null && !(s.equals(""))) {
				game.getPlayer().createFighter('F', s);
				panels.show(GameView.getPanels(), "worldView");
				GameView.getStats().setVisible(true);
				updateStats();
			} else {
				JOptionPane.showMessageDialog(GameView,
						"Please enter a fighter name", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (x == GameView.getSaiyan()) {
			String s = (String) JOptionPane
					.showInputDialog("Please enter fighter name");
			if (s != null && !(s.equals(""))) {
				game.getPlayer().createFighter('S', s);
				panels.show(GameView.getPanels(), "worldView");
				GameView.getStats().setVisible(true);
				updateStats();

			} else {
				JOptionPane.showMessageDialog(GameView,
						"Please enter a fighter name", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (x == GameView.getNamekian()) {
			String s = (String) JOptionPane
					.showInputDialog("Please enter fighter name");
			if (s != null && !(s.equals(""))) {
				game.getPlayer().createFighter('N', s);
				panels.show(GameView.getPanels(), "worldView");
				GameView.getStats().setVisible(true);
				updateStats();

			} else {
				JOptionPane.showMessageDialog(GameView,
						"Please enter a fighter name", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (x == GameView.getUp()) {
			disableCellIcon(game.getWorld().getPlayerRow(), game.getWorld()
					.getPlayerColumn());
			game.getWorld().moveUp();
			setCellIcon(game.getWorld().getPlayerRow(), game.getWorld()
					.getPlayerColumn());

		} else if (x == GameView.getDown()) {
			disableCellIcon(game.getWorld().getPlayerRow(), game.getWorld()
					.getPlayerColumn());
			game.getWorld().moveDown();
			setCellIcon(game.getWorld().getPlayerRow(), game.getWorld()
					.getPlayerColumn());
		} else if (x == GameView.getRight()) {
			disableCellIcon(game.getWorld().getPlayerRow(), game.getWorld()
					.getPlayerColumn());
			game.getWorld().moveRight();
			setCellIcon(game.getWorld().getPlayerRow(), game.getWorld()
					.getPlayerColumn());
		} else if (x == GameView.getLeft()) {
			disableCellIcon(game.getWorld().getPlayerRow(), game.getWorld()
					.getPlayerColumn());
			game.getWorld().moveLeft();
			setCellIcon(game.getWorld().getPlayerRow(), game.getWorld()
					.getPlayerColumn());
		} else if (x == GameView.getCreateNewFighter()) {
			GameView.getStats().setVisible(false);
			panels.show(GameView.getPanels(), "chooseFighter");
		} else if (x == GameView.getSwitchFighter()) {
			GameView.getStats().setVisible(false);
			if (GameView.getFightersBtns().size() != game.getPlayer()
					.getFighters().size())
				showFighters();
			panels.show(GameView.getPanels(), "fightersPanel");
		} else if (GameView.getFightersBtns().contains(x)) {
			int index = GameView.getFightersBtns().indexOf(x);
			game.getPlayer().setActiveFighter(
					game.getPlayer().getFighters().get(index));
			panels.show(GameView.getPanels(), "worldView");
			GameView.getStats().setVisible(true);
			updateStats();
		} else if (x == GameView.getAssignAttack()) {
			showAttacks();
			panels.show(GameView.getPanels(), "assignAttackPanel");
			GameView.getStats().setVisible(false);
		} else if (x == GameView.getDoneAssigning()) {
			Attack newAttack = (Attack) GameView.getNewattacks()
					.getSelectedItem();
			Attack oldAttack = (Attack) GameView.getOldattacks()
					.getSelectedItem();
			if (newAttack != null) {
				if (oldAttack != null) {
					if (newAttack instanceof SuperAttack
							&& oldAttack instanceof SuperAttack) {
						try {
							game.getPlayer().assignAttack(
									game.getPlayer().getActiveFighter(),
									(SuperAttack) newAttack,
									(SuperAttack) oldAttack);
						} catch (NotASaiyanException | DuplicateAttackException
								| MaximumAttacksLearnedException s) {
							JOptionPane.showMessageDialog(GameView,
									s.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);

						}
						updateStats();
						panels.show(GameView.getPanels(), "worldView");
						GameView.getStats().setVisible(true);

					} else if (newAttack instanceof UltimateAttack
							&& oldAttack instanceof UltimateAttack) {
						try {
							game.getPlayer().assignAttack(
									game.getPlayer().getActiveFighter(),
									(UltimateAttack) newAttack,
									(UltimateAttack) oldAttack);
						} catch (NotASaiyanException | DuplicateAttackException
								| MaximumAttacksLearnedException s) {
							JOptionPane.showMessageDialog(GameView,
									s.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);

						}
						updateStats();
						panels.show(GameView.getPanels(), "worldView");
						GameView.getStats().setVisible(true);
					} else {
						JOptionPane
								.showMessageDialog(
										GameView,
										"In order to swap attacks, Both attacks must be of the same type either SuperAttack (SA) or UltimateAttack (UA)",
										"Error", JOptionPane.ERROR_MESSAGE);
					}

				} else {
					if (newAttack instanceof SuperAttack) {
						try {
							game.getPlayer().assignAttack(
									game.getPlayer().getActiveFighter(),
									(SuperAttack) newAttack, null);
						} catch (NotASaiyanException | DuplicateAttackException
								| MaximumAttacksLearnedException s) {
							JOptionPane.showMessageDialog(GameView,
									s.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);

						}
						updateStats();
						panels.show(GameView.getPanels(), "worldView");
						GameView.getStats().setVisible(true);

					} else {

						try {
							game.getPlayer().assignAttack(
									game.getPlayer().getActiveFighter(),
									(UltimateAttack) newAttack, null);
						} catch (NotASaiyanException | DuplicateAttackException
								| MaximumAttacksLearnedException s) {
							JOptionPane.showMessageDialog(GameView,
									s.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);

						}
						updateStats();
						panels.show(GameView.getPanels(), "worldView");
						GameView.getStats().setVisible(true);
					}
				}
			} else {
				JOptionPane
						.showMessageDialog(
								GameView,
								"You dont have any attacks to assign to your fighters.",
								"No attacks", JOptionPane.ERROR_MESSAGE);

			}

		} else if (x == GameView.getBackToWorld1()) {
			panels.show(GameView.getPanels(), "worldView");
			GameView.getStats().setVisible(true);

		} else if (x == GameView.getUpgradeFighter()) {
			showUpgrades();
			GameView.getStats().setVisible(false);
			panels.show(GameView.getPanels(), "upgradeFighterPanel");
		} else if (x == GameView.getDoneUpgrading()) {
			if (GameView.getMaxHealth().isSelected())
				try {
					game.getPlayer().upgradeFighter(
							game.getPlayer().getActiveFighter(), 'H');
				} catch (NotEnoughAbilityPointsException s) {
					JOptionPane.showMessageDialog(GameView, s.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			else if (GameView.getPhysicalDamage().isSelected())
				try {
					game.getPlayer().upgradeFighter(
							game.getPlayer().getActiveFighter(), 'P');
				} catch (NotEnoughAbilityPointsException s) {
					JOptionPane.showMessageDialog(GameView, s.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			else if (GameView.getBlastDamage().isSelected())
				try {
					game.getPlayer().upgradeFighter(
							game.getPlayer().getActiveFighter(), 'B');
				} catch (NotEnoughAbilityPointsException s) {
					JOptionPane.showMessageDialog(GameView, s.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			else if (GameView.getMaxKi().isSelected())
				try {
					game.getPlayer().upgradeFighter(
							game.getPlayer().getActiveFighter(), 'K');
				} catch (NotEnoughAbilityPointsException s) {
					JOptionPane.showMessageDialog(GameView, s.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			else if (GameView.getMaxStamina().isSelected())
				try {
					game.getPlayer().upgradeFighter(
							game.getPlayer().getActiveFighter(), 'S');
				} catch (NotEnoughAbilityPointsException s) {
					JOptionPane.showMessageDialog(GameView, s.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			updateStats();
			panels.show(GameView.getPanels(), "worldView");
			GameView.getStats().setVisible(true);

		} else if (x == GameView.getBackToWorld()) {
			panels.show(GameView.getPanels(), "worldView");
			GameView.getStats().setVisible(true);
		} else if (x == GameView.getSave()) {
			try {
				game.save("yahia");
			} catch (IOException s) {
				s.printStackTrace();
			}
			JOptionPane.showMessageDialog(GameView, "Game Saved", "Game saved",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (x == GameView.getDoneWishing()) {
			DragonWish d = (DragonWish) GameView.getWishes().getSelectedItem();
			game.getPlayer().chooseWish(d);
			updateStats();
			panels.show(GameView.getPanels(), "worldView");
			GameView.getStats().setVisible(true);
		} else if (x == GameView.getAttack()) {
			GameView.getAttack().setVisible(false);
			GameView.getBlock().setVisible(false);
			GameView.getUse().setVisible(false);
			GameView.getSuperAtt().setVisible(true);
			GameView.getPhysicalAttack().setVisible(true);
			GameView.getUltimateAtt().setVisible(true);
			GameView.getBackToActions().setVisible(true);
			NonPlayableFighter k = (NonPlayableFighter) battle.getFoe();

		} else if (x == GameView.getPhysicalAttack()) {
			try {
				battle.attack(new PhysicalAttack());
			} catch (NotEnoughKiException f) {
				JOptionPane.showMessageDialog(GameView, f.getMessage(),
						"NotEnoughKi", JOptionPane.ERROR_MESSAGE);

			}

		} else if (x == GameView.getBlock()) {
			battle.block();
		} else if (x == GameView.getUse()) {
			try {
				battle.use(game.getPlayer(), Collectible.SENZU_BEAN);
			} catch (NotEnoughSenzuBeansException k) {
				JOptionPane.showMessageDialog(GameView, k.getMessage(),
						"Not enough senzu beans.", JOptionPane.ERROR_MESSAGE);

			}
		} else if (x == GameView.getBackToActions()) {
			GameView.getPhysicalAttack().setVisible(false);
			GameView.getSuperAtt().setVisible(false);
			GameView.getUltimateAtt().setVisible(false);
			GameView.getBackToActions().setVisible(false);
			GameView.getAttack().setVisible(true);
			GameView.getBlock().setVisible(true);
			GameView.getUse().setVisible(true);
		} else if (x == GameView.getSuperAtt()) {

			GameView.getSuperAtt().setVisible(false);
			GameView.getUltimateAtt().setVisible(false);
			GameView.getPhysicalAttack().setVisible(false);
			GameView.getSuperAttacks().setVisible(true);
			GameView.getBackToActions().setVisible(false);
			GameView.getDoAttack().setVisible(true);
			GameView.getUltimateAttacks().setVisible(false);
			GameView.getBackToActions1().setVisible(true);

		} else if (x == GameView.getUltimateAtt()) {
			GameView.getSuperAtt().setVisible(false);
			GameView.getUltimateAtt().setVisible(false);
			GameView.getPhysicalAttack().setVisible(false);
			GameView.getSuperAttacks().setVisible(true);
			GameView.getBackToActions().setVisible(false);
			GameView.getDoAttack().setVisible(true);
			GameView.getUltimateAttacks().setVisible(false);
			GameView.getBackToActions1().setVisible(true);
		} else if (x == GameView.getBackToActions1()) {
			GameView.getSuperAttacks().setVisible(false);
			GameView.getUltimateAttacks().setVisible(false);
			GameView.getDoAttack().setVisible(false);
			GameView.getPhysicalAttack().setVisible(true);
			GameView.getSuperAtt().setVisible(true);
			GameView.getUltimateAtt().setVisible(true);
			GameView.getBackToActions1().setVisible(false);
			GameView.getBackToActions().setVisible(true);

		} else if (x == GameView.getDoAttack()) {
			if (GameView.getSuperAttacks().getSelectedItem() != null) {
				try {
					battle.attack((SuperAttack) GameView.getSuperAttacks()
							.getSelectedItem());
				} catch (NotEnoughKiException u) {
					JOptionPane.showMessageDialog(GameView, u.getMessage(),
							"Not enough ki.", JOptionPane.ERROR_MESSAGE);
				}
				GameView.getSuperAttacks().setVisible(false);
				GameView.getUltimateAttacks().setVisible(false);
				GameView.getDoAttack().setVisible(false);
				GameView.getBackToActions1().setVisible(false);
				GameView.getAttack().setVisible(true);
				GameView.getBlock().setVisible(true);
				GameView.getUse().setVisible(true);
			}

			else if (GameView.getUltimateAttacks().getSelectedItem() != null) {
				try {
					battle.attack((UltimateAttack) GameView.getSuperAttacks()
							.getSelectedItem());
				} catch (NotEnoughKiException u) {
					JOptionPane.showMessageDialog(GameView, u.getMessage(),
							"Not enough ki.", JOptionPane.ERROR_MESSAGE);
				}
				GameView.getSuperAttacks().setVisible(false);
				GameView.getUltimateAttacks().setVisible(false);
				GameView.getDoAttack().setVisible(false);
				GameView.getBackToActions1().setVisible(false);
				GameView.getAttack().setVisible(true);
				GameView.getBlock().setVisible(true);
				GameView.getUse().setVisible(true);
			} else
				JOptionPane.showMessageDialog(GameView, "No attack selected",
						"No selected attack", JOptionPane.ERROR_MESSAGE);

		}
	}

	public void showUpgrades() {
		GameView.getUpgradeFighterPanel().removeAll();
		JLabel upgradingFighter = new JLabel();
		upgradingFighter.setBounds(50, 100, 400, 150);
		upgradingFighter.setFont(new Font("Saiyan Sans", Font.PLAIN, 72));
		upgradingFighter.setText("Upgrading");
		upgradingFighter.setForeground(Color.RED);
		JLabel upgradingFighter1 = new JLabel();
		upgradingFighter1.setBounds(260, 100, 400, 150);
		upgradingFighter1.setFont(new Font("Saiyan Sans", Font.PLAIN, 72));
		upgradingFighter1.setText(" Fighter");
		upgradingFighter1.setForeground(Color.ORANGE);
		GameView.getUpgradeFighterPanel().add(upgradingFighter);
		GameView.getUpgradeFighterPanel().add(upgradingFighter1);
		JLabel x = new JLabel();
		x.setBounds(50, 250, 300, 750);
		GameView.getUpgradeFighterPanel().add(GameView.getMaxHealth());
		GameView.getUpgradeFighterPanel().add(GameView.getPhysicalDamage());
		GameView.getUpgradeFighterPanel().add(GameView.getBlastDamage());
		GameView.getUpgradeFighterPanel().add(GameView.getMaxKi());
		GameView.getUpgradeFighterPanel().add(GameView.getMaxStamina());
		GameView.getUpgradeFighterPanel().add(GameView.getDoneUpgrading());
		GameView.getUpgradeFighterPanel().add(GameView.getBackToWorld());
		if (game.getPlayer().getActiveFighter() instanceof Frieza) {
			x.setIcon(new ImageIcon(((new ImageIcon("frieza.png").getImage()
					.getScaledInstance(x.getWidth(), x.getHeight(),
							java.awt.Image.SCALE_SMOOTH)))));
			GameView.getUpgradeFighterPanel().add(x);
		} else if (game.getPlayer().getActiveFighter() instanceof Saiyan) {
			x.setIcon(new ImageIcon(((new ImageIcon("saiyan.png").getImage()
					.getScaledInstance(x.getWidth(), x.getHeight(),
							java.awt.Image.SCALE_SMOOTH)))));
			GameView.getUpgradeFighterPanel().add(x);
		} else if (game.getPlayer().getActiveFighter() instanceof Majin) {
			x.setIcon(new ImageIcon(((new ImageIcon("majin.png").getImage()
					.getScaledInstance(x.getWidth(), x.getHeight(),
							java.awt.Image.SCALE_SMOOTH)))));
			GameView.getUpgradeFighterPanel().add(x);
		} else if (game.getPlayer().getActiveFighter() instanceof Earthling) {
			x.setIcon(new ImageIcon(((new ImageIcon("earthling.png").getImage()
					.getScaledInstance(x.getWidth(), x.getHeight(),
							java.awt.Image.SCALE_SMOOTH)))));
			GameView.getUpgradeFighterPanel().add(x);
		} else {
			x.setIcon(new ImageIcon(((new ImageIcon("namekian.png").getImage()
					.getScaledInstance(x.getWidth(), x.getHeight(),
							java.awt.Image.SCALE_SMOOTH)))));
			GameView.getUpgradeFighterPanel().add(x);
		}
		JLabel y = new JLabel();
		y.setFont(y.getFont().deriveFont(23f));
		y.setText("Current Maximum HealthPoints: "
				+ game.getPlayer().getActiveFighter().getMaxHealthPoints());
		y.setBounds(1000, 250, 450, 100);
		JLabel z = new JLabel();
		z.setFont(z.getFont().deriveFont(23f));
		z.setText("Current Physical Damage: "
				+ game.getPlayer().getActiveFighter().getPhysicalDamage());
		z.setBounds(1000, 300, 400, 100);
		JLabel w = new JLabel();
		w.setFont(w.getFont().deriveFont(23f));
		w.setText("Current Blast Damage: "
				+ game.getPlayer().getActiveFighter().getBlastDamage());
		w.setBounds(1000, 350, 400, 100);
		JLabel k = new JLabel();
		k.setFont(k.getFont().deriveFont(23f));
		k.setText("Current Maximum Ki: "
				+ game.getPlayer().getActiveFighter().getMaxKi());
		k.setBounds(1000, 400, 400, 100);
		JLabel l = new JLabel();
		l.setFont(l.getFont().deriveFont(23f));
		l.setText("Current Maximum Stamina: "
				+ game.getPlayer().getActiveFighter().getMaxStamina());
		l.setBounds(1000, 450, 400, 100);
		JLabel p = new JLabel();
		p.setFont(p.getFont().deriveFont(23f));
		p.setText("Current Ability Points: "
				+ game.getPlayer().getActiveFighter().getAbilityPoints());
		p.setBounds(1000, 500, 400, 100);
		GameView.getUpgradeFighterPanel().add(y);
		GameView.getUpgradeFighterPanel().add(z);
		GameView.getUpgradeFighterPanel().add(w);
		GameView.getUpgradeFighterPanel().add(k);
		GameView.getUpgradeFighterPanel().add(l);
		GameView.getUpgradeFighterPanel().add(p);

	}

	public void showAttacks() {
		GameView.getNewattacks().removeAllItems();
		GameView.getOldattacks().removeAllItems();
		GameView.getCurrentSuperAttacks().setText(
				"Current number of super attacks: "
						+ game.getPlayer().getActiveFighter().getSuperAttacks()
								.size());
		GameView.getCurrentUltimateAttacks().setText(
				"Current number of ultimate attacks: "
						+ game.getPlayer().getActiveFighter()
								.getUltimateAttacks().size());
		for (Attack attack : game.getPlayer().getSuperAttacks())
			GameView.getNewattacks().addItem((SuperAttack) attack);
		for (Attack attack : game.getPlayer().getUltimateAttacks())
			GameView.getNewattacks().addItem((UltimateAttack) attack);
		for (SuperAttack attack : game.getPlayer().getActiveFighter()
				.getSuperAttacks())
			GameView.getOldattacks().addItem(attack);
		for (UltimateAttack attack : game.getPlayer().getActiveFighter()
				.getUltimateAttacks())
			GameView.getOldattacks().addItem(attack);
		GameView.getOldattacks().addItem(null);
	}

	public void showFighters() {
		GameView.getFightersBtns().clear();
		GameView.getFightersPanel().removeAll();
		for (PlayableFighter f : game.getPlayer().getFighters()) {
			JButton fighter = new JButton();
			fighter.setSize(150, 250);
			fighter.setContentAreaFilled(false);
			fighter.setOpaque(false);
			fighter.setFocusPainted(false);
			if (f instanceof Saiyan)
				fighter.setIcon(new ImageIcon(((new ImageIcon("saiyan.png")
						.getImage().getScaledInstance(fighter.getWidth(),
						fighter.getHeight(), java.awt.Image.SCALE_SMOOTH)))));
			else if (f instanceof Namekian)
				fighter.setIcon(new ImageIcon(((new ImageIcon("namekian.png")
						.getImage().getScaledInstance(fighter.getWidth(),
						fighter.getHeight(), java.awt.Image.SCALE_SMOOTH)))));
			else if (f instanceof Frieza)
				fighter.setIcon(new ImageIcon(((new ImageIcon("frieza.png")
						.getImage().getScaledInstance(fighter.getWidth(),
						fighter.getHeight(), java.awt.Image.SCALE_SMOOTH)))));
			else if (f instanceof Majin)
				fighter.setIcon(new ImageIcon(((new ImageIcon("majin.png")
						.getImage().getScaledInstance(fighter.getWidth(),
						fighter.getHeight(), java.awt.Image.SCALE_SMOOTH)))));
			else
				fighter.setIcon(new ImageIcon(((new ImageIcon("earthling.png")
						.getImage().getScaledInstance(fighter.getWidth(),
						fighter.getHeight(), java.awt.Image.SCALE_SMOOTH)))));
			GameView.getFightersBtns().add(fighter);
			fighter.addActionListener(this);
			GameView.getFightersPanel().add(fighter);

		}
	}

	public void updateStats() {
		showAttacks();
		GameView.getPlayerName().setText(
				"Player Name: " + game.getPlayer().getName());
		GameView.getFighterName().setText(
				"Fighter Name: "
						+ game.getPlayer().getActiveFighter().getName());
		GameView.getFighterLevel().setText(
				"Fighter Level: "
						+ game.getPlayer().getActiveFighter().getLevel());
		GameView.getDragonBalls().setIcon(new ImageIcon(
				((new ImageIcon("dragonballball.png").getImage().getScaledInstance(
						GameView.getDragonBalls().getWidth(),
						GameView.getDragonBalls().getHeight(),
						java.awt.Image.SCALE_SMOOTH)))));
		GameView.getDragonBallstext().setText(": "+game.getPlayer().getDragonBalls());
		GameView.getSenzuBeans().setIcon(new ImageIcon(
				((new ImageIcon("senzubean.png").getImage().getScaledInstance(
						GameView.getSenzuBeans().getWidth(),
						GameView.getSenzuBeans().getHeight(),
						java.awt.Image.SCALE_SMOOTH)))));
		GameView.getSenzuBeanstext().setText(": "+game.getPlayer().getSenzuBeans());

	}

	public void disableCellIcon(int row, int column) {
		GameView.getMapView()[row][column].setIcon(null);
		GameView.getMapView()[row][column].setDisabledIcon(null);
	}

	public void setCellIcon(int row, int column) {
		GameView.getMapView()[row][column].setIcon(new ImageIcon(
				((new ImageIcon("goku.gif").getImage().getScaledInstance(
						GameView.getMapView()[0][0].getWidth(),
						GameView.getMapView()[0][0].getHeight(),
						java.awt.Image.SCALE_SMOOTH)))));
		GameView.getMapView()[row][column].setDisabledIcon(new ImageIcon(
				((new ImageIcon("goku.gif").getImage().getScaledInstance(
						GameView.getMapView()[0][0].getWidth(),
						GameView.getMapView()[0][0].getHeight(),
						java.awt.Image.SCALE_SMOOTH)))));

	}

	public void onCollectibleFound(Collectible collectible) {
		switch (collectible) {
		case SENZU_BEAN:
			JOptionPane.showMessageDialog(GameView,
					"You have collected a senzu bean", "Collectible found",
					JOptionPane.INFORMATION_MESSAGE, new ImageIcon(
							"senzufound.gif"));
			break;
		case DRAGON_BALL:
			JOptionPane.showMessageDialog(GameView,
					"You have collected a dragon ball", "Collectible found",
					JOptionPane.INFORMATION_MESSAGE, new ImageIcon(
							"dragonball2.jpg"));
			break;
		}
		updateStats();

	}

	public void onDragonCalled(Dragon dragon) {
		JOptionPane
				.showMessageDialog(
						GameView,
						"You have collected 7 dragonballs, you will meet the dragon now!",
						"7 Dragon balls found", JOptionPane.INFORMATION_MESSAGE);
		GameView.getStats().setVisible(false);
		JLabel x = new JLabel();
		x.setFont(x.getFont().deriveFont(Font.BOLD, 20f));
		x.setText("Available Wishes: ");
		x.setBounds(1350, 365, 250, 30);
		GameView.getDragonPanel().add(x);
		panels.show(GameView.getPanels(), "dragonPanel");
		DragonWish[] wishes = dragon.getWishes();
		for (DragonWish wish : wishes) {
			GameView.getWishes().addItem(wish);

		}
	}

	public void onBattleEvent(BattleEvent e) {
		Battle b = (Battle) e.getSource();
		battle = b;
		NonPlayableFighter f = (NonPlayableFighter) battle.getFoe();
		foe1 = f;
		me = game.getPlayer().getActiveFighter();

		JLabel me = new JLabel();
		JLabel foe = new JLabel();
		JLabel meFace = new JLabel();
		JLabel foeFace = new JLabel();
		if (e.getType() == BattleEventType.STARTED) {
			GameView.getBattlePanel().removeAll();
			GameView.getSuperAttacks().removeAllItems();
			GameView.getUltimateAttacks().removeAllItems();
			for (SuperAttack att : game.getPlayer().getActiveFighter()
					.getSuperAttacks())
				GameView.getSuperAttacks().addItem(att);
			for (UltimateAttack att : game.getPlayer().getActiveFighter()
					.getUltimateAttacks())
				GameView.getUltimateAttacks().addItem(att);
			JOptionPane.showMessageDialog(GameView,
					"Get ready! The battle is about to start!",
					"Battle notification", JOptionPane.INFORMATION_MESSAGE);
			me.setFont(me.getFont().deriveFont(24f));
			me.setBounds(120, 20, 250, 50);
			me.setText(game.getPlayer().getActiveFighter().getName()
					+ "          Level:"
					+ game.getPlayer().getActiveFighter().getLevel());

			foe.setFont(foe.getFont().deriveFont(24f));
			foe.setBounds(1500, 20, 400, 50);
			foe.setText(f.getName() + "        Level:" + f.getLevel());
			meFace.setBounds(40, 5, 80, 100);
			meFace.setIcon(new ImageIcon(((new ImageIcon("gokuface.png")
					.getImage().getScaledInstance(meFace.getWidth(),
					meFace.getHeight(), java.awt.Image.SCALE_SMOOTH)))));

			foeFace.setBounds(1400, 20, 80, 100);
			foeFace.setIcon(new ImageIcon(((new ImageIcon("goroface.png")
					.getImage().getScaledInstance(foeFace.getWidth(),
					foeFace.getHeight(), java.awt.Image.SCALE_SMOOTH)))));

			foeHealth = new JProgressBar(0, f.getMaxHealthPoints());
			foeHealth.setStringPainted(true);
			foeHealth.setValue(f.getHealthPoints());
			foeHealth.setString("HP " + foeHealth.getValue() + " / "
					+ foeHealth.getMaximum());
			foeHealth.setBounds(1500, 60, 350, 30);
			foeHealth.setForeground(Color.GREEN);
			foeHealth.setBackground(Color.RED);
			GameView.getBattlePanel().add(foeHealth);

			meHealth = new JProgressBar(0, game.getPlayer().getActiveFighter()
					.getMaxHealthPoints());
			meHealth.setStringPainted(true);
			meHealth.setValue(game.getPlayer().getActiveFighter()
					.getHealthPoints());
			meHealth.setString("HP " + meHealth.getValue() + " / "
					+ meHealth.getMaximum());
			meHealth.setBounds(120, 60, 350, 30);
			meHealth.setForeground(Color.GREEN);
			meHealth.setBackground(Color.RED);
			meStamina = new JProgressBar(0, game.getPlayer().getActiveFighter()
					.getMaxStamina());
			meStamina.setStringPainted(true);
			meStamina
					.setValue(game.getPlayer().getActiveFighter().getStamina());
			meStamina.setString("Stamina " + meStamina.getValue() + " / "
					+ meStamina.getMaximum());
			meStamina.setBounds(120, 90, 150, 30);
			meStamina.setForeground(Color.ORANGE);

			meKi = new JProgressBar(0, game.getPlayer().getActiveFighter()
					.getMaxKi());
			meKi.setStringPainted(true);
			meKi.setValue(game.getPlayer().getActiveFighter().getKi());
			meKi.setString("Ki " + meKi.getValue() + " / " + meKi.getMaximum());
			meKi.setBounds(120, 120, 150, 30);
			meKi.setForeground(Color.ORANGE);

			foeStamina = new JProgressBar(0, f.getMaxStamina());
			foeStamina.setStringPainted(true);
			foeStamina.setValue(f.getStamina());
			foeStamina.setString("Stamina " + foeStamina.getValue() + " / "
					+ foeStamina.getMaximum());
			foeStamina.setBounds(1500, 90, 150, 30);
			foeStamina.setForeground(Color.ORANGE);

			foeKi = new JProgressBar(0, f.getMaxKi());
			foeKi.setStringPainted(true);
			foeKi.setValue(f.getKi());
			foeKi.setString("Ki " + foeKi.getValue() + " / "
					+ foeKi.getMaximum());
			foeKi.setBounds(1500, 120, 150, 30);
			foeKi.setForeground(Color.ORANGE);

			GameView.getBattlePanel().add(meHealth);
			GameView.getBattlePanel().add(meKi);
			GameView.getBattlePanel().add(meStamina);
			GameView.getBattlePanel().add(foeStamina);
			GameView.getBattlePanel().add(foeKi);
			GameView.getBattlePanel().add(foeFace);
			GameView.getBattlePanel().add(meFace);
			GameView.getBattlePanel().add(me);
			GameView.getBattlePanel().add(foe);
			GameView.getBattlePanel().add(GameView.getMe());
			GameView.getBattlePanel().add(GameView.getFoe());
			panels.show(GameView.getPanels(), "battlePanel");
			GameView.getStats().setVisible(false);
			GameView.getBattleActions().setVisible(true);
		} else if (e.getType() == BattleEventType.ATTACK) {
			update();
			if (e.getAttack() instanceof SuperSaiyan)
				JOptionPane.showMessageDialog(GameView,
						((Fighter) battle.getAttacker()).getName()
								+ " has transformed into a super saiyan. ",
						"Super saiyan notifiation",
						JOptionPane.INFORMATION_MESSAGE);
			else

				JOptionPane.showMessageDialog(GameView,
						((Fighter) battle.getAttacker()).getName()
								+ " has attacked with "
								+ e.getAttack().getName(),
						"Attack notifiation", JOptionPane.INFORMATION_MESSAGE);

		} else if (e.getType() == BattleEventType.BLOCK) {
			update();
			JOptionPane
					.showMessageDialog(GameView,
							((Fighter) battle.getAttacker()).getName()
									+ " has blocked", "Block notification",
							JOptionPane.INFORMATION_MESSAGE);

		} else if (e.getType() == BattleEventType.USE) {
			update();
			JOptionPane.showMessageDialog(GameView,
					((Fighter) battle.getAttacker()).getName()
							+ " has used a senzu bean", "Use notification",
					JOptionPane.INFORMATION_MESSAGE);

		} else if (e.getType() == BattleEventType.NEW_TURN) {
			JOptionPane
					.showMessageDialog(GameView,
							((Fighter) battle.getAttacker()).getName()
									+ " on turn", "Switch turn notification",
							JOptionPane.INFORMATION_MESSAGE);
			GameView.getSuperAttacks().setVisible(false);
			GameView.getUltimateAttacks().setVisible(false);
			GameView.getDoAttack().setVisible(false);
			GameView.getPhysicalAttack().setVisible(false);
			GameView.getBackToActions().setVisible(false);
			GameView.getSuperAtt().setVisible(false);
			GameView.getUltimateAtt().setVisible(false);
			GameView.getBackToActions1().setVisible(false);
			GameView.getAttack().setVisible(true);
			GameView.getBlock().setVisible(true);
			GameView.getUse().setVisible(true);
			if (battle.getAttacker() == battle.getFoe())
				do {
					try {
						battle.play();
					} catch (NotEnoughKiException s) {

					}
					break;
				} while (true);
		}

		else if (e.getType() == BattleEventType.ENDED) {
			if (e.getWinner() == battle.getFoe()) {
				JOptionPane.showMessageDialog(GameView,
						"You lost. " + f.getName() + " has won the battle.",
						"You lost", JOptionPane.INFORMATION_MESSAGE);
				for (int i = 0; i < 10; i++)
					for (int j = 0; j < 10; j++)
						if (i != 0 && j != 0)
							GameView.getMapView()[i][j].setIcon(null);
				if (f.isStrong()) {
					GameView.getMapView()[0][0].setIcon(new ImageIcon(
							"daba72.gif"));
					GameView.getMapView()[0][0].setDisabledIcon(new ImageIcon(
							"daba72.gif"));
				}
				setCellIcon(game.getWorld().getPlayerRow(), game.getWorld()
						.getPlayerColumn());
				panels.show(GameView.getPanels(), "worldView");
				GameView.getStats().setVisible(true);
				GameView.getBattleActions().setVisible(false);
			} else if (e.getWinner() == battle.getMe()) {
				updateStats();
				String attacks = "";
				String newLevel = "Your new level: "
						+ game.getPlayer().getActiveFighter().getLevel()
						+ "\nNew target xp: "
						+ game.getPlayer().getActiveFighter().getTargetXp()
						+ "\nGained ability points: 2";
				for (SuperAttack att : f.getSuperAttacks())
					attacks += att.getName() + " (SA)\n";
				for (UltimateAttack att : f.getUltimateAttacks())
					attacks += att.getName() + " (UA)\n";
				if (f.isStrong()) {
					JOptionPane
							.showMessageDialog(
									GameView,
									"You have defeated a boss, a new map will be generated",
									"Boss defeated",
									JOptionPane.INFORMATION_MESSAGE);

				}

				if (game.getPlayer().getActiveFighter().getXp() != 0)
					JOptionPane.showMessageDialog(GameView,
							"You won\nCurrent XP: "
									+ game.getPlayer().getActiveFighter()
											.getXp() + "\nGained Skills: "
									+ attacks);
				else
					JOptionPane.showMessageDialog(GameView,
							"You won\nCurrent XP: "
									+ game.getPlayer().getActiveFighter()
											.getXp() + "\nGained Skills: "
									+ attacks + newLevel);

				panels.show(GameView.getPanels(), "worldView");
				GameView.getStats().setVisible(true);
				GameView.getBattleActions().setVisible(false);

			}

		}
	}

	public void update() {
		foeHealth.setValue(foe1.getHealthPoints());
		foeHealth.setString("HP " + foeHealth.getValue() + " / "
				+ foeHealth.getMaximum());
		foeKi.setValue(foe1.getKi());
		foeKi.setString("Ki " + foeKi.getValue() + " / " + foeKi.getMaximum());
		meKi.setValue(me.getKi());
		meKi.setString("Ki " + meKi.getValue() + " / " + meKi.getMaximum());
		foeStamina.setValue(foe1.getStamina());
		foeStamina.setString("Stamina " + foeStamina.getValue() + " / "
				+ foeStamina.getMaximum());
		meStamina.setValue(me.getStamina());
		meStamina.setString("Stamina " + meStamina.getValue() + " / "
				+ meStamina.getMaximum());
		meHealth.setValue(me.getHealthPoints());
		meHealth.setString("HP " + meHealth.getValue() + " / "
				+ meHealth.getMaximum());

	}

	public Game getGame() {
		return game;
	}

	public static void main(String[] args) throws IOException {
		GameGUI x = new GameGUI();
		

	}
}
