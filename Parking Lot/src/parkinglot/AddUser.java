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
	
	private AddStyle ADS=new AddStyle();//�����ӽ���
	
	private String UserName;
	private String Password;
	
	private JComboBox BoxGarage;//����ѡ��
	private String[] BoxGarageData;//����ѡ�������
	private ComboBoxModel BoxGarageModel;//����ѡ��ģ��
	//��ť
	private JLabel LabelOK;
	private JLabel LabelCancle;
	
	private ImageIcon ImageOK;
	private ImageIcon ImageCancle;
	
	private JButton ButtonOK;
	private JButton ButtonCancle;
	private ConnectToAccess access;
	private ResultSet rs=null;
	public AddUser(ConnectToAccess Access,final UserManage um) throws Exception {
		//===============================================================һϵ�г�ʼ��
		access=Access;//�������ݿ�����
		setUndecorated(true);//ȥ�߿�
		setResizable(false);//���ò��ܵ�����С
		setBounds(720, 370, 600, 800);
		this.setSize(400, 400);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�������
		MainJPanel=(JPanel)getContentPane();
		
		//���ر���ͼƬ
		MainBG=new ImageIcon("UI/AddUser.jpg");
		
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
				um.setEnabled(true);//���Բ���ui����
				AddUser.this.dispose();
			}
		});
		ButtonExit.setBounds(368, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//��С����ť
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddUser.this.setExtendedState(ICONIFIED);//������С��
			}
		});
		ButtonMinimize.setBounds(336, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		//===============================================================Ԫ��
//����Աѡ���
		final JCheckBox checkBox = new JCheckBox();
		checkBox.setBounds(82, 17, 25, 27);
		//checkBox.setBorder(null);
		checkBox.setContentAreaFilled(false);
		checkBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED)//��ѡ��
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
//�û���
		final JTextField TextUserName=new JTextField();
		TextUserName.setBounds(90, 56, 220, 27);
		TextUserName.setBorder(null);
		MainJPanel.add(TextUserName);
//����
		final JPasswordField TextPassword=new JPasswordField();
		TextPassword.setBounds(90, 127, 220, 27);
		TextPassword.setBorder(null);
		MainJPanel.add(TextPassword);
//ȷ������
		final JPasswordField TextDoPassword=new JPasswordField();
		TextDoPassword.setBounds(90, 205, 220, 27);
		TextDoPassword.setBorder(null);
		MainJPanel.add(TextDoPassword);
//ѡ�񳵿�
		BoxGarage=new JComboBox();
		rs=access.executeQuery("Select * From Configuration");
		int recordgarage;//��¼��������
		for(recordgarage=0;rs.next();recordgarage++);//���㳵������
		rs=access.executeQuery("Select * From Configuration");
		BoxGarageData=new String[recordgarage];
		//��ʼ��ֵ
		for(int i=0;rs.next();i++)
		{
			BoxGarageData[i]=String.valueOf(i+1);
		}
		BoxGarageModel=new DefaultComboBoxModel(BoxGarageData);
		BoxGarage.setModel(BoxGarageModel);
		BoxGarage.setBounds(90, 290, 220, 27);
		MainJPanel.add(BoxGarage);
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
				  //����û���
				  UserName=TextUserName.getText();
				  if(UserName.equals(""))
				  {
					  JOptionPane.showMessageDialog(null,  "�������û���!", "����",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  for(int i=0;i<UserName.length();i++)
				  {
					  if(!((UserName.charAt(i)>='a'&&UserName.charAt(i)<='z')||(UserName.charAt(i)>='A'&&UserName.charAt(i)<='Z')||(UserName.charAt(i)>='0'&&UserName.charAt(i)<='9')))
					  {
						  JOptionPane.showMessageDialog(null,  "�û��������Ƿ��ַ�!", "����",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  if(!String.valueOf(TextPassword.getPassword()).equals(String.valueOf(TextDoPassword.getPassword())))
				  {
					  JOptionPane.showMessageDialog(null,  "�����������벻��ͬ!", "����",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  //����û��������ظ�
				  try {
					rs=access.executeQuery("Select UserName From PeopleManagement");
					while(rs.next())
					{
						if(UserName.equals(rs.getString("UserName")))//�û����ظ�
						{
							JOptionPane.showMessageDialog(null,  "���û��Ѵ���!", "����",JOptionPane.ERROR_MESSAGE);
							return ;
						}
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				  //������
				  try {
					  if(checkBox.isSelected())//����Ա,û�г���
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
					  AddUser.this.setLocation(xframenew, yframenew);
					  }
					 });
	}
}
