package games.stendhal.client.gui;

import Util.Management.*;
import games.stendhal.client.entity.User;
import marauroa.common.game.RPEvent;

public class UweFlowIncQuestViewer {
	private String[] code;
	private String[] ans;
	private String out,exp;
	private String title,type,npcId;
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
		npcId="";
		if (e.has("npcId")) {
			npcId = e.get("npcId");
		}
		
		FlowIncQuest q=null;
		if(type.equals("logical"))
		{
			q=ManagementAPI.api.getLogicalQuestion(npcId, User.getCharacterName());
		}
		else if(type.equals("syntax"))
		{
			q=ManagementAPI.api.getSyntaxQuestion(npcId, User.getCharacterName());
		}
		
		if(q==null)
		{
			return;
		}
		
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
		UweFlowIncQuestViewPanel vp=new UweFlowIncQuestViewPanel(code,ans,out,exp,rewards,npcId,type);
		new UweFlowIncQuestWindow(vp,title);
	}
	
}
