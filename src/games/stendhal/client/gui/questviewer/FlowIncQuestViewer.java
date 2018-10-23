package games.stendhal.client.gui.questviewer;

import marauroa.common.game.RPEvent;

public class FlowIncQuestViewer {
	private String code;
	private String title;
	
	public FlowIncQuestViewer(final RPEvent e)
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
		new FlowIncQuestViewer(e);
	}
	public void view()
	{
		FlowIncQuestViewPanel vp=new FlowIncQuestViewPanel(code);
		new FlowIncQuestWindow(vp,title);
	}
}
