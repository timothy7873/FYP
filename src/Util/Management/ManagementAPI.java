package Util.Management;

public abstract class ManagementAPI {
	public static ManagementAPI api=new PhpManagementAPI("http://127.0.0.1/fyp_php/");
	//public static ManagementAPI api=new HardcodeManagementAPI();
	
	public abstract FlowIncQuest getLogicalQuestion(String user, String journeyId);
	public abstract FlowIncQuest getSyntaxQuestion(String user, String journeyId);
	public abstract TraceQuest getTraceQuestion(String user, String journeyId);
	public abstract ReorderQuest getReorderQuestion(String user, String journeyId);
	public abstract YesNoTest[] getYesNoTests(String npcId);
	public abstract QuestHint getHint(String npcId, String user);
	public abstract void setQuestStatus(String journeyRowId, String user, String status);
	public abstract void setLastStartTime(String journeyRowId, String user);
	public abstract void stopTimeCount(String journeyRowId, String user);
	
	public abstract Journey[] getNewJourneyList(String user);
	public abstract void startJourney(String user, String journeyId);
	public abstract Journey[] getOnDoingJourney(String user, String npcId);
	public abstract JourneyRow getJourneyRow(String user, String journeyId);
	public abstract JourneyRow getJourneyRow(String journeyRowId);
	public abstract String getQuestType(String user, String journeyId);
}
