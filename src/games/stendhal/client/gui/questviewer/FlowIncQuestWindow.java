package games.stendhal.client.gui.questviewer;

import javax.swing.SwingUtilities;

import games.stendhal.client.gui.InternalManagedWindow;
import games.stendhal.client.gui.j2DClient;

public class FlowIncQuestWindow extends InternalManagedWindow{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1331241549198527966L;

	public FlowIncQuestWindow(FlowIncQuestViewPanel vp,String title)
	{
		super("FlowIncQuest",title);
		
		vp.prepare();
		setContent(vp);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				j2DClient.get().addWindow(FlowIncQuestWindow.this);
				center();
				getParent().validate();
			}
		});
		
	}
}
