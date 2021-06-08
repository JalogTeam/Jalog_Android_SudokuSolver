package jalog_demo.sudoku_solver;
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

public class MainActivity extends AppCompatActivity {
  class sudoku_cell {
    TextView view;
    int value;
  };
  static sudoku_cell[][] sudoku_field;
  static TextView wait_message;
  static float cell_translation_one;
  static AssetManager am = null;
  static String convert_number(int v) {
    String disp_string;

    if (v == 0) {
      disp_string = " ";
    } else {
      disp_string = "" + (char)('0' + v);
    }
    return disp_string;
  }
  static View.OnClickListener click_listener = new View.OnClickListener() {
    public void onClick(View v) {
      // your handler code here
      char symbol;
      String disp_string;
      Button x = (Button)v;
      sudoku_cell cell = (sudoku_cell)x.getTag();

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
        if(value == 0) {
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
      if (myJalog.call("sudoku", board))
      { // Getting the solution
        matrix = board.getElements();
        for (i = 0; i < 9; i++) {
          line = matrix[i].getElements();
          for (j = 0; j < 9; j++) {
            element = line[j];
            if (element.getType() == Jalog.INTEGER) {
//              sudoku_solution[i][j] = (int)element.getIntegerValue();
              sudoku_field[i][j].value = (int)element.getIntegerValue();
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




  @Override
  protected void onCreate(Bundle savedInstanceState) {
    int i, j;
    int button_width, button_height;
    Button push_button;
    super.onCreate(savedInstanceState);
    sudoku_field = new sudoku_cell[9][];
//    sudoku_field = new sudoku_cell[9][9];
    setContentView(R.layout.activity_main);
    am = getResources().getAssets();
/*
    final InputStream   is;
    final AssetManager am = getResources().getAssets();
    String line;
    try {

      is = am.open("sudoku_solver_component.pro");

      Reader input =
          new InputStreamReader(is, "UTF-8");
      BufferedReader file1 = new BufferedReader(input);
      line = file1.readLine();
    } catch (Exception e) {
    }
*/


    wait_message = (TextView) findViewById(R.id.wait_message);
    wait_message.setVisibility(View.INVISIBLE);


    int button_style = R.style.Widget_AppCompat_Button;
    button_width = 80;
    button_height = 80;
//    Drawable buttonBackground = push_button11.getBackground();
    int buttonBackground = 0;
    int button_padding = 0;
    int button_color = 0xFFFFFF;
    float button_textsize = 20;
    float cell_translation_one = 80;

    ViewGroup main_viewgroup = ((ViewGroup)findViewById(R.id.main_layout));
    for (i = 0; i < 9; i++) {
      sudoku_field[i] = new sudoku_cell[9];
      for (j = 0; j < 9; j++) {
        push_button = new Button(this);

        sudoku_field[i][j] = new sudoku_cell();

        push_button.setPadding(button_padding, 0, button_padding, 0);
        push_button.setBackgroundColor(buttonBackground);
        push_button.setLayoutParams(new ViewGroup.LayoutParams(button_width, button_height));
        push_button.setTranslationX(j*cell_translation_one);
        push_button.setTranslationY(i*cell_translation_one);
        push_button.setTextSize(20);
//    push_button31.setZ(99);
        main_viewgroup.addView(push_button);
        sudoku_field[i][j].view = push_button;
        /*
        sudoku_field[i][j].value = 10*(i+1) + j + 1;
        push_button.setText("" + (i + 1) + (j + 1));
        */
        sudoku_field[i][j].value = 0;
        push_button.setText(" ");
        push_button.setTag(sudoku_field[i][j]);

        push_button.setOnClickListener(click_listener);

      }
    }

    push_button = ((Button)findViewById(R.id.solve));
    push_button.setOnClickListener(solve_listener);



  }
}

