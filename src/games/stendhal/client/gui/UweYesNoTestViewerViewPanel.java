package games.stendhal.client.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;

import games.stendhal.client.gui.InternalWindow.CloseListener;

public class UweYesNoTestViewerViewPanel extends JComponent implements CloseListener{
	private static final long serialVersionUID = 6327842553901572328L;
	private InternalManagedWindow window=null;
	private String[] question;
	private boolean[] ans;
	private JTextField textbox;
	public int qindex;
	
	private BtnHandler btnHandler;
	
	public UweYesNoTestViewerViewPanel(String[] question, boolean[] ans)
	{
		this.question=question;
		this.ans=ans;
		
		setLayout(new BorderLayout(2,2));
		setOpaque(true);
		
		qindex=0;
		btnHandler=new BtnHandler(this);
		textbox=null;
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
		JTextField text=new JTextField();
		text.setEditable(false);
		add(text);
		setX(text,5);
		setY(text,5);
		setWidth(text,200);
		setHeight(text,70);
		textbox=text;
		
		Button btn1=new Button("Yes");
		btn1.setPreferredSize(new Dimension(70,30));
		btn1.setName("yes");
		btn1.addActionListener(btnHandler);
		add(btn1);
		setX(btn1,getX(text));
		setY(btn1,getY(text)+getHeight(text)+5);
		
		Button btn2=new Button("No");
		btn2.setPreferredSize(new Dimension(70,30));
		btn2.setName("no");
		btn2.addActionListener(btnHandler);
		add(btn2);
		setX(btn2,getX(btn1)+getWidth(btn1)+5);
		setY(btn2,getY(btn1));
		
		setWindowSize(getX(text)+getWidth(text)+5, getY(btn2)+getHeight(btn2)+5);
	}
	private void refreshView()
	{
		if(question==null || ans==null || question.length!=ans.length || qindex>=question.length || textbox==null)
			return;
		
		textbox.setText(question[qindex]);
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
	
	private class BtnHandler implements ActionListener
	{
		private UweYesNoTestViewerViewPanel self;
		
		public BtnHandler(UweYesNoTestViewerViewPanel self)
		{
			this.self=self;
		}
		public void actionPerformed(ActionEvent e)
		{
			Button curBtn=(Button)e.getSource();
			
			boolean cor=ans[qindex];
			boolean my=curBtn.getName().equals("yes");
			String msg="";
			
			if(cor==my)
				msg="You are right!";
			else
				msg="No you are wrong!";
			JOptionPane.showMessageDialog(null,msg,"Result",JOptionPane.INFORMATION_MESSAGE);
			
			qindex++;
			if(qindex>=ans.length)
			{
				JOptionPane.showMessageDialog(null, "You have done the test and you suppose to be ready for the quest.", "Done", JOptionPane.INFORMATION_MESSAGE);
				self.window.closeButton.doClick();
			}
			else
				refreshView();
		}
	}
}
