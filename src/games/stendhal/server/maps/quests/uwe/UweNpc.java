package games.stendhal.server.maps.quests.uwe;

import java.awt.Point;

import games.stendhal.server.core.engine.StendhalRPZone;

public class UweNpc implements LoadableContent{
	public String npcName = "UweNpc";
	protected StendhalRPZone zone = null;//SingletonRepository.getRPWorld().getZone("-1_semos_dungeon");
	protected Point spawnPoint = new Point(0,0);

	@Override
	public void addToWorld() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeFromWorld() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
