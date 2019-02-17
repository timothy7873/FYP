package games.stendhal.client.events;

import games.stendhal.client.entity.Entity;
import games.stendhal.client.gui.UweOutputIncQuestViewer;


class UweOutputIncQuestEvent extends Event<Entity> {
	/**
	 * executes the event
	 */
	@Override
	public void execute() {
		UweOutputIncQuestViewer.viewQuest(event);
	}
	
	
	
}