package games.stendhal.server.entity.item;

import java.util.Iterator;
import java.util.LinkedList;

import games.stendhal.server.entity.PassiveEntity;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;

public class CombinatorCorpse extends Corpse{
	private static final String ATTR_NAME = "name";
	private static final String ATTR_IMAGE = "image";
	private static final String ATTR_HARMLESS_IMAGE = "harmless_image";
	private static final String CONTENT_SLOT = "content";
	
	public CombinatorCorpse(int x, int y)
	{
		super("", x, y);
		//super.put(ATTR_NAME, "Combination container");
		setName("container");
		super.put(ATTR_IMAGE, "player");
		super.put(ATTR_HARMLESS_IMAGE, "harmless_player");
	}
	
	public LinkedList<PassiveEntity> getItems()
	{
		LinkedList<PassiveEntity> result=new LinkedList<PassiveEntity>();
		
		RPSlot content = super.getSlot(CONTENT_SLOT);
		Iterator<RPObject> items=content.iterator();
		while(items.hasNext())
		{
			result.add((PassiveEntity)items.next());
		}
		return result;
	}
	public RPSlot getSlot()
	{
		return super.getSlot(CONTENT_SLOT);
	}
}