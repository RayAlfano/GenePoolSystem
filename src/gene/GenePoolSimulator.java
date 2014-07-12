package gene;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

@SuppressWarnings("unchecked")
public class GenePoolSimulator
{
    private final static int GENERATIONS;
    private final static int POPULATION;
    public final static double MUTATION_PERCENTAGE;
    public final static double DECAY_RATE;
    private static Class<? extends MutationMethod> MUTATION_MODE = null;
    private final static Color GOAL_ORGANISM_COLOR = new Color(128, 128, 128);
    
    static
    {
        Properties prop = new Properties();
        try
        {
            prop.load(new FileInputStream("./src/config.properties"));
        }
        catch (IOException e)
        {
            System.err.println("config.properties not found");
            System.exit(1);
        }
        
        GENERATIONS = Integer.parseInt(prop.getProperty("GENERATIONS"));
        POPULATION = Integer.parseInt(prop.getProperty("POPULATION"));
        MUTATION_PERCENTAGE = Double.parseDouble(prop.getProperty("MUTATION_PERCENTAGE"));
        DECAY_RATE = Double.parseDouble(prop.getProperty("DECAY_RATE"));
        try
        {
            MUTATION_MODE = (Class<? extends MutationMethod>) GenePoolSimulator.class.getClassLoader().loadClass(prop.getProperty("MUTATION_MODE"));
        }
        catch (ClassNotFoundException e)
        {
            System.err.println("MUTATION_MODE invalid");
            System.exit(1);
        }
    }
    
    public static void printAverageFitness(List<Organism> orgs)
    {
        long fitSum = 0;
        for (Organism org : orgs)
        {
            fitSum += org.getRelativeFitness(Organism.perfectOrganism);
        }
        
        System.out.println("Average Fitness: " + (fitSum / orgs.size()));
    }
    
    public static void main(String[] args) throws InstantiationException, IllegalAccessException
    {
        Organism.mutationMethod = MUTATION_MODE.newInstance();
        Organism.perfectOrganism = new Organism(GOAL_ORGANISM_COLOR);
        
        final ArrayList<Organism> organism = new ArrayList<Organism>();
        final Random rnd = new Random();
        
        System.out.println("Starting Simulation...");
        
        System.out.print("Building Random Population... ");
        for (int i = 0; i < POPULATION; i++)
        {
            organism.add(new Organism(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
        }
        System.out.println("Done.");
        
        
        System.out.println();
        
        System.out.println("Initial Set: ");
        for (int i = 0; i < POPULATION; i++)
        {
            System.out.println(organism.get(i).toString());
        }
        printAverageFitness(organism);
        System.out.println();
        
        
        for (int g = 1; g <= GENERATIONS; g++)
        {
            //System.out.print("Simulating Generation #" + g + "... ");
            
            // Some Organisms Die
            for (int i = 0; i < POPULATION * DECAY_RATE; i++)
            {
                Organism lowestOrg = null;
                int lowestScore = Integer.MIN_VALUE;
                
                for (Organism org : organism)
                {
                    int score = org.rollWeightedDie();
                    
                    if (score > lowestScore)
                    {
                        lowestOrg = org;
                        lowestScore = score;
                    }
                }
                
                organism.remove(lowestOrg);
            }
            
            // Reproduction to fill the open slots in population
            for (int i = organism.size(); i < POPULATION; i++)
            {
                int p1 = rnd.nextInt(organism.size());
                int p2 = p1;
                while (p2 == p1) p2 = rnd.nextInt(organism.size());
                
                organism.add(new Organism(organism.get(p1), organism.get(p2)));
            }
            //System.out.println("Done.");
        }
        
        System.out.println("Ending Set: ");
        for (int i = 0; i < POPULATION; i++)
        {
            System.out.println(organism.get(i).toString());
        }
        printAverageFitness(organism);
        System.out.println("End of Simulation.");
    }
}
