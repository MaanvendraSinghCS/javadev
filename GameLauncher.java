import java.util.Random;
public class GameLauncher
{
    public static void main(String[] args) {
        GueassGame game = new GueassGame();
        game.startGame();
    }
}
class GueassGame
{
    Player p1;
    Player p2;
    Player p3;

    public void startGame()
    {
        p1 = new Player();
        p2 = new Player();
        p3 = new Player();

        
        Random r = new Random();
        int target = r.nextInt(20);
        System.out.println("Number to guess is: " + target);
        while(true)
        {
            p1.guess();
            p2.guess();
            p3.guess();
            System.out.println("p1 guesses : " + p1.number + "\n" + "p2 guesses : " + p2.number + "\n" + "p3 guesses : " + p3.number + "\n" );

            String announce = p1.number == target ? "p1 guessed correct" : p2.number == target ? "p2 guessed correct" : p3.number == target ? "p3 guessed correct" : "No one guessed, run it again";
            if(!announce.equals("No one guessed, run it again"))
            {
                System.out.println(announce);
                break;
            }
            else
            {
                System.out.println(announce);
            }
        }

        
    }
}
class Player
{
    int number;
    public void guess()
    {
        Random g = new Random();
        number = g.nextInt(20);
    }
}