package games.stendhal.server.maps.quests.uwe.quests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.EquipItemAction;
import games.stendhal.server.entity.npc.action.IncreaseXPAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.KilledForQuestCondition;
import games.stendhal.server.entity.npc.condition.QuestActiveCondition;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.quests.AbstractQuest;
import games.stendhal.server.maps.quests.uwe.actions.StartRecordingMeetsAction;
import games.stendhal.server.maps.quests.uwe.conditions.MetForQuestCondition;

public class Q1 extends AbstractQuest {

	private static final String QUEST_SLOT = "meet_uwe_quest_maker";
 

	private static Logger logger = Logger.getLogger(MeetDataTypeQuest.class);

	private ChatCondition neverAnswer;
	private ChatCondition questStarted;
	private ChatCondition questCompleted;
	
	private void buildConditions() {

		neverAnswer = new QuestNotStartedCondition(QUEST_SLOT);
		questStarted = new QuestActiveCondition(QUEST_SLOT);
		questCompleted = new QuestCompletedCondition(QUEST_SLOT);
	}
	
	@Override
	public List<String> getHistory(Player player) {

		final List<String> res = new ArrayList<String>();

		if (!player.hasQuest(QUEST_SLOT)) {
			return res;
		}

		final String questState = player.getQuest(QUEST_SLOT);

		res.add("Q1 is started");
		if (player.getQuest(QUEST_SLOT, 0).equals("start") 
				&& new MetForQuestCondition(QUEST_SLOT,1).fire(player, null, null)) {
			res.add("I have met UweComparison and should go back");
		}
		
		if (player.getQuest(QUEST_SLOT, 0).equals("start")) {
			return res;
		}

	 
		
		if (player.getQuest(QUEST_SLOT, 0).equals("done")) {
			res.add("I have met UweComparison ");
			res.add("Q1 is completed");
			return res;
		}

		final List<String> debug = new ArrayList<String>();

		debug.add("Quest state is: " + QUEST_SLOT + ":" + questState);
		logger.error("History doesn't have a matching quest state for " + QUEST_SLOT + ":" + questState);
		return debug;
	}

	@Override
	public String getSlotName() {
		return QUEST_SLOT;
	}

	@Override
	public void addToWorld() {
		fillQuestInfo("Q1", "Q1111", true);
		prepareQ1();
	}

	private void prepareQ1() {
		buildConditions();

		final SpeakerNPC npc = npcs.get("UweQuestMaker");
		
		final List<ChatAction> actions = new LinkedList<ChatAction>();
		actions.add(new SetQuestAction(QUEST_SLOT, 0, "start"));
		actions.add(new StartRecordingMeetsAction(QUEST_SLOT, 1, "UweComparison", 0, 1));
		
		npc.add(ConversationStates.INFORMATION_1, 
				ConversationPhrases.YES_MESSAGES,
				null, 
				ConversationStates.ATTENDING,
				"Good. Go and meet XXX at XXX and learn something",
				new MultipleActions(actions));
		 
		npc.add(ConversationStates.ATTENDING, 
				"restart",
				new AndCondition(
						new GreetingMatchesNameCondition(npc.getName()),
						new QuestCompletedCondition(QUEST_SLOT)),
				
				ConversationStates.ATTENDING,
				"restarting...  Go and meet XXX at XXX and learn something",
				new MultipleActions(actions));
		
		npc.add(ConversationStates.INFORMATION_1, 
				ConversationPhrases.NO_MESSAGES,
			   null,
				
				ConversationStates.IDLE,
				"It is ok.... ",
				null);

	 

		final List<ChatAction> reward = new LinkedList<ChatAction>();
		reward.add(new SetQuestAction(QUEST_SLOT, 0, "done"));
		reward.add(new EquipItemAction("money", 5));
		reward.add(new IncreaseXPAction(10)); 
		
		npc.add(ConversationStates.ATTENDING, 
				"completed",
				new AndCondition(
						new GreetingMatchesNameCondition(npc.getName()),
					    new MetForQuestCondition(QUEST_SLOT, 1)
						),
				ConversationStates.ATTENDING, 
				"good", 
				new MultipleActions(reward));
		


	}

	@Override
	public String getName() {
		return "Q1";
	}

}
