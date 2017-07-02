package parkinglot;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowAllCar extends JFrame {
	private ConnectToAccess access;
	private ResultSet rs=null;
	private TableModel tablemodel;//���ģ��
	private String TableTitle[]={"����","���ƺ�","�Ƿ������⿨","�Ƿ����ڼƷ�","����"};
	private JTable ShowAll;
	private JTextField textField;
	public ShowAllCar(ConnectToAccess Access) throws Exception {
		access=Access;
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		//Table
		ShowAll = new JTable();
		ShowAll.setBorder(null);
		ShowAll.setFont(new Font("��Բ", Font.PLAIN, 28));
		ShowAll.setBounds(65, 150, 470, 485);
		ShowAll.setRowHeight(46);//���ñ��߶�
		JScrollPane scrollpane = new JScrollPane(ShowAll);
		scrollpane.setBounds(65, 150, 470, 485);
		getContentPane().add(scrollpane);
		
		textField = new JTextField();
		textField.setBounds(65, 61, 86, 24);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String CardNumber=textField.getText();
				if(CardNumber.equals(""))
				{
					JOptionPane.showMessageDialog(null,  "���ƺŲ���Ϊ��", "����",JOptionPane.ERROR_MESSAGE);
					return;
				}
				for(int i=0;i<CardNumber.length();i++)
				{
					if(CardNumber.charAt(i)<'0'||CardNumber.charAt(i)>'9')
					{
						JOptionPane.showMessageDialog(null,  "�Ƿ�����", "����",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				int row=0;//��
				int rowlen=0;//������
				int column=0;//��
				String content[][];
				try {
					rs=access.executeQuery("Select * From Card WHERE CardNumber='"+CardNumber+"'");
					if(rs.next()==false)
					{
						JOptionPane.showMessageDialog(null,  "�������޴˳�λ", "����",JOptionPane.ERROR_MESSAGE);
						return;
					}
					content=new String[1][5];
					//rs=access.executeQuery("Select * From Card");//��ȡ�����
					//��ʼ��ֵ
					content[0][0]=String.valueOf(rs.getInt("CardNumber"));//����
					content[0][1]=rs.getString("License");//��������
					content[0][2]=String.valueOf(rs.getBoolean("IsRentalCard"));
					content[0][3]=String.valueOf(rs.getBoolean("IsCharging"));
					content[0][4]=rs.getString("Garage");
//					for(row=0;rs.next();)
//					{
//						if(rs.getBoolean("IsRentalCard")==true||rs.getBoolean("IsCharging"))
//						{
//							content[row][0]=String.valueOf(rs.getInt("CardNumber"));//����
//							content[row][1]=rs.getString("License");//��������
//							content[row][2]=String.valueOf(rs.getBoolean("IsRentalCard"));
//							content[row][3]=String.valueOf(rs.getBoolean("IsCharging"));
//							content[row][4]=rs.getString("Garage");
//							row++;
//						}
//					}
//					if(row<10)//û�������
//					{
//						for(;row<10;row++)
//						{
//							content[row][0]=null;
//							content[row][1]=null;
//							content[row][2]=null;
//							content[row][3]=null;
//							content[row][4]=null;
//						}
//					}
					tablemodel=new DefaultTableModel(content,TableTitle){
						public boolean isCellEditable(int rowIndex,int columnIndex)
						{
							return false;
						}
					};
					ShowAll.setModel(tablemodel);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//��ȡ�����
			}
		});
		btnNewButton.setBounds(224, 60, 113, 27);
		getContentPane().add(btnNewButton);
	}
}
