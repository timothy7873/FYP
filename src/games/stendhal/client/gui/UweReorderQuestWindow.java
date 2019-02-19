package games.stendhal.client.gui;

import javax.swing.SwingUtilities;

public class UweReorderQuestWindow extends InternalManagedWindow{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1331241549198527966L;

	public UweReorderQuestWindow(UweReorderQuestViewPanel vp,String title)
	{
		super("ReorderQuest",title);
		
		vp.setWindow(this);
		vp.prepare();
		setContent(vp);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				j2DClient.get().addWindow(UweReorderQuestWindow.this);
				center();
				getParent().validate();
			}
		});
		
	}
	
}
