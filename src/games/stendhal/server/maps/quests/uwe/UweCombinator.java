package games.stendhal.server.maps.quests.uwe;

import java.awt.Point;
import java.util.Arrays;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.item.CombinatorCorpse;
import games.stendhal.server.entity.item.Corpse;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.CombinatorAction;
import games.stendhal.server.entity.npc.action.ExamineChatAction;

public class UweCombinator implements LoadableContent{
	private String npcName = "UweCombinator";
	final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
	private final Point pos=new Point(41,28);
	
	private SpeakerNPC npc;
	private CombinatorCorpse corpse;
	
	public UweCombinator()
	{}

	private void buildConditions() {
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
		npc.setPosition(pos.x, pos.y);
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
				"I am Combinator. I can #combine items for you. ", 
				null);
		npc.add(ConversationStates.ATTENDING,
				Arrays.asList("combine","combinator","com","c"),
				null, 
				ConversationStates.ATTENDING, 
				"Combination complete, enjoy! ",
				//null
				new CombinatorAction(npc, corpse)
		);
		npc.addJob("I can provide items to power up yourself. ");
		npc.addQuest("I don't have any quests for you, I am a combinator! ");
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
	
	private void createCorpse()
	{
		corpse=new CombinatorCorpse(pos.x+1,pos.y);
		zone.add(corpse);
	}

	/**
	 * removes Susi from her home in Ados and adds her to the Mine Towns.
	 */
	@Override
	public void addToWorld() {
		//removeNPC(npcName);

		//buildConditions();
		//createNPC();

		//addDialog();
		
		createCorpse();
		createNPC();
		
		addDialog();
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