package jalog_demo.sudoku_solver;
/* ToDo
  * Thinking - message does not show
  * If there is no room for solve-button
*/
import android.content.Context;
import android.content.res.AssetManager;
import io.github.JalogTeam.jalog.Jalog;
import io.github.JalogTeam.jalog.Pro_Term;

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

  class sudoku_cell {
    Button view;
    int value;
  };

  static View[] horlines = new View[10];
  static View[] verlines = new View[10];


  static sudoku_cell[][] sudoku_field;
  static TextView wait_message;
  static float cell_translation_one;
  static AssetManager am = null;

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
/*
      if (cell.value > 9) {
        cell.value = 0;
        disp_string = " ";
      } else {
        symbol = (char)('0' + cell.value);
        disp_string = "" + symbol;
      }
 */
//        push_button11.setText(disp_string);
//      x.setText(disp_string);
      x.setText(convert_number(cell.value));
    }
  };

  static View.OnClickListener solve_listener = new View.OnClickListener() {
    public void onClick(View v) {
/*
      if (main_viewgroup != null) {
        width = main_viewgroup.getWidth();
        height = main_viewgroup.getHeight();
      }

 */
      if (grid_area != null) {
        width = grid_area.getWidth();
        height = grid_area.getHeight();
      }
      // your handler code here
      /*
      char symbol;
      String disp_string;
      Button x = (Button)v;
      sudoku_cell cell = (sudoku_cell)x.getTag();

      cell.value = cell.value + 1;
      if (cell.value > 9) {
        cell.value = 0;
        disp_string = "_";
      } else {
        symbol = (char)('0' + cell.value);
        disp_string = "" + symbol;
      }
//        push_button11.setText(disp_string);
      x.setText(disp_string);
      */
      wait_message.setVisibility(View.VISIBLE);

      new CountDownTimer(1000, 1000) {

        public void onTick(long millisUntilFinished) {
          // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
        }

        public void onFinish() {
          Jalog myJalog = new Jalog(am);

//          Context mContext = getApplicationContext();
//          InputStream is = mContext.getAssets().open("sudoku_solver_component.pro");


          myJalog.consult_file("res:sudoku_solver_component.pro");

          solve_sudoku(myJalog);

          myJalog.dispose();

          wait_message.setVisibility(View.INVISIBLE);
        }
      }.start();

    }
  };

  static void solve_sudoku(Jalog myJalog) {

    int i, j, value;
    Jalog.Term line[] = new Jalog.Term[9];
    Jalog.Term matrix[] = new Jalog.Term[9];
    Jalog.Term board;
    Jalog.Term element;

//  Preparation of the problem

    for (i = 0; i < 9; i++) {
      for (j = 0; j < 9; j++) {
//        value = sudoku_problem[i][j];
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
//              sudoku_solution[i][j] = (int)element.getIntegerValue();
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
    super.onCreate(savedInstanceState);
    sudoku_field = new sudoku_cell[9][];
//    sudoku_field = new sudoku_cell[9][9];
    setContentView(R.layout.activity_main);
    am = getResources().getAssets();


    int button_style = R.style.Widget_AppCompat_Button;
//    Drawable buttonBackground = push_button11.getBackground();
    int buttonBackground = 0;
    int button_padding = 0;
    int button_color = 0xFFFFFF;

    main_viewgroup = ((ViewGroup)findViewById(R.id.main_layout));
    grid_area = ((TextView)findViewById(R.id.grid_area));

    for (i = 0; i < 9; i++) {
      sudoku_field[i] = new sudoku_cell[9];
      for (j = 0; j < 9; j++) {
        push_button = new Button(this);

        sudoku_field[i][j] = new sudoku_cell();

        push_button.setPadding(button_padding, 0, button_padding, 0);
        push_button.setBackgroundColor(buttonBackground);
        main_viewgroup.addView(push_button);
        sudoku_field[i][j].view = push_button;
        sudoku_field[i][j].value = 0;
        push_button.setText("-");
        push_button.setTag(sudoku_field[i][j]);

        push_button.setOnClickListener(click_listener);

      }
    }
    for (i = 0; i < 9; i++) {
      line = new View(this);

      /*
              android:id="@+id/bottom_line"
        android:layout_width="362dp"
        android:layout_height="2dp"
        android:layout_marginTop="360dp"
        android:background="@android:color/black"
        app:layout_constraintTop_toTopOf="parent" />

horlines
       */
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
    View line;
    int button_width, button_height;
    wait_message = (TextView) findViewById(R.id.wait_message);
    wait_message.setVisibility(View.INVISIBLE);
    int a = grid_area.getMeasuredHeight();
    int b = grid_area.getMeasuredWidth();

    int button_dim = (a < b ? a : b) / 9 - 1;
    button_width = button_dim;
    button_height = button_dim;
    float button_textsize = button_dim / 4;
    float cell_translation_one = button_dim;

    for (i = 0; i < 9; i++) {
      for (j = 0; j < 9; j++) {
        push_button = sudoku_field[i][j].view;
        push_button.setLayoutParams(new ConstraintLayout.LayoutParams(button_width, button_height));
        push_button.setTranslationX(j * cell_translation_one);
        push_button.setTranslationY(i * cell_translation_one);
        push_button.setTextSize(20);
        push_button.setText(" ");
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

    // Horizontal or vertical layout?
    push_button = (Button) findViewById(R.id.solve);

    if (a > b) {
      // Vertical layout
      push_button.setTranslationY(9 * cell_translation_one + 0);
    } else {
      // Horizontal layout
      push_button.setTranslationX(9 * cell_translation_one + 0);
/*
      ConstraintSet set = new ConstraintSet();
      set.connect(main_viewgroup.getId(), ConstraintSet.LEFT,
          push_button.getId(), ConstraintSet.LEFT, (int)(9 * cell_translation_one) + 20);
      set.applyTo((ConstraintLayout)main_viewgroup);
*/
    }

  }
}

