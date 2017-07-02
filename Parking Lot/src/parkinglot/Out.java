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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Out extends JFrame {

	private JPanel MainJPanel;
	private ImageIcon MainBG;
	private JLabel mainbackground;
	
	private int xframeold;
	private int yframeold;
	
	private AddStyle ADS=new AddStyle();//快捷添加界面
	
	private int garage;
	
	//按钮
	private JLabel LabelOK;
	
	private ImageIcon ImageOK;
	
	private JButton ButtonOK;
	
	private ConnectToAccess access;
	private ResultSet rs=null;
	
	public Out(ConnectToAccess Access,final SecurityGuardUI sg,int Garage) throws Exception {
		//===============================================================一系列初始化
		access=Access;//设置数据库连接
		setUndecorated(true);//去边框
		setResizable(false);//设置不能调整大小
		setBounds(720, 370, 600, 800);
		this.setSize(400, 400);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		garage=Garage;//车库
		
		//面板设置
		MainJPanel=(JPanel)getContentPane();
		
		//加载背景图片
		MainBG=new ImageIcon("UI/Out.jpg");
		
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
				sg.setEnabled(true);//可以操作ui界面
				Out.this.dispose();
			}
		});
		ButtonExit.setBounds(368, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//最小化按钮
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Out.this.setExtendedState(ICONIFIED);//设置最小化
			}
		});
		ButtonMinimize.setBounds(336, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		//===============================================================元件
//车牌号
		final JTextField TextCard=new JTextField();
		TextCard.setBounds(52, 207, 300, 45);
		TextCard.setFont(new Font("方正兰亭超细黑简体",0,30));
		TextCard.setBorder(null);
		TextCard.setOpaque(false);
		MainJPanel.add(TextCard);
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
				  //检查
				  String card=TextCard.getText();//文本框里的卡号
				  if(card.equals(""))
				  {
					  JOptionPane.showMessageDialog(null,  "请输入卡号号!", "错误！",JOptionPane.ERROR_MESSAGE);
					  return ;
				  }
				  for(int i=0;i<card.length();i++)
				  {
					  if(card.charAt(i)<'0'||card.charAt(i)>'9')
					  {
						  JOptionPane.showMessageDialog(null,  "卡号有误!", "错误！",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  //检查车库里是否有该车位
				  try {
					rs=access.executeQuery("SELECT * FROM Card WHERE CardNumber='"+card+"'");
					if(rs.next()==false||rs.getInt("Garage")!=garage)
					{
						JOptionPane.showMessageDialog(null,  "车库中无此卡", "错误！",JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(rs.getBoolean("IsCharging")==false)//说明此车位还没有人停,错误！
					{
						JOptionPane.showMessageDialog(null,  "该车位无车停放", "错误！",JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				  
				  //检查完毕无误!
				  //判断是否是月租卡
				  try {
					rs=access.executeQuery("Select * From MonthlyRentInfo WHERE Garage='"+garage+"'");
					while(rs.next())
					{
						if(card.equals(rs.getString("CardNumber")))//是月租卡
						{
							String license=rs.getString("License");
							//修改card,carport,插入schedule
							access.executeUpdate("UPDATE Card SET IsCharging='"+false+"' WHERE CardNumber='"+card+"'");
							//carport
							access.executeUpdate("UPDATE Carport SET IsAvaliable='"+true+"' WHERE ID='"+card+"'");
							//schedule
							access.executeUpdate("INSERT INTO Schedule VALUES('"+card+"','"+new Time().getValidTimetoStore()+"','"+false+"','"+0+"','"+license+"','"+true+"','"+garage+"','"+0+"','"+""+"')");
							JOptionPane.showMessageDialog(null,  "祝您路途愉快", "提示",JOptionPane.OK_OPTION);
							sg.setEnabled(true);
							sg.Update();
							Out.this.dispose();
							return;
						}
					}
					//不是月租卡
					//获得车牌号
					rs=access.executeQuery("Select * From Card WHERE Garage='"+garage+"'");
					String license=null;
					while(rs.next())
					{
						if(rs.getString("CardNumber").equals(card))
						{
							license=rs.getString("License");
							break;
						}
					}
					//card
					access.executeUpdate("UPDATE Card SET License='"+""+"' WHERE CardNumber='"+card+"'");//清空车牌号
					access.executeUpdate("UPDATE Card SET IsCharging='"+false+"' WHERE CardNumber='"+card+"'");//不在计费状态
					//carport
					access.executeUpdate("UPDATE Carport SET IsAvaliable='"+true+"' WHERE ID='"+card+"'");
					
					//计算收费
					int charge=0;
					//读入时间1--选择最近的时间
					rs=access.executeQuery("Select * From Schedule WHERE CardNumber='"+card+"'");
					rs.last();//移至最后一个数据
					String time1=rs.getString("Time");
					
					String nowtime=new Time().getValidTimetoStore();
					int hours=new Time().getChargeHours(time1, nowtime);//得到停车时长
					rs=access.executeQuery("Select * From Configuration WHERE Garage='"+garage+"'");//获得收费规则
					rs.next();//移动到数据域
					int charge1=rs.getInt("ChargingRule1");
					int charge2=rs.getInt("ChargingRule2");
					int charge3=rs.getInt("ChargingRule3");
					int charge4=rs.getInt("ChargingRule4");
					if(hours<=1)//为charge赋值
					{
						charge=charge1;
					}
					else if(hours<=5)
					{
						charge=charge2;
					}
					else if(hours<=10)
					{
						charge=charge3;
					}
					else
					{
						charge=charge4;
					}
					//schedule
					access.executeUpdate("INSERT INTO Schedule VALUES('"+card+"','"+nowtime+"','"+false+"','"+charge+"','"+license+"','"+false+"','"+garage+"','"+0+"','"+""+"')");
					JOptionPane.showMessageDialog(null,  "请缴费"+charge+"元,祝您路途愉快", "出库成功!",JOptionPane.OK_OPTION);
					sg.setEnabled(true);
					sg.Update();
					Out.this.dispose();
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
					  Out.this.setLocation(xframenew, yframenew);
					  }
					 });
	}
}
