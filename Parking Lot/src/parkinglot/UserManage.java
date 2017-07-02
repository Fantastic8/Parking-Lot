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
	private JPanel MainJPanel;//���
	
	private ImageIcon MainBG;//���ñ���
	
	private JLabel mainbackground;//���ñ����ı�ǩ
	private ConnectToAccess access;
	private ResultSet rs=null;
	
	private int xframeold;
	private int yframeold;
	
	private AdministratorUI Adu=null;
	
	private AddStyle ADS=new AddStyle();//�����ӽ���
	private JTable TableUserManage;
	
	private TableModel tablemodel;//���ģ��
	private String TableTitle[]={"�û���","ְλ","״̬","����"};
	
	//��ť����
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
		//===============================================================һϵ�г�ʼ��
		Adu=adu;
		access=Access;//�������ݿ�����
		//System.out.println("���ݿ����ӳɹ�");
		rs=access.executeQuery("Select * From PeopleManagement");//��ȡ�����
		setUndecorated(true);//ȥ�߿�
		setResizable(false);//���ò��ܵ�����С
		setBounds(620, 150, 450, 300);
		this.setSize(600, 800);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�������
		MainJPanel=(JPanel)getContentPane();
		
		//���ر���ͼƬ
		MainBG=new ImageIcon("UI/UserManage.jpg");
		
		//���ر���ͼƬ�ı�ǩ
		mainbackground=new JLabel(MainBG);
		//MonthlyRentBG=new ImageIcon();
		//===============================================================���ñ���ͼƬ��ԭ��
		MainPane();//���������
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
			  UserManage.this.setLocation(xframenew, yframenew);
			  }
			 });
		//============================================================���ݲ���
		UpdateTable();
	}
	public void MainPane()
	{
		
		mainbackground.setBounds(0, 0, MainBG.getIconWidth(),MainBG.getIconHeight());
		//��С���͹رհ�ť
		MainJPanel.setOpaque(false);
		MainJPanel.setLayout(null);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(mainbackground, new Integer(Integer.MIN_VALUE));
		
		JButton ButtonExit = new JButton();//�رհ�ť
		ButtonExit.setContentAreaFilled(false);
		ButtonExit.setBorderPainted(false);
		ButtonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Adu.setEnabled(true);//���Բ���ui����
				UserManage.this.dispose();
			}
		});
		ButtonExit.setBounds(568, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//��С����ť
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserManage.this.setExtendedState(ICONIFIED);//������С��
			}
		});
		ButtonMinimize.setBounds(536, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
//�����û��ı���
		final JTextField TextFind=new JTextField();
		TextFind.setBounds(155, 155, 290, 50);
		TextFind.setFont(new Font("��Բ",Font.PLAIN,30));
		TextFind.setBorder(null);
		TextFind.setOpaque(false);
		//���Ӷ�̬�����¼�
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
		TableUserManage.setFont(new Font("��Բ", Font.PLAIN, 28));
		TableUserManage.setBounds(116, 241, 369, 400);
		TableUserManage.setRowHeight(50);//���ñ��߶�
		JScrollPane scrollpane = new JScrollPane(TableUserManage);
		scrollpane.setBounds(116, 241, 369, 400);
		MainJPanel.add(scrollpane);
		
//����û���ť
		ADS.AddMyButton(MainJPanel, "UI/AddUser.png", 50, 710);
		LabelAddUser=ADS.getLabel();
		ImageAddUser=ADS.getimageicon();
		ButtonAddUser=ADS.getbutton();
		ButtonAddUser.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageAddUser.setImage(ImageAddUser.getImage().getScaledInstance(ImageAddUser.getIconWidth()-1,ImageAddUser.getIconHeight()-1 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelAddUser.setIcon(ImageAddUser);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageAddUser.setImage(ImageAddUser.getImage().getScaledInstance(ImageAddUser.getIconWidth()+1,ImageAddUser.getIconHeight()+1 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelAddUser.setIcon(ImageAddUser);
				  //����û�����
				  
				  try {
					  UserManage.this.setEnabled(false);//���ò��ܿ���
					  AddUser ad;
					  ad = new AddUser(access,UserManage.this);
					  ad.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  
			  }
			});
//�޸���Ϣ��ť
		ADS.AddMyButton(MainJPanel, "UI/EditUserInfo.png", 230, 710);
		LabelEditInfo=ADS.getLabel();
		ImageEditInfo=ADS.getimageicon();
		ButtonEditInfo=ADS.getbutton();
		ButtonEditInfo.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageEditInfo.setImage(ImageEditInfo.getImage().getScaledInstance(ImageEditInfo.getIconWidth()-1,ImageEditInfo.getIconHeight()-1 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelEditInfo.setIcon(ImageEditInfo);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageEditInfo.setImage(ImageEditInfo.getImage().getScaledInstance(ImageEditInfo.getIconWidth()+1,ImageEditInfo.getIconHeight()+1 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelEditInfo.setIcon(ImageEditInfo);
				  int row=TableUserManage.getSelectedRow();
				  int column=TableUserManage.getSelectedColumn();
				  //�����û��ѡ��
				  if(row<0||TableUserManage.getValueAt(row, 0)==null||TableUserManage.getValueAt(row, 0).equals("Administrator"))
				  {
					  JOptionPane.showMessageDialog(null,  "û��ѡ���û�����û����ܱ��޸�!", "����",JOptionPane.ERROR_MESSAGE);
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
//ɾ���û���ť
		ADS.AddMyButton(MainJPanel, "UI/DeleteUser.png", 410, 710);
		LabelDeleteUser=ADS.getLabel();
		ImageDeleteUser=ADS.getimageicon();
		ButtonDeleteUser=ADS.getbutton();
		ButtonDeleteUser.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageDeleteUser.setImage(ImageDeleteUser.getImage().getScaledInstance(ImageDeleteUser.getIconWidth()-1,ImageDeleteUser.getIconHeight()-1 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelDeleteUser.setIcon(ImageDeleteUser);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageDeleteUser.setImage(ImageDeleteUser.getImage().getScaledInstance(ImageDeleteUser.getIconWidth()+1,ImageDeleteUser.getIconHeight()+1 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelDeleteUser.setIcon(ImageDeleteUser);
				  int row=TableUserManage.getSelectedRow();
				  int column=TableUserManage.getSelectedColumn();
				  //�����û��ѡ��
				  if(row<0||TableUserManage.getValueAt(row, 0)==null||TableUserManage.getValueAt(row, 0).equals("Administrator"))
				  {
					  JOptionPane.showMessageDialog(null,  "û��ѡ���û�����û����ܱ�ɾ��!", "����",JOptionPane.ERROR_MESSAGE);
				  }
				  else
				  {
					  //JOptionPane.showMessageDialog(null,  "ȷ��Ҫɾ�����û���ɾ�����ܳ���!", "����",JOptionPane.ERROR_MESSAGE);
					  Object[] options = { "ȷ��", "ȡ��" };   
					  int response=JOptionPane.showOptionDialog(null, "ȷ��Ҫɾ�����û���ɾ�����ܳ���!", "����",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
					  if(response==0)//ȷ��
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
		int row=0;//��
		int rowlen=0;//������
		int column=0;//��
		String content[][];
		rs=access.executeQuery("Select * From PeopleManagement");//��ȡ�����
		while(rs.next())//�ռ�������
		{
			rowlen++;
		}
		if(rowlen<8)//���
		{
			content=new String[8][4];
		}
		else
		{
			content=new String[rowlen][4];
		}
		rs=access.executeQuery("Select * From PeopleManagement");//��ȡ�����
		//��ʼ��ֵ
		for(row=0;rs.next();row++)
		{
			content[row][0]=rs.getString("UserName");
			if(rs.getString("IsAdministrator")=="true")
			{
				content[row][1]="����Ա";
			}
			else
			{
				content[row][1]="����";
			}
			if(rs.getString("IsOnline")=="true")
			{
				content[row][2]="����";
			}
			else
			{
				content[row][2]="����";
			}
			switch(rs.getInt("Garage"))
			{
			case 1:content[row][3]="����һ";break;
			case 2:content[row][3]="�����";break;
			case 3:content[row][3]="������";break;
			case 4:content[row][3]="������";break;
			case 5:content[row][3]="������";break;
			case 6:content[row][3]="������";break;
			default:content[row][3]="ȫ��";break;
			}
		}
		if(row<8)//û�������
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
		int row=0;//��
		int rowlen=0;//������
		int column=0;//��
		String content[][];
		rs=access.executeQuery("Select * From PeopleManagement");//��ȡ�����
		while(rs.next())//�ռ�������
		{
			if(rs.getString("UserName").contains(name))
			{
				rowlen++;
			}
		}
		if(rowlen<8)//���
		{
			content=new String[8][4];
		}
		else
		{
			content=new String[rowlen][4];
		}
		rs=access.executeQuery("Select * From PeopleManagement");//��ȡ�����
		//��ʼ��ֵ
		for(row=0;rs.next();)
		{
			if(rs.getString("UserName").contains(name))
			{
				content[row][0]=rs.getString("UserName");
				if(rs.getString("IsAdministrator")=="true")
				{
					content[row][1]="����Ա";
				}
				else
				{
					content[row][1]="����";
				}
				if(rs.getString("IsOnline")=="true")
				{
					content[row][2]="����";
				}
				else
				{
					content[row][2]="����";
				}
				switch(rs.getInt("Garage"))
				{
				case 1:content[row][3]="����һ";break;
				case 2:content[row][3]="�����";break;
				case 3:content[row][3]="������";break;
				case 4:content[row][3]="������";break;
				case 5:content[row][3]="������";break;
				case 6:content[row][3]="������";break;
				default:content[row][3]="ȫ��";break;
				}
				row++;
			}
		}
		if(row<8)//û�������
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
