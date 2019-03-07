package Util.Management;

public class JourneyRow {
	public FlowIncQuest flowIncQuest;
	public TraceQuest traceQuest;
	public ReorderQuest reorderQuest;
	public String speakBefore;
	public String speakAfter;
	
	public JourneyRow() {}
	public JourneyRow(FlowIncQuest flowIncQuest, TraceQuest traceQuest, ReorderQuest reorderQuest, String speakBefore, String speakAfter) 
	{
		this.flowIncQuest=flowIncQuest;
		this.traceQuest=traceQuest;
		this.reorderQuest=reorderQuest;
		this.speakBefore=speakBefore;
		this.speakAfter=speakAfter;
	}
}
