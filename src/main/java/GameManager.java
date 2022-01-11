import java.util.ArrayList;
import java.util.stream.IntStream;

public class GameManager extends Board {
    boolean doubleJump = false;
    boolean nextPlayer = false;
    int x2 = 0;
    int y2 = 0;
    int piecesInBase = 0;
    int baseSize = 0;
    int allPiecesInBase = 0;
    int blockedPieces = 0;
    ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Piece> redBase = new ArrayList();
    public ArrayList<Piece> greenBase = new ArrayList();
    public ArrayList<Piece> blueBase = new ArrayList();
    public ArrayList<Piece> yellowBase = new ArrayList();
    public ArrayList<Piece> purpleBase = new ArrayList();
    public ArrayList<Piece> cyanBase = new ArrayList();
    ArrayList<Piece> redBlocked = new ArrayList<>();
    ArrayList<Piece> yellowBlocked = new ArrayList<>();
    ArrayList<Piece> blueBlocked = new ArrayList<>();
    ArrayList<Piece> purpleBlocked = new ArrayList<>();
    ArrayList<Piece> greenBlocked = new ArrayList<>();
    ArrayList<Piece> cyanBlocked = new ArrayList<>();
    int currentPlayerId=1;
    int[] winnersId;
    int winnersCount;

    public void communication(String com) {
        for (Player player : players) {
            player.sendMessage(com);
        }
    }

    public void moveHandler(String mess, Player currentPlayer) {
        String[] values = mess.split(";");
        int clickedX = Integer.parseInt(values[1]);
        int clickedY = Integer.parseInt(values[2]);
        int currentPlayerNumber = Integer.parseInt(values[3]);
        if (currentPlayerNumber!=playerList[currentPlayerId-1]){
            return;
        }
        ArrayList<Piece> pieces = getCurrentPlayerPieces(currentPlayerNumber);
        ArrayList<Piece> base = getCurrentPlayerEndBase(currentPlayerNumber);
        ArrayList<Piece> block = getCurrentPlayerBlockedTiles(currentPlayerNumber);
        Piece currentPiece;
        Tile currentTile;
        if (mess.startsWith("TRY1")) {
            for (Piece piece : pieces) {
                if (piece.x == clickedX && piece.y == clickedY) {
                    currentPiece = piece;
                    firstClick(currentPlayer, base, currentPiece);
                }
            }
        } else if (mess.startsWith("TRY2")) {
            for (Tile tile : tiles) {
                if (tile.x == clickedX && tile.y == clickedY) {
                    currentTile = tile;
                    secondClick(currentPlayerNumber, pieces, currentTile);
                    countingPiecesinBase(pieces, base);
                    countingBlockedPieces(pieces, block);
                    gettingAllPiecesInBase(base);
                    checkEvent();
                    turnEnd(currentPlayer, pieces);
                }
            }
        }
    }

    private void checkEvent() {
        if (piecesInBase == baseSize) {
            System.out.println("Winning");
            winnersId[winnersCount]=currentPlayerId;
            communication("WIN;"+currentPlayerId);
            winnersCount++;
        } else if (blockedPieces == (2 * boardSize - 1) && allPiecesInBase == baseSize) {
            System.out.println("Blokada");
        }
    }

    private void gettingAllPiecesInBase(ArrayList<Piece> base) {
        allPiecesInBase = 0;
        for (Piece bas : base) {
            for (Tile til : tiles) {
                if (bas.x == til.x && bas.y == til.y && til.isTaken) {
                    allPiecesInBase++;
                }
            }
        }
    }

    private void countingBlockedPieces(ArrayList<Piece> pieces, ArrayList<Piece> block) {
        blockedPieces = 0;
        for (Piece pie : pieces) {
            for (Piece blo : block) {
                if (pie.x == blo.x && pie.y == blo.y) {
                    blockedPieces++;
                }
            }
        }
    }

    private void countingPiecesinBase(ArrayList<Piece> pieces, ArrayList<Piece> base) {
        piecesInBase = 0;
        for (Piece pie : pieces) {
            for (Piece bas : base) {
                if (pie.x == bas.x && pie.y == bas.y) {
                    piecesInBase++;
                }
            }
        }
    }

