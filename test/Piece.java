package test;

import java.awt.Point;

public abstract class Piece {
	final int DOWN = 0; //방향지정
	final int LEFT = 1;
	final int RIGHT = 2;
	
	protected int r[]; //Y축 좌표 배ㅕㅇㄹ
	protected int c[];  //X축 좌표 배열
	protected TetrisData data; // 테트리스 내부 데이터
	protected Point center; //조각의 중심 좌표
	
	public Piece(TetrisData data)
	{
		r = new int[4];
		c = new int[4];
		this.data = data;
		center = new Point(5,0);
	}
	public abstract int getType();
	public abstract int roteType();
	
	public int getX() {return center.x;}
	public int getY() {return center.y;}
	
	public boolean copy()
	{
		//값 복사
		boolean value = false;
		int x = getX();
		int y = getY();
		if(getMinY()+ y<=0)//게임종료상황
		{
			value = true;
		}
		for(int i=0; i<4; i++)
		{
			data.setAt(y + r[i],x + c[i],getType());
		}
		return value;
	}
	public boolean isOverlap(int dir) //다른 조각과 겹치는지 파악
	{
		int x = getX();
		int y = getY();
		
		switch(dir)
		{
		case 0: //아래
			for(int i=0; i<r.length; i++)
			{
				if(data.getAt(y+r[i]+1,x+c[i])!=0)
				{
					return true;
				}
			}
			break;
		case 1: //왼쪽
			for(int i=0; i<r.length; i++)
			{
				if(data.getAt(y+r[i],x+c[i]-1)!=0)
				{
					return true;
				}
			}
			break;
		
		case 2: //오른쪽
			for(int i=0;i<r.length; i++)
			{
				if(data.getAt(y+r[i],x+c[i]+1)!=0)
				{
					return true;
				}
			}
			break;
		}
		return false;
	}
	public int getMinX()
	{
		int min = c[0];
		for(int i =1; i<c.length; i++)
		{
			if(c[i] < min)
			{
				min = c[i];
			}
		}
		return min;
	}
	public int getMaxX()
	{
		int max = c[0];
		for(int i=1; i<c.length; i++)
		{
			if(c[i] > max)
			{
				max = c[i];
			}
		}
		return max;
	}
	public int getMinY()
	{
		int min = r[0];
		for(int i=1; i<r.length; i++)
		{
			if(r[i] < min)
			{
				min = r[i];
			}
		}
		return min;
	}
	public int getMaxY()
	{
		int max = r[0];
		for(int i=1;i<r.length; i++)
		{
			if(r[i]>max)
			{
				max = r[i];
			}
		}
		return max;
	}
	public boolean moveDown() //아래로 이동
	{
		if(center.y + getMaxY()+1 < TetrisData.ROW)
		{
			if(isOverlap(DOWN) != true)
			{
				center.y++;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return true;
		}
		return false;
	}
	public void moveLeft() //왼쪽으로 이동
	{
		if(center.x + getMinX()-1 >= 0)
			if(isOverlap(LEFT) != true) {center.x--;}
			else return;
	}
	public void moveRight() //오른쪽으로 이동
	{
		if(center.x + getMaxX()+1 <TetrisData.COL)
			if(isOverlap(RIGHT) != true) {center.x++;}
			else return;
	}
	public void resetPosition()
	{
		if(center.y + getMaxY()+1 < TetrisData.ROW)
		{
			center.y = data.data[0][4];
			center.x = 4;
		}
	}
	public void rotate()//조각회전
	{
		int rc = roteType();
		if(rc <=1) return;
		
		if(rc ==2)
		{
			rotate4();
			rotate4();
			rotate4();
		} else {
			rotate4();
		}
	}
	public void rotate4() // 조각 회전
	{
		int[] newC = new int[4];
	    int[] newR = new int[4];
	    
		for(int i =0; i<4; i++)
		{
		     int temp = c[i];
		     newC[i] = -r[i];
		     newR[i] = temp;
		}
		for(int i=0 ; i<4; i++)
		{
			int newX = getX() + newC[i];
			int newY = getY() + newR[i];
			
			//회전한 블록이 공간 밖으로 나가거나 다른 블럭과 겹치는경우, 블록이 회전하지 않게한다.
			if(newX<0 || newX>= TetrisData.COL ||  
			   newY < 0 || newY >= TetrisData.ROW ||
			   data.getAt(newY, newX) != 0)
			{
				//블럭이 화면밖으로 나가거나 다른 블럭과 겹치는 경우를 각 경우별로나눠서
				//다르게 처리할 수 있음.
				//ex)블럭위치를 살짝 변경
				return;
			}
		}
		for(int i=0;i<4; i++)
		{
			c[i] = newC[i];
			r[i] = newR[i];
		}
	}
	
}
