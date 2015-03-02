package bolado;

public class deathTrigger implements trigger
{

	public deathTrigger()
	{
		
	}
	
	@Override
	public void onCharCollision(BulletEntity ent) {
		
		ent.hide();	
	}

	@Override
	public void onMobCollision(BulletEntity ent) {
		
		ent.hide();	
	}
}

