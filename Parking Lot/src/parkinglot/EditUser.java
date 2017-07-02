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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class EditUser extends JFrame {private JPanel MainJPanel;
private ImageIcon MainBG;
private JLabel mainbackground;

private int xframeold;
private int yframeold;

private AddStyle ADS=new AddStyle();//�����ӽ���

private String UserName;
private String Password;
//��ť
private JLabel LabelOK;
private JLabel LabelCancle;

private ImageIcon ImageOK;
private ImageIcon ImageCancle;

private JButton ButtonOK;
private JButton ButtonCancle;
private ConnectToAccess access;
private ResultSet rs;
private boolean changepassword=false;
private String OldUserName;
public EditUser(ConnectToAccess Access,final UserManage um,ResultSet rsuser) throws SQLException {
	//===============================================================һϵ�г�ʼ��
	rs=rsuser;//��ѡ�е��û�
	rs.next();//�ƶ���������
	OldUserName=rs.getString("UserName");//��ȡԭ���û���
	access=Access;//�������ݿ�����
	setUndecorated(true);//ȥ�߿�
	setResizable(false);//���ò��ܵ�����С
	setBounds(720, 370, 600, 800);
	this.setSize(400, 300);
	getContentPane().setLayout(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	//�������
	MainJPanel=(JPanel)getContentPane();
	
	//���ر���ͼƬ
	MainBG=new ImageIcon("UI/EditUser.jpg");
	
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
			EditUser.this.dispose();
		}
	});
	ButtonExit.setBounds(368, 0, 30, 33);
	getContentPane().add(ButtonExit);
	
	JButton ButtonMinimize = new JButton();//��С����ť
	ButtonMinimize.setContentAreaFilled(false);
	ButtonMinimize.setBorderPainted(false);
	ButtonMinimize.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			EditUser.this.setExtendedState(ICONIFIED);//������С��
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
	checkBox.setSelected(rs.getBoolean("IsAdministrator"));
	MainJPanel.add(checkBox);
//�û���
	final JTextField TextUserName=new JTextField();
	TextUserName.setBounds(90, 56, 220, 27);
	TextUserName.setBorder(null);
	TextUserName.setText(rs.getString("UserName"));
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
//ȷ����ť
	ADS.AddMyButton(MainJPanel, "UI/OK.png", 160, 250);
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
			  //�ж��Ƿ���Ҫ�޸�����
			  if(String.valueOf(TextPassword.getPassword()).equals("")&&String.valueOf(TextDoPassword.getPassword()).equals(""))//���޸�����
			  {
				  changepassword=false;//���޸�����
			  }
			  else
			  {
				  changepassword=true;//�޸�����
			  }
			  //����û���
			  UserName=TextUserName.getText();
			  for(int i=0;i<UserName.length();i++)
			  {
				  if(!((UserName.charAt(i)>='a'&&UserName.charAt(i)<='z')||(UserName.charAt(i)>='A'&&UserName.charAt(i)<='Z')||(UserName.charAt(i)>='0'&&UserName.charAt(i)<='9')))
				  {
					  JOptionPane.showMessageDialog(null,  "�û��������Ƿ��ַ�!", "����",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
			  }
			//����û��������ظ�
			  try {
				rs=access.executeQuery("Select UserName From PeopleManagement");
				while(rs.next())
				{
					if(UserName.equals(rs.getString("UserName"))&&(!UserName.equals(OldUserName)))//�û����ظ�
					{
						JOptionPane.showMessageDialog(null,  "���û��Ѵ���!", "����",JOptionPane.ERROR_MESSAGE);
						return ;
					}
				}
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			  if(changepassword)//Ҫ�޸�����
			  {
				  if(!String.valueOf(TextPassword.getPassword()).equals(String.valueOf(TextDoPassword.getPassword())))
				  {
					  JOptionPane.showMessageDialog(null,  "�����������벻��ͬ!", "����",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
			  }
			  //������
			  try {
				  if(checkBox.isSelected())//����Ա
				  {
					  access.executeUpdate("UPDATE PeopleManagement SET Garage='"+0+"' WHERE UserName='"+OldUserName+"'");
				  }
				  access.executeUpdate("UPDATE PeopleManagement SET IsAdministrator='"+checkBox.isSelected()+"' WHERE UserName='"+OldUserName+"'");
				  access.executeUpdate("UPDATE PeopleManagement SET UserName='"+UserName+"' WHERE UserName='"+OldUserName+"'");
				  if(changepassword)//�޸�����
				  {
					  
					  access.executeUpdate("UPDATE PeopleManagement SET Password='"+String.valueOf(TextPassword.getPassword())+"' WHERE UserName='"+OldUserName+"'");
					  
				  }
				um.setEnabled(true);
				try {
					um.UpdateTable();
					EditUser.this.dispose();
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
				  EditUser.this.setLocation(xframenew, yframenew);
				  }
				 });
}
}
