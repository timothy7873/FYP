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

public class UweCreature implements LoadableContent {

	final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
	private final EntityManager em = SingletonRepository.getEntityManager();

	@Override
	public void addToWorld() {
		// TODO Auto-generated method stub
		createCreature();
		//createCreature1();
	}

	@Override
	public boolean removeFromWorld() {
		// TODO Auto-generated method stub
		return false;
	}

	public void createCreature() {

		// final List<String> slots = new LinkedList<String>();
		// slots.add("bag");
		/*
		 * DefaultItem item = new DefaultItem("key", "gold", "golden key", -1);
		 * item.setImplementation(Item.class); item.setWeight(1);
		 * item.setEquipableSlots(slots); manager.addItem(item);
		 */
		// Item item = manager.getItem( "golden key");
	//	Item item = em.getItem("=");
		//item.put("menu", "Drink|Use");

		Creature rat = em.getCreature("rat");

		//rat.addDropItem("golden key", 100, 2);

		final Creature creature = new ItemGuardCreature(rat, "=");

		creature.addDropItem("golden key", 100, 2);
		
		final CreatureRespawnPoint point = new CreatureRespawnPoint(zone, 13, 12, creature, 1);

		rat.setRespawnPoint(point);
		rat.setRespawned(true);
		point.setRespawnTime(5);

		zone.add(point);
		point.spawnNow();
		
		Creature rat1 = em.getCreature("rat");

		// rat.addDropItem("golden key", 100, 2);

		final Creature creature1 = new ItemGuardCreature(rat1, "=");

		final CreatureRespawnPoint point1 = new CreatureRespawnPoint(zone, 5, 10, creature1, 1);

		rat1.setRespawnPoint(point1);
		rat1.setRespawned(true);
		point1.setRespawnTime(5);

		zone.add(point1);

	}
	public void createCreature1() {

		// final List<String> slots = new LinkedList<String>();
		// slots.add("bag");
		/*
		 * DefaultItem item = new DefaultItem("key", "gold", "golden key", -1);
		 * item.setImplementation(Item.class); item.setWeight(1);
		 * item.setEquipableSlots(slots); manager.addItem(item);
		 */
		// Item item = manager.getItem( "golden key");
	//	Item item = em.getItem("=");
		//item.put("menu", "Drink|Use");

		Creature rat = em.getCreature("rat");

		 //rat.addDropItem("golden key", 100, 2);

		final Creature creature = new ItemGuardCreature(rat, "=");

		final CreatureRespawnPoint point = new CreatureRespawnPoint(zone, 10, 10, creature, 1);

		rat.setRespawnPoint(point);
		rat.setRespawned(true);
		point.setRespawnTime(5);

		zone.add(point);
		//point.spawnNow();

	}

}