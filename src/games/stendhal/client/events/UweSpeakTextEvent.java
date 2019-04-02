package games.stendhal.client.events;

import Util.Management.JourneyRow;
import Util.Management.ManagementAPI;
import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.entity.Entity;
import games.stendhal.client.gui.chatlog.HeaderLessEventLine;
import games.stendhal.common.NotificationType;

public class UweSpeakTextEvent extends Event<Entity>{
	public void execute() {
		String text="";
		if (event.has("text")) {
			text = event.get("text");
		}
		
		ClientSingletonRepository.getUserInterface().addEventLine(new HeaderLessEventLine(text, NotificationType.TUTORIAL));
		ClientSingletonRepository.getUserInterface().addGameScreenText(
				0, 0,
				text.replace("|", ""), NotificationType.TUTORIAL, false);
	}
}
