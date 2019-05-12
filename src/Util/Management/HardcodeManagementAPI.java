package Util.Management;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;

public class HardcodeManagementAPI extends ManagementAPI{
	private static List hints;
	
	public HardcodeManagementAPI()
	{
		hints=new LinkedList();
		hints.add(new QuestHint("java is programming language"));
		hints.add(new QuestHint("java is good"));
	}
	
	public FlowIncQuest getLogicalQuestion(String user, String journeyRowId)
	{
		String code,out,exp,ans;
		
		code="public class test {\n"+
				"\tpublic static void main(String[] args){\n"+
				"\t\tint x=11;\n"+
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
		
		Reward[] rewards= {new Reward(null,0,10,10,10),new Reward("club",1,0,0,10)};
		
		return new FlowIncQuest(code,out,exp,ans,rewards);
	}
	public FlowIncQuest getSyntaxQuestion(String user, String journeyRowId){return null;}
	public TraceQuest getTraceQuestion(String user, String journeyRowId){return null;}
	public ReorderQuest getReorderQuestion(String user, String journeyRowId) 
	{
		Reward[] rewards={new Reward(null,0,0,0,0)};
		
		return new ReorderQuest("","","",rewards);
	}
	public YesNoTest[] getYesNoTests(String npcId)
	{
		return new YesNoTest[] {new YesNoTest("Is java a program language?",true),new YesNoTest("Is java a script language?",false)};
	}
	public QuestHint getHint(String user, String journeyId) 
	{
		if(hints.size()==0)
			return null;
		QuestHint h=(QuestHint)hints.get(0);
		hints.remove(0);
		return h;
	}
	public String getQuestType(String user, String journeyId) {return null;}
	
	public void setQuestStatus(String journeyRowId, String user, String status) {}
	public void stopTimeCount(String journeyRowId, String user) {}
	public void setLastStartTime(String journeyRowId, String user) {}
	
	public Journey[] getNewJourneyList(String user) {return null;}
	public void startJourney(String user, String journeyId) {}
	public Journey[] getOnDoingJourney(String user, String npcId) {return null;}
	public JourneyRow getJourneyRow(String user, String journeyId) {return null;}
	public JourneyRow getJourneyRow(String journeyRowId) {return null;}
	public boolean touchQuest(String user, String journeyId, String subject) {return false;}
	public void nextJourneyRow(String user, String journeyId) {}
	public boolean hasNextJourneyRow(String user, String journeyId) {return false;}
	public Journey getJourney(String journeyId) {return null;}
}
