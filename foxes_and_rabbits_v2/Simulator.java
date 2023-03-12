import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import java.util.Collection;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;
    // The probability that a hunter will be created in any given grid position.
    private static final double HUNTER_CREATION_PROBABILITY = 0.005;
    // The probability that a mountain will be created in any given grid position.
    private static final double MOUNTAIN_CREATION_PROBABILITY = 0.02;
    
    // List of actors in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) 
        {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        actors = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor((Class)Rabbit.class, Color.ORANGE);
        view.setColor((Class)Fox.class, Color.BLUE);
        view.setColor((Class)Hunter.class, Color.RED);
        view.setColor((Class)Mountain.class, Color.GRAY);
        
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) 
        {
            simulateOneStep();
            //delay(60);   // uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
        
        // Provide space for new actors.
        List<Actor> newActors = new ArrayList<>();        
        // Let all actors act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) 
        {
            Actor actor = it.next();
            actor.act(newActors);
            if(! actor.isAlive()) 
            {
                it.remove();
            }
        }
               
        // Add the newly born foxes and rabbits and the new hunters to the main lists.
        actors.addAll(newActors);
        view.showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with foxes, rabbits, hunters and mountains.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) 
        {
            for(int col = 0; col < field.getWidth(); col++) 
            {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) 
                {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    actors.add((Actor)fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) 
                {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    actors.add((Actor)rabbit);
                }
                else if (rand.nextDouble() <= HUNTER_CREATION_PROBABILITY)
                {
                    Location location = new Location(row, col);
                    Hunter hunter = new Hunter(field, location);
                    actors.add((Actor)hunter);
                }
                if (rand.nextDouble() <= MOUNTAIN_CREATION_PROBABILITY)
                {
                    Location location = new Location(row, col);
                    Mountain mountain = new Mountain (field, location);
                    for ( Location locate : field.getFreeAdjacentLocations(location)) 
                    {
                         mountain = new Mountain(field, locate);
                    }
                    // else leave the location empty.
                }
            }
            }
        }
        
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try 
        {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) 
        {
            // wake up
        }
    }
}
