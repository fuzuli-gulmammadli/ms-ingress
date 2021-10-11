package ingress.fuzuli.gulmamadli.microservice.lesson4;

public class TicTacToe {
    private final Character[][] gameState = {
            {'\0', '\0', '\0'},
            {'\0', '\0', '\0'},
            {'\0', '\0', '\0'}
    };

    private Character currentPlayer = '\0';
    public String gameResult = "No winner";

    public void play(int x, int y) {
        setCurrentPlayer();
        checkAxis(x, 'X');
        checkAxis(y, 'Y');
        checkBox(x, y);
        writeMove(x, y);
        checkWin(x, y);
    }

    public void checkAxis(int axis, Character axisName) {
        if (axis < 1 || axis > 3) {
            throw new RuntimeException(axisName + " is out of the Board!");
        }
    }

    public void setCurrentPlayer(){
        if(currentPlayer == '\0' || currentPlayer == 'O'){
            currentPlayer = 'X';
        }else{
            currentPlayer = 'O';
        }
    }

    public void checkBox(int x, int y){
        if(gameState[x-1][y-1] != '\0'){
            throw new RuntimeException("Box already taken!");
        }
    }

    public void writeMove(int x, int y){
        gameState[x-1][y-1] = currentPlayer;
    }

    public Character getCurrentPlayer(){
        return currentPlayer;
    }

    private void checkWin(int x, int y){
        if(gameResult.equals("No winner")) {
            gameResult = checkHorizontalWin(x);
        }
        if(gameResult.equals("No winner")) {
            gameResult = checkVerticalWin(y);
        }
        if(gameResult.equals("No winner")) {
            gameResult = checkDiagonalWin(x, y);
        }
        if(gameResult.equals("No winner")) {
            gameResult = checkDraw();
        }
    }

    private String checkHorizontalWin(int x){
        Character[] horizontal = gameState[x-1];
        String result = currentPlayer + " has won!";
        for(int i=0; i<horizontal.length; i++){
            if(horizontal[i] != currentPlayer){
                result = "No winner";
                break;
            }
        }

        return result;
    }

    private String checkVerticalWin(int y){
        String result = currentPlayer + " has won!";
        for(int i=0; i<gameState.length; i++){
            Character symbol = gameState[i][y-1];
            if(symbol != currentPlayer){
                result = "No winner";
                break;
            }
        }

        return result;
    }

    private String checkDiagonalWin(int xx, int yy){
        String result = "No winner";

        if((xx==1 && yy==1) || (xx==2 && yy==2) || (xx==3 && yy==3)) {
            result = currentPlayer + " has won!";
            for (int x = 0; x < gameState.length; x++) {
                if (gameState[x][x] != currentPlayer) {
                    result = "No winner";
                    break;
                }
            }
        }else if((xx==1 && yy==3) || (xx==2 && yy==2) || (xx==3 && yy==1)) {
            result = currentPlayer + " has won!";
            for (int x = 0; x < gameState.length; x++) {
                if (gameState[x][gameState.length - x - 1] != currentPlayer) {
                    result = "No winner";
                    break;
                }
            }
        }
        return result;
    }

    private String checkDraw(){
        String result = "Draw!";
        for(int x=0; x<gameState.length; x++){
            for(int y=0; y<gameState[x].length; y++){
                if(gameState[x][y] == '\0'){
                    result = "No winner";
                    break;
                }
            }
        }
        return result;
    }
}