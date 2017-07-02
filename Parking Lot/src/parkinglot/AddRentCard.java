package parkinglot;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;

public class AddRentCard extends JFrame {
	private ConnectToAccess access;
	private ResultSet rs=null;
	private JPanel MainJPanel;
	private ImageIcon MainBG;
	private JLabel mainbackground;
	private int xframeold;
	private int yframeold;
	
	private int SelectedLot;
	private int SelectedStall;
	
	private JComboBox BoxStall;
	private JComboBox BoxLot;
	private ComboBoxModel cbmlot;
	private ComboBoxModel cbmstall;
	
	private JTextField TextName;
	private JTextField TextLicense;
	private JTextField TextCharge;
	
	private int garagenum;//总车库的个数
	private int stallnum;//该车库总共有多少个车位
	
	private String SLot[];//放置车库名字
	private String SStall[];//放置车位名字
	
	public AddRentCard(ConnectToAccess Access,final RentManage rm) throws Exception {
		//===============================================================一系列初始化
		access=Access;//设置数据库连接
		setUndecorated(true);//去边框
		setResizable(false);//设置不能调整大小
		setBounds(620, 250, 600, 600);
		this.setSize(600, 600);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//面板设置
		MainJPanel=(JPanel)getContentPane();
		
		//加载背景图片
		MainBG=new ImageIcon("UI/AddRentCard.jpg");
		
		//加载背景图片的标签
		mainbackground=new JLabel(MainBG);
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
				rm.setEnabled(true);//可以操作ui界面
				AddRentCard.this.dispose();
			}
		});
		ButtonExit.setBounds(568, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//最小化按钮
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddRentCard.this.setExtendedState(ICONIFIED);//设置最小化
			}
		});
		ButtonMinimize.setBounds(536, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		
		//-------------添加内容元件
//车位选择
		stallnum=0;
		BoxStall=new JComboBox();
		BoxStall.setBounds(270, 229, 150, 25);
		MainJPanel.add(BoxStall);
		
//车库选择
		garagenum=0;
		BoxLot=new JComboBox();
		BoxLot.setBounds(270, 153, 150, 25);
		rs=access.executeQuery("Select * From Configuration");//读取配置信息
		for(garagenum=0;rs.next();garagenum++);//计算有多少个可以添加月租卡的车库
		SLot=new String[garagenum];
		//为SLot赋值
		for(int lot=0;lot<garagenum;lot++)
		{
			SLot[lot]=String.valueOf(lot+1);
		}
		cbmlot=new DefaultComboBoxModel(SLot);
		BoxLot.setModel(cbmlot);
		BoxLot.setSelectedIndex(-1);//设置默认不选
		BoxLot.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED)//只执行一次
				{
					SelectedLot=BoxLot.getSelectedIndex()+1;
					//System.out.println("选中"+SelectedLot);
					try {
						rs=access.executeQuery("Select * From Carport Where Garage='"+SelectedLot+"'");
						//计算车位的个数
						for(stallnum=0;rs.next();)
						{
							if(rs.getBoolean("IsRent")==true)//不计算已租过的车位
							{
								continue;
							}
							stallnum++;
						}
						SStall=new String[stallnum];
						//开始赋值
						rs=access.executeQuery("Select * From Carport Where Garage='"+SelectedLot+"'");
						for(int i=0;rs.next();)
						{
							if(rs.getBoolean("IsRent")==true)//不显示已租的车位
							{
								continue;
							}
							SStall[i]=String.valueOf(rs.getInt("ID"));
							i++;
						}
						cbmstall=new DefaultComboBoxModel(SStall);
						BoxStall.setModel(cbmstall);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}});
		MainJPanel.add(BoxLot);
		
//添加车主姓名文本框
		TextName=new JTextField();
		TextName.setBounds(233, 315, 217, 30);
		TextName.setBorder(null);
		TextName.setOpaque(false);
		TextName.setFont(new Font("幼圆", Font.PLAIN, 20));
		MainJPanel.add(TextName);
		
//添加车牌号文本框
		TextLicense=new JTextField();
		TextLicense.setBounds(233, 393, 218, 30);
		TextLicense.setBorder(null);
		TextLicense.setOpaque(false);
		TextLicense.setFont(new Font("幼圆", Font.PLAIN, 20));
		MainJPanel.add(TextLicense);
