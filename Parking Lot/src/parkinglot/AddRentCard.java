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
import javax.swing.JTextField;
import javax.swing.text.Document;

public class AddRentCard extends JFrame {
	private ConnectToAccess access;
	private ResultSet rs=null;
	private JPanel MainJPanel;
	private ImageIcon MainBG;
	private JLabel mainbackground;
	private int xframeold;
	private int yframeold;
	
	private int SelectedLot;
	private int SelectedStall;
	
	private JComboBox BoxStall;
	private JComboBox BoxLot;
	private ComboBoxModel cbmlot;
	private ComboBoxModel cbmstall;
	
	private JTextField TextName;
	private JTextField TextLicense;
	private JTextField TextCharge;
	
	private int garagenum;//�ܳ���ĸ���
	private int stallnum;//�ó����ܹ��ж��ٸ���λ
	
	private String SLot[];//���ó�������
	private String SStall[];//���ó�λ����
	
	public AddRentCard(ConnectToAccess Access,final RentManage rm) throws Exception {
		//===============================================================һϵ�г�ʼ��
		access=Access;//�������ݿ�����
		setUndecorated(true);//ȥ�߿�
		setResizable(false);//���ò��ܵ�����С
		setBounds(620, 250, 600, 600);
		this.setSize(600, 600);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�������
		MainJPanel=(JPanel)getContentPane();
		
		//���ر���ͼƬ
		MainBG=new ImageIcon("UI/AddRentCard.jpg");
		
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
				rm.setEnabled(true);//���Բ���ui����
				AddRentCard.this.dispose();
			}
		});
		ButtonExit.setBounds(568, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//��С����ť
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddRentCard.this.setExtendedState(ICONIFIED);//������С��
			}
		});
		ButtonMinimize.setBounds(536, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		
		//-------------�������Ԫ��
//��λѡ��
		stallnum=0;
		BoxStall=new JComboBox();
		BoxStall.setBounds(270, 229, 150, 25);
		MainJPanel.add(BoxStall);
		
//����ѡ��
		garagenum=0;
		BoxLot=new JComboBox();
		BoxLot.setBounds(270, 153, 150, 25);
		rs=access.executeQuery("Select * From Configuration");//��ȡ������Ϣ
		for(garagenum=0;rs.next();garagenum++);//�����ж��ٸ�����������⿨�ĳ���
		SLot=new String[garagenum];
		//ΪSLot��ֵ
		for(int lot=0;lot<garagenum;lot++)
		{
			SLot[lot]=String.valueOf(lot+1);
		}
		cbmlot=new DefaultComboBoxModel(SLot);
		BoxLot.setModel(cbmlot);
		BoxLot.setSelectedIndex(-1);//����Ĭ�ϲ�ѡ
		BoxLot.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED)//ִֻ��һ��
				{
					SelectedLot=BoxLot.getSelectedIndex()+1;
					//System.out.println("ѡ��"+SelectedLot);
					try {
						rs=access.executeQuery("Select * From Carport Where Garage='"+SelectedLot+"'");
						//���㳵λ�ĸ���
						for(stallnum=0;rs.next();)
						{
							if(rs.getBoolean("IsRent")==true)//������������ĳ�λ
							{
								continue;
							}
							stallnum++;
						}
						SStall=new String[stallnum];
						//��ʼ��ֵ
						rs=access.executeQuery("Select * From Carport Where Garage='"+SelectedLot+"'");
						for(int i=0;rs.next();)
						{
							if(rs.getBoolean("IsRent")==true)//����ʾ����ĳ�λ
							{
								continue;
							}
							SStall[i]=String.valueOf(rs.getInt("ID"));
							i++;
						}
						cbmstall=new DefaultComboBoxModel(SStall);
						BoxStall.setModel(cbmstall);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}});
		MainJPanel.add(BoxLot);
		
//��ӳ��������ı���
		TextName=new JTextField();
		TextName.setBounds(233, 315, 217, 30);
		TextName.setBorder(null);
		TextName.setOpaque(false);
		TextName.setFont(new Font("��Բ", Font.PLAIN, 20));
		MainJPanel.add(TextName);
		
//��ӳ��ƺ��ı���
		TextLicense=new JTextField();
		TextLicense.setBounds(233, 393, 218, 30);
		TextLicense.setBorder(null);
		TextLicense.setOpaque(false);
		TextLicense.setFont(new Font("��Բ", Font.PLAIN, 20));
		MainJPanel.add(TextLicense);
