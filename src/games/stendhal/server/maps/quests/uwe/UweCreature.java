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

		Creature rat = em.getCreature("deer");

		// rat.addDropItem("golden key", 100, 2);

		final Creature creature = new ItemGuardCreature(rat, "=");

		final CreatureRespawnPoint point = new CreatureRespawnPoint(zone, 13, 12, rat, 1);

		rat.setRespawnPoint(point);
		rat.setRespawned(true);
		point.setRespawnTime(0);//rats reborn

		zone.add(point);
/*
 * 
		Creature[] animal = new Creature[20];
		animal[0]=em.getCreature("crab");
		animal[1]=em.getCreature("deer");
		animal[2]=em.getCreature("mouse");
		animal[3]=em.getCreature("chicken");
		animal[4]=em.getCreature("bat");
		animal[5]=em.getCreature("dodo");
		animal[6]=em.getCreature("penguin");
		animal[7]=em.getCreature("pigeon");
		animal[8]=em.getCreature("bat");
		animal[9]=em.getCreature("caverat");
		animal[10]=em.getCreature("piglet");
		animal[11]=em.getCreature("babybear");
		animal[12]=em.getCreature("beaver");
		animal[13]=em.getCreature("boar");
		animal[14]=em.getCreature("bull");
		animal[15]=em.getCreature("cow");
		animal[16]=em.getCreature("fox");
		animal[17]=em.getCreature("goat");
		animal[18]=em.getCreature("horse");
		animal[19]=em.getCreature("monkey");
		
		CreatureRespawnPoint[] apoint=new CreatureRespawnPoint[20];
		for(int i=0;i<20;i++) {
			apoint[i] = new CreatureRespawnPoint(zone, 13, 12, animal[i], 1);

			animal[i].setRespawnPoint(apoint[i] );
			animal[i].setRespawned(true);
			apoint[i].setRespawnTime(1);//rats reborn

			zone.add(apoint[i]);
		}
	*/	
	}

}