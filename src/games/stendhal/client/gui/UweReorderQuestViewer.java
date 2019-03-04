package games.stendhal.client.gui;

import Util.Management.*;
import games.stendhal.client.entity.User;
import marauroa.common.game.RPEvent;

public class UweReorderQuestViewer {
	private String[] code;
	private String[] ans;
	private String out;
	private String title,npcId;
	private Reward[] rewards;
	
	public UweReorderQuestViewer(final RPEvent e)
	{
		title="";
		if (e.has("title")) {
			title = e.get("title");
		}
		npcId="";
		if (e.has("npcId")) {
			npcId = e.get("npcId");
		}
		
		ReorderQuest q=ManagementAPI.api.getReorderQuestion(npcId, User.getCharacterName());
		if(q==null)
		{
			return;
		}
		
		code=q.code.split("\n");
		ans=q.ans.split("\n");
		out=q.out;
		rewards=q.reward;
		
		view();
	}
	public static void viewQuest(final RPEvent e) {
		new UweReorderQuestViewer(e);
	}
	public void view()
	{
		UweReorderQuestViewPanel vp=new UweReorderQuestViewPanel(code,out,ans,rewards,npcId);
		new UweReorderQuestWindow(vp,title);
	}
	
}
