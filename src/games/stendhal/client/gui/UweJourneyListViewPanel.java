package games.stendhal.client.gui;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.Spring;
import javax.swing.SpringLayout;

import Util.Management.Journey;

public class UweJourneyListViewPanel extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1137127972118811668L;
	private InternalManagedWindow window=null;
	private Journey[] journeys;
	
	public UweJourneyListViewPanel(Journey[] journeys)
	{
		this.journeys=journeys;
	}
	public void setWindow(InternalManagedWindow window)
	{
		this.window=window;
		//window.addCloseListener(this);
	}
	public void prepare()
	{
		setLayout(new SpringLayout());
		
		//buildFirstStage();
	}
	
	private void setX(Component c, int x){((SpringLayout)getLayout()).getConstraints(c).setX(Spring.constant(x));}
	private void setY(Component c, int y){((SpringLayout)getLayout()).getConstraints(c).setY(Spring.constant(y));}
	private void setWidth(Component c, int w){((SpringLayout)getLayout()).getConstraints(c).setWidth(Spring.constant(w));}
	private void setHeight(Component c, int h){((SpringLayout)getLayout()).getConstraints(c).setHeight(Spring.constant(h));}
	private int getX(Component c){return ((SpringLayout)getLayout()).getConstraints(c).getX().getValue();}
	private int getY(Component c){return ((SpringLayout)getLayout()).getConstraints(c).getY().getValue();}
	private int getWidth(Component c) {return ((SpringLayout)getLayout()).getConstraints(c).getWidth().getValue();}
	private int getHeight(Component c) {return ((SpringLayout)getLayout()).getConstraints(c).getHeight().getValue();}
	private void setWindowSize(int w, int h)
	{
		SpringLayout layout=(SpringLayout)getLayout();

		layout.getConstraints(this).setConstraint(SpringLayout.EAST, Spring.constant(w));
		layout.getConstraints(this).setConstraint(SpringLayout.SOUTH, Spring.constant(h));
	}
	
	
}
