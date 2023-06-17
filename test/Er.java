package test;

public class Er extends Piece{
	public Er(TetrisData data)
	{
		super(data);
		c[0] = 0;   r[0] = 0;
		c[1] = 0;	r[1] = -1;
		c[2] = 1;	r[2] = -1;
		c[3] = 0;	r[3] = 1;
	}
	public int getType()
	{
		return 5;
	}
	public int roteType()
	{
		return 4;
	}

}
