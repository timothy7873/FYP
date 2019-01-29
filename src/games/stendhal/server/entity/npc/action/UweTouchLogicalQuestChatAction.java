package games.stendhal.server.entity.npc.action;

import Util.Management.ManagementAPI;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;

public class UweTouchLogicalQuestChatAction implements ChatAction{
	SpeakerNPC npc;
	
	public UweTouchLogicalQuestChatAction(SpeakerNPC npc)
	{
		this.npc=npc;
	}
	
	@Override
	public void fire(Player player, Sentence sentence, EventRaiser npc) {
		// TODO Auto-generated method stub
		if(ManagementAPI.api.getLogicalQuestion(npc.getName(), player.getName())==null)
		{
			npc.say("Sorry! We currently have no quests that can provide to you!");
		}
		else
		{
			npc.setCurrentState(ConversationStates.INFORMATION_1);
			npc.say("Are you familar with java code? #Yes/ #No/ #A #bit");
		}
		ManagementAPI.api.stopTimeCount(npc.getName(), player.getName());
	}
	
}
