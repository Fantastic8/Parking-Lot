package parkinglot;

import java.awt.EventQueue;
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
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AddUser extends JFrame {
	private JPanel MainJPanel;
	private ImageIcon MainBG;
	private JLabel mainbackground;
	
	private int xframeold;
	private int yframeold;
	
	private AddStyle ADS=new AddStyle();//快捷添加界面
	
	private String UserName;
	private String Password;
	
	private JComboBox BoxGarage;//车库选择
	private String[] BoxGarageData;//车库选择的数据
	private ComboBoxModel BoxGarageModel;//车库选择模型
	//按钮
	private JLabel LabelOK;
	private JLabel LabelCancle;
	
	private ImageIcon ImageOK;
	private ImageIcon ImageCancle;
	
	private JButton ButtonOK;
	private JButton ButtonCancle;
	private ConnectToAccess access;
	private ResultSet rs=null;
	public AddUser(ConnectToAccess Access,final UserManage um) throws Exception {
		//===============================================================一系列初始化
		access=Access;//设置数据库连接
		setUndecorated(true);//去边框
		setResizable(false);//设置不能调整大小
		setBounds(720, 370, 600, 800);
		this.setSize(400, 400);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//面板设置
		MainJPanel=(JPanel)getContentPane();
		
		//加载背景图片
		MainBG=new ImageIcon("UI/AddUser.jpg");
		
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
				um.setEnabled(true);//可以操作ui界面
				AddUser.this.dispose();
			}
		});
		ButtonExit.setBounds(368, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//最小化按钮
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddUser.this.setExtendedState(ICONIFIED);//设置最小化
			}
		});
		ButtonMinimize.setBounds(336, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		//===============================================================元件
//管理员选择框
		final JCheckBox checkBox = new JCheckBox();
		checkBox.setBounds(82, 17, 25, 27);
		//checkBox.setBorder(null);
		checkBox.setContentAreaFilled(false);
		checkBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED)//被选中
				{
					BoxGarage.setEnabled(false);
				}
				else if(e.getStateChange()==ItemEvent.DESELECTED)
				{
					BoxGarage.setEnabled(true);
				}
			}
			
		});
		MainJPanel.add(checkBox);
//用户名
		final JTextField TextUserName=new JTextField();
		TextUserName.setBounds(90, 56, 220, 27);
		TextUserName.setBorder(null);
		MainJPanel.add(TextUserName);
//密码
		final JPasswordField TextPassword=new JPasswordField();
		TextPassword.setBounds(90, 127, 220, 27);
		TextPassword.setBorder(null);
		MainJPanel.add(TextPassword);
//确认密码
		final JPasswordField TextDoPassword=new JPasswordField();
		TextDoPassword.setBounds(90, 205, 220, 27);
		TextDoPassword.setBorder(null);
		MainJPanel.add(TextDoPassword);
//选择车库
		BoxGarage=new JComboBox();
		rs=access.executeQuery("Select * From Configuration");
		int recordgarage;//记录车库数量
		for(recordgarage=0;rs.next();recordgarage++);//计算车库数量
		rs=access.executeQuery("Select * From Configuration");
		BoxGarageData=new String[recordgarage];
		//开始赋值
		for(int i=0;rs.next();i++)
		{
			BoxGarageData[i]=String.valueOf(i+1);
		}
		BoxGarageModel=new DefaultComboBoxModel(BoxGarageData);
		BoxGarage.setModel(BoxGarageModel);
		BoxGarage.setBounds(90, 290, 220, 27);
		MainJPanel.add(BoxGarage);
//确定按钮
		ADS.AddMyButton(MainJPanel, "UI/OK.png", 160, 340);
		LabelOK=ADS.getLabel();
		ImageOK=ADS.getimageicon();
		ButtonOK=ADS.getbutton();
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
				  //检查用户名
				  UserName=TextUserName.getText();
				  if(UserName.equals(""))
				  {
					  JOptionPane.showMessageDialog(null,  "请输入用户名!", "错误！",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  for(int i=0;i<UserName.length();i++)
				  {
					  if(!((UserName.charAt(i)>='a'&&UserName.charAt(i)<='z')||(UserName.charAt(i)>='A'&&UserName.charAt(i)<='Z')||(UserName.charAt(i)>='0'&&UserName.charAt(i)<='9')))
					  {
						  JOptionPane.showMessageDialog(null,  "用户名包含非法字符!", "错误！",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  if(!String.valueOf(TextPassword.getPassword()).equals(String.valueOf(TextDoPassword.getPassword())))
				  {
					  JOptionPane.showMessageDialog(null,  "两次输入密码不相同!", "错误！",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  //检查用户名有无重复
				  try {
					rs=access.executeQuery("Select UserName From PeopleManagement");
					while(rs.next())
					{
						if(UserName.equals(rs.getString("UserName")))//用户名重复
						{
							JOptionPane.showMessageDialog(null,  "该用户已存在!", "错误！",JOptionPane.ERROR_MESSAGE);
							return ;
						}
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				  //检查完毕
				  try {
					  if(checkBox.isSelected())//管理员,没有车库
					  {
						  access.executeUpdate("INSERT INTO PeopleManagement VALUES('"+UserName+"','"+String.valueOf(TextPassword.getPassword())+"','"+true+"','"+false+"','"+""+"')");
					  }
					  else
					  {
						  access.executeUpdate("INSERT INTO PeopleManagement VALUES('"+UserName+"','"+String.valueOf(TextPassword.getPassword())+"','"+false+"','"+false+"','"+(BoxGarage.getSelectedIndex()+1)+"')");
					  }
					um.setEnabled(true);
					try {
						um.UpdateTable();
						AddUser.this.dispose();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
				}
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
					  AddUser.this.setLocation(xframenew, yframenew);
					  }
					 });
	}
}
