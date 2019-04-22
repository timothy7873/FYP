package games.stendhal.client.gui;

import marauroa.common.game.RPEvent;

public class UweCombinatorViewer {
	public UweCombinatorViewer(final RPEvent e)
	{
		view();
	}
	public static void viewCombinator(final RPEvent e) {
		new UweCombinatorViewer(e);
	}
	public void view()
	{
		UweCombinatorViewPanel vp=new UweCombinatorViewPanel();
		new UweCombinatorWindow(vp,"Combinator");
	}
}
