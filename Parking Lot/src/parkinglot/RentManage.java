package parkinglot;

import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.Document;

public class RentManage extends JFrame {
	private JPanel MainJPanel;//面板
	
	private ImageIcon MainBG;//设置背景
	
	private JLabel mainbackground;//设置背景的标签
	private ConnectToAccess access;
	private ResultSet rs=null;
	
	private int xframeold;
	private int yframeold;
	
	private AdministratorUI Adu=null;
	
	private AddStyle ADS=new AddStyle();//快捷添加界面
	private JTable TableRentManage;
	
	private TableModel tablemodel;//表格模型
	private String TableTitle[]={"卡号","车主姓名","车牌号"};
	
	//按钮集合
	private JLabel LabelAddRentCard;
	private JLabel LabelDeleteRentCard;
	private JLabel LabelEditInfo;
	
	private ImageIcon ImageAddRentCard;
	private ImageIcon ImageDeleteRentCard;
	private ImageIcon ImageEditInfo;
	
	private JButton ButtonAddRentCard;
	private JButton ButtonDeleteRentCard;
	private JButton ButtonEditInfo;
	
	public RentManage(ConnectToAccess Access,AdministratorUI adu) throws Exception {
		//===============================================================一系列初始化
		Adu=adu;
		access=Access;//设置数据库连接
		rs=access.executeQuery("Select * From MonthlyRentInfo");//读取结果集
		setUndecorated(true);//去边框
		setResizable(false);//设置不能调整大小
		setBounds(620, 150, 450, 300);
		this.setSize(600, 800);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//面板设置
		MainJPanel=(JPanel)getContentPane();
		
		//加载背景图片
		MainBG=new ImageIcon("UI/RentManage.jpg");
		
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
			  RentManage.this.setLocation(xframenew, yframenew);
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
				//更新界面
				try {
					Adu.ShowInfo(Adu.getShowGar());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				RentManage.this.dispose();
			}
		});
		ButtonExit.setBounds(568, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton("");//最小化按钮
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RentManage.this.setExtendedState(ICONIFIED);//设置最小化
			}
		});
		ButtonMinimize.setBounds(536, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
//Table
		TableRentManage = new JTable();
		TableRentManage.setBorder(null);
		TableRentManage.setFont(new Font("幼圆", Font.PLAIN, 28));
		TableRentManage.setBounds(65, 150, 470, 485);
		TableRentManage.setRowHeight(46);//设置表格高度
		JScrollPane scrollpane = new JScrollPane(TableRentManage);
		scrollpane.setBounds(65, 150, 470, 485);
		MainJPanel.add(scrollpane);
		
//添加月租卡按钮
		ADS.AddMyButton(MainJPanel, "UI/AddRentCard.png", 40, 710);
		LabelAddRentCard=ADS.getLabel();
		ImageAddRentCard=ADS.getimageicon();
		ButtonAddRentCard=ADS.getbutton();
		ButtonAddRentCard.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageAddRentCard.setImage(ImageAddRentCard.getImage().getScaledInstance(ImageAddRentCard.getIconWidth()-1,ImageAddRentCard.getIconHeight()-1 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelAddRentCard.setIcon(ImageAddRentCard);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageAddRentCard.setImage(ImageAddRentCard.getImage().getScaledInstance(ImageAddRentCard.getIconWidth()+1,ImageAddRentCard.getIconHeight()+1 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelAddRentCard.setIcon(ImageAddRentCard);
				  AddRentCard ard;
				try {
					ard = new AddRentCard(access,RentManage.this);
					ard.setVisible(true);
					RentManage.this.setEnabled(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
//修改月租卡按钮
		ADS.AddMyButton(MainJPanel, "UI/EditRentCard.png", 220, 710);
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
				  //检查选中的是否为有效的行
				  int row=TableRentManage.getSelectedRow();//获得所选行
				  //检查有没有选择
				  if(row<0||TableRentManage.getValueAt(row, 0)==null)
				  {
					  JOptionPane.showMessageDialog(null,  "请选择月租卡!", "错误！",JOptionPane.ERROR_MESSAGE);
				  }
				  else
				  {
					  int SelStall=Integer.valueOf(String.valueOf(TableRentManage.getValueAt(row, 0)));//车位
					  int SelLot=SelStall/1000;//车库
					  String SelOwnerName=String.valueOf(TableRentManage.getValueAt(row, 1));//车主姓名
					  String SelLicense=String.valueOf(TableRentManage.getValueAt(row, 2));//车牌号
					  EditRentCard erc;
					try {
						erc = new EditRentCard(access,RentManage.this,SelOwnerName,SelLicense,SelStall,SelLot);//最后四个参数分别为:车主姓名,车牌号,车位,车库
						erc.setVisible(true);
						RentManage.this.setEnabled(false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				  }
			  }
			});
//删除月租卡按钮
		ADS.AddMyButton(MainJPanel, "UI/DeleteRentCard.png", 400, 710);
		LabelDeleteRentCard=ADS.getLabel();
		ImageDeleteRentCard=ADS.getimageicon();
		ButtonDeleteRentCard=ADS.getbutton();
		ButtonDeleteRentCard.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageDeleteRentCard.setImage(ImageDeleteRentCard.getImage().getScaledInstance(ImageDeleteRentCard.getIconWidth()-1,ImageDeleteRentCard.getIconHeight()-1 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelDeleteRentCard.setIcon(ImageDeleteRentCard);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageDeleteRentCard.setImage(ImageDeleteRentCard.getImage().getScaledInstance(ImageDeleteRentCard.getIconWidth()+1,ImageDeleteRentCard.getIconHeight()+1 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelDeleteRentCard.setIcon(ImageDeleteRentCard);
				  int row=TableRentManage.getSelectedRow();
				  int column=TableRentManage.getSelectedColumn();
				  //检查有没有选择
				  if(row<0||TableRentManage.getValueAt(row, 0)==null)
				  {
					  JOptionPane.showMessageDialog(null,  "请选择月租卡!", "错误！",JOptionPane.ERROR_MESSAGE);
				  }
				  else
				  {
					  Object[] options = { "确定", "取消" };   
					  int response=JOptionPane.showOptionDialog(null, "确定要删除此月租卡吗？删除后不能撤销!", "警告",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
					  if(response==0)//确定
					  {
						  try {
							    rs=access.executeQuery("Select * From Card WHERE CardNumber='"+TableRentManage.getValueAt(row, 0)+"'");
							    rs.next();
							    String Licen=rs.getString("License");//获取当前的车牌号
							    int Gara=rs.getInt("Garage");//获取当前的车库
							    //获取车主姓名
							    rs=access.executeQuery("SELECT * From MonthlyRentInfo Where License='"+Licen+"'");
							    rs.next();
							    String Nam=rs.getString("OwnerName");
								access.executeUpdate("Delete From MonthlyRentInfo Where CardNumber='"+TableRentManage.getValueAt(row, 0)+"'");//删除此月租卡
								access.executeUpdate("UPDATE Card Set IsRentalCard='"+false+"' WHERE CardNumber='"+TableRentManage.getValueAt(row, 0)+"'");//不是月租卡
								access.executeUpdate("UPDATE Card Set License='"+""+"' WHERE CardNumber='"+TableRentManage.getValueAt(row, 0)+"'");//月租卡车牌号清零
								access.executeUpdate("UPDATE Carport Set IsRent='"+false+"' WHERE ID='"+TableRentManage.getValueAt(row, 0)+"'");//不是月租车位
								access.executeUpdate("INSERT INTO Schedule VALUES('"+TableRentManage.getValueAt(row, 0)+"','"+new Time().getValidTimetoStore()+"','"+false+"','"+0+"','"+Licen+"','"+true+"','"+Gara+"','"+2+"','"+Nam+"')");//最后一个参数1-添加月租卡,2-退月租卡,0-普通入库出库
								UpdateTable();//更新表
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
		rs=access.executeQuery("Select * From MonthlyRentInfo");//读取结果集
		while(rs.next())//收集行数量
		{
			rowlen++;
		}
		if(rowlen<10)//填充
		{
			content=new String[10][3];
		}
		else
		{
			content=new String[rowlen][3];
		}
		rs=access.executeQuery("Select * From MonthlyRentInfo");//读取结果集
		//开始赋值
		for(row=0;rs.next();row++)
		{
			content[row][0]=String.valueOf(rs.getInt("CardNumber"));//卡号
			content[row][1]=rs.getString("OwnerName");//车主姓名
			content[row][2]=rs.getString("License");//车牌号
		}
		if(row<10)//没有填充满
		{
			for(;row<10;row++)
			{
				content[row][0]=null;
				content[row][1]=null;
				content[row][2]=null;
			}
		}
		tablemodel=new DefaultTableModel(content,TableTitle){
			public boolean isCellEditable(int rowIndex,int columnIndex)
			{
				return false;
			}
		};
		TableRentManage.setModel(tablemodel);
	}
}
