package games.stendhal.server.entity.npc.action;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPEvent;

public class FireEventChatAction implements ChatAction{
	private RPEvent event;
	
	public FireEventChatAction(RPEvent event)
	{
		this.event=event;
	}
	
	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
		player.addEvent(event);
		player.notifyWorldAboutChanges();
	}
}
