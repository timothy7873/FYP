package games.stendhal.server.entity.npc.action;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.quests.uwe.UweNpc;

public class UweSetJourneyQuestReadChatAction implements ChatAction{
	private String npcId;
	
	public UweSetJourneyQuestReadChatAction(String npcId)
	{
		this.npcId=npcId;
	}
	
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc)//cur state must be start
	{
		String journeyId="";
		String quest=player.getQuest(npcId);
		if(quest==null)
			return;
		String[] strs=quest.split("_");
		if(strs.length!=2)
			return;
		journeyId=strs[1];
		
		player.setQuest(npcId, "read_"+journeyId);
	}
}
