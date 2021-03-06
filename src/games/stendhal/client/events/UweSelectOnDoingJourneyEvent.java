package games.stendhal.client.events;

import java.awt.event.ActionEvent;

import Util.Management.Journey;
import Util.Management.JourneyRow;
import Util.Management.ManagementAPI;
import Util.game.client.UweClientAction;
import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.entity.Entity;
import games.stendhal.client.entity.User;
import games.stendhal.client.gui.*;
import games.stendhal.client.gui.chatlog.HeaderLessEventLine;
import games.stendhal.common.NotificationType;

public class UweSelectOnDoingJourneyEvent extends Event<Entity>{
	private String npcId;
	
	public void execute() {
		npcId="";
		if (event.has("npcId")) {
			npcId = event.get("npcId");
		}
		
		Journey[] journeys=ManagementAPI.api.getOnDoingJourney(User.getCharacterName(), npcId);
		if(journeys==null)
		{
			return;
		}
		
		UweJourneyListViewer.viewJourneyList(event, "On doing journey list", "Choose journey", new SubmitBtnHandler(), journeys);
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
			
			//send selected journey
			UweClientAction.selectJourney(npcId, j.id);
			
			//close window
			self.closeWindow();
		}
	}
	
}
