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
	
	private JPanel MainJPanel;//面板
	
	private ImageIcon MainBG;//设置背景
	
	private JLabel mainbackground;//设置背景的标签
	
	private ConnectToAccess access;
	private ResultSet rs=null;
	
	private int xframeold;
	private int yframeold;
	
	private Transfer transfer;
	
	private AddStyle ADS;//快速添加自定义按钮
	
	private JLabel LabelStall;
	
	private int garage;
	
	private JLabel LabelTime;
	private ShowTime showtime;//用于显示时间的线程
	//按钮类
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
		//===============================================================一系列初始化
		access=Access;//设置数据库连接
		garage=Garage;//车库
		setUndecorated(true);//去边框
		setResizable(false);//设置不能调整大小
		setBounds(400, 150, 450, 300);
		this.setSize(1000, 800);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ADS=new AddStyle();//快速添加按钮接口
		transfer=atransfer;//值传递
		
		main=MAIN;//main函数的线程处理
		
		//面板设置
		MainJPanel=(JPanel)getContentPane();
		
		//加载背景图片
		MainBG=new ImageIcon("UI/SecurityGuardUI.jpg");
		
		//加载背景图片的标签
		mainbackground=new JLabel(MainBG);
		
		//===============================================================设置背景图片和原件
		MainPane();//加载主面板
		//===============================================================实现拖拽效果
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
		
		ADS.AddBorderButton(MainJPanel, SecurityGuardUI.this, access,transfer);//添加边框按钮
		//------------------------------------------------------------------添加元件
//时间标签
		LabelTime=new JLabel();
		LabelTime.setFont(new Font("方正兰亭超细黑简体",Font.PLAIN,50));
		LabelTime.setBounds(10, 10, 300, 50);
		//MainJPanel.add(LabelTime);
		ShowTime();
//注销按钮
		ADS.AddMyButton(MainJPanel, "UI/LogOff.png", 905, 750);
		ImageLogOff=ADS.getimageicon();
		LabelLogOff=ADS.getLabel();
		ButtonLogOff=ADS.getbutton();
		ButtonLogOff.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageLogOff.setImage(ImageLogOff.getImage().getScaledInstance(ImageLogOff.getIconWidth()-2,ImageLogOff.getIconHeight()-3 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelLogOff.setIcon(ImageLogOff);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageLogOff.setImage(ImageLogOff.getImage().getScaledInstance(ImageLogOff.getIconWidth()+2,ImageLogOff.getIconHeight()+3 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelLogOff.setIcon(ImageLogOff);
				  try {
						access.executeUpdate("UPDATE PeopleManagement SET IsOnline = false WHERE UserName=+'"+transfer.getUserName()+"'");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//关闭数据库
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
					//该用户为下线状态
				  main.Notify();
				  SecurityGuardUI.this.dispose();
			  }
			});
//空闲车位显示标签
		LabelStall=new JLabel();
		LabelStall.setFont(new Font("方正兰亭超细黑简体",Font.PLAIN,120));
		LabelStall.setBounds(425, 280, 300, 300);
		MainJPanel.add(LabelStall);
//入库按钮
		ADS.AddMyButton(MainJPanel, "UI/In.png", 130, 650);
		LabelIn=ADS.getLabel();
		ImageIn=ADS.getimageicon();
		ButtonIn=ADS.getbutton();
		ButtonIn.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageIn.setImage(ImageIn.getImage().getScaledInstance(ImageIn.getIconWidth()-2,ImageIn.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelIn.setIcon(ImageIn);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageIn.setImage(ImageIn.getImage().getScaledInstance(ImageIn.getIconWidth()+2,ImageIn.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
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
//出库按钮
		ADS.AddMyButton(MainJPanel, "UI/Out.png", 430, 650);
		LabelOut=ADS.getLabel();
		ImageOut=ADS.getimageicon();
		ButtonOut=ADS.getbutton();
		ButtonOut.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageOut.setImage(ImageOut.getImage().getScaledInstance(ImageOut.getIconWidth()-2,ImageOut.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelOut.setIcon(ImageOut);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageOut.setImage(ImageOut.getImage().getScaledInstance(ImageOut.getIconWidth()+2,ImageOut.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
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
//日报表
		ADS.AddMyButton(MainJPanel, "UI/DailyExcel.png", 730, 650);
		LabelDailyExcel=ADS.getLabel();
		ImageDailyExcel=ADS.getimageicon();
		ButtonDailyExcel=ADS.getbutton();
		ButtonDailyExcel.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageDailyExcel.setImage(ImageDailyExcel.getImage().getScaledInstance(ImageDailyExcel.getIconWidth()-2,ImageDailyExcel.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelDailyExcel.setIcon(ImageDailyExcel);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageDailyExcel.setImage(ImageDailyExcel.getImage().getScaledInstance(ImageDailyExcel.getIconWidth()+2,ImageDailyExcel.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
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
		Update();//显示空闲停车信息
	}
	public void Update() throws Exception
	{
		int recordAVStall=0;//记录空闲车位数量
		rs=access.executeQuery("SELECT * FROM Carport WHERE Garage='"+garage+"'");
		for(recordAVStall=0;rs.next();)
		{
			if(rs.getBoolean("IsAvaliable")==true&&rs.getBoolean("IsRent")==false)//没有停车且不是月租车位
			{
				recordAVStall++;
			}
		}
		if(recordAVStall==0)//不能入库
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
	public void ShowTime()//显示当前时间
	{
		//显示时间的线程
		showtime=new ShowTime(MainJPanel,LabelTime);
		new Thread(showtime).start();
	}
}
