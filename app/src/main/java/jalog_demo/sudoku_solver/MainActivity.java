package jalog_demo.sudoku_solver;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.*;


import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
  static TextView wait_message;
  static Button push_button;
  static int value;
  static char symbol;
  static String disp_string;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    wait_message = (TextView) findViewById(R.id.wait_message);
    wait_message.setVisibility(View.INVISIBLE);

    value = 4;
    symbol = (char)('0' + value);
    disp_string = "" + symbol;
    push_button = (Button) findViewById(R.id.button);
    push_button.setText(disp_string);

    push_button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        // your handler code here
        value = value + 1;
        if (value > 9) {
          value = 0;
          disp_string = "_";
        } else {
          symbol = (char)('0' + value);
          disp_string = "" + symbol;
        }
        push_button.setText(disp_string);
      }
    });

  }
}

