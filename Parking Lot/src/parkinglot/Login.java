package parkinglot;



import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class Login extends JFrame{
	private Main main;
	private JTextField TextFieldUserName;
	private ConnectToAccess Access;
	private JPasswordField passwordFieldPassword;
	private int xframeold,yframeold;
	private ResultSet rs=null;
	private JButton ButtonMinimize;
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public Login(final ConnectToAccess access,final Transfer transfer,Main MAIN) throws Exception 
	{
		//super("�������ϵͳ");//���ô������
		//===============================================================һϵ�г�ʼ��
		Access=access;//�������ݿ�����
		rs=Access.executeQuery("Select * From PeopleManagement");//�����ݿ��е�����Ա��Ϣ
		setUndecorated(true);//ȥ�߿�
		setResizable(false);//���ò��ܵ�����С
		setBounds(700, 300, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		getContentPane().setLayout(null);
		
		main=MAIN;
		//===============================================================���ñ���ͼƬ
		ImageIcon bg=new ImageIcon("UI/ParkinglotBackground.jpg");
		JLabel background=new JLabel(bg);
		background.setBounds(0, 0, bg.getIconWidth(),bg.getIconHeight());
		JPanel imagePanel=(JPanel)getContentPane();
		imagePanel.setOpaque(false);
		imagePanel.setLayout(null);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		
		TextFieldUserName = new JTextField();
		TextFieldUserName.setBounds(205, 337, 113, 18);
		TextFieldUserName.setBorder(null);
		TextFieldUserName.setColumns(10);
		getContentPane().add(TextFieldUserName);
		
		passwordFieldPassword = new JPasswordField();
		passwordFieldPassword.setBounds(205, 376, 113, 18);
		passwordFieldPassword.setBorder(null);
		getContentPane().add(passwordFieldPassword);
		
		ImageIcon buttonloginIcon=new ImageIcon("UI/MinimizeButtonNormal.jpg");
		JButton ButtonLogin = new JButton(buttonloginIcon);
		ButtonLogin.setBounds(212, 426, 106, 36);
		//ButtonLogin.setContentAreaFilled(false);
		//ButtonLogin.setBorderPainted(false);
		ButtonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)//��¼ 
			{
				try {
					rs=Access.executeQuery("Select * From PeopleManagement");//�����ݿ��е�����Ա��Ϣ
					String Username=TextFieldUserName.getText();
					String Password=String.valueOf(passwordFieldPassword.getPassword());
					String getUserName;
					while(rs.next())//�����û�
					{
						getUserName=rs.getString("UserName");
						if(Username.equals(getUserName))
						{
							if(Password.equals(rs.getString("Password")))
							{
								transfer.setUserName(getUserName);//�����¼���û���
								if(rs.getString("IsAdministrator").equals("true"))//�ǹ���Ա
								{
									//��¼�ɹ�,����״̬
									transfer.setLoginToMain(1);//����Ա
								}
								else
								{
									transfer.setGuardGarage(rs.getInt("Garage"));
									transfer.setLoginToMain(0);//����
								}
								
								Access.executeUpdate("UPDATE PeopleManagement SET IsOnline = '"+true+"' WHERE UserName='"+getUserName+"'");//���¸��û���״̬-����
								main.Notify();
								Login.this.dispose();//�رմ˴���
								return;
							}
						}
					}
					JOptionPane.showMessageDialog(null,  "�û����������", "����",JOptionPane.ERROR_MESSAGE);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		ButtonLogin.setBounds(212, 426, 70, 36);
		getContentPane().add(ButtonLogin);
		
		//----------------------------------���Ʊ߿�
		JButton Exit = new JButton();
		Exit.setContentAreaFilled(false);
		Exit.setBorder(null);
		Exit.addActionListener(new ActionListener() {//�˳�ʱ�ر����ݿ�
			public void actionPerformed(ActionEvent e) {
				int height=getContentPane().getHeight();
				int width=getContentPane().getWidth();
				for(int x=width;x>0;x-=2)//�ر�Ч��
				{
					Login.this.setSize(x, height);
				}
				try {
					Access.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		Exit.setBounds(474, 0, 26, 27);
		getContentPane().add(Exit);
		
		ButtonMinimize = new JButton();
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.this.setExtendedState(ICONIFIED);
			}
		});
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorder(null);
		ButtonMinimize.setBounds(447, 0, 26, 27);
		getContentPane().add(ButtonMinimize);
		
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
			  Login.this.setLocation(xframenew, yframenew);
			  }
			 });
	}
}
