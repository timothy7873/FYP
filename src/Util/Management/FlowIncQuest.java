package Util.Management;

public class FlowIncQuest{
	public String code;
	public String out;
	public String exp;
	public String ans;
	public Reward[] reward;
	
	public FlowIncQuest() {}
	public FlowIncQuest(String code, String out, String exp, String ans, Reward[] reward)
	{
		this.code=code;
		this.out=out;
		this.exp=exp;
		this.ans=ans;
		this.reward=reward;
	}
}
