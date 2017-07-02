package parkinglot;

import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowTime implements Runnable{
	private JPanel MainJPanel;
	private JLabel LabelTime;
	public ShowTime(JPanel MainJPanel,JLabel LabelTime)
	{
		this.MainJPanel=MainJPanel;
		this.LabelTime=LabelTime;
		MainJPanel.add(LabelTime);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			Time time=new Time();
			LabelTime.setText(time.getCurrentTime());
		}
	}
}
