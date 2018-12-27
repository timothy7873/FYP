package games.stendhal.server.actions;

import static games.stendhal.common.constants.Actions.UWERETURNITEM;

import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;

public class UweReturnItemAction implements ActionListener{
	public void onAction(Player player, RPAction action)
	{
		RPSlot bag=player.getSlot("bag");
		for(int i=0;i<10;i++)
		{
			RPSlot slot=player.getSlot("uwepopup"+i);
			RPObject item=slot.getFirst();
			if(item==null)
				continue;
			if(!bag.isFull())
			{
				
			}
			
		}

	}
	
	public static void register() {
		CommandCenter.register(UWERETURNITEM, new UweReturnItemAction());
	}
}
