package dmikurube;

import main.Player;


public class MyopicPlayer extends Player {
    public MyopicPlayer(){
        offence = new MyopicOffence();
        defence = new MyopicDefence();
    }
}
