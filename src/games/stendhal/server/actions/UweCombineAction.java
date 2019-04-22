package games.stendhal.server.actions;

import static games.stendhal.common.constants.Actions.UWECOMBINE;

import games.stendhal.server.entity.PassiveEntity;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.UweItemManager;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;

public class UweCombineAction implements ActionListener{

	public void onAction(Player player, RPAction action)
	{
		RPObject item1=player.getSlot("uwepopup1").getFirst();
		RPObject item2=player.getSlot("uwepopup2").getFirst();
		RPObject item3=UweItemManager.createCodeItem(item1.get("name")+item2.get("name"));
		
		player.getSlot("uwepopup3").add(item3);
		player.getSlot("uwepopup1").clear();
		player.getSlot("uwepopup2").clear();
		
		player.updateItemAtkDef();
		player.notifyWorldAboutChanges();
	}
	public static void register() {
		CommandCenter.register(UWECOMBINE, new UweCombineAction());
	}
	
	
}
