import java.util.Iterator;
import java.util.List;

/**
 * Here we create hunters.
 * Hunters can move one step at a time, if there is a free space.
 * Hunters can kill all the animals that are in the its neighboring cells.
 */
public class Hunter extends LocatedObject implements Actor
{
    private Field field;
    private Location location;
    
    /**
     * Creates a hunter.
     * Hunters have no age and they don't die. 
     */
    public Hunter(Field field, Location location)
    {
        super(field, location);
    }

    /**
     * Here we choose how the hunter can act in the field.
     */
    public void act (List<Actor> newActors)
    {
        Location newLocation =getField().freeAdjacentLocation(getLocation());
        if (newLocation != null) 
        {
            setLocation(newLocation);
        }
        Field field = getField();
        List<Location> adjacent = (List<Location>)field.adjacentLocations(getLocation());
        for (final Location where : adjacent) 
        {
            Object obj = field.getObjectAt(where);
            if (obj instanceof Animal) 
            {
                Animal animal = (Animal)obj;
                if (!animal.isAlive()) 
                {
                    continue;
                }
                animal.setDead();
            }
        }
    }
    
    /**
     * Hunter is always alive and can't die.
     */
    public boolean isAlive() 
    {
        return true;
    }
    }

