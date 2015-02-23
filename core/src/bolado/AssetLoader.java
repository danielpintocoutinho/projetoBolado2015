package bolado;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.extras.btBulletWorldImporter;
import com.badlogic.gdx.utils.Array;
import com.sun.xml.internal.stream.Entity;

public class AssetLoader extends btBulletWorldImporter
{
	public static AssetLoader si;
	
	public Model model;
	public BulletEntity playerEntity, woodenBlock, coconutPalm, house, boulder;
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
//			if (node.id.contains("grass") || node.id.contains("flower") || node.id.contains("mushroom"))
//			BulletEntity.instances.add(new ModelInstance(model, node.id, true, true));
		}
//		
		world.setGravity(new Vector3(0,0,-30));
//		
//		for (btRigidBody e : movableBodies) 
//		{
//			e.setFriction(1.f);
//			e.setRestitution(.5f);
//			
//			e.setContactCallbackFlag(4);
//			e.setContactCallbackFilter(4);
//			
//			if (e.getUserValue()!=10 && e.getUserValue()!=11) e.setUserValue(9);
//		}
	}

	@Override
	public btRigidBody createRigidBody(boolean isDynamic, float mass, Matrix4 startTransform, btCollisionShape shape, String bodyName)
	{
		bodyName.replace(".", "_");
		Gdx.app.log("Assets", "Loading -> " + bodyName);
		
//		if (bodyName.contains("ground"))
//		{
//			Gdx.app.log("Assets", "TEST1 "+mass); 
//			Vector3 localInertia = new Vector3();
//			if (mass > 0f) shape.calculateLocalInertia(mass, localInertia);
//			btRigidBody body = new btRigidBody(mass, null, shape, localInertia);
//			shapes.add(shape);
//
//			ModelInstance instance = new ModelInstance(model, bodyName, true, true);
//			instance.transform.set(startTransform);
//
//			BulletEntity entity = new BulletEntity(instance, body);
//			entities.add(entity);
//			App.si.world.addRigidBody(body);
//			
//
//			movableBodies.add(body); 
//			
//			
//			return body;
//		}
//		
//		else if (bodyName.contains("house"))
//		{
//			Vector3 localInertia = new Vector3();
//			if (mass > 0f) shape.calculateLocalInertia(mass, localInertia);
//			btRigidBody body = new btRigidBody(mass, null, shape, localInertia);
//			shapes.add(shape);
//
//			ModelInstance instance = new ModelInstance(model, bodyName, true, true);
//			instance.transform.set(startTransform);
//
//			BulletEntity entity = new BulletEntity(instance, body);
//			entities.add(entity);
//			App.si.world.addRigidBody(body);
//			
//			if (!bodyName.equals("house"))
//			{
//				entity.hide();
//				houseFragments.add(entity);
//				instance.materials.get(0).set(new ColorAttribute(ColorAttribute.Specular));
//			}
//			else
//			{
//				movableBodies.add(body); 
//				
//				house = entity;
//				body.setUserValue(11);
//			}
//			
//			
//			return body;
//		}
//		
//		else if (bodyName.contains("boulder"))
//		{
//			Vector3 localInertia = new Vector3();
//			if (mass > 0f) shape.calculateLocalInertia(mass, localInertia);
//			btRigidBody body = new btRigidBody(mass, null, shape, localInertia);
//			shapes.add(shape);
//
//			ModelInstance instance = new ModelInstance(model, bodyName, true, true);
//			instance.transform.set(startTransform);
//
//			BulletEntity entity = new BulletEntity(instance, body);
//			entities.add(entity);
//			App.si.world.addRigidBody(body);
//			
//			if (!bodyName.equals("boulder"))
//			{
//				entity.hide();
//				
//				boulderFragments.add(entity);
//			}
//			else
//			{
//				boulder = entity;
//				movableBodies.add(body); 
//				body.setUserValue(10);
//			}
//			
//			
//			return body;
//		}
//		
//		else if (bodyName.contains("flower"))
//		{
//		}
//		
//		else if (bodyName.contains("artillery"))
//		{
//			Vector3 localInertia = new Vector3();
//			if (mass > 0f) shape.calculateLocalInertia(mass, localInertia);
//			btRigidBody body = new btRigidBody(mass, null, shape, localInertia);
//			shapes.add(shape);
//
//			ModelInstance instance = new ModelInstance(model, bodyName, true, true);
//			instance.transform.set(startTransform);
//
//			BulletEntity entity = new BulletEntity(instance, body);
//			entities.add(entity);
//			App.si.world.addRigidBody(body);
//			
//			return body;
//		}
//		
//		else if (bodyName.contains("woodenBlock"))
//		{
//			Vector3 localInertia = new Vector3();
//			if (mass > 0f) shape.calculateLocalInertia(mass, localInertia);
//			btRigidBody body = new btRigidBody(mass, null, shape, localInertia);
//			shapes.add(shape);
//
//			ModelInstance instance = new ModelInstance(model, bodyName, true, true);
//			instance.transform.set(startTransform);
//
//			BulletEntity entity = new BulletEntity(instance, body);
//			entities.add(entity);
//			App.si.world.addRigidBody(body);
//			
//			if (!bodyName.equals("woodenBlock"))
//			{
//				entity.hide();
//			}
//			else
//			{
//				movableBodies.add(body); 
//			}
//			
//			return body;
//		}
//		
//		else if (bodyName.contains("freeCoconut"))
//		{
////			body.setContactCallbackFilter(2);
////			body.setContactCallbackFlag(1);
//		}
//		
		 if (bodyName.contains("char"))
		{
			Gdx.app.log("Assets", "TEST2 " + mass);
			Vector3 localInertia = new Vector3();
//			if (mass > 0f) shape.calculateLocalInertia(mass, localInertia);
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
//		
//		else if (bodyName.contains("projectile"))
//		{
//		}
//		
//		else if (bodyName.equals("grass"))
//		{
//			Vector3 localInertia = new Vector3();
//			if (mass > 0f) shape.calculateLocalInertia(mass, localInertia);
//			btRigidBody body = new btRigidBody(mass, null, shape, localInertia);
//			shapes.add(shape);
//
//			ModelInstance instance = new ModelInstance(model, bodyName, true, true);
//			instance.transform.set(startTransform);
//
//			BulletEntity entity = new BulletEntity(instance, body);
//			entities.add(entity);
//			App.si.world.addRigidBody(body);
//			
//			entity.hide();
//			skyBox = instance;
//			
//			return body;
//		}
		
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
