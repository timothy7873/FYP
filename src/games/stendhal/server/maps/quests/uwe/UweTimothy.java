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

public class UweTimothy implements LoadableContent {

	@Override
	public void addToWorld() {
		// TODO Auto-generated method stub
		createCreature();
	}

	@Override
	public boolean removeFromWorld() {
		// TODO Auto-generated method stub
		return false;
	}

	public void createCreature() {
		
		
		//timothy
//		StendhalRPZone tZone = SingletonRepository.getRPWorld().getZone("0_semos_village_w");
//		EntityManager em = SingletonRepository.getEntityManager();
//		Creature tRat = em.getCreature("rat");
//		//rat.addDropItem("golden key", 100, 2);
//		Creature tCreature = new ItemGuardCreature(tRat, "=");
//		CreatureRespawnPoint tPoint=new CreatureRespawnPoint(tZone, 18, 37, tCreature, 1);
//		tZone.add(tPoint);
		
//		StendhalRPZone tZone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
//		EntityManager em = SingletonRepository.getEntityManager();
//		Creature tRat = em.getCreature("rat");
//		//rat.addDropItem("golden key", 100, 2);
//		Creature tCreature = new ItemGuardCreature(tRat, "=");
//		CreatureRespawnPoint tPoint=new CreatureRespawnPoint(tZone, 10, 10, tCreature, 1);
//		tZone.add(tPoint);
		
//		tZone = SingletonRepository.getRPWorld().getZone("int_semos_guard_house");
//		tRat = em.getCreature("rat");
//		//rat.addDropItem("golden key", 100, 2);
//		tCreature = new ItemGuardCreature(tRat, "=");
//		tPoint=new CreatureRespawnPoint(tZone, 1, 1, tCreature, 1);
//		tZone.add(tPoint);
	}

}