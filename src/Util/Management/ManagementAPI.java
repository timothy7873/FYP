package Util.Management;

public abstract class ManagementAPI {
	public static ManagementAPI api=new PhpManagementAPI("http://127.0.0.1/fyp_php/");
	//public static ManagementAPI api=new HardcodeManagementAPI();
	
	public abstract FlowIncQuest getLogicalQuestion(String npcId, String user);
	public abstract FlowIncQuest getSyntaxQuestion(String npcId, String user);
	public abstract TraceQuest getTraceQuestion(String npcId, String user);
	public abstract ReorderQuest getReorderQuestion(String npcId, String user);
	public abstract YesNoTest[] getYesNoTests(String npcId);
	public abstract QuestHint getHint(String npcId, String user);
	public abstract void setQuestStatus(String npcId, String user, String status);
	public abstract void stopTimeCount(String npcId, String user);
	public abstract void setLastStartTime(String npcId, String user);
	
	public abstract Journey[] getNewJourneyList(String user);
}
