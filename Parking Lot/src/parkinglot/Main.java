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
			Transfer transfer=new Transfer();//窗口传递类
			//只能创建一个对象,再进行传递	
			//打开登录窗口
			Access.Open();//打开数据库
			//new ShowAllCar(Access).setVisible(true);;
			while(true)
			{
				Login lg=new Login(Access,transfer,Main.this);
				lg.setVisible(true);
				Wait();
				Access.close();//关闭数据库
				Access.Open();//打开数据库
				if(transfer.getLoginToMain()==1)//管理员登录
				{
					AdministratorUI au=new AdministratorUI(Access,transfer,Main.this);
					au.setVisible(true);
					au.Animation();//加载动画效果
					Wait();
				}
				else if(transfer.getLoginToMain()==0)//保安登录
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
//				au.Animation();//加载动画效果
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
