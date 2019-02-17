package games.stendhal.server.maps.quests.uwe;

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
import games.stendhal.server.entity.npc.action.UweFireQuestEventChatAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.OrCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.npc.condition.QuestStartedCondition;
import games.stendhal.server.events.UweFlowIncQuestEvent;

public class UweSyntaxErrorQuestNPC implements LoadableContent{
	private String npcName = "UweSyntaxErrorQuestNPC";
	private final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
	
	private String leaderNpc;
	private SpeakerNPC npc;
	
	public UweSyntaxErrorQuestNPC(String leaderNpc)
	{
		this.leaderNpc=leaderNpc;
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
		npc.setPosition(5, 15);
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
				"\nI am a quest NPC of syntax error fixing, are you looking for #quest?.", 
				null);
		//quest
		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES, 
				new AndCondition(
						new QuestStartedCondition(leaderNpc), 
						new OrCondition(
								new QuestInStateCondition(leaderNpc, "started"), 
								new QuestInStateCondition(leaderNpc, "read"))),
				ConversationStates.ATTENDING, 
				"Please help me fix the code", 
				new MultipleActions(
						new SetQuestAction(leaderNpc, "read"),
						new UweFireQuestEventChatAction(new UweFlowIncQuestEvent("syntax", "syntax error fixing quest", leaderNpc)))
				);
		npc.add(ConversationStates.ATTENDING, 
				ConversationPhrases.QUEST_MESSAGES, 
				new OrCondition(
						new QuestNotStartedCondition(leaderNpc),
						new QuestInStateCondition(leaderNpc, "blank"), 
						new QuestInStateCondition(leaderNpc, "done")),
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
