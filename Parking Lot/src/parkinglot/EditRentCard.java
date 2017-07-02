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

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class EditRentCard extends JFrame {
	private JPanel MainJPanel;
	private ImageIcon MainBG;
	private JLabel mainbackground;
	
	private int xframeold;
	private int yframeold;
	
	private AddStyle ADS=new AddStyle();//快捷添加界面
	
	private JTextField TextOwnerName;//车主姓名文本框
	private JTextField TextLicense;//车牌号文本框
	private JComboBox ComboBoxStall;//车位选择框
	private ComboBoxModel BoxModelStall;
	private JLabel LabelLot;//车库标签-车库不能更改
	
	private int Stallnum;//记录Garage中有多少车位
	private String[] StallInfo;//动态存储车位信息

	//按钮
	private JLabel LabelOK;
	
	private ImageIcon ImageOK;
	
	private JButton ButtonOK;
	private ConnectToAccess access;
	private ResultSet rs;
	public EditRentCard(ConnectToAccess Access,final RentManage rm,String Name,String License,final int Stall,int Garage) throws Exception {
		//===============================================================一系列初始化
		access=Access;//设置数据库连接
		setUndecorated(true);//去边框
		setResizable(false);//设置不能调整大小
		setBounds(720, 370, 400, 300);
		this.setSize(400, 300);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//面板设置
		MainJPanel=(JPanel)getContentPane();
		
		//加载背景图片
		MainBG=new ImageIcon("UI/EditRentCard.jpg");
		
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
				EditRentCard.this.dispose();
			}
		});
		ButtonExit.setBounds(368, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//最小化按钮
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditRentCard.this.setExtendedState(ICONIFIED);//设置最小化
			}
		});
		ButtonMinimize.setBounds(336, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		//===============================================================元件
//车主姓名文本框
		TextOwnerName=new JTextField();
		TextOwnerName.setBounds(190, 82, 160,30);
		TextOwnerName.setBorder(null);
		TextOwnerName.setOpaque(false);
		TextOwnerName.setFont(new Font("方正兰亭超细黑简体",0,15));
		TextOwnerName.setText(Name);
		MainJPanel.add(TextOwnerName);
		
//车牌号文本框
		TextLicense=new JTextField();
		TextLicense.setBounds(190, 136, 160,30);
		TextLicense.setBorder(null);
		TextLicense.setOpaque(false);
		TextLicense.setFont(new Font("方正兰亭超细黑简体",0,15));
		TextLicense.setText(License);
		MainJPanel.add(TextLicense);
		
//车位选择框
		ComboBoxStall=new JComboBox();
		ComboBoxStall.setBounds(186, 183, 168, 30);
		rs=access.executeQuery("Select * From Carport WHERE Garage='"+Garage+"'");
		for(Stallnum=0;rs.next();)//计算该车库的车位数量,不包括已租车位,但包括自己
		{
			if(rs.getBoolean("ISRent")==true&&!(rs.getInt("ID")==Stall))
			{
				continue;
			}
			Stallnum++;
		}
		StallInfo=new String[Stallnum];
		//开始为Stall赋值
		rs=access.executeQuery("Select * From Carport WHERE Garage='"+Garage+"'");
		for(int i=0;rs.next();)
		{
			if(rs.getBoolean("ISRent")==true&&!(rs.getInt("ID")==Stall))
			{
				continue;
			}
			StallInfo[i]=rs.getString("ID");
			i++;
		}
		BoxModelStall=new DefaultComboBoxModel(StallInfo);
		ComboBoxStall.setModel(BoxModelStall);
		ComboBoxStall.setSelectedItem(String.valueOf(Stall));//设置默认选择项
		MainJPanel.add(ComboBoxStall);
		
		
//车库标签--车库不能更改
		LabelLot=new JLabel();
		LabelLot.setText(String.valueOf(Garage));
		LabelLot.setBounds(190, 215, 100, 70);
		LabelLot.setFont(new Font("方正兰亭超细黑简体",0,25));
		MainJPanel.add(LabelLot);
		
//确定按钮
		ADS.AddMyButton(MainJPanel, "UI/OK.png", 280, 240);
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
				  //检查有无非法输入
				  int SStall=ComboBoxStall.getSelectedIndex();
				  String Name=TextOwnerName.getText();//车主姓名
				  String License=TextLicense.getText();//车牌号
				//检查
				  if(SStall<0)
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
				  for(int i=0;i<License.length();i++)
				  {
					  if(!((License.charAt(i)>='a'&&License.charAt(i)<='z')||(License.charAt(i)>='A'&&License.charAt(i)<='Z')||(License.charAt(i)>='0'&&License.charAt(i)<='9')))
					  {
						  JOptionPane.showMessageDialog(null,  "车牌号中包含非法字符！", "错误！",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  //检查完毕
				  int SelectedStall=Integer.valueOf(ComboBoxStall.getSelectedItem().toString());//ID
				  System.out.println(SelectedStall);
				  try {
					access.executeUpdate("UPDATE MonthlyRentInfo SET OwnerName='"+Name+"' WHERE CardNumber='"+Stall+"'");//修改月租卡信息表中的车主姓名和车牌号信息
					access.executeUpdate("UPDATE MonthlyRentInfo SET License='"+License+"' WHERE CardNumber='"+Stall+"'");//修改月租卡信息表中的车主姓名和车牌号信息
					access.executeUpdate("UPDATE MonthlyRentInfo SET CardNumber='"+SelectedStall+"' WHERE CardNumber='"+Stall+"'");//修改月租卡信息表中的车主姓名和车牌号信息
					access.executeUpdate("UPDATE Carport SET IsRent='"+false+"' WHERE ID='"+Stall+"'");//将Carport中Stall的Isrent设为false,SelectedStall的isrent为true
					access.executeUpdate("UPDATE Carport SET IsRent='"+true+"' WHERE ID='"+SelectedStall+"'");//将Carport中Stall的Isrent设为false,SelectedStall的isrent为true
					rs=access.executeQuery("Select * From Carport WHERE ID='"+Stall+"'");//将carport的isavaliable新旧对换
					rs.next();//移动到数据域
					boolean oldavaliable=rs.getBoolean("IsAvaliable");
					rs=access.executeQuery("Select * From Carport WHERE ID='"+SelectedStall+"'");//将carport的isavaliable新旧对换
					rs.next();//移动到数据域
					boolean newavaliable=rs.getBoolean("IsAvaliable");
					access.executeUpdate("UPDATE Carport SET IsAvaliable='"+newavaliable+"' WHERE ID='"+Stall+"'");//进行对换
					access.executeUpdate("UPDATE Carport SET IsAvaliable='"+oldavaliable+"' WHERE ID='"+SelectedStall+"'");//进行对换
					//carport对换完毕
					//将Card中license 和IsRentalCard和IsCharging新旧对换
					rs=access.executeQuery("Select * From Card WHERE CardNumber='"+Stall+"'");
					rs.next();//指向数据域
					boolean oldIsCharging=rs.getBoolean("IsCharging");
					rs=access.executeQuery("Select * From Card WHERE CardNumber='"+SelectedStall+"'");
					rs.next();
					boolean newIsCharging=rs.getBoolean("IsCharging");
					access.executeUpdate("UPDATE Card SET License='"+""+"' WHERE CardNumber='"+Stall+"'");//旧的车牌号为空--先清空后设置,防止车位相同的情况
					access.executeUpdate("UPDATE Card SET License='"+License+"' WHERE CardNumber='"+SelectedStall+"'");
					
					access.executeUpdate("UPDATE Card SET IsCharging='"+oldIsCharging+"' WHERE CardNumber='"+SelectedStall+"'");
					access.executeUpdate("UPDATE Card SET IsCharging='"+newIsCharging+"' WHERE CardNumber='"+Stall+"'");
					access.executeUpdate("UPDATE Card SET IsRentalCard='"+false+"' WHERE CardNumber='"+Stall+"'");
					access.executeUpdate("UPDATE Card SET IsRentalCard='"+true+"' WHERE CardNumber='"+SelectedStall+"'");
					//数据交换完毕
					rm.setEnabled(true);
					rm.UpdateTable();//更新数据
					EditRentCard.this.dispose();
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
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
					  EditRentCard.this.setLocation(xframenew, yframenew);
					  }
					 });
	}
}
