import javax.swing.*;
import java.awt.*;

public class ConnectFour{
    public int columnSel;
    private Frame myFrame;
    private JPanel oldGridPanel;
    public int playerTurn;
    int[][] gameData;
    private int[][] storeData;
    int winner;
    private ImageIcon square = new ImageIcon("square.png");
    private ImageIcon redCircle = new ImageIcon("redCircle.png");
    private ImageIcon yellowCircle = new ImageIcon("yellowCircle.png");

    private ImageIcon selSquare = new ImageIcon("selSquare.png");
    private ImageIcon selRedCircle = new ImageIcon("selRedCircle.png");
    private ImageIcon selYellowCircle = new ImageIcon("selYellowCircle.png");
    
    private ImageIcon winRedCircle = new ImageIcon("winRedCircle.png");
    private ImageIcon winYellowCircle = new ImageIcon("winYellowCircle.png");

    private ImageIcon[] images = {square, redCircle, yellowCircle, selSquare, selRedCircle, selYellowCircle, winRedCircle, winYellowCircle};

    ConnectFour(){
        this.columnSel = 3;
        this.myFrame = new Frame(this);
        this.oldGridPanel = null;
        this.playerTurn = 1; // 1 is P1, 2 is P2
        this.gameData = new int[6][7];
        this.winner = 0;
        scaler();
    }

    public void constructBoard(){
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(6, 7)); 

        JLabel[][] board = new JLabel[6][7];


        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(winner != 0 && gameData[i][j] == winner){
                    boolean flag = false;
                    for(int k = 0; k < storeData[4].length; k += 2){
                        if(storeData[4][k] == i && storeData[4][k + 1] == j){
                            if(winner == 1){
                                board[i][j] = new JLabel(winRedCircle);

                            }
                            else{
                                board[i][j] = new JLabel(winYellowCircle);
                            }

                            gridPanel.add(board[i][j]);
                            flag = true;
                            break;
                        }
                    }
                    if(flag){
                        continue;
                    }
                }

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

    public void scaler() {
        int cellSizeH = (myFrame.getWidth() / 7);
        int cellSizeV = (myFrame.getHeight() / 6);
        for(int i = 0; i < images.length; i++){
            Image newImage = images[i].getImage();
            Image scaled = newImage.getScaledInstance(cellSizeH, cellSizeV, java.awt.Image.SCALE_SMOOTH);
            images[i] = new ImageIcon(scaled);
        }
        square = images[0];
        redCircle = images[1];
        yellowCircle = images[2];
        selSquare = images[3];
        selRedCircle = images[4];
        selYellowCircle = images[5];
        winRedCircle = images[6];
        winYellowCircle = images[7];
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
        storeData = new int[5][8]; // 0 = vert, 1 = hori, 2 = DR, 3 = DL, 4 = winners
        int temp = 0;

        for(int i = 0; i < gameData.length; i++){ // best way i thought I could do it is check each element and how they "interact" with the target
            for(int j = 0; j < gameData[0].length; j++){
                temp = 0;

                if(j == column && gameData[i][j] == gameData[row][column]){ // vertical connect test
                    for(int k = Math.min(i, row); k < Math.max(i, row); k++){
                        if(gameData[k][column] != gameData[row][column]){
                            break;
                        } else if(k == Math.max(i, row) - 1){
                            storeData[0][2 * valueV - 2] = i;
                            storeData[0][2 * valueV - 1] = j;
                            valueV += 1;
                        }
                    } 
                } else if (i == row && gameData[i][j] == gameData[row][column]){ // horizontal test
                    for(int l = Math.min(j, column); l < Math.max(j, column); l++){
                        if(gameData[row][l] != gameData[row][column]){
                            break;
                        } else if(l == Math.max(j, column) - 1){ // this made me want to kms icl holy shit it has been 40m on this if statement i need sleep it's because I had fucking L instead of j in max holy shit i'm crahsing out
                        storeData[1][2 * valueH - 2] = i;
                        storeData[1][2 * valueH - 1] = j;
                            valueH += 1;
                        }
                    } 
                } else if ((i - row) == (column - j) && gameData[i][j] == gameData[row][column] ){ // right diag test
                    int maxI = Math.max(i, row);
                    for(int m = Math.min(j, column); m < Math.max(j, column); m++){
                        if(gameData[maxI - temp][m] != gameData[row][column]){
                            break;
                        } else if(m == Math.max(j, column) - 1){ 
                            storeData[2][2 * valueDR - 2] = i;
                            storeData[2][2 * valueDR - 1] = j;
                            valueDR += 1;
                        }   temp += 1;
                    }
                } else if ((i - row) == (j - column) && gameData[i][j] == gameData[row][column]){ // left diag test
                    int maxI = Math.min(i, row);
                    for(int m = Math.min(j, column); m < Math.max(j, column); m++){
                        if(gameData[maxI + temp][m] != gameData[row][column]){
                            break;
                        } else if(m == Math.max(j, column) - 1){ 
                            storeData[3][2 * valueDL - 2] = i;
                            storeData[3][2 * valueDL - 1] = j;
                            valueDL += 1;
                        }   temp += 1;
                    }
                } 
            }
        }

        if(valueV == 4|| valueH == 4 || valueDR == 4 || valueDL == 4){
            System.out.println("Player: " + playerTurn + " WON!");
            for(int[] data : storeData){
                if (data[4] == 0 && data[5] == 0){ // shold never equal row 0 at the end
                    continue;
                }     // now we have [a, b, c, d, e, f] where [a, b], [c, d], and [e, f]
                int x = 0;
                data[6] = row;
                data[7] = column;
                for(int store : data){
                    storeData[4][x] = store; // so we know in constructBoard where the winning must be
                    x += 1;
                    if(x >= 8){
                        break;
                    }
                }

            }
        
            winner = playerTurn;
        }
    }

    public static void main(String[] args){
        ConnectFour game = new ConnectFour();
        game.constructBoard();
    }
}

