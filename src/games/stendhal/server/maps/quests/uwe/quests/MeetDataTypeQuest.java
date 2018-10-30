package games.stendhal.server.maps.quests.uwe.quests;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.quests.AbstractQuest;

public class MeetDataTypeQuest extends AbstractQuest {

	

	private static final String QUEST_SLOT = "DataTypeQuest";
	private static Logger logger = Logger.getLogger(MeetDataTypeQuest.class);
	
	@Override
	public List<String> getHistory(Player player) {
		final List<String> res = new ArrayList<String>();
		

		if (!player.hasQuest(QUEST_SLOT)) {
			return res;
		}
		final String questState = player.getQuest(QUEST_SLOT);

		
		res.add("Meet Data Type Quest is just started ");
		
	//	if (player.getQuest(QUEST_SLOT, 0).equals("start")) {
	//		return res;
	//	}
		
		// if things have gone wrong and the quest state didn't match any of the above,
		// debug a bit:
		final List<String> debug = new ArrayList<String>();

		debug.add("Quest state is: " + QUEST_SLOT +":" + questState);
		logger.error("History doesn't have a matching quest state for " + QUEST_SLOT +":"+ questState);
		return debug;
	}

	@Override
	public String getSlotName() { 
		return QUEST_SLOT;
	}

	@Override
	public void addToWorld() { 
		
	}

	@Override
	public String getName() { 
		return MeetDataTypeQuest.class.getName();
	} 

}
