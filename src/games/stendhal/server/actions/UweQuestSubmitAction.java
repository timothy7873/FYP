package games.stendhal.server.actions;

import static games.stendhal.common.constants.Actions.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import Util.Management.JourneyRow;
import Util.Management.ManagementAPI;
import Util.Management.Reward;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.rule.EntityManager;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;

public class UweQuestSubmitAction implements ActionListener{

	@Override
	public void onAction(Player player, RPAction action) {
		// TODO Auto-generated method stub
		EntityManager em = SingletonRepository.getEntityManager();
		RPSlot slot=player.getSlot("bag");
		String journeyRowId=action.get("journeyRowId");
		JourneyRow jr=ManagementAPI.api.getJourneyRow(journeyRowId);
		
		//get reward
		int money=0;
		int exp=0;
		double karma=0;
		LinkedList<Reward> items=new LinkedList<Reward>();
		try
		{
			money=Integer.parseInt(action.get("money"));
			exp=Integer.parseInt(action.get("exp"));
			karma=Double.parseDouble(action.get("karma"));
			
			String itemStr=action.get("items");
			String[] rows=itemStr.split("\n");
			for(int i=0;i<rows.length;i++)
			{
				String[] cols=rows[i].split(",");
				if(cols.length!=2)
					continue;
				int itemCount=Integer.parseInt(cols[1]);
				items.add(new Reward(cols[0],itemCount,0,0,0));
				
			}
		}
		catch(Exception e) 
		{}
		
		//add item
		for(int i=0;i<items.size();i++)
		{
			Reward reward=(Reward)items.get(i);
			if(reward.itemName==null)
				continue;

			Item item=em.getItem(reward.itemName);
			if(item==null)
				continue;
			for(int q=0;q<reward.count;q++)
				slot.add(item);
		}

		//add money, exp, karma
		StackableItem moneyItem = (StackableItem)SingletonRepository.getEntityManager().getItem("money");
		moneyItem.setQuantity(money);
		player.equipOrPutOnGround(moneyItem);
		player.addXP(exp);
		player.addKarma(karma);

		//clear submitted item
		for(int i=0;;i++)
		{
			RPSlot submition=player.getSlot("uwepopup"+i);
			if(submition==null)
				break;

			RPObject item=submition.getFirst();
			if(item!=null)
				submition.remove(item.getID());
		}
		player.setQuest(jr.npcId, "done");
		
		player.updateItemAtkDef();
		player.update();
		player.notifyWorldAboutChanges();
		
		//cal api to set quest not doing
		ManagementAPI.api.stopTimeCount(journeyRowId, player.getName());
		//cal api to set quest done time
		ManagementAPI.api.setLastStartTime(journeyRowId, player.getName());
		//call api to set quest done
		ManagementAPI.api.setQuestStatus(journeyRowId, player.getName(), "done");
	}

	public static void register() {
		CommandCenter.register(UWEQUESTSUBMIT, new UweQuestSubmitAction());
	}
}
