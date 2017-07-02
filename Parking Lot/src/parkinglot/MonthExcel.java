package parkinglot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MonthExcel extends JFrame {
	private JPanel MainJPanel;
	private ImageIcon MainBG;
	private JLabel mainbackground;
	
	private int xframeold;
	private int yframeold;
	
	private AddStyle ADS=new AddStyle();//�����ӽ���
	
	private Time time;//ʱ��
	private int Year;//��ʾ��������
	private int Month;//��ʾ��������
	private int Garage;//��ʾ�����ĳ���
	private int MinYear;//��С���
	private int MinMonth;//��С�·�
	private JLabel LabelYear;//�����ʾ��ǩ
	private JLabel LabelMonth;//�·���ʾ��ǩ
	private JLabel LabelGarage;//������ʾ��ǩ
	private JLabel LabelIn;//��ʾ������ǩ
	private JLabel LabelOut;//��ʾ�ܳ����ǩ
	private JLabel LabelCharge;//��ʾ�շѱ�ǩ
	private JLabel LabelRent;//��ʾ���⿨��ǩ
	
	//point
	private JLabel[] LabelPoint;
	private ImageIcon ImagePoint;
	
	//time����
	private int[] xco;
	private int yco=680;
//	private JLabel LabelDay1;
//	private JLabel LabelDay2;
//	private JLabel LabelDay3;
//	private JLabel LabelDay4;
	private JLabel LabelDay5;
//	private JLabel LabelDay6;
//	private JLabel LabelDay7;
//	private JLabel LabelDay8;
//	private JLabel LabelDay9;
	private JLabel LabelDay10;
//	private JLabel LabelDay11;
//	private JLabel LabelDay12;
//	private JLabel LabelDay13;
//	private JLabel LabelDay14;
	private JLabel LabelDay15;
//	private JLabel LabelDay16;
//	private JLabel LabelDay17;
//	private JLabel LabelDay18;
//	private JLabel LabelDay19;
	private JLabel LabelDay20;
//	private JLabel LabelDay21;
//	private JLabel LabelDay22;
//	private JLabel LabelDay23;
//	private JLabel LabelDay24;
	private JLabel LabelDay25;
//	private JLabel LabelDay26;
//	private JLabel LabelDay27;
//	private JLabel LabelDay28;
//	private JLabel LabelDay29;
	private JLabel LabelDay30;
//	private JLabel LabelDay31;
	
	//��������
	private JLabel LabelChar100;
	private JLabel LabelChar200;
	private JLabel LabelChar300;
	private JLabel LabelChar400;
	private JLabel LabelChar500;
	
	public int y=680;
	
	//��ť
	private JLabel LabelBack;
	private JLabel LabelYearAdd;
	private JLabel LabelYearDecrease;
	private JLabel LabelMonthAdd;
	private JLabel LabelMonthDecrease;
	
	private ImageIcon ImageBack;
	private ImageIcon ImageYearAdd;
	private ImageIcon ImageYearDecrease;
	private ImageIcon ImageMonthAdd;
	private ImageIcon ImageMonthDecrease;
	
	private JButton ButtonBack;
	private JButton ButtonYearAdd;
	private JButton ButtonYearDecrease;
	private JButton ButtonMonthAdd;
	private JButton ButtonMonthDecrease;
	private JButton ButtonNext;
	private JButton ButtonLast;
	
	private ConnectToAccess access;
	private ResultSet rs=null;
	
	public MonthExcel(ConnectToAccess Access,final AdministratorUI au,int garage) throws Exception {
		//===============================================================һϵ�г�ʼ��
		access=Access;//�������ݿ�����
		setUndecorated(true);//ȥ�߿�
		setResizable(false);//���ò��ܵ�����С
		setBounds(520, 150, 600, 800);
		this.setSize(800, 800);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Garage=garage;//����
		
		//�������
		MainJPanel=(JPanel)getContentPane();
		
		//���ر���ͼƬ
		MainBG=new ImageIcon("UI/MonthExcel.jpg");
		
		//���ر���ͼƬ�ı�ǩ
		mainbackground=new JLabel(MainBG);
		
		
		//����λ�ó�ʼ��
		xco=new int[32];
		for(int i=1;i<32;i++)
		{
			xco[i]=137+18*(i-1);
		}
		//potin��ʼ��
		ImagePoint=new ImageIcon("UI/Point.png");//���ͼƬ
		LabelPoint=new JLabel[32];
		for(int i=1;i<32;i++)
		{
			LabelPoint[i]=new JLabel(ImagePoint);
			LabelPoint[i].setBounds(xco[i]+7, 688, ImagePoint.getIconWidth(), ImagePoint.getIconHeight());
			MainJPanel.add(LabelPoint[i]);
		}
		//ʱ��
		time=new Time();
		Year=time.getYear();
		Month=time.getMonth();
		
		//������С��ݺ���С�·�
		rs=access.executeQuery("Select * From Schedule WHERE Garage='"+Garage+"'");
		
		rs.next();//������
		MinYear=time.getYearFromString(rs.getString("Time"));
		MinMonth=time.getMonthFromString(rs.getString("Time"));
		//===============================================================���ñ���ͼƬ��ԭ��
		mainbackground.setBounds(0, 0, MainBG.getIconWidth(),MainBG.getIconHeight());
		
		MainJPanel.setOpaque(false);
		MainJPanel.setLayout(null);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(mainbackground, new Integer(Integer.MIN_VALUE));
		
		//--------------�߿�Ԫ��
		JButton ButtonExit = new JButton();//�رհ�ť
		ButtonExit.setContentAreaFilled(false);
		ButtonExit.setBorderPainted(false);
		ButtonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				au.setEnabled(true);//���Բ���ui����
				MonthExcel.this.dispose();
			}
		});
		ButtonExit.setBounds(2, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//��С����ť
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MonthExcel.this.setExtendedState(ICONIFIED);//������С��
			}
		});
		ButtonMinimize.setBounds(34, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		//===============================================================Ԫ��
//time����
		//5
		LabelDay5=new JLabel("5");
		LabelDay5.setBounds(xco[5], yco, 50, 50);
		LabelDay5.setFont(new Font("������ͤ��ϸ�ڼ���",0,20));
		MainJPanel.add(LabelDay5);
		//10
		LabelDay10=new JLabel("10");
		LabelDay10.setBounds(xco[10], yco, 50, 50);
		LabelDay10.setFont(new Font("������ͤ��ϸ�ڼ���",0,20));
		MainJPanel.add(LabelDay10);
		//15
		LabelDay15=new JLabel("15");
		LabelDay15.setBounds(xco[15], yco, 50, 50);
		LabelDay15.setFont(new Font("������ͤ��ϸ�ڼ���",0,20));
		MainJPanel.add(LabelDay15);
		//20
		LabelDay20=new JLabel("20");
		LabelDay20.setBounds(xco[20], yco, 50, 50);
		LabelDay20.setFont(new Font("������ͤ��ϸ�ڼ���",0,20));
		MainJPanel.add(LabelDay20);
		//25
		LabelDay25=new JLabel("25");
		LabelDay25.setBounds(xco[25], yco, 50, 50);
		LabelDay25.setFont(new Font("������ͤ��ϸ�ڼ���",0,20));
		MainJPanel.add(LabelDay25);
		//30
		LabelDay30=new JLabel("30");
		LabelDay30.setBounds(xco[30], yco, 50, 50);
		LabelDay30.setFont(new Font("������ͤ��ϸ�ڼ���",0,20));
		MainJPanel.add(LabelDay30);
//��������
		//100
		LabelChar100=new JLabel("100");
		LabelChar100.setBounds(80, 636, 50, 50);
		LabelChar100.setFont(new Font("������ͤ��ϸ�ڼ���",0,20));
		MainJPanel.add(LabelChar100);
		//200
		LabelChar200=new JLabel("200");
		LabelChar200.setBounds(80, 592, 50, 50);
		LabelChar200.setFont(new Font("������ͤ��ϸ�ڼ���",0,20));
		MainJPanel.add(LabelChar200);
		//300
		LabelChar300=new JLabel("300");
		LabelChar300.setBounds(80, 548, 50, 50);
		LabelChar300.setFont(new Font("������ͤ��ϸ�ڼ���",0,20));
		MainJPanel.add(LabelChar300);
		//400
		LabelChar400=new JLabel("400");
		LabelChar400.setBounds(80, 504, 50, 50);
		LabelChar400.setFont(new Font("������ͤ��ϸ�ڼ���",0,20));
		MainJPanel.add(LabelChar400);
		//500
		LabelChar500=new JLabel("500");
		LabelChar500.setBounds(80, 460, 50, 50);
		LabelChar500.setFont(new Font("������ͤ��ϸ�ڼ���",0,20));
		MainJPanel.add(LabelChar500);
//�������ʾ��ǩ
		LabelIn=new JLabel();
		LabelIn.setBounds(380, 100, 100, 40);
		LabelIn.setFont(new Font("������ͤ��ϸ�ڼ���",0,45));
		MainJPanel.add(LabelIn);
//�ܳ�����ʾ��ǩ
		LabelOut=new JLabel();
		LabelOut.setBounds(380, 180, 100, 40);
		LabelOut.setFont(new Font("������ͤ��ϸ�ڼ���",0,45));
		MainJPanel.add(LabelOut);
//���շ���ʾ��ǩ
		LabelCharge=new JLabel();
		LabelCharge.setBounds(380, 260, 100, 40);
		LabelCharge.setFont(new Font("������ͤ��ϸ�ڼ���",0,45));
		MainJPanel.add(LabelCharge);
//���⿨��ʾ��ǩ
		LabelRent=new JLabel();
		LabelRent.setBounds(380, 340, 100, 40);
		LabelRent.setFont(new Font("������ͤ��ϸ�ڼ���",0,45));
		MainJPanel.add(LabelRent);
		
//������ʾ��ǩ
		LabelGarage=new JLabel();
		LabelGarage.setBounds(10, 745, 150, 50);
		LabelGarage.setFont(new Font("������ͤ��ϸ�ڼ���",0,40));
		setGarage(Garage);
		MainJPanel.add(LabelGarage);
		
		UpdateData();
		
//�����ʾ��ǩ
		LabelYear=new JLabel();
		LabelYear.setBounds(670, 7, 150, 50);
		LabelYear.setFont(new Font("������ͤ��ϸ�ڼ���",0,45));
		LabelYear.setForeground(Color.white);//���ð�ɫ����
		LabelYear.setText(String.valueOf(Year));//����Ĭ�����-�������
		MainJPanel.add(LabelYear);
//�·���ʾ��ǩ
		LabelMonth=new JLabel();
		LabelMonth.setBounds(733, 73, 100, 50);
		LabelMonth.setFont(new Font("������ͤ��ϸ�ڼ���",0,40));
		LabelMonth.setForeground(Color.white);//���ð�ɫ����
		LabelMonth.setText(String.valueOf(Month));//����Ĭ���·�-�����·�
		MainJPanel.add(LabelMonth);
		
//��һ����
		ADS.AddMyButton(MainJPanel, "UI/Next.png", 60, 720);
		ButtonNext=ADS.getbutton();
		ButtonNext.addMouseListener(new MouseAdapter() {
			  public void mouseReleased(MouseEvent e)
			  {
				  try {
					if(setGarage(++Garage)==false)
					{
						setGarage(--Garage);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
//��һ����
		ADS.AddMyButton(MainJPanel, "UI/Last.png", 40, 720);
		ButtonLast=ADS.getbutton();
		ButtonLast.addMouseListener(new MouseAdapter() {
			  public void mouseReleased(MouseEvent e)
			  {
				  try {
					  if(setGarage(--Garage)==false)
						{
							setGarage(++Garage);
						}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
		
//������Ӱ�ť
		ADS.AddMyButton(MainJPanel, "UI/+.png", 650, 15);
		LabelYearAdd=ADS.getLabel();
		ImageYearAdd=ADS.getimageicon();
		ButtonYearAdd=ADS.getbutton();
		ButtonYearAdd.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageYearAdd.setImage(ImageYearAdd.getImage().getScaledInstance(ImageYearAdd.getIconWidth()-2,ImageYearAdd.getIconHeight()-2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelYearAdd.setIcon(ImageYearAdd);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageYearAdd.setImage(ImageYearAdd.getImage().getScaledInstance(ImageYearAdd.getIconWidth()+2,ImageYearAdd.getIconHeight()+2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelYearAdd.setIcon(ImageYearAdd);
				  if(Year+1>time.getYear())
				  {
					  return;
				  }
				  try {
					setDate(++Year,Month);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
//��ݼ��ٰ�ť
		ADS.AddMyButton(MainJPanel, "UI/-.png", 650, 35);
		LabelYearDecrease=ADS.getLabel();
		ImageYearDecrease=ADS.getimageicon();
		ButtonYearDecrease=ADS.getbutton();
		ButtonYearDecrease.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageYearDecrease.setImage(ImageYearDecrease.getImage().getScaledInstance(ImageYearDecrease.getIconWidth()-2,ImageYearDecrease.getIconHeight()-2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelYearDecrease.setIcon(ImageYearDecrease);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageYearDecrease.setImage(ImageYearDecrease.getImage().getScaledInstance(ImageYearDecrease.getIconWidth()+2,ImageYearDecrease.getIconHeight()+2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelYearDecrease.setIcon(ImageYearDecrease);
				  try {
					  if(Year-1<MinYear)
					  {
						  return;
					  }
					setDate(--Year,Month);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
//�·����Ӱ�ť
		ADS.AddMyButton(MainJPanel, "UI/+.png", 720, 83);
		LabelMonthAdd=ADS.getLabel();
		ImageMonthAdd=ADS.getimageicon();
		ButtonMonthAdd=ADS.getbutton();
		ButtonMonthAdd.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageMonthAdd.setImage(ImageMonthAdd.getImage().getScaledInstance(ImageMonthAdd.getIconWidth()-2,ImageMonthAdd.getIconHeight()-2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelMonthAdd.setIcon(ImageMonthAdd);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageMonthAdd.setImage(ImageMonthAdd.getImage().getScaledInstance(ImageMonthAdd.getIconWidth()+2,ImageMonthAdd.getIconHeight()+2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelMonthAdd.setIcon(ImageMonthAdd);
				  if(Year==time.getYear())
				  {
					  if(Month==time.getMonth())
					  {
						  return;
					  }
				  }
				  ++Month;
				  if(Month>12)
				  {
					  ++Year;
					  Month=1;
				  }
				  try {
					setDate(Year,Month);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
//�·ݼ��ٰ�ť
		ADS.AddMyButton(MainJPanel, "UI/-.png", 720, 103);
		LabelMonthDecrease=ADS.getLabel();
		ImageMonthDecrease=ADS.getimageicon();
		ButtonMonthDecrease=ADS.getbutton();
		ButtonMonthDecrease.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageMonthDecrease.setImage(ImageMonthDecrease.getImage().getScaledInstance(ImageMonthDecrease.getIconWidth()-2,ImageMonthDecrease.getIconHeight()-2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelMonthDecrease.setIcon(ImageMonthDecrease);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageMonthDecrease.setImage(ImageMonthDecrease.getImage().getScaledInstance(ImageMonthDecrease.getIconWidth()+2,ImageMonthDecrease.getIconHeight()+2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelMonthDecrease.setIcon(ImageMonthDecrease);
				  if(Year==MinYear&&Month-1<MinMonth)
				  {
					  return ;
				  }
				  --Month;
				  if(Month<1)
				  {
					  Month=12;
					  Year--;
				  }
				  try {
					setDate(Year,Month);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
//���ذ�ť
		ADS.AddMyButton(MainJPanel, "UI/Back.png", 700, 750);
		LabelBack=ADS.getLabel();
		ImageBack=ADS.getimageicon();
		ButtonBack=ADS.getbutton();
		ButtonBack.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageBack.setImage(ImageBack.getImage().getScaledInstance(ImageBack.getIconWidth()-2,ImageBack.getIconHeight()-2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelBack.setIcon(ImageBack);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageBack.setImage(ImageBack.getImage().getScaledInstance(ImageBack.getIconWidth()+2,ImageBack.getIconHeight()+2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelBack.setIcon(ImageBack);
				  au.setEnabled(true);//���Բ���ui����
				  MonthExcel.this.dispose();
			  }
			});
		//===============================================================ʵ����קЧ��
				this.addMouseListener(new MouseAdapter() 
				{
				  @Override
				  public void mousePressed(MouseEvent e) {
				  xframeold = e.getX();
				  yframeold = e.getY();
				  }
				 });
				this.addMouseMotionListener(new MouseMotionAdapter() {
					  public void mouseDragged(MouseEvent e) {
					  int xOnScreen = e.getXOnScreen();
					  int yOnScreen = e.getYOnScreen();
					  int xframenew = xOnScreen - xframeold;
					  int yframenew = yOnScreen - yframeold;
					  MonthExcel.this.setLocation(xframenew, yframenew);
					  }
					 });
	}
	void UpdateData() throws Exception//������ʾ����е�����,year�� month�� garage����
	{
		int in=0;
		int out=0;
		int charge=0;
		int rent=0;
		rs=access.executeQuery("Select * From Schedule");
		while(rs.next())
		{
			if((Year==time.getYearFromString(rs.getString("Time"))&&(Month==time.getMonthFromString(rs.getString("Time")))&&(Garage==rs.getInt("Garage"))))//����year��month��garage
			{
				if(rs.getBoolean("IsIn"))//�����
				{
					in++;
				}
				else if(rs.getInt("Status")==0)//�ܳ���
				{
					out++;
				}
				charge+=rs.getInt("Charge");
				if(rs.getInt("Status")==1)
				{
					rent++;
				}
			}
		}
		LabelIn.setText(String.valueOf(in));
		LabelOut.setText(String.valueOf(out));
		LabelCharge.setText(String.valueOf(charge));
		LabelRent.setText(String.valueOf(rent));
		Excel();
	}
	void setDate(int year,int month) throws Exception
	{
		Year=year;
		Month=month;
		LabelYear.setText(String.valueOf(Year));
		LabelMonth.setText(String.valueOf(Month));
		UpdateData();//����
}
	boolean setGarage(int gar) throws Exception
	{
		Garage=gar;
		rs=access.executeQuery("Select * From Configuration WHERE Garage='"+Garage+"'");
		if(rs.next()==false)//�޴˳�λ
		{
			LabelGarage.setVisible(false);
			UpdateData();//����
			return false;
		}
		else
		{
			LabelGarage.setVisible(true);
		}
		switch(Garage)
		{
		case 1:LabelGarage.setText("����һ");break;
		case 2:LabelGarage.setText("�����");break;
		case 3:LabelGarage.setText("������");break;
		case 4:LabelGarage.setText("������");break;
		case 5:LabelGarage.setText("������");break;
		case 6:LabelGarage.setText("������");break;
		default:LabelGarage.setText("");break;
		}
		UpdateData();//����
		return true;
	}
	void Excel() throws Exception
	{
		int[] day=new int[32];//ÿ����շ�
		String t;
		for(int i=1;i<32;i++)
		{
			day[i]=0;
		}
		rs=access.executeQuery("Select * From Schedule WHERE Garage='"+Garage+"'");
		while(rs.next())
		{
			t=rs.getString("Time");
			if(time.getYearFromString(t)==Year&&time.getMonthFromString(t)==Month)
			{
				day[time.getDayFromString(t)]+=rs.getInt("Charge");
			}
		}
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=0;i<300;i++)
				{
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					repaint();
				}
			}}).start();
		for(int i=1;i<32;i++)
		{
			new Thread(new ExcelAnimation(MainJPanel,LabelPoint[i],xco[i]+7,(int)(688-(11*day[i])/25))).start();
		}
	}
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		float lineWidth = 1.7f;
	    ((Graphics2D)g).setStroke(new BasicStroke(lineWidth));
		for(int i=1;i<31;i++)
		{
			g.drawLine(LabelPoint[i].getX()+2,LabelPoint[i].getY()+2 , LabelPoint[i+1].getX()+2, LabelPoint[i+1].getY()+2);
		}
	}
}
