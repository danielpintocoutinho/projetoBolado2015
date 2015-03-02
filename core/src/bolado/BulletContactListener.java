package bolado;

import com.badlogic.gdx.Gdx;
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
		int value0 = colObj0.getUserValue();
		int value1 = colObj1.getUserValue();
		
		if (value0 > 99)
		{
			Gdx.app.log("Trigger", "Touched!");
			if(value1 == 0)
			{
				AssetLoader.si.triggers.get(value0 - 100).onCharCollision(AssetLoader.si.playerEntity);
			}
			
			else
				AssetLoader.si.triggers.get(value0 - 100).onMobCollision(AssetLoader.si.otherEntities.get(value1-1));
		}
		else if (value1 > 99)
		{
			Gdx.app.log("Trigger", "Touched!");
			if(value0 == 0)
				AssetLoader.si.triggers.get(value0 - 100).onCharCollision(AssetLoader.si.playerEntity);
			else
				AssetLoader.si.triggers.get(value0 - 100).onMobCollision(AssetLoader.si.otherEntities.get(value0-1));
		}
	}
}
