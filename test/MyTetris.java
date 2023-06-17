package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import java.awt.FlowLayout;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.sql.*;

public class MyTetris extends JFrame {

	private JPanel contentPane;
	private TetrisCanvas tetrisCanvas;
    private SearchDB searchDB;

    private JMenuItem mntmNewMenuItem;
    private JPanel clientCanvasPanel;
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyTetris frame = new MyTetris();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public MyTetris() {
		setTitle("Tetris");
		searchDB = new SearchDB("root", "rkarbfgid819");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 470, 600);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("\uAC8C\uC784");
		menuBar.add(mnNewMenu);
		
		SoundPlayer soundPlayer = new SoundPlayer();
		soundPlayer.playBackgroundMusic("01-Tetris-Title.wav");
		
		mntmNewMenuItem = new JMenuItem("\uC2DC\uC791");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				mntmNewMenuItem.setEnabled(false);
				tetrisCanvas.start();
				soundPlayer.stopBackgroundMusic();
				soundPlayer.playBackgroundMusic("TetrisBGM");
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\uC885\uB8CC");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("기록");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("점수");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try {
					searchDB.getAll();
					JOptionPane.showMessageDialog(null,searchDB.getResult(),"점수 순위",1);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		
		clientCanvasPanel = new JPanel();
		contentPane.add(clientCanvasPanel,BorderLayout.EAST);
		


		
		tetrisCanvas = new TetrisCanvas();
		contentPane.add(tetrisCanvas, BorderLayout.CENTER);
		tetrisCanvas.setLayout(null);
		
		JPanel nextBlock = new JPanel();
		nextBlock.setPreferredSize(new Dimension(100, 100));
		nextBlock.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(255, 255, 255), new Color(160, 160, 160)), "NEXT", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		nextBlock.setBackground(SystemColor.menu);
		nextBlock.setBounds(290, 20, 120, 120);
		tetrisCanvas.add(nextBlock);
		
		JPanel Hold = new JPanel();
		Hold.setPreferredSize(new Dimension(100, 100));
		Hold.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(255, 255, 255), new Color(160, 160, 160)), "HOLD", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		Hold.setBackground(SystemColor.menu);
		Hold.setBounds(290, 150, 120, 120);
		tetrisCanvas.add(Hold);
		
	}


	protected TetrisCanvas getTetrisCanvas() {
		return tetrisCanvas;
	}
	public JPanel getNextBlock() {
		return getNextBlock();
	}
	public JPanel getTemp() {
		return getTemp();
	}
	public JMenuItem getMntmNewMenuItem() {
		return mntmNewMenuItem;
	}
}
