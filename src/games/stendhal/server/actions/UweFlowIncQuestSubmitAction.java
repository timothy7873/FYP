package games.stendhal.server.actions;

import static games.stendhal.common.constants.Actions.UWEFLOWINCQUESTSUBMIT;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.rule.EntityManager;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPSlot;

public class UweFlowIncQuestSubmitAction implements ActionListener{

	@Override
	public void onAction(Player player, RPAction action) {
		// TODO Auto-generated method stub
		EntityManager em = SingletonRepository.getEntityManager();
		RPSlot slot=player.getSlot("bag");
		
		//add item
		String itemsStr=action.get("items");
		if(itemsStr.length()>0)
		{
			if(itemsStr.substring(itemsStr.length()-1,itemsStr.length()).equals(";"))
				itemsStr=itemsStr.substring(0,itemsStr.length()-1);
			
			String[] items=itemsStr.split(";");
			for(int i=0;i<items.length;i++)
			{
				String[] row=items[i].split(",");
				String itemName=row[0];
				int count=Integer.parseInt(row[1]);
				
				Item item=em.getItem(itemName);
				for(int q=0;q<count;q++)
					slot.add(item);
			}
		}

		//add exp & karma
		int exp=Integer.parseInt(action.get("exp"));
		player.addXP(exp);
		
		double karma=Double.parseDouble(action.get("karma"));
		player.addKarma(karma);
		
		player.updateItemAtkDef();
		player.update();
		player.notifyWorldAboutChanges();

		for(int i=0;;i++)
		{
			RPSlot submition=player.getSlot("uwepopup"+i);
			if(submition==null)
				break;
			submition.clear();
		}
		
	}

	public static void register() {
		CommandCenter.register(UWEFLOWINCQUESTSUBMIT, new UweFlowIncQuestSubmitAction());
	}
}
