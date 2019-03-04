package games.stendhal.client.events;

import games.stendhal.client.entity.Entity;
import games.stendhal.client.gui.*;

public class UweReorderQuestEvent extends Event<Entity>{
	public void execute() {
		UweReorderQuestViewer.viewQuest(event);
	}
}
