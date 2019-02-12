package games.stendhal.server.entity.npc.condition;

import Util.Management.ManagementAPI;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.player.Player;

public class UweYesNoTestValidCondition implements ChatCondition{
	private String npcId;
	
	public UweYesNoTestValidCondition(String npcId)
	{
		this.npcId=npcId;
	}
	
	public boolean fire(final Player player, final Sentence sentence, final Entity entity)
	{
		return ManagementAPI.api.getYesNoTests(npcId).length>0;
	}
}
