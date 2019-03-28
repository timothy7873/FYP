package games.stendhal.client.gui;

import Util.Management.*;
import games.stendhal.client.entity.User;
import marauroa.common.game.RPEvent;

public class UweOutputIncQuestViewer {
	private String code;
	private String[] ans;
	private String[] out;
	private String title,journeyRowId;
	private Reward[] rewards;
	
	public UweOutputIncQuestViewer(final RPEvent e)
	{
		title="";
		if (e.has("title")) {
			title = e.get("title");
		}
		journeyRowId="";
		if (e.has("journeyRowId")) {
			journeyRowId = e.get("journeyRowId");
		}
		
		TraceQuest q=ManagementAPI.api.getTraceQuestion(User.getCharacterName(), journeyRowId);
		if(q==null)
		{
			return;
		}
		
		code=q.code;
		ans=q.ans.split("\n");
		out=q.out.split("\n");
		rewards=q.reward;
		
		view();
	}
	public static void viewQuest(final RPEvent e) {
		new UweOutputIncQuestViewer(e);
	}
	public void view()
	{
		UweOutputIncQuestViewPanel vp=new UweOutputIncQuestViewPanel(code,out,ans,rewards,journeyRowId);
		new UweOutputIncQuestWindow(vp,title);
	}
	
}
