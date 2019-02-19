package games.stendhal.server.entity.npc.action;

import Util.Management.ManagementAPI;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;

public class UweTouchReorderQuestChatAction implements ChatAction{
	private SpeakerNPC npc;
	private String success;
	private String failed;
	private ConversationStates next;
	
	public UweTouchReorderQuestChatAction(SpeakerNPC npc, String success, String failed, ConversationStates next)
	{
		this.npc=npc;
		this.success=success;
		this.failed=failed;
		this.next=next;
	}
	
	@Override
	public void fire(Player player, Sentence sentence, EventRaiser npc) {
		// TODO Auto-generated method stub
		if(ManagementAPI.api.getReorderQuestion(npc.getName(), player.getName())==null)
		{
			npc.say(failed);
		}
		else
		{
			npc.setCurrentState(next);
			npc.say(success);
		}
		ManagementAPI.api.stopTimeCount(npc.getName(), player.getName());
	}
}
