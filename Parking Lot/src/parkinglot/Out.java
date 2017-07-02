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
	
	private AddStyle ADS=new AddStyle();//�����ӽ���
	
	private int garage;
	
	//��ť
	private JLabel LabelOK;
	
	private ImageIcon ImageOK;
	
	private JButton ButtonOK;
	
	private ConnectToAccess access;
	private ResultSet rs=null;
	
	public Out(ConnectToAccess Access,final SecurityGuardUI sg,int Garage) throws Exception {
		//===============================================================һϵ�г�ʼ��
		access=Access;//�������ݿ�����
		setUndecorated(true);//ȥ�߿�
		setResizable(false);//���ò��ܵ�����С
		setBounds(720, 370, 600, 800);
		this.setSize(400, 400);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		garage=Garage;//����
		
		//�������
		MainJPanel=(JPanel)getContentPane();
		
		//���ر���ͼƬ
		MainBG=new ImageIcon("UI/Out.jpg");
		
		//���ر���ͼƬ�ı�ǩ
		mainbackground=new JLabel(MainBG);
		//===============================================================���ñ���ͼƬ��ԭ��
		mainbackground.setBounds(0, 0, MainBG.getIconWidth(),MainBG.getIconHeight());
		
		MainJPanel.setOpaque(false);
		MainJPanel.setLayout(null);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(mainbackground, new Integer(Integer.MIN_VALUE));
		
		//--------------�߿�Ԫ��
		JButton ButtonExit = new JButton();//�رհ�ť
		ButtonExit.setContentAreaFilled(false);
		ButtonExit.setBorderPainted(false);
		ButtonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sg.setEnabled(true);//���Բ���ui����
				Out.this.dispose();
			}
		});
		ButtonExit.setBounds(368, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//��С����ť
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Out.this.setExtendedState(ICONIFIED);//������С��
			}
		});
		ButtonMinimize.setBounds(336, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		//===============================================================Ԫ��
//���ƺ�
		final JTextField TextCard=new JTextField();
		TextCard.setBounds(52, 207, 300, 45);
		TextCard.setFont(new Font("������ͤ��ϸ�ڼ���",0,30));
		TextCard.setBorder(null);
		TextCard.setOpaque(false);
		MainJPanel.add(TextCard);
//ȷ����ť
		ADS.AddMyButton(MainJPanel, "UI/OK.png", 160, 340);
		LabelOK=ADS.getLabel();
		ImageOK=ADS.getimageicon();
		ButtonOK=ADS.getbutton();
		ButtonOK.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageOK.setImage(ImageOK.getImage().getScaledInstance(ImageOK.getIconWidth()-2,ImageOK.getIconHeight()-2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelOK.setIcon(ImageOK);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageOK.setImage(ImageOK.getImage().getScaledInstance(ImageOK.getIconWidth()+2,ImageOK.getIconHeight()+2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelOK.setIcon(ImageOK);
				  //���
				  String card=TextCard.getText();//�ı�����Ŀ���
				  if(card.equals(""))
				  {
					  JOptionPane.showMessageDialog(null,  "�����뿨�ź�!", "����",JOptionPane.ERROR_MESSAGE);
					  return ;
				  }
				  for(int i=0;i<card.length();i++)
				  {
					  if(card.charAt(i)<'0'||card.charAt(i)>'9')
					  {
						  JOptionPane.showMessageDialog(null,  "��������!", "����",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  //��鳵�����Ƿ��иó�λ
				  try {
					rs=access.executeQuery("SELECT * FROM Card WHERE CardNumber='"+card+"'");
					if(rs.next()==false||rs.getInt("Garage")!=garage)
					{
						JOptionPane.showMessageDialog(null,  "�������޴˿�", "����",JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(rs.getBoolean("IsCharging")==false)//˵���˳�λ��û����ͣ,����
					{
						JOptionPane.showMessageDialog(null,  "�ó�λ�޳�ͣ��", "����",JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				  
				  //����������!
				  //�ж��Ƿ������⿨
				  try {
					rs=access.executeQuery("Select * From MonthlyRentInfo WHERE Garage='"+garage+"'");
					while(rs.next())
					{
						if(card.equals(rs.getString("CardNumber")))//�����⿨
						{
							String license=rs.getString("License");
							//�޸�card,carport,����schedule
							access.executeUpdate("UPDATE Card SET IsCharging='"+false+"' WHERE CardNumber='"+card+"'");
							//carport
							access.executeUpdate("UPDATE Carport SET IsAvaliable='"+true+"' WHERE ID='"+card+"'");
							//schedule
							access.executeUpdate("INSERT INTO Schedule VALUES('"+card+"','"+new Time().getValidTimetoStore()+"','"+false+"','"+0+"','"+license+"','"+true+"','"+garage+"','"+0+"','"+""+"')");
							JOptionPane.showMessageDialog(null,  "ף��·;���", "��ʾ",JOptionPane.OK_OPTION);
							sg.setEnabled(true);
							sg.Update();
							Out.this.dispose();
							return;
						}
					}
					//�������⿨
					//��ó��ƺ�
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
					access.executeUpdate("UPDATE Card SET License='"+""+"' WHERE CardNumber='"+card+"'");//��ճ��ƺ�
					access.executeUpdate("UPDATE Card SET IsCharging='"+false+"' WHERE CardNumber='"+card+"'");//���ڼƷ�״̬
					//carport
					access.executeUpdate("UPDATE Carport SET IsAvaliable='"+true+"' WHERE ID='"+card+"'");
					
					//�����շ�
					int charge=0;
					//����ʱ��1--ѡ�������ʱ��
					rs=access.executeQuery("Select * From Schedule WHERE CardNumber='"+card+"'");
					rs.last();//�������һ������
					String time1=rs.getString("Time");
					
					String nowtime=new Time().getValidTimetoStore();
					int hours=new Time().getChargeHours(time1, nowtime);//�õ�ͣ��ʱ��
					rs=access.executeQuery("Select * From Configuration WHERE Garage='"+garage+"'");//����շѹ���
					rs.next();//�ƶ���������
					int charge1=rs.getInt("ChargingRule1");
					int charge2=rs.getInt("ChargingRule2");
					int charge3=rs.getInt("ChargingRule3");
					int charge4=rs.getInt("ChargingRule4");
					if(hours<=1)//Ϊcharge��ֵ
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
					JOptionPane.showMessageDialog(null,  "��ɷ�"+charge+"Ԫ,ף��·;���", "����ɹ�!",JOptionPane.OK_OPTION);
					sg.setEnabled(true);
					sg.Update();
					Out.this.dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
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
					  Out.this.setLocation(xframenew, yframenew);
					  }
					 });
	}
}
