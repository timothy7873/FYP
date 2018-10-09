package games.stendhal.server.entity.npc.action;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;

public class QuestStartAction implements ChatAction{
	private String questName;
	
	public QuestStartAction(String questName)
	{
		this.questName=questName;
	}
	
	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc)
	{
		player.setQuest(questName, "start");
	}
}
