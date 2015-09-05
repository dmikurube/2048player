package dmikurube;

import main.Player;


public class DMPlayer extends Player {
    public DMPlayer(){
        offence = new DMOffence();
        defence = new DMDefence();
    }
}
