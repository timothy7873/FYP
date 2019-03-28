package games.stendhal.client.events;

import java.awt.event.ActionEvent;

import Util.Management.Journey;
import Util.Management.ManagementAPI;
import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.entity.Entity;
import games.stendhal.client.entity.User;
import games.stendhal.client.gui.UweJourneyListBtnAction;
import games.stendhal.client.gui.UweJourneyListViewer;
import games.stendhal.client.gui.chatlog.HeaderLessEventLine;
import games.stendhal.common.NotificationType;

public class UweShowNewJourneyListEvent extends Event<Entity>{
	public void execute() {
		Journey[] journeys=ManagementAPI.api.getNewJourneyList(User.getCharacterName());
		UweJourneyListViewer.viewJourneyList(event, "Available journey list", "Start journey", new SubmitBtnHandler(), journeys);
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
			self.closeWindow();
		}
	}
	
}
