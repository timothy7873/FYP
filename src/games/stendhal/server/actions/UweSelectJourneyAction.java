package games.stendhal.server.actions;

import static games.stendhal.common.constants.Actions.*;

import Util.game.server.UweNpcInfo;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.quests.uwe.UweNpc;
import marauroa.common.game.RPAction;

public class UweSelectJourneyAction implements ActionListener{
	public void onAction(Player player, RPAction action)
	{
		String npcId=action.get("npcId");
		String journeyId=action.get("journeyId");
		UweNpc npc=UweNpcInfo.npcs.get(npcId);
		if(npc==null)
		{
			return;
		}
		npc.curJourney=journeyId;
		
	}
	
	public static void register() {
		CommandCenter.register(UWESELECTJOURNEY, new UweSelectJourneyAction());
	}
}
