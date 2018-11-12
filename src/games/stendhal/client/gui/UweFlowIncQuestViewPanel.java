package games.stendhal.client.gui;
//package games.stendhal.client.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SpringLayout.Constraints;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import games.stendhal.client.GameObjects;
import games.stendhal.client.entity.ContentChangeListener;
import games.stendhal.client.entity.IEntity;
import games.stendhal.client.entity.User;
import games.stendhal.client.entity.factory.EntityMap;
import layout.SpringUtilities;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPObject.ID;
import marauroa.common.game.RPSlot;

public class UweFlowIncQuestViewPanel extends JComponent implements ContentChangeListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6327842553901572328L;
	private String [] lines;
	private String[] ans;
	private String out,exp;
	private List<ItemPanel> panels;
	private String slotName="uwequest";
	private IEntity parent;
	private final int CODEHEIGHT=25;
	private FirstStageBtnHandler firstBtnHandler;
	private SecondStageBtnHandler secondBtnHandler;

	public UweFlowIncQuestViewPanel(String[] lines,String[] ans,String out,String exp)
	{
		this.lines=lines;
		this.ans=ans;
		this.out=out;
		this.exp=exp;
		
		setLayout(new BorderLayout(2,2));
		setOpaque(true);
		
		parent=User.get();
		firstBtnHandler=new FirstStageBtnHandler(this);
		panel=new ArrayList<ItemPanel>)();
	}
	public void buildFirstStage()
	{
		int codeAreaY;
		
		//add code heading
		JLabel codeHeading=new JLabel("<html><b>Please select the incorrect line</b></html>");
		add(codeHeading);
		setX(codeHeading,10);
		setY(codeHeading,0);
		setHeight(codeHeading,30);
		codeAreaY=getY(codeHeading)+getHeight(codeHeading);
		
		//add code body & buttons
		int maxWidth=getWidth(codeHeading);
		for(int i=0;i<lines.length;i++)
		{
			String line=lines[i]+"\t";
			
			Button btn=new Button("Select");
			btn.setPreferredSize(new Dimension(50,CODEHEIGHT));
			btn.setName("line"+i);
			btn.addActionListener(firstBtnHandler);
			add(btn);
			setX(btn,5);
			setY(btn,codeAreaY+5+CODEHEIGHT*i);
			
			JTextField box=new JTextField();
			box.setEditable(false);
			box.setText(line);
			box.setName("line"+i);
			add(box);
			setX(box,5+50+5);
			setY(box,codeAreaY+5+CODEHEIGHT*i);
			setHeight(box,CODEHEIGHT);
			
			maxWidth=Integer.max(maxWidth, getWidth(box));
		}
		for(int i=0;i<getComponentCount();i++)
		{
			Component c=getComponent(i);

			if(c instanceof JTextField)
				setWidth(c,maxWidth);
		}
		
		//add out heading
		JLabel outHeading=new JLabel("Current output");
		add(outHeading);
		setX(outHeading,10);
		setY(outHeading,codeAreaY+5+lines.length*CODEHEIGHT+10);
		setHeight(outHeading,20);
		
		//add out body
		JTextField outBody=new JTextField();
		outBody.setEditable(false);
		outBody.setText(out);
		add(outBody);
		setX(outBody,5);
		setY(outBody,getY(outHeading)+getHeight(outHeading));
		setWidth(outBody,maxWidth+50+5);
		
		//add exp heading
		JLabel expHeading=new JLabel("Expected output");
		add(expHeading);
		setX(expHeading,10);
		setY(expHeading,getY(outBody)+getHeight(outBody)+10);
		setHeight(expHeading,20);
		
		//add exp body
		JTextField expBody=new JTextField();
		expBody.setEditable(false);
		expBody.setText(exp);
		add(expBody);
		setX(expBody,5);
		setY(expBody,getY(expHeading)+getHeight(expHeading));
		setWidth(expBody,maxWidth+50+5);
		
		//add submit button
		Button submit=new Button("Submit");
		submit.addActionListener(firstBtnHandler);
		submit.setName("submit");
		add(submit);
		setWidth(submit,70);
		setHeight(submit,30);
		setX(submit,getX(expBody)+getWidth(expBody)-getWidth(submit));
		setY(submit,getY(expBody)+getHeight(expBody)+10);
		
		
		//format all btn and box text
		Font f=new Font("arial",Font.PLAIN,12);
		Component[] coms=this.getComponents();
		for(int i=0;i<coms.length;i++)
		{
			if(coms[i] instanceof Button || coms[i] instanceof JTextField)
			{
				coms[i].setFont(f);
				coms[i].setForeground(Color.BLACK);
			}
		}

		setWindowSize(maxWidth+5+50+5+5, getY(submit)+getHeight(submit)+5);
	}
	public void buildSecondStage()
	{
		int lastY=0;
		
		//add code heading
		JLabel codeHeading=new JLabel("<html><b>Please replace the incorrect line with your code item</b></html>");
		add(codeHeading);
		setX(codeHeading,10);
		setY(codeHeading,0);
		setHeight(codeHeading,30);
		lastY=getY(codeHeading)+getHeight(codeHeading);
		
		//add code body & panels
		int maxWidth=getWidth(codeHeading);
		for(int i=0;i<lines.length;i++)
		{
			String line=lines[i]+"\t";
			
			JTextField box;
			box=new JTextField();
			box.setEditable(false);
			box.setText(line);
			box.setName("line"+i);
			add(box);
			setX(box,5+50+5);
			setY(box,lastY+5);
			
			if(ans[i].replaceAll("\t", "").equals(lines[i].replaceAll("\t", "")))
				setHeight(box,CODEHEIGHT);
			else
			{
				setHeight(box,50);
				
				//add item container
				ItemPanel panel = new ItemPanel(null, null);
				panel.setItemNumber(panels.size());
				add(panel);
				setX(panel,10);//ori=5
				setY(panel,getY(box)+5);
				setWidth(panel,50);
				setHeight(panel,50);
				panel.setAcceptedTypes(EntityMap.getClass("item", null, null));
				panel.setParent(parent);
				panel.setName(slotName);
				parent.addContentChangeListener(this);
				contentAdded(parent.getSlot(slotName));
				
				panels.add(panel);
			}
			
			lastY=getY(box)+getHeight(box);
			maxWidth=Integer.max(maxWidth, getWidth(box));

		}
		for(int i=0;i<getComponentCount();i++)
		{
			Component c=getComponent(i);

			if(c instanceof JTextField)
				setWidth(c,maxWidth);
		}
		
		//add out heading
		JLabel outHeading=new JLabel("Current output");
		add(outHeading);
		setX(outHeading,10);
		setY(outHeading,lastY+10);
		setHeight(outHeading,20);
		lastY=getY(outHeading)+getHeight(outHeading);
		
		//add out body
		JTextField outBody=new JTextField();
		outBody.setEditable(false);
		outBody.setText(out);
		add(outBody);
		setX(outBody,5);
		setY(outBody,lastY);
		setWidth(outBody,maxWidth+50+5);
		lastY=getY(outBody)+getHeight(outBody);
		
		//add exp heading
		JLabel expHeading=new JLabel("Expected output");
		add(expHeading);
		setX(expHeading,10);
		setY(expHeading,lastY+10);
		setHeight(expHeading,20);
		lastY=getY(expHeading)+getHeight(expHeading);
		
		//add exp body
		JTextField expBody=new JTextField();
		expBody.setEditable(false);
		expBody.setText(exp);
		add(expBody);
		setX(expBody,5);
		setY(expBody,lastY);
		setWidth(expBody,maxWidth+50+5);
		lastY=getY(expBody)+getHeight(expBody);
		
		//add submit button
		Button submit=new Button("Submit");
		submit.addActionListener(secondBtnHandler);
		submit.setName("submit");
		add(submit);
		setWidth(submit,70);
		setHeight(submit,30);
		setX(submit,getX(expBody)+getWidth(expBody)-getWidth(submit));
		setY(submit,lastY+10);
		
		
		//format all btn and box text
		Font f=new Font("arial",Font.PLAIN,12);
		Component[] coms=this.getComponents();
		for(int i=0;i<coms.length;i++)
		{
			if(coms[i] instanceof Button || coms[i] instanceof JTextField)
			{
				coms[i].setFont(f);//format
				coms[i].setForeground(Color.BLACK);//color
			}
		}

		//setWindowSize(maxWidth+5+50+5+5, getY(submit)+getHeight(submit)+5);
		setWindowSize(0,0);
		this.resize(new Dimension(0,0));
		
		//update dialog
		validate();
		revalidate();
		repaint();
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
		
		Constraints cs=layout.getConstraints(this);
		layout.getConstraints(this).setConstraint(SpringLayout.EAST, Spring.constant(w));
		layout.getConstraints(this).setConstraint(SpringLayout.SOUTH, Spring.constant(h));
	}
	
	public String[] getAns() {return ans;}
	
	//Content change listener
	public void contentAdded(RPSlot slot)
	{
		
		
		//handle slot
		if (!slotName.equals(slot.getName()))
			return;//not our slot
		
		for(RPObject obj:slot)
		{
			if(obj==null)
				continue;
			
			ID id = obj.getID();
			for(ItemPanel panel: panels)
			{
				IEntity entity = panel.getEntity();
				if (entity != null && id.equals(entity.getRPObject().getID()))
					break;// Changed rather than added.
				
				
			}
		}
		
		RPObject obj=added.getFirst();//one item only
		if(obj==null)
			return;//no item added
		
		ID id = obj.getID();
		IEntity entity = panel.getEntity();
		if (entity != null && id.equals(entity.getRPObject().getID()))
			return;// Changed rather than added.
		
		IEntity real_entity = GameObjects.getInstance().get(obj);
		panel.setEntity(real_entity);
		
		
		//handle code
		int index=(getY(panel)-5-5)/50;
		Component[] c=getComponents();
		for(int i=0;i<c.length;i++)
		{
			if(c[i] instanceof JTextField && c[i].getName().equals(""+index))
			{
				JTextField box=(JTextField)c[i];
				String tabs="";
				for(int pos=0;pos<box.getText().length() && box.getText().charAt(pos)=='\t';pos++)
					tabs+="\t";
				box.setText(tabs+obj.get("name"));
			}
		}
	}
	public void contentRemoved(RPSlot removed)
	{
//		if (!slotName.equals(removed.getName()))
//			return;//not our slot
//		
//		RPObject obj=removed.getFirst();//first only
//		ID id = obj.getID();
//		IEntity entity = panel.getEntity();
//		if (entity != null && id.equals(entity.getRPObject().getID())) {
//			if (obj.size() == 1) {
//				// The object was removed
//				panel.setEntity(null);
//			}
//		}
//		
//		
//		//handle code
//		int index=(getY(panel)-5-5)/50;
//		Component[] c=getComponents();
//		for(int i=0;i<c.length;i++)
//		{
//			if(c[i] instanceof JTextField && c[i].getName().equals(""+index))
//			{
//				JTextField box=(JTextField)c[i];
//				box.setText(lines[index]);
//			}
//		}
	}


	private class FirstStageBtnHandler implements ActionListener
	{
		private UweFlowIncQuestViewPanel self;
		public FirstStageBtnHandler(UweFlowIncQuestViewPanel self)
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
						
						
						if(!ans[index].replaceAll("\t", "").equals(text.getText().replaceAll("\t", "")) &&
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
					JOptionPane.showMessageDialog(null, "Your selections are not correct, please retry!", "Quest", JOptionPane.INFORMATION_MESSAGE);;
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
		private UweFlowIncQuestViewPanel self;
		public SecondStageBtnHandler(UweFlowIncQuestViewPanel self)
		{
			this.self=self;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			
		}
	}
}
