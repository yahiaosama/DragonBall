package dragonball.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;

import dragonball.controller.ArrowAction;

public class DragonballView extends JFrame {
	private JButton newGame;
	private JButton loadGame;
	private JButton chosePlayer;
	private JTextField enterName;
	private JButton up;
	private JButton down;
	private JButton right;
	private JButton left;
	private JPanelWithBackground menu;
	private JPanelWithBackground newPlayer;
	private JPanelWithBackground worldView;
	private ArrayList<JButton> fightersBtns;
	private JPanelWithBackground fightersPanel;
	private JPanelWithBackground assignAttackPanel;
	private JPanelWithBackground battlePanel;
	private JPanel upgradeFighterPanel;
	private JComboBox newattacks;
	private JComboBox oldattacks;
	private JComboBox wishes;
	private JComboBox superAttacks;
	private JComboBox ultimateAttacks;
	private JButton[][] mapView;
	private JPanel panels;
	private JPanel chooseFighter;
	private JLabel senzuBeanstext;
	private JPanel battleActions;
	private ArrayList<JButton> btns;
	private JLabel foe;
	private JLabel me;
	private JLabel playerName;
	private JLabel fighterName;
	private JLabel fighterLevel;
	private JLabel dragonBalls;
	private JLabel senzuBeans;
	private JLabel currentSuperAttacks;
	private JLabel currentUltimateAttacks;
	private JLabel dragonBallstext;
	private JPanelWithBackground dragonPanel;
	private JPanel stats;
	private JButton backToActions;
	private JRadioButton maxHealth;
	private JRadioButton physicalDamage;
	private JRadioButton blastDamage;
	private JRadioButton maxKi;
	private JRadioButton maxStamina;
	private JButton doneUpgrading;
	private JButton backToWorld;
	private JButton createNewFighter;
	private JButton attack;
	private JButton doAttack;
	private JButton backToActions1;
	private JButton block;
	private JButton use;
	private JButton physicalAttack;
	private JButton superAtt;
	private JButton ultimateAtt;
	private JButton backToWorld1;
	private JButton switchFighter;
	private JButton assignAttack;
	private JButton upgradeFighter;
	private JButton save;
	private JButton doneWishing;
	private JButton doneAssigning;
	private JButton frieza;
	private JButton saiyan;
	private JButton majin;
	private JButton earthling;
	private JButton namekian;

	public DragonballView() throws IOException {
		super("DragonBall Adventures");
		btns = new ArrayList<JButton>();
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0,0,screenSize.width,screenSize.height);
		setLayout(new BorderLayout());
		newGame = new JButton("New Game");
		loadGame = new JButton("Load Game");
		menu = new JPanelWithBackground("menu.png");
		menu.setLayout(null);
		newGame.setBounds(400, 650, 300, 70);
		loadGame.setBounds(400, 830, 300, 70);
		newGame.setFont(new Font("Saiyan Sans",Font.PLAIN,48));
		loadGame.setFont(new Font("Saiyan Sans",Font.PLAIN,48));
		newGame.setBackground(Color.ORANGE);
		newGame.setForeground(Color.BLACK);
		loadGame.setBackground(Color.ORANGE);
		loadGame.setForeground(Color.BLACK);
		
		newGame.setFocusPainted(false);
		
		loadGame.setFocusPainted(false);
		
		menu.add(newGame);
		menu.add(loadGame);
		btns.add(newGame);
		btns.add(loadGame);

		newPlayer = new JPanelWithBackground("newplayer.jpg");
		chosePlayer = new JButton("To race selection");
		chosePlayer.setBackground(Color.ORANGE);
		chosePlayer.setForeground(Color.BLACK);
		chosePlayer.setFocusPainted(false);
		JLabel playerNameLabel = new JLabel("Choose a player name:");
		JLabel fighterNameLabel = new JLabel("Choose a fighter name:");
		enterName = new JTextField();
		newPlayer.setLayout(null);
		newPlayer.add(enterName);
		newPlayer.add(chosePlayer);
		newPlayer.add(playerNameLabel);
		newPlayer.add(fighterNameLabel);
		chosePlayer.setBounds(1080, 600, 150, 40);
		playerNameLabel.setBounds(800, 500, 200, 50);
		enterName.setBounds(1050, 500, 200, 50);
		btns.add(chosePlayer);

