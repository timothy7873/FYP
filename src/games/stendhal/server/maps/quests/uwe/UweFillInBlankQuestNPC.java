package games.stendhal.server.maps.quests.uwe;

import java.util.Arrays;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.item.CombinatorCorpse;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.CombinatorAction;
import games.stendhal.server.entity.npc.action.IncreaseKarmaAction;
import games.stendhal.server.entity.npc.action.IncreaseXPAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.QuestCompleteAction;
import games.stendhal.server.entity.npc.action.QuestStartAction;
import games.stendhal.server.entity.npc.action.RemoveItemAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.npc.condition.QuestStartedCondition;
import games.stendhal.server.entity.npc.condition.HasItemCondition;

public class UweFillInBlankQuestNPC implements LoadableContent{
	private String npcName = "UweFillInBlankQuestNPC";
	private String questName="UweFillInBlankQuestNPC";
	private SpeakerNPC npc;
	
	private String question;
	
	public UweFillInBlankQuestNPC()
	{
		question="What is the entry of a Java program? \n"+
				"public static void ____(String[] args)";
	}

	private void buildConditions() 
	{

	}

	final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");

	private void createNPC() {

		npc = new SpeakerNPC(npcName) {

			@Override
			protected void createPath() {

			}

			@Override
			protected void createDialog() {
				// done outside
			}
		};

		// npc.setOutfit(new Outfit(0, 4, 7, 32, 13));
		npc.setEntityClass("noimagenpc");
		npc.setCollisionAction(CollisionAction.REVERSE);
		npc.setPosition(15, 15);
		npc.setDirection(Direction.UP);
		npc.initHP(100);
		// npc.setSpeed(1.0);
		zone.add(npc);
	}

	private void addDialog() {

		npc.add(ConversationStates.IDLE, 
				ConversationPhrases.GREETING_MESSAGES, 
				null, 
				ConversationStates.ATTENDING,
				"I am a sample of fill in blank quest NPC, I can provide sample #quest for you. ", 
				null);
		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES, 
				new QuestNotStartedCondition(questName),
				ConversationStates.ATTENDING, 
				question, 
				new QuestStartAction(questName));
		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES, 
				new AndCondition(
						new QuestStartedCondition(questName), 
						new HasItemCondition("club")),
				ConversationStates.ATTENDING, 
				"Quest complete! ", 
				new MultipleActions(
					new IncreaseKarmaAction(10), 
			        new IncreaseXPAction(25),
			        new RemoveItemAction("club"),
			        new QuestCompleteAction(questName)
					)
				);
		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES, 
				new AndCondition(
						new QuestStartedCondition(questName), 
						new HasItemCondition("club", true)),
				ConversationStates.ATTENDING, 
				"You haven't collect the correct answer! \n"+question, 
				null
				);
		
		npc.addJob("I can provide quest for you to complete. ");
		//npc.addQuest("I don't have any quests for you, I am a combinator! ");
		npc.addGoodbye("Have fun!");
	}

	/**
	 * removes an NPC from the world and NPC list
	 *
	 * @param name
	 *            name of NPC
	 */
	private void removeNPC(String name) {
		npc = NPCList.get().get(name);
		if (npc == null) {
			return;
		}
		npc.getZone().remove(npc);
	}

	/**
	 * removes Susi from her home in Ados and adds her to the Mine Towns.
	 */
	@Override
	public void addToWorld() {
		//removeNPC(npcName);

		buildConditions();
		
		createNPC();
		addDialog();
		buildConditions();
	}

	/**
	 * removes Susi from the Mine Town and places her back into her home in Ados.
	 *
	 * @return <code>true</code>, if the content was removed, <code>false</code>
	 *         otherwise
	 */
	@Override
	public boolean removeFromWorld() {
		removeNPC(npcName);

		// final StendhalRPZone zone =
		// SingletonRepository.getRPWorld().getZone("int_semos_frank_house");
		// new LittleGirlNPC().createGirlNPC(zone);

		return true;
	}
}
