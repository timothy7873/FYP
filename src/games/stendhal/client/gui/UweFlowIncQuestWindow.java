package games.stendhal.client.gui;

import javax.swing.SwingUtilities;

public class UweFlowIncQuestWindow extends InternalManagedWindow{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1331241549198527966L;

	public UweFlowIncQuestWindow(UweFlowIncQuestViewPanel vp,String title)
	{
		super("FlowIncQuest",title);
		
		vp.prepare();
		setContent(vp);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				j2DClient.get().addWindow(UweFlowIncQuestWindow.this);
				center();
				getParent().validate();
			}
		});
		
	}
}
