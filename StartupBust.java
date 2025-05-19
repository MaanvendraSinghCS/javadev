import java.util.*;

public class StartupBust {
    public static void main(String[] args) {
        GameHelper helper = new GameHelper();
        List<StartUp> startUps = helper.putStartup();
        int guesses = 0;

        while (!startUps.isEmpty()) {
            String userGuess = helper.getUserInput();
            guesses++;
            String result = "Miss";

            for (int i = 0; i < startUps.size(); i++) {
                StartUp s = startUps.get(i);
                result = s.chekUserGuess(userGuess);
                if (result.equals("Hit")) {
                    break;
                }
                if (!s.getLifeStatus()) {
                    result = "Kill " + s.getName();
                    startUps.remove(s);
                    break;
                }
            }
            System.out.println(result);
        }

        System.out.println("All Startups are busted! You took " + guesses + " guesses.");
    }
}


class GameHelper {
    class Coords {
        public int row;
        public int col;
        public boolean oddOrEven;

        Coords(int row, int col) {
            this.row = row;
            this.col = col;
            oddOrEven = (row * 7 + col) % 2 == 1;
        }
    }

    private static final int GRID_LENGTH = 7;
    private int[][] grid = new int[GRID_LENGTH][GRID_LENGTH];
    private final Random random = new Random();
    private static final int startUpsInGrid = 3;
    private final Scanner sc = new Scanner(System.in);

    public List<String> makeStartUps() {
        List<String> StartUps = new ArrayList<>();
        for (int i = 0; i < startUpsInGrid; i++) {
            int length = random.nextInt(3, 6);
            char[] nameCharacters = new char[length];
            for (int j = 0; j < length; j++) {
                nameCharacters[j] = (char) (65 + random.nextInt(26));
            }
            String name = new String(nameCharacters);  // Fix here
            StartUps.add(name);
        }
        return StartUps;
    }

    public List<StartUp> putStartup() {
        List<String> names = makeStartUps();
        List<StartUp> placed = new ArrayList<>();

        for (String name : names) {
            int length = name.length();
            boolean placedFlag = false;

            while (!placedFlag) {
                int index = random.nextInt(GRID_LENGTH * GRID_LENGTH);
                int row = index / GRID_LENGTH;
                int col = index % GRID_LENGTH;
                Coords c = new Coords(row, col);
                int direction = checkStartUp(c, length);

                if (direction != 0) {
                    List<String> loc = new ArrayList<>();
                    for (int i = 0; i < length; i++) {
                        int r = c.row, cl = c.col;
                        switch (direction) {
                            case 1 -> cl -= i;
                            case 2 -> r += i;
                            case 3 -> cl += i;
                            case 4 -> r -= i;
                        }
                        grid[r][cl] = 1;
                        loc.add("" + (char) ('A' + r) + cl);
                    }
                    placed.add(new StartUp(name, loc));
                    placedFlag = true;
                }
            }
        }

        return placed;
    }

    public String getUserInput() {
        System.out.print("Please enter your guess (e.g. A3): ");
        return sc.nextLine().trim().toUpperCase();
    }

    public Coords getIndexFromAlpha(String guess) {
        if (guess.length() == 2) {
            int row = guess.charAt(0) - 'A';
            int col = guess.charAt(1) - '0';
            if (row >= 0 && row < GRID_LENGTH && col >= 0 && col < GRID_LENGTH) {
                return new Coords(row, col);
            }
        }
        return null;
    }

    public int checkStartUp(Coords c, int length) {
        if (c.oddOrEven) {
            if (c.row + length <= GRID_LENGTH) {
                for (int i = 0; i < length; i++)
                    if (grid[c.row + i][c.col] == 1) return 0;
                return 2;
            } else if (c.row - length >= 0) {
                for (int i = 0; i < length; i++)
                    if (grid[c.row - i][c.col] == 1) return 0;
                return 4;
            }
        } else {
            if (c.col + length <= GRID_LENGTH) {
                for (int i = 0; i < length; i++)
                    if (grid[c.row][c.col + i] == 1) return 0;
                return 3;
            } else if (c.col - length >= 0) {
                for (int i = 0; i < length; i++)
                    if (grid[c.row][c.col - i] == 1) return 0;
                return 1;
            }
        }
        return 0;
    }
}

class StartUp {
    private final String name;
    private final List<String> location;
    private boolean lifeStatus;

    StartUp(String name, List<String> location) {
        this.name = name;
        this.location = new ArrayList<>(location);
        lifeStatus = true;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getLocation() {
        return this.location;
    }

    public boolean getLifeStatus() {
        return lifeStatus;
    }

    public String chekUserGuess(String guess) {
        String effect = "Miss";
        if (location.contains(guess) && lifeStatus) {
            location.remove(guess);
            effect = "Hit";
            if (location.isEmpty()) {
                lifeStatus = false;
            }
        }
        return effect;
    }
}
