package bolado;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.extras.btBulletWorldImporter;
import com.badlogic.gdx.utils.Array;

public class AssetLoader extends btBulletWorldImporter
{
	public static AssetLoader si;

	public Model model;
	public BulletEntity playerEntity;
	public ModelInstance skyBox;
	public AssetManager assets;

	public Array<btCollisionShape> shapes = new Array<btCollisionShape>();
	public Array<BulletEntity> entities = new Array<BulletEntity>();

	public Array<btRigidBody> movableBodies = new Array<btRigidBody>();
	public Array<BulletEntity> boulderFragments = new Array<BulletEntity>();
	public Array<BulletEntity> houseFragments = new Array<BulletEntity>();

	public AssetLoader(btDynamicsWorld world)
	{
		super(world);

		si = this;

		// Load assets
		String modelName = "scene.g3db";
		assets = new AssetManager();
		assets.load(modelName, Model.class);
		assets.finishLoading();
		model = assets.get(modelName, Model.class);

		loadFile(Gdx.files.internal("scene.bullet"));

		for (Node node : model.nodes)
		{
			Gdx.app.log("MODELS", node.id);
		}

		world.setGravity(new Vector3(0,0,-30));

	}

	@Override
	public btRigidBody createRigidBody(boolean isDynamic, float mass, Matrix4 startTransform, btCollisionShape shape, String bodyName)
	{
		bodyName.replace(".", "_");
		Gdx.app.log("Assets", "Loading -> " + bodyName);

		if (bodyName.contains("char"))
		{
			Gdx.app.log("Assets", "TEST2 " + mass);
			Vector3 localInertia = new Vector3();
			btRigidBody body = new btRigidBody(mass, null, shape, localInertia);
			shapes.add(shape);

			ModelInstance instance = new ModelInstance(model, bodyName, true, true);
			instance.transform.set(startTransform);

			BulletEntity entity = new BulletEntity(instance, body);
			entities.add(entity);
			body.setFriction(.9f);
			body.setRestitution(.2f);
			App.si.world.addRigidBody(body);

			body.setLinearVelocity(new Vector3(0,0,20));

			playerEntity = entity;

			body.setLinearFactor(new Vector3(.9f, .9f, .9f));

			return body;
		}


		Vector3 localInertia = new Vector3();
		if (mass > 0f) shape.calculateLocalInertia(mass, localInertia);
		btRigidBody body = new btRigidBody(mass, null, shape, localInertia);
		shapes.add(shape);

		ModelInstance instance = new ModelInstance(model, bodyName, true, true);
		instance.transform.set(startTransform);

		BulletEntity entity = new BulletEntity(instance, body);
		entities.add(entity);
		App.si.world.addRigidBody(body);

		return body;
	}


	@Override
	public void dispose()
	{
		super.dispose();

		for (btCollisionShape shape : shapes) shape.dispose();
		shapes.clear();

		model.dispose();
	}
}
