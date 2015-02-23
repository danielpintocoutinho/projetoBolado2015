package bolado;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import bolado.App;

// Adapted from XOPPA's original code in LibGDX tests
public class BulletEntity implements Disposable
{
	public Matrix4 transform;
	public ModelInstance modelInstance;
	public BulletEntity.MotionState motionState;
	public btRigidBody body;
	
	public boolean isHidden = false;
	
	public static Array<ModelInstance> instances = new Array<ModelInstance>();

	public BulletEntity (final ModelInstance modelInstance, final btRigidBody body) {
		this.modelInstance = modelInstance;
		instances.add(modelInstance);
		this.transform = this.modelInstance.transform;
		this.body = body;

		if (body != null) {
			body.userData = this;
			if (body instanceof btRigidBody) {
				this.motionState = new MotionState(this.modelInstance.transform);
				((btRigidBody)this.body).setMotionState(motionState);
			} else
				body.setWorldTransform(transform);
		}
	}
	
	public void hide()
	{
		if (isHidden) return;
		
		App.si.world.removeRigidBody(body);
		instances.removeValue(modelInstance, true);
		
		isHidden = true;
	}
	
	public void unhide()
	{
		if (!isHidden) return;
		
		App.si.world.addRigidBody(body);
		instances.add(modelInstance);
		
		isHidden = false;
	}
	
	public BulletEntity copy()
	{
		return null;
//		return new BulletEntity(modelInstance.copy(), btRigidBody.)
	}

	@Override
	public void dispose () {
		// Don't rely on the GC
		if (motionState != null) motionState.dispose();
		if (body != null) body.dispose();
		// And remove the reference
		motionState = null;
		body = null;
	}

	static class MotionState extends btMotionState {
		private final Matrix4 transform;

		public MotionState (final Matrix4 transform) {
			this.transform = transform;
		}

		/** For dynamic and static bodies this method is called by bullet once to get the initial state of the body. For kinematic
		 * bodies this method is called on every update, unless the body is deactivated. */
		@Override
		public void getWorldTransform (final Matrix4 worldTrans) {
			worldTrans.set(transform);
		}

		/** For dynamic bodies this method is called by bullet every update to inform about the new position and rotation. */
		@Override
		public void setWorldTransform (final Matrix4 worldTrans) {
			transform.set(worldTrans);
		}
	}
}
