import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

public class GameManager {
    // TODO: USUNAC MOZE ALBO NAPRAWIC
    boolean doubleJump = false;
    boolean nextPlayer = true;
    int x2 = 0;
    int y2 = 0;
    int boardSize = 6;
    int piecesInBase = 0;
    int baseSize = 0;
    ArrayList<Player> players = new ArrayList();
    ArrayList<Tile> tiles = new ArrayList();
    ArrayList<Piece> redPieces = new ArrayList();
    ArrayList<Piece> greenPieces = new ArrayList();
    ArrayList<Piece> bluePieces = new ArrayList();
    ArrayList<Piece> yellowPieces = new ArrayList();
    ArrayList<Piece> purplePieces = new ArrayList();
    ArrayList<Piece> cyanPieces = new ArrayList();
    ArrayList<Piece> redBase = new ArrayList();
    ArrayList<Piece> greenBase = new ArrayList();
    ArrayList<Piece> blueBase = new ArrayList();
    ArrayList<Piece> yellowBase = new ArrayList();
    ArrayList<Piece> purpleBase = new ArrayList();
    ArrayList<Piece> cyanBase = new ArrayList();
    int[] playerList;
//    private char[][] board = {
//            {'x','x','x','x','x','x','x','x','x','x','x','x','o','x','x','x','x'},
//            {'x','x','x','x','x','x','x','x','x','x','x','o','o','x','x','x','x'},
//            {'x','x','x','x','x','x','x','x','x','x','o','o','o','x','x','x','x'},
//            {'x','x','x','x','x','x','x','x','x','o','o','o','o','x','x','x','x'},
//            {'x','x','x','x','o','o','o','o','o','o','o','o','o','o','o','o','o'},
//            {'x','x','x','x','o','o','o','o','o','o','o','o','o','o','o','o','x'},
//            {'x','x','x','x','o','o','o','o','o','o','o','o','o','o','o','x','x'},
//            {'x','x','x','x','o','o','o','o','o','o','o','o','o','o','x','x','x'},
//            {'x','x','x','x','o','o','o','o','o','o','o','o','o','x','x','x','x'},
//            {'x','x','x','o','o','o','o','o','o','o','o','o','o','x','x','x','x'},
//            {'x','x','o','o','o','o','o','o','o','o','o','o','o','x','x','x','x'},
//            {'x','o','o','o','o','o','o','o','o','o','o','o','o','x','x','x','x'},
//            {'o','o','o','o','o','o','o','o','o','o','o','o','o','x','x','x','x'},
//            {'x','x','x','x','o','o','o','o','x','x','x','x','x','x','x','x','x'},
//            {'x','x','x','x','o','o','o','x','x','x','x','x','x','x','x','x','x'},
//            {'x','x','x','x','o','o','x','x','x','x','x','x','x','x','x','x','x'},
//            {'x','x','x','x','o','x','x','x','x','x','x','x','x','x','x','x','x'},
//    };
    private char[][] board = new char[4*boardSize + 1][4*boardSize + 1];

    public void communication(String com){
        for (Player player: players) {
            player.sendMessage(com);
        }
    }

