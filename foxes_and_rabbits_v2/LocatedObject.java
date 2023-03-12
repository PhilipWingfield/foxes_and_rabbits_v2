/**
 * This class has all the information about locations and the field of the game
*/
public class LocatedObject
{
    public Field field;
    public Location location;
    
    public LocatedObject(final Field field, final Location location) 
    {
        this.field = field;
        this.setLocation(location);
    }
    
    /**
     * Returns field.
     */
    protected Field getField() 
    {
        return this.field;
    }
    
    /**
     * Returns the location.
     */
    protected Location getLocation() 
    {
        return this.location;
    }
    
    /**
     * Sets new location.
     */
    protected void setLocation(Location newLocation) 
    {
        if (this.location != null) 
        {
            this.field.clear(this.location);
        }
        this.location = newLocation;
        this.field.place((Object)this, newLocation);
    }
    
    /**
     * Clears the location of the field.
     */
    protected void remove() 
    {
        if (this.getLocation() != null) 
        {
            this.field.clear(this.location);
            this.location = null;
            this.field = null;
        }
    }
}
