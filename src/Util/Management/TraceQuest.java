package Util.Management;

public class TraceQuest {
	public String code;
	public String out;
	public String ans;
	public Reward[] reward;
	
	public TraceQuest() {}
	public TraceQuest(String code, String out, String ans, Reward[] reward)
	{
		this.code=code;
		this.out=out;
		this.ans=ans;
		this.reward=reward;
	}
}
