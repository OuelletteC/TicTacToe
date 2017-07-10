
public class Board {

	char[][] board;
	
	public Board() {
		board = new char[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = '-';
			}
		}
	}
	
	public boolean move(int selection, char c) {
		if (selection < 1 || selection > 9) return false;
		int x = (selection-1) / 3;
		int y = (selection-1) % 3;
		if (board[x][y] != 'X' && board[x][y] != 'O') {
			board[x][y] = c;
			return true;
		}
		return false;
	}
	
	public boolean isWin() {
		
		for (int i = 0; i < 3; i++) {
			if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return true;
			if (board[0][i] != '-' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return true;
		}
		if (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return true;
		if (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return true;
		
		return false;
	}
	
	
	public void printBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
		
	}
	
}
