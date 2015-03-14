package bolado;

/*
 * This interface will be used to represent triggers on game. 
 * They will, ideally, be represented by invisible collision volumes.
 * 
 * [!]--------------------------[!][ INTENDED USE ][!]--------------------------[!]
 * Somewhere on the project (for now on AssetLoader), we will have a Hashtable with
 * 	instances of the trigger subclasses that will be used.
 *	Ideally, we would have a Hashtable for each level;
 * 
 * On Blender all trigger objects will have the "triggerType:" prefix in the name;
 * 
 * On btBulletWorldImporter instance (on our case: AssetLoader), 
 * 	this prefix will be used to identify the object as a trigger.
 * 	When identified, the correct trigger is searched on the Hashtable 
 * 	and assigned to the object's body.userData;
 * 
 * On ContactListener instance (on our case: BulletContactListener),
 * 	the trigger.onCollision(BulletEntity ent) method will be called on callback. 
*/

public interface trigger {
	
	//What happens when one of the players collide with this trigger
	//void onCharCollision(BulletEntity ent);
	
	//What happens when a Mob collides with the trigger
	//void onMobCollision(BulletEntity ent);
	
	//What happens when anything collides with the trigger
	void onCollision(BulletEntity ent);

}
