package games.stendhal.server.maps.quests.uwe;

import java.awt.Point;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.action.UweFireQuestChatAction;
import games.stendhal.server.entity.npc.action.UweFireQuestEventChatAction;
import games.stendhal.server.entity.npc.action.UweSetJourneyQuestReadChatAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.NotCondition;
import games.stendhal.server.entity.npc.condition.OrCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.npc.condition.QuestStartedCondition;
import games.stendhal.server.entity.npc.condition.UweJourneyQuestTotalCleanCondition;
import games.stendhal.server.entity.npc.condition.UweJourneyQuestReadCondition;
import games.stendhal.server.entity.npc.condition.UweJourneyQuestStartedCondition;
import games.stendhal.server.events.UweReorderQuestEvent;

public class UweJourneyQuestOneNPC extends UweNpc{
	private String npcName = "UweReorderQuestNPC";
	//private final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
	protected final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("-1_semos_dungeon");
	protected final Point spawnPoint = new Point(20,40);
	
	private UweNpc leaderNpc;
	private SpeakerNPC npc;
	
	public UweJourneyQuestOneNPC(UweNpc leaderNpc,String npcId)
	{
		this.leaderNpc=leaderNpc;
		this.npcName=npcId;
	}
	
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
		npc.setPosition(spawnPoint.x, spawnPoint.y);
		npc.setDirection(Direction.UP);
		npc.initHP(100);
		// npc.setSpeed(1.0);
		zone.add(npc);
	}
	private void addDialog()
	{
		//hi
		npc.add(ConversationStates.IDLE, 
				ConversationPhrases.GREETING_MESSAGES, 
				null, 
				ConversationStates.ATTENDING,
				"\nI am a "+npcName+", are you looking for #quest?.", 
				null);
		//quest
		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES, 
				new AndCondition(
						new QuestStartedCondition(leaderNpc.npcName), 
						new OrCondition(
								new UweJourneyQuestStartedCondition(leaderNpc.npcName), 
								new UweJourneyQuestReadCondition(leaderNpc.npcName))),
				ConversationStates.ATTENDING, 
				"Please help me fix the code", 
				new MultipleActions(
						new UweSetJourneyQuestReadChatAction(leaderNpc.npcName),
						new UweFireQuestChatAction(leaderNpc.npcName))
						
				);
		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES, 
				new OrCondition(
						new QuestNotStartedCondition(leaderNpc.npcName),
						new UweJourneyQuestTotalCleanCondition(leaderNpc.npcName), 
						new QuestInStateCondition(leaderNpc.npcName, "done")),
				ConversationStates.ATTENDING, 
				"Oh i have no quest in here currently", 
				null);
		
		
		//bye
		npc.addGoodbye("Have fun!");
		
	}
	private void buildConditions()
	{}
	
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