    public void moveHandler(String mess, Player currentPlayer) {
        String[] values = mess.split(";");
        int clickedX = Integer.parseInt(values[1]);
        int clickedY = Integer.parseInt(values[2]);
        int currentPlayerNumber = Integer.parseInt(values[3]);
        boolean firstClick = Boolean.parseBoolean(values[4]);
        ArrayList<Piece> pieces = getCurrentPlayerPieces(currentPlayerNumber);
        ArrayList<Piece> base = getCurrentPlayerEndBase(currentPlayerNumber);
        Piece currentPiece = null;
        Tile currentTile = null;
        if(mess.startsWith("TRY1")){
            for (Piece piece: pieces) {
                if (piece.x == clickedX && piece.y == clickedY) {
                    currentPiece = piece;
                }
            }
        }
        else if(mess.startsWith("TRY2")){
            for (Tile tile: tiles){
                if (tile.x == clickedX && tile.y == clickedY) {
                    currentTile = tile;
                }
            }
        }
        if(!firstClick){
            for(Tile obje: tiles){
                if( (obje.x == currentPiece.x + 1 && obje.y == currentPiece.y ||
                        obje.x == currentPiece.x - 1 && obje.y == currentPiece.y ||
                        obje.x == currentPiece.x + 1 && obje.y == currentPiece.y - 1 ||
                        obje.x == currentPiece.x - 1 && obje.y == currentPiece.y + 1 ||
                        obje.x == currentPiece.x && obje.y == currentPiece.y + 1||
                        obje.x == currentPiece.x && obje.y == currentPiece.y - 1) && !obje.isTaken && !doubleJump){
                    currentPlayer.sendMessage("COLOR;"+obje.x+";"+obje.y+";"+currentPiece.R+";"+currentPiece.G+";"+currentPiece.B);
                    obje.isAvailable = true;
                }
                if(!doubleJump || (doubleJump && currentPiece.x == x2 && currentPiece.y == y2)){
                    if(obje.x == currentPiece.x + 1 && obje.y == currentPiece.y && obje.isTaken){
                        isTakenTileChecking(currentPiece.x + 2,currentPiece.y,currentPiece.R,currentPiece.G,currentPiece.B, currentPlayer, currentPiece);
                    }
                    if(obje.x == currentPiece.x - 1 && obje.y == currentPiece.y && obje.isTaken){
                        isTakenTileChecking(currentPiece.x - 2,currentPiece.y,currentPiece.R,currentPiece.G,currentPiece.B, currentPlayer, currentPiece);
                    }
                    if(obje.x == currentPiece.x + 1 && obje.y == currentPiece.y - 1 && obje.isTaken){
                        isTakenTileChecking(currentPiece.x + 2,currentPiece.y - 2,currentPiece.R,currentPiece.G,currentPiece.B, currentPlayer, currentPiece);
                    }
                    if(obje.x == currentPiece.x - 1 && obje.y == currentPiece.y + 1 && obje.isTaken){
                        isTakenTileChecking(currentPiece.x - 2,currentPiece.y + 2,currentPiece.R,currentPiece.G,currentPiece.B, currentPlayer, currentPiece);
                    }
                    if(obje.x == currentPiece.x && obje.y == currentPiece.y + 1 && obje.isTaken){
                        isTakenTileChecking(currentPiece.x,currentPiece.y + 2,currentPiece.R,currentPiece.G,currentPiece.B, currentPlayer, currentPiece);
                    }
                    if(obje.x == currentPiece.x && obje.y == currentPiece.y - 1 && obje.isTaken){
                        isTakenTileChecking(currentPiece.x,currentPiece.y - 2,currentPiece.R,currentPiece.G,currentPiece.B, currentPlayer, currentPiece);
                    }
                }
                for(Piece bas1: base){
                    if(currentPiece.x == bas1.x && currentPiece.y == bas1.y){
                        for(Tile obj: tiles){
                            if(obj.isAvailable){
                                obj.isAvailable = false;
                                currentPlayer.sendMessage("COLOR;"+obj.x+";"+obj.y+";"+0+";"+0+";"+0);
                                for(Piece bas2: base){
                                    if(obj.x == bas2.x && obj.y == bas2.y){
                                        obj.isAvailable = true;
                                        currentPlayer.sendMessage("COLOR;"+obj.x+";"+obj.y+";"+currentPiece.R+";"+currentPiece.G+";"+currentPiece.B);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            currentPiece.isActive = true;
        }
        else{
            if(currentTile.isAvailable){
                for(Piece pie: pieces) {
                    if (((currentTile.x == pie.x + 1 && currentTile.y == pie.y) ||
                            (currentTile.x == pie.x - 1 && currentTile.y == pie.y) ||
                            (currentTile.x == pie.x + 1 && currentTile.y == pie.y - 1) ||
                            (currentTile.x == pie.x - 1 && currentTile.y == pie.y + 1) ||
                            (currentTile.x == pie.x && currentTile.y == pie.y + 1) ||
                            (currentTile.x == pie.x && currentTile.y == pie.y - 1)) && pie.isActive){
                        nextPlayer = true;
                    }
                    else {
                        if(pie.isActive){
                            doubleJump = true;
                            x2 = pie.x;
                            y2 = pie.y;
                        }
                    }

                }
                for(Piece pie: pieces){
                    if(pie.isActive){
                        if(pie.x == x2){
                            x2 = currentTile.x;
                        }
                        if(pie.y == y2){
                            y2 = currentTile.y;
                        }
                        pie.x = currentTile.x;
                        pie.y = currentTile.y;
                        pie.isActive = false;
                        communication("MOVE;"+pie.x+";"+pie.y+";"+pie.getID()+";"+currentPlayerNumber+";"+nextPlayer);
                    }
                }
            }
            piecesInBase = 0;
            for(Piece pie: pieces){
                for(Piece bas: base){
                    if(pie.x == bas.x && pie.y == bas.y){
                        piecesInBase ++;
                    }
                }
            }
            if(piecesInBase == baseSize){
                System.out.println("Winning");
            }
            turnEnd(currentPlayer, pieces);
        }
    }

    public void nextTurn(String mess, Player currentPlayer){
        String[] values = mess.split(";");
        int currentPlayerNumber = Integer.parseInt(values[1]);
        ArrayList<Piece> pieces = getCurrentPlayerPieces(currentPlayerNumber);
        nextPlayer = true;
        turnEnd(currentPlayer, pieces);
        communication("NEXT");
    }

    private ArrayList<Piece> getCurrentPlayerPieces(int currentPlayerNumber){
        switch (currentPlayerNumber) {
            case 1 -> {
                return redPieces;
            }
            case 2 -> {
                return yellowPieces;
            }
            case 3 -> {
                return bluePieces;
            }
            case 4 -> {
                return purplePieces;
            }
            case 5 -> {
                return greenPieces;
            }
            case 6 -> {
                return cyanPieces;
            }
        }
        return null;
    }

    private ArrayList<Piece> getCurrentPlayerEndBase(int currentPlayerNumber){
        switch (currentPlayerNumber) {
            case 1 -> {
                return redBase;
            }
            case 2 -> {
                return yellowBase;
            }
            case 3 -> {
                return blueBase;
            }
            case 4 -> {
                return purpleBase;
            }
            case 5 -> {
                return greenBase;
            }
            case 6 -> {
                return cyanBase;
            }
        }
        return null;
    }

    private void turnEnd(Player currentPlayer, ArrayList<Piece> pieces) {
        for(Piece pie: pieces){
            if(pie.isActive){
                pie.isActive = false;
            }
        }

        for(Tile obje: tiles){
            obje.isAvailable = false;
            obje.isTaken = false;
            currentPlayer.sendMessage("COLOR;"+obje.x+";"+obje.y+";"+0+";"+0+";"+0);
        }
        checkTakenTiles();
        if(nextPlayer){
            doubleJump = false;
        }
        nextPlayer = false;
    }

    private void isTakenTileChecking( int a1, int a2, int c1, int c2, int c3, Player currentPlayer, Piece currentPiece){
        for(Tile objec: tiles){
            if(objec.x == a1 && objec.y == a2 && !objec.isTaken){
                objec.isAvailable = true;
                currentPlayer.sendMessage("COLOR;"+objec.x+";"+objec.y+";"+currentPiece.R+";"+currentPiece.G+";"+currentPiece.B);
            }
        }
    }

    private void checkTakenTiles(){
        checkIsTaken(redPieces);
        checkIsTaken(yellowPieces);
        checkIsTaken(bluePieces);
        checkIsTaken(purplePieces);
        checkIsTaken(greenPieces);
        checkIsTaken(cyanPieces);
    }

    private void checkIsTaken(ArrayList<Piece> pieces) {
        for (Piece piece : pieces){
            for (Tile tile : tiles) {
                if (piece.x == tile.x && piece.y == tile.y) {
                    tile.isTaken = true;
                }
            }
        }
    }





    public void startGame(String com){
        String[] values =com.split(";");
        int numberOfPlayers = Integer.parseInt(values[1]);
        prepareVirtualBoard(numberOfPlayers);
        for (Player player: players) {
            player.sendMessage(com+";"+player.getId());
        }
    }

    private void prepareVirtualBoard(int numberOfPlayers) {
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

        for(int i = 1; i <= boardSize; i++){
            baseSize += i;
        }

        playerList = new int[numberOfPlayers];
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
//        for(int i = 0; i < playerList.length; i++) {
//            switch(playerList[i]) {
//                case 1:
//                    addingPiecesToList(1,4,7,13,16,redPieces,255,0,0);
//                    creatingBase(2, 9, 12, 3, 3, redBase);
//                    break;
//                case 2:
//                    addingPiecesToList(2,0,3,12,12,yellowPieces,255,255,0);
//                    creatingBase(1, 13, 16, 4, 7, yellowBase);
//                    break;
//                case 3:
//                    addingPiecesToList(1,4,7,4,7,bluePieces,0,0,255);
//                    creatingBase(2, 9, 12, 12, 12, blueBase);
//                    break;
//                case 4:
//                    addingPiecesToList(2,9,12,3,3,purplePieces,255,0,255);
//                    creatingBase(1, 4, 7, 13, 16, purpleBase);
//                    break;
//                case 5:
//                    addingPiecesToList(1,13,16,4,7,greenPieces,0,255,0);
//                    creatingBase(2, 0, 3, 12, 12, greenBase);
//                    break;
//                case 6:
//                    addingPiecesToList(2,9,12,12,12,cyanPieces,0,255,255);
//                    creatingBase(1, 4, 7, 4, 7, cyanBase);
//                    break;
//            }
//        }
        for(int i = 0; i < playerList.length; i++) {
            switch(playerList[i]) {
                case 1:
                    addingPiecesToList(1,boardSize,2*boardSize - 1,3*boardSize + 1,4*boardSize,redPieces,255,0,0);
                    creatingBase(2, 2*boardSize + 1, 3*boardSize, boardSize - 1, boardSize - 1, redBase);
                    break;
                case 2:
                    addingPiecesToList(2,0,boardSize - 1,3*boardSize,3*boardSize,yellowPieces,255,255,0);
                    creatingBase(1, 3*boardSize +1, 4*boardSize, boardSize, 2*boardSize - 1, yellowBase);
                    break;
                case 3:
                    addingPiecesToList(1,boardSize,2*boardSize -1,boardSize,2*boardSize - 1,bluePieces,0,0,255);
                    creatingBase(2, 2*boardSize + 1, 3*boardSize, 3*boardSize, 3*boardSize, blueBase);
                    break;
                case 4:
                    addingPiecesToList(2,2*boardSize + 1,3*boardSize,boardSize - 1,boardSize - 1,purplePieces,255,0,255);
                    creatingBase(1, boardSize, 2*boardSize - 1, 3*boardSize + 1, 4*boardSize, purpleBase);
                    break;
                case 5:
                    addingPiecesToList(1,3*boardSize + 1,4*boardSize,boardSize,2*boardSize - 1,greenPieces,0,255,0);
                    creatingBase(2, 0, boardSize - 1, 3*boardSize, 3*boardSize, greenBase);
                    break;
                case 6:
                    addingPiecesToList(2,2*boardSize + 1,3*boardSize,3*boardSize,3*boardSize,cyanPieces,0,255,255);
                    creatingBase(1, boardSize, 2*boardSize - 1, boardSize, 2*boardSize - 1, cyanBase);
                    break;
            }
        }
        checkTakenTiles();
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
    private void creatingBase(int t, int j_1, int j_2, int k_1, int subs, ArrayList<Piece> pieces){
        int id=1;
        int i = subs;
        int type = t;
        for (int j = j_1; j <= j_2; j++) {
            if (type == 1) {
                for (int k = k_1; k <= i; k++) {
                    pieces.add(new Piece(id,j, k, false, 0, 0, 0));
                    id++;
                }
                i--;
            }
            else if(type == 2){
                for (int k = i; k <= k_1; k++) {
                    pieces.add(new Piece(id,j, k, false, 0, 0, 0));
                    id++;
                }
                i--;
            }
        }
    }
}