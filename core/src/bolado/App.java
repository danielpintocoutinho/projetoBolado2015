package bolado;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.math.FloatCounter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.utils.PerformanceCounter;

public class App implements ApplicationListener
{
	static App si;
	
	AssetLoader assetLoader;
	
	public PerspectiveCamera camera;
	public ModelBatch modelBatch;
	public Environment environment;
	public DirectionalLight light;
	public ModelBatch shadowBatch;

	public StringBuilder performance = new StringBuilder();
	public PerformanceCounter performanceCounter = new PerformanceCounter(this.getClass().getSimpleName());
	public FloatCounter fpsCounter = new FloatCounter(5);
	
	btDiscreteDynamicsWorld world;
	btDefaultCollisionConfiguration collisionConfiguration;
	btCollisionDispatcher dispatcher;
	btDbvtBroadphase broadphase;
	btSequentialImpulseConstraintSolver solver;
	DebugDrawer drawerDrawer;
	boolean debugModeOn = false;

	inputHandler inputHandler;
	
	BulletContactListener contactProcessedListener;
	
	@Override
	public void create()
	{
		si = this;
		
		// Bullet initialization
		Bullet.init();
		collisionConfiguration = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfiguration);
		broadphase = new btDbvtBroadphase();
		solver = new btSequentialImpulseConstraintSolver();
		world = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
		
		// Debug drawer initialization
		drawerDrawer = new DebugDrawer();
		world.setDebugDrawer(drawerDrawer);
		drawerDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_DrawWireframe);
		
		// Import our assets
		assetLoader = new AssetLoader(world);

		// Create the model batch
		modelBatch = new ModelBatch();
		
		// Setup lights
		setupLights();
		
		// Setup the camera
		camera = new PerspectiveCamera(67f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(20, 20, 25);
		camera.up.set(0, 0, 1);
		camera.lookAt(0, 0, 0);
		camera.update();
		
		// Set the input handler
		inputHandler = new inputHandler(camera);
		Gdx.input.setInputProcessor(inputHandler);
		
		// Setup the contact listener
		contactProcessedListener = new BulletContactListener();
//		contactProcessedListener.bodies = AssetLoader.si.movableBodies;
	}
	


	@Override
	public void render()
	{
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(.2f, .2f, .2f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		inputHandler.update();
		
		if (debugModeOn)
		{
			fpsCounter.put(Gdx.graphics.getFramesPerSecond());

			performanceCounter.tick();
			performanceCounter.start();
		}
		
		((btDynamicsWorld) world).stepSimulation(Gdx.graphics.getDeltaTime(), 20);

		if (debugModeOn)
		{
			performanceCounter.stop();
		}

		renderShadows();
		
		modelBatch.begin(camera);
		modelBatch.render(BulletEntity.instances, environment);
		modelBatch.end();
		
		if (debugModeOn)
		{
			drawerDrawer.begin(camera);
			world.debugDrawWorld();
			drawerDrawer.end();

			performance.setLength(0);
			performance.append("FPS: ").append(fpsCounter.value).append(", Bullet: ").append((int) (performanceCounter.load.value * 100f)).append("%");
			Gdx.app.log("FPS:", performance.toString());
		}
	}

	@Override
	public void resize(int width, int height)
	{
		camera.viewportWidth = Gdx.graphics.getWidth();
		camera.viewportHeight = Gdx.graphics.getHeight();
		camera.update();
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}
	
	@Override
	public void dispose()
	{
		world.dispose();
		solver.dispose();
		broadphase.dispose();
		dispatcher.dispose();
		collisionConfiguration.dispose();
		
		modelBatch.dispose();
		assetLoader.dispose();
	}

	@SuppressWarnings("deprecation")
	private void setupLights()
	{
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.6f, 0.6f, 0.6f, 1.f));
		light = new DirectionalShadowLight(4096, 4096, 180f, 180f, 1f, 300f);
		light.set(0.8f, 0.8f, 0.8f, -0.5f, -1f, -0.7f);
		environment.add(light);
		environment.shadowMap = (DirectionalShadowLight)light;
		shadowBatch = new ModelBatch(new DepthShaderProvider());
	}
	
	@SuppressWarnings("deprecation")
	private void renderShadows()
	{
		((DirectionalShadowLight)light).begin(Vector3.Zero, camera.direction);
		shadowBatch.begin(((DirectionalShadowLight)light).getCamera());
		shadowBatch.render(BulletEntity.instances, environment);
		shadowBatch.end();
		((DirectionalShadowLight)light).end();
	}

}