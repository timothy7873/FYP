package games.stendhal.client.events;

import games.stendhal.client.entity.Entity;
import games.stendhal.client.gui.*;

public class UweSelectOnDoingJourneyEvent extends Event<Entity>{
	public void execute() {
		UweReorderQuestViewer.viewQuest(event);
	}
}