    private void secondClick(int currentPlayerNumber, ArrayList<Piece> pieces, Tile currentTile) {
        if (currentTile.isAvailable) {
            for (Piece pie : pieces) {
                if (((currentTile.x == pie.x + 1 && currentTile.y == pie.y) ||
                        (currentTile.x == pie.x - 1 && currentTile.y == pie.y) ||
                        (currentTile.x == pie.x + 1 && currentTile.y == pie.y - 1) ||
                        (currentTile.x == pie.x - 1 && currentTile.y == pie.y + 1) ||
                        (currentTile.x == pie.x && currentTile.y == pie.y + 1) ||
                        (currentTile.x == pie.x && currentTile.y == pie.y - 1)) && pie.isActive) {
                    nextPlayer = true;
                } else {
                    if (pie.isActive) {
                        doubleJump = true;
                        x2 = pie.x;
                        y2 = pie.y;
                    }
                }

            }
            for (Piece pie : pieces) {
                if (pie.isActive) {
                    if (pie.x == x2) {
                        x2 = currentTile.x;
                    }
                    if (pie.y == y2) {
                        y2 = currentTile.y;
                    }
                    pie.x = currentTile.x;
                    pie.y = currentTile.y;
                    pie.isActive = false;
                    communication("MOVE;" + pie.x + ";" + pie.y + ";" + pie.getID() + ";" + currentPlayerNumber);
                }
            }
        }
    }

    private void firstClick(Player currentPlayer, ArrayList<Piece> base, Piece currentPiece) {
        for (Tile obje : tiles) {
            if ((obje.x == currentPiece.x + 1 && obje.y == currentPiece.y ||
                    obje.x == currentPiece.x - 1 && obje.y == currentPiece.y ||
                    obje.x == currentPiece.x + 1 && obje.y == currentPiece.y - 1 ||
                    obje.x == currentPiece.x - 1 && obje.y == currentPiece.y + 1 ||
                    obje.x == currentPiece.x && obje.y == currentPiece.y + 1 ||
                    obje.x == currentPiece.x && obje.y == currentPiece.y - 1) && !obje.isTaken && !doubleJump) {
                currentPlayer.sendMessage("COLOR;" + obje.x + ";" + obje.y + ";" + currentPiece.R + ";" + currentPiece.G + ";" + currentPiece.B);
                obje.isAvailable = true;
            }
            if (!doubleJump || (doubleJump && currentPiece.x == x2 && currentPiece.y == y2)) {
                if (obje.x == currentPiece.x + 1 && obje.y == currentPiece.y && obje.isTaken) {
                    isTakenTileChecking(currentPiece.x + 2, currentPiece.y, currentPiece.R, currentPiece.G, currentPiece.B, currentPlayer, currentPiece);
                }
                if (obje.x == currentPiece.x - 1 && obje.y == currentPiece.y && obje.isTaken) {
                    isTakenTileChecking(currentPiece.x - 2, currentPiece.y, currentPiece.R, currentPiece.G, currentPiece.B, currentPlayer, currentPiece);
                }
                if (obje.x == currentPiece.x + 1 && obje.y == currentPiece.y - 1 && obje.isTaken) {
                    isTakenTileChecking(currentPiece.x + 2, currentPiece.y - 2, currentPiece.R, currentPiece.G, currentPiece.B, currentPlayer, currentPiece);
                }
                if (obje.x == currentPiece.x - 1 && obje.y == currentPiece.y + 1 && obje.isTaken) {
                    isTakenTileChecking(currentPiece.x - 2, currentPiece.y + 2, currentPiece.R, currentPiece.G, currentPiece.B, currentPlayer, currentPiece);
                }
                if (obje.x == currentPiece.x && obje.y == currentPiece.y + 1 && obje.isTaken) {
                    isTakenTileChecking(currentPiece.x, currentPiece.y + 2, currentPiece.R, currentPiece.G, currentPiece.B, currentPlayer, currentPiece);
                }
                if (obje.x == currentPiece.x && obje.y == currentPiece.y - 1 && obje.isTaken) {
                    isTakenTileChecking(currentPiece.x, currentPiece.y - 2, currentPiece.R, currentPiece.G, currentPiece.B, currentPlayer, currentPiece);
                }
            }
            for (Piece bas1 : base) {
                if (currentPiece.x == bas1.x && currentPiece.y == bas1.y) {
                    for (Tile obj : tiles) {
                        if (obj.isAvailable) {
                            obj.isAvailable = false;
                            currentPlayer.sendMessage("COLOR;" + obj.x + ";" + obj.y + ";" + 0 + ";" + 0 + ";" + 0);
                            for (Piece bas2 : base) {
                                if (obj.x == bas2.x && obj.y == bas2.y) {
                                    obj.isAvailable = true;
                                    currentPlayer.sendMessage("COLOR;" + obj.x + ";" + obj.y + ";" + currentPiece.R + ";" + currentPiece.G + ";" + currentPiece.B);
                                }
                            }
                        }
                    }
                }
            }
        }
        currentPiece.isActive = true;
    }

