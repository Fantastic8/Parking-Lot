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
	private TableModel tablemodel;//表格模型
	private String TableTitle[]={"卡号","车牌号","是否是月租卡","是否正在计费","车库"};
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
		ShowAll.setFont(new Font("幼圆", Font.PLAIN, 28));
		ShowAll.setBounds(65, 150, 470, 485);
		ShowAll.setRowHeight(46);//设置表格高度
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
					JOptionPane.showMessageDialog(null,  "车牌号不能为空", "错误！",JOptionPane.ERROR_MESSAGE);
					return;
				}
				for(int i=0;i<CardNumber.length();i++)
				{
					if(CardNumber.charAt(i)<'0'||CardNumber.charAt(i)>'9')
					{
						JOptionPane.showMessageDialog(null,  "非法输入", "错误！",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				int row=0;//行
				int rowlen=0;//行数量
				int column=0;//列
				String content[][];
				try {
					rs=access.executeQuery("Select * From Card WHERE CardNumber='"+CardNumber+"'");
					if(rs.next()==false)
					{
						JOptionPane.showMessageDialog(null,  "车库中无此车位", "错误！",JOptionPane.ERROR_MESSAGE);
						return;
					}
					content=new String[1][5];
					//rs=access.executeQuery("Select * From Card");//读取结果集
					//开始赋值
					content[0][0]=String.valueOf(rs.getInt("CardNumber"));//卡号
					content[0][1]=rs.getString("License");//车主姓名
					content[0][2]=String.valueOf(rs.getBoolean("IsRentalCard"));
					content[0][3]=String.valueOf(rs.getBoolean("IsCharging"));
					content[0][4]=rs.getString("Garage");
//					for(row=0;rs.next();)
//					{
//						if(rs.getBoolean("IsRentalCard")==true||rs.getBoolean("IsCharging"))
//						{
//							content[row][0]=String.valueOf(rs.getInt("CardNumber"));//卡号
//							content[row][1]=rs.getString("License");//车主姓名
//							content[row][2]=String.valueOf(rs.getBoolean("IsRentalCard"));
//							content[row][3]=String.valueOf(rs.getBoolean("IsCharging"));
//							content[row][4]=rs.getString("Garage");
//							row++;
//						}
//					}
//					if(row<10)//没有填充满
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
				}//读取结果集
			}
		});
		btnNewButton.setBounds(224, 60, 113, 27);
		getContentPane().add(btnNewButton);
	}
}
