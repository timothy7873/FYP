package games.stendhal.server.entity.npc.action;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.actions.UweReturnItemAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPEvent;

public class UweFireQuestEventChatAction extends FireEventChatAction{
	public UweFireQuestEventChatAction(RPEvent event)
	{
		super(event);
	}
	
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc)
	{
		UweReturnItemAction.manuallyFire(player);
		super.fire(player, sentence, npc);
	}
}
