import java.awt.event.*;
import javax.swing.JFrame;
public class Frame extends JFrame implements KeyListener{
    private ConnectFour game;
    
    Frame(ConnectFour game){
        this.game = game;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(800, 800);
        this.setTitle("Connect Four");
        this.setResizable(false);
        addKeyListener(this);
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}