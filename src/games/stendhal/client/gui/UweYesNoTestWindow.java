package games.stendhal.client.gui;

import javax.swing.SwingUtilities;

public class UweYesNoTestWindow extends InternalManagedWindow{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1331241549198527966L;

	public UweYesNoTestWindow(UweYesNoTestViewerViewPanel vp,String title)
	{
		super("YesNoTest",title);
		
		vp.setWindow(this);
		vp.prepare();
		setContent(vp);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				j2DClient.get().addWindow(UweYesNoTestWindow.this);
				center();
				getParent().validate();
			}
		});
		
	}
	
}
