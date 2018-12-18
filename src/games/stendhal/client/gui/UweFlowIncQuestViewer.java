package games.stendhal.client.gui;

import marauroa.common.game.RPEvent;

public class UweFlowIncQuestViewer {
	private String[] code;
	private String[] ans;
	private String out,exp,spliter;
	private String title;
	
	public UweFlowIncQuestViewer(final RPEvent e)
	{
		title="";
		if (e.has("title")) {
			title = e.get("title");
		}
		out="";
		if (e.has("out")) {
			out = e.get("out");
		}
		exp="";
		if (e.has("exp")) {
			exp = e.get("exp");
		}
		spliter="\n";
		if (e.has("spliter")) {
			spliter = e.get("spliter");
		}
		
		code=null;
		if(e.has("code"))
			code=e.get("code").split(spliter);
		ans=null;
		if(e.has("ans"))
			ans=e.get("ans").split(spliter);
		

		view();
	}
	public static void viewQuest(final RPEvent e) {
		new UweFlowIncQuestViewer(e);
	}
	public void view()
	{
		UweFlowIncQuestViewPanel vp=new UweFlowIncQuestViewPanel(code,ans,out,exp);
		new UweFlowIncQuestWindow(vp,title);
	}
}
