package Util.Management;

public interface ManagementAPI {
	public FlowIncQuest getLogicalQuestion(String npcId, String user);
	public FlowIncQuest getSyntaxQuestion(String npcId, String user);
	public TraceQuest getTraceQuestion(String npcId, String user);
	public YesNoTest[] getYesNoTests(String subject);
	public QuestHint getHint(String npcId, String user);
	public void setQuestStatus(String npcId, String user, String status);
	public void stopTimeCount(String npcId, String user);
}
