package games.stendhal.client.gui;

import javax.swing.SwingUtilities;

public class UweCombinatorWindow extends InternalManagedWindow{
	
	
	public UweCombinatorWindow(UweCombinatorViewPanel vp,String title)
	{
		super("FlowIncQuest",title);
		
		vp.setWindow(this);
		vp.prepare();
		setContent(vp);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				j2DClient.get().addWindow(UweCombinatorWindow.this);
				center();
				getParent().validate();
			}
		});
	}
}
