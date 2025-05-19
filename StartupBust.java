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
            oddOrEven = (row*7 + col)%2 == 1;
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
            int length = name.length();
            int indexStartUp = random.nextInt(GRID_LENGTH * GRID_LENGTH);
            int row = indexStartUp / GRID_LENGTH;
            int col = indexStartUp % GRID_LENGTH;
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
    public int checkStartUp(Coords c, int length)
    {
        
        int direct = 0;
        if(c.oddOrEven)
        {
            //check in vertical for availability
            if((c.row + length) < GRID_LENGTH)
            {
                for(int i = 0; i < length; i++)
                {
                    if(grid[c.row + i][c.col] == 1)
                    {
                        direct = 0;
                        break;
                    }
                }
                direct = 2;
            }
            else if((c.row + GRID_LENGTH - length)%GRID_LENGTH < GRID_LENGTH)
            {
                for(int i = 0; i < length; i++)
                {
                    if(grid[(c.row + GRID_LENGTH - i)%GRID_LENGTH][c.col] == 1)
                    {
                        direct = 0;
                        break;
                    }
                }
                direct = 4;
            }
        }
        else
        {
            if((c.col + length) < GRID_LENGTH)
            {
                for(int i = 0; i < length; i++)
                {
                    if(grid[c.row][c.col + i] == 1)
                    {
                        direct = 0;
                        break;
                    }
                }
                direct = 3;
            }
            else if((c.col + GRID_LENGTH - length)%GRID_LENGTH < GRID_LENGTH)
            {
                for(int i = 0; i < length; i++)
                {
                    if(grid[c.row][(c.col + GRID_LENGTH - length)%GRID_LENGTH] == 1)
                    {
                        direct = 0;
                        break;
                    }
                }
                direct = 1;
            }
        }
        return direct;
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
