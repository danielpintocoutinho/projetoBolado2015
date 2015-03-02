package bolado;

import com.badlogic.gdx.Gdx;


//This interface will be used to represent triggers on game. 
//They will, ideally, be represented by invisible collision volumes.
public interface trigger {
	
	//What happens when one of the players collide with this trigger
	void onCharCollision(BulletEntity ent);
	
	//What happens when a Mob collides with the trigger
	void onMobCollision(BulletEntity ent);

}
