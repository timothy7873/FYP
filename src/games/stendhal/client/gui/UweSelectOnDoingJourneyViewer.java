package games.stendhal.client.gui;

import java.awt.event.ActionEvent;

import Util.Management.Journey;
import Util.Management.JourneyRow;
import Util.Management.ManagementAPI;
import Util.Management.ReorderQuest;
import Util.Management.Reward;
import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.entity.User;
import games.stendhal.client.gui.chatlog.HeaderLessEventLine;
import games.stendhal.common.NotificationType;
import marauroa.common.game.RPEvent;

public class UweSelectOnDoingJourneyViewer {
	private String npcId;
	
	private Journey[] journeys;
	
	public UweSelectOnDoingJourneyViewer(final RPEvent e)
	{
		npcId="";
		if (e.has("npcId")) {
			npcId = e.get("npcId");
		}
		
		journeys=ManagementAPI.api.getOnDoingJourney(User.getCharacterName(), npcId);
		if(journeys==null)
		{
			return;
		}
		
		view();
	}
	public static void viewQuest(final RPEvent e) {
		new UweSelectOnDoingJourneyViewer(e);
	}
	public void view()
	{
		UweJourneyListViewPanel vp=new UweJourneyListViewPanel(journeys, new SubmitBtnHandler(), "Start journey");
		new UweJourneyListWindow(vp,"");
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
			JourneyRow jr=ManagementAPI.api.getJourneyRow(User.getCharacterName(), j.id);
			
			//perform before string
			String str=jr.speakBefore;
			ClientSingletonRepository.getUserInterface().addEventLine(new HeaderLessEventLine(str, NotificationType.TUTORIAL));
			ClientSingletonRepository.getUserInterface().addGameScreenText(
					self.getX() + (self.getWidth() / 2.0), self.getY(),
					str.replace("|", ""), NotificationType.TUTORIAL, false);
			
			//close window
			self.window.closeButton.doClick();
		}
	}
}
