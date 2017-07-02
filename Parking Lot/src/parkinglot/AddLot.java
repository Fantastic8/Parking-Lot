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
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JComboBox;

import java.awt.Font;
import javax.swing.DefaultComboBoxModel;

public class AddLot extends JFrame {
	private ConnectToAccess access;
	private ResultSet rs=null;
	private JPanel MainJPanel;
	private ImageIcon MainBG;
	private JLabel mainbackground;
	private int xframeold;
	private int yframeold;
	
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public AddLot(ConnectToAccess Access,final AdministratorUI adu,final int garage) {
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
		MainBG=new ImageIcon("UI/AddLotFrame.jpg");
		
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
				AddLot.this.dispose();
			}
		});
		ButtonExit.setBounds(568, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton("");//��С����ť
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddLot.this.setExtendedState(ICONIFIED);//������С��
			}
		});
		ButtonMinimize.setBounds(536, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		
		//-------------�������Ԫ��
		String Floor[]={"1","2","3","4","5","6"};
		final JComboBox ComboBoxFloor = new JComboBox(Floor);
		ComboBoxFloor.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		ComboBoxFloor.setBounds(243, 264, 107, 24);
		//ComboBoxFloor.addItem(makeObj(""));
		getContentPane().add(ComboBoxFloor);
		
		String Stall[]={"20","40","60","80","100","120","140","160","180","200"};
		final JComboBox ComboBoxStall = new JComboBox(Stall);
		ComboBoxStall.setModel(new DefaultComboBoxModel(new String[] {"5", "10", "15", "20", "25", "30"}));
		ComboBoxStall.setFont(new Font("����", Font.PLAIN, 15));
		ComboBoxStall.setBounds(227, 419, 149, 24);
		getContentPane().add(ComboBoxStall);
		
		final JTextField TextField1=new JTextField();//1Сʱ��
		TextField1.setBounds(250, 555, 80, 25);
		MainJPanel.add(TextField1);
		
		final JTextField TextField2=new JTextField();//1-5Сʱ
		TextField2.setBounds(250, 595, 80, 25);
		MainJPanel.add(TextField2);
		
		final JTextField TextField3=new JTextField();//5-10Сʱ
		TextField3.setBounds(250, 640, 80, 25);
		MainJPanel.add(TextField3);
		
		final JTextField TextField4=new JTextField();//10-24Сʱ
		TextField4.setBounds(250, 680, 80, 25);
		MainJPanel.add(TextField4);
		
		//------------ȷ����ť��ȡ����ť
		final ImageIcon ImageOK=new ImageIcon("UI/OK.png");//���ȷ����ť
		final JLabel LabelOK=new JLabel(ImageOK);
		LabelOK.setBounds(180, 730, ImageOK.getIconWidth(), ImageOK.getIconHeight());
		MainJPanel.add(LabelOK);
		final JButton ButtonOK=new JButton();//ȷ����Ӧ��ť
		ButtonOK.setContentAreaFilled(false);
		ButtonOK.setBorderPainted(false);
		ButtonOK.setBounds(180, 730, ImageOK.getIconWidth(), ImageOK.getIconHeight());
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
				  int Floor=ComboBoxFloor.getSelectedIndex()+1;//¥��
				  int Stall=(ComboBoxStall.getSelectedIndex()+1)*5;//��λ
				  //���
				  if(Floor<=0)
				  {
					  JOptionPane.showMessageDialog(null,  "��ѡ��¥��!", "����",JOptionPane.ERROR_MESSAGE);
				  }
				  if(Stall<=0)
				  {
					  JOptionPane.showMessageDialog(null,  "��ѡ��ÿ��ĳ�λ����!", "����",JOptionPane.ERROR_MESSAGE);
				  }
				  if(TextField1.getText().isEmpty()||TextField2.getText().isEmpty()||TextField3.getText().isEmpty()||TextField4.getText().isEmpty())
				  {
					  JOptionPane.showMessageDialog(null,  "�շѹ�����Ϊ��!", "����",JOptionPane.ERROR_MESSAGE);
					  return;
				  }
				  //�������޷Ƿ�����
				  for(int i=0;i<TextField1.getText().length();i++)
				  {
					  if(TextField1.getText().charAt(i)>57||TextField1.getText().charAt(i)<48)
					  {
						  JOptionPane.showMessageDialog(null,  "�Ƿ�����!", "����",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  for(int i=0;i<TextField2.getText().length();i++)
				  {
					  if(TextField2.getText().charAt(i)>57||TextField2.getText().charAt(i)<48)
					  {
						  JOptionPane.showMessageDialog(null,  "�Ƿ�����!", "����",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  for(int i=0;i<TextField3.getText().length();i++)
				  {
					  if(TextField3.getText().charAt(i)>57||TextField3.getText().charAt(i)<48)
					  {
						  JOptionPane.showMessageDialog(null,  "�Ƿ�����!", "����",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  for(int i=0;i<TextField4.getText().length();i++)
				  {
					  if(TextField4.getText().charAt(i)>57||TextField4.getText().charAt(i)<48)
					  {
						  JOptionPane.showMessageDialog(null,  "�Ƿ�����!", "����",JOptionPane.ERROR_MESSAGE);
						  return;
					  }
				  }
				  //��ʼ����
				  int charge1=Integer.parseInt(TextField1.getText());//�۸�1
				  int charge2=Integer.parseInt(TextField2.getText());//�۸�2
				  int charge3=Integer.parseInt(TextField3.getText());//�۸�3
				  int charge4=Integer.parseInt(TextField4.getText());//�۸�4
				  
				try {
					access.executeUpdate("INSERT INTO Configuration VALUES("+garage+","+Floor+","+Stall+","+charge1+","+charge2+","+charge3+","+charge4+")");//�����������ݿ�
					//access.ReadyForPtmt("INSERT INTO Carport VALUES(?,?,?)");//׼����������
					for(int f=1;f<=Floor;f++)
					{
						for(int s=1;s<=Stall;s++)
						{
							access.executeUpdate("INSERT INTO Carport VALUES("+(garage*1000+f*100+s)+",true,"+garage+",false)");//���³�λ���ݿ�
							access.executeUpdate("INSERT INTO Card VALUES("+(garage*1000+f*100+s)+",null,"+false+","+false+","+garage+")");
						}
					}
					access.close();
					access.Open();
					//ִ������
					//access.getPtmt().executeBatch();
					//access.executeUpdate("INSERT INTO Card VALUSE()");//���¿����ݿ�
					//�������
				} catch (SQLException e1 ) {
					// TODO Auto-generated catch block
					e1.getNextException();
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
				adu.setEnabled(true);
				AddLot.this.dispose();
				
				adu.UpdateData();//����adu������
			  }
			});
		MainJPanel.add(ButtonOK);
		
		final ImageIcon ImageCancel=new ImageIcon("UI/Cancel.png");//���ȡ����ť
		final JLabel LabelCancel=new JLabel(ImageCancel);
		LabelCancel.setBounds(350, 730, ImageCancel.getIconWidth(), ImageCancel.getIconHeight());
		MainJPanel.add(LabelCancel);
		final JButton ButtonCancel=new JButton();//ȡ����Ӧ��ť
		ButtonCancel.setContentAreaFilled(false);
		ButtonCancel.setBorderPainted(false);
		ButtonCancel.setBounds(350, 730, ImageCancel.getIconWidth(), ImageCancel.getIconHeight());
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
				  adu.setEnabled(true);//���Բ���ui����
				  AddLot.this.dispose();
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
			  AddLot.this.setLocation(xframenew, yframenew);
			  }
			 });
	}
		
}
