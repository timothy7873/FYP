package games.stendhal.server.entity.creature.impl;

import games.stendhal.server.entity.item.Item;

public class DropInstanceItem {
	public double percentage;
	public Item item;
	
	public DropInstanceItem(Item item, double percentage)
	{
		this.item=item;
		this.percentage=percentage;
	}
}
