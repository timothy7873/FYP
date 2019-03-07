package games.stendhal.client.gui;

import Util.Management.Journey;
import Util.Management.ManagementAPI;
import marauroa.common.game.RPEvent;

public class UweJourneyListViewer {
	private String npcId;
	private boolean showNew;
	private Journey[] journeys;
	
	public UweJourneyListViewer(final RPEvent e)
	{
		npcId="";
		if (e.has("npcId")) {
			npcId = e.get("npcId");
		}
		showNew=e.has("showNew") && e.get("npcId").equals("new");
		
		journeys=ManagementAPI.api.getJourneyList(npcId, user);
		
		view();
	}
	public void view()
	{
		UweJourneyListViewPanel vp=new UweJourneyListViewPanel(journeys);
		new UweJourneyListWindow(vp,"Available journey list");
	}
	public static void viewJourneyList(final RPEvent e) {
		new UweJourneyListViewer(e);
	}
}
