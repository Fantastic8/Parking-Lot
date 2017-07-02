package parkinglot;

public class Transfer {
	private int LoginToMain=-1;
	private int GuardGarage;
	private String UserName;
	public void setLoginToMain(int logintomain)
	{
		LoginToMain=logintomain;
	}
	public void setUserName(String Name)
	{
		UserName=Name;
	}
	public String getUserName()
	{
		return UserName;
	}
	public int getLoginToMain()
	{
		return LoginToMain;
	}
	public void setGuardGarage(int garage)
	{
		GuardGarage=garage;
	}
	public int getGuardGarage()
	{
		return GuardGarage;
	}
}
