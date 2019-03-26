package games.stendhal.server.entity.npc.action;

import Util.Management.ManagementAPI;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.events.UweFlowIncQuestEvent;
import games.stendhal.server.events.UweOutputIncQuestEvent;
import games.stendhal.server.events.UweReorderQuestEvent;
import games.stendhal.server.maps.quests.uwe.UweNpc;
import marauroa.common.game.RPEvent;

public class UweFireQuestChatAction implements ChatAction{
	private UweNpc npc;
	public UweFireQuestChatAction(UweNpc npc)
	{
		this.npc=npc;
	}
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
		RPEvent event=null;
		
		String type=ManagementAPI.api.getQuestType(player.getName(), this.npc.curJourney);
		if(type.equals("Logic error"))
			event=new UweFlowIncQuestEvent(type, "Logical error fixing",this.npc.npcName);
		else if(type.equals("Syntax error"))
			event=new UweFlowIncQuestEvent(type, "Syntax error fixing",this.npc.npcName);
		else if(type.equals("Tracing program"))
			event=new UweOutputIncQuestEvent("Program output tracing",this.npc.npcName);
		else if(type.equals("Reorder"))
			event=new UweReorderQuestEvent("Program components reordering",this.npc.npcName);
		else
			return;
		
		player.addEvent(event);
		player.notifyWorldAboutChanges();
	}
}
