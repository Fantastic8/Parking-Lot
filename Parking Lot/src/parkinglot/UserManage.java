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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.Document;

import java.awt.Font;

import javax.swing.UIManager;

public class UserManage extends JFrame {
	private JPanel MainJPanel;//面板
	
	private ImageIcon MainBG;//设置背景
	
	private JLabel mainbackground;//设置背景的标签
	private ConnectToAccess access;
	private ResultSet rs=null;
	
	private int xframeold;
	private int yframeold;
	
	private AdministratorUI Adu=null;
	
	private AddStyle ADS=new AddStyle();//快捷添加界面
	private JTable TableUserManage;
	
	private TableModel tablemodel;//表格模型
	private String TableTitle[]={"用户名","职位","状态","区域"};
	
	//按钮集合
	private JLabel LabelAddUser;
	private JLabel LabelDeleteUser;
	private JLabel LabelEditInfo;
	
	private ImageIcon ImageAddUser;
	private ImageIcon ImageDeleteUser;
	private ImageIcon ImageEditInfo;
	
	private JButton ButtonAddUser;
	private JButton ButtonDeleteUser;
	private JButton ButtonEditInfo;
	
	public UserManage(ConnectToAccess Access,AdministratorUI adu) throws Exception {
		//===============================================================一系列初始化
		Adu=adu;
		access=Access;//设置数据库连接
		//System.out.println("数据库连接成功");
		rs=access.executeQuery("Select * From PeopleManagement");//读取结果集
		setUndecorated(true);//去边框
		setResizable(false);//设置不能调整大小
		setBounds(620, 150, 450, 300);
		this.setSize(600, 800);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//面板设置
		MainJPanel=(JPanel)getContentPane();
		
		//加载背景图片
		MainBG=new ImageIcon("UI/UserManage.jpg");
		
		//加载背景图片的标签
		mainbackground=new JLabel(MainBG);
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
			  UserManage.this.setLocation(xframenew, yframenew);
			  }
			 });
		//============================================================数据操作
		UpdateTable();
	}
	public void MainPane()
	{
		
		mainbackground.setBounds(0, 0, MainBG.getIconWidth(),MainBG.getIconHeight());
		//最小化和关闭按钮
		MainJPanel.setOpaque(false);
		MainJPanel.setLayout(null);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(mainbackground, new Integer(Integer.MIN_VALUE));
		
		JButton ButtonExit = new JButton();//关闭按钮
		ButtonExit.setContentAreaFilled(false);
		ButtonExit.setBorderPainted(false);
		ButtonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Adu.setEnabled(true);//可以操作ui界面
				UserManage.this.dispose();
			}
		});
		ButtonExit.setBounds(568, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//最小化按钮
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserManage.this.setExtendedState(ICONIFIED);//设置最小化
			}
		});
		ButtonMinimize.setBounds(536, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
//查找用户文本框
		final JTextField TextFind=new JTextField();
		TextFind.setBounds(155, 155, 290, 50);
		TextFind.setFont(new Font("幼圆",Font.PLAIN,30));
		TextFind.setBorder(null);
		TextFind.setOpaque(false);
		//增加动态监听事件
		Document doc=TextFind.getDocument();
		doc.addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					UpdateFind(TextFind.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					UpdateFind(TextFind.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					UpdateFind(TextFind.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}});
		MainJPanel.add(TextFind);
//Table
		TableUserManage = new JTable();
		TableUserManage.setBorder(null);
		TableUserManage.setFont(new Font("幼圆", Font.PLAIN, 28));
		TableUserManage.setBounds(116, 241, 369, 400);
		TableUserManage.setRowHeight(50);//设置表格高度
		JScrollPane scrollpane = new JScrollPane(TableUserManage);
		scrollpane.setBounds(116, 241, 369, 400);
		MainJPanel.add(scrollpane);
		
//添加用户按钮
		ADS.AddMyButton(MainJPanel, "UI/AddUser.png", 50, 710);
		LabelAddUser=ADS.getLabel();
		ImageAddUser=ADS.getimageicon();
		ButtonAddUser=ADS.getbutton();
		ButtonAddUser.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageAddUser.setImage(ImageAddUser.getImage().getScaledInstance(ImageAddUser.getIconWidth()-1,ImageAddUser.getIconHeight()-1 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelAddUser.setIcon(ImageAddUser);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageAddUser.setImage(ImageAddUser.getImage().getScaledInstance(ImageAddUser.getIconWidth()+1,ImageAddUser.getIconHeight()+1 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelAddUser.setIcon(ImageAddUser);
				  //添加用户界面
				  
				  try {
					  UserManage.this.setEnabled(false);//设置不能控制
					  AddUser ad;
					  ad = new AddUser(access,UserManage.this);
					  ad.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  
			  }
			});
//修改信息按钮
		ADS.AddMyButton(MainJPanel, "UI/EditUserInfo.png", 230, 710);
		LabelEditInfo=ADS.getLabel();
		ImageEditInfo=ADS.getimageicon();
		ButtonEditInfo=ADS.getbutton();
		ButtonEditInfo.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageEditInfo.setImage(ImageEditInfo.getImage().getScaledInstance(ImageEditInfo.getIconWidth()-1,ImageEditInfo.getIconHeight()-1 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelEditInfo.setIcon(ImageEditInfo);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageEditInfo.setImage(ImageEditInfo.getImage().getScaledInstance(ImageEditInfo.getIconWidth()+1,ImageEditInfo.getIconHeight()+1 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelEditInfo.setIcon(ImageEditInfo);
				  int row=TableUserManage.getSelectedRow();
				  int column=TableUserManage.getSelectedColumn();
				  //检查有没有选择
				  if(row<0||TableUserManage.getValueAt(row, 0)==null||TableUserManage.getValueAt(row, 0).equals("Administrator"))
				  {
					  JOptionPane.showMessageDialog(null,  "没有选择用户或该用户不能被修改!", "错误！",JOptionPane.ERROR_MESSAGE);
				  }
				  else
				  {
					  try {
						EditUser eu=new EditUser(access,UserManage.this,access.executeQuery("Select * From PeopleManagement Where UserName='"+TableUserManage.getValueAt(row, 0)+"'"));
						eu.setVisible(true);
						UserManage.this.setEnabled(false);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				  }
			  }
			});
//删除用户按钮
		ADS.AddMyButton(MainJPanel, "UI/DeleteUser.png", 410, 710);
		LabelDeleteUser=ADS.getLabel();
		ImageDeleteUser=ADS.getimageicon();
		ButtonDeleteUser=ADS.getbutton();
		ButtonDeleteUser.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageDeleteUser.setImage(ImageDeleteUser.getImage().getScaledInstance(ImageDeleteUser.getIconWidth()-1,ImageDeleteUser.getIconHeight()-1 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelDeleteUser.setIcon(ImageDeleteUser);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageDeleteUser.setImage(ImageDeleteUser.getImage().getScaledInstance(ImageDeleteUser.getIconWidth()+1,ImageDeleteUser.getIconHeight()+1 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelDeleteUser.setIcon(ImageDeleteUser);
				  int row=TableUserManage.getSelectedRow();
				  int column=TableUserManage.getSelectedColumn();
				  //检查有没有选择
				  if(row<0||TableUserManage.getValueAt(row, 0)==null||TableUserManage.getValueAt(row, 0).equals("Administrator"))
				  {
					  JOptionPane.showMessageDialog(null,  "没有选择用户或该用户不能被删除!", "错误！",JOptionPane.ERROR_MESSAGE);
				  }
				  else
				  {
					  //JOptionPane.showMessageDialog(null,  "确定要删除此用户吗？删除后不能撤销!", "警告",JOptionPane.ERROR_MESSAGE);
					  Object[] options = { "确定", "取消" };   
					  int response=JOptionPane.showOptionDialog(null, "确定要删除此用户吗？删除后不能撤销!", "警告",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
					  if(response==0)//确定
					  {
						  try {
								access.executeUpdate("Delete From PeopleManagement Where UserName='"+TableUserManage.getValueAt(row, 0)+"'");
								//dtm.removeRow(row);
								//UpdateTable();
								UpdateFind(TextFind.getText());
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
					  else
					  {
						  return;
					  }
				  }
			  }
			});
	}
	public void UpdateTable() throws Exception
	{
		int row=0;//行
		int rowlen=0;//行数量
		int column=0;//列
		String content[][];
		rs=access.executeQuery("Select * From PeopleManagement");//读取结果集
		while(rs.next())//收集行数量
		{
			rowlen++;
		}
		if(rowlen<8)//填充
		{
			content=new String[8][4];
		}
		else
		{
			content=new String[rowlen][4];
		}
		rs=access.executeQuery("Select * From PeopleManagement");//读取结果集
		//开始赋值
		for(row=0;rs.next();row++)
		{
			content[row][0]=rs.getString("UserName");
			if(rs.getString("IsAdministrator")=="true")
			{
				content[row][1]="管理员";
			}
			else
			{
				content[row][1]="保安";
			}
			if(rs.getString("IsOnline")=="true")
			{
				content[row][2]="上线";
			}
			else
			{
				content[row][2]="离线";
			}
			switch(rs.getInt("Garage"))
			{
			case 1:content[row][3]="车库一";break;
			case 2:content[row][3]="车库二";break;
			case 3:content[row][3]="车库三";break;
			case 4:content[row][3]="车库四";break;
			case 5:content[row][3]="车库五";break;
			case 6:content[row][3]="车库六";break;
			default:content[row][3]="全部";break;
			}
		}
		if(row<8)//没有填充满
		{
			for(;row<8;row++)
			{
				content[row][0]=null;
				content[row][1]=null;
				content[row][2]=null;
				content[row][3]=null;
			}
		}
		tablemodel=new DefaultTableModel(content,TableTitle){
			public boolean isCellEditable(int rowIndex,int columnIndex)
			{
				return false;
			}
		};
		TableUserManage.setModel(tablemodel);
	}
	public void UpdateFind(String name) throws Exception
	{
		int row=0;//行
		int rowlen=0;//行数量
		int column=0;//列
		String content[][];
		rs=access.executeQuery("Select * From PeopleManagement");//读取结果集
		while(rs.next())//收集行数量
		{
			if(rs.getString("UserName").contains(name))
			{
				rowlen++;
			}
		}
		if(rowlen<8)//填充
		{
			content=new String[8][4];
		}
		else
		{
			content=new String[rowlen][4];
		}
		rs=access.executeQuery("Select * From PeopleManagement");//读取结果集
		//开始赋值
		for(row=0;rs.next();)
		{
			if(rs.getString("UserName").contains(name))
			{
				content[row][0]=rs.getString("UserName");
				if(rs.getString("IsAdministrator")=="true")
				{
					content[row][1]="管理员";
				}
				else
				{
					content[row][1]="保安";
				}
				if(rs.getString("IsOnline")=="true")
				{
					content[row][2]="上线";
				}
				else
				{
					content[row][2]="离线";
				}
				switch(rs.getInt("Garage"))
				{
				case 1:content[row][3]="车库一";break;
				case 2:content[row][3]="车库二";break;
				case 3:content[row][3]="车库三";break;
				case 4:content[row][3]="车库四";break;
				case 5:content[row][3]="车库五";break;
				case 6:content[row][3]="车库六";break;
				default:content[row][3]="全部";break;
				}
				row++;
			}
		}
		if(row<8)//没有填充满
		{
			for(;row<8;row++)
			{
				content[row][0]=null;
				content[row][1]=null;
				content[row][2]=null;
				content[row][3]=null;
			}
		}
		tablemodel=new DefaultTableModel(content,TableTitle){
			public boolean isCellEditable(int rowIndex,int columnIndex)
			{
				return false;
			}
		};
		TableUserManage.setModel(tablemodel);
	}
}
