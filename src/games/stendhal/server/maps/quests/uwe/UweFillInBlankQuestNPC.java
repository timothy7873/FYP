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
	private String answerItemName;
	
	public UweFillInBlankQuestNPC()
	{
		question="What is the entry of a Java program? \n"+
				"public static ____ ____(String[] args)";
		answerItemName="void main";
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

		//hi
		npc.add(ConversationStates.IDLE, 
				ConversationPhrases.GREETING_MESSAGES, 
				null, 
				ConversationStates.ATTENDING,
				"\nI am a quest NPC of fill in the blank, I can provide fill in the blank #quest for you.", 
				null);
		//quest
		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES, 
				new QuestNotStartedCondition(questName),
				ConversationStates.INFORMATION_1, 
				"\nI have many incompleted function that requires a serial of codes to fill in.\n"+
						"You can kill rats in this room to collect code items and combine them into one by UweCombinator.\n"+
						"Then you can submit the correct combined code item of quests to me!\n"+
						"If you do not know the answer, you can also ask for #help!\n"+
						"Are you ready to #start the quest?\n", 
				null);
		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES, 
				new QuestStartedCondition(questName),
				ConversationStates.INFORMATION_2, 
				"\nI find out that you have received a quest before by the record.\n"+
						"Let me remind you the question first:\n"+
						question+"\n\n"+
						"Do you want to #submit your answer code item now?", 
				null
				);
		
		//submit
		npc.add(ConversationStates.INFORMATION_2, 
				Arrays.asList("submit","s"), 
				new AndCondition(
						new QuestStartedCondition(questName), 
						new HasItemCondition(answerItemName)),
				ConversationStates.IDLE, 
				"Congradulation! Your answer is correct! Quest is completed! Enjoy your reward!", 
				new MultipleActions(
					new IncreaseKarmaAction(10), 
			        new IncreaseXPAction(25),
			        new RemoveItemAction(answerItemName),
			        new QuestCompleteAction(questName)
					)
				);
		npc.add(ConversationStates.INFORMATION_2, 
				Arrays.asList("submit","s"), 
				new AndCondition(
						new QuestStartedCondition(questName), 
						new HasItemCondition(answerItemName, true)),
				ConversationStates.IDLE, 
				"\nYou have not collect the correct code item! Try harder.\n"+
						"If you really can't find the answer, you may ask for #help!\nGood Luck!", 
				null
				);
		//start
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("start","s"), 
				new QuestNotStartedCondition(questName),
				ConversationStates.IDLE, 
				"\n"+question+"\n"+
						"Please go get the correct code item and come back to submit.\nGood Luck!", 
				new QuestStartAction(questName));
		//help
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("help","h"), 
				new QuestStartedCondition(questName),
				ConversationStates.IDLE, 
				"You can go find UweHelper to ask for some hints! He is good at programming! \nHave fun!", 
				new QuestStartAction(questName+"_help"));
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("help","h"), 
				new QuestNotStartedCondition(questName),
				ConversationStates.INFORMATION_1, 
				"I don't think you need help now as I don't find you have received quest before.\n"+
						"Do you want to #start a quest?\n", 
				null);
		
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
