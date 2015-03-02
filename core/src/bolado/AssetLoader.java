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
	
	public Array<trigger> triggers = new Array<trigger>();
	public Array<BulletEntity> triggerEntities = new Array<BulletEntity>();
	public int triggerCounter = 0;
	public deathTrigger dt = new deathTrigger();
	public logTrigger lt = new logTrigger();

	public Array<btCollisionShape> shapes = new Array<btCollisionShape>();
	public Array<BulletEntity> entities = new Array<BulletEntity>();
	
	//This will allow entity hiding (has to be subtracted by 1 om reading)
	public Array<BulletEntity> otherEntities = new Array<BulletEntity>();
	public int bodyIndexOnOtherEntitiesPlus = 0;

	//public Array<btRigidBody> movableBodies = new Array<btRigidBody>();

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
			//Main player userValue = 1
			body.setUserValue(0);

			return body;
		}

		if (bodyName.contains("trigger"))
		{
			Gdx.app.log("Assets", "Found a trigger!");
			
			Vector3 localInertia = new Vector3();
			if (mass > 0f) shape.calculateLocalInertia(mass, localInertia);
			btRigidBody body = new btRigidBody(mass, null, shape, localInertia);
			
			//All triggers have user value = 100 + trigger id (temporary)
			body.setUserValue(100 + triggerCounter);
			Gdx.app.log("Assets", "Trigger userValue = " + (100 + triggerCounter));
			triggerCounter++;
			
			shapes.add(shape);

			ModelInstance instance = new ModelInstance(model, bodyName, true, true);
			instance.transform.set(startTransform);

			BulletEntity entity = new BulletEntity(instance, body);
			entities.add(entity);
			App.si.world.addRigidBody(body);
			
			//Add this entity to the trigger objects list
			triggerEntities.add(entity);
			
			
			//Add the trigger to the trigger list. 
			//The trigger in question shall be defined in some kind of level file:
			
			//Find out wich trigger to add...
			if(bodyName.endsWith("2"))
				triggers.add(dt);
			else
				triggers.add(lt);
			

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
		
		//all other objects have user value > 0
		otherEntities.add(entity);
		body.setUserValue(bodyIndexOnOtherEntitiesPlus+1);
		bodyIndexOnOtherEntitiesPlus++;

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
