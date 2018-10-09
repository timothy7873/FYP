package games.stendhal.server.entity.npc.condition;

import java.util.Iterator;

import org.apache.log4j.Logger;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.engine.StendhalRPRuleProcessor;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPObject;

public class HasItemCondition implements ChatCondition{
	private static final Logger logger = Logger.getLogger(StendhalRPRuleProcessor.class);
	
	private String requiredItemName;
	private boolean reverse;
	
	public HasItemCondition(String requiredItemName)
	{
		this.requiredItemName=requiredItemName;
		this.reverse=false;
	}
	public HasItemCondition(String requiredItemName, boolean reverse)
	{
		this.requiredItemName=requiredItemName;
		this.reverse=reverse;
	}
	
	@Override
	public boolean fire(final Player player, final Sentence sentence, final Entity entity)
	{
		Iterator<RPObject> items=player.getSlot("bag").iterator();
		while(items.hasNext())
		{
			Item item=(Item)items.next();
			//logger.error(item.getName());
			
			if(item.getName().equals(requiredItemName))
				return !reverse;
		}
		
		return reverse;
	}
}
