package games.stendhal.client.gui.questviewer;

import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class FlowIncQuestViewPanel extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6327842553901572328L;
	private String code;
	
	public FlowIncQuestViewPanel(String code)
	{
		this.code=code;
		
		setLayout(new BorderLayout());
		setOpaque(true);
	}
	public void prepare()
	{
		JLabel lbl=new JLabel(code);
		add(lbl);
		//add(new Button("code"), BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
}
