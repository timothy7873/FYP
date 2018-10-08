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
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.events.ExamineEvent;

public class CombinatorAction implements ChatAction{
	private static final Logger logger = Logger.getLogger(StendhalRPRuleProcessor.class);
	
	private final EntityManager em = SingletonRepository.getEntityManager();
	private SpeakerNPC npc;
	private CombinatorCorpse corpse;
	private static final String[][] COMBINATION_LIST= 
		{
			{"club","club","club of thorns"},
			{"club of thorns","club","golden hammer"}
		};
	
	public CombinatorAction(SpeakerNPC npc, CombinatorCorpse corpse) {
		this.npc=npc;
		this.corpse=corpse;
	}
	
	private PassiveEntity combine(PassiveEntity item1, PassiveEntity item2)
	{
		//if(true)return (PassiveEntity)new Item("a","b","c",null);
		for(int row=0;row<COMBINATION_LIST.length;row++)
		{
			if(COMBINATION_LIST[row][0].equals(item1.getName()) &&
					COMBINATION_LIST[row][1].equals(item2.getName()))
			{
				//return;
				return (PassiveEntity)em.getItem(COMBINATION_LIST[row][2]);
				//return (PassiveEntity)new Item("a","b","c",null);
			}
		}
		//return (PassiveEntity)new Item("a","b","c",null);
		return null;
	}

	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
		
		LinkedList<PassiveEntity> items=corpse.getItems();
		
		if(items.size()==0)
			return;
		PassiveEntity comItem=items.getFirst();
		items.removeFirst();
		logger.error("club name: "+comItem.getName());
		logger.error("items size: "+items.size());
		
		while(items.size()>0)
		{
			comItem=combine(comItem,items.getFirst());
			if(comItem==null)
				break;
			items.removeFirst();
		}
		logger.error("combine name: "+comItem.getName());
		
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