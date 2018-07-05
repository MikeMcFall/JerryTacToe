public class JerryTacToe {
    public static final int HUMAN        = 0;
    public static final int COMPUTER     = 1;
    public static final int EMPTY        = 2;

    public static final int HUMAN_WIN    = 0;
    public static final int DRAW         = 1;
    public static final int UNCLEAR      = 2;
    public static final int COMPUTER_WIN = 3;

    private int [] board = new int[10];

    // Constructor
    public JerryTacToe()
    {
        clearBoard();
    }

    // Find optimal move
    public Best chooseMove(int side)
    {
        int opp;              // The other side
        Best reply;           // Opponent's best reply
        int simpleEval;       // Result of an immediate evaluation
        int bestCircle = 1;
        int value;

        if((simpleEval = positionValue()) != UNCLEAR)
            return new Best(simpleEval);

        if(side == COMPUTER)
        {
            opp = HUMAN; value = HUMAN_WIN;
        }
        else
        {
            opp = COMPUTER; value = COMPUTER_WIN;
        }

        for( int circle=1; circle<10; circle++ )
            if( squareIsEmpty(circle) )
            {
                place( side, circle );
                reply = chooseMove( opp );
                place( EMPTY, circle );

                // Update if side gets better position
                if( side == COMPUTER && reply.val >= value
                        ||  side == HUMAN && reply.val <= value )
                {
                    value = reply.val;
                    bestCircle = circle;
                }
            }

        return new Best(value, bestCircle);
    }
    // Play move, including checking legality
    public boolean playMove( int side, int circle)
    {
        if( circle< 1 || circle >= 10 || board[circle] != EMPTY )
            return false;
        board[circle] = side;
        return true;
    }

    // Simple supporting routines
    public void clearBoard()
    {
        for(int i=1; i<10; i++)
            board[i] = EMPTY;
    }

    public boolean boardIsFull()
    {
        for(int circle=1; circle<10; circle++)
            if(board[circle] == EMPTY)
                return false;
        return true;
    }

    public int makeBlock()
    {
        for(int i=1; i<10; i++)
        {
            if(squareIsEmpty(i))
            {
                place(HUMAN, i);
                if(isAWin(HUMAN))
                {
                    place(EMPTY, i);
                    return i;
                }
                place(EMPTY, i);
            }
        }
        return 0;
    }

    public int goForWin()
    {
        for(int i=1; i<10; i++)
        {
            if(squareIsEmpty(i))
            {
                place(COMPUTER, i);
                if(isAWin(COMPUTER))
                {
                    place(EMPTY, i);
                    return i;
                }
                place(EMPTY, i);
            }
        }
        return 0;
    }

    public boolean isAWin(int side)
    {
        if(board[1]==side && board[2]==side && board[3]==side)
            return true;
        if(board[4]==side && board[5]==side && board[6]==side)
            return true;
        if(board[7]==side && board[8]==side && board[9]==side)
            return true;
        if(board[1]==side && board[4]==side && board[8]==side)
            return true;
        if(board[1]==side && board[5]==side && board[9]==side)
            return true;
        if(board[2]==side && board[4]==side && board[7]==side)
            return true;
        if(board[2]==side && board[6]==side && board[9]==side)
            return true;
        if(board[3]==side && board[5]==side && board[7]==side)
            return true;
        if(board[3]==side && board[6]==side && board[8]==side)
            return true;

        return false;
    }

    // Play a move, possibly clearing a square
    private void place(int piece, int circle)
    {
        board[circle] = piece;
    }

    private boolean squareIsEmpty(int circle)
    {
        return board[circle] == EMPTY;
    }

    // Compute static value of current position (win, draw, etc.)
    private int positionValue()
    {
        return isAWin(COMPUTER) ? COMPUTER_WIN :
                isAWin(HUMAN)    ? HUMAN_WIN    :
                        boardIsFull()    ? DRAW         : UNCLEAR;
    }
}
