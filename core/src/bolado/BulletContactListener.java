package bolado;

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
	}
}
