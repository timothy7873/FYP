package games.stendhal.server.maps.quests.uwe.actions;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.annotations.Dev;
import games.stendhal.server.core.config.annotations.Dev.Category;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;
import marauroa.common.Pair;

@Dev(category = Category.MEETS, label = "State")
public class MeetNPCChatAction implements ChatAction {

	private final String npcName;
	private   String questname;
	private  int index;

	public MeetNPCChatAction(final String questSlot, @Dev(defaultValue = "1") final int index, String npcName) {

		this.questname = checkNotNull(questSlot);
		this.npcName =  checkNotNull(npcName);
		this.index = index;
	}
	
	public MeetNPCChatAction( final String npcName) {
 
		this.npcName =  checkNotNull(npcName);
 
	}
	 
	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
		player.setSharedMeet(npcName);
		
	}

}
