public class PlacedObject_obselete
{
    public Field field;
    public Location location;
    
    public PlacedObject_obselete(final Field field, final Location location) 
    {
        this.field = field;
        this.setLocation(location);
    }
    
    protected Location getLocation() 
    {
        return this.location;
    }
    
    protected void setLocation(final Location newLocation) 
    {
        if (this.location != null) 
        {
            this.field.clear(this.location);
        }
        this.location = newLocation;
        this.field.place((Object)this, newLocation);
    }
    
    protected Field getField() 
    {
        return this.field;
    }
    
    protected void remove() {
        if (this.getLocation() != null) 
        {
            this.field.clear(this.location);
            this.location = null;
            this.field = null;
        }
    }
}
