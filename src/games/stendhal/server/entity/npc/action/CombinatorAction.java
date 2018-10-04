package games.stendhal.server.entity.npc.action;

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.log4j.Logger;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.core.engine.StendhalRPRuleProcessor;
import games.stendhal.server.entity.PassiveEntity;
import games.stendhal.server.entity.item.CombinatorCorpse;
import games.stendhal.server.entity.item.Corpse;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.events.ExamineEvent;

public class CombinatorAction implements ChatAction{
	private static final Logger logger = Logger.getLogger(StendhalRPRuleProcessor.class);
	
	private SpeakerNPC npc;
	private CombinatorCorpse corpse;
	private String[][] combinationList;
	
	public CombinatorAction(SpeakerNPC npc, CombinatorCorpse corpse) {
		this.npc=npc;
		this.corpse=corpse;
		
		combinationList=new String[1][2];
		
	}
	
	private PassiveEntity combine(PassiveEntity item1, PassiveEntity item2)
	{
		if(true)return (PassiveEntity)new Item("a","b","c",null);
		for(int row=0;row<combinationList.length;row++)
		{
			if(combinationList[row][0].equals(item1.getName()) &&
					combinationList[row][1].equals(item2.getName()))
			{
				//return;
				return (PassiveEntity)new Item("a","b","c",null);
			}
		}
		return (PassiveEntity)new Item("a","b","c",null);
		//return null;
	}

	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
		if(false)return;
		//logger.warn("corpse: "+corpse.toString());
		PassiveEntity[] items=corpse.getItems();
		corpse.getSlot().clear();
		//corpse.getSlot().add((PassiveEntity)new Item("a","b","c",null));
		//corpse.update();
		//corpse.notifyWorldAboutChanges();
		if(false)return;
		
		if(items.length==0)
			return;
		PassiveEntity comItem=items[0];
		logger.error("club name: "+comItem.getName());
		logger.error("items size: "+items.length);
		for(int i=1;i<items.length;i++)
		{
			comItem=combine(comItem,items[i]);
			if(comItem==null)
				break;
			//corpse.getSlot().add(comItem);
			logger.error("loop index: "+i);
		}
		logger.error("combine name: "+comItem.getName());
		corpse.getSlot().add(comItem);
		//corpse.update();
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
