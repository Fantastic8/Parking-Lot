package parkinglot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DayExcel extends JFrame {
	private JPanel MainJPanel;
	private ImageIcon MainBG;
	private JLabel mainbackground;
	
	private int xframeold;
	private int yframeold;
	
	private AddStyle ADS=new AddStyle();//�����ӽ���
	
	private int Garage;
	
	private Time time;//ʱ��
	private int Year;
	private int Month;
	private int Day;
	private JLabel LabelIn;//��ʾ������ǩ
	private JLabel LabelOut;//��ʾ�ܳ����ǩ
	private JLabel LabelCharge;//��ʾ�շѱ�ǩ
	
	//��ť
	private JLabel LabelBack;
	
	private ImageIcon ImageBack;
	
	private JButton ButtonBack;
	
	private ConnectToAccess access;
	private ResultSet rs=null;
	
	public DayExcel(ConnectToAccess Access,final SecurityGuardUI sg,int garage) throws Exception {
		//===============================================================һϵ�г�ʼ��
		access=Access;//�������ݿ�����
		setUndecorated(true);//ȥ�߿�
		setResizable(false);//���ò��ܵ�����С
		setBounds(520, 150, 600, 800);
		this.setSize(800, 800);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Garage=garage;//����
		
		//�������
		MainJPanel=(JPanel)getContentPane();
		
		//���ر���ͼƬ
		MainBG=new ImageIcon("UI/DayExcel.jpg");
		
		//���ر���ͼƬ�ı�ǩ
		mainbackground=new JLabel(MainBG);
		
		//ʱ���ʼ��
		time=new Time();
		Year=time.getYear();
		Month=time.getMonth();
		Day=time.getDay();
		
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
				sg.setEnabled(true);//���Բ���ui����
				DayExcel.this.dispose();
			}
		});
		ButtonExit.setBounds(768, 0, 30, 33);
		getContentPane().add(ButtonExit);
		
		JButton ButtonMinimize = new JButton();//��С����ť
		ButtonMinimize.setContentAreaFilled(false);
		ButtonMinimize.setBorderPainted(false);
		ButtonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DayExcel.this.setExtendedState(ICONIFIED);//������С��
			}
		});
		ButtonMinimize.setBounds(736, 0, 30, 33);
		getContentPane().add(ButtonMinimize);
		//===============================================================Ԫ��
//�������ʾ��ǩ
		LabelIn=new JLabel();
		LabelIn.setBounds(380, 200, 100, 50);
		LabelIn.setFont(new Font("������ͤ��ϸ�ڼ���",0,50));
		MainJPanel.add(LabelIn);
//�ܳ�����ʾ��ǩ
		LabelOut=new JLabel();
		LabelOut.setBounds(380, 360, 100, 50);
		LabelOut.setFont(new Font("������ͤ��ϸ�ڼ���",0,50));
		MainJPanel.add(LabelOut);
//���շ���ʾ��ǩ
		LabelCharge=new JLabel();
		LabelCharge.setBounds(380, 530, 100, 50);
		LabelCharge.setFont(new Font("������ͤ��ϸ�ڼ���",0,50));
		MainJPanel.add(LabelCharge);		
		UpdateData();
		

//���ذ�ť
		ADS.AddMyButton(MainJPanel, "UI/Back.png", 700, 750);
		LabelBack=ADS.getLabel();
		ImageBack=ADS.getimageicon();
		ButtonBack=ADS.getbutton();
		ButtonBack.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mousePressed(MouseEvent e) {
				  ImageBack.setImage(ImageBack.getImage().getScaledInstance(ImageBack.getIconWidth()-2,ImageBack.getIconHeight()-2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelBack.setIcon(ImageBack);
			  }
			  @Override
			  public void mouseReleased(MouseEvent e)
			  {
				  ImageBack.setImage(ImageBack.getImage().getScaledInstance(ImageBack.getIconWidth()+2,ImageBack.getIconHeight()+2 , Image.SCALE_DEFAULT));//�ȱ�������90%
				  LabelBack.setIcon(ImageBack);
				  sg.setEnabled(true);//���Բ���ui����
				  DayExcel.this.dispose();
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
					  DayExcel.this.setLocation(xframenew, yframenew);
					  }
					 });
	}
	void UpdateData() throws Exception//������ʾ����е�����,year�� month�� garage����
	{
		int in=0;
		int out=0;
		int charge=0;
		rs=access.executeQuery("Select * From Schedule");
		while(rs.next())
		{
			if((Year==time.getYearFromString(rs.getString("Time"))&&(Month==time.getMonthFromString(rs.getString("Time")))&&(Garage==rs.getInt("Garage"))&&(Day==time.getDayFromString(rs.getString("Time")))))//����year��month��garage
			{
				if(rs.getBoolean("IsIn"))//�����
				{
					in++;
				}
				else if(rs.getInt("Status")==0)//�ܳ���
				{
					out++;
					charge+=rs.getInt("Charge");
				}
			}
		}
		LabelIn.setText(String.valueOf(in));
		LabelOut.setText(String.valueOf(out));
		LabelCharge.setText(String.valueOf(charge));
	}
}
