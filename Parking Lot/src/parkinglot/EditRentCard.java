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
	
	private AddStyle ADS=new AddStyle();//�����ӽ���
	
	private JTextField TextOwnerName;//���������ı���
	private JTextField TextLicense;//���ƺ��ı���
	private JComboBox ComboBoxStall;//��λѡ���
	private ComboBoxModel BoxModelStall;
	private JLabel LabelLot;//�����ǩ-���ⲻ�ܸ���
	
	private int Stallnum;//��¼Garage���ж��ٳ�λ
	private String[] StallInfo;//��̬�洢��λ��Ϣ

	//��ť
	private JLabel LabelOK;
	
	private ImageIcon ImageOK;
	
	private JButton ButtonOK;
	private ConnectToAccess access;
	private ResultSet rs;
	public EditRentCard(ConnectToAccess Access,final RentManage rm,String Name,String License,final int Stall,int Garage) throws Exception {
		//===============================================================һϵ�г�ʼ��
		access=Access;//�������ݿ�����
		setUndecorated(true);//ȥ�߿�
		setResizable(false);//���ò��ܵ�����С
		setBounds(720, 370, 400, 300);
		this.setSize(400, 300);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�������
		MainJPanel=(JPanel)getContentPane();
		
		//���ر���ͼƬ
		MainBG=new ImageIcon("UI/EditRentCard.jpg");
		
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
				EditRentCard.this.dispose();
			}
		});
		ButtonExit.setBounds(368, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//��С����ť
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditRentCard.this.setExtendedState(ICONIFIED);//������С��
			}
		});
		ButtonMinimize.setBounds(336, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		//===============================================================Ԫ��
//���������ı���
		TextOwnerName=new JTextField();
		TextOwnerName.setBounds(190, 82, 160,30);
		TextOwnerName.setBorder(null);
		TextOwnerName.setOpaque(false);
		TextOwnerName.setFont(new Font("������ͤ��ϸ�ڼ���",0,15));
		TextOwnerName.setText(Name);
		MainJPanel.add(TextOwnerName);
		
//���ƺ��ı���
		TextLicense=new JTextField();
		TextLicense.setBounds(190, 136, 160,30);
		TextLicense.setBorder(null);
		TextLicense.setOpaque(false);
		TextLicense.setFont(new Font("������ͤ��ϸ�ڼ���",0,15));
		TextLicense.setText(License);
		MainJPanel.add(TextLicense);
		
//��λѡ���
		ComboBoxStall=new JComboBox();
		ComboBoxStall.setBounds(186, 183, 168, 30);
		rs=access.executeQuery("Select * From Carport WHERE Garage='"+Garage+"'");
		for(Stallnum=0;rs.next();)//����ó���ĳ�λ����,���������⳵λ,�������Լ�
		{
			if(rs.getBoolean("ISRent")==true&&!(rs.getInt("ID")==Stall))
			{
				continue;
			}
			Stallnum++;
		}
		StallInfo=new String[Stallnum];
		//��ʼΪStall��ֵ
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
		ComboBoxStall.setSelectedItem(String.valueOf(Stall));//����Ĭ��ѡ����
		MainJPanel.add(ComboBoxStall);
		
		
//�����ǩ--���ⲻ�ܸ���
		LabelLot=new JLabel();
		LabelLot.setText(String.valueOf(Garage));
		LabelLot.setBounds(190, 215, 100, 70);
		LabelLot.setFont(new Font("������ͤ��ϸ�ڼ���",0,25));
		MainJPanel.add(LabelLot);
		
//ȷ����ť
		ADS.AddMyButton(MainJPanel, "UI/OK.png", 280, 240);
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
				  //������޷Ƿ�����
				  int SStall=ComboBoxStall.getSelectedIndex();
				  String Name=TextOwnerName.getText();//��������
				  String License=TextLicense.getText();//���ƺ�
				//���
				  if(SStall<0)
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
				  for(int i=0;i<License.length();i++)
				  {
					  if(!((License.charAt(i)>='a'&&License.charAt(i)<='z')||(License.charAt(i)>='A'&&License.charAt(i)<='Z')||(License.charAt(i)>='0'&&License.charAt(i)<='9')))
					  {
						  JOptionPane.showMessageDialog(null,  "���ƺ��а����Ƿ��ַ���", "����",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  //������
				  int SelectedStall=Integer.valueOf(ComboBoxStall.getSelectedItem().toString());//ID
				  System.out.println(SelectedStall);
				  try {
					access.executeUpdate("UPDATE MonthlyRentInfo SET OwnerName='"+Name+"' WHERE CardNumber='"+Stall+"'");//�޸����⿨��Ϣ���еĳ��������ͳ��ƺ���Ϣ
					access.executeUpdate("UPDATE MonthlyRentInfo SET License='"+License+"' WHERE CardNumber='"+Stall+"'");//�޸����⿨��Ϣ���еĳ��������ͳ��ƺ���Ϣ
					access.executeUpdate("UPDATE MonthlyRentInfo SET CardNumber='"+SelectedStall+"' WHERE CardNumber='"+Stall+"'");//�޸����⿨��Ϣ���еĳ��������ͳ��ƺ���Ϣ
					access.executeUpdate("UPDATE Carport SET IsRent='"+false+"' WHERE ID='"+Stall+"'");//��Carport��Stall��Isrent��Ϊfalse,SelectedStall��isrentΪtrue
					access.executeUpdate("UPDATE Carport SET IsRent='"+true+"' WHERE ID='"+SelectedStall+"'");//��Carport��Stall��Isrent��Ϊfalse,SelectedStall��isrentΪtrue
					rs=access.executeQuery("Select * From Carport WHERE ID='"+Stall+"'");//��carport��isavaliable�¾ɶԻ�
					rs.next();//�ƶ���������
					boolean oldavaliable=rs.getBoolean("IsAvaliable");
					rs=access.executeQuery("Select * From Carport WHERE ID='"+SelectedStall+"'");//��carport��isavaliable�¾ɶԻ�
					rs.next();//�ƶ���������
					boolean newavaliable=rs.getBoolean("IsAvaliable");
					access.executeUpdate("UPDATE Carport SET IsAvaliable='"+newavaliable+"' WHERE ID='"+Stall+"'");//���жԻ�
					access.executeUpdate("UPDATE Carport SET IsAvaliable='"+oldavaliable+"' WHERE ID='"+SelectedStall+"'");//���жԻ�
					//carport�Ի����
					//��Card��license ��IsRentalCard��IsCharging�¾ɶԻ�
					rs=access.executeQuery("Select * From Card WHERE CardNumber='"+Stall+"'");
					rs.next();//ָ��������
					boolean oldIsCharging=rs.getBoolean("IsCharging");
					rs=access.executeQuery("Select * From Card WHERE CardNumber='"+SelectedStall+"'");
					rs.next();
					boolean newIsCharging=rs.getBoolean("IsCharging");
					access.executeUpdate("UPDATE Card SET License='"+""+"' WHERE CardNumber='"+Stall+"'");//�ɵĳ��ƺ�Ϊ��--����պ�����,��ֹ��λ��ͬ�����
					access.executeUpdate("UPDATE Card SET License='"+License+"' WHERE CardNumber='"+SelectedStall+"'");
					
					access.executeUpdate("UPDATE Card SET IsCharging='"+oldIsCharging+"' WHERE CardNumber='"+SelectedStall+"'");
					access.executeUpdate("UPDATE Card SET IsCharging='"+newIsCharging+"' WHERE CardNumber='"+Stall+"'");
					access.executeUpdate("UPDATE Card SET IsRentalCard='"+false+"' WHERE CardNumber='"+Stall+"'");
					access.executeUpdate("UPDATE Card SET IsRentalCard='"+true+"' WHERE CardNumber='"+SelectedStall+"'");
					//���ݽ������
					rm.setEnabled(true);
					rm.UpdateTable();//��������
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
					  EditRentCard.this.setLocation(xframenew, yframenew);
					  }
					 });
	}
}
