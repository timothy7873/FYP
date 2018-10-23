package games.stendhal.client.gui.questviewer;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
		Button btn=new Button("code");
		//JOptionPane.showMessageDialog(null, this.getClass().getName(), "title", JOptionPane.INFORMATION_MESSAGE);
		ActionListener al=new LinkedActionListener(this) ;
		btn.addActionListener(al);
		
		add(btn, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	public void shout()
	{
		JOptionPane.showMessageDialog(null, "shout", "title", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private class LinkedActionListener implements ActionListener
	{
		private FlowIncQuestViewPanel self;
		
		public LinkedActionListener(FlowIncQuestViewPanel self)
		{
			this.self=self;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			Component[] c=self.getComponents();
			for(int i=0;i<c.length;i++)
			{
				if(c[i] instanceof Button)
				{
					self.remove(c[i]);
				}
			}
		}
	}
	
}
