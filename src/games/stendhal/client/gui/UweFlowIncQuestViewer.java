package games.stendhal.client.gui;

import Util.Quest.*;
import games.stendhal.client.entity.User;
import marauroa.common.game.RPEvent;

public class UweFlowIncQuestViewer {
	private String[] code;
	private String[] ans;
	private String out,exp;
	private String title,type;
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
		
		FlowIncQuest q=null;
		QuestGetter getter=new HardcodeQuestGetter();
		if(type.equals("logical"))
		{
			User.get();
			q=getter.getLogicalQuestion(User.getCharacterName());
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
		UweFlowIncQuestViewPanel vp=new UweFlowIncQuestViewPanel(code,ans,out,exp,rewards);
		new UweFlowIncQuestWindow(vp,title);
	}
	
}
