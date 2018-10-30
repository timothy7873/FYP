package games.stendhal.client.gui;
//package games.stendhal.client.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import games.stendhal.client.entity.User;
import games.stendhal.client.entity.factory.EntityMap;
import layout.SpringUtilities;

public class UweFlowIncQuestViewPanel extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6327842553901572328L;
	private String code;
	
	public UweFlowIncQuestViewPanel(String code)
	{
		this.code=code;
		
		setLayout(new BorderLayout(2,2));
		setOpaque(true);
	}
	public void prepare()
	{
		String [] lines=code.split("\n");
		
		setLayout(new SpringLayout());
		
		int maxWidth=0;
		for(int i=0;i<lines.length;i++)
		{
			String line=lines[i]+"\t";
			
			Button btn=new Button(i+".");
			btn.setPreferredSize(new Dimension(50,50));
			add(btn);
			setX(btn,5);
			setY(btn,5+50*i);
			
			JTextField box=new JTextField();
			box.setEditable(false);
			box.setText(line);
			//box.setPreferredSize(new Dimension(500,50));
			//box.setFont(new Font(Font.DIALOG,Font.PLAIN,25));
			add(box);
			setX(box,5+50+5);
			setY(box,5+50*i);
			setHeight(box,50);
			
			maxWidth=Integer.max(maxWidth, getWidth(box));
		}
		for(int i=0;i<getComponentCount();i++)
		{
			Component c=getComponent(i);

			if(c instanceof JTextField)
				setWidth(c,maxWidth);
		}
		ItemPanel panel = new ItemPanel(null, null);
		panel.setItemNumber(0);
		add(panel);
		setX(panel,5);
		setY(panel,5+50*lines.length+5);
		setWidth(panel,50);
		setHeight(panel,50);
		panel.setAcceptedTypes(EntityMap.getClass("item", null, null));
		panel.setParent(User.get());
		panel.setName("uwequest");
//		panel.setInspector(inspector);
		
		setWindowSize(maxWidth+5+50+5+5, 5+lines.length*50+5+50+5);
		
		
		
		
		//SpringUtilities.makeGrid(this, lines.length, 2, 5, 5, 1, 1);
		//if(true)return;
		
		
//		int width=3;
//		int height=3;
//		int PADDING = 1;
//		List<ItemPanel> panels;
//		
//		//setLayout(new GridLayout(height, width, PADDING, PADDING));
//		setLayout(new GridLayout(4, 0, PADDING, PADDING));
//		panels = new ArrayList<ItemPanel>();
//
//		for (int i = 0; i < width * height; i++) {
//			ItemPanel panel = new ItemPanel(null, null);
//			panel.setItemNumber(i);
//			panels.add(panel);
//			add(panel);
//		}
		
		
		//add(new SlotGrid(width, height));
		
		
		setVisible(true);
	}
	private void setX(Component c, int x){((SpringLayout)getLayout()).getConstraints(c).setX(Spring.constant(x));}
	private void setY(Component c, int y){((SpringLayout)getLayout()).getConstraints(c).setY(Spring.constant(y));}
	private void setWidth(Component c, int w){((SpringLayout)getLayout()).getConstraints(c).setWidth(Spring.constant(w));}
	private void setHeight(Component c, int h){((SpringLayout)getLayout()).getConstraints(c).setHeight(Spring.constant(h));}
	private int getWidth(Component c) {return ((SpringLayout)getLayout()).getConstraints(c).getWidth().getValue();}
	private int getHeight(Component c) {return ((SpringLayout)getLayout()).getConstraints(c).getHeight().getValue();}
	private void setWindowSize(int w, int h)
	{
		SpringLayout layout=(SpringLayout)getLayout();
		
		layout.getConstraints(this).setConstraint(SpringLayout.EAST, Spring.constant(w));
		layout.getConstraints(this).setConstraint(SpringLayout.SOUTH, Spring.constant(h));
	}
	
	
	private class UweFlowIncQuestBtnActionListener implements ActionListener
	{
		private UweFlowIncQuestViewPanel self;
		
		public UweFlowIncQuestBtnActionListener(UweFlowIncQuestViewPanel self)
		{
			this.self=self;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			e.getSource();
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
