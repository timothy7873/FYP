package games.stendhal.client.gui;

import java.awt.Button;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Spring;
import javax.swing.SpringLayout;

import Util.Management.Journey;
import Util.Management.ManagementAPI;
import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.entity.User;
import games.stendhal.client.gui.chatlog.HeaderLessEventLine;
import games.stendhal.common.NotificationType;

public class UweJourneyListViewPanel extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1137127972118811668L;
	private InternalManagedWindow window=null;
	private Journey[] journeys;
	private UweJourneyListBtnAction submitAction;
	private String submitBtnName;
	
	public UweJourneyListViewPanel(Journey[] journeys, UweJourneyListBtnAction submitAction, String submitBtnName)
	{
		this.journeys=journeys;
		this.submitAction=submitAction;
		this.submitBtnName=submitBtnName;
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
		buildWindow();
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
	
	private void buildWindow()
	{
		int lastY;
		int maxWidth;
		
		//add window heading
		JLabel windowHeading=new JLabel("Please choose a journey at the following list");
		add(windowHeading);
		setX(windowHeading,10);
		setY(windowHeading,0);
		setHeight(windowHeading,30);
		lastY=getY(windowHeading)+getHeight(windowHeading);
		
		//add list heading
		JLabel listHeading=new JLabel("Journey List");
		add(listHeading);
		setX(listHeading,getX(windowHeading));
		setY(listHeading,lastY);
		setHeight(listHeading,30);
		lastY=getY(listHeading)+getHeight(listHeading);
		maxWidth=getWidth(listHeading);
		
		//add list body
		DefaultListModel data = new DefaultListModel();
		for(int i=0;i<journeys.length;i++)
			data.add(i,journeys[i].name);
		JList list=new JList(data);
		//list.addMouseListener(new ClickEventHandler(list,journeys,null));
		JScrollPane listBody=new JScrollPane(list);
		add(listBody);
		setX(listBody,getX(listHeading));
		setY(listBody,lastY);
		setHeight(listBody,200);
		//lastY=getY(listBody)+getHeight(listBody);
		maxWidth=getWidth(listBody)>maxWidth?getWidth(listBody):maxWidth;
		
		//set list to max width
		setWidth(listBody,maxWidth);
		
		//recover lastY
		lastY=getY(windowHeading)+getHeight(windowHeading);
		
		//add des heading
		JLabel desHeading=new JLabel("Journey Description");
		add(desHeading);
		setX(desHeading,getX(listBody)+maxWidth+10);
		setY(desHeading,lastY);
		setHeight(desHeading,30);
		lastY=getY(desHeading)+getHeight(desHeading);
		
		//add des body
		JTextArea desBody=new JTextArea();
		desBody.setEditable(false);
		add(desBody);
		setX(desBody,getX(desHeading));
		setY(desBody,lastY);
		setWidth(desBody,300);
		setHeight(desBody,getHeight(listBody));
		lastY=getY(desBody)+getHeight(desBody);
		
		//add mouse listener for list
		list.addMouseListener(new ClickEventHandler(list,journeys,desBody));
		
		//add cancel
		Button cancel=new Button("Cancel");
		cancel.addActionListener(new CancelBtnHandler(this));
		cancel.setName("cancel");
		add(cancel);
		setWidth(cancel,70);
		setHeight(cancel,30);
		setX(cancel,getX(listBody));
		setY(cancel,lastY+20);
		
		//add start journey
		submitAction.setInfo(this, list, journeys);
		Button submit=new Button(submitBtnName);
		submit.addActionListener(submitAction);
		submit.setName("cancel");
		add(submit);
		setWidth(submit,100);
		setHeight(submit,30);
		setX(submit,getX(desBody)+getWidth(desBody)-getWidth(submit));
		setY(submit,lastY+20);
		
		//set window size
		setWindowSize(getX(submit)+getWidth(submit)+10,getY(submit)+getHeight(submit)+10);
	}
	public void closeWindow() {window.closeButton.doClick();}
	
	private class CancelBtnHandler implements ActionListener
	{
		private UweJourneyListViewPanel self;
		
		public CancelBtnHandler(UweJourneyListViewPanel self)
		{
			this.self=self;
		}
		public void actionPerformed(ActionEvent e)
		{
			self.window.closeButton.doClick();
		}
		
	}

	private class ClickEventHandler implements MouseListener
	{
		private JList list;
		private Journey[] data;
		private JTextArea text;
		
		public ClickEventHandler(JList list,Journey[] data,JTextArea text)
		{
			this.list=list;
			this.data=data;
			this.text=text;
		}
		
		public void mouseClicked(MouseEvent e) 
		{
			if(list==null || data==null || text==null)
				return;
			if(list.getSelectedIndex()>=data.length)
				return;
			text.setText(data[list.getSelectedIndex()].description);
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		
		
	}
	
}
