import javax.swing.*;
import java.awt.*;

public class ConnectFour{
    public int columnSel;
    private Frame myFrame;
    private JPanel oldGridPanel;
    public int playerTurn;
    private int[][] gameData;
    ImageIcon square = new ImageIcon("square.png");
    ImageIcon redCircle = new ImageIcon("redCircle.png");
    ImageIcon yellowCircle = new ImageIcon("yellowCircle.png");

    ImageIcon selSquare = new ImageIcon("selSquare.png");
    ImageIcon selRedCircle = new ImageIcon("selRedCircle.png");
    ImageIcon selYellowCircle = new ImageIcon("selYellowCircle.png");
    
    ConnectFour(){
        this.columnSel = 3;
        this.myFrame = new Frame(this);
        this.oldGridPanel = null;
        this.playerTurn = 1; // 1 is P1, 2 is P2
        this.gameData = new int[6][7];
        this.square = scaler(square);
        this.redCircle = scaler(redCircle);
        this.yellowCircle = scaler(yellowCircle);
        this.selSquare = scaler(selSquare);
        this.selRedCircle = scaler(selRedCircle);
        this.selYellowCircle = scaler(selYellowCircle);
    }

    public void constructBoard(){
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(6, 7)); 

        JLabel[][] board = new JLabel[6][7];


        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(gameData[i][j] == 1){
                    if(columnSel == j){
                        board[i][j] = new JLabel(selRedCircle);
                    } else{
                        board[i][j] = new JLabel(redCircle);
                    }
                    gridPanel.add(board[i][j]);
                } else if (gameData[i][j] == 2){
                    if(columnSel == j){
                        board[i][j] = new JLabel(selYellowCircle);
                    } else{
                        board[i][j] = new JLabel(yellowCircle);
                    }
                    gridPanel.add(board[i][j]);
                } else{
                    if(columnSel == j){
                        board[i][j] = new JLabel(selSquare);
                    } else{
                        board[i][j] = new JLabel(square);
                    }
                    gridPanel.add(board[i][j]);
                }
            }
        }

        if (oldGridPanel != null){
            myFrame.remove(oldGridPanel);
        }

        oldGridPanel = gridPanel;
        myFrame.add(gridPanel); 
        myFrame.revalidate();
        myFrame.repaint();
    }

    public static ImageIcon scaler(ImageIcon original) {
        int cellSizeH = 114;
        int cellSizeV = 133;
        Image image = original.getImage();
        Image scaled = image.getScaledInstance(cellSizeH, cellSizeV, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public boolean currentTurn() { // logic checks for current turn of P1 or P2
        for(int i = 5; i > -1; i--){
            if (gameData[i][columnSel] != 0){
                continue;
            } else {
                gameData[i][columnSel] = playerTurn;
                checkForWin(i, columnSel);
                return true;
            }
        }
        return false; // failed


    }

    public void checkForWin(int row, int column) { // just runs after every turn cus lazy idk man
        int valueV = 1; 
        int valueH = 1;
        int valueDR = 1;
        int valueDL = 1;
        int temp = 0;

        for(int i = 0; i < gameData.length; i++){ // best way i thought I could do it is check each element and how they "interact" with the target
            for(int j = 0; j < gameData[0].length; j++){
                temp = 0;

                if(j == column && gameData[i][j] == gameData[row][column]){ // vertical connect test
                    for(int k = Math.min(i, row); k < Math.max(i, row); k++){
                        if(gameData[k][column] != gameData[row][column]){
                            break;
                        } else if(k == Math.max(i, row) - 1){
                            valueV += 1;
                        }
                    } 
                } else if (i == row && gameData[i][j] == gameData[row][column]){ // horizontal test
                    for(int l = Math.min(j, column); l < Math.max(j, column); l++){
                        if(gameData[row][l] != gameData[row][column]){
                            break;
                        } else if(l == Math.max(j, column) - 1){ // this made me want to kms icl holy shit it has been 40m on this if statement i need sleep it's because I had fucking L instead of j in max holy shit i'm crahsing out
                            valueH += 1;
                        }
                    } 
                } else if ((i - row) == (column - j) && gameData[i][j] == gameData[row][column] ){ // right diag test
                    int maxI = Math.max(i, row);
                    for(int m = Math.min(j, column); m < Math.max(j, column); m++){
                        if(gameData[maxI - temp][m] != gameData[row][column]){
                            break;
                        } else if(m == Math.max(j, column) - 1){ 
                            valueDR += 1;
                        }   temp += 1;
                    }
                } else if ((i - row) == (j - column) && gameData[i][j] == gameData[row][column]){ // left diag test
                    int maxI = Math.min(i, row);
                    for(int m = Math.min(j, column); m < Math.max(j, column); m++){
                        if(gameData[maxI + temp][m] != gameData[row][column]){
                            break;
                        } else if(m == Math.max(j, column) - 1){ 
                            valueDL += 1;
                        }   temp += 1;
                    }
                } 
            }
        }

        if(valueV == 4|| valueH == 4 || valueDR == 4 || valueDL == 4){
            System.out.println("Player: " + playerTurn + " WON!");
            JLabel won = new JLabel();
            won.setText("Player: " + playerTurn + " HAS ONE!!!"); // yes i spelt won like that and think it's funny to keep it atp

        }

    }

    public static void main(String[] args){
        ConnectFour game = new ConnectFour();
        game.constructBoard();
    }
}

