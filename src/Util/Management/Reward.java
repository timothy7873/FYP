package Util.Management;

public class Reward {
	public String itemName;
	public int count;
	public int exp;
	public double karma;
	
	public Reward(){}
	public Reward(String itemName, int count, int exp, double karma)
	{
		this.itemName=itemName;
		this.count=count;
		this.exp=exp;
		this.karma=karma;
	}
}
