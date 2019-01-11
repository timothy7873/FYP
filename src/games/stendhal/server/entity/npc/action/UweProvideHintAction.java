package games.stendhal.server.entity.npc.action;

import Util.Management.ManagementAPI;
import Util.Management.QuestHint;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;

public class UweProvideHintAction implements ChatAction{
	private SpeakerNPC npc;
	
	public UweProvideHintAction(SpeakerNPC npc)
	{
		this.npc=npc;
	}
	
	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc)
	{
		QuestHint h=ManagementAPI.api.getHint(npc.getName(), player.getName());
		if(h!=null)
			npc.say("Here is the hint for your current quest: "+"#"+h.hint.replaceAll(" ", " #").replaceAll("\n", "\n#"));
		else
			npc.say("There is no more hints for your current quest");
		npc.say("Good luck! See you next time");
	}
}
