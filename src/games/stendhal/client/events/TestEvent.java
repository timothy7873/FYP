package games.stendhal.client.events;

import games.stendhal.client.entity.Entity;
import games.stendhal.client.gui.questviewer.FlowIncQuestViewPanel;
import games.stendhal.client.gui.questviewer.FlowIncQuestViewer;
import games.stendhal.client.gui.questviewer.FlowIncQuestWindow;


class TestEvent extends Event<Entity> {
	/**
	 * executes the event
	 */
	@Override
	public void execute() {
		//RPEventImageViewer.viewImage(event);
		
		FlowIncQuestViewer.viewQuest(event);

		
	}
	
	
	
}