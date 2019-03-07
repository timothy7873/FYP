package games.stendhal.client.events;

import games.stendhal.client.entity.Entity;
import games.stendhal.client.gui.UweJourneyListViewer;

public class UweShowJourneyListEvent extends Event<Entity>{
	public void execute() {
		UweJourneyListViewer.viewJourneyList(event);
	}
}
