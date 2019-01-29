package games.stendhal.server.actions;

import static games.stendhal.common.constants.Actions.*;

import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;

public class UweReturnItemAction implements ActionListener{
	public void onAction(Player player, RPAction action)
	{
		manuallyFire(player);
	}
	
	public static void register() {
		CommandCenter.register(UWERETURNITEM, new UweReturnItemAction());
	}
	
	public static void manuallyFire(Player player)
	{
		RPSlot bag=player.getSlot("bag");
		for(int i=0;i<10;i++)
		{
			RPSlot slot=player.getSlot("uwepopup"+i);
			RPObject obj=slot.getFirst();
			if(obj==null || !(obj instanceof Item))
				continue;
			Item item=(Item)obj;
			
			
			item.removeFromWorld();
			if(!bag.isFull())
			{
				bag.add(item);
			}
			else
			{
				item.setPosition(player.getX(), player.getY());
				player.getZone().add(item, player);
			}
			//slot.remove(item.getID());
			
		}
		player.updateItemAtkDef();
		player.notifyWorldAboutChanges();
	}
}
