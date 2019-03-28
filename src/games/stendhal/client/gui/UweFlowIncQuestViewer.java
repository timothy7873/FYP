package games.stendhal.client.gui;

import Util.Management.*;
import games.stendhal.client.entity.User;
import marauroa.common.game.RPEvent;

public class UweFlowIncQuestViewer {
	private String[] code;
	private String[] ans;
	private String out,exp;
	private String title,type,journeyRowId;
	private Reward[] rewards;
	
	public UweFlowIncQuestViewer(final RPEvent e)
	{
		title="";
		if (e.has("title")) {
			title = e.get("title");
		}
		type="";
		if (e.has("type")) {
			type = e.get("type");
		}
		journeyRowId="";
		if (e.has("journeyRowId")) {
			journeyRowId = e.get("journeyRowId");
		}
		
		FlowIncQuest q=null;
		if(type.equals("logical"))
		{
			q=ManagementAPI.api.getLogicalQuestion(User.getCharacterName(),journeyRowId);
		}
		else if(type.equals("syntax"))
		{
			q=ManagementAPI.api.getSyntaxQuestion(User.getCharacterName(),journeyRowId);
		}
		if(q==null)
			return;
		
		code=q.code.split("\n");
		ans=q.ans.split("\n");
		out=q.out;
		exp=q.exp;
		rewards=q.reward;
		
		view();
	}
	public static void viewQuest(final RPEvent e) {
		new UweFlowIncQuestViewer(e);
	}
	public void view()
	{
		UweFlowIncQuestViewPanel vp=new UweFlowIncQuestViewPanel(code,ans,out,exp,rewards, journeyRowId);
		new UweFlowIncQuestWindow(vp,title);
	}
	
}
