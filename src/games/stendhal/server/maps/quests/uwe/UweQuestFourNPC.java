package games.stendhal.server.maps.quests.uwe;

import java.util.Arrays;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.action.UweProvideHintAction;
import games.stendhal.server.entity.npc.action.UweStartQuestAction;
import games.stendhal.server.entity.npc.action.UweTouchProgramTracingQuestChatAction;
import games.stendhal.server.entity.npc.action.UweTouchReorderQuestChatAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.NotCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestStartedCondition;
import games.stendhal.server.entity.npc.condition.UweYesNoTestValidCondition;

public class UweQuestFourNPC implements LoadableContent{
	public static String npcName = "UweQuestFour";
	private final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
	
	private SpeakerNPC npc;
	
	public UweQuestFourNPC(){}
	
	@Override
	public void addToWorld() {
		//removeNPC(npcName);

		createNPC();
		addDialog();
		buildConditions();
	}
	private void createNPC() {

		npc = new SpeakerNPC(npcName) {
			@Override
			protected void createPath() {}
			@Override
			protected void createDialog() {}
		};

		npc.setEntityClass("noimagenpc");
		npc.setCollisionAction(CollisionAction.REVERSE);
		npc.setPosition(13, 11);
		npc.setDirection(Direction.UP);
		npc.initHP(100);
		zone.add(npc);
	}
	private void addDialog()
	{
		//hi
		npc.add(ConversationStates.IDLE, 
				ConversationPhrases.GREETING_MESSAGES, 
				null, 
				ConversationStates.ATTENDING,
				"\nI am a quest four NPC, I can provide #quest for you.", 
				new UweStartQuestAction(npcName, "blank"));
		//quest
		
		
		//quest blank
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("quest","q"), 
				new AndCondition(
						new QuestStartedCondition(npcName), 
						new QuestInStateCondition(npcName, "blank")), 
				ConversationStates.ATTENDING, 
				null, 
				new UweTouchReorderQuestChatAction(npc,
						"Are you familiar with program understanding? #Yes/ #No/ #A #bit",
						"Sorry! We currently have no quests that can provide to you!",
						ConversationStates.INFORMATION_1));
		
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("Yes","yes","y"), 
				null, 
				ConversationStates.ATTENDING, 
				"Please go find #UweReorderQuestNPC and get the quest", 
				new SetQuestAction(npcName, "started"));
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("A bit","a bit","Abit","abit","ab","a"), 
				new UweYesNoTestValidCondition(npcName), 
				ConversationStates.ATTENDING, 
				"Please go find #UweTestFourNPC to practise java", 
				null);
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("A bit","a bit","Abit","abit","ab","a"), 
				new NotCondition(new UweYesNoTestValidCondition(npcName)), 
				ConversationStates.ATTENDING, 
				"Please visit #http://www.google.com/ to find resources to learn java", 
				null);
		npc.add(ConversationStates.INFORMATION_1, 
				Arrays.asList("No","no","n"), 
				null, 
				ConversationStates.ATTENDING, 
				"Please visit #http://www.google.com/ to find resources to learn java", 
				null);
		
		
		//quest done
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("quest","q"), 
				new AndCondition(
						new QuestStartedCondition(npcName), 
						new QuestInStateCondition(npcName, "done")), 
				ConversationStates.ATTENDING, 
				"Good job, you have complete the previous quest\nLet me give you some bonus reward\nWe are welcome if you want more #quest", 
				new SetQuestAction(npcName, "blank"));
		
		//quest doing
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("quest","q"), 
				new AndCondition(
						new QuestStartedCondition(npcName), 
						new QuestInStateCondition(npcName, "started")), 
				ConversationStates.ATTENDING, 
				"Please go find #UweProgramTracingQuestNPC and get the quest about java code", 
				null);
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("quest","q"), 
				new AndCondition(
						new QuestStartedCondition(npcName), 
						new QuestInStateCondition(npcName, "read")), 
				ConversationStates.INFORMATION_2, 
				"Oh you came back, if you think the quest is too hard, we can provide some #hints to you", 
				null);
		npc.add(ConversationStates.INFORMATION_2, 
				Arrays.asList("hints","hint","h"), 
				null, 
				ConversationStates.IDLE, 
				null, 
				new UweProvideHintAction(npc));
		
		//bye
		npc.addGoodbye("Have fun!");
		
	}
	private void buildConditions(){}
	
	
	@Override
	public boolean removeFromWorld() {
		removeNPC(npcName);
		return true;
	}
	private void removeNPC(String name) {
		npc = NPCList.get().get(name);
		if (npc == null) {
			return;
		}
		npc.getZone().remove(npc);
	}
}
