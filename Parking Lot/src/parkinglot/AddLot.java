package parkinglot;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JComboBox;

import java.awt.Font;
import javax.swing.DefaultComboBoxModel;

public class AddLot extends JFrame {
	private ConnectToAccess access;
	private ResultSet rs=null;
	private JPanel MainJPanel;
	private ImageIcon MainBG;
	private JLabel mainbackground;
	private int xframeold;
	private int yframeold;
	
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public AddLot(ConnectToAccess Access,final AdministratorUI adu,final int garage) {
		//===============================================================一系列初始化
		access=Access;//设置数据库连接
		setUndecorated(true);//去边框
		setResizable(false);//设置不能调整大小
		setBounds(620, 150, 600, 800);
		this.setSize(600, 800);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//面板设置
		MainJPanel=(JPanel)getContentPane();
		
		//加载背景图片
		MainBG=new ImageIcon("UI/AddLotFrame.jpg");
		
		//加载背景图片的标签
		mainbackground=new JLabel(MainBG);
		//===============================================================设置背景图片和原件
		mainbackground.setBounds(0, 0, MainBG.getIconWidth(),MainBG.getIconHeight());
		
		MainJPanel.setOpaque(false);
		MainJPanel.setLayout(null);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(mainbackground, new Integer(Integer.MIN_VALUE));
		
		//--------------边框元件
		JButton ButtonExit = new JButton("");//关闭按钮
		ButtonExit.setContentAreaFilled(false);
		ButtonExit.setBorderPainted(false);
		ButtonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adu.setEnabled(true);//可以操作ui界面
				AddLot.this.dispose();
			}
		});
		ButtonExit.setBounds(568, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton("");//最小化按钮
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddLot.this.setExtendedState(ICONIFIED);//设置最小化
			}
		});
		ButtonMinimize.setBounds(536, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		
		//-------------添加内容元件
		String Floor[]={"1","2","3","4","5","6"};
		final JComboBox ComboBoxFloor = new JComboBox(Floor);
		ComboBoxFloor.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		ComboBoxFloor.setBounds(243, 264, 107, 24);
		//ComboBoxFloor.addItem(makeObj(""));
		getContentPane().add(ComboBoxFloor);
		
		String Stall[]={"20","40","60","80","100","120","140","160","180","200"};
		final JComboBox ComboBoxStall = new JComboBox(Stall);
		ComboBoxStall.setModel(new DefaultComboBoxModel(new String[] {"5", "10", "15", "20", "25", "30"}));
		ComboBoxStall.setFont(new Font("宋体", Font.PLAIN, 15));
		ComboBoxStall.setBounds(227, 419, 149, 24);
		getContentPane().add(ComboBoxStall);
		
		final JTextField TextField1=new JTextField();//1小时内
		TextField1.setBounds(250, 555, 80, 25);
		MainJPanel.add(TextField1);
		
		final JTextField TextField2=new JTextField();//1-5小时
		TextField2.setBounds(250, 595, 80, 25);
		MainJPanel.add(TextField2);
		
		final JTextField TextField3=new JTextField();//5-10小时
		TextField3.setBounds(250, 640, 80, 25);
		MainJPanel.add(TextField3);
		
		final JTextField TextField4=new JTextField();//10-24小时
		TextField4.setBounds(250, 680, 80, 25);
		MainJPanel.add(TextField4);
		
		//------------确定按钮和取消按钮
		final ImageIcon ImageOK=new ImageIcon("UI/OK.png");//添加确定按钮
		final JLabel LabelOK=new JLabel(ImageOK);
		LabelOK.setBounds(180, 730, ImageOK.getIconWidth(), ImageOK.getIconHeight());
		MainJPanel.add(LabelOK);
		final JButton ButtonOK=new JButton();//确定相应按钮
		ButtonOK.setContentAreaFilled(false);
		ButtonOK.setBorderPainted(false);
		ButtonOK.setBounds(180, 730, ImageOK.getIconWidth(), ImageOK.getIconHeight());
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
				  int Floor=ComboBoxFloor.getSelectedIndex()+1;//楼层
				  int Stall=(ComboBoxStall.getSelectedIndex()+1)*5;//车位
				  //检查
				  if(Floor<=0)
				  {
					  JOptionPane.showMessageDialog(null,  "请选择楼层!", "错误！",JOptionPane.ERROR_MESSAGE);
				  }
				  if(Stall<=0)
				  {
					  JOptionPane.showMessageDialog(null,  "请选择每层的车位数量!", "错误！",JOptionPane.ERROR_MESSAGE);
				  }
				  if(TextField1.getText().isEmpty()||TextField2.getText().isEmpty()||TextField3.getText().isEmpty()||TextField4.getText().isEmpty())
				  {
					  JOptionPane.showMessageDialog(null,  "收费规则不能为空!", "错误！",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  //查找有无非法输入
				  for(int i=0;i<TextField1.getText().length();i++)
				  {
					  if(TextField1.getText().charAt(i)>57||TextField1.getText().charAt(i)<48)
					  {
						  JOptionPane.showMessageDialog(null,  "非法输入!", "错误！",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  for(int i=0;i<TextField2.getText().length();i++)
				  {
					  if(TextField2.getText().charAt(i)>57||TextField2.getText().charAt(i)<48)
					  {
						  JOptionPane.showMessageDialog(null,  "非法输入!", "错误！",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  for(int i=0;i<TextField3.getText().length();i++)
				  {
					  if(TextField3.getText().charAt(i)>57||TextField3.getText().charAt(i)<48)
					  {
						  JOptionPane.showMessageDialog(null,  "非法输入!", "错误！",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  for(int i=0;i<TextField4.getText().length();i++)
				  {
					  if(TextField4.getText().charAt(i)>57||TextField4.getText().charAt(i)<48)
					  {
						  JOptionPane.showMessageDialog(null,  "非法输入!", "错误！",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  //开始插入
				  int charge1=Integer.parseInt(TextField1.getText());//价格1
				  int charge2=Integer.parseInt(TextField2.getText());//价格2
				  int charge3=Integer.parseInt(TextField3.getText());//价格3
				  int charge4=Integer.parseInt(TextField4.getText());//价格4
				  
				try {
					access.executeUpdate("INSERT INTO Configuration VALUES("+garage+","+Floor+","+Stall+","+charge1+","+charge2+","+charge3+","+charge4+")");//更新配置数据库
					//access.ReadyForPtmt("INSERT INTO Carport VALUES(?,?,?)");//准备批量插入
					for(int f=1;f<=Floor;f++)
					{
						for(int s=1;s<=Stall;s++)
						{
							access.executeUpdate("INSERT INTO Carport VALUES("+(garage*1000+f*100+s)+",true,"+garage+",false)");//更新车位数据库
							access.executeUpdate("INSERT INTO Card VALUES("+(garage*1000+f*100+s)+",null,"+false+","+false+","+garage+")");
						}
					}
					access.close();
					access.Open();
					//执行批量
					//access.getPtmt().executeBatch();
					//access.executeUpdate("INSERT INTO Card VALUSE()");//更新卡数据库
					//更新完毕
				} catch (SQLException e1 ) {
					// TODO Auto-generated catch block
					e1.getNextException();
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
				adu.setEnabled(true);
				AddLot.this.dispose();
				
				adu.UpdateData();//更新adu的数据
			  }
			});
		MainJPanel.add(ButtonOK);
		
		final ImageIcon ImageCancel=new ImageIcon("UI/Cancel.png");//添加取消按钮
		final JLabel LabelCancel=new JLabel(ImageCancel);
		LabelCancel.setBounds(350, 730, ImageCancel.getIconWidth(), ImageCancel.getIconHeight());
		MainJPanel.add(LabelCancel);
		final JButton ButtonCancel=new JButton();//取消相应按钮
		ButtonCancel.setContentAreaFilled(false);
		ButtonCancel.setBorderPainted(false);
		ButtonCancel.setBounds(350, 730, ImageCancel.getIconWidth(), ImageCancel.getIconHeight());
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
				  adu.setEnabled(true);//可以操作ui界面
				  AddLot.this.dispose();
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
			  AddLot.this.setLocation(xframenew, yframenew);
			  }
			 });
	}
		
}
