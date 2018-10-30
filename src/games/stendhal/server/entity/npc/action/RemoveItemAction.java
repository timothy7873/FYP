package games.stendhal.server.entity.npc.action;

import java.util.Iterator;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;

public class RemoveItemAction implements ChatAction{
	private String itemName;
	
	public RemoveItemAction(String itemName)
	{
		this.itemName=itemName;
	}
	
	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc)
	{
		RPSlot slot=player.getSlot("bag");
		Iterator<RPObject> items=slot.iterator();
		while(items.hasNext())
		{
			Item item=(Item)items.next();
			if(item.getName().equals(itemName))
			{
				slot.remove(item.getID());
				player.notifyWorldAboutChanges();
			}
		}
	}
}
