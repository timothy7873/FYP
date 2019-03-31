package games.stendhal.server.entity.npc.action;

import Util.Management.JourneyRow;
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
	private String npcId;
	public UweFireQuestChatAction(String npcId)
	{
		this.npcId=npcId;
	}
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
		String journeyId="";
		RPEvent event=null;
		
		String quest=player.getQuest(npcId);
		if(quest==null)
			return;
		String[] strs=quest.split("_");
		if(strs.length!=2)
			return;
		journeyId=strs[1];
		
		boolean questValid=ManagementAPI.api.touchQuest(player.getName(), journeyId);
		if(!questValid)
			return;
		String type=ManagementAPI.api.getQuestType(player.getName(), journeyId);
		JourneyRow jr=ManagementAPI.api.getJourneyRow(player.getName(), journeyId);
		if(type.equals("Logic error"))
			event=new UweFlowIncQuestEvent("logical", "Logical error fixing",jr.journeyRowId);
		else if(type.equals("Syntax error"))
			event=new UweFlowIncQuestEvent("syntax", "Syntax error fixing",jr.journeyRowId);
		else if(type.equals("Tracing program"))
			event=new UweOutputIncQuestEvent("Program output tracing",jr.journeyRowId);
		else if(type.equals("Reorder"))
			event=new UweReorderQuestEvent("Program components reordering",jr.journeyRowId);
		else
			return;
		
		player.addEvent(event);
		player.notifyWorldAboutChanges();
	}
}
