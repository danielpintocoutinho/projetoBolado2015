package bolado;

import com.badlogic.gdx.Gdx;

public class logTrigger implements trigger 
{
	public logTrigger ()
	{
		
	}
	/*
	@Override
	public void onCharCollision(BulletEntity ent)
	{
		Gdx.app.log("Triggered","Log trigger activated");
	}

	@Override
	public void onMobCollision(BulletEntity ent) 
	{
		Gdx.app.log("Triggered","Log trigger activated");
		
	}
	*/

	@Override
	public void onCollision(BulletEntity ent) {
		Gdx.app.log("Triggered","Log trigger activated");
		
	}	
	
}
