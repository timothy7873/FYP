package games.stendhal.client.gui;

import marauroa.common.game.RPEvent;

public class UweYesNoTestViewer {
	private String subject;
	private String title;
	private ;
	
	public UweYesNoTestViewer(final RPEvent e)
	{
		subject="";
		if (e.has("subject")) {
			subject = e.get("subject");
		}
		title="";
		if (e.has("title")) {
			title = e.get("title");
		}
		
		view();
	}
	
	public static void viewTest(final RPEvent e) {
		new UweYesNoTestViewer(e);
	}
	public void view()
	{
		UweYesNoTestViewerViewPanel vp=new UweYesNoTestViewerViewPanel();
		new UweYesNoTestWindow(vp,title);
	}
}