//添加收费文本框
		TextCharge=new JTextField();
		TextCharge.setBounds(233, 457, 218, 30);
		TextCharge.setBorder(null);
		TextCharge.setOpaque(false);
		TextCharge.setFont(new Font("幼圆", Font.PLAIN, 20));
		MainJPanel.add(TextCharge);
		
		
		//------------确定按钮和取消按钮
		final ImageIcon ImageOK=new ImageIcon("UI/OK.png");//添加确定按钮
		final JLabel LabelOK=new JLabel(ImageOK);
		LabelOK.setBounds(180, 530, ImageOK.getIconWidth(), ImageOK.getIconHeight());
		MainJPanel.add(LabelOK);
		final JButton ButtonOK=new JButton();//确定相应按钮
		ButtonOK.setContentAreaFilled(false);
		ButtonOK.setBorderPainted(false);
		ButtonOK.setBounds(180, 530, ImageOK.getIconWidth(), ImageOK.getIconHeight());
		ButtonOK.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageOK.setImage(ImageOK.getImage().getScaledInstance(ImageOK.getIconWidth()-2,ImageOK.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelOK.setIcon(ImageOK);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageOK.setImage(ImageOK.getImage().getScaledInstance(ImageOK.getIconWidth()+2,ImageOK.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelOK.setIcon(ImageOK);
				  
				  int Lot=BoxLot.getSelectedIndex();
				  int Stall=BoxStall.getSelectedIndex();
				  String Name=TextName.getText();
				  String License=TextLicense.getText();
				//检查
				  if(Lot<0)
				  {
					  JOptionPane.showMessageDialog(null,  "请选择车库！", "错误！",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  if(Stall<0)
				  {
					  JOptionPane.showMessageDialog(null,  "请选择车位！", "错误！",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  if(Name.equals(""))
				  {
					  JOptionPane.showMessageDialog(null,  "请输入车主姓名!", "错误！",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  if(License.equals(""))
				  {
					  JOptionPane.showMessageDialog(null,  "请输入车牌号!", "错误！",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  if(Name.contains("'")||Name.contains("\"")||Name.contains("?")||Name.contains("!")||Name.contains("`")||Name.contains("~"))
				  {
					  JOptionPane.showMessageDialog(null,  "车主姓名中包含非法字符！", "错误！",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  if(TextCharge.getText().equals(""))
				  {
					  JOptionPane.showMessageDialog(null,  "请收费!", "错误！",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  for(int i=0;i<TextCharge.getText().length();i++)
				  {
					  if(!(TextCharge.getText().charAt(i)>='0'&&TextCharge.getText().charAt(i)<='9'))
					  {
						  JOptionPane.showMessageDialog(null,  "收费错误!", "错误！",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  for(int i=0;i<License.length();i++)
				  {
					  if(!((License.charAt(i)>='a'&&License.charAt(i)<='z')||(License.charAt(i)>='A'&&License.charAt(i)<='Z')||(License.charAt(i)>='0'&&License.charAt(i)<='9')))
					  {
						  JOptionPane.showMessageDialog(null,  "车牌号中包含非法字符！", "错误！",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  //检查完毕
				  SelectedStall=Integer.valueOf(BoxStall.getSelectedItem().toString());
				  try {
					access.executeUpdate("INSERT INTO MonthlyRentInfo VALUES('"+Name+"','"+License+"','"+SelectedStall+"','"+SelectedLot+"')");
					access.executeUpdate("UPDATE Card SET License='"+License+"' WHERE CardNumber='"+SelectedStall+"'");
					access.executeUpdate("UPDATE Card SET IsRentalCard='"+true+"' WHERE CardNumber='"+SelectedStall+"'");
					access.executeUpdate("UPDATE Carport SET IsRent='"+true+"' WHERE ID='"+SelectedStall+"'");
					access.executeUpdate("INSERT INTO Schedule VALUES('"+SelectedStall+"','"+new Time().getValidTimetoStore()+"','"+false+"','"+Integer.valueOf(TextCharge.getText())+"','"+License+"','"+true+"','"+SelectedLot+"','"+1+"','"+Name+"')");//倒数第二个参数1-添加月租卡,2-退月租卡,0-普通入库出库
					rm.UpdateTable();
					rm.setEnabled(true);
					AddRentCard.this.dispose();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
		MainJPanel.add(ButtonOK);
		
		final ImageIcon ImageCancel=new ImageIcon("UI/Cancel.png");//添加取消按钮
		final JLabel LabelCancel=new JLabel(ImageCancel);
		LabelCancel.setBounds(350, 530, ImageCancel.getIconWidth(), ImageCancel.getIconHeight());
		MainJPanel.add(LabelCancel);
		final JButton ButtonCancel=new JButton();//取消相应按钮
		ButtonCancel.setContentAreaFilled(false);
		ButtonCancel.setBorderPainted(false);
		ButtonCancel.setBounds(350, 530, ImageCancel.getIconWidth(), ImageCancel.getIconHeight());
		ButtonCancel.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageCancel.setImage(ImageCancel.getImage().getScaledInstance(ImageCancel.getIconWidth()-2,ImageCancel.getIconHeight()-2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelCancel.setIcon(ImageCancel);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageCancel.setImage(ImageCancel.getImage().getScaledInstance(ImageCancel.getIconWidth()+2,ImageCancel.getIconHeight()+2 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelCancel.setIcon(ImageCancel);
				  rm.setEnabled(true);//可以操作ui界面
				  AddRentCard.this.dispose();
			  }
			});
		MainJPanel.add(ButtonCancel);
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
			  AddRentCard.this.setLocation(xframenew, yframenew);
			  }
			 });
	}
}