    public void nextTurn(String mess, Player currentPlayer) {
        String[] values = mess.split(";");
        int currentPlayerNumber = Integer.parseInt(values[1]);
        ArrayList<Piece> pieces = getCurrentPlayerPieces(currentPlayerNumber);
        nextPlayer = true;
        turnEnd(currentPlayer, pieces);
    }

    private ArrayList<Piece> getCurrentPlayerPieces(int currentPlayerNumber) {
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

    private ArrayList<Piece> getCurrentPlayerEndBase(int currentPlayerNumber) {
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

    private ArrayList<Piece> getCurrentPlayerBlockedTiles(int currentPlayerNumber) {
        switch (currentPlayerNumber) {
            case 1 -> {
                return redBlocked;
            }
            case 2 -> {
                return yellowBlocked;
            }
            case 3 -> {
                return blueBlocked;
            }
            case 4 -> {
                return purpleBlocked;
            }
            case 5 -> {
                return greenBlocked;
            }
            case 6 -> {
                return cyanBlocked;
            }
        }
        return null;
    }

    private void turnEnd(Player currentPlayer, ArrayList<Piece> pieces) {
        for (Piece pie : pieces) {
            if (pie.isActive) {
                pie.isActive = false;
            }
        }

        for (Tile obje : tiles) {
            obje.isAvailable = false;
            obje.isTaken = false;
            currentPlayer.sendMessage("COLOR;" + obje.x + ";" + obje.y + ";" + 0 + ";" + 0 + ";" + 0);
        }
        checkTakenTiles();
        if (nextPlayer) {
            doubleJump = false;

            do {
                if (currentPlayerId==playerList.length){
                    currentPlayerId=1;
                }
                else if (currentPlayerId < playerList.length){
                    currentPlayerId++;
                }
                else{
                    System.out.println("PROBLEM");
                }
            } while(IntStream.of(winnersId).anyMatch(x -> x == currentPlayerId));
            for (Player player : players) {
                player.sendMessage("PLAYERCOLOR;"+playerList[currentPlayerId-1]);
               if(player.getId()==currentPlayerId){
                   player.sendMessage("NEXT;true");
               }
               else{
                   player.sendMessage("NEXT;false");
               }
            }

        }
        nextPlayer = false;
    }

    private void isTakenTileChecking(int a1, int a2, int c1, int c2, int c3, Player currentPlayer, Piece currentPiece) {
        for (Tile objec : tiles) {
            if (objec.x == a1 && objec.y == a2 && !objec.isTaken) {
                objec.isAvailable = true;
                currentPlayer.sendMessage("COLOR;" + objec.x + ";" + objec.y + ";" + currentPiece.R + ";" + currentPiece.G + ";" + currentPiece.B);
            }
        }
    }

    private void checkTakenTiles() {
        checkIsTaken(redPieces);
        checkIsTaken(yellowPieces);
        checkIsTaken(bluePieces);
        checkIsTaken(purplePieces);
        checkIsTaken(greenPieces);
        checkIsTaken(cyanPieces);
    }

    private void checkIsTaken(ArrayList<Piece> pieces) {
        for (Piece piece : pieces) {
            for (Tile tile : tiles) {
                if (piece.x == tile.x && piece.y == tile.y) {
                    tile.isTaken = true;
                }
            }
        }
    }


    public void startGame(String com) {
        String[] values = com.split(";");
        int numberOfPlayers = Integer.parseInt(values[1]);
        prepareVirtualBoard(numberOfPlayers);
        for (Player player : players) {
            player.sendMessage(com + ";" + player.getId());
        }
    }

    public void creatingBlockedTiles(ArrayList<Piece> pieces) {
        int id = 1;
        if (pieces == redBlocked) {
            for (int i = 2 * boardSize + 1; i <= 3 * boardSize; i++) {
                pieces.add(new Piece(id, i, boardSize - 1, false, 0, 0, 0));
                id++;
            }
            for (int i = 2 * boardSize + 2; i <= 3 * boardSize; i++) {
                pieces.add(new Piece(id, i, boardSize - 2, false, 0, 0, 0));
                id++;
            }
        }
        if (pieces == yellowBlocked) {
            for (int i = boardSize; i <= 2 * boardSize - 1; i++) {
                pieces.add(new Piece(id, 3 * boardSize + 1, i, false, 0, 0, 0));
                id++;
            }
            for (int i = boardSize; i <= 2 * boardSize - 2; i++) {
                pieces.add(new Piece(id, 3 * boardSize + 2, i, false, 0, 0, 0));
                id++;
            }
        }
        if (pieces == blueBlocked) {
            int i = 2 * boardSize + 1;
            int j = 3 * boardSize;
            for (int k = 1; k <= boardSize; k++) {
                pieces.add(new Piece(id, i, j, false, 0, 0, 0));
                id++;
                i++;
                j--;
            }
            i = 2 * boardSize + 2;
            j = 3 * boardSize;
            for (int k = 1; k < boardSize; k++) {
                pieces.add(new Piece(id, i, j, false, 0, 0, 0));
                id++;
                i++;
                j--;
            }
        }
        if (pieces == purpleBlocked) {
            for (int i = boardSize; i <= 2 * boardSize - 1; i++) {
                pieces.add(new Piece(id, i, 3 * boardSize + 1, false, 0, 0, 0));
                id++;
            }
            for (int i = boardSize; i <= 2 * boardSize - 2; i++) {
                pieces.add(new Piece(id, i, 3 * boardSize + 2, false, 0, 0, 0));
                id++;
            }
        }

        if (pieces == greenBlocked) {
            for (int i = 2 * boardSize + 1; i <= 3 * boardSize; i++) {
                pieces.add(new Piece(id, boardSize - 1, i, false, 0, 0, 0));
                id++;
            }
            for (int i = 2 * boardSize + 2; i <= 3 * boardSize; i++) {
                pieces.add(new Piece(id, boardSize - 2, i, false, 0, 0, 0));
                id++;
            }
        }

        if (pieces == cyanBlocked) {
            int i = boardSize;
            int j = 2 * boardSize - 1;
            for (int k = 1; k <= boardSize; k++) {
                pieces.add(new Piece(id, i, j, false, 0, 0, 0));
                id++;
                i++;
                j--;
            }
            i = boardSize;
            j = 2 * boardSize - 2;
            for (int k = 1; k < boardSize; k++) {
                pieces.add(new Piece(id, i, j, false, 0, 0, 0));
                id++;
                i++;
                j--;
            }
        }

    }

    private void prepareVirtualBoard(int numberOfPlayers) {
        createBoard();

        for (int i = 1; i <= boardSize; i++) {
            baseSize += i;
        }

        playerList = new int[numberOfPlayers];
        createPlayerList(numberOfPlayers);
        addPieces();
        for (int i = 0; i < playerList.length; i++) {
            switch (playerList[i]) {
                case 1:
                    creatingBase(2, 2 * boardSize + 1, 3 * boardSize, boardSize - 1, boardSize - 1, redBase);
                    creatingBlockedTiles(redBlocked);
                    break;
                case 2:
                    creatingBase(1, 3 * boardSize + 1, 4 * boardSize, boardSize, 2 * boardSize - 1, yellowBase);
                    creatingBlockedTiles(yellowBlocked);
                    break;
                case 3:
                    creatingBase(2, 2 * boardSize + 1, 3 * boardSize, 3 * boardSize, 3 * boardSize, blueBase);
                    creatingBlockedTiles(blueBlocked);
                    break;
                case 4:
                    creatingBase(1, boardSize, 2 * boardSize - 1, 3 * boardSize + 1, 4 * boardSize, purpleBase);
                    creatingBlockedTiles(purpleBlocked);
                    break;
                case 5:
                    creatingBase(2, 0, boardSize - 1, 3 * boardSize, 3 * boardSize, greenBase);
                    creatingBlockedTiles(greenBlocked);
                    break;
                case 6:
                    creatingBase(1, boardSize, 2 * boardSize - 1, boardSize, 2 * boardSize - 1, cyanBase);
                    creatingBlockedTiles(cyanBlocked);
                    break;
            }
        }
        checkTakenTiles();
        winnersId = new int[numberOfPlayers];
    }


    private void creatingBase(int t, int j_1, int j_2, int k_1, int subs, ArrayList<Piece> pieces) {
        int id = 1;
        int i = subs;
        int type = t;
        for (int j = j_1; j <= j_2; j++) {
            if (type == 1) {
                for (int k = k_1; k <= i; k++) {
                    pieces.add(new Piece(id, j, k, false, 0, 0, 0));
                    id++;
                }
                i--;
            } else if (type == 2) {
                for (int k = i; k <= k_1; k++) {
                    pieces.add(new Piece(id, j, k, false, 0, 0, 0));
                    id++;
                }
                i--;
            }
        }
    }
}