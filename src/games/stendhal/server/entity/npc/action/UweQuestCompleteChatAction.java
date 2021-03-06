package games.stendhal.server.entity.npc.action;

import Util.Management.Journey;
import Util.Management.JourneyRow;
import Util.Management.ManagementAPI;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.events.UweSpeakTextEvent;

public class UweQuestCompleteChatAction implements ChatAction{
	private String npcId;
	
	public UweQuestCompleteChatAction(String npcId)
	{
		this.npcId=npcId;
	}
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc)
	{
		try
		{
			//perform after text
			String quest=player.getQuest(npcId);
			String journeyRowId=quest.substring(5, quest.length());
			JourneyRow jr=ManagementAPI.api.getJourneyRow(journeyRowId);
			
			player.addEvent(new UweSpeakTextEvent(jr.speakAfter));
			player.notifyWorldAboutChanges();
			
			//check has next journeyrow record
			if(ManagementAPI.api.hasNextJourneyRow(player.getName(), jr.journeyId))
			{
				//add next journey row record
				ManagementAPI.api.nextJourneyRow(player.getName(), jr.journeyId);
			}
			else
			{
				//perform ending
				Journey j=ManagementAPI.api.getJourney(jr.journeyId);
				
				player.addEvent(new UweSpeakTextEvent(j.ending));
				player.notifyWorldAboutChanges();
			}
		}
		catch(Exception e) {}
		
		
		
	}
	
}
