package games.stendhal.client.gui;
//package games.stendhal.client.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import java.awt.event.MouseListener;

import Util.Management.*;
import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.GameObjects;
import games.stendhal.client.entity.ContentChangeListener;
import games.stendhal.client.entity.IEntity;
import games.stendhal.client.entity.User;
import games.stendhal.client.entity.factory.EntityMap;
import games.stendhal.client.gui.InternalWindow.CloseListener;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPObject.ID;
import marauroa.common.game.RPSlot;

public class UweReorderQuestViewPanel extends JComponent implements ContentChangeListener,CloseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6327842553901572328L;
	private InternalManagedWindow window=null;
	private String[] code;
	private String out;
	private String[] ans;
	private Reward[] rewards;
	private String npcId;
	private Map<UweItemPanel,Integer> panels;
	private String slotName="uwepopup";
	private IEntity parent;
	private final int CODEHEIGHT=25;
	private FirstStageBtnHandler firstBtnHandler;
	private SecondStageBtnHandler secondBtnHandler;

	public UweReorderQuestViewPanel(String[] code,String out,String[] ans, Reward[] rewards, String npcId)
	{
		this.code=code;
		this.ans=ans;
		this.out=out;
		this.rewards=rewards;
		this.npcId=npcId;
		
		setLayout(new BorderLayout(2,2));
		setOpaque(true);
		
		parent=User.get();
		firstBtnHandler=new FirstStageBtnHandler(this);
		secondBtnHandler=new SecondStageBtnHandler(this);
		panels=new HashMap<UweItemPanel,Integer>();
	}
	public void setWindow(InternalManagedWindow window)
	{
		this.window=window;
		window.addCloseListener(this);
	}
	public void windowClosed(InternalWindow window) {
		// TODO Auto-generated method stub
		
		//return item
		RPAction action = new RPAction();
		action.put("type", "UweReturnItem");
		ClientSingletonRepository.getClientFramework().send(action);
		
		//call api to stop timer
		ManagementAPI.api.stopTimeCount(npcId, User.getCharacterName());
		
		//stop mon item change
		parent.removeContentChangeListener(this);
	}
	
	public void buildFirstStage()
	{
		int lastY=0;
		
		//add code heading
		JLabel codeHeading=new JLabel("Please first activate the codes");
		add(codeHeading);
		setX(codeHeading,10);
		setY(codeHeading,0);
		setHeight(codeHeading,30);
		lastY=getY(codeHeading)+getHeight(codeHeading);
		
		//add code body
		int maxWidth=getX(codeHeading)+getWidth(codeHeading);
		Font f=new Font("arial",Font.ITALIC,12);
		for(int i=0;i<code.length;i++)
		{
			String line=code[i]+"\t";
			
			JTextField box;
			box=new JTextField();
			box.setEditable(false);
			box.setText(line);
			box.setName("line"+i);
			box.setFont(f);
			box.setForeground(Color.BLACK);
			add(box);
			setX(box,5+50+5);
			setY(box,lastY+5+i*(50+5));
			setHeight(box,50);
			Map attr=f.getAttributes();
			box.setFont(new Font(attr));
			maxWidth=getX(box)+getWidth(box)>maxWidth?getX(box)+getWidth(box):maxWidth;
			
			//add item container
			UweItemPanel panel = new UweItemPanel(null, null);
			//panel.setItemNumber(panels.size());
			panel.setItemNumber(0);
			add(panel);
			setX(panel,10);//ori=5
			setY(panel,getY(box)+5);
			setWidth(panel,50);
			setHeight(panel,50);
			panel.setAcceptedTypes(EntityMap.getClass("item", null, null));
			panel.setParent(parent);
			panel.setName(slotName+i);

			panels.put(panel, i);
		}
		lastY+=5+code.length*(50+5);
		
		//add submit button
		Button submit=new Button("Submit");
		submit.addActionListener(firstBtnHandler);
		submit.setName("submit");
		add(submit);
		setWidth(submit,70);
		setHeight(submit,30);
		setX(submit,maxWidth-getWidth(submit));
		setY(submit,lastY+10);
		
		setWindowSize(maxWidth+5+5, getY(submit)+getHeight(submit)+5);
		
		//format all box width
		Component[] coms=this.getComponents();
		for(int i=0;i<coms.length;i++)
		{
			if(coms[i] instanceof JTextField)
			{
				setWidth(coms[i],maxWidth-getX(coms[i]));
			}
		}
		
		//add item handler
		parent.addContentChangeListener(this);
		
	}
	public void buildSecondStage()
	{
		int lastY;
		int maxWidth;
		
		//add code heading
		JLabel codeHeading=new JLabel("Please reorder the codes by draging them");
		add(codeHeading);
		setX(codeHeading,10);
		setY(codeHeading,0);
		setHeight(codeHeading,30);
		lastY=getY(codeHeading)+getHeight(codeHeading);
		maxWidth=getX(codeHeading)+getWidth(codeHeading);
		
		DefaultListModel m = new DefaultListModel();
		for(int i=0;i<code.length;i++)
			m.addElement((code[i]+"\t").replaceAll("\t", "        "));
		final JList list = new JList(m);
		list.setCellRenderer(new DefaultListCellRenderer());
		list.setFixedCellHeight(50+5);
		add(list);
		setX(list, 5+50+5);
		setY(list, lastY+5);
		lastY=getY(list)+getHeight(list);
		maxWidth=getX(list)+getWidth(list)>maxWidth?getX(list)+getWidth(list):maxWidth;
		
		//format list width to max width
		setWidth(list, maxWidth-getX(list));

		//add submit button
		Button submit=new Button("Submit");
		submit.addActionListener(secondBtnHandler);
		submit.setName("submit");
		add(submit);
		setWidth(submit,70);
		setHeight(submit,30);
		setX(submit,maxWidth+10-getWidth(submit));
		setY(submit,lastY+10);

		int w=getX(submit)+getWidth(submit)+10+5+5;
		int h=getY(submit)+getHeight(submit)+5+5;
		Insets in=window.getInsets();
		h+=window.getTitlebar().getHeight();
		window.setSize(w, h);
		
		//update dialog
		validate();
		revalidate();
		repaint();
		window.validate();
		window.revalidate();
		window.repaint();
		window.getParent().validate();
		window.getParent().revalidate();
		window.getParent().repaint();
		
	}
	public void prepare()
	{
		setLayout(new SpringLayout());
		
		buildFirstStage();
		//setVisible(true);
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
	
	public String[] getAns() {return ans;}
	public Reward[] getRewards() {return rewards;}
	
	//Content change listener
	public void contentAdded(RPSlot added)
	{
		//handle slot
		if (added.getName().length()<=slotName.length() ||
				!added.getName().substring(0, slotName.length()).equals(slotName))
			return;//not our slot
		
		//invalid request
		if(UweItemPanel.curDraggedTarget==null)
			return;
		
		//there suppose to be only one item added once
		RPObject obj=added.getFirst();
		if(obj==null)
			return;
		ID id = obj.getID();
		for(UweItemPanel panel: panels.keySet())
		{
			IEntity entity = panel.getEntity();
			if (entity != null && id.equals(entity.getRPObject().getID()))
				return;// Changed rather than added.
		}
		
		IEntity entity = GameObjects.getInstance().get(obj);
		UweItemPanel.curDraggedTarget.setEntity(entity);
		
		
		//handle code
		if(!(this.panels.get(UweItemPanel.curDraggedTarget) instanceof Integer))
		{
			UweItemPanel.curDraggedTarget=null;
			return;
		}
		int index=((Integer)panels.get(UweItemPanel.curDraggedTarget)).intValue();
		String name="line"+index;
		Component[] c=getComponents();
		for(int i=0;i<c.length;i++)
		{
			if(c[i].getName()==null)
				continue;
			if(c[i] instanceof JTextField && c[i].getName().equals(name))
			{
				JTextField box=(JTextField)c[i];
				
				Font f;
				f=new Font("arial",Font.BOLD,12);
				if(!obj.get("name").replaceAll("\t", "").equals(code[index].replaceAll("\t", "")))
				{
					Map attr=f.getAttributes();
					attr.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
					f=new Font(attr);
				}
				box.setFont(f);
				
				String tabs="";
				for(int pos=0;pos<box.getText().length() && box.getText().charAt(pos)=='\t';pos++)
					tabs+="\t";
				box.setText(tabs+obj.get("name"));

				break;
			}
		}
		
		UweItemPanel.curDraggedTarget=null;
		

	}
	public void contentRemoved(RPSlot removed)
	{
		if (removed.getName().length()<=slotName.length() ||
				!removed.getName().substring(0, slotName.length()).equals(slotName))
			return;//not our slot
		
		for (RPObject obj : removed)
		{
			ID id = obj.getID();
			for (ItemPanel panel : panels.keySet())
			{
				IEntity entity = panel.getEntity();
				if (entity != null && id.equals(entity.getRPObject().getID())) {
					if (obj.size() == 1) {
						// The object was removed
						panel.setEntity(null);
						
						int index=panels.get(panel);
						String name="line"+index;
						Component[] coms=getComponents();
						for(Component c:coms)
						{
							if(c.getName()==null)
								continue;
							if(c instanceof JTextField && c.getName().equals(name))
							{
								JTextField text=(JTextField)c;
								
								text.setText(code[index]);
								text.setFont(new Font("arial",Font.ITALIC,12));
							}
						}
						
					}
				}
			}
			
		}
		
	}


	private class FirstStageBtnHandler implements ActionListener
	{
		private UweReorderQuestViewPanel self;
		public FirstStageBtnHandler(UweReorderQuestViewPanel self)
		{
			this.self=self;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			if(!(e.getSource() instanceof Button))
				return;
			
			Button btn=(Button)e.getSource();
			if(!btn.getName().equals("submit"))
				return;
			
			String msg="";
			Component[] coms=self.getComponents();
			for(int i=0;i<coms.length;i++)
			{
				if(coms[i] instanceof JTextField)
				{
					JTextField text=(JTextField)coms[i];
					
					Map m=text.getFont().getAttributes();
					if(m.get(TextAttribute.STRIKETHROUGH)==TextAttribute.STRIKETHROUGH_ON)
					{
						msg="Please active all codes with correct code item";
						break;
					}
					if(text.getFont().getStyle()==Font.ITALIC)
					{
						msg="Please active all codes before submitting";
						break;
					}
				}
			}
			if(msg.equals(""))
			{
				//build second stage dialog
				//remove all component
				Component[] coms1=self.getComponents();
				for(int i=0;i<coms1.length;i++)
					self.remove(coms1[i]);
				//start build
				self.buildSecondStage();
			}
			else
			{
				JOptionPane.showMessageDialog(null, msg, "Quest", JOptionPane.INFORMATION_MESSAGE);
			}
			

			//update dialog
			self.validate();
			self.revalidate();
			self.repaint();
			
		}
	}
	
	private class SecondStageBtnHandler implements ActionListener
	{
		private UweReorderQuestViewPanel self;
		public SecondStageBtnHandler(UweReorderQuestViewPanel self)
		{
			this.self=self;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			if(!(e.getSource() instanceof Button))
				return;
			
			Button btn=(Button)e.getSource();
			if(!btn.getName().equals("submit"))
				return;
			
			String msg="";
			Component[] coms=self.getComponents();
			for(int i=0;i<coms.length;i++)
			{
				if(coms[i] instanceof JTextField)
				{
					JTextField text=(JTextField)coms[i];
					
					Map m=text.getFont().getAttributes();
					if(m.get(TextAttribute.STRIKETHROUGH)==TextAttribute.STRIKETHROUGH_ON)
					{
						msg="Please active all codes with correct code item";
						break;
					}
					if(text.getFont().getStyle()==Font.ITALIC)
					{
						msg="Please active all codes before submitting";
						break;
					}
				}
			}
			if(msg.equals(""))
			{
				//build second stage dialog
				//remove all component
				Component[] coms1=self.getComponents();
				for(int i=0;i<coms1.length;i++)
					self.remove(coms1[i]);
				//start build
				self.buildSecondStage();
			}
			else
			{
				JOptionPane.showMessageDialog(null, msg, "Quest", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private class DragEventHander implements MouseListener
	{
		private int from;
		private JList list;
		
		public DragEventHander(JList list)
		{
			this.list=list;
		}
		
		public void mouseClicked(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			from=list.locationToIndex(e.getPoint());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			int to=list.locationToIndex(e.getPoint());
			String fromStr,toStr;
			fromStr=list.get;
			list.clearSelection();
			
//			for (int i = 0; i < numlist.length; i++) {
//				if (Integer.parseInt(m.getElementAt(i).toString().substring(0, 1)) == numlist[i]) {
//					Component ct = list.getComponent(i);
//					ct.setBackground(Color.GREEN);
//				}
//			}
			
			sec = list.locationToIndex(e.getPoint());
			System.out.println(sec);
			String buf = "";
			if (sec != -1) {
				String s1 = m.getElementAt(first).toString();
				String s2 = m.getElementAt(sec).toString();
				if (first != sec) {
					if (first > sec) {
						System.out.println("up");
						for (int i = first; i > sec; i--) {
							buf = m.getElementAt(i).toString();
							m.set(i, m.getElementAt(i - 1).toString());
							m.set(i - 1, buf);
						}
					} else {
						System.out.println("down");
						for (int i = first; i < sec; i++) {
							buf = m.getElementAt(i).toString();
							m.set(i, m.getElementAt(i + 1).toString());
							m.set(i + 1, buf);
						}
					}
					list.clearSelection();
				}
			}


		}
		
		
	}
}