		chooseFighter = new JPanel();
		chooseFighter.setBackground(Color.WHITE);
		chooseFighter.setLayout(null);
		frieza = new JButton();
		frieza.setBounds(0, 0, 385, 1000);
		frieza.setIcon(new ImageIcon(((new ImageIcon("frieza.png").getImage()
				.getScaledInstance(frieza.getWidth(), frieza.getHeight(),
						java.awt.Image.SCALE_SMOOTH)))));
		chooseFighter.add(frieza);

		saiyan = new JButton();
		saiyan.setBounds(385, 0, 385, 1000);
		saiyan.setIcon(new ImageIcon(((new ImageIcon("saiyan.png").getImage()
				.getScaledInstance(saiyan.getWidth(), saiyan.getHeight(),
						java.awt.Image.SCALE_SMOOTH)))));
		chooseFighter.add(saiyan);

		majin = new JButton();
		majin.setBounds(770, 0, 385, 1000);
		majin.setIcon(new ImageIcon(((new ImageIcon("majin.png").getImage()
				.getScaledInstance(majin.getWidth(), majin.getHeight(),
						java.awt.Image.SCALE_SMOOTH)))));
		chooseFighter.add(majin);

		earthling = new JButton();
		earthling.setBounds(1155, 0, 385, 1000);
		earthling.setIcon(new ImageIcon(((new ImageIcon("earthling.png")
				.getImage().getScaledInstance(earthling.getWidth(),
				earthling.getHeight(), java.awt.Image.SCALE_SMOOTH)))));
		chooseFighter.add(earthling);

		namekian = new JButton();
		namekian.setBounds(1540, 0, 385, 1000);
		namekian.setIcon(new ImageIcon(((new ImageIcon("namekian.png")
				.getImage().getScaledInstance(namekian.getWidth(),
				namekian.getHeight(), java.awt.Image.SCALE_SMOOTH)))));
		chooseFighter.add(namekian);

		btns.add(saiyan);
		btns.add(majin);
		btns.add(earthling);
		btns.add(frieza);
		btns.add(namekian);

