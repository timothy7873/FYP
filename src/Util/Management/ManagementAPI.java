package Util.Management;

public abstract class ManagementAPI {
	public static ManagementAPI api=new PhpManagementAPI("http://127.0.0.1/fyp_php/API/");
	//public static ManagementAPI api=new HardcodeManagementAPI();
	
	public abstract FlowIncQuest getLogicalQuestion(String user, String journeyRowId);
	public abstract FlowIncQuest getSyntaxQuestion(String user, String journeyRowId);
	public abstract TraceQuest getTraceQuestion(String user, String journeyRowId);
	public abstract ReorderQuest getReorderQuestion(String user, String journeyRowId);
	public abstract YesNoTest[] getYesNoTests(String npcId);
	public abstract QuestHint getHint(String user, String journeyId);
	public abstract void setQuestStatus(String journeyRowId, String user, String status);
	public abstract void setLastStartTime(String journeyRowId, String user);
	public abstract void stopTimeCount(String journeyRowId, String user);
	
	public abstract Journey[] getNewJourneyList(String user);
	public abstract void startJourney(String user, String journeyId);
	public abstract Journey[] getOnDoingJourney(String user, String npcId);
	public abstract JourneyRow getJourneyRow(String user, String journeyId);
	public abstract JourneyRow getJourneyRow(String journeyRowId);
	public abstract String getQuestType(String user, String journeyId);
	public abstract boolean touchQuest(String user, String journeyId, String subject);
	public abstract void nextJourneyRow(String user, String journeyId);
	public abstract boolean hasNextJourneyRow(String user, String journeyId);
	public abstract Journey getJourney(String journeyId);
}
