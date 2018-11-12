package games.stendhal.client.gui;

import java.awt.Point;

import games.stendhal.client.entity.IEntity;
import games.stendhal.client.sprite.Sprite;

public class UweItemPanel extends ItemPanel{
	public static UweItemPanel lastDraggedTarget=null;
	
	public UweItemPanel(final String slotName, final Sprite placeholder)
	{
		super(slotName,placeholder);
	}
	
	public void dropEntity(IEntity entity, int amount, Point point)
	{
		lastDraggedTarget=this;
		super.dropEntity(entity, amount, point);
	}
}
