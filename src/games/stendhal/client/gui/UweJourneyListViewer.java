package games.stendhal.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import Util.Management.Journey;
import Util.Management.ManagementAPI;
import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.entity.User;
import games.stendhal.client.gui.chatlog.HeaderLessEventLine;
import games.stendhal.common.NotificationType;
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
		
		journeys=ManagementAPI.api.getNewJourneyList(User.getCharacterName());
		
		view();
	}
	public void view()
	{
		UweJourneyListViewPanel vp=new UweJourneyListViewPanel(journeys, new SubmitBtnHandler(), "Start journey");
		new UweJourneyListWindow(vp,"Available journey list");
	}
	public static void viewJourneyList(final RPEvent e) {
		new UweJourneyListViewer(e);
	}
	
	private class SubmitBtnHandler extends UweJourneyListBtnAction
	{
		public SubmitBtnHandler(){}
		
		public void actionPerformed(ActionEvent e)
		{
			if(list==null || data==null)
				return;
			if(list.getSelectedIndex()>=data.length || list.getSelectedIndex()<0)
				return;
			Journey j=data[list.getSelectedIndex()];
			ManagementAPI.api.startJourney(User.getCharacterName(), j.id);
			
			//perform starting
			ClientSingletonRepository.getUserInterface().addEventLine(new HeaderLessEventLine(j.begining, NotificationType.TUTORIAL));
			ClientSingletonRepository.getUserInterface().addGameScreenText(
					self.getX() + (self.getWidth() / 2.0), self.getY(),
					j.begining.replace("|", ""), NotificationType.TUTORIAL, false);
			
			//close window
			self.window.closeButton.doClick();
		}
	}
}
