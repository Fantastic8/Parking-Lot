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
	//��ť��
	private JLabel LabelDelete;
	private JButton ButtonDelete;
	private ImageIcon ImageDelete;
	public ShowLotInfo(ConnectToAccess Access,final AdministratorUI adu,final int garage,final boolean isdelete) throws Exception {
		//===============================================================һϵ�г�ʼ��
		access=Access;//�������ݿ�����
		setUndecorated(true);//ȥ�߿�
		setResizable(false);//���ò��ܵ�����С
		setBounds(620, 150, 600, 800);
		this.setSize(600, 800);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�������
		MainJPanel=(JPanel)getContentPane();
		
		//���ر���ͼƬ
		MainBG=new ImageIcon("UI/ShowLotInfo.jpg");
		
		//���ر���ͼƬ�ı�ǩ
		mainbackground=new JLabel(MainBG);
		//===============================================================���ñ���ͼƬ��ԭ��
		mainbackground.setBounds(0, 0, MainBG.getIconWidth(),MainBG.getIconHeight());
		
		MainJPanel.setOpaque(false);
		MainJPanel.setLayout(null);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(mainbackground, new Integer(Integer.MIN_VALUE));
		
		//--------------�߿�Ԫ��
		JButton ButtonExit = new JButton("");//�رհ�ť
		ButtonExit.setContentAreaFilled(false);
		ButtonExit.setBorderPainted(false);
		ButtonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adu.setEnabled(true);//���Բ���ui����
				ShowLotInfo.this.dispose();
			}
		});
		ButtonExit.setBounds(568, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton("");//��С����ť
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowLotInfo.this.setExtendedState(ICONIFIED);//������С��
			}
		});
		ButtonMinimize.setBounds(536, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		
		//-------------�������Ԫ��
		LabelTitle=new JLabel();
		LabelTitle.setBounds(200,10,300,100);
		LabelTitle.setFont(new Font("������ͤ��ϸ�ڼ���", Font.PLAIN, 70));
		switch(garage)
		{
		case 1:LabelTitle.setText("����һ");break;
		case 2:LabelTitle.setText("�����");break;
		case 3:LabelTitle.setText("������");break;
		case 4:LabelTitle.setText("������");break;
		case 5:LabelTitle.setText("������");break;
		case 6:LabelTitle.setText("������");break;
		default:;break;
		}
		MainJPanel.add(LabelTitle);
		
		LabelReStall=new JLabel();
		LabelReStall.setBounds(400,105,300,100);
		LabelReStall.setFont(new Font("������ͤ��ϸ�ڼ���", Font.PLAIN, 60));
		MainJPanel.add(LabelReStall);
		
		LabelTUStall=new JLabel();
		LabelTUStall.setBounds(400,183,300,100);
		LabelTUStall.setFont(new Font("������ͤ��ϸ�ڼ���", Font.PLAIN, 60));
		MainJPanel.add(LabelTUStall);
		
		LabelTAStall=new JLabel();
		LabelTAStall.setBounds(400,260,300,100);
		LabelTAStall.setFont(new Font("������ͤ��ϸ�ڼ���", Font.PLAIN, 60));
		MainJPanel.add(LabelTAStall);
		
		LabelFloor=new JLabel();
		LabelFloor.setBounds(400,340,300,100);
		LabelFloor.setFont(new Font("������ͤ��ϸ�ڼ���", Font.PLAIN, 60));
		MainJPanel.add(LabelFloor);
		
		LabelStall=new JLabel();
		LabelStall.setBounds(400,410,300,100);
		LabelStall.setFont(new Font("������ͤ��ϸ�ڼ���", Font.PLAIN, 60));
		MainJPanel.add(LabelStall);
		
		LabelCharge1=new JLabel();
		LabelCharge1.setBounds(270,558,300,100);
		LabelCharge1.setFont(new Font("������ͤ��ϸ�ڼ���", Font.PLAIN, 30));
		MainJPanel.add(LabelCharge1);
		
		LabelCharge2=new JLabel();
		LabelCharge2.setBounds(270,608,300,100);
		LabelCharge2.setFont(new Font("������ͤ��ϸ�ڼ���", Font.PLAIN, 30));
		MainJPanel.add(LabelCharge2);
		
		LabelCharge3=new JLabel();
		LabelCharge3.setBounds(270,653,300,100);
		LabelCharge3.setFont(new Font("������ͤ��ϸ�ڼ���", Font.PLAIN, 30));
		MainJPanel.add(LabelCharge3);
		
		LabelCharge4=new JLabel();
		LabelCharge4.setBounds(270,698,300,100);
		LabelCharge4.setFont(new Font("������ͤ��ϸ�ڼ���", Font.PLAIN, 30));
		MainJPanel.add(LabelCharge4);
	//ɾ����ť	
		ADS.AddMyButton(MainJPanel, "UI/Delete.png", 470, 720);
		LabelDelete=ADS.getLabel();
		ImageDelete=ADS.getimageicon();
		ButtonDelete=ADS.getbutton();
		ButtonDelete.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageDelete.setImage(ImageDelete.getImage().getScaledInstance(ImageDelete.getIconWidth()-1,ImageDelete.getIconHeight()-1 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelDelete.setIcon(ImageDelete);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageDelete.setImage(ImageDelete.getImage().getScaledInstance(ImageDelete.getIconWidth()+1,ImageDelete.getIconHeight()+1 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelDelete.setIcon(ImageDelete);
				  if(deleteable&&isdelete==true)
				  {
					  Object[] options = { "ȷ��", "ȡ��" };   
					  int response=JOptionPane.showOptionDialog(null, "ȷ��Ҫɾ���˳�����ɾ���˳���Ὣ��ó�������ı���һ��ɾ����ɾ�����ܳ���!", "����",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
					  if(response==0)//ȷ��
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
					  JOptionPane.showMessageDialog(null,  "�˳����г����������⿨δ�ˣ��������ĳ��⣬����ɾ����", "����",JOptionPane.ERROR_MESSAGE);
				  }
			  }
			});
		ShowInfo(garage);
		//===============================================================ʵ����קЧ��
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
			if(rs.getBoolean("IsRent")==true)//���⳵λ
			{
				restall++;
			}
			else if(rs.getBoolean("IsAvaliable")==true)//��ʱ���г�λ
			{
				tastall++;
			}
			else//��ʱ��ͣ��λ
			{
				tustall++;
			}
		}
		if(restall==0&&tustall==0)//�г�ͣ���������⿨����ɾ��
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
		rs.next();//�ƶ���������
		LabelFloor.setText(new String().valueOf(rs.getInt("Floor")));
		LabelStall.setText(new String().valueOf(rs.getInt("StallEachFloor")));
		LabelCharge1.setText(new String().valueOf(rs.getInt("ChargingRule1")));
		LabelCharge2.setText(new String().valueOf(rs.getInt("ChargingRule2")));
		LabelCharge3.setText(new String().valueOf(rs.getInt("ChargingRule3")));
		LabelCharge4.setText(new String().valueOf(rs.getInt("ChargingRule4")));
	}
}
