package games.stendhal.client.events;

import games.stendhal.client.entity.Entity;
import games.stendhal.client.gui.UweFlowIncQuestViewer;

class FlowIncQuestEvent extends Event<Entity> {
	/**
	 * executes the event
	 */
	@Override
	public void execute() {
		//RPEventImageViewer.viewImage(event);
		
		UweFlowIncQuestViewer.viewQuest(event);

		
	}
	
	
	
}