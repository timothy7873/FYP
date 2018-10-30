package games.stendhal.server.entity.npc.action;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPRuleProcessor;
import games.stendhal.server.core.rule.EntityManager;
import games.stendhal.server.entity.PassiveEntity;
import games.stendhal.server.entity.creature.impl.DropItem;
import games.stendhal.server.entity.item.CombinatorCorpse;
import games.stendhal.server.entity.item.Corpse;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.UweItemManager;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.events.ExamineEvent;

public class CombinatorAction implements ChatAction{
	private static class CombinationList
	{
		private static final String[][] COMBINATION_LIST= 
		{
				//{"club","club","club of thorns"},
				//{"club of thorns","club","golden hammer"},
				{"void","main","void main"}
		};
		
		public String[] get(int index)
		{
			return COMBINATION_LIST[index];
		}
		public int size()
		{
			return COMBINATION_LIST.length;
		}
	};
	
	private static final Logger logger = Logger.getLogger(StendhalRPRuleProcessor.class);
	
	private final EntityManager em = SingletonRepository.getEntityManager();
	private SpeakerNPC npc;
	private CombinatorCorpse corpse;
	private static CombinationList combinationList= new CombinationList();
	
	public CombinatorAction(SpeakerNPC npc, CombinatorCorpse corpse) {
		this.npc=npc;
		this.corpse=corpse;
	}
	
	private PassiveEntity combine(PassiveEntity item1, PassiveEntity item2)
	{
		
		for(int i=0;i<combinationList.size();i++)
		{
			String[] row=combinationList.get(i);
			if(row[0].equals(item1.getName()) &&
					row[1].equals(item2.getName()))
			{
				if(UweItemManager.isCodeItem((Item)item1))
					return UweItemManager.createCodeItem(row[2]);
				else
					return em.getItem(row[2]);
			}
		}
		return null;
	}

	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
		
		LinkedList<PassiveEntity> items=corpse.getItems();
		
		if(items.size()==0)
			return;
		PassiveEntity comItem=items.getFirst();
		items.removeFirst();
		//logger.error("club name: "+comItem.getName());
		//logger.error("items size: "+items.size());
		
		while(items.size()>0)
		{
			comItem=combine(comItem,items.getFirst());
			if(comItem==null)
				break;
			items.removeFirst();
		}
		//logger.error("combine name: "+comItem.getName());
		
		corpse.getSlot().clear();
		corpse.getSlot().add(comItem);
		while(items.size()>0)
		{
			corpse.getSlot().add(items.getFirst());
			items.removeFirst();
		}
		corpse.notifyWorldAboutChanges();
	}

//	@Override
//	public String toString() {
//		return "CombinatorAction ";
//	}
//
//	@Override
//	public int hashCode() {
//		return 1;
//		//return 5189 * (image.hashCode() + 5197 * (title.hashCode() + 5209 * caption.hashCode()));
//	}
//
//	@Override
//	public boolean equals(final Object obj) {
//		if (!(obj instanceof ExamineChatAction)) {
//			return false;
//		}
//		ExamineChatAction other = (ExamineChatAction) obj;
//		return image.equals(other.image)
//			&& title.equals(other.title)
//			&& caption.equals(other.caption);
//	}
}