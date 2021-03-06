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
import games.stendhal.server.entity.npc.condition.QuestStartedCondition;

public class UweHelper implements LoadableContent{
	private String npcName = "UweHelper";
	private SpeakerNPC npc;
	private String helpQuestName="UweFillInBlankQuestNPC_help";
	
	private final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
	
	public UweHelper()
	{
		
	}
	
	private void buildConditions() 
	{
		
	}
	
	private void createNPC()
	{
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
		npc.setPosition(15, 7);
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
				"\nI am a quest helper, I can provide #hints of quest for you.", 
				null);
		//hints
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("hints","hint","h"), 
				new QuestStartedCondition(helpQuestName), 
				ConversationStates.ATTENDING,
				"\nThe entry of Java does not require return value.\n"+
						"Also, entry in java means the #main function.\n", 
				null);
		npc.add(ConversationStates.ATTENDING, 
				Arrays.asList("hints","hint","h"), 
				null, 
				ConversationStates.ATTENDING,
				"\nI don't have any hints for your current quest!\n", 
				null);
	}
	private void removeNPC(String name) {
		npc = NPCList.get().get(name);
		if (npc == null) {
			return;
		}
		npc.getZone().remove(npc);
	}
	@Override
	public void addToWorld() {
		//removeNPC(npcName);

		createNPC();
		addDialog();
		buildConditions();
	}
	@Override
	public boolean removeFromWorld() {
		removeNPC(npcName);

		return true;
	}
}
