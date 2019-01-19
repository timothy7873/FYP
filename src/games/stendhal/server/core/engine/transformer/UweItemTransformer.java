package games.stendhal.server.core.engine.transformer;

import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.UweItemManager;
import games.stendhal.server.entity.player.UpdateConverter;
import marauroa.common.game.RPObject;

public class UweItemTransformer extends ItemTransformer{
	public Item transform(RPObject rpobject)
	{
		Item result=null;
		
		if (rpobject.get("type")!=null && rpobject.get("type").equals("item") &&
				UpdateConverter.updateItem(UpdateConverter.updateItemName(rpobject.get("name")))==null)
			result=UweItemManager.createCodeItem(rpobject.get("name"));
		else
			result=super.transform(rpobject);
		
		return result;
	}
}
