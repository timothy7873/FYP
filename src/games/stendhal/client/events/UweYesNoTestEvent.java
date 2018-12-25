package games.stendhal.client.events;

import games.stendhal.client.entity.Entity;
import games.stendhal.client.gui.UweYesNoTestViewer;


class UweYesNoTestEvent extends Event<Entity> {
	/**
	 * executes the event
	 */
	@Override
	public void execute() {
		UweYesNoTestViewer.viewTest(event);
	}
	
	
	
}