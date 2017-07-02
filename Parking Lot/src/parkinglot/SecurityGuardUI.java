package parkinglot;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SecurityGuardUI extends JFrame{
	
	private Main main;
	
	private JPanel MainJPanel;//���
	
	private ImageIcon MainBG;//���ñ���
	
	private JLabel mainbackground;//���ñ����ı�ǩ
	
	private ConnectToAccess access;
	private ResultSet rs=null;
	
	private int xframeold;
	private int yframeold;
	
	private Transfer transfer;
	
	private AddStyle ADS;//��������Զ��尴ť
	
	private JLabel LabelStall;
	
	private int garage;
	
	private JLabel LabelTime;
	private ShowTime showtime;//������ʾʱ����߳�
	//��ť��
	private JLabel LabelIn;
	private JLabel LabelOut;
	private JLabel LabelDailyExcel;
	private JLabel LabelLogOff;
	
	private JButton ButtonIn;
	private JButton ButtonOut;
	private JButton ButtonDailyExcel;
	private JButton ButtonLogOff;
	
	private ImageIcon ImageIn;
	private ImageIcon ImageOut;
	private ImageIcon ImageDailyExcel;
	private ImageIcon ImageLogOff;
	
	public SecurityGuardUI(ConnectToAccess Access,Transfer atransfer,int Garage,Main MAIN) throws Exception {
		//===============================================================һϵ�г�ʼ��
		access=Access;//�������ݿ�����
		garage=Garage;//����
		setUndecorated(true);//ȥ�߿�
		setResizable(false);//���ò��ܵ�����С
		setBounds(400, 150, 450, 300);
		this.setSize(1000, 800);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ADS=new AddStyle();//������Ӱ�ť�ӿ�
		transfer=atransfer;//ֵ����
		
		main=MAIN;//main�������̴߳���
		
		//�������
		MainJPanel=(JPanel)getContentPane();
		
		//���ر���ͼƬ
		MainBG=new ImageIcon("UI/SecurityGuardUI.jpg");
		
		//���ر���ͼƬ�ı�ǩ
		mainbackground=new JLabel(MainBG);
		
		//===============================================================���ñ���ͼƬ��ԭ��
		MainPane();//���������
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
			  SecurityGuardUI.this.setLocation(xframenew, yframenew);
			  }
			 });
		}
	void MainPane() throws Exception
	{
		mainbackground.setBounds(0, 0, MainBG.getIconWidth(),MainBG.getIconHeight());
		
		MainJPanel.setOpaque(false);
		MainJPanel.setLayout(null);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(mainbackground, new Integer(Integer.MIN_VALUE));
		
		ADS.AddBorderButton(MainJPanel, SecurityGuardUI.this, access,transfer);//��ӱ߿�ť
		//------------------------------------------------------------------���Ԫ��
//ʱ���ǩ
		LabelTime=new JLabel();
		LabelTime.setFont(new Font("������ͤ��ϸ�ڼ���",Font.PLAIN,50));
		LabelTime.setBounds(10, 10, 300, 50);
		//MainJPanel.add(LabelTime);
		ShowTime();
//ע����ť
		ADS.AddMyButton(MainJPanel, "UI/LogOff.png", 905, 750);
		ImageLogOff=ADS.getimageicon();
		LabelLogOff=ADS.getLabel();
		ButtonLogOff=ADS.getbutton();
		ButtonLogOff.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageLogOff.setImage(ImageLogOff.getImage().getScaledInstance(ImageLogOff.getIconWidth()-2,ImageLogOff.getIconHeight()-3 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelLogOff.setIcon(ImageLogOff);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageLogOff.setImage(ImageLogOff.getImage().getScaledInstance(ImageLogOff.getIconWidth()+2,ImageLogOff.getIconHeight()+3 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelLogOff.setIcon(ImageLogOff);
				  try {
						access.executeUpdate("UPDATE PeopleManagement SET IsOnline = false WHERE UserName=+'"+transfer.getUserName()+"'");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//�ر����ݿ�
					catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//���û�Ϊ����״̬
				  main.Notify();
				  SecurityGuardUI.this.dispose();
			  }
			});
//���г�λ��ʾ��ǩ
		LabelStall=new JLabel();
		LabelStall.setFont(new Font("������ͤ��ϸ�ڼ���",Font.PLAIN,120));
		LabelStall.setBounds(425, 280, 300, 300);
		MainJPanel.add(LabelStall);
//��ⰴť
		ADS.AddMyButton(MainJPanel, "UI/In.png", 130, 650);
		LabelIn=ADS.getLabel();
		ImageIn=ADS.getimageicon();
		ButtonIn=ADS.getbutton();
		ButtonIn.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageIn.setImage(ImageIn.getImage().getScaledInstance(ImageIn.getIconWidth()-2,ImageIn.getIconHeight()-2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelIn.setIcon(ImageIn);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageIn.setImage(ImageIn.getImage().getScaledInstance(ImageIn.getIconWidth()+2,ImageIn.getIconHeight()+2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelIn.setIcon(ImageIn);
				  try {
					In in=new In(access,SecurityGuardUI.this,garage);
					in.setVisible(true);
					SecurityGuardUI.this.setEnabled(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
		});
//���ⰴť
		ADS.AddMyButton(MainJPanel, "UI/Out.png", 430, 650);
		LabelOut=ADS.getLabel();
		ImageOut=ADS.getimageicon();
		ButtonOut=ADS.getbutton();
		ButtonOut.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageOut.setImage(ImageOut.getImage().getScaledInstance(ImageOut.getIconWidth()-2,ImageOut.getIconHeight()-2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelOut.setIcon(ImageOut);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageOut.setImage(ImageOut.getImage().getScaledInstance(ImageOut.getIconWidth()+2,ImageOut.getIconHeight()+2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelOut.setIcon(ImageOut);
				  try {
						Out out=new Out(access,SecurityGuardUI.this,garage);
						out.setVisible(true);
						SecurityGuardUI.this.setEnabled(false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			  }
		});
//�ձ���
		ADS.AddMyButton(MainJPanel, "UI/DailyExcel.png", 730, 650);
		LabelDailyExcel=ADS.getLabel();
		ImageDailyExcel=ADS.getimageicon();
		ButtonDailyExcel=ADS.getbutton();
		ButtonDailyExcel.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageDailyExcel.setImage(ImageDailyExcel.getImage().getScaledInstance(ImageDailyExcel.getIconWidth()-2,ImageDailyExcel.getIconHeight()-2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelDailyExcel.setIcon(ImageDailyExcel);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageDailyExcel.setImage(ImageDailyExcel.getImage().getScaledInstance(ImageDailyExcel.getIconWidth()+2,ImageDailyExcel.getIconHeight()+2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelDailyExcel.setIcon(ImageDailyExcel);
				  try {
					DayExcel de=new DayExcel(access,SecurityGuardUI.this,garage);
					de.setVisible(true);
					SecurityGuardUI.this.setEnabled(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
		});
		Update();//��ʾ����ͣ����Ϣ
	}
	public void Update() throws Exception
	{
		int recordAVStall=0;//��¼���г�λ����
		rs=access.executeQuery("SELECT * FROM Carport WHERE Garage='"+garage+"'");
		for(recordAVStall=0;rs.next();)
		{
			if(rs.getBoolean("IsAvaliable")==true&&rs.getBoolean("IsRent")==false)//û��ͣ���Ҳ������⳵λ
			{
				recordAVStall++;
			}
		}
		if(recordAVStall==0)//�������
		{
			LabelIn.setVisible(false);
			ButtonIn.setVisible(false);
		}
		else
		{
			LabelIn.setVisible(true);
			ButtonIn.setVisible(true);
		}
		LabelStall.setText(String.valueOf(recordAVStall));
	}
	public void ShowTime()//��ʾ��ǰʱ��
	{
		//��ʾʱ����߳�
		showtime=new ShowTime(MainJPanel,LabelTime);
		new Thread(showtime).start();
	}
}
