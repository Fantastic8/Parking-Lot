package parkinglot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class AdministratorUI extends JFrame {

	private Main main;//用于对main的线程处理
	
	private JPanel MainJPanel;//2个面板
	private JPanel SetJPanel;
	
	private ImageIcon MainBG;//设置2个背景
	private ImageIcon SetBG;
	
	private JLabel mainbackground;//设置2个背景的标签
	private JLabel setbackground;
	
	private boolean setswitch=false;//表示还没有被初始化
	//private boolean monthlyrentswich=false;//表示还没有被初始化
	
	private ConnectToAccess access;
	private ResultSet rs=null;
	
	private int xframeold;
	private int yframeold;
	
	private int lotx[];//六个parkinglot的位置
	private int loty[];
	
	private boolean SetParkingManagementstatus=false;
	
	private Transfer transfer;
	
	private int garage=0;//存储添加车库按钮的车库下标
	private int showgar=1;//表示当前mainjpanel上面显示的车库下标
	private AddStyle ADS;//快速添加自定义按钮
	//--------------------------------------按钮集合
	//ImageIcon
	private ImageIcon ImageSet;
	private ImageIcon ImageMonthlyRentExcel;
	private ImageIcon ImageParkingManagement;
	private ImageIcon ImageBack;
	private ImageIcon ImageUserManagement;
	private ImageIcon ImageMonthlyRent;
	private ImageIcon ImageAddLot;
	private ImageIcon ImageLot1;
	private ImageIcon ImageLot2;
	private ImageIcon ImageLot3;
	private ImageIcon ImageLot4;
	private ImageIcon ImageLot5;
	private ImageIcon ImageLot6;
	private ImageIcon ImageLogOff;
	
	//JLabel
	private JLabel LabelSet;
	private JLabel LabelMonthlyRentExcel;
	private JLabel LabelParkingManagement;
	private JLabel LabelBack;
	private JLabel LabelUserManagement;
	private JLabel LabelMonthlyRent;
	private JLabel LabelAddLot;
	private JLabel LabelLot1;
	private JLabel LabelLot2;
	private JLabel LabelLot3;
	private JLabel LabelLot4;
	private JLabel LabelLot5;
	private JLabel LabelLot6;
	private JLabel LabelLogOff;
	//--非按钮类
	private JLabel LabelShowGarage;
	private JLabel LabelShowReStall;
	private JLabel LabelShowTUStall;
	private JLabel LabelShowTAStall;
	
	//JButton
	private JButton ButtonSet;
	private JButton ButtonMonthlyRentExcel;
	private JButton ButtonParkingManagement;
	private JButton ButtonBack;
	private JButton ButtonUserManagement;
	private JButton ButtonMonthlyRent;
	private JButton ButtonAddLot;
	private JButton ButtonLot1;
	private JButton ButtonLot2;
	private JButton ButtonLot3;
	private JButton ButtonLot4;
	private JButton ButtonLot5;
	private JButton ButtonLot6;
	private JButton ButtonNext;
	private JButton ButtonLast;
	private JButton ButtonLogOff;
	
	

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public AdministratorUI(ConnectToAccess Access,Transfer atransfer,Main MAIN) throws Exception  {
		lotx=new int[6];
		loty=new int[6];
		
		lotx[0]=150;
		lotx[1]=405;
		lotx[2]=660;
		lotx[3]=150;
		lotx[4]=405;
		lotx[5]=660;
		
		loty[0]=290;
		loty[1]=290;
		loty[2]=290;
		loty[3]=535;
		loty[4]=535;
		loty[5]=535;
		//===============================================================一系列初始化
		access=Access;//设置数据库连接
		setUndecorated(true);//去边框
		setResizable(false);//设置不能调整大小
		setBounds(400, 150, 450, 300);
		this.setSize(1000, 800);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ADS=new AddStyle();//快速添加按钮接口
		transfer=atransfer;//值传递
		
		main=MAIN;//main函数的线程
		
		//面板设置
		MainJPanel=(JPanel)getContentPane();
		SetJPanel=new JPanel();//新建设置面板
		//MonthlyRentJPanel=new JPanel();//新建月租管理面板
		
		//加载背景图片
		MainBG=new ImageIcon("UI/AdministratorUI.jpg");
		SetBG=new ImageIcon("UI/ADSetiUI.jpg");
		
		//加载背景图片的标签
		mainbackground=new JLabel(MainBG);
		setbackground=new JLabel(SetBG);
		//MonthlyRentBG=new ImageIcon();
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
			  AdministratorUI.this.setLocation(xframenew, yframenew);
			  }
			 });
	}

	public void Animation() throws InterruptedException
	{
		//================================================================动画效果--添加5个小车
		CarAnimation car1=new CarAnimation(this);
		CarAnimation car2=new CarAnimation(this);
		CarAnimation car3=new CarAnimation(this);
		CarAnimation car4=new CarAnimation(this);
		CarAnimation car5=new CarAnimation(this);
		new Thread(car1).start();
		new Thread(car2).start();
		new Thread(car3).start();
		new Thread(car4).start();
		new Thread(car5).start();
	}
	
	public boolean ShowInfo(int gar) throws Exception
	{
		rs=access.executeQuery("Select * From Carport WHERE Garage="+gar);//读取结果集
		//int gar;//车库标签
		int restall=0;
		int tustall=0;
		int tastall=0;
		if(rs.next()==false)//车库中无任何车位信息
		{
			LabelShowGarage.setText(null);
			LabelShowReStall.setText(null);
			LabelShowTUStall.setText(null);
			LabelShowTAStall.setText(null);
			return false;//啥子都不显示
		}
		else
		{
			//gar=rs.getInt("Garage");
			while(true)
			{
				if(rs.getBoolean("IsRent")==true)//月租车位
				{
					restall++;
				}
				else if(rs.getBoolean("IsAvaliable")==true)//临时空闲车位
				{
					tastall++;
				}
				else//临时已停车位
				{
					tustall++;
				}
				if(rs.next()==false||rs.getInt("Garage")!=gar)
				{
					break;
				}
			}
			LabelShowGarage.setText(new String().valueOf(gar));//显示车库
			LabelShowReStall.setText(new String().valueOf(restall));//显示月租车位
			LabelShowTUStall.setText(new String().valueOf(tustall));//显示临时已停车位
			LabelShowTAStall.setText(new String().valueOf(tastall));//显示临时空闲车位
			return true;
		}
	}
	public void MainPane() throws Exception
	{
		mainbackground.setBounds(0, 0, MainBG.getIconWidth(),MainBG.getIconHeight());
		
		MainJPanel.setOpaque(false);
		MainJPanel.setLayout(null);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(mainbackground, new Integer(Integer.MIN_VALUE));
		
		ADS.AddBorderButton(MainJPanel, AdministratorUI.this, access,transfer);
		
		//------------------变面板
//下一车库
		ADS.AddMyButton(MainJPanel, "UI/Next.png", 260, 20);
		ButtonNext=ADS.getbutton();
		ButtonNext.addMouseListener(new MouseAdapter() {
			  public void mouseReleased(MouseEvent e)
			  {
				  try {
					if(ShowInfo(++showgar)==false)
					{
						ShowInfo(--showgar);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
//上一车库
		ADS.AddMyButton(MainJPanel, "UI/Last.png", 240, 20);
		ButtonLast=ADS.getbutton();
		ButtonLast.addMouseListener(new MouseAdapter() {
			  public void mouseReleased(MouseEvent e)
			  {
				  try {
					if(ShowInfo(--showgar)==false)
					{
						ShowInfo(++showgar);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
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
				  AdministratorUI.this.dispose();
			  }
			});
//车库标签
		LabelShowGarage=new JLabel();
		LabelShowGarage.setBounds(25, 50, 70, 70);
		LabelShowGarage.setFont(new Font("方正兰亭超细黑简体",0,45));
		LabelShowGarage.setForeground(Color.white);
		MainJPanel.add(LabelShowGarage);
//已租车位标签
		LabelShowReStall=new JLabel();
		LabelShowReStall.setBounds(530, 330, 300, 100);
		LabelShowReStall.setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 70));
		MainJPanel.add(LabelShowReStall);
//临时已停车位标签
		LabelShowTUStall=new JLabel();
		LabelShowTUStall.setBounds(530, 440, 300, 100);
		LabelShowTUStall.setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 70));
		MainJPanel.add(LabelShowTUStall);
//临时空闲车位标签
		LabelShowTAStall=new JLabel();
		LabelShowTAStall.setBounds(530, 550, 300, 100);
		LabelShowTAStall.setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 70));
		MainJPanel.add(LabelShowTAStall);
//显示
		ShowInfo(1);//显示动态信息
//oo
//设置按钮
		ADS.AddMyButton(MainJPanel, "UI/AdministratorUI-Set.jpg", 100, 700);
		ImageSet=ADS.getimageicon();
		LabelSet=ADS.getLabel();
		ButtonSet=ADS.getbutton();
		ButtonSet.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageSet.setImage(ImageSet.getImage().getScaledInstance(ImageSet.getIconWidth()-2,ImageSet.getIconHeight()-3 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelSet.setIcon(ImageSet);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageSet.setImage(ImageSet.getImage().getScaledInstance(ImageSet.getIconWidth()+2,ImageSet.getIconHeight()+3 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelSet.setIcon(ImageSet);
				  try {
					  if(!setswitch)
					  {
						  SetPane();
					  }
					  else
					  {
						  //已经初始化过只用换面板和背景
						  getLayeredPane().remove(mainbackground);
						  getLayeredPane().add(setbackground);
						  MainJPanel.setVisible(false);
						  SetJPanel.setVisible(true);
						  setContentPane(SetJPanel);
					  }
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//调用设置面板
			  }
			});
		
		
//月租管理按钮
		ADS.AddMyButton(MainJPanel, "UI/AdministratorUI-MonthlyRent.jpg", 390, 700);
		LabelMonthlyRent=ADS.getLabel();
		ImageMonthlyRent=ADS.getimageicon();
		ButtonMonthlyRent=ADS.getbutton();
		ButtonMonthlyRent.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageMonthlyRent.setImage(ImageMonthlyRent.getImage().getScaledInstance(ImageMonthlyRent.getIconWidth()-3,ImageMonthlyRent.getIconHeight()-3 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelMonthlyRent.setIcon(ImageMonthlyRent);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageMonthlyRent.setImage(ImageMonthlyRent.getImage().getScaledInstance(ImageMonthlyRent.getIconWidth()+3,ImageMonthlyRent.getIconHeight()+3 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelMonthlyRent.setIcon(ImageMonthlyRent);
				  try {
					RentManage rm=new RentManage(access,AdministratorUI.this);
					rm.setVisible(true);
					AdministratorUI.this.setEnabled(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
		
//月报表按钮
		ADS.AddMyButton(MainJPanel, "UI/AdministratorUI-MonthlyRentExcel.jpg", 750, 700);
		ImageMonthlyRentExcel=ADS.getimageicon();
		LabelMonthlyRentExcel=ADS.getLabel();
		ButtonMonthlyRentExcel=ADS.getbutton();
		ButtonMonthlyRentExcel.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageMonthlyRentExcel.setImage(ImageMonthlyRentExcel.getImage().getScaledInstance(ImageMonthlyRentExcel.getIconWidth()-2,ImageMonthlyRentExcel.getIconHeight()-3 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelMonthlyRentExcel.setIcon(ImageMonthlyRentExcel);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageMonthlyRentExcel.setImage(ImageMonthlyRentExcel.getImage().getScaledInstance(ImageMonthlyRentExcel.getIconWidth()+2,ImageMonthlyRentExcel.getIconHeight()+3 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelMonthlyRentExcel.setIcon(ImageMonthlyRentExcel);
				  try {
					MonthExcel me=new MonthExcel(access,AdministratorUI.this,showgar);
					me.setVisible(true);
					AdministratorUI.this.setEnabled(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
		}
	
	
	
	
	
	
	//=========================================================设置面板
	public void SetPane() throws InterruptedException
	{
		//============================================================一系列初始化
		MainJPanel.setVisible(false);//关闭当前面板
		this.getLayeredPane().remove(mainbackground);//去除背景
		SetJPanel.setLayout(null);//设置布局
		SetJPanel.setSize(1000,800);//设置大小
		setContentPane(SetJPanel);//将面板切换至set面板
		setswitch=true;//表示已经初始化过了下一次不再初始化
		//-----------------------------------------------初始化背景
		setbackground.setBounds(0, 0, SetBG.getIconWidth(),SetBG.getIconHeight());
		SetJPanel=(JPanel)getContentPane();
		SetJPanel.setOpaque(false);
		SetJPanel.setLayout(null);
		//this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(setbackground, new Integer(Integer.MIN_VALUE));
		//============================================================添加元件
		
		ADS.AddBorderButton(SetJPanel, AdministratorUI.this, access,transfer);//添加关闭和最小化
		
//返回按钮		
		ADS.AddMyButton(SetJPanel, "UI/Back.png", 905, 750);
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
				  //返回主界面
				  SetJPanel.setVisible(false);//隐藏设置面板
				  getLayeredPane().remove(setbackground);//去除背景
				  MainJPanel.setVisible(true);
				  getLayeredPane().add(mainbackground);//加载背景
				  setContentPane(MainJPanel);//将面板切换至main面板
				  try {
					ShowInfo(showgar);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//显示信息
			  }
			});
		
		
//用户管理按钮
		ADS.AddMyButton(SetJPanel, "UI/ADUserManagement.png", 400, 250);
		LabelUserManagement=ADS.getLabel();
		ButtonUserManagement=ADS.getbutton();
		ImageUserManagement=ADS.getimageicon();
		ButtonUserManagement.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageUserManagement.setImage(ImageUserManagement.getImage().getScaledInstance(ImageUserManagement.getIconWidth()-2,ImageUserManagement.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelUserManagement.setIcon(ImageUserManagement);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageUserManagement.setImage(ImageUserManagement.getImage().getScaledInstance(ImageUserManagement.getIconWidth()+2,ImageUserManagement.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelUserManagement.setIcon(ImageUserManagement);
				  try {
					  AdministratorUI.this.setEnabled(false);
					UserManage um=new UserManage(access,AdministratorUI.this);//新建用户管理窗口
					um.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
		
		
		//-------------------------------------------------------------------------车库管理
//showlot边框
		ImageIcon ImageShowLot=new ImageIcon("UI/ShowLot.png");
		final JLabel LabelShowLot=new JLabel(ImageShowLot);
		LabelShowLot.setBounds(160, 300, ImageShowLot.getIconWidth(), ImageShowLot.getIconHeight());
		SetJPanel.add(LabelShowLot);
		LabelShowLot.setVisible(false);
		
//添加车库按钮
		ADS.AddMyButton(SetJPanel, "UI/AddLot.png", 0, 0);
		LabelAddLot=ADS.getLabel();
		ButtonAddLot=ADS.getbutton();
		ImageAddLot=ADS.getimageicon();
		ButtonAddLot.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageAddLot.setImage(ImageAddLot.getImage().getScaledInstance(ImageAddLot.getIconWidth()-2,ImageAddLot.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelAddLot.setIcon(ImageAddLot);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageAddLot.setImage(ImageAddLot.getImage().getScaledInstance(ImageAddLot.getIconWidth()+2,ImageAddLot.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelAddLot.setIcon(ImageAddLot);
				  try {
					access.close();
					access.Open();
				} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  AddLot ad=new AddLot(access,AdministratorUI.this,garage);//添加
				  ad.setVisible(true);
				  AdministratorUI.this.setEnabled(false);//不可用主窗口
			  }
			});
		LabelAddLot.setVisible(false);//先隐藏
		ButtonAddLot.setVisible(false);//先隐藏
		
//车库一按钮
		ADS.AddMyButton(SetJPanel, "UI/Lot1.png", lotx[0], loty[0]);
		LabelLot1=ADS.getLabel();
		ButtonLot1=ADS.getbutton();
		ImageLot1=ADS.getimageicon();
		ButtonLot1.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageLot1.setImage(ImageLot1.getImage().getScaledInstance(ImageLot1.getIconWidth()-2,ImageLot1.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelLot1.setIcon(ImageLot1);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  try {
				    ImageLot1.setImage(ImageLot1.getImage().getScaledInstance(ImageLot1.getIconWidth()+2,ImageLot1.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
				    LabelLot1.setIcon(ImageLot1);
				    ShowLotInfo Lot1;
				    if(garage==2)
					{
						Lot1 = new ShowLotInfo(access,AdministratorUI.this,1,true);
					}
					else
					{
						Lot1 = new ShowLotInfo(access,AdministratorUI.this,1,false);
					}
					Lot1.setVisible(true);
					AdministratorUI.this.setEnabled(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
		LabelLot1.setVisible(false);//先隐藏
		ButtonLot1.setVisible(false);//先隐藏
		
//车库二按钮
		ADS.AddMyButton(SetJPanel, "UI/Lot2.png", lotx[1], loty[1]);
		LabelLot2=ADS.getLabel();
		ButtonLot2=ADS.getbutton();
		ImageLot2=ADS.getimageicon();
		ButtonLot2.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageLot2.setImage(ImageLot2.getImage().getScaledInstance(ImageLot2.getIconWidth()-2,ImageLot2.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelLot2.setIcon(ImageLot2);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageLot2.setImage(ImageLot2.getImage().getScaledInstance(ImageLot2.getIconWidth()+2,ImageLot2.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelLot2.setIcon(ImageLot2);
				  ShowLotInfo Lot2;
					try {
						if(garage==3)
						{
							Lot2 = new ShowLotInfo(access,AdministratorUI.this,2,true);
						}
						else
						{
							Lot2 = new ShowLotInfo(access,AdministratorUI.this,2,false);
						}
						Lot2.setVisible(true);
						AdministratorUI.this.setEnabled(false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
			  }
			});
		LabelLot2.setVisible(false);//先隐藏
		ButtonLot2.setVisible(false);//先隐藏
		
//车库三按钮
		ADS.AddMyButton(SetJPanel, "UI/Lot3.png", lotx[2], loty[2]);
		LabelLot3=ADS.getLabel();
		ButtonLot3=ADS.getbutton();
		ImageLot3=ADS.getimageicon();
		ButtonLot3.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageLot3.setImage(ImageLot3.getImage().getScaledInstance(ImageLot3.getIconWidth()-2,ImageLot3.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelLot3.setIcon(ImageLot3);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageLot3.setImage(ImageLot3.getImage().getScaledInstance(ImageLot3.getIconWidth()+2,ImageLot3.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelLot3.setIcon(ImageLot3);
				  ShowLotInfo Lot3;
					try {
						if(garage==4)
						{
							Lot3 = new ShowLotInfo(access,AdministratorUI.this,3,true);
						}
						else
						{
							Lot3 = new ShowLotInfo(access,AdministratorUI.this,3,false);
						}
						Lot3.setVisible(true);
						AdministratorUI.this.setEnabled(false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			  }
			});
		LabelLot3.setVisible(false);//先隐藏
		ButtonLot3.setVisible(false);//先隐藏
		
//车库四按钮
		ADS.AddMyButton(SetJPanel, "UI/Lot4.png", lotx[3], loty[3]);
		LabelLot4=ADS.getLabel();
		ButtonLot4=ADS.getbutton();
		ImageLot4=ADS.getimageicon();
		ButtonLot4.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageLot4.setImage(ImageLot4.getImage().getScaledInstance(ImageLot4.getIconWidth()-2,ImageLot4.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelLot4.setIcon(ImageLot4);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageLot4.setImage(ImageLot4.getImage().getScaledInstance(ImageLot4.getIconWidth()+2,ImageLot4.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelLot4.setIcon(ImageLot4);
				  ShowLotInfo Lot4;
					try {
						if(garage==5)
						{
							Lot4 = new ShowLotInfo(access,AdministratorUI.this,4,true);
						}
						else
						{
							Lot4 = new ShowLotInfo(access,AdministratorUI.this,4,false);
						}
						Lot4.setVisible(true);
						AdministratorUI.this.setEnabled(false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			  }
			});
		LabelLot4.setVisible(false);//先隐藏
		ButtonLot4.setVisible(false);//先隐藏
		
//车库五按钮
		ADS.AddMyButton(SetJPanel, "UI/Lot5.png", lotx[4], loty[4]);
		LabelLot5=ADS.getLabel();
		ButtonLot5=ADS.getbutton();
		ImageLot5=ADS.getimageicon();
		ButtonLot5.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageLot5.setImage(ImageLot5.getImage().getScaledInstance(ImageLot5.getIconWidth()-2,ImageLot5.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelLot5.setIcon(ImageLot5);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageLot5.setImage(ImageLot5.getImage().getScaledInstance(ImageLot5.getIconWidth()+2,ImageLot5.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelLot5.setIcon(ImageLot5);
				  ShowLotInfo Lot5;
					try {
						if(garage==6)
						{
							Lot5 = new ShowLotInfo(access,AdministratorUI.this,5,true);
						}
						else
						{
							Lot5 = new ShowLotInfo(access,AdministratorUI.this,5,false);
						}
						Lot5.setVisible(true);
						AdministratorUI.this.setEnabled(false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			  }
			});
		LabelLot5.setVisible(false);//先隐藏
		ButtonLot5.setVisible(false);//先隐藏
		
//车库六按钮
		ADS.AddMyButton(SetJPanel, "UI/Lot6.png", lotx[5], loty[5]);
		LabelLot6=ADS.getLabel();
		ButtonLot6=ADS.getbutton();
		ImageLot6=ADS.getimageicon();
		ButtonLot6.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageLot6.setImage(ImageLot6.getImage().getScaledInstance(ImageLot6.getIconWidth()-2,ImageLot6.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelLot6.setIcon(ImageLot6);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageLot6.setImage(ImageLot6.getImage().getScaledInstance(ImageLot6.getIconWidth()+2,ImageLot6.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelLot6.setIcon(ImageLot6);
				  ShowLotInfo Lot6;
					try {
						if(garage==7)
						{
							Lot6 = new ShowLotInfo(access,AdministratorUI.this,6,true);
						}
						else
						{
							Lot6 = new ShowLotInfo(access,AdministratorUI.this,6,false);
						}
						Lot6.setVisible(true);
						AdministratorUI.this.setEnabled(false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			  }
			});
		LabelLot6.setVisible(false);//先隐藏
		ButtonLot6.setVisible(false);//先隐藏
		//ooooooooooooooooooooooooooooooooooooooooooo车库按钮设置完毕
		
		ADS.AddMyButton(SetJPanel, "UI/ADParkingManagement.png", 400, 550);
		LabelParkingManagement=ADS.getLabel();
		ImageParkingManagement=ADS.getimageicon();
		ButtonParkingManagement=ADS.getbutton();
		ButtonParkingManagement.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageParkingManagement.setImage(ImageParkingManagement.getImage().getScaledInstance(ImageParkingManagement.getIconWidth()-2,ImageParkingManagement.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelParkingManagement.setIcon(ImageParkingManagement);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageParkingManagement.setImage(ImageParkingManagement.getImage().getScaledInstance(ImageParkingManagement.getIconWidth()+2,ImageParkingManagement.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelParkingManagement.setIcon(ImageParkingManagement);
				  if(SetParkingManagementstatus==false)//点击了车库管理
				  {
					  LabelUserManagement.setVisible(false);//隐藏用户管理按钮
					  ButtonUserManagement.setVisible(false);
					  LabelParkingManagement.setLocation(400,100);
					  ButtonParkingManagement.setLocation(400, 100);
					  SetParkingManagementstatus=true;
					  LabelShowLot.setVisible(true);//添加边框
					  //------------下面添加按钮
					  UpdateData();
				  }
				  else
				  {
					  LabelUserManagement.setVisible(true);//显示用户管理按钮
					  ButtonUserManagement.setVisible(true);
					  LabelParkingManagement.setLocation(400,550);
					  ButtonParkingManagement.setLocation(400, 550);
					  SetParkingManagementstatus=false;
					  LabelShowLot.setVisible(false);
					  //------下面隐藏所有车库按钮
					  LabelLot1.setVisible(false);ButtonLot1.setVisible(false);
					  LabelLot2.setVisible(false);ButtonLot2.setVisible(false);
					  LabelLot3.setVisible(false);ButtonLot3.setVisible(false);
					  LabelLot4.setVisible(false);ButtonLot4.setVisible(false);
					  LabelLot5.setVisible(false);ButtonLot5.setVisible(false);
					  LabelLot6.setVisible(false);ButtonLot6.setVisible(false);
					  //隐藏添加车库按钮
					  LabelAddLot.setVisible(false);
					  ButtonAddLot.setVisible(false);
				  }
				  
			  }
			});
	}
	
	
	
	public JPanel getMainJPanel()
	{
		return MainJPanel;
	}
	
	public void UpdateData()
	{
		try {
			rs=access.executeQuery("Select * From Configuration");
			int lots=0;
			LabelLot1.setVisible(false);ButtonLot1.setVisible(false);
			LabelLot2.setVisible(false);ButtonLot2.setVisible(false);
			LabelLot3.setVisible(false);ButtonLot3.setVisible(false);
			LabelLot4.setVisible(false);ButtonLot4.setVisible(false);
			LabelLot5.setVisible(false);ButtonLot5.setVisible(false);
			LabelLot6.setVisible(false);ButtonLot6.setVisible(false);
			while(rs.next())
			{
				lots++;
				switch(lots)
				{
				case 1:LabelLot1.setVisible(true);ButtonLot1.setVisible(true);break;
				case 2:LabelLot2.setVisible(true);ButtonLot2.setVisible(true);break;
				case 3:LabelLot3.setVisible(true);ButtonLot3.setVisible(true);break;
				case 4:LabelLot4.setVisible(true);ButtonLot4.setVisible(true);break;
				case 5:LabelLot5.setVisible(true);ButtonLot5.setVisible(true);break;
				case 6:LabelLot6.setVisible(true);ButtonLot6.setVisible(true);break;
				default:;break;
				}
			}
			garage=lots+1;//将要添加的车库下标
			//将添加按钮加到lots下标
			if(lots<6)//车库小于6个
			{
				LabelAddLot.setLocation(lotx[lots], loty[lots]);
				ButtonAddLot.setLocation(lotx[lots], loty[lots]);
				LabelAddLot.setVisible(true);//添加车库添加按钮
				ButtonAddLot.setVisible(true);//添加车库添加按钮
			}
			else
			{
				LabelAddLot.setVisible(false);//添加车库添加按钮
				ButtonAddLot.setVisible(false);//添加车库添加按钮
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//访问数据库
	}
	public int getShowGar()//返回正在显示的车库下标
	{
		return showgar;
	}
}