		mapView = new JButton[10][10];
		worldView = new JPanelWithBackground("maptest.jpg");
		worldView.setLayout(new GridLayout(10, 10));

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				mapView[i][j] = new JButton();
				mapView[i][j].setEnabled(false);
				mapView[i][j].setOpaque(false);
				mapView[i][j].setSize(new Dimension(100, 100));
				mapView[i][j].setContentAreaFilled(false);
				worldView.add(mapView[i][j]);

			}
		mapView[9][9].setIcon(new ImageIcon(((new ImageIcon("goku.gif")
				.getImage().getScaledInstance(mapView[9][9].getWidth(),
				mapView[9][9].getHeight(), java.awt.Image.SCALE_SMOOTH)))));
		mapView[9][9].setDisabledIcon(new ImageIcon(((new ImageIcon("goku.gif")
				.getImage().getScaledInstance(mapView[9][9].getWidth(),
				mapView[9][9].getHeight(), java.awt.Image.SCALE_SMOOTH)))));
		mapView[0][0].setIcon(new ImageIcon("daba72.gif"));
		mapView[0][0].setDisabledIcon(new ImageIcon("daba72.gif"));

		stats = new JPanel();
		stats.setLayout(null);
		add(stats, BorderLayout.EAST);
		stats.setPreferredSize(new Dimension(450, 1280));
		stats.setBackground(Color.BLACK);
		stats.setVisible(false);

		up = new JButton();
		up.setBounds(225, 760, 80, 70);
		up.setIcon(new ImageIcon(((new ImageIcon("up.png").getImage()
				.getScaledInstance(up.getWidth(), up.getHeight(),
						java.awt.Image.SCALE_SMOOTH)))));
		up.setOpaque(false);
		up.setContentAreaFilled(false);
		stats.add(up);

		down = new JButton();
		down.setBounds(225, 900, 80, 70);
		down.setIcon(new ImageIcon(((new ImageIcon("down.png").getImage()
				.getScaledInstance(down.getWidth(), down.getHeight(),
						java.awt.Image.SCALE_SMOOTH)))));
		down.setOpaque(false);
		down.setContentAreaFilled(false);
		stats.add(down);

		right = new JButton();
		right.setBounds(310, 830, 80, 70);
		right.setIcon(new ImageIcon(((new ImageIcon("right.png").getImage()
				.getScaledInstance(right.getWidth(), right.getHeight(),
						java.awt.Image.SCALE_SMOOTH)))));
		right.setOpaque(false);
		right.setContentAreaFilled(false);
		stats.add(right);

		left = new JButton();
		left.setBounds(140, 830, 80, 70);
		left.setIcon(new ImageIcon(((new ImageIcon("left.png").getImage()
				.getScaledInstance(left.getWidth(), left.getHeight(),
						java.awt.Image.SCALE_SMOOTH)))));
		left.setOpaque(false);
		left.setContentAreaFilled(false);
		stats.add(left);

		btns.add(up);
		btns.add(right);
		btns.add(down);
		btns.add(left);

		playerName = new JLabel();
		fighterName = new JLabel();
		fighterLevel = new JLabel();
		dragonBalls = new JLabel();
		dragonBallstext = new JLabel();
		senzuBeans = new JLabel();
		senzuBeanstext = new JLabel();

		playerName.setBounds(0, 50, 450, 50);
		fighterName.setBounds(0, 100, 450, 50);
		fighterLevel.setBounds(0, 150, 450, 50);
		dragonBalls.setBounds(0, 200, 50, 50);
		dragonBallstext.setBounds(55,200,100,50);
		senzuBeans.setBounds(0, 250, 50, 50);
		senzuBeanstext.setBounds(55,250,50,50);

		playerName.setFont(playerName.getFont().deriveFont(25.0f));
		fighterName.setFont(playerName.getFont());
		dragonBallstext.setFont(playerName.getFont());
		fighterLevel.setFont(playerName.getFont());
		dragonBalls.setFont(playerName.getFont());
		senzuBeans.setFont(playerName.getFont());
		senzuBeanstext.setFont(playerName.getFont());
		
		playerName.setForeground(Color.WHITE);
		fighterName.setForeground(Color.WHITE);
		dragonBallstext.setForeground(Color.WHITE);
		senzuBeanstext.setForeground(Color.WHITE);
		fighterLevel.setForeground(Color.WHITE);
		dragonBalls.setForeground(Color.WHITE);
		senzuBeans.setForeground(Color.WHITE);

		createNewFighter = new JButton();
		createNewFighter.setBounds(0, 300, 450, 92);
		createNewFighter.setBackground(Color.BLACK);
		createNewFighter.setFont(new Font("Saiyan Sans",Font.PLAIN,48));
		createNewFighter.setForeground(Color.WHITE);
		createNewFighter.setText("CREATE NEW FIGHTER");
		createNewFighter.setOpaque(false);
		createNewFighter.setFocusPainted(false);

		switchFighter = new JButton();
		switchFighter.setBounds(0, 392, 415, 92);
		switchFighter.setBackground(Color.BLACK);
		switchFighter.setFont(new Font("Saiyan Sans",Font.PLAIN,48));
		switchFighter.setForeground(Color.WHITE);
		switchFighter.setText("SWITCH FIGHTER");
		switchFighter.setOpaque(false);
		switchFighter.setFocusPainted(false);

		assignAttack = new JButton();
		assignAttack.setBounds(0, 484, 399, 92);
		assignAttack.setBackground(Color.BLACK);
		assignAttack.setFont(new Font("Saiyan Sans",Font.PLAIN,48));
		assignAttack.setForeground(Color.WHITE);
		assignAttack.setText("ASSIGN ATTACK");
		assignAttack.setOpaque(false);
		assignAttack.setFocusPainted(false);

		upgradeFighter = new JButton();
		upgradeFighter.setBounds(0, 576, 421, 92);
		upgradeFighter.setBackground(Color.BLACK);
		upgradeFighter.setFont(new Font("Saiyan Sans",Font.PLAIN,48));
		upgradeFighter.setForeground(Color.WHITE);
		upgradeFighter.setText("UPGRADE FIGHTER");
		upgradeFighter.setOpaque(false);
		upgradeFighter.setFocusPainted(false);

		save = new JButton();
		save.setBounds(0, 668, 305, 92);
		save.setBackground(Color.BLACK);
		save.setFont(new Font("Saiyan Sans",Font.PLAIN,48));
		save.setForeground(Color.WHITE);
		save.setText("SAVE GAME");
		save.setOpaque(false);
		save.setFocusPainted(false);

		assignAttackPanel = new JPanelWithBackground("assigning.png");
		assignAttackPanel.setLayout(null);
		assignAttackPanel.setBackground(Color.WHITE);
		JLabel y = new JLabel();
		JLabel y2 = new JLabel();
		JLabel y3 = new JLabel();
		JLabel y4 = new JLabel();
		y.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		y.setText("New ");
		y.setBounds(850, 480, 150, 50);
		y.setForeground(Color.ORANGE);
		y2.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		y2.setBounds(925, 480, 150, 50);
		y2.setText("Attack:");
		y2.setForeground(Color.RED);
		y3.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		y3.setText("Old");
		y3.setBounds(1300, 480, 150, 50);
		y3.setForeground(Color.RED);
		y4.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		y4.setText("Attack");
		y4.setBounds(1375, 480, 150, 50);
		y4.setForeground(Color.ORANGE);
		newattacks = new JComboBox();
		oldattacks = new JComboBox();
		newattacks.setBounds(850, 530, 300, 50);
		oldattacks.setBounds(1300, 530, 300, 50);

		doneAssigning = new JButton();
		doneAssigning.setFont(new Font("Saiyan Sans", Font.PLAIN, 72));
		doneAssigning.setText("Finish");
		doneAssigning.setBackground(Color.WHITE);
		doneAssigning.setOpaque(false);
		doneAssigning.setFocusPainted(false);
		doneAssigning.setBounds(1060, 625, 225, 72);

		currentSuperAttacks = new JLabel();
		currentSuperAttacks.setBounds(700, 50, 1000, 72);
		currentSuperAttacks.setFont(currentSuperAttacks.getFont().deriveFont(
				25.0f));

		currentUltimateAttacks = new JLabel();
		currentUltimateAttacks.setBounds(700, 150, 1000, 72);
		currentUltimateAttacks.setFont(currentUltimateAttacks.getFont()
				.deriveFont(25.0f));

		assignAttackPanel.add(y);
		assignAttackPanel.add(y2);
		assignAttackPanel.add(y3);
		assignAttackPanel.add(y4);
		assignAttackPanel.add(newattacks);
		assignAttackPanel.add(oldattacks);
		assignAttackPanel.add(doneAssigning);
		assignAttackPanel.add(currentSuperAttacks);
		assignAttackPanel.add(currentUltimateAttacks);

		upgradeFighterPanel = new JPanel();
		upgradeFighterPanel.setBackground(Color.WHITE);
		upgradeFighterPanel.setLayout(null);
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
		upgradeFighterPanel.add(upgradingFighter);
		upgradeFighterPanel.add(upgradingFighter1);

		maxHealth = new JRadioButton(
				"Increase Maximum Health Points by 50 points.");
		physicalDamage = new JRadioButton(
				"Increase Physical Damage by 50 points.");
		blastDamage = new JRadioButton("Increase Blast Damage by 50 points.");
		maxKi = new JRadioButton("Increase Maximum Ki by 1 bar.");
		maxStamina = new JRadioButton("Increase Maximum Stamina by 1 bar.");

		maxHealth.setBounds(350, 250, 600, 100);
		maxHealth.setBackground(Color.WHITE);
		maxHealth.setFont(maxHealth.getFont().deriveFont(23f));
		physicalDamage.setBounds(350, 350, 600, 100);
		physicalDamage.setBackground(Color.WHITE);
		physicalDamage.setFont(physicalDamage.getFont().deriveFont(23f));
		blastDamage.setBounds(350, 450, 600, 100);
		blastDamage.setBackground(Color.WHITE);
		blastDamage.setFont(blastDamage.getFont().deriveFont(23f));
		maxKi.setBounds(350, 550, 600, 100);
		maxKi.setBackground(Color.WHITE);
		maxKi.setFont(maxKi.getFont().deriveFont(23f));
		maxStamina.setBounds(350, 650, 600, 100);
		maxStamina.setBackground(Color.WHITE);
		maxStamina.setFont(maxStamina.getFont().deriveFont(23f));

		doneUpgrading = new JButton();
		doneUpgrading.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		doneUpgrading.setText("Finish");
		doneUpgrading.setBounds(750, 800, 200, 100);
		doneUpgrading.setBackground(Color.WHITE);
		doneUpgrading.setOpaque(false);
		doneUpgrading.setFocusPainted(false);

		backToWorld = new JButton();
		backToWorld.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		backToWorld.setBackground(Color.WHITE);
		backToWorld.setBounds(980, 800, 200, 100);
		backToWorld.setText("Back");

		backToWorld1 = new JButton();
		backToWorld1.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		backToWorld1.setBackground(Color.WHITE);
		backToWorld1.setText("Back");
		backToWorld1.setOpaque(false);
		backToWorld1.setFocusPainted(false);
		backToWorld1.setBounds(1285, 625, 225, 72);
		assignAttackPanel.add(backToWorld1);

		upgradeFighterPanel.add(doneUpgrading);
		upgradeFighterPanel.add(backToWorld);

		dragonPanel = new JPanelWithBackground("dragon.jpg");
		dragonPanel.setLayout(null);
		wishes = new JComboBox();
		wishes.setFont(wishes.getFont().deriveFont(Font.BOLD, 20f));
		wishes.setBounds(1350, 400, 250, 30);
		wishes.setOpaque(false);

		doneWishing = new JButton();
		doneWishing.setOpaque(false);
		doneWishing.setFont(doneWishing.getFont().deriveFont(Font.BOLD, 20f));
		doneWishing.setText("Finish");
		doneWishing.setBounds(1350, 460, 150, 50);
		dragonPanel.add(wishes);
		dragonPanel.add(doneWishing);

		battlePanel = new JPanelWithBackground("battle.jpg");
		battlePanel.setLayout(null);
		me = new JLabel();
		me.setBounds(600, 550, 100, 170);
		me.setIcon(new ImageIcon(((new ImageIcon("goku.gif").getImage()
				.getScaledInstance(me.getWidth(), me.getHeight(),
						java.awt.Image.SCALE_SMOOTH)))));
		foe = new JLabel();
		foe.setBounds(1150, 500, 250, 250);
		foe.setIcon(new ImageIcon(("goro.gif")));
		// JLabel ref = new JLabel();
		// ref.setBounds(875,550,312,312);
		// ref.setIcon(new ImageIcon("referee.gif"));
		battlePanel.add(me);
		battlePanel.add(foe);

		battleActions = new JPanel();
		battleActions.setLayout(null);
		battleActions.setBackground(Color.BLACK);
		battleActions.setPreferredSize(new Dimension(1928, 200));
		battleActions.setVisible(false);
		add(battleActions, BorderLayout.SOUTH);

		attack = new JButton();
		attack.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		attack.setForeground(Color.WHITE);
		attack.setBackground(Color.BLACK);
		attack.setText("Attack");
		attack.setBounds(300, 40, 200, 100);
		attack.setOpaque(false);
		attack.setFocusPainted(false);
		battleActions.add(attack);

		block = new JButton();
		block.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		block.setForeground(Color.WHITE);
		block.setBackground(Color.BLACK);
		block.setText("Block");
		block.setBounds(900, 40, 200, 100);
		block.setOpaque(false);
		block.setFocusPainted(false);
		battleActions.add(block);

		use = new JButton();
		use.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		use.setForeground(Color.WHITE);
		use.setBackground(Color.BLACK);
		use.setText("Use");
		use.setBounds(1500, 40, 200, 100);
		use.setOpaque(false);
		use.setFocusPainted(false);
		battleActions.add(use);

		physicalAttack = new JButton();
		physicalAttack.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		physicalAttack.setForeground(Color.WHITE);
		physicalAttack.setBackground(Color.BLACK);
		physicalAttack.setText("Physical Attack");
		physicalAttack.setBounds(100, 40, 350, 100);
		physicalAttack.setOpaque(false);
		physicalAttack.setFocusPainted(false);
		battleActions.add(physicalAttack);
		physicalAttack.setVisible(false);

		superAtt = new JButton();
		superAtt.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		superAtt.setForeground(Color.WHITE);
		superAtt.setBackground(Color.BLACK);
		superAtt.setText("Super Attack");
		superAtt.setBounds(500, 40, 350, 100);
		superAtt.setOpaque(false);
		superAtt.setFocusPainted(false);
		battleActions.add(superAtt);
		superAtt.setVisible(false);

		ultimateAtt = new JButton();
		ultimateAtt.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		ultimateAtt.setForeground(Color.WHITE);
		ultimateAtt.setBackground(Color.BLACK);
		ultimateAtt.setText("Ultimate Attack");
		ultimateAtt.setBounds(900, 40, 350, 100);
		ultimateAtt.setOpaque(false);
		ultimateAtt.setFocusPainted(false);
		battleActions.add(ultimateAtt);
		ultimateAtt.setVisible(false);

		backToActions = new JButton();
		backToActions.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		backToActions.setForeground(Color.WHITE);
		backToActions.setBackground(Color.BLACK);
		backToActions.setText("Back");
		backToActions.setBounds(1300, 40, 350, 100);
		backToActions.setOpaque(false);
		backToActions.setFocusPainted(false);
		battleActions.add(backToActions);
		backToActions.setVisible(false);

		superAttacks = new JComboBox();
		ultimateAttacks = new JComboBox();
		superAttacks.setBounds(300, 60, 350, 50);
		ultimateAttacks.setBounds(300, 60, 350, 50);
		superAttacks.setVisible(false);
		ultimateAttacks.setVisible(false);
		battleActions.add(superAttacks);
		battleActions.add(ultimateAttacks);

		doAttack = new JButton();
		doAttack.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		doAttack.setText("Attack");
		doAttack.setForeground(Color.WHITE);
		doAttack.setBackground(Color.BLACK);
		doAttack.setBounds(750, 50, 350, 100);
		battleActions.add(doAttack);
		doAttack.setVisible(false);
		doAttack.setOpaque(false);
		doAttack.setFocusPainted(false);

		backToActions1 = new JButton();
		backToActions1 = new JButton();
		backToActions1.setFont(new Font("Saiyan Sans", Font.PLAIN, 48));
		backToActions1.setForeground(Color.WHITE);
		backToActions1.setBackground(Color.BLACK);
		backToActions1.setText("Back");
		backToActions1.setBounds(1200, 50, 350, 100);
		backToActions1.setOpaque(false);
		backToActions1.setFocusPainted(false);
		battleActions.add(backToActions1);
		backToActions1.setVisible(false);

		ButtonGroup group = new ButtonGroup();
		group.add(maxHealth);
		group.add(physicalDamage);
		group.add(blastDamage);
		group.add(maxKi);
		group.add(maxStamina);

		btns.add(createNewFighter);
		btns.add(switchFighter);
		btns.add(assignAttack);
		btns.add(upgradeFighter);
		btns.add(save);
		btns.add(doneAssigning);
		btns.add(doneUpgrading);
		btns.add(backToWorld);
		btns.add(backToWorld1);
		btns.add(doneWishing);
		btns.add(attack);
		btns.add(block);
		btns.add(use);
		btns.add(physicalAttack);
		btns.add(superAtt);
		btns.add(ultimateAtt);
		btns.add(backToActions);
		btns.add(doAttack);
		btns.add(backToActions1);

		stats.add(playerName);
		stats.add(fighterName);
		stats.add(fighterLevel);
		stats.add(dragonBalls);
		stats.add(senzuBeans);
		stats.add(dragonBallstext);
		stats.add(senzuBeanstext);
		stats.add(createNewFighter);
		stats.add(switchFighter);
		stats.add(assignAttack);
		stats.add(upgradeFighter);
		stats.add(save);

		fightersPanel = new JPanelWithBackground("switching.jpg");
		fightersPanel.setLayout(new FlowLayout());

		fightersBtns = new ArrayList<JButton>();

		panels = new JPanel(new CardLayout());
		panels.add(menu, "menu");
		panels.add(newPlayer, "newPlayer");
		panels.add(chooseFighter, "chooseFighter");
		panels.add(worldView, "worldView");
		panels.add(fightersPanel, "fightersPanel");
		panels.add(assignAttackPanel, "assignAttackPanel");
		panels.add(upgradeFighterPanel, "upgradeFighterPanel");
		panels.add(dragonPanel, "dragonPanel");
		panels.add(battlePanel, "battlePanel");
		add(panels);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JFrame frame = (JFrame) e.getSource();

				int result = JOptionPane.showConfirmDialog(frame,
						"Are you sure you want to exit the game?",
						"Exit", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION)
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		setVisible(true);
	}

	public JButton getDoneWishing() {
		return doneWishing;
	}

	public JPanel getFightersPanel() {
		return fightersPanel;
	}

	public JButton getAssignAttack() {
		return assignAttack;
	}

	public JButton getUpgradeFighter() {
		return upgradeFighter;
	}

	public JButton getSave() {
		return save;
	}

	public JButton getChosePlayer() {
		return chosePlayer;
	}

	public JTextField getEnterName() {
		return enterName;
	}

	public JPanelWithBackground getNewPlayer() {
		return newPlayer;
	}

	public ArrayList<JButton> getBtns() {
		return btns;
	}

	public JPanel getChooseFighter() {
		return chooseFighter;
	}

	public JButton getFrieza() {
		return frieza;
	}

	public JButton getSaiyan() {
		return saiyan;
	}

	public JPanel getStats() {
		return stats;
	}

	public JPanelWithBackground getWorldView() {
		return worldView;
	}

	public JButton[][] getMapView() {
		return mapView;
	}

	public JButton getMajin() {
		return majin;
	}

	public JButton getEarthling() {
		return earthling;
	}

	public JButton getNamekian() {
		return namekian;
	}

	public JPanelWithBackground getMenu() {
		return menu;
	}

	public JButton getUp() {
		return up;
	}

	public JButton getDown() {
		return down;
	}

	public JButton getRight() {
		return right;
	}

	public JButton getLeft() {
		return left;
	}

	public JLabel getPlayerName() {
		return playerName;
	}

	public JButton getCreateNewFighter() {
		return createNewFighter;
	}

	public JButton getSwitchFighter() {
		return switchFighter;
	}

	public JPanelWithBackground getAssignAttackPanel() {
		return assignAttackPanel;
	}

	public JButton getBackToWorld1() {
		return backToWorld1;
	}

	public JButton getDoAttack() {
		return doAttack;
	}

	public JButton getBackToActions1() {
		return backToActions1;
	}

	public JButton getBackToActions() {
		return backToActions;
	}

	public JButton getPhysicalAttack() {
		return physicalAttack;
	}

	public JButton getSuperAtt() {
		return superAtt;
	}

	public JButton getUltimateAtt() {
		return ultimateAtt;
	}

	public JButton getDoneUpgrading() {
		return doneUpgrading;
	}

	public JComboBox getWishes() {
		return wishes;
	}

	public JButton getBackToWorld() {
		return backToWorld;
	}

	public ArrayList<JButton> getFightersBtns() {
		return fightersBtns;
	}

	public JPanel getUpgradeFighterPanel() {
		return upgradeFighterPanel;
	}

	public JPanelWithBackground getDragonPanel() {
		return dragonPanel;
	}

	public JRadioButton getMaxHealth() {
		return maxHealth;
	}

	public JRadioButton getPhysicalDamage() {
		return physicalDamage;
	}

	public JRadioButton getBlastDamage() {
		return blastDamage;
	}

	public JRadioButton getMaxKi() {
		return maxKi;
	}

	public JRadioButton getMaxStamina() {
		return maxStamina;
	}

	public JLabel getCurrentSuperAttacks() {
		return currentSuperAttacks;
	}

	public JLabel getCurrentUltimateAttacks() {
		return currentUltimateAttacks;
	}

	public JButton getDoneAssigning() {
		return doneAssigning;
	}

	public JLabel getFighterName() {
		return fighterName;
	}

	public JComboBox getNewattacks() {
		return newattacks;
	}

	public JComboBox getOldattacks() {
		return oldattacks;
	}

	public JLabel getFighterLevel() {
		return fighterLevel;
	}

	public JPanelWithBackground getBattlePanel() {
		return battlePanel;
	}

	public JButton getAttack() {
		return attack;
	}

	public JButton getBlock() {
		return block;
	}

	public JButton getUse() {
		return use;
	}

	public JLabel getFoe() {
		return foe;
	}

	public JLabel getMe() {
		return me;
	}

	public JLabel getDragonBallstext() {
		return dragonBallstext;
	}

	public JLabel getDragonBalls() {
		return dragonBalls;
	}

	public JLabel getSenzuBeans() {
		return senzuBeans;
	}

	public JLabel getSenzuBeanstext() {
		return senzuBeanstext;
	}

	public JComboBox getSuperAttacks() {
		return superAttacks;
	}

	public JComboBox getUltimateAttacks() {
		return ultimateAttacks;
	}

	public JPanel getBattleActions() {
		return battleActions;
	}

	public JButton getNewGame() {
		return newGame;
	}

	public JButton getLoadGame() {
		return loadGame;
	}

	public JPanel getPanels() {
		return panels;
	}

	public static void main(String[] args) throws IOException {
		new DragonballView();
	}

}