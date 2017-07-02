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
import javax.swing.JPanel;

public class DayExcel extends JFrame {
	private JPanel MainJPanel;
	private ImageIcon MainBG;
	private JLabel mainbackground;
	
	private int xframeold;
	private int yframeold;
	
	private AddStyle ADS=new AddStyle();//快捷添加界面
	
	private int Garage;
	
	private Time time;//时间
	private int Year;
	private int Month;
	private int Day;
	private JLabel LabelIn;//显示总入库标签
	private JLabel LabelOut;//显示总出库标签
	private JLabel LabelCharge;//显示收费标签
	
	//按钮
	private JLabel LabelBack;
	
	private ImageIcon ImageBack;
	
	private JButton ButtonBack;
	
	private ConnectToAccess access;
	private ResultSet rs=null;
	
	public DayExcel(ConnectToAccess Access,final SecurityGuardUI sg,int garage) throws Exception {
		//===============================================================一系列初始化
		access=Access;//设置数据库连接
		setUndecorated(true);//去边框
		setResizable(false);//设置不能调整大小
		setBounds(520, 150, 600, 800);
		this.setSize(800, 800);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Garage=garage;//车库
		
		//面板设置
		MainJPanel=(JPanel)getContentPane();
		
		//加载背景图片
		MainBG=new ImageIcon("UI/DayExcel.jpg");
		
		//加载背景图片的标签
		mainbackground=new JLabel(MainBG);
		
		//时间初始化
		time=new Time();
		Year=time.getYear();
		Month=time.getMonth();
		Day=time.getDay();
		
		//===============================================================设置背景图片和原件
		mainbackground.setBounds(0, 0, MainBG.getIconWidth(),MainBG.getIconHeight());
		
		MainJPanel.setOpaque(false);
		MainJPanel.setLayout(null);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(mainbackground, new Integer(Integer.MIN_VALUE));
		
		//--------------边框元件
		JButton ButtonExit = new JButton();//关闭按钮
		ButtonExit.setContentAreaFilled(false);
		ButtonExit.setBorderPainted(false);
		ButtonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sg.setEnabled(true);//可以操作ui界面
				DayExcel.this.dispose();
			}
		});
		ButtonExit.setBounds(768, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//最小化按钮
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DayExcel.this.setExtendedState(ICONIFIED);//设置最小化
			}
		});
		ButtonMinimize.setBounds(736, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		//===============================================================元件
//总入库显示标签
		LabelIn=new JLabel();
		LabelIn.setBounds(380, 200, 100, 50);
		LabelIn.setFont(new Font("方正兰亭超细黑简体",0,50));
		MainJPanel.add(LabelIn);
//总出库显示标签
		LabelOut=new JLabel();
		LabelOut.setBounds(380, 360, 100, 50);
		LabelOut.setFont(new Font("方正兰亭超细黑简体",0,50));
		MainJPanel.add(LabelOut);
//总收费显示标签
		LabelCharge=new JLabel();
		LabelCharge.setBounds(380, 530, 100, 50);
		LabelCharge.setFont(new Font("方正兰亭超细黑简体",0,50));
		MainJPanel.add(LabelCharge);		
		UpdateData();
		

//返回按钮
		ADS.AddMyButton(MainJPanel, "UI/Back.png", 700, 750);
		LabelBack=ADS.getLabel();
		ImageBack=ADS.getimageicon();
		ButtonBack=ADS.getbutton();
		ButtonBack.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageBack.setImage(ImageBack.getImage().getScaledInstance(ImageBack.getIconWidth()-2,ImageBack.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelBack.setIcon(ImageBack);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageBack.setImage(ImageBack.getImage().getScaledInstance(ImageBack.getIconWidth()+2,ImageBack.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelBack.setIcon(ImageBack);
				  sg.setEnabled(true);//可以操作ui界面
				  DayExcel.this.dispose();
			  }
			});
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
					  DayExcel.this.setLocation(xframenew, yframenew);
					  }
					 });
	}
	void UpdateData() throws Exception//更新显示面板中的数据,year年 month月 garage车库
	{
		int in=0;
		int out=0;
		int charge=0;
		rs=access.executeQuery("Select * From Schedule");
		while(rs.next())
		{
			if((Year==time.getYearFromString(rs.getString("Time"))&&(Month==time.getMonthFromString(rs.getString("Time")))&&(Garage==rs.getInt("Garage"))&&(Day==time.getDayFromString(rs.getString("Time")))))//符合year和month和garage
			{
				if(rs.getBoolean("IsIn"))//总入库
				{
					in++;
				}
				else if(rs.getInt("Status")==0)//总出库
				{
					out++;
					charge+=rs.getInt("Charge");
				}
			}
		}
		LabelIn.setText(String.valueOf(in));
		LabelOut.setText(String.valueOf(out));
		LabelCharge.setText(String.valueOf(charge));
	}
}
