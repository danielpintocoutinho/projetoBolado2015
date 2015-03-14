package bolado;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Array;

public class BulletContactListener extends ContactListener
{
	public Array<btRigidBody> bodies;
	
	/*
	@Override
	public void onContactStarted(btCollisionObject colObj0, btCollisionObject colObj1)
	{
		if (colObj0.userData instanceof trigger )
		{
			Gdx.app.log("Trigger", "Touched!");
			
			((trigger)(colObj0.userData)).onCollision(AssetLoader.si.entities.get(colObj1.getUserValue()));
			
		}
		else if (colObj1.userData instanceof trigger )
		{
			Gdx.app.log("Trigger", "Touched!");
			((trigger)(colObj1.userData)).onCollision(AssetLoader.si.entities.get(colObj0.getUserValue()));
		}
	}
	*/
	
	//Using callback filtering with filters and flags. Callbacks only happen when match == true, being:
	// boolean match = (A.filter & B.flag == B.flag); 
	//Therefore, if B.flag == 0, callback is always called for that object. 
	@Override
	public void onContactStarted(btCollisionObject colObj0, boolean match0, btCollisionObject colObj1, boolean match1) 
	{
		if (colObj0.userData instanceof trigger )
		{
			Gdx.app.log("Trigger", "Touched!");
			
			((trigger)(colObj0.userData)).onCollision(AssetLoader.si.entities.get(colObj1.getUserValue()));
			
		}
		else if (colObj1.userData instanceof trigger )
		{
			Gdx.app.log("Trigger", "Touched!");
			((trigger)(colObj1.userData)).onCollision(AssetLoader.si.entities.get(colObj0.getUserValue()));
		}
	}
}
