package games.stendhal.server.entity.creature;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import games.stendhal.common.Rand;
import games.stendhal.server.core.rule.EntityManager;
import games.stendhal.server.entity.creature.impl.DropInstanceItem;
import games.stendhal.server.entity.creature.impl.DropItem;
import games.stendhal.server.entity.item.Corpse;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.StackableItem;

public class UweQuestCreature extends Creature{
	private static final double SERVER_DROP_GENEROSITY = 1;
	protected List<DropInstanceItem> dropsRealItems;
	
	public UweQuestCreature(Creature copy)
	{
		super(copy);
		if (copy.dropItemInstances != null) {
			this.dropItemInstances = copy.dropItemInstances;
		}
		if(copy instanceof UweQuestCreature)
		{
			UweQuestCreature realCopy=(UweQuestCreature)copy;
			this.dropsRealItems = realCopy.dropsRealItems;
		}
		if(dropsRealItems==null)
			dropsRealItems=new ArrayList<DropInstanceItem>();
	}
	
	public void addDropItem(final Item item, double percentage) {
		DropInstanceItem tItem=new DropInstanceItem(item, percentage);
		dropsRealItems.add(tItem);
	}
	
	@Override
	protected void dropItemsOn(final Corpse corpse)
	{
		super.dropItemsOn(corpse);
		
		for (final Item item : getDroppedItems()) {
			corpse.add(item);
			item.setFromCorpse(true);
			if (corpse.isFull()) {
				break;
			}
		}
	}
	private List<Item> getDroppedItems() {
		final List<Item> list = new LinkedList<Item>();

		for (final DropInstanceItem dropped : dropsRealItems) {
			final double probability = Rand.rand(1000000) / 10000.0;

			if (probability <= (dropped.percentage / SERVER_DROP_GENEROSITY)) {
				list.add(dropped.item);
			}
		}
		return list;
	}
	
	public Creature getNewInstance() {
		return new UweQuestCreature(this);
	}
}
