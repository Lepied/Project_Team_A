package test;

public class Oh extends Piece{
	public Oh(TetrisData data)
	{
		super(data);
		c[0] = 0;   r[0] = 0;
		c[1] = 1;	r[1] = 0;
		c[2] = 0;	r[2] = 1;
		c[3] = 1;	r[3] = 1;
	}
	public int getType()
	{
		return 4;
	}
	public int roteType()
	{
		return 1;
	}

}