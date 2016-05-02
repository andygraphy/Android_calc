package hcil.umd.edu.Android_calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import hcil.umd.edu.cmsc434calculator.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayoutCalculator);
        int count = gridLayout.getChildCount();
        ButtonClickHandler buttonClickHandler = new ButtonClickHandler();
        for(int i=0; i<count; i++){
            View v = gridLayout.getChildAt(i);
            if(v instanceof Button){
                v.setOnClickListener(buttonClickHandler);
            }
        }
    }

    private class ButtonClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            TextView textViewOutputScreen = (TextView)findViewById(R.id.textViewOutputScreen);

            if(v instanceof Button){
                Button buttonClicked = (Button)v;

                if(v.getId() == R.id.buttonKeyC){
                    textViewOutputScreen.setText("0");
                }
                else if(v.getId() == R.id.buttonKeyEquals){
                    try {
                        double calcResult = CalcUtils.evaluate(textViewOutputScreen.getText().toString());
                        textViewOutputScreen.setText(Double.toString(calcResult));
                    }
                    catch(Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        textViewOutputScreen.setText("0");
                    }
                }

                else if(textViewOutputScreen.getText().length() > 0 &&
                        CalcUtils.isOperator(textViewOutputScreen.getText().charAt(textViewOutputScreen.getText().length() - 1)) &&
                        CalcUtils.isOperator(buttonClicked.getText().charAt(0)))
                {
                    String errorMessage = "You cannot enter two math operators in a row. You entered " + textViewOutputScreen.getText().charAt(textViewOutputScreen.getText().length() - 1) + " and " + buttonClicked.getText() + ".";
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                }

                else {
                    if (textViewOutputScreen.getText().equals("0") &&
                            !CalcUtils.isOperator(buttonClicked.getText().charAt(0))) {
                        textViewOutputScreen.setText("");
                    }

                    textViewOutputScreen.setText(textViewOutputScreen.getText().toString() + buttonClicked.getText());
                }
            }

        }
    }
}
