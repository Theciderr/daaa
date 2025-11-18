// UI Controller
class SudokuUI {
    constructor() {
        this.solver = new SudokuSolver();
        this.grid = document.getElementById('sudoku-grid');
        this.cells = [];
        this.givenCells = new Set();
        
        this.initGrid();
        this.initButtons();
    }

    initGrid() {
        for (let i = 0; i < 81; i++) {
            const cell = document.createElement('div');
            cell.className = 'cell';
            
            const input = document.createElement('input');
            input.type = 'text';
            input.maxLength = 1;
            input.addEventListener('input', (e) => this.handleInput(e, i));
            input.addEventListener('keydown', (e) => this.handleKeydown(e, i));
            
            cell.appendChild(input);
            this.grid.appendChild(cell);
            this.cells.push(input);
        }
    }

    initButtons() {
        document.getElementById('solveBtn').addEventListener('click', () => this.solve());
        document.getElementById('clearBtn').addEventListener('click', () => this.clear());
        document.getElementById('exampleBtn').addEventListener('click', () => this.loadExample());
    }

    handleInput(e, index) {
        const value = e.target.value;
        if (value && !/^[1-9]$/.test(value)) {
            e.target.value = '';
        }
    }

    handleKeydown(e, index) {
        const row = Math.floor(index / 9);
        const col = index % 9;

        let newIndex = index;

        switch(e.key) {
            case 'ArrowUp':
                newIndex = row > 0 ? (row - 1) * 9 + col : index;
                e.preventDefault();
                break;
            case 'ArrowDown':
                newIndex = row < 8 ? (row + 1) * 9 + col : index;
                e.preventDefault();
                break;
            case 'ArrowLeft':
                newIndex = col > 0 ? row * 9 + (col - 1) : index;
                e.preventDefault();
                break;
            case 'ArrowRight':
                newIndex = col < 8 ? row * 9 + (col + 1) : index;
                e.preventDefault();
                break;
        }

        if (newIndex !== index) {
            this.cells[newIndex].focus();
        }
    }

    getBoardFromUI() {
        const board = [];
        for (let i = 0; i < 9; i++) {
            board[i] = [];
            for (let j = 0; j < 9; j++) {
                const value = this.cells[i * 9 + j].value;
                board[i][j] = value ? parseInt(value) : 0;
            }
        }
        return board;
    }

    setBoardToUI(board, markSolved = false) {
        for (let i = 0; i < 9; i++) {
            for (let j = 0; j < 9; j++) {
                const index = i * 9 + j;
                const value = board[i][j];
                this.cells[index].value = value || '';
                
                if (markSolved && value && !this.givenCells.has(index)) {
                    this.cells[index].parentElement.classList.add('solved');
                }
            }
        }
    }

    async solve() {
        // Mark given cells
        this.givenCells.clear();
        this.cells.forEach((cell, index) => {
            cell.parentElement.classList.remove('solved');
            if (cell.value) {
                this.givenCells.add(index);
                cell.parentElement.classList.add('given');
            }
        });

        const board = this.getBoardFromUI();

        // Validate input
        if (!this.solver.isValidBoard(board)) {
            this.showStats('Invalid puzzle! Check for conflicts.');
            return;
        }

        this.showStats('Solving...');
        
        // Small delay for UI update
        await new Promise(resolve => setTimeout(resolve, 100));

        const startTime = performance.now();
        const solved = this.solver.solve(board);
        const endTime = performance.now();

        if (solved) {
            this.setBoardToUI(board, true);
            const stats = this.solver.getStats();
            this.showStats(
                `Solved in ${(endTime - startTime).toFixed(2)}ms | ` +
                `Steps: ${stats.steps} | Backtracks: ${stats.backtracks}`
            );
        } else {
            this.showStats('No solution exists for this puzzle.');
        }
    }

    clear() {
        this.cells.forEach(cell => {
            cell.value = '';
            cell.parentElement.classList.remove('given', 'solved');
        });
        this.givenCells.clear();
        this.showStats('');
    }

    loadExample() {
        // Medium difficulty puzzle
        const example = [
            [5, 3, 0, 0, 7, 0, 0, 0, 0],
            [6, 0, 0, 1, 9, 5, 0, 0, 0],
            [0, 9, 8, 0, 0, 0, 0, 6, 0],
            [8, 0, 0, 0, 6, 0, 0, 0, 3],
            [4, 0, 0, 8, 0, 3, 0, 0, 1],
            [7, 0, 0, 0, 2, 0, 0, 0, 6],
            [0, 6, 0, 0, 0, 0, 2, 8, 0],
            [0, 0, 0, 4, 1, 9, 0, 0, 5],
            [0, 0, 0, 0, 8, 0, 0, 7, 9]
        ];

        this.clear();
        this.setBoardToUI(example);
        this.showStats('Example puzzle loaded. Click Solve!');
    }

    showStats(message) {
        document.getElementById('stats').textContent = message;
    }
}

// Initialize the app
document.addEventListener('DOMContentLoaded', () => {
    new SudokuUI();
});
