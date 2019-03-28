package games.stendhal.server.entity.npc.condition;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.player.Player;

public class UweJourneyQuestNewlyStartedCondition implements ChatCondition{
	private String npcId;
	
	public UweJourneyQuestNewlyStartedCondition(String npcId)
	{
		this.npcId=npcId;
	}
	
	public boolean fire(final Player player, final Sentence sentence, final Entity entity)
	{
		String state="start_";
		
		if(!player.hasQuest(npcId))
			return false;
		
		String quest=player.getQuest(npcId);
		return quest.length()>state.length() && quest.substring(0, state.length()).equals(state);
	}
}
