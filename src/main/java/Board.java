import javax.swing.*;
import java.util.ArrayList;

public abstract class Board extends JFrame {
    public ArrayList<Tile> tiles = new ArrayList();
    public ArrayList<Piece> redPieces = new ArrayList();
    public ArrayList<Piece> greenPieces = new ArrayList();
    public ArrayList<Piece> bluePieces = new ArrayList();
    public ArrayList<Piece> yellowPieces = new ArrayList();
    public ArrayList<Piece> purplePieces = new ArrayList();
    public ArrayList<Piece> cyanPieces = new ArrayList();

    int boardSize = 4;
    public int[] playerList;
    public char[][] board = new char[4*boardSize + 1][4*boardSize + 1];

    protected void createBoard(){
        int k = 0;

        for(int i = 0; i <= 4*boardSize; i++) {
            for(int j = 0; j <= 4*boardSize; j++){
                board[i][j] = 'x';
            }
        }

        for(int i = boardSize; i<= 3*boardSize; i++) {
            for(int j =boardSize; j <= 3*boardSize; j++){
                board[i][j] = 'o';
            }
        }

        k = 4*boardSize;
        for(int i = boardSize; i <= 2*boardSize - 1; i++){
            for(int j = 3*boardSize + 1; j <= k; j++) {
                board[i][j] = 'o';
            }
            k--;
        }

        k = boardSize -1;
        for(int i = 2*boardSize + 1; i <= 3*boardSize; i++){
            for(int j = k; j <= boardSize - 1; j++){
                board[i][j] = 'o';
            }
            k--;
        }

        k = 3*boardSize;
        for(int i = 0; i <= boardSize - 1; i++) {
            for(int j = k; j <= 3*boardSize; j++) {
                board[i][j] ='o';
            }
            k--;
        }

        k = 2*boardSize - 1;
        for(int i = 3*boardSize + 1; i <= 4*boardSize; i++) {
            for(int j = boardSize; j <= k; j++) {
                board[i][j] = 'o';
            }
            k--;
        }



        for(int x = 0; x <=4 * boardSize; x++) {
            for(int y = 0; y <= 4 * boardSize; y++) {
                if(board[x][y] == 'o'){
                    tiles.add(new Tile(x,y, false, false, 0, 0, 0));
                }
            }
        }
    }

    protected void createPlayerList(int numberOfPlayers){
        switch(numberOfPlayers){
            case 2:
                playerList[0] = 1;
                playerList[1] = 4;
                break;
            case 3:
                playerList[0] = 1;
                playerList[1] = 3;
                playerList[2] = 5;
                break;
            case 4:
                playerList[0] = 2;
                playerList[1] = 3;
                playerList[2] = 5;
                playerList[3] = 6;
                break;
            case 6:
                playerList[0] = 1;
                playerList[1] = 2;
                playerList[2] = 3;
                playerList[3] = 4;
                playerList[4] = 5;
                playerList[5] = 6;
                break;
        }
    }

    protected void addPieces(){
        for(int i = 0; i < playerList.length; i++) {
            switch(playerList[i]) {
                case 1:
                    addingPiecesToList(1,boardSize,2*boardSize - 1,3*boardSize + 1,4*boardSize,redPieces,255,0,0);
                    break;
                case 2:
                    addingPiecesToList(2,0,boardSize - 1,3*boardSize,3*boardSize,yellowPieces,255,255,0);
                    break;
                case 3:
                    addingPiecesToList(1,boardSize,2*boardSize -1,boardSize,2*boardSize - 1,bluePieces,0,0,255);
                    break;
                case 4:
                    addingPiecesToList(2,2*boardSize + 1,3*boardSize,boardSize - 1,boardSize - 1,purplePieces,255,0,255);
                    break;
                case 5:
                    addingPiecesToList(1,3*boardSize + 1,4*boardSize,boardSize,2*boardSize - 1,greenPieces,0,255,0);
                    break;
                case 6:
                    addingPiecesToList(2,2*boardSize + 1,3*boardSize,3*boardSize,3*boardSize,cyanPieces,0,255,255);
                    break;
            }
        }
    }

    private void addingPiecesToList(int t, int j_1, int j_2, int k_1, int subs, ArrayList<Piece> pieces, int R, int G, int B){
        int id=1;
        int i = subs;
        int type = t;
        for (int j = j_1; j <= j_2; j++) {
            if (type == 1) {
                for (int k = k_1; k <= i; k++) {
                    pieces.add(new Piece(id,j, k, false, R, G, B));
                    id++;
                }
                i--;
            }
            else if(type == 2){
                for (int k = i; k <= k_1; k++) {
                    pieces.add(new Piece(id,j, k, false, R, G, B));
                    id++;
                }
                i--;
            }
        }
    }
}
