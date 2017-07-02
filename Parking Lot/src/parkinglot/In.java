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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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

public class In extends JFrame {
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
	
	public In(ConnectToAccess Access,final SecurityGuardUI sg,int Garage) throws Exception {
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
		MainBG=new ImageIcon("UI/In.jpg");
		
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
				In.this.dispose();
			}
		});
		ButtonExit.setBounds(368, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//最小化按钮
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				In.this.setExtendedState(ICONIFIED);//设置最小化
			}
		});
		ButtonMinimize.setBounds(336, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		//===============================================================元件
//车牌号
		final JTextField TextLicense=new JTextField();
		TextLicense.setBounds(52, 207, 300, 45);
		TextLicense.setFont(new Font("方正兰亭超细黑简体",0,30));
		TextLicense.setBorder(null);
		TextLicense.setOpaque(false);
		MainJPanel.add(TextLicense);
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
				  String License=TextLicense.getText();
				  if(License.equals(""))
				  {
					  JOptionPane.showMessageDialog(null,  "请输入车牌号!", "错误！",JOptionPane.ERROR_MESSAGE);
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
				  try {
					rs=access.executeQuery("Select * From Card WHERE License='"+License+"'");//检查是否已经在车库里
					if(rs.next())//有记录
					{
						if(rs.getBoolean("IsCharging"))//该车库中有该车不能再次停放
						{
							JOptionPane.showMessageDialog(null,  "该车已在车库中", "错误！",JOptionPane.ERROR_MESSAGE);
							  return;
						}
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				  //检查完毕
				  //开始存入数据库
				  //判断是不是月租卡
				  try {
					rs=access.executeQuery("Select * From MonthlyRentInfo WHERE Garage='"+garage+"'");
					while(rs.next())
					{
						if(License.equals(rs.getString("License")))//是月租卡
						{
							String card=rs.getString("CardNumber");
							//修改card和Carport里的数据并存入schedule记录
							access.executeUpdate("UPDATE Card SET IsCharging='"+true+"' WHERE CardNumber='"+card+"'");
							access.executeUpdate("UPDATE Carport SET IsAvaliable='"+false+"' WHERE ID='"+card+"'");
							access.executeUpdate("INSERT INTO Schedule VALUES('"+card+"','"+new Time().getValidTimetoStore()+"','"+true+"','"+""+"','"+License+"','"+true+"','"+garage+"','"+0+"','"+""+"')");
							JOptionPane.showMessageDialog(null,  "欢迎您回来,卡号"+card, "提示",JOptionPane.OK_OPTION);
							sg.setEnabled(true);
							sg.Update();
							In.this.dispose();
							return;
						}
					}
					//不是月租卡
					rs=access.executeQuery("Select * From Carport WHERE Garage='"+garage+"'");
					String Stall="";
					while(rs.next())
					{
						if(rs.getBoolean("IsAvaliable")&&!rs.getBoolean("IsRent"))
						{
							//找到空车位
							Stall=rs.getString("ID");
							break;//一定能找到车位
						}
					}
					//显示该车位信息
					JOptionPane.showMessageDialog(null,  "分配的车位为:"+Stall, "提示",JOptionPane.OK_OPTION);
					//存入数据库...
					//1.card 2.carport 3.schedule
					//1.card
					access.executeUpdate("UPDATE Card SET IsCharging='"+true+"' WHERE CardNumber='"+Stall+"'");
					access.executeUpdate("UPDATE Card SET License='"+License+"' WHERE CardNumber='"+Stall+"'");
					//2.carport
					access.executeUpdate("UPDATE Carport SET IsAvaliable='"+false+"' WHERE ID='"+Stall+"'");
					//3.schedule
					access.executeUpdate("INSERT INTO Schedule VALUES('"+Stall+"','"+new Time().getValidTimetoStore()+"','"+true+"','"+""+"','"+License+"','"+false+"','"+garage+"','"+0+"','"+""+"')");
					sg.setEnabled(true);
					sg.Update();
					In.this.dispose();
					
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
			  In.this.setLocation(xframenew, yframenew);
			  }
			 });
		
//		Socket socket = new Socket();
//		SocketAddress remoteAddr = new InetSocketAddress("172.27.35.3",1010);
//		socket.connect(remoteAddr, 60000); //等待建立连接的超时时间为1分钟
//		System.out.println("Connect Success!");
//		System.out.println(socket.getInputStream().read());
		
		ServerSocket ss = new ServerSocket(1010);
        
        System.out.println("The server is waiting your input...");
        Socket socket;
        while(true)
        {
        	socket = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String line = in.readLine();
            System.out.println("you input is : " + line);
            //out.println("you input is :" + line);  
            out.close();
            in.close();
            socket.close();
            
            if(line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit"))
                break;
        }
        ss.close();
	}
}
