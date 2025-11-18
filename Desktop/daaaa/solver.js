/**
 * Sudoku Solver using Backtracking with Constraint Propagation
 * 
 * Algorithm Analysis:
 * - Time Complexity: O(9^m) where m is the number of empty cells
 * - Space Complexity: O(m) for the recursion stack
 * - Optimization: MRV (Minimum Remaining Values) heuristic
 */

class SudokuSolver {
    constructor() {
        this.steps = 0;
        this.backtrackCount = 0;
    }

    /**
     * Main solve function
     * @param {number[][]} board - 9x9 Sudoku board
     * @returns {boolean} - True if solved, false otherwise
     */
    solve(board) {
        this.steps = 0;
        this.backtrackCount = 0;
        return this.backtrack(board);
    }

    /**
     * Backtracking algorithm with MRV heuristic
     */
    backtrack(board) {
        this.steps++;
        
        // Find the cell with minimum remaining values (MRV heuristic)
        const cell = this.findBestCell(board);
        
        if (!cell) {
            return true; // Puzzle solved
        }

        const [row, col] = cell;
        const possibleValues = this.getPossibleValues(board, row, col);

        for (const num of possibleValues) {
            if (this.isValid(board, row, col, num)) {
                board[row][col] = num;

                if (this.backtrack(board)) {
                    return true;
                }

                // Backtrack
                board[row][col] = 0;
                this.backtrackCount++;
            }
        }

        return false;
    }

    /**
     * MRV Heuristic: Find cell with minimum remaining values
     * This significantly reduces the search space
     */
    findBestCell(board) {
        let minOptions = 10;
        let bestCell = null;

        for (let row = 0; row < 9; row++) {
            for (let col = 0; col < 9; col++) {
                if (board[row][col] === 0) {
                    const options = this.getPossibleValues(board, row, col).length;
                    
                    if (options === 0) {
                        return null; // Invalid state
                    }
                    
                    if (options < minOptions) {
                        minOptions = options;
                        bestCell = [row, col];
                        
                        if (minOptions === 1) {
                            return bestCell; // Best case
                        }
                    }
                }
            }
        }

        return bestCell;
    }

    /**
     * Get all possible values for a cell using constraint propagation
     */
    getPossibleValues(board, row, col) {
        const possible = new Set([1, 2, 3, 4, 5, 6, 7, 8, 9]);

        // Remove values in same row
        for (let c = 0; c < 9; c++) {
            possible.delete(board[row][c]);
        }

        // Remove values in same column
        for (let r = 0; r < 9; r++) {
            possible.delete(board[r][col]);
        }

        // Remove values in same 3x3 box
        const boxRow = Math.floor(row / 3) * 3;
        const boxCol = Math.floor(col / 3) * 3;
        for (let r = boxRow; r < boxRow + 3; r++) {
            for (let c = boxCol; c < boxCol + 3; c++) {
                possible.delete(board[r][c]);
            }
        }

        return Array.from(possible);
    }

    /**
     * Check if placing a number is valid
     */
    isValid(board, row, col, num) {
        // Check row
        for (let c = 0; c < 9; c++) {
            if (board[row][c] === num) return false;
        }

        // Check column
        for (let r = 0; r < 9; r++) {
            if (board[r][col] === num) return false;
        }

        // Check 3x3 box
        const boxRow = Math.floor(row / 3) * 3;
        const boxCol = Math.floor(col / 3) * 3;
        for (let r = boxRow; r < boxRow + 3; r++) {
            for (let c = boxCol; c < boxCol + 3; c++) {
                if (board[r][c] === num) return false;
            }
        }

        return true;
    }

    /**
     * Validate the entire board
     */
    isValidBoard(board) {
        for (let row = 0; row < 9; row++) {
            for (let col = 0; col < 9; col++) {
                if (board[row][col] !== 0) {
                    const num = board[row][col];
                    board[row][col] = 0;
                    if (!this.isValid(board, row, col, num)) {
                        board[row][col] = num;
                        return false;
                    }
                    board[row][col] = num;
                }
            }
        }
        return true;
    }

    getStats() {
        return {
            steps: this.steps,
            backtracks: this.backtrackCount
        };
    }
}
