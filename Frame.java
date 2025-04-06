import java.awt.event.*;
import javax.swing.JFrame;
public class Frame extends JFrame implements KeyListener{
    private ConnectFour game;
    int red = 0;
    int yellow = 0;
    
    Frame(ConnectFour game){
        this.game = game;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(800, 800);
        this.setTitle("Connect Four");
        addKeyListener(this);
        addComponentListener(new ComponentAdapter() { // for the laggy dybamic scaling
            @Override
            public void componentResized(ComponentEvent e) {
                game.scaler();
                game.constructBoard();
            }
        });
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
        if(game.winner == 0){
            if (e.getKeyCode() == 37){
                if (game.columnSel == 0) {
                    game.columnSel = 6;
                } else {
                    game.columnSel -= 1;
                }
                game.constructBoard();
            } else if (e.getKeyCode() == 39){
                if (game.columnSel == 6) {
                    game.columnSel = 0;
                } else {
                    game.columnSel += 1;
                }
                game.constructBoard();
            } else if (e.getKeyCode() == 10){
                if(game.playerTurn == 1){
                    while(true){
                        if(game.currentTurn()){
                            break;
                        } return;
                    }
                    game.playerTurn = 2;
                } else {
                    while(true){
                        if(game.currentTurn()){
                            break;
                        } return;
                    }
                    game.playerTurn = 1;
                }
                game.constructBoard();
            }
        } else {
            if (e.getKeyCode() == 10){ // enter restarts the game
                game.gameData = new int[6][7];
                game.playerTurn = game.winner;
                if(game.winner == 1){
                    red += 1;
                } else{
                    yellow += 1;
                }
                game.winner = 0;
                System.out.println("Current Stats!\nRed Wins: " + red + "\nYellow Wins: " + yellow);
                game.constructBoard();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
}

