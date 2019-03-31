package games.stendhal.server.actions;

import static games.stendhal.common.constants.Actions.*;

import Util.game.server.UweNpcInfo;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPAction;

public class UweSelectJourneyAction implements ActionListener{
	public void onAction(Player player, RPAction action)
	{
		String npcId=action.get("npcId");
		String journeyId=action.get("journeyId");
		
		UweNpcInfo.setNpcQuestStart(player, npcId, journeyId);
		UweNpcInfo.callJourneySetected(npcId);
	}
	
	public static void register() {
		CommandCenter.register(UWESELECTJOURNEY, new UweSelectJourneyAction());
	}
}
