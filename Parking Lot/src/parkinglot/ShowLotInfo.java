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

public class ShowLotInfo extends JFrame {
	private ConnectToAccess access;
	private ResultSet rs=null;
	private JPanel MainJPanel;
	private ImageIcon MainBG;
	private JLabel mainbackground;
	private int xframeold;
	private int yframeold;
	private AddStyle ADS=new AddStyle();
	private JLabel LabelTitle;
	private JLabel LabelReStall;
	private JLabel LabelTUStall;
	private JLabel LabelTAStall;
	private JLabel LabelFloor;
	private JLabel LabelStall;
	private JLabel LabelCharge1;
	private JLabel LabelCharge2;
	private JLabel LabelCharge3;
	private JLabel LabelCharge4;
	private boolean deleteable;
	//按钮类
	private JLabel LabelDelete;
	private JButton ButtonDelete;
	private ImageIcon ImageDelete;
	public ShowLotInfo(ConnectToAccess Access,final AdministratorUI adu,final int garage,final boolean isdelete) throws Exception {
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
		MainBG=new ImageIcon("UI/ShowLotInfo.jpg");
		
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
				ShowLotInfo.this.dispose();
			}
		});
		ButtonExit.setBounds(568, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton("");//最小化按钮
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowLotInfo.this.setExtendedState(ICONIFIED);//设置最小化
			}
		});
		ButtonMinimize.setBounds(536, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		
		//-------------添加内容元件
		LabelTitle=new JLabel();
		LabelTitle.setBounds(200,10,300,100);
		LabelTitle.setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 70));
		switch(garage)
		{
		case 1:LabelTitle.setText("车库一");break;
		case 2:LabelTitle.setText("车库二");break;
		case 3:LabelTitle.setText("车库三");break;
		case 4:LabelTitle.setText("车库四");break;
		case 5:LabelTitle.setText("车库五");break;
		case 6:LabelTitle.setText("车库六");break;
		default:;break;
		}
		MainJPanel.add(LabelTitle);
		
		LabelReStall=new JLabel();
		LabelReStall.setBounds(400,105,300,100);
		LabelReStall.setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 60));
		MainJPanel.add(LabelReStall);
		
		LabelTUStall=new JLabel();
		LabelTUStall.setBounds(400,183,300,100);
		LabelTUStall.setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 60));
		MainJPanel.add(LabelTUStall);
		
		LabelTAStall=new JLabel();
		LabelTAStall.setBounds(400,260,300,100);
		LabelTAStall.setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 60));
		MainJPanel.add(LabelTAStall);
		
		LabelFloor=new JLabel();
		LabelFloor.setBounds(400,340,300,100);
		LabelFloor.setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 60));
		MainJPanel.add(LabelFloor);
		
		LabelStall=new JLabel();
		LabelStall.setBounds(400,410,300,100);
		LabelStall.setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 60));
		MainJPanel.add(LabelStall);
		
		LabelCharge1=new JLabel();
		LabelCharge1.setBounds(270,558,300,100);
		LabelCharge1.setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 30));
		MainJPanel.add(LabelCharge1);
		
		LabelCharge2=new JLabel();
		LabelCharge2.setBounds(270,608,300,100);
		LabelCharge2.setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 30));
		MainJPanel.add(LabelCharge2);
		
		LabelCharge3=new JLabel();
		LabelCharge3.setBounds(270,653,300,100);
		LabelCharge3.setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 30));
		MainJPanel.add(LabelCharge3);
		
		LabelCharge4=new JLabel();
		LabelCharge4.setBounds(270,698,300,100);
		LabelCharge4.setFont(new Font("方正兰亭超细黑简体", Font.PLAIN, 30));
		MainJPanel.add(LabelCharge4);
	//删除按钮	
		ADS.AddMyButton(MainJPanel, "UI/Delete.png", 470, 720);
		LabelDelete=ADS.getLabel();
		ImageDelete=ADS.getimageicon();
		ButtonDelete=ADS.getbutton();
		ButtonDelete.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageDelete.setImage(ImageDelete.getImage().getScaledInstance(ImageDelete.getIconWidth()-1,ImageDelete.getIconHeight()-1 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelDelete.setIcon(ImageDelete);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageDelete.setImage(ImageDelete.getImage().getScaledInstance(ImageDelete.getIconWidth()+1,ImageDelete.getIconHeight()+1 , Image.SCALE_DEFAULT));//等比例缩放90%
				  LabelDelete.setIcon(ImageDelete);
				  if(deleteable&&isdelete==true)
				  {
					  Object[] options = { "确定", "取消" };   
					  int response=JOptionPane.showOptionDialog(null, "确定要删除此车库吗？删除此车库会将与该车库关联的保安一并删除。删除后不能撤销!", "警告",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
					  if(response==0)//确定
					  {
						  try {
							access.executeUpdate("Delete From Configuration Where Garage="+garage);
							access.executeUpdate("Delete From Carport Where Garage="+garage);
							access.executeUpdate("Delete From Card Where Garage="+garage);
							access.executeUpdate("Delete From PeopleManagement WHERE Garage='"+garage+"'");
							adu.setEnabled(true);
							adu.UpdateData();
							ShowLotInfo.this.dispose();
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
				  }
				  else
				  {
					  JOptionPane.showMessageDialog(null,  "此车库有车或者有月租卡未退，或不是最后的车库，不能删除！", "错误！",JOptionPane.ERROR_MESSAGE);
				  }
			  }
			});
		ShowInfo(garage);
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
			  ShowLotInfo.this.setLocation(xframenew, yframenew);
			  }
			 });
	}
	void ShowInfo(int garage) throws Exception
	{
		int restall=0;
		int tustall=0;
		int tastall=0;
		rs=access.executeQuery("Select * From Carport Where Garage="+garage);
		
		while(rs.next())
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
		}
		if(restall==0&&tustall==0)//有车停或者有月租卡则不能删除
		{
			deleteable=true;
		}
		else
		{
			deleteable=false;
		}
		LabelReStall.setText(new String().valueOf(restall));
		LabelTUStall.setText(new String().valueOf(tustall));
		LabelTAStall.setText(new String().valueOf(tastall));
		
		rs=access.executeQuery("Select * From Configuration Where Garage="+garage);
		rs.next();//移动到数据域
		LabelFloor.setText(new String().valueOf(rs.getInt("Floor")));
		LabelStall.setText(new String().valueOf(rs.getInt("StallEachFloor")));
		LabelCharge1.setText(new String().valueOf(rs.getInt("ChargingRule1")));
		LabelCharge2.setText(new String().valueOf(rs.getInt("ChargingRule2")));
		LabelCharge3.setText(new String().valueOf(rs.getInt("ChargingRule3")));
		LabelCharge4.setText(new String().valueOf(rs.getInt("ChargingRule4")));
	}
}
