package games.stendhal.server.entity.npc.action;

import Util.game.server.UweNpcInfo;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;

public class UweInitQuestStateAction implements ChatAction{
	private String npcId;
	
	public UweInitQuestStateAction(String npcId)
	{
		this.npcId=npcId;
	}
	
	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc)
	{
		if(!player.hasQuest(npcId))
			UweNpcInfo.setNpcQuestClean(player, npcId);
	}
}
