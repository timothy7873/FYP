package games.stendhal.server.maps.quests.uwe;

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
	private SpeakerNPC npc;
	private CombinatorCorpse corpse;

	private void buildConditions() {
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
		npc.setPosition(9, 10);
		npc.setDirection(Direction.UP);
		npc.initHP(100);
		// npc.setSpeed(1.0);
		zone.add(npc);
	}

	private void addDialog() {

		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES, null, ConversationStates.ATTENDING,
				"I am Combinator. I can #combine items for you. ", null);
		
		npc.addJob("I can provide items to power up yourself. ");
		//npc.addJob( "I am DataType NPC. Are you interested in Data Type?   I can tell you more #datatypes ");

		
		npc.add(ConversationStates.ATTENDING,
				Arrays.asList("combine","combinator","com","c"),
				null, 
				ConversationStates.ATTENDING, 
				"Enjoy! ",
				//null
				new CombinatorAction(npc, corpse)
		);
		
		
//		String images[] = { "http://localhost:8888/GameDesign/api/datatype.php?name=summary",
//				"https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=imgres&cd=&cad=rja&uact=8&ved=2ahUKEwiCv_K-tufdAhXHW7wKHWPRCasQjRx6BAgBEAU&url=https%3A%2F%2Fwww.apple.com%2Fhk-zh%2Fshop%2Fproduct%2FMK0C2ZA%2FA%2Fapple-pencil&psig=AOvVaw2XRYs8DbGdTVGbdSNWFF-S&ust=1538558312266411"};
//		npc.add(ConversationStates.ATTENDING,
//				Arrays.asList("primitive"),
//				null, ConversationStates.ATTENDING, "Here is the summary ",
//				//new ExamineChatAction(images[1], "Primitive Types", "Here is the summary")
//				//null
//		);
		  
	 
		


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
		corpse=new CombinatorCorpse(10,10);
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
