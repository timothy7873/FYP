package games.stendhal.client.gui.questviewer;

import marauroa.common.game.RPEvent;

public class FlowIncQuestViewer {
	private String code;
	
	public FlowIncQuestViewer(final RPEvent e)
	{
		code="123";
		view();
	}
	public static void viewQuest(final RPEvent e) {
		new FlowIncQuestViewer(e);
	}
	public void view()
	{
		FlowIncQuestViewPanel vp=new FlowIncQuestViewPanel(code);
		new FlowIncQuestWindow(vp);
	}
}
