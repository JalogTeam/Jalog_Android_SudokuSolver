![alt text](https://github.com/JalogTeam/Jalog_Android_SudokuSolver/raw/master/doc/SudokuSolver_screenshot.jpg "Screenshot")
# Jalog_Android_demo
**Application (using Jalog logic) to solve Sudoku puzzles**
1. Create Android Studio project
  * New Project
  * Empty Activity
    - Name: `SudokuSolver`
    - Package name: `jalog_demo.sudoku_solver`

2. Install SudokuSolver package
  * From https://github.com/JalogTeam/Jalog_Android_SudokuSolver > Code > Download ZIP
  * Unzip `Jalog_Android_demo-master.zip`
  * Copy unzipped directories `app` to `app/src/main` 
    - confirm merging, if asked

3. Install Jalog
  * From https://github.com/JalogTeam/Jalog > Code > Download ZIP
  * Unzip `Jalog-master.zip` 
  * Copy directory `io` from unzipped `Jalog-master/src/` to `app/src/main/java`
    - confirm merging, if asked

4 Build SudokuSolver
  * In Android Studio 
    - select Nokia 3.1 API 28 (tested with this)

5 Run   
  * Run 
  * Set numbers by tapping squares.
  * Hit Solve button
    - Should show "Thinking" first
    - Should show solution next, if soluble
    
