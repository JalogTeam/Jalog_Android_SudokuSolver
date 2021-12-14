package jalog_demo.sudoku_solver;

import android.content.Context;
import android.content.res.AssetManager;
import io.github.JalogTeam.jalog.Jalog;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.os.CountDownTimer;


import android.os.Bundle;
import java.io.*;
import android.content.res.Configuration;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends AppCompatActivity {

  static ViewGroup main_viewgroup = null;
  static TextView grid_area = null;

  class MyResourceManager extends Jalog.ResourceManager {
    public InputStream getResourceAsStream(String fileName) throws IOException {
      return getResources().getAssets().open(fileName);
    }
  }
 
  static MyResourceManager myResourceManager;
      
  static class sudoku_cell {
    Button view;
    int value;
  };

  static View[] horlines = new View[10];
  static View[] verlines = new View[10];


  static sudoku_cell[][] sudoku_field;
  static {
    int i, j;
    sudoku_field = new sudoku_cell[9][];
    for (i = 0; i < 9; i++) {
      sudoku_field[i] = new sudoku_cell[9];
      for (j = 0; j < 9; j++) {
        sudoku_field[i][j] = new sudoku_cell();
        sudoku_field[i][j].view = null;
        sudoku_field[i][j].value = 0;
      }
    }
  }



  static TextView wait_message;
  static float cell_translation_one;
//  static AssetManager am = null;

  static String convert_number(int v) {
    String disp_string;

    if (v == 0) {
      disp_string = " ";
    } else {
      disp_string = "" + (char) ('0' + v);
    }
    return disp_string;
  }

  static View.OnClickListener click_listener = new View.OnClickListener() {
    public void onClick(View v) {
      // your handler code here
      char symbol;
      String disp_string;
      Button x = (Button) v;
      sudoku_cell cell = (sudoku_cell) x.getTag();

      cell.value = (cell.value + 1) % 10;
      x.setText(convert_number(cell.value));
    }
  };

  static View.OnClickListener solve_listener = new View.OnClickListener() {
    public void onClick(View v) {
      if (grid_area != null) {
        width = grid_area.getWidth();
        height = grid_area.getHeight();
      }

      wait_message.setVisibility(View.VISIBLE);

      new CountDownTimer(1000, 1000) {

        public void onTick(long millisUntilFinished) {
          // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
        }

        public void onFinish() {
          solve_sudoku();
          wait_message.setVisibility(View.INVISIBLE);
        }
        
      }.start();

    }
  };

  static void solve_sudoku() {

    int i, j, value;
    Jalog.Term line[] = new Jalog.Term[9];
    Jalog.Term matrix[] = new Jalog.Term[9];
    Jalog.Term board;
    Jalog.Term element;
    
    Jalog myJalog = new Jalog() ;
    myJalog.setResourceManager(myResourceManager);
    myJalog.consult_file("res:sudoku_solver_component.pro");

//  Preparation of the problem

    for (i = 0; i < 9; i++) {
      for (j = 0; j < 9; j++) {
        value = sudoku_field[i][j].value;
        if (value == 0) {
          line[j] = Jalog.open();
        } else {
          line[j] = Jalog.integer(value);
        }
      }
      matrix[i] = Jalog.list(line);
    }
    board = Jalog.list(matrix);

//  Finding the solution

    try {
      if (myJalog.call("sudoku", board)) { // Getting the solution
        matrix = board.getElements();
        for (i = 0; i < 9; i++) {
          line = matrix[i].getElements();
          for (j = 0; j < 9; j++) {
            element = line[j];
            if (element.getType() == Jalog.INTEGER) {
              sudoku_field[i][j].value = (int) element.getIntegerValue();
              sudoku_field[i][j].view.setText(convert_number(sudoku_field[i][j].value));

            }
          }
        }
      } else { // fail: Report solution not found
        System.out.println("x");
      }
    } catch (Jalog.Exit e) { // Report exception
      System.out.println("x");
    }
    myJalog.dispose();
  }

  static int width;
  static int height;

  @Override
  public void onPostResume() {
    super.onPostResume();

    new CountDownTimer(1000, 1000) {

      public void onTick(long millisUntilFinished) {
        // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
      }

      public void onFinish() {
        setLayout();

      }
    }.start();

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    int i, j;
    Button push_button;
    View line;
    sudoku_cell cell;
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    int button_style = R.style.Widget_AppCompat_Button;
    int buttonBackground = 0;
    int button_padding = 0;
    int button_color = 0xFFFFFF;

    myResourceManager = new MyResourceManager();

    main_viewgroup = ((ViewGroup)findViewById(R.id.main_layout));
    grid_area = ((TextView)findViewById(R.id.grid_area));

    for (i = 0; i < 9; i++) {
      for (j = 0; j < 9; j++) {
        push_button = new Button(this);
        cell = sudoku_field[i][j];
        push_button.setPadding(button_padding, 0, button_padding, 0);
        push_button.setBackgroundColor(buttonBackground);
        main_viewgroup.addView(push_button);
        cell.view = push_button;
        push_button.setText(convert_number(cell.value));

        push_button.setTag(cell);

        push_button.setOnClickListener(click_listener);

      }
    }
    for (i = 0; i < 9; i++) {
      line = new View(this);

      // Horizontal lines
      line.setBackgroundColor(0xff000000);
      line.setLayoutParams(new ConstraintLayout.LayoutParams(10, (i%3 != 0 ? 2 : 1)));
      main_viewgroup.addView(line);
      horlines[i] = line;

      // Vertical lines
      line = new View(this);
      line.setBackgroundColor(0xff000000);
      line.setLayoutParams(new ConstraintLayout.LayoutParams((i%3 != 0 ? 2 : 1), 10));
      main_viewgroup.addView(line);
      verlines[i] = line;
    }

    line = ((View)findViewById(R.id.bottom_line));
    line.setBackgroundColor(0xff000000);
    line.setLayoutParams(new ConstraintLayout.LayoutParams(10, 2));
    horlines[9] = line;

    line = ((View)findViewById(R.id.right_line));
    line.setBackgroundColor(0xff000000);
    line.setLayoutParams(new ConstraintLayout.LayoutParams(2, 10));
    verlines[9] = line;

    push_button = ((Button)findViewById(R.id.solve));
    push_button.setOnClickListener(solve_listener);
  }

  protected void setLayout() {
    int i, j;
    Button push_button;
    Button solve_button;
    View line;
    int button_width, button_height;
    int solve_width, solve_height;
    int a = grid_area.getMeasuredHeight();
    int b = grid_area.getMeasuredWidth();
    int button_dim;
    float button_textsize;
    float cell_translation_one;
    int grid_dim, grid_dim_h, grid_dim_w;

    wait_message = (TextView) findViewById(R.id.wait_message);
    wait_message.setVisibility(View.INVISIBLE);
    solve_button = (Button) findViewById(R.id.solve);
    solve_width = solve_button.getMeasuredWidth();
    solve_height = solve_button.getMeasuredHeight();

    grid_dim_w = b - solve_width - 40;
    grid_dim_h = a - solve_height - 40;
    if (grid_dim_w > grid_dim_h) {
      // horizontal layout
      grid_dim = grid_dim_w;
      if (grid_dim > a) grid_dim = a;
      solve_button.setTranslationY(40);
      solve_button.setTranslationX(grid_dim + 40);
    } else {
      // vertical layout
      grid_dim = grid_dim_h;
      if (grid_dim > b) grid_dim = b;
      solve_button.setTranslationY(grid_dim + 40);
      solve_button.setTranslationX(40);
    }
    button_dim = (grid_dim-4) / 9;
    button_width = button_dim;
    button_height = button_dim;
    button_textsize = button_dim / 4;
    cell_translation_one = button_dim;

    for (i = 0; i < 9; i++) {
      for (j = 0; j < 9; j++) {
        push_button = sudoku_field[i][j].view;
        push_button.setLayoutParams(new ConstraintLayout.LayoutParams(button_width, button_height));
        push_button.setTranslationX(j * cell_translation_one);
        push_button.setTranslationY(i * cell_translation_one);
        push_button.setTextSize(20);
      }
    }

    for (i = 0; i < 10; i++) {
      line = horlines[i];
      line.setLayoutParams(new ConstraintLayout.LayoutParams(9 * button_dim, (i%3 != 0 ? 2 : 4)));
      line.setTranslationY(i * cell_translation_one);

      line = verlines[i];
      line.setLayoutParams(new ConstraintLayout.LayoutParams((i%3 != 0 ? 2 : 4), 9 * button_dim));
      line.setTranslationX(i * cell_translation_one);

    }
  }
}

