package games.stendhal.server.entity.npc.action;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;

public class UweStartQuestAction implements ChatAction{
	private String questName;
	private String defaultState;
	
	public UweStartQuestAction(String questName, String defaultState)
	{
		this.questName=questName;
		this.defaultState=defaultState;
	}
	
	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc)
	{
		if(!player.hasQuest(questName))
			player.setQuest(questName, defaultState);
	}
}
