package games.stendhal.server.maps.quests.uwe;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPRuleProcessor;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.UseListener;
import games.stendhal.server.core.rule.EntityManager;
import games.stendhal.server.core.rule.defaultruleset.DefaultItem;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.creature.AttackableCreature;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.creature.ItemGuardCreature;
import games.stendhal.server.entity.creature.UweQuestCreature;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.UweItemManager;
import games.stendhal.server.entity.mapstuff.spawner.CreatureRespawnPoint;
import games.stendhal.server.entity.npc.PassiveNPC;

public class UweCreature implements LoadableContent {
	private final EntityManager em = SingletonRepository.getEntityManager();
	private static final Logger logger = Logger.getLogger(StendhalRPRuleProcessor.class);

	@Override
	public void addToWorld() {
		// TODO Auto-generated method stub
		createCreature();
		//createCreature();
		//createCreature();
		//createCreature();
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
		 //Item item = em.getItem( "golden key");
	//	Item item = em.getItem("=");
		//item.put("menu", "Drink|Use");

		//Creature rat = em.getCreature("rat");
//
		//rat.addDropItem("golden key", 100, 2);
//
//		final Creature creature = new ItemGuardCreature(rat, "=");
//
//		final CreatureRespawnPoint point = new CreatureRespawnPoint(zone, 13, 12, creature, 1);
//
//		rat.setRespawnPoint(point);
//		rat.setRespawned(true);
//		point.setRespawnTime(900);
//
//		zone.add(point);
		
		
		//timothy
		
		StendhalRPZone tZone = SingletonRepository.getRPWorld().getZone("-1_semos_dungeon");
		Creature tRat = em.getCreature("rat");
		UweQuestCreature tCreature=new UweQuestCreature(tRat);

		tCreature.addDropItem(UweItemManager.createCodeItem("System.out.println(x)"),100);
		tCreature.addDropItem(UweItemManager.createCodeItem(";"),100);
		tCreature.addDropItem(UweItemManager.createCodeItem("int x=11;"),100);
		tCreature.addDropItem(UweItemManager.createCodeItem("x"),100);

		CreatureRespawnPoint tPoint=new CreatureRespawnPoint(tZone, 32, 34, tCreature, 1);
		tPoint.setRespawnTime(1);
		tZone.add(tPoint);
		
		tPoint.spawnNow();
		
		
		
		//tZone.collisionMap.setCollide(11, 6);
	}

}