package games.stendhal.server.maps.quests.uwe;

import java.util.LinkedList;
import java.util.List;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SayTextAction;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.QuestActiveCondition;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestNotCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.maps.quests.uwe.actions.StartRecordingMeetsAction;

public class UweQuestMakerNPC implements LoadableContent {

	private final String UweQuestMaker = "UweQuestMaker";
	private static final String QUEST_SLOT = "meet_uwe_quest_maker";

	private SpeakerNPC npc;

	final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("0_semos_village_w");

	

	@Override
	public void addToWorld() {
		removeNPC(UweQuestMaker);
 
		createNPC();

		addDialog();
	}



	private void createNPC() {

		npc = new SpeakerNPC(UweQuestMaker) {
			@Override
			protected void createPath() {

			}

			@Override
			protected void createDialog() {
				// done outside
			}
		};
		npc.setEntityClass("noimagenpc");
		npc.setCollisionAction(CollisionAction.REVERSE);
		npc.setPosition(16, 37);
		npc.setDirection(Direction.DOWN);
		npc.initHP(100);
		zone.add(npc);
	}

	private void addDialog() {
		// greeting
		addGreetingDependingOnQuestState();

		npc.addJob("I am uwe Quest Maker");
		npc.addGoodbye("Bye Have fun!");
		npc.addHelp("Help message");
	}

	private void addGreetingDependingOnQuestState() {
 
		// meet this NPC the first time
		npc.add(ConversationStates.IDLE, 
				ConversationPhrases.GREETING_MESSAGES,
				new AndCondition(
						new GreetingMatchesNameCondition(npc.getName()),
						new QuestNotCompletedCondition(UweQuestMaker)
						),
				ConversationStates.INFORMATION_1, 
				"First Time meet........start quest ? Yes or No", 
				new SetQuestAction(UweQuestMaker, "done"));
		
		
		npc.addGreeting(null, new SayTextAction("Hi again, [name]. xxxxxx"));

		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES,
				new AndCondition(
						new GreetingMatchesNameCondition(npc.getName()) ),
				ConversationStates.INFORMATION_1, 
				" start quest? Yes or No"
				+ "", 
				null);
		
		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES,
				new AndCondition(
						new GreetingMatchesNameCondition(npc.getName()),
						new QuestActiveCondition(QUEST_SLOT)),
				ConversationStates.ATTENDING, 
				"You have not #completed the quest. Go and talk with XXXX"
				+ "", 
				null);
		
		
		npc.add(ConversationStates.IDLE, 
				ConversationPhrases.GREETING_MESSAGES,
				new AndCondition(
						new GreetingMatchesNameCondition(npc.getName()),
						new QuestCompletedCondition(QUEST_SLOT)),
				ConversationStates.ATTENDING, 
				"You have completed the quest. You can #restart the quest again.", 
				null);
	
	
	}

	@Override
	public boolean removeFromWorld() {
		removeNPC(UweQuestMaker);

		return true;
	}

	/**
	 * removes an NPC from the world and NPC list
	 *
	 * @param name
	 *            name of NPC
	 */
	private void removeNPC(String name) {
		SpeakerNPC npc = NPCList.get().get(name);
		if (npc == null) {
			return;
		}
		npc.getZone().remove(npc);
	}

}
