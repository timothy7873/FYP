package games.stendhal.server.maps.quests.uwe;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.UseListener;
import games.stendhal.server.core.rule.EntityManager;
import games.stendhal.server.core.rule.defaultruleset.DefaultItem;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.creature.AttackableCreature;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.creature.ItemGuardCreature;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.mapstuff.spawner.CreatureRespawnPoint;
import games.stendhal.server.entity.npc.PassiveNPC; 

public class UweDog implements LoadableContent {

	final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
	private final EntityManager manager= SingletonRepository.getEntityManager();
	
	@Override
	public void addToWorld() {
		// TODO Auto-generated method stub
		createPet();
	}

	@Override
	public boolean removeFromWorld() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * A puppy that can be petted.
	 */
	private static class Puppy extends PassiveNPC implements UseListener {
		/*@Override
		protected void createPath() {
			final List<Node> nodes = new LinkedList<Node>();
			nodes.add(new Node(23, 54));
			nodes.add(new Node(17, 54));
			nodes.add(new Node(17, 49));
			nodes.add(new Node(19, 49));
			nodes.add(new Node(19, 58));
			nodes.add(new Node(23, 58));
			setPath(new FixedPath(nodes, true));
		}*/

		@Override
		public boolean onUsed(RPEntity user) {
			if (nextTo(user)) {
				say("!me wags tail.");
				return true;
			}
			return false;
		}
	}
	public void createPet(){
		final PassiveNPC dog = new Puppy();

		dog.put("menu", "Pet|Use");
		// Not visible, but used for the emote action
		dog.setName("Puppy");
		dog.setPosition(15, 12);
		dog.setDescription("You see a playful puppy.");
		dog.setEntityClass("animal/puppy");
		dog.setBaseSpeed(0.5);
		dog.moveRandomly();
		dog.setRandomMovementRadius(25, true);
		dog.setSounds(Arrays.asList("dog-small-bark-1", "dog-small-bark-2"));
		zone.add(dog);
		
		//final List<String> slots = new LinkedList<String>();
		//slots.add("bag");
		/*
		DefaultItem item = new DefaultItem("key", "gold", "golden key", -1);
		item.setImplementation(Item.class);
		item.setWeight(1);
		item.setEquipableSlots(slots);
		manager.addItem(item);
		*/
		//Item item = 	manager.getItem( "golden key");
		Item item = 	manager.getItem( "=");
		item.put("menu", "Drink|Use");
		
		Creature rat = manager.getCreature("rat");

		// rat.addDropItem("golden key", 100, 2);
	   
	
		
		final Creature creature = new ItemGuardCreature(rat, "=");
	
		final CreatureRespawnPoint point = new CreatureRespawnPoint(zone, 13, 12, creature, 1);
		
	rat.setRespawnPoint(point);
	rat.setRespawned(true);
	point.setRespawnTime(400);
 	
		zone.add(point);
		 
	}

}