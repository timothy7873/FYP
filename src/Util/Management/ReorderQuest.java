package Util.Management;

public class ReorderQuest {
	public String[] code;
	public String[] ans;
	public String out;
	public Reward[] reward;
	
	public ReorderQuest(String[] code, String[] ans, String out, Reward[] reward)
	{
		this.code=code;
		this.ans=ans;
		this.out=out;
		this.reward=reward;
	}
}
