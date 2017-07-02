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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.Document;

public class RentManage extends JFrame {
	private JPanel MainJPanel;//���
	
	private ImageIcon MainBG;//���ñ���
	
	private JLabel mainbackground;//���ñ����ı�ǩ
	private ConnectToAccess access;
	private ResultSet rs=null;
	
	private int xframeold;
	private int yframeold;
	
	private AdministratorUI Adu=null;
	
	private AddStyle ADS=new AddStyle();//�����ӽ���
	private JTable TableRentManage;
	
	private TableModel tablemodel;//���ģ��
	private String TableTitle[]={"����","��������","���ƺ�"};
	
	//��ť����
	private JLabel LabelAddRentCard;
	private JLabel LabelDeleteRentCard;
	private JLabel LabelEditInfo;
	
	private ImageIcon ImageAddRentCard;
	private ImageIcon ImageDeleteRentCard;
	private ImageIcon ImageEditInfo;
	
	private JButton ButtonAddRentCard;
	private JButton ButtonDeleteRentCard;
	private JButton ButtonEditInfo;
	
	public RentManage(ConnectToAccess Access,AdministratorUI adu) throws Exception {
		//===============================================================һϵ�г�ʼ��
		Adu=adu;
		access=Access;//�������ݿ�����
		rs=access.executeQuery("Select * From MonthlyRentInfo");//��ȡ�����
		setUndecorated(true);//ȥ�߿�
		setResizable(false);//���ò��ܵ�����С
		setBounds(620, 150, 450, 300);
		this.setSize(600, 800);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�������
		MainJPanel=(JPanel)getContentPane();
		
		//���ر���ͼƬ
		MainBG=new ImageIcon("UI/RentManage.jpg");
		
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
			  RentManage.this.setLocation(xframenew, yframenew);
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
				//���½���
				try {
					Adu.ShowInfo(Adu.getShowGar());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				RentManage.this.dispose();
			}
		});
		ButtonExit.setBounds(568, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton("");//��С����ť
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RentManage.this.setExtendedState(ICONIFIED);//������С��
			}
		});
		ButtonMinimize.setBounds(536, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
//Table
		TableRentManage = new JTable();
		TableRentManage.setBorder(null);
		TableRentManage.setFont(new Font("��Բ", Font.PLAIN, 28));
		TableRentManage.setBounds(65, 150, 470, 485);
		TableRentManage.setRowHeight(46);//���ñ��߶�
		JScrollPane scrollpane = new JScrollPane(TableRentManage);
		scrollpane.setBounds(65, 150, 470, 485);
		MainJPanel.add(scrollpane);
		
//������⿨��ť
		ADS.AddMyButton(MainJPanel, "UI/AddRentCard.png", 40, 710);
		LabelAddRentCard=ADS.getLabel();
		ImageAddRentCard=ADS.getimageicon();
		ButtonAddRentCard=ADS.getbutton();
		ButtonAddRentCard.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageAddRentCard.setImage(ImageAddRentCard.getImage().getScaledInstance(ImageAddRentCard.getIconWidth()-1,ImageAddRentCard.getIconHeight()-1 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelAddRentCard.setIcon(ImageAddRentCard);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageAddRentCard.setImage(ImageAddRentCard.getImage().getScaledInstance(ImageAddRentCard.getIconWidth()+1,ImageAddRentCard.getIconHeight()+1 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelAddRentCard.setIcon(ImageAddRentCard);
				  AddRentCard ard;
				try {
					ard = new AddRentCard(access,RentManage.this);
					ard.setVisible(true);
					RentManage.this.setEnabled(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  }
			});
//�޸����⿨��ť
		ADS.AddMyButton(MainJPanel, "UI/EditRentCard.png", 220, 710);
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
				  //���ѡ�е��Ƿ�Ϊ��Ч����
				  int row=TableRentManage.getSelectedRow();//�����ѡ��
				  //�����û��ѡ��
				  if(row<0||TableRentManage.getValueAt(row, 0)==null)
				  {
					  JOptionPane.showMessageDialog(null,  "��ѡ�����⿨!", "����",JOptionPane.ERROR_MESSAGE);
				  }
				  else
				  {
					  int SelStall=Integer.valueOf(String.valueOf(TableRentManage.getValueAt(row, 0)));//��λ
					  int SelLot=SelStall/1000;//����
					  String SelOwnerName=String.valueOf(TableRentManage.getValueAt(row, 1));//��������
					  String SelLicense=String.valueOf(TableRentManage.getValueAt(row, 2));//���ƺ�
					  EditRentCard erc;
					try {
						erc = new EditRentCard(access,RentManage.this,SelOwnerName,SelLicense,SelStall,SelLot);//����ĸ������ֱ�Ϊ:��������,���ƺ�,��λ,����
						erc.setVisible(true);
						RentManage.this.setEnabled(false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				  }
			  }
			});
//ɾ�����⿨��ť
		ADS.AddMyButton(MainJPanel, "UI/DeleteRentCard.png", 400, 710);
		LabelDeleteRentCard=ADS.getLabel();
		ImageDeleteRentCard=ADS.getimageicon();
		ButtonDeleteRentCard=ADS.getbutton();
		ButtonDeleteRentCard.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageDeleteRentCard.setImage(ImageDeleteRentCard.getImage().getScaledInstance(ImageDeleteRentCard.getIconWidth()-1,ImageDeleteRentCard.getIconHeight()-1 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelDeleteRentCard.setIcon(ImageDeleteRentCard);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageDeleteRentCard.setImage(ImageDeleteRentCard.getImage().getScaledInstance(ImageDeleteRentCard.getIconWidth()+1,ImageDeleteRentCard.getIconHeight()+1 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelDeleteRentCard.setIcon(ImageDeleteRentCard);
				  int row=TableRentManage.getSelectedRow();
				  int column=TableRentManage.getSelectedColumn();
				  //�����û��ѡ��
				  if(row<0||TableRentManage.getValueAt(row, 0)==null)
				  {
					  JOptionPane.showMessageDialog(null,  "��ѡ�����⿨!", "����",JOptionPane.ERROR_MESSAGE);
				  }
				  else
				  {
					  Object[] options = { "ȷ��", "ȡ��" };   
					  int response=JOptionPane.showOptionDialog(null, "ȷ��Ҫɾ�������⿨��ɾ�����ܳ���!", "����",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
					  if(response==0)//ȷ��
					  {
						  try {
							    rs=access.executeQuery("Select * From Card WHERE CardNumber='"+TableRentManage.getValueAt(row, 0)+"'");
							    rs.next();
							    String Licen=rs.getString("License");//��ȡ��ǰ�ĳ��ƺ�
							    int Gara=rs.getInt("Garage");//��ȡ��ǰ�ĳ���
							    //��ȡ��������
							    rs=access.executeQuery("SELECT * From MonthlyRentInfo Where License='"+Licen+"'");
							    rs.next();
							    String Nam=rs.getString("OwnerName");
								access.executeUpdate("Delete From MonthlyRentInfo Where CardNumber='"+TableRentManage.getValueAt(row, 0)+"'");//ɾ�������⿨
								access.executeUpdate("UPDATE Card Set IsRentalCard='"+false+"' WHERE CardNumber='"+TableRentManage.getValueAt(row, 0)+"'");//�������⿨
								access.executeUpdate("UPDATE Card Set License='"+""+"' WHERE CardNumber='"+TableRentManage.getValueAt(row, 0)+"'");//���⿨���ƺ�����
								access.executeUpdate("UPDATE Carport Set IsRent='"+false+"' WHERE ID='"+TableRentManage.getValueAt(row, 0)+"'");//�������⳵λ
								access.executeUpdate("INSERT INTO Schedule VALUES('"+TableRentManage.getValueAt(row, 0)+"','"+new Time().getValidTimetoStore()+"','"+false+"','"+0+"','"+Licen+"','"+true+"','"+Gara+"','"+2+"','"+Nam+"')");//���һ������1-������⿨,2-�����⿨,0-��ͨ������
								UpdateTable();//���±�
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
		rs=access.executeQuery("Select * From MonthlyRentInfo");//��ȡ�����
		while(rs.next())//�ռ�������
		{
			rowlen++;
		}
		if(rowlen<10)//���
		{
			content=new String[10][3];
		}
		else
		{
			content=new String[rowlen][3];
		}
		rs=access.executeQuery("Select * From MonthlyRentInfo");//��ȡ�����
		//��ʼ��ֵ
		for(row=0;rs.next();row++)
		{
			content[row][0]=String.valueOf(rs.getInt("CardNumber"));//����
			content[row][1]=rs.getString("OwnerName");//��������
			content[row][2]=rs.getString("License");//���ƺ�
		}
		if(row<10)//û�������
		{
			for(;row<10;row++)
			{
				content[row][0]=null;
				content[row][1]=null;
				content[row][2]=null;
			}
		}
		tablemodel=new DefaultTableModel(content,TableTitle){
			public boolean isCellEditable(int rowIndex,int columnIndex)
			{
				return false;
			}
		};
		TableRentManage.setModel(tablemodel);
	}
}
