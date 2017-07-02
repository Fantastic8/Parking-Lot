package parkinglot;

import java.sql.SQLException;

public class Main implements Runnable{
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Main main=new Main();
		new Thread(main).start();
	}
	@Override
	public void run() 
	{
		try {
			ConnectToAccess Access;
			Access = new ConnectToAccess();
			Transfer transfer=new Transfer();//���ڴ�����
			//ֻ�ܴ���һ������,�ٽ��д���	
			//�򿪵�¼����
			Access.Open();//�����ݿ�
			//new ShowAllCar(Access).setVisible(true);;
			while(true)
			{
				Login lg=new Login(Access,transfer,Main.this);
				lg.setVisible(true);
				Wait();
				Access.close();//�ر����ݿ�
				Access.Open();//�����ݿ�
				if(transfer.getLoginToMain()==1)//����Ա��¼
				{
					AdministratorUI au=new AdministratorUI(Access,transfer,Main.this);
					au.setVisible(true);
					au.Animation();//���ض���Ч��
					Wait();
				}
				else if(transfer.getLoginToMain()==0)//������¼
				{
					SecurityGuardUI sg=new SecurityGuardUI(Access,transfer,transfer.getGuardGarage(),Main.this);
					sg.setVisible(true);
					Wait();
				}
				else
				{
					System.out.println("ERROR!");
					Wait();
				}
//				AdministratorUI au=new AdministratorUI(Access,transfer,Main.this);
//				au.setVisible(true);
//				au.Animation();//���ض���Ч��
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized void Wait() throws InterruptedException
	{
		wait();
	}
	public synchronized void Notify()
	{
		notify();
	}
}
