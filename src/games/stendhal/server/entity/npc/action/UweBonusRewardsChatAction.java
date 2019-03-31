package games.stendhal.server.entity.npc.action;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;

public class UweBonusRewardsChatAction implements ChatAction{
	public UweBonusRewardsChatAction()
	{}
	public void fire(Player player, Sentence sentence, EventRaiser npc)
	{
		player.addXP(10);
		
		player.updateItemAtkDef();
		player.update();
		player.notifyWorldAboutChanges();
	}
}
