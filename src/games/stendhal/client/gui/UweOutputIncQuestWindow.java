package games.stendhal.client.gui;

import javax.swing.SwingUtilities;

public class UweOutputIncQuestWindow extends InternalManagedWindow{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1331241549198527966L;

	public UweOutputIncQuestWindow(UweOutputIncQuestViewPanel vp,String title)
	{
		super("OutputIncQuest",title);
		
		vp.setWindow(this);
		vp.prepare();
		setContent(vp);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				j2DClient.get().addWindow(UweOutputIncQuestWindow.this);
				center();
				getParent().validate();
			}
		});
		
	}
	
}
