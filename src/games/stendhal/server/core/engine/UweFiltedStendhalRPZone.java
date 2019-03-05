package games.stendhal.server.core.engine;

public class UweFiltedStendhalRPZone extends StendhalRPZone{
	private static String[] blocklist= {"-1_semos_dungeon","-2_semos_dungeon"};
	
	private boolean block;

	public UweFiltedStendhalRPZone(String name) {
		super(name);
		
		block=false;
		for(int i=0;i<blocklist.length;i++)
			block=block || name.equals(blocklist[i]);
		
		//block all
		block=true;
	}
	
	protected void createEntityAt(final String clazz, final int type, final int x, final int y)
	{
		if(block && SingletonRepository.getEntityManager().isCreature(clazz, type))
			return;
		super.createEntityAt(clazz, type, x, y);
	}

}
