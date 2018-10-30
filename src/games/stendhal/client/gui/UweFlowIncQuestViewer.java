package games.stendhal.client.gui;

import marauroa.common.game.RPEvent;

public class UweFlowIncQuestViewer {
	private String code;
	private String title;
	
	public UweFlowIncQuestViewer(final RPEvent e)
	{
		code="";
		title="";
		
		if (e.has("code")) {
			code = e.get("code");
		}
		if (e.has("title")) {
			title = e.get("title");
		}

		view();
	}
	public static void viewQuest(final RPEvent e) {
		new UweFlowIncQuestViewer(e);
	}
	public void view()
	{
		UweFlowIncQuestViewPanel vp=new UweFlowIncQuestViewPanel(code);
		new UweFlowIncQuestWindow(vp,title);
	}
}
