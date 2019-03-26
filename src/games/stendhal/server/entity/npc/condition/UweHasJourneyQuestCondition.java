package games.stendhal.server.entity.npc.condition;

import Util.Management.Journey;
import Util.Management.ManagementAPI;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.player.Player;

public class UweHasJourneyQuestCondition implements ChatCondition{
	private String npcId;
	
	public UweHasJourneyQuestCondition(String npcId)
	{
		this.npcId=npcId;
	}
	
	public boolean fire(final Player player, final Sentence sentence, final Entity entity)
	{
		Journey[] js=ManagementAPI.api.getOnDoingJourney(player.getName(), npcId);
		return js!=null && js.length>0;
	}
}
