package test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TetrisCanvas extends JPanel implements Runnable,KeyListener{
	protected Thread worker;
	protected Color colors[];
	protected int w=25;
	protected TetrisData data;
	protected int margin =20;
	protected boolean stop, makeNew;
	protected Piece current;
	protected int interval = 1000;
	protected int level =1;
	public int score = 0;
	
	private Piece currentPiece;
	private Piece nextPiece;

	public TetrisCanvas()
	{
		data = new TetrisData();
		addKeyListener(this);
		colors = new Color[8]; // 테트리스 배경 및 조각 색
		colors[0] = new Color(80,80,80); //  배경색(회색)
		colors[1] = new Color(255,0,0); //  빨간색
		colors[2] = new Color(0,255,0); //  녹색
		colors[3] = new Color(0,200,255); //  노란색
		colors[4] = new Color(255,255,0); //  하늘색
		colors[5] = new Color(255,150,0); //  황토색
		colors[6] = new Color(210,0,240); //  보라색
		colors[7] = new Color(40,0,240); //  파란색
		
	}
	
	public synchronized int getScore()
	{
		return score;
	}

	public void start()
	{
		data.clear();
		worker = new Thread(this);
		worker.start();
		makeNew = true;
		stop = false;
		requestFocus();
		repaint();
		
	}
	public void stop()
	{
		stop = true;
		current = null;
	}
	
	
	public void paint(Graphics g)
	{
		super.paint(g);
		//쌓인 조각들 그리기
		for(int i = 0; i<TetrisData.ROW; i ++)
		{
			for(int k = 0; k<TetrisData.COL; k++)
			{
				if(data.getAt(i, k)== 0)
				{
					g.setColor(colors[data.getAt(i,k)]);
					g.draw3DRect(margin/2+w*k, margin/2+w*i, w, w, true);
				}
				else
				{
					g.setColor(colors[data.getAt(i,k)]);
					g.fill3DRect(margin/2+w*k, margin/2+w*i, w, w, true);
				}
			}
		}
		//현재 내려오고있는 테트리스 조각 그리기
		if(current !=null)
		{
			for(int i =0; i<4; i++)
			{
				g.setColor(colors[current.getType()]);
				g.fill3DRect(margin/2+w*(current.getX()+current.c[i]), margin/2+w*(current.getY()+current.r[i])
						, w, w, true);
			}
		}
		if(nextPiece !=null)
		{
			for(int i =0; i<4; i++)
			{
				g.setColor(colors[nextPiece.getType()]);
				g.fill3DRect(400/2+w*(nextPiece.getX()+nextPiece.c[i]), 150/2+w*(nextPiece.getY()+nextPiece.r[i])
						, w, w, true);
			}
		}
		drawScoreAndLevel(g);

	}
	public Dimension getPreferredSize() //테트리스 판의 크기 지정
	{
		int tw= w*TetrisData.COL + margin;
		int th= w*TetrisData.ROW + margin;
		return new Dimension(tw,th);
	}
	
	private void generateNextPiece() {  //다음 피스를 생성하는 함수
        if (nextPiece == null) {
            // 다음 피스 생성
            int random = (int) (Math.random() * Integer.MAX_VALUE) % 7;
            switch (random) {
                case 0:
                    nextPiece = new Bar(data);
                    break;
                case 1:
                    nextPiece = new Tee(data);
                    break;
                case 2:
                    nextPiece = new El(data);
                    break;
                case 3:
                    nextPiece = new Oh(data);
                    break;
                case 4:
                    nextPiece = new Er(data);
                    break;
                case 5:
                    nextPiece = new Kl(data);
                    break;
                case 6:
                    nextPiece = new Kr(data);
                    break;
                default:
                    if (random % 2 == 0)
                        nextPiece = new Tee(data);
                    else
                        nextPiece = new El(data);
            }
          }
        current = nextPiece;
       	nextPiece = null;
        if (nextPiece == null) {
            // 다음 피스 생성
            int random = (int) (Math.random() * Integer.MAX_VALUE) % 7;
            switch (random) {
                case 0:
                    nextPiece = new Bar(data);
                    break;
                case 1:
                    nextPiece = new Tee(data);
                    break;
                case 2:
                    nextPiece = new El(data);
                    break;
                case 3:
                    nextPiece = new Oh(data);
                    break;
                case 4:
                    nextPiece = new Er(data);
                    break;
                case 5:
                    nextPiece = new Kl(data);
                    break;
                case 6:
                    nextPiece = new Kr(data);
                    break;
                default:
                    if (random % 2 == 0)
                        nextPiece = new Tee(data);
                    else
                        nextPiece = new El(data);
            }
          }
      }
    
   
	public void run()
	{
		while(!stop)	
		{	
			
			data.removeLines(); //라인제거를 상시로 실행(여러줄 동시에 완성되면 한번에 제거)
			score = data.getLine()*175 *level;
			try
			{
				if(makeNew) //새로운 조각 만들기 
				{
					generateNextPiece();
					makeNew = false;
				}
				else  // 현재 만들어진 테트리스 조각 아래로 이동
				{
					if(current.moveDown())
					{
						makeNew =true;
						if(current.copy())
						{
							stop();
							JOptionPane.showMessageDialog(this, "게임끝\n점수:" + score);
						}
						current = null;
					}
					data.removeLines();
					score = data.getLine()*175 *level;
					
				}
				repaint();
				Thread.currentThread().sleep(interval/level);
			}
			catch(Exception e) 
			{
				e.printStackTrace(); //예외발생 경로 추적
			}
		
		}
	}
	private void drawPiece(Graphics g, Piece piece, int x, int y) { // UI에 피스그리기
			if(nextPiece !=null)
			{
				for(int i =0; i<4; i++)
				{
					g.setColor(colors[nextPiece.getType()]);
					g.fill3DRect(margin/2+w*(nextPiece.getX()+nextPiece.c[i]), margin/2+w*(nextPiece.getY()+nextPiece.r[i])
							, w, w, true);
				}
			}
	    }
	public Piece getNextPiece() { //다음블록 가져오기
		    return nextPiece;
		}
	
	public void paintNextPiece(Graphics g) {
		//다음 피스 그리기 
	    Piece nextPiece = getNextPiece();
	    drawPiece(g, nextPiece, 500, 800);
	}
	
	private void drawScoreAndLevel(Graphics g) {
        // 점수와 레벨 그리기 작업 수행
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, getWidth() - 100, getHeight() - 50);
        g.drawString("Level: " + level, getWidth() - 100, getHeight() - 30);
    }
    
	public void keyPressed(KeyEvent e)
	{
		if(current == null) return;
		
		switch(e.getKeyCode())
		{
			case 37: //왼쪽 화살표
			  current.moveLeft();
			  repaint();
			  break;
			case 39: //오른쪽 화살표
				current.moveRight();
				repaint();
				break;
			case 38: //윗쪽 화살표
				current.rotate();
				repaint();
				break;
			case 40: //아랫쪽 화살표
				boolean temp = current.moveDown();
				if(temp)
				{
					makeNew = true;
					if(current.copy())
					{
						stop();
						JOptionPane.showMessageDialog(this, "게임끝\n점수 : " + score);
					}
					
				}
				data.removeLines();
				score =data.getLine()*175 *level;
				
				repaint();
				break;
			case 32:
				// 블록 하드 드롭
			    while (!current.moveDown()) {
			        // 더 이상 아래로 내려갈 수 없을 때까지 블록을 계속해서 내리기
			    }
			    makeNew = true;
			    if (current.copy()) {
			        stop();
			        JOptionPane.showMessageDialog(this, "게임 끝\n점수: " + score);
			    }
			    data.removeLines();
			    score = data.getLine()*175 *level;
			    repaint();
			    break;
				

		}
	}
	

	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
