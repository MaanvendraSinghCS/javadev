import java.util.*;
public class StartupBust {
    
}

class GameHelper
{
    class Coords
    {
        public int row;
        public int col;
        public boolean oddOrEven;
        Coords(int row, int col)
        {
            this.row = row;
            this.col = col;
            oddOrEven = (row + col)%2 == 1;
        }
    }
    private static final int GRID_LENGTH = 7;
    private int[][] grid = new int[GRID_LENGTH][GRID_LENGTH];


    private final Random random = new Random();

    private static final int startUpsInGrid = 3;

    public List<String> makeStartUps()
    {
        List<String> StartUps = new ArrayList<>();
        for(int i = 0; i < startUpsInGrid; i++)
        {
            int length = random.nextInt(3, 6);
            char[] nameCharacters = new char[length];
            for(int j = 0; j < length; j++)
            {
                nameCharacters[j] = (char)(65 + random.nextInt(26));
            }
            String name = nameCharacters.toString();
            StartUps.add(name);
        }
        return StartUps;

    }

    public void putStartup()
    {
        List<String> names = makeStartUps();
        for(String name : names)
        {
            
        }
    }

    public String getUserInput()
    {
        System.out.print("Please enter your guess: ");
        Scanner sc = new Scanner(System.in);
        String guess = sc.nextLine();
        sc.close();
        return guess;
    }

    public Coords getIndexFromAlpha(String guess)
    {
        int r, c;
        if(guess.length() == 2)
        {
            String row = guess.substring(0, 1);
            String col = guess.substring(1,2);

            r = (int)(row.toCharArray()[0] - 'A');
            c = (int)(col.toCharArray()[0] - '0');
            return new Coords(r, c);

        }
        else return null;
        
    }
}

class StartUp
{
    private String name;
    private List<String> location;
    private boolean lifeStatus;

    StartUp(String name, List<String> location)
    {
        this.name = name;
        this.location = new ArrayList<>(location);
        lifeStatus = true;
    }

    public String getName()
    {
        return this.name;
    }
    public List<String> getLocation()
    {
        return this.location;
    }
    public boolean getLifeStatus()
    {
        return lifeStatus;
    }

    public String chekUserGuess(String guess)
    {
        String effect = "Miss";
        if(location.contains(guess) && lifeStatus)
        {
            int index = location.indexOf(guess);
            effect = "Hit";
            location.remove(index);
            if(location.isEmpty())
            {
                lifeStatus = false;
            }
        }
        else{
            return effect;
        }
        return effect;
    }
}
