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
  static Button push_button11, push_button12;
  static Button push_button31;
  static int value11, value12;
  static char symbol;
  static String disp_string;
  static float cell_translation_one;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    int i, j;
    int button_width, button_height;
    Button push_button;
    super.onCreate(savedInstanceState);
    sudoku_field = new sudoku_cell[9][];
//    sudoku_field = new sudoku_cell[9][9];
    for (i = 0; i < 9; i++) {
      sudoku_field[i] = new sudoku_cell[9];
      for (j = 0; j < 9; j++) {
        sudoku_field[i][j] = new sudoku_cell();
      }
    }
    setContentView(R.layout.activity_main);

    wait_message = (TextView) findViewById(R.id.wait_message);
    wait_message.setVisibility(View.INVISIBLE);

    sudoku_field[0][0].value = 4;
    sudoku_field[0][1].value = 5;
    value11 = 4;
    value12 = 5;
    symbol = (char)('0' + value11);
    disp_string = "" + symbol;
    push_button11 = (Button) findViewById(R.id.button11);
    push_button12 = (Button) findViewById(R.id.button12);

    push_button31 = new Button(this);
    push_button11.setText(disp_string);

    int button_style = R.style.Widget_AppCompat_Button;
    button_width = push_button11.getLayoutParams().width;
    button_height = push_button11.getLayoutParams().height;
    Drawable buttonBackground = push_button11.getBackground();
    int button_padding = push_button11.getPaddingLeft();
    int button_color = 0xFFFFFF;
    float button_textsize = push_button11.getTextSize();
    float cell_translation_one = push_button12.getTranslationX();



//    push_button31 = new Button(this, null, button_style);
    push_button31 = new Button(this);
/*
    push_button31.setVisibility(View.VISIBLE);
*/
    push_button31.setPadding(button_padding, 0, button_padding, 0);
    push_button31.setBackground(buttonBackground);
    push_button31.setLayoutParams(new ViewGroup.LayoutParams(button_width, button_height));
    push_button31.setTranslationX(0*cell_translation_one);
    push_button31.setTranslationY(1*cell_translation_one);
    push_button31.setText("31");
    push_button31.setTextSize(20);
//    push_button31.setZ(99);
    ViewGroup main_viewgroup = ((ViewGroup)findViewById(R.id.main_layout));
    main_viewgroup.addView(push_button31);
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

    push_button = push_button11;
    push_button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        // your handler code here
        Button x = (Button)v;

        value11 = value11 + 1;
        if (value11 > 9) {
          value11 = 0;
          disp_string = "_";
        } else {
          symbol = (char)('0' + value11);
          disp_string = "" + symbol;
        }
//        push_button11.setText(disp_string);
        x.setText(disp_string);
      }
    });

  }
}

