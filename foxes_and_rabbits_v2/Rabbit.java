import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Rabbit extends Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        if(randomAge) 
        {
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    /**
     * Returns max age for rabbits.
     */
    protected int getMaxAge()
    {
        return MAX_AGE;
    }
    
    /**
     * Returns new rabbit.
     */
    protected Animal getYoung(final Field field, final Location location)     
    {
        return new Rabbit(false, field, location);
    }
    
    /**
     * Returns breeding age for rabbits.
     */
    protected int getBreedingAge() 
    {
        return BREEDING_AGE;
    }
    
    /**
     * Returns max litter size for rabbits.
     */
    protected int getMaxLitterSize() 
    {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Returns breeding probability for rabbits.
     */
    protected double getBreedingProbability() 
    {
        return BREEDING_PROBABILITY;
    }
    
}
