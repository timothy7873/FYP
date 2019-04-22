package games.stendhal.client.gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import Util.game.client.UweClientAction;
import games.stendhal.client.GameObjects;
import games.stendhal.client.entity.ContentChangeListener;
import games.stendhal.client.entity.IEntity;
import games.stendhal.client.entity.User;
import games.stendhal.client.entity.factory.EntityMap;
import games.stendhal.client.gui.InternalWindow.CloseListener;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;
import marauroa.common.game.RPObject.ID;

public class UweCombinatorViewPanel extends JComponent implements ContentChangeListener,CloseListener{
	private InternalManagedWindow window=null;
	private UweItemPanel panel1,panel2,panel3;
	private IEntity player;
	
	public UweCombinatorViewPanel()
	{
		player=User.get();
	}
	public void setWindow(InternalManagedWindow window)
	{
		this.window=window;
		window.addCloseListener(this);
	}
	public void prepare()
	{
		setLayout(new SpringLayout());
		
		build();
		//setVisible(true);
	}
	private void build()
	{
		int lastY;
		
		//add heading
		JLabel heading=new JLabel("<html><b>Please drag in the item that you want to combine</b></html>");
		add(heading);
		setX(heading,10);
		setY(heading,0);
		setHeight(heading,30);
		lastY=getY(heading)+getHeight(heading);
		
		//add panel 1
		panel1 = new UweItemPanel(null, null);
		panel1.setItemNumber(0);
		add(panel1);
		setX(panel1,10);
		setY(panel1,lastY+5);
		setWidth(panel1,50);
		setHeight(panel1,50);
		panel1.setAcceptedTypes(EntityMap.getClass("item", null, null));
		panel1.setParent(player);
		panel1.setName("uwepopup1");
		
		//add text 1
		JTextField box;
		box=new JTextField();
		box.setEditable(false);
		box.setName("line0");
		add(box);
		setX(box,5+50+5);
		setY(box,lastY+5);
		setWidth(box,getX(heading)+getWidth(heading)-getX(box));
		setHeight(box,25);
		lastY=getY(panel1)+getHeight(panel1);
		
		//add symbol
		JLabel addSym=new JLabel("<html><b>+</b></html>",SwingConstants.CENTER);
		add(addSym);
		setX(addSym,10);
		setY(addSym,lastY);
		setHeight(addSym,15);
		setWidth(addSym,getWidth(heading));
		lastY=getY(addSym)+getHeight(addSym);
		
		//add panel 2
		panel2 = new UweItemPanel(null, null);
		panel2.setItemNumber(0);
		add(panel2);
		setX(panel2,10);
		setY(panel2,lastY+5);
		setWidth(panel2,50);
		setHeight(panel2,50);
		panel2.setAcceptedTypes(EntityMap.getClass("item", null, null));
		panel2.setParent(User.get());
		panel2.setName("uwepopup2");
		
		//add text 2
		box=new JTextField();
		box.setEditable(false);
		box.setName("line1");
		box.setForeground(Color.BLACK);
		add(box);
		setX(box,5+50+5);
		setY(box,lastY+5);
		setWidth(box,getX(heading)+getWidth(heading)-getX(box));
		setHeight(box,25);
		lastY=getY(panel2)+getHeight(panel2);
		
		//add symbol
		JLabel eqSym=new JLabel("<html><b>=</b></html>",SwingConstants.CENTER);
		add(eqSym);
		setX(eqSym,10);
		setY(eqSym,lastY);
		setWidth(eqSym,getWidth(heading));
		setHeight(eqSym,15);
		lastY=getY(eqSym)+getHeight(eqSym);
		
		//panel 3
		
		//add text 3
		box=new JTextField();
		box.setEditable(false);
		box.setName("line2");
		box.setForeground(Color.BLACK);
		add(box);
		setX(box,5+50+5);
		setY(box,lastY+5);
		setWidth(box,getX(heading)+getWidth(heading)-getX(box));
		setHeight(box,25);
		
		//add btn
		Button submit=new Button("Combine");
		submit.addActionListener(new BtnHandler(this));
		submit.setName("combine");
		add(submit);
		setWidth(submit,70);
		setHeight(submit,30);
		setX(submit,getX(box)+getWidth(box)-getWidth(submit));
		setY(submit,getY(box)+getHeight(box)+10);
		
		player.addContentChangeListener(this);
		
		setWindowSize(getX(heading)+getWidth(heading)+10,getY(submit)+getHeight(submit)+10);
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

	public void windowClosed(InternalWindow window) {
		// TODO Auto-generated method stub
		UweClientAction.returnItems();
		player.removeContentChangeListener(this);
	}
	public void contentAdded(RPSlot added) {
		// TODO Auto-generated method stub
		
		String slotName="uwepopup";
		//handle slot
		if (added.getName().length()<=slotName.length() ||
				!added.getName().substring(0, slotName.length()).equals(slotName))
			return;//not our slot
		
		//there suppose to be only one item added once
		RPObject obj=added.getFirst();
		if(obj==null)
			return;
		
		if(UweItemPanel.curDraggedTarget==null)
		{
			if(added.getName().equals("uwepopup3"))
			{
				Component[] c=getComponents();
				for(int i=0;i<c.length;i++)
				{
					if(c[i] instanceof UweItemPanel && c[i].getName().equals("uwepopup3"))
					{
						IEntity entity = GameObjects.getInstance().get(obj);
						UweItemPanel panel=(UweItemPanel)c[i];
						panel.setEntity(entity);
						return;
					}
				}
			}
			return;
		}
		IEntity entity = GameObjects.getInstance().get(obj);
		UweItemPanel.curDraggedTarget.setEntity(entity);
		
		String name=added.getName();
		if(name.equals("uwepopup1"))
		{
			Component[] c=getComponents();
			for(int i=0;i<c.length;i++)
			{
				String cName=c[i].getName();
				if(cName==null)
					continue;
				if(cName.equals("line0"))
				{
					JTextField box=(JTextField)c[i];
					box.setText(added.getFirst().get("name"));
					break;
				}
			}
			
		}
		else if(name.equals("uwepopup2"))
		{
			Component[] c=getComponents();
			for(int i=0;i<c.length;i++)
			{
				String cName=c[i].getName();
				if(cName==null)
					continue;
				if(cName.equals("line1"))
				{
					JTextField box=(JTextField)c[i];
					box.setText(added.getFirst().get("name"));
					break;
				}
			}
		}
		else if(name.equals("uwepopup3"))
		{}
		
		//check btn clicked
		boolean clicked=false;
		Component[] c=getComponents();
		for(int i=0;i<c.length;i++)
		{
			if(c[i] instanceof Button && c[i].getName().equals("combine"))
			{
				clicked=!c[i].isEnabled();
				break;
			}
		}
		//update combined text
		if(!clicked)
		{
			String name1="",name2="";
			for(int i=0;i<c.length;i++)
			{
				String cName=c[i].getName();
				if(cName==null)
					continue;
				if(cName.equals("line0"))
				{
					JTextField box=(JTextField)c[i];
					name1=box.getText();
				}
				else if(cName.equals("line1"))
				{
					JTextField box=(JTextField)c[i];
					name2=box.getText();
				}
			}
			for(int i=0;i<c.length;i++)
			{
				String cName=c[i].getName();
				if(cName==null)
					continue;
				if(cName.equals("line2"))
				{
					JTextField box=(JTextField)c[i];
					box.setText(name1+name2);
				}
			}
		}
		
		UweItemPanel.curDraggedTarget=null;
	}
	public void contentRemoved(RPSlot removed) {
		// TODO Auto-generated method stub
		String slotName="uwepopup";
		
		if (removed.getName().length()<=slotName.length() ||
				!removed.getName().substring(0, slotName.length()).equals(slotName))
			return;//not our slot
		
		//blank the text
		String name=removed.getName();
		if(name.equals("uwepopup1"))
		{
			Component[] c=getComponents();
			for(int i=0;i<c.length;i++)
			{
				String cName=c[i].getName();
				if(cName==null)
					continue;
				if(cName.equals("line0"))
				{
					JTextField box=(JTextField)c[i];
					box.setText("");
					break;
				}
			}
		}
		else if(name.equals("uwepopup2"))
		{
			Component[] c=getComponents();
			for(int i=0;i<c.length;i++)
			{
				String cName=c[i].getName();
				if(cName==null)
					continue;
				if(cName.equals("line1"))
				{
					JTextField box=(JTextField)c[i];
					box.setText("");
					break;
				}
			}
		}
		else if(name.equals("uwepopup3"))
		{}
		
		//set panel entity to null
		Component[] c=getComponents();
		for(int i=0;i<c.length;i++)
		{
			if(!(c[i] instanceof UweItemPanel))
				continue;
			UweItemPanel panel=(UweItemPanel)c[i];
			
			String cName=panel.getName();
			if(cName==null)
				continue;
			if(!cName.equals(removed.getName()))
				continue;
			
			panel.setEntity(null);
			break;
		}
		
		//check btn clicked
		boolean clicked=false;
		for(int i=0;i<c.length;i++)
		{
			if(c[i] instanceof Button && c[i].getName().equals("combine"))
			{
				clicked=!c[i].isEnabled();
				break;
			}
		}
		//update combined text
		if(!clicked)
		{
			String name1="",name2="";
			for(int i=0;i<c.length;i++)
			{
				String cName=c[i].getName();
				if(cName==null)
					continue;
				if(cName.equals("line0"))
				{
					JTextField box=(JTextField)c[i];
					name1=box.getText();
				}
				else if(cName.equals("line1"))
				{
					JTextField box=(JTextField)c[i];
					name2=box.getText();
				}
			}
			for(int i=0;i<c.length;i++)
			{
				String cName=c[i].getName();
				if(cName==null)
					continue;
				if(cName.equals("line2"))
				{
					JTextField box=(JTextField)c[i];
					box.setText(name1+name2);
				}
			}
		}
		
		
	}
	
	private class BtnHandler implements ActionListener
	{
		private UweCombinatorViewPanel self;
		
		public BtnHandler(UweCombinatorViewPanel self)
		{
			this.self=self;
		}
		public void actionPerformed(ActionEvent e)
		{
			boolean bothHas=true;
			Component[] c=getComponents();
			for(int i=0;i<c.length;i++)
			{
				if(!(c[i] instanceof UweItemPanel))
					continue;
				UweItemPanel panel=(UweItemPanel)c[i];
				if(!panel.getName().equals("uwepopup3") && panel.getEntity()==null)
				{
					bothHas=false;
					break;
				}
				self.remove(panel);
			}
			if(!bothHas)
			{
				JOptionPane.showMessageDialog(null, "Please drag item into the item panels", "Error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			//find symbol =
			JLabel symTxt=null;
			for(int i=0;i<c.length;i++)
			{
				if(!(c[i] instanceof JLabel))
					continue;
				JLabel txt=(JLabel)c[i];
				if(!txt.getText().equals("<html><b>=</b></html>"))
					continue;
				
				symTxt=txt;
				break;
			}
			if(symTxt==null)
				return;
			
			//add panel 3
			panel3 = new UweItemPanel(null, null);
			panel3.setItemNumber(0);
			add(panel3);
			setX(panel3,10);
			setY(panel3,getY(symTxt)+getHeight(symTxt)+5);
			setWidth(panel3,50);
			setHeight(panel3,50);
			panel3.setAcceptedTypes(EntityMap.getClass("item", null, null));
			panel3.setParent(User.get());
			panel3.setName("uwepopup3");
			
			//blank text
			for(int i=0;i<c.length;i++)
			{
				if(c[i] instanceof JTextField && (c[i].getName().equals("line0") || c[i].getName().equals("line1")))
				{
					JTextField box=(JTextField)c[i];
					box.setText("");
				}
			}
			
			//unable btn
			for(int i=0;i<c.length;i++)
			{
				if(c[i] instanceof Button && c[i].getName().equals("combine"))
				{
					c[i].setEnabled(false);
					break;
				}
			}
			
			//update dialog
			self.validate();
			self.revalidate();
			self.repaint();
			
			//do combine
			UweClientAction.doCombine();
		}
	}
}
