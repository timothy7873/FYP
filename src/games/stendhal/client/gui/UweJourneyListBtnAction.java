package games.stendhal.client.gui;

import java.awt.event.ActionListener;

import javax.swing.JList;

import Util.Management.Journey;

public abstract class UweJourneyListBtnAction implements ActionListener{
	protected UweJourneyListViewPanel self;
	protected JList list;
	protected Journey[] data;
	
	public void setInfo(UweJourneyListViewPanel self, JList list, Journey[] data)
	{
		this.self=self;
		this.list=list;
		this.data=data;
	}
}
