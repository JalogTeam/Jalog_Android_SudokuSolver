![alt text](https://github.com/JalogTeam/Jalog_Android_SudokuSolver/raw/master/doc/SudokuSolver_screenshot.jpg "Screenshot")
# Jalog_Android_SudokuSolver
**Application (using Jalog logic) to solve Sudoku puzzles**

1. Create Android Studio project
  * New Project
  * Empty Activity
    - Name: `SudokuSolver`
    - Package name: `jalog_demo.sudoku_solver` 
         **NOTE**: Package name must be exactly right.
    - Save location: Choose a good location.
    - Language: `Java`

2. Install SudokuSolver package
  * From https://github.com/JalogTeam/Jalog_Android_SudokuSolver > Code > Download ZIP
  * Unzip `Jalog_Android_SudokuSolver-master.zip`
  * Copy unzipped directory `app` on top of `app` in Android Studio project
    - confirm merging, if asked

3. Install Jalog
  * From https://github.com/JalogTeam/Jalog > Code > Download ZIP
  * Unzip `Jalog-master.zip` 
  * Copy directory `io` from unzipped `Jalog-master/src/` to `app/src/main/java`
    - confirm merging, if asked

4 Build SudokuSolver
  * In Android Studio 
    - select Nexus S API 28 (tested with this)
    - Main menu > Build > Make Project

5 Run   
  * Run 
    - Should show text "SudokuSolver" near to the top of the display. The
      Sudoku grid should be displayed under the name.
  * Set numbers by tapping squares.
  * Hit Solve button
    - Should show "Thinking" first
    - Should show solution next, if soluble
    
