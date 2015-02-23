package bolado;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Array;

public class BulletContactListener extends ContactListener
{
	public Array<btRigidBody> bodies;

	@Override
	public void onContactStarted(btCollisionObject colObj0, btCollisionObject colObj1)
	{

		if ((colObj0.getUserValue() == 9 && colObj1.getUserValue() == 10) || (colObj0.getUserValue() == 10 && colObj1.getUserValue() == 9))
		{
			Gdx.app.log("", "boulder destroyed ");
			
			AssetLoader.si.boulder.hide();

			for (BulletEntity ent : AssetLoader.si.boulderFragments) ent.unhide();
		}
		
		if ((colObj0.getUserValue() == 9 && colObj1.getUserValue() == 11) || (colObj0.getUserValue() == 11 && colObj1.getUserValue() == 9))
		{
			Gdx.app.log("", "house destroyed ");
			
			AssetLoader.si.house.hide();

			for (BulletEntity ent : AssetLoader.si.houseFragments) ent.unhide();
		}
	}
}
