import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal extends LocatedObject implements Actor
{
    // Whether the animal is alive or not.
    public boolean alive;
    //The animal's age.
    public int age;
    // A shared random number generator to control breeding.
    private static final Random rand;
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        super(field, location);
        alive = true;
        age=0;
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    public void act(List<Actor> newActors) 
    {
        incrementAge();
        if (this instanceof Predator) 
        {
            ((Predator)this).incrementHunger();
        }
        if (this.isAlive()) 
        {
            giveBirth(newActors);
            Location newLocation = null;
            if (this instanceof Predator) 
            {
                newLocation = ((Predator)this).findFood();
            }
            if (newLocation == null) 
            {
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            if (newLocation != null) 
            {
                setLocation(newLocation);
            }
            else 
            {
                setDead();
            }
        }
    }
        
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        remove();
    }
    
    protected void setAge(final int newAge) 
    {
        age = newAge;
    }
    
    /**
     * Returns the age of the animals.
     */
    protected int getAge()
    {
        return age;
    }
    
    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    protected void incrementAge()
    {
        age++;
        if (age > getMaxAge())
        {
                setDead();
        }
    }
    
     /**
     * Get max age for every animal.
     */
    protected abstract int getMaxAge();
    
    /**
    * Calculates the number of babies thath will be born base on BREEDING_PROBABILITY and the maximum number of babies MAX_LITTER_SIZE.
    */
    protected int breed() 
    {
        int births = 0;
        if (canBreed() && Animal.rand.nextDouble() <= getBreedingProbability()) 
        {
            births = Animal.rand.nextInt(this.getMaxLitterSize()) + 1;
        }
        return births;
    }
    
    /**
     * Examines whether it has reached reproductive age.
     */
    protected boolean canBreed() 
    {
        return getAge() >= getBreedingAge();
    }
    
    /**
     * Gets from every animal the breeding probality.
     */
    protected abstract double getBreedingProbability();
    
    /**
     * Gets from every animal the breeding age.
     */
    protected abstract int getBreedingAge();
    
    /**
     * Gets from every animal the max litter size.
     */
    protected abstract int getMaxLitterSize();
    
    /**
    *  The animal gives birth to babies that are placed in adjacent positions
    *  Added to the list newRabbits and newFoxes respectively. 
    */
    protected void giveBirth (List<Actor> newActors)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        for(int births = breed(), b=0; b < births && free.size() > 0; b++) 
        {
            Location loc = free.remove(0);
            Animal young = getYoung(field, loc);
            newActors.add(young);
        }
    }
    
    protected abstract Animal getYoung(Field field, Location location);
    
    static 
    {
        rand = Randomizer.getRandom();
    }
}

