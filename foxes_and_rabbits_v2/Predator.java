
/**
 * An abstract class for the Predator interface.
 * We implement this class in Fox class so we can use only the essential informetion.
 */
public interface Predator
{
    void incrementHunger();
    Location findFood();
}
