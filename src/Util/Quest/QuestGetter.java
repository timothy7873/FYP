package Util.Quest;

public interface QuestGetter {
	public FlowIncQuest getLogicalQuestion(String user);
	public FlowIncQuest getSyntaxQuestion(String user);
	public TraceQuest getTraceQuestion(String user);
	public YesNoTest[] getYesNoTests(String subject);
}
