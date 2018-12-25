package games.stendhal.client.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;

import games.stendhal.client.entity.ContentChangeListener;
import games.stendhal.client.gui.InternalWindow.CloseListener;
import marauroa.common.game.RPSlot;

public class UweYesNoTestViewerViewPanel extends JComponent implements CloseListener{
	private static final long serialVersionUID = 6327842553901572328L;
	private InternalManagedWindow window=null;
	private String[] question;
	private boolean[] ans;
	private int qindex;
	
	public UweYesNoTestViewerViewPanel(String[] question, boolean[] ans)
	{
		this.question=question;
		this.ans=ans;
		
		setLayout(new BorderLayout(2,2));
		setOpaque(true);
		
		qindex=0;
	}
	public void setWindow(InternalManagedWindow window)
	{
		this.window=window;
		window.addCloseListener(this);
	}
	public void windowClosed(InternalWindow window) {
		// TODO Auto-generated method stub
		//parent.removeContentChangeListener(this);
	}
	public void prepare()
	{
		setLayout(new SpringLayout());
		
		buildView();
		refreshView();
		//setVisible(true);
	}
	private void buildView()
	{
		JTextField box=new JTextField();
		box.setEditable(false);
		add(box);
		setX(box,5+50+5);
		setY(box,codeAreaY+5+CODEHEIGHT*i);
		setWidth(box,CODEHEIGHT);
		setHeight(box,CODEHEIGHT);
		
		Button btn=new Button("Yes");
		btn.setPreferredSize(new Dimension(50,CODEHEIGHT));
		btn.setName("line"+i);
		btn.addActionListener(firstBtnHandler);
		add(btn);
		setX(btn,5);
		setY(btn,codeAreaY+5+CODEHEIGHT*i);
		
		Button btn=new Button("No");
		btn.setPreferredSize(new Dimension(50,CODEHEIGHT));
		btn.setName("line"+i);
		btn.addActionListener(firstBtnHandler);
		add(btn);
		setX(btn,5);
		setY(btn,codeAreaY+5+CODEHEIGHT*i);
	}
	private void refreshView()
	{
		if(question==null || ans==null || question.length!=ans.length || qindex>=question.length)
			return;
		
		
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
