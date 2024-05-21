public class TicTacToe {
    static char player = 'X', opponent = 'O';

    static class Move {
        int row, col;
    }

    static boolean isMovesLeft(char board[][]) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_')
                    return true;
        return false;
    }

    static int evaluate(char b[][]) {
        // Verifica linhas
        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                if (b[row][0] == player)
                    return +10;
                else if (b[row][0] == opponent)
                    return -10;
            }
        }

        // Verifica colunas
        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
                if (b[0][col] == player)
                    return +10;
                else if (b[0][col] == opponent)
                    return -10;
            }
        }

        // Verifica diagonais
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == player)
                return +10;
            else if (b[0][0] == opponent)
                return -10;
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == player)
                return +10;
            else if (b[0][2] == opponent)
                return -10;
        }

        // Nenhuma vitória
        return 0;
    }

    static int minimax(char board[][], int depth, boolean isMax) {
        int score = evaluate(board);

        // Se o jogador atual ganhou
        if (score == 10)
            return score - depth;

        // Se o oponente ganhou
        if (score == -10)
            return score + depth;

        // Se não há mais movimentos e ninguém ganhou
        if (!isMovesLeft(board))
            return 0;

        if (isMax) {
            int best = -1000;

            // Itera sobre todas as células
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Verifica se a célula está vazia
                    if (board[i][j] == '_') {
                        board[i][j] = player;
                        best = Math.max(best, minimax(board, depth + 1, !isMax));
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        } else {
            int best = 1000;

            // Itera sobre todas as células
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Verifica se a célula está vazia
                    if (board[i][j] == '_') {
                        board[i][j] = opponent;
                        best = Math.min(best, minimax(board, depth + 1, !isMax));
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    static Move findBestMove(char board[][]) {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        // Itera sobre todas as células, chama minimax para cada célula vazia
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Verifica se a célula está vazia
                if (board[i][j] == '_') {
                    board[i][j] = player;
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = '_';

                    // Atualiza o melhor valor e a melhor jogada
                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    public static void main(String[] args) {
        char board[][] = {
            { 'X', 'O', 'X' },
            { 'O', 'O', 'X' },
            { '_', '_', '_' }
        };

        Move bestMove = findBestMove(board);
        System.out.println("A melhor jogada é:");
        System.out.println("Linha: " + bestMove.row + ", Coluna: " + bestMove.col);
    }
}
