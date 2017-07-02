package parkinglot;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CarAnimation implements Runnable {
	
	private AdministratorUI au;
	private int randomimageicon;
	private int randomsleep;
	private int randomshowup;
	CarAnimation(AdministratorUI au)
	{
		this.au=au;
		randomsleep=(int)(Math.random()*10)+7;
		randomshowup=(int)(Math.random()*10000);
		randomimageicon=(int)(Math.random()*3);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ImageIcon car1;
		switch(randomimageicon)
		{
		case 0:car1=new ImageIcon("UI/001.png");break;
		case 1:car1=new ImageIcon("UI/003.png");break;
		case 2:car1=new ImageIcon("UI/017.png");break;
		default:car1=new ImageIcon("UI/001.png");break;
		}
		JLabel car1Label=new JLabel(car1);
		try {
			Thread.sleep(randomshowup);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		car1Label.setBounds(-30, 765, car1.getIconWidth(), car1.getIconHeight());
		au.getMainJPanel().add(car1Label);
		for(int x=-30;x<1000;x++)
		{
			car1Label.setLocation(x, 765);
			try {
				Thread.sleep(randomsleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//ÖØÖÃ
		randomsleep=(int)(Math.random()*10)+7;
		randomshowup=(int)(Math.random()*10000);
		randomimageicon=(int)(Math.random()*5);
		this.run();
	}

}
