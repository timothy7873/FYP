package games.stendhal.server.actions;

import static games.stendhal.common.constants.Actions.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import Util.Management.ManagementAPI;
import Util.Management.Reward;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.rule.EntityManager;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPSlot;

public class UweQuestSubmitAction implements ActionListener{

	@Override
	public void onAction(Player player, RPAction action) {
		// TODO Auto-generated method stub
		EntityManager em = SingletonRepository.getEntityManager();
		RPSlot slot=player.getSlot("bag");
		String npcId=action.get("npcId");
		
		//get reward
		List rewards=new LinkedList();
		String questType=action.get("questType");
		if(questType.equals("logical"))
		{
			rewards.addAll(Arrays.asList(ManagementAPI.api.getLogicalQuestion(npcId, player.getName()).reward));
		}
		
		//add item
		for(int i=0;i<rewards.size();i++)
		{
			Reward reward=(Reward)rewards.get(i);
			if(reward.itemName==null)
				continue;

			Item item=em.getItem(reward.itemName);
			for(int q=0;q<reward.count;q++)
				slot.add(item);
		}

		//add exp & karma
		int exp=0;
		for(int i=0;i<rewards.size();i++)
			exp+=((Reward)rewards.get(i)).exp;
		player.addXP(exp);
		
		double karma=0;
		for(int i=0;i<rewards.size();i++)
			karma+=((Reward)rewards.get(i)).karma;
		player.addKarma(karma);
		
		//add money
		int money=0;
		for(int i=0;i<rewards.size();i++)
			money+=((Reward)rewards.get(i)).money;
		StackableItem moneyItem = (StackableItem)SingletonRepository.getEntityManager().getItem("money");
		moneyItem.setQuantity(money);
		player.equipOrPutOnGround(moneyItem);

		//clear submitted item
		for(int i=0;;i++)
		{
			RPSlot submition=player.getSlot("uwepopup"+i);
			if(submition==null)
				break;
			submition.clear();
		}
		player.setQuest(npcId, "done");
		
		player.updateItemAtkDef();
		player.update();
		player.notifyWorldAboutChanges();
		
	}

	public static void register() {
		CommandCenter.register(UWEQUESTSUBMIT, new UweQuestSubmitAction());
	}
}
