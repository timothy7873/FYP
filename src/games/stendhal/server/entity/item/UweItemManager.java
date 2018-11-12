package games.stendhal.server.entity.item;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import games.stendhal.common.Rand;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPRuleProcessor;
import games.stendhal.server.core.rule.EntityManager;
import marauroa.common.game.RPSlot;

public class UweItemManager {
	private static final double SERVER_DROP_GENEROSITY = 1;
	private static final EntityManager em = SingletonRepository.getEntityManager();
	private static final String codeItemNameHeader="You see a code item called ";
	private static final Logger logger = Logger.getLogger(StendhalRPRuleProcessor.class);
	
	public static Item createCodeItem(String codeName)
	{
		Item result;
		result=new Item("blank","blank","blank",null);
		//result=em.getItem("club");
		
		result.put("name", codeName);
		result.put("description", "You see a code item called "+codeName+". It is used to complete quests.");
		
		List<String> slots=new LinkedList<String>();
		slots.add("bag");
		slots.add("uwebag");
		slots.add("uwequest");
		slots.add("ground");
		//slots.add("content");
		slots.add("trade");
		
		result.setEquipableSlots(slots);
		
		return result;
	}
	public static Item createCodeItem(String codeName, double percentage)
	{
		Item result=null;
		double gen = Rand.rand(1000000) / 10000.0;

		if (gen <= (percentage / SERVER_DROP_GENEROSITY)) 
		{
			result=createCodeItem(codeName);
		}
		
		return result;
	}
	public static boolean isCodeItem(Item item)
	{
		String des=item.getDescription();
		if(des.length()<codeItemNameHeader.length())
			return false;
		return des.substring(0,codeItemNameHeader.length()).equals(codeItemNameHeader);
	}
}
