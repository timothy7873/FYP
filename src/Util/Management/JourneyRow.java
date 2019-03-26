package Util.Management;

public class JourneyRow {
	public String journeyRowId;
	public String journeyId;
	public String speakBefore;
	public String speakAfter;
	public int row;
	public String npcId;
	
	public JourneyRow() {}
	public JourneyRow(String journeyRowId, String journeyId, String speakBefore, String speakAfter, int row, String npcId) 
	{
		this.journeyRowId=journeyRowId;
		this.journeyId=journeyId;
		this.speakBefore=speakBefore;
		this.speakAfter=speakAfter;
		this.row=row;
		this.npcId=npcId;
	}
}
