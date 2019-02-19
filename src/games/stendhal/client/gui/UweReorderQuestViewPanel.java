package games.stendhal.client.gui;
//package games.stendhal.client.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;

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
	private int usedSlot=0;
	private IEntity parent;
	private final int OUTPUTHEIGHT=25;
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
//		int outAreaY;
//		
//		//add code heading
//		JLabel codeHeading=new JLabel("Please first activate the codes");
//		add(codeHeading);
//		setX(codeHeading,10);
//		setY(codeHeading,0);
//		setHeight(codeHeading,30);
//		
//		//add code
//		//int maxWidth=getWidth(codeHeading);
//		JTextArea codeBody=new JTextArea();
//		codeBody.setEditable(false);
//		codeBody.setText(code);
//		add(codeBody);
//		setX(codeBody,5);
//		setY(codeBody,getY(codeHeading)+getHeight(codeHeading));
//		setWidth(codeBody,getWidth(codeBody)+50);
//		//setHeight(codeBody,getHeight(codeBody)*out.length);
//
//		//add out heading
//		JLabel outHeading=new JLabel("Please select the incorrect output line");
//		add(outHeading);
//		setX(outHeading,10);
//		setY(outHeading,getY(codeBody)+getHeight(codeBody));
//		setHeight(outHeading,20);
//		outAreaY=getY(outHeading)+getHeight(outHeading);
//		
//		//add out body
//		int maxWidth=getWidth(codeBody);
//		for(int i=0;i<out.length;i++)
//		{
//			String line=out[i];
//			
//			Button btn=new Button("Select");
//			btn.setPreferredSize(new Dimension(50,OUTPUTHEIGHT));
//			btn.setName("line"+i);
//			btn.addActionListener(firstBtnHandler);
//			add(btn);
//			setX(btn,5);
//			setY(btn,outAreaY+5+OUTPUTHEIGHT*i);
//			
//			JTextField box=new JTextField();
//			box.setEditable(false);
//			box.setText(line);
//			box.setName("line"+i);
//			add(box);
//			setX(box,5+50+5);
//			setY(box,outAreaY+5+OUTPUTHEIGHT*i);
//			setHeight(box,OUTPUTHEIGHT);
//			
//			maxWidth=Integer.max(maxWidth, getWidth(box));
//		}
//		for(int i=0;i<getComponentCount();i++)
//		{
//			Component c=getComponent(i);
//
//			if(c instanceof JTextField)
//				setWidth(c,maxWidth-50-5);
//		}
//		
//		//add submit button
//		Button submit=new Button("Submit");
//		submit.addActionListener(firstBtnHandler);
//		submit.setName("submit");
//		add(submit);
//		setWidth(submit,70);
//		setHeight(submit,30);
//		setX(submit,maxWidth-getWidth(submit));
//		setY(submit,outAreaY+5+out.length*OUTPUTHEIGHT+10);
//		
//		
//		//format all btn and box text
//		Font f=new Font("arial",Font.PLAIN,12);
//		Component[] coms=this.getComponents();
//		for(int i=0;i<coms.length;i++)
//		{
//			if(coms[i] instanceof Button || coms[i] instanceof JTextField)
//			{
//				coms[i].setFont(f);
//				coms[i].setForeground(Color.BLACK);
//			}
//		}
//
//		setWindowSize(maxWidth+5+5, getY(submit)+getHeight(submit)+5);
	}
	public void buildSecondStage()
	{
//		int lastY=0;
//		
//		//add code heading
//		JLabel codeHeading=new JLabel("The existing program code");
//		add(codeHeading);
//		setX(codeHeading,10);
//		setY(codeHeading,0);
//		setHeight(codeHeading,30);
//		lastY=getY(codeHeading)+getHeight(codeHeading);
//		
//		//add code body
//		JTextArea codeBody=new JTextArea();
//		codeBody.setEditable(false);
//		codeBody.setText(code);
//		add(codeBody);
//		setX(codeBody,5);
//		setY(codeBody,lastY);
//		setWidth(codeBody,getWidth(codeBody)+50);
//		lastY=getY(codeBody)+getHeight(codeBody);
//
//		//add out heading
//		JLabel outHeading=new JLabel("Current output");
//		add(outHeading);
//		setX(outHeading,10);
//		setY(outHeading,lastY+10);
//		setHeight(outHeading,20);
//		lastY=getY(outHeading)+getHeight(outHeading);
//		
//		//add out body
//		Font f=new Font("arial",Font.PLAIN,12);
//		int maxWidth=getWidth(codeBody);
//		for(int i=0;i<out.length;i++)
//		{
//			String line=out[i];
//			
//			JTextField box;
//			box=new JTextField();
//			box.setEditable(false);
//			box.setText(line);
//			box.setName("line"+i);
//			box.setFont(f);
//			box.setForeground(Color.BLACK);
//			add(box);
//			setX(box,5+50+5);
//			setY(box,lastY+5);
//			
//			if(ans[i].equals(out[i]))
//				setHeight(box,OUTPUTHEIGHT);
//			else
//			{
//				Map attr=f.getAttributes();
//				attr.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
//				box.setFont(new Font(attr));
//				setHeight(box,50);
//				
//				//add item container
//				UweItemPanel panel = new UweItemPanel(null, null);
//				panel.setItemNumber(panels.size());
//				//panel.("line"+i);
//				add(panel);
//				setX(panel,10);//ori=5
//				setY(panel,getY(box)+5);
//				setWidth(panel,50);
//				setHeight(panel,50);
//				panel.setAcceptedTypes(EntityMap.getClass("item", null, null));
//				panel.setParent(parent);
//				panel.setName(slotName+usedSlot);
//				usedSlot++;
//
//				panels.put(panel, i);
//			}
//			
//			lastY=getY(box)+getHeight(box);
//			maxWidth=Integer.max(maxWidth, getWidth(box));
//
//		}
//		for(int i=0;i<getComponentCount();i++)
//		{
//			Component c=getComponent(i);
//
//			if(c instanceof JTextField)
//				setWidth(c,maxWidth-50);
//		}
//		
//		//add item change monitor
//		parent.addContentChangeListener(this);
//		
//		//add submit button
//		Button submit=new Button("Submit");
//		submit.addActionListener(secondBtnHandler);
//		submit.setName("submit");
//		add(submit);
//		setWidth(submit,70);
//		setHeight(submit,30);
//		setX(submit,maxWidth-getWidth(submit));
//		setY(submit,lastY+10);
//
//		int w=getX(submit)+getWidth(submit)+10+5+5;
//		int h=getY(submit)+getHeight(submit)+5+5;
//		Insets in=window.getInsets();
//		h+=window.getTitlebar().getHeight();
//		window.setSize(w, h);
//		
//		//update dialog
//		validate();
//		revalidate();
//		repaint();
//		window.validate();
//		window.revalidate();
//		window.repaint();
//		window.getParent().validate();
//		window.getParent().revalidate();
//		window.getParent().repaint();
		
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
		String name="line"+((Integer)panels.get(UweItemPanel.curDraggedTarget)).intValue();
		Component[] c=getComponents();
		for(int i=0;i<c.length;i++)
		{
			if(c[i].getName()==null)
				continue;
			if(c[i] instanceof JTextField && c[i].getName().equals(name))
			{
				JTextField box=(JTextField)c[i];
				
				String tabs="";
				for(int pos=0;pos<box.getText().length() && box.getText().charAt(pos)=='\t';pos++)
					tabs+="\t";
				box.setText(tabs+obj.get("name"));
				
				box.setFont(new Font("arial",Font.BOLD,12));
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
								//text.setText(out[index]);
								
								Font f=new Font("arial",Font.PLAIN,12);
								Map attr=f.getAttributes();
								attr.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
								text.setFont(new Font(attr));
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
			Button curBtn=(Button)e.getSource();
			
			if(curBtn.getName().length()>4 && curBtn.getName().substring(0,4).equals("line"))//select btn
			{
				int index=-1;
				try
				{
					index=Integer.parseInt(curBtn.getName().substring(4));
				}
				catch(Exception ex)
				{return;}
				
				if(curBtn.getLabel().equals("Select"))
				{
					Component[] coms=self.getComponents();
					for(int i=0;i<coms.length;i++)
					{
						Component c=coms[i];
						if(c instanceof JTextField && c.getName().equals("line"+index))
						{
							JTextField text=(JTextField)c;
							
							Font f=new Font("arial",Font.BOLD,12);
							Map attr=f.getAttributes();
							attr.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
							c.setFont(new Font(attr));
							break;
						}
					}
					
					curBtn.setLabel("Cancel");
				}
				else
				{
					Component[] coms=self.getComponents();
					for(int i=0;i<coms.length;i++)
					{
						Component c=coms[i];
						if(c instanceof JTextField && c.getName().equals("line"+index))
						{
							c.setFont(new Font("arial",Font.PLAIN,12));
							break;
						}
					}
					
					curBtn.setLabel("Select");
				}
				

			}
			else if(curBtn.getName().equals("submit"))//submit btn
			{
				boolean allCorrect=true;
				
				String[] ans=self.getAns();
				Component[] coms=self.getComponents();
				for(int i=0;i<coms.length;i++)
				{
					if(coms[i] instanceof JTextField)
					{
						JTextField text=(JTextField)coms[i];
						
						//get index
						int index=-1;
						if(text.getName()==null)//sometimes null
							continue;
						if(text.getName().length()<=4)//check if name so short, 100% not start with "line"
							continue;
						if(!text.getName().substring(0, 4).equals("line"))//check if start with "line"
							continue;
						try {index=Integer.parseInt(text.getName().substring(4));}//convert to int
						catch(Exception ex) {continue;}
						if(index>=ans.length)//check if index out of bounds
							continue;
						
						
						if(!ans[index].equals(text.getText()) &&
							text.getFont().getAttributes().get(TextAttribute.STRIKETHROUGH)!=TextAttribute.STRIKETHROUGH_ON)//need tick
						{
							allCorrect=false;
							break;
						}
						else if(ans[index].replaceAll("\t", "").equals(text.getText().replaceAll("\t", "")) &&
								text.getFont().getAttributes().get(TextAttribute.STRIKETHROUGH)==TextAttribute.STRIKETHROUGH_ON)//no need tick but ticked
						{
							allCorrect=false;
							break;
						}
					}

				}
				if(allCorrect)
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
					JOptionPane.showMessageDialog(null, "Your selections are not correct, please retry!", "Quest", JOptionPane.INFORMATION_MESSAGE);
				}
				
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
			
			boolean allCorrect=true;
			
			String[] ans=self.getAns();
			Component[] coms=self.getComponents();
			for(int i=0;i<coms.length;i++)
			{
				if(coms[i] instanceof JTextField)
				{
					JTextField text=(JTextField)coms[i];
					
					//get index
					int index=-1;
					if(text.getName()==null)//sometimes null
						continue;
					if(text.getName().length()<=4)//check if name so short, 100% not start with "line"
						continue;
					if(!text.getName().substring(0, 4).equals("line"))//check if start with "line"
						continue;
					try {index=Integer.parseInt(text.getName().substring(4));}//convert to int
					catch(Exception ex) {continue;}
					if(index>=ans.length)//check if index out of bounds
						continue;
					
					
					if(!ans[index].equals(text.getText()))
					{
						allCorrect=false;
						break;
					}
				}

			}
			if(allCorrect)
			{
				//tell correct
				//send reward
				//send destroy code item
				//close window
				
				JOptionPane.showMessageDialog(null, "Correct!", "Quest", JOptionPane.INFORMATION_MESSAGE);;
				
				String items="";
				int exp=0;
				double karma=0;
				Reward[] rewards=self.getRewards();
				for(int i=0;i<rewards.length;i++)
				{
					if(rewards[i].itemName!=null)
						items+=rewards[i].itemName+","+rewards[i].count+";";
					exp+=rewards[i].exp;
					karma+=0;
				}
				
				RPAction action = new RPAction();
				action.put("type", "UweQuestSubmit");
				action.put("npcId", npcId);
				action.put("questType", "trace");
				ClientSingletonRepository.getClientFramework().send(action);
				
				self.window.closeButton.doClick();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Your codes are not correct, please retry!", "Quest", JOptionPane.INFORMATION_MESSAGE);;
			}
			
			

		}
	}

}
