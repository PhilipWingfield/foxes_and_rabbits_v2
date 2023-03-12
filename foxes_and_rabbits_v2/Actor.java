import java.util.List;

/**
 * Animals and hunters are now under actor class.
 * We create actor interface so we can replace two lists (animals and hunters) with one list (actor).
 */
public interface Actor
{
    void act(List<Actor> field);
    
    boolean isAlive();
}
