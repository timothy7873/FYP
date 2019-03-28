package games.stendhal.client.gui;

import Util.Management.Journey;
import marauroa.common.game.RPEvent;

public class UweJourneyListViewer {
	private UweJourneyListBtnAction btnHandler;
	private String title;
	private String btnTitle;
	private Journey[] journeys;
	
	public UweJourneyListViewer(final RPEvent e, String title, String btnTitle, UweJourneyListBtnAction btnHandler, Journey[] journeys)
	{
		this.title=title;
		this.btnTitle=btnTitle;
		this.btnHandler=btnHandler;
		this.journeys=journeys;

		view();
	}
	public void view()
	{
		UweJourneyListViewPanel vp=new UweJourneyListViewPanel(journeys, btnHandler, btnTitle);
		new UweJourneyListWindow(vp,title);
	}
	public static void viewJourneyList(final RPEvent e, String title, String btnTitle, UweJourneyListBtnAction btnHandler, Journey[] journeys) {
		new UweJourneyListViewer(e, title, btnTitle, btnHandler, journeys);
	}
	
	
}