//����շ��ı���
		TextCharge=new JTextField();
		TextCharge.setBounds(233, 457, 218, 30);
		TextCharge.setBorder(null);
		TextCharge.setOpaque(false);
		TextCharge.setFont(new Font("��Բ", Font.PLAIN, 20));
		MainJPanel.add(TextCharge);
		
		
		//------------ȷ����ť��ȡ����ť
		final ImageIcon ImageOK=new ImageIcon("UI/OK.png");//���ȷ����ť
		final JLabel LabelOK=new JLabel(ImageOK);
		LabelOK.setBounds(180, 530, ImageOK.getIconWidth(), ImageOK.getIconHeight());
		MainJPanel.add(LabelOK);
		final JButton ButtonOK=new JButton();//ȷ����Ӧ��ť
		ButtonOK.setContentAreaFilled(false);
		ButtonOK.setBorderPainted(false);
		ButtonOK.setBounds(180, 530, ImageOK.getIconWidth(), ImageOK.getIconHeight());
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
				  
				  int Lot=BoxLot.getSelectedIndex();
				  int Stall=BoxStall.getSelectedIndex();
				  String Name=TextName.getText();
				  String License=TextLicense.getText();
				//���
				  if(Lot<0)
				  {
					  JOptionPane.showMessageDialog(null,  "��ѡ�񳵿⣡", "����",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  if(Stall<0)
				  {
					  JOptionPane.showMessageDialog(null,  "��ѡ��λ��", "����",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  if(Name.equals(""))
				  {
					  JOptionPane.showMessageDialog(null,  "�����복������!", "����",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  if(License.equals(""))
				  {
					  JOptionPane.showMessageDialog(null,  "�����복�ƺ�!", "����",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  if(Name.contains("'")||Name.contains("\"")||Name.contains("?")||Name.contains("!")||Name.contains("`")||Name.contains("~"))
				  {
					  JOptionPane.showMessageDialog(null,  "���������а����Ƿ��ַ���", "����",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  if(TextCharge.getText().equals(""))
				  {
					  JOptionPane.showMessageDialog(null,  "���շ�!", "����",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  for(int i=0;i<TextCharge.getText().length();i++)
				  {
					  if(!(TextCharge.getText().charAt(i)>='0'&&TextCharge.getText().charAt(i)<='9'))
					  {
						  JOptionPane.showMessageDialog(null,  "�շѴ���!", "����",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  for(int i=0;i<License.length();i++)
				  {
					  if(!((License.charAt(i)>='a'&&License.charAt(i)<='z')||(License.charAt(i)>='A'&&License.charAt(i)<='Z')||(License.charAt(i)>='0'&&License.charAt(i)<='9')))
					  {
						  JOptionPane.showMessageDialog(null,  "���ƺ��а����Ƿ��ַ���", "����",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  //������
				  SelectedStall=Integer.valueOf(BoxStall.getSelectedItem().toString());
				  try {
					access.executeUpdate("INSERT INTO MonthlyRentInfo VALUES('"+Name+"','"+License+"','"+SelectedStall+"','"+SelectedLot+"')");
					access.executeUpdate("UPDATE Card SET License='"+License+"' WHERE CardNumber='"+SelectedStall+"'");
					access.executeUpdate("UPDATE Card SET IsRentalCard='"+true+"' WHERE CardNumber='"+SelectedStall+"'");
					access.executeUpdate("UPDATE Carport SET IsRent='"+true+"' WHERE ID='"+SelectedStall+"'");
					access.executeUpdate("INSERT INTO Schedule VALUES('"+SelectedStall+"','"+new Time().getValidTimetoStore()+"','"+false+"','"+Integer.valueOf(TextCharge.getText())+"','"+License+"','"+true+"','"+SelectedLot+"','"+1+"','"+Name+"')");//�����ڶ�������1-������⿨,2-�����⿨,0-��ͨ������
					rm.UpdateTable();
					rm.setEnabled(true);
					AddRentCard.this.dispose();
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
			});
		MainJPanel.add(ButtonOK);
		
		final ImageIcon ImageCancel=new ImageIcon("UI/Cancel.png");//���ȡ����ť
		final JLabel LabelCancel=new JLabel(ImageCancel);
		LabelCancel.setBounds(350, 530, ImageCancel.getIconWidth(), ImageCancel.getIconHeight());
		MainJPanel.add(LabelCancel);
		final JButton ButtonCancel=new JButton();//ȡ����Ӧ��ť
		ButtonCancel.setContentAreaFilled(false);
		ButtonCancel.setBorderPainted(false);
		ButtonCancel.setBounds(350, 530, ImageCancel.getIconWidth(), ImageCancel.getIconHeight());
		ButtonCancel.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageCancel.setImage(ImageCancel.getImage().getScaledInstance(ImageCancel.getIconWidth()-2,ImageCancel.getIconHeight()-2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelCancel.setIcon(ImageCancel);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageCancel.setImage(ImageCancel.getImage().getScaledInstance(ImageCancel.getIconWidth()+2,ImageCancel.getIconHeight()+2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelCancel.setIcon(ImageCancel);
				  rm.setEnabled(true);//���Բ���ui����
				  AddRentCard.this.dispose();
			  }
			});
		MainJPanel.add(ButtonCancel);
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
			  AddRentCard.this.setLocation(xframenew, yframenew);
			  }
			 });
	}
}
