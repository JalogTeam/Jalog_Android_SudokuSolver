package jalog_demo.sudoku_solver;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
  class sudoku_cell {
    View view;
    int value;
  };
  static sudoku_cell[][] sudoku_field;
  static TextView wait_message;
  static float cell_translation_one;

  static View.OnClickListener click_listener = new View.OnClickListener() {
    public void onClick(View v) {
      // your handler code here
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

    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    int i, j;
    int button_width, button_height;
    Button push_button;
    super.onCreate(savedInstanceState);
    sudoku_field = new sudoku_cell[9][];
//    sudoku_field = new sudoku_cell[9][9];
    setContentView(R.layout.activity_main);

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
        push_button.setText("" + (i + 1) + (j + 1));
        push_button.setTextSize(20);
//    push_button31.setZ(99);
        main_viewgroup.addView(push_button);
        sudoku_field[i][j].view = push_button;
        sudoku_field[i][j].value = 10*(i+1) + j + 1;
        push_button.setTag(sudoku_field[i][j]);

        push_button.setOnClickListener(click_listener);

      }
    }

    push_button = ((Button)findViewById(R.id.solve));
    push_button.setOnClickListener(solve_listener);
//    push_button31 = new Button(this, null, button_style);
/*
        android:id="@+id/button11"
        style="@style/Widget.AppCompat.Button"
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:background="@android:color/transparent"
        android:text="11"
        android:padding="0dp"
        android:textColor="#000000"
        android:textSize="20sp"
        android:translationX="0dp" />
 */



  }
}

