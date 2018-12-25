package Util.Quest;

import org.json.simple.JSONObject;

public class HardcodeQuestGetter implements QuestGetter{
	public FlowIncQuest getLogicalQuestion(String user)
	{
		String code,out,exp,ans;
		
		code="public class test {\n"+
				"\tpublic static void main(String[] args){\n"+
				"\t\tint x=12;\n"+
				"\t\tSystem.out.println(\"x\");\n"+
				"\t}\n"+
				"}";
		ans="public class test {\n"+
				"\tpublic static void main(String[] args){\n"+
				"\t\tint x=12;\n"+
				"\t\tSystem.out.println(x);\n"+
				"\t}\n"+
				"}";
		out="x";
		exp="12";
		
		Reward[] rewards= {new Reward(null,null,10,10),new Reward("club","1",0,0)};
		
		return new FlowIncQuest(code,out,exp,ans,rewards);
	}
	public FlowIncQuest getSyntaxQuestion(String user)
	{
		return new FlowIncQuest();
	}
	public TraceQuest getTraceQuestion(String user)
	{
		return new TraceQuest();
	}
}
