package parkinglot;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.*;

public class AddStyle {
	private JLabel returnlabel=null;
	private JButton returnbutton=null;
	private ImageIcon returnimageicon=null;
	
	private Transfer transfer;//ֵ������
	public void AddBorderButton(final JPanel jp,final JFrame jf,final ConnectToAccess access,final Transfer transfer)
	{
		JButton ButtonExit = new JButton();//�رհ�ť
		ButtonExit.setContentAreaFilled(false);
		ButtonExit.setBorderPainted(false);
		ButtonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int height=jp.getHeight();
				int width=jp.getWidth();
				for(int y=height;y>0;y-=5)//�ر�Ч��
				{
					jf.setSize(width, y);
				}
				try {
					access.executeUpdate("UPDATE PeopleManagement SET IsOnline = false WHERE UserName=+'"+transfer.getUserName()+"'");
					access.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//�ر����ݿ�
				catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//���û�Ϊ����״̬
				System.exit(0);//���ùر�
			}
		});
		ButtonExit.setBounds(jf.getWidth()-32, 0, 30, 33);
		jp.add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//��С����ť
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jf.setExtendedState(jf.ICONIFIED);//������С��
			}
		});
		ButtonMinimize.setBounds(jf.getWidth()-64, 0, 30, 33);
		jp.add(ButtonMinimize);
	}
	public void AddMyButton(JPanel jp,String imageaddress,int x,int y)
	{
		returnimageicon=new ImageIcon(imageaddress);//���ͼƬ
		returnlabel=new JLabel(returnimageicon);//ͼƬ����-label
		returnlabel.setBounds(x, y, returnimageicon.getIconWidth(), returnimageicon.getIconHeight());
		jp.add(returnlabel);
		returnbutton=new JButton();//���͸����ť
		returnbutton.setContentAreaFilled(false);
		returnbutton.setBorderPainted(false);
		returnbutton.setBounds(x, y, returnimageicon.getIconWidth(), returnimageicon.getIconHeight());
		jp.add(returnbutton);
	}
	public JLabel getLabel()
	{
		return returnlabel;
	}
	public JButton getbutton()
	{
		return returnbutton;
	}
	public ImageIcon getimageicon()
	{
		return returnimageicon;
	}
}
