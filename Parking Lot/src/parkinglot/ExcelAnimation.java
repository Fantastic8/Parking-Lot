package parkinglot;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ExcelAnimation implements Runnable{
	private int x;
	private int yfinal;
	private int yzero=688;
	private Graphics g;
	private JLabel LabelPoint;
	public ExcelAnimation(JPanel MainJPanel,JLabel LabelPoint,int x,int y)
	{
		this.x=x;
		yfinal=y;
		this.LabelPoint=LabelPoint;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=yzero;i>=yfinal;i-=3)
		{
			LabelPoint.setLocation(x, i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
