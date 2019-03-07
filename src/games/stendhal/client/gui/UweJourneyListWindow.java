package games.stendhal.client.gui;

import javax.swing.SwingUtilities;

public class UweJourneyListWindow extends InternalManagedWindow{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4265543999716431046L;

	public UweJourneyListWindow(UweJourneyListViewPanel vp,String title)
	{
		super("JourneyList",title);
		
		vp.setWindow(this);
		vp.prepare();
		setContent(vp);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				j2DClient.get().addWindow(UweJourneyListWindow.this);
				center();
				getParent().validate();
			}
		});
		
	}
}
