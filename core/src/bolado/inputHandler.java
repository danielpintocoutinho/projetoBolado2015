package bolado;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btStaticPlaneShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

public class inputHandler implements InputProcessor
{
	public Camera camera;

	static inputHandler si;

	private float camNear = 1f, camFar = 500f;
	
	public boolean isDraggingObject = false;

	ClosestRayResultCallback rayTest;
	Vector3 rayFrom = new Vector3();
	Vector3 rayTo = new Vector3(), tmpV2 = new Vector3();
	btRigidBody selectedBody = null;
	Vector3 dragTo = new Vector3();
	
	btStaticPlaneShape ss; btRigidBody dbg;

	public inputHandler(Camera cam)
	{
		camera = cam;

		camera.far = camFar;
		camera.near = camNear;
		camera.update();

		// Create the ray tester
		rayTest = new ClosestRayResultCallback(Vector3.Zero, Vector3.Z);
		
		ss = new btStaticPlaneShape(new Vector3(0,0,1), 0);
		dbg = new btRigidBody(0, null, ss);
		App.si.world.addRigidBody(dbg, (short)8, (short)8);
	}

	Vector3 dir = new Vector3();
	public void update()
	{		
		camera.position.set(AssetLoader.si.playerEntity.body.getCenterOfMassPosition().cpy().add(15, 15, 15));
		camera.update();
		
		dir = new Vector3();
		if(W)
		{
			dir.add(camera.direction.x,camera.direction.y,0);
		}
		if(S)
		{
			dir.add(-camera.direction.x,-camera.direction.y,0);
		}
		if(A)
		{
			dir.add(-camera.direction.y,camera.direction.x,0);
		}
		if(D)
		{
			dir.add(camera.direction.y,-camera.direction.x,0);
		}
		
		dir.nor();
		dir.scl(40);
		
		if(SPACE)
		{
			dir.add(0, 0, 70);
		}
		
		AssetLoader.si.playerEntity.body.activate();
		AssetLoader.si.playerEntity.body.applyCentralForce(dir);
	}

	boolean W,A,S,D,SPACE;
	@Override
	public boolean keyDown(int keycode)
	{
		if (keycode == Input.Keys.F12)
			App.si.debugModeOn = !App.si.debugModeOn;
		
		if (keycode == Input.Keys.F10)
			Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);
		
		if (keycode == Input.Keys.ESCAPE)
			Gdx.app.exit();

		if (keycode == Input.Keys.W)
			W=true;
		if (keycode == Input.Keys.A)
			A=true;
		if (keycode == Input.Keys.S)
			S=true;
		if (keycode == Input.Keys.D)
			D=true;	
		if (keycode == Input.Keys.SPACE)
			SPACE=true;
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		if (keycode == Input.Keys.W)
			W=false;
		if (keycode == Input.Keys.A)
			A=false;
		if (keycode == Input.Keys.S)
			S=false;
		if (keycode == Input.Keys.D)
			D=false;
		if (keycode == Input.Keys.SPACE)
			SPACE=false;

		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		Ray ray = camera.getPickRay(screenX, screenY);
		rayFrom.set(ray.origin);
		rayTo.set(ray.direction).scl(100f).add(rayFrom);

		// Because we reuse the ClosestRayResultCallback, we need reset it's values
		rayTest.setCollisionObject(null);
		rayTest.setClosestHitFraction(1f);
		rayTest.setRayFromWorld(rayFrom.cpy());
		rayTest.setRayToWorld(rayTo.cpy());
		rayTest.setCollisionFilterGroup((short)8);
		rayTest.setCollisionFilterMask ((short)8);
		
		Matrix4 mat = new Matrix4();
		mat.setTranslation(AssetLoader.si.playerEntity.body.getCenterOfMassPosition());
		dbg.setCenterOfMassTransform(mat);

		App.si.world.rayTest(rayFrom, rayTo, rayTest);

		if (rayTest.hasHit()) 
		{
			btCollisionObject obj = rayTest.getCollisionObject();
			if ( !obj.isKinematicObject() ) 
			{
				Vector3 hitPos = new Vector3();
				rayTest.getHitPointWorld(hitPos);
				
				Vector3 t = AssetLoader.si.playerEntity.body.getCenterOfMassPosition();
				Vector3 v = hitPos.sub(t).nor();
				float a = (new Vector2(v.x, v.y)).angle();

				AssetLoader.si.playerEntity.body.setWorldTransform(
						new Matrix4(t, new Quaternion(Vector3.Z, a-90f), new Vector3(1,1,1)));
			}
		}
		
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
}