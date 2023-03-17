package com.nseaf.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvNumber;
    TextView tvDetails;
    Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculator = new Calculator();
        tvNumber = findViewById(R.id.tv_number);
        tvDetails = findViewById(R.id.tv_details);
    }

    //process numbers
    public void numberClicked(View view) {
        switch (view.getId()){
            case R.id.b_0: calculator.processNumber(0); break;
            case R.id.b_1: calculator.processNumber(1); break;
            case R.id.b_2: calculator.processNumber(2); break;
            case R.id.b_3: calculator.processNumber(3); break;
            case R.id.b_4: calculator.processNumber(4); break;
            case R.id.b_5: calculator.processNumber(5); break;
            case R.id.b_6: calculator.processNumber(6); break;
            case R.id.b_7: calculator.processNumber(7); break;
            case R.id.b_8: calculator.processNumber(8); break;
            case R.id.b_9: calculator.processNumber(9); break;
        }
//        Log.e("Simul2",calculator.numberString);
        updateCalcUI();
    }

    //process operators
    public void operatorClicked(View view) {
        switch (view.getId()){
            case R.id.b_div: calculator.processOperator('÷'); break;
            case R.id.b_multiply: calculator.processOperator('x'); break;
            case R.id.b_subtract: calculator.processOperator('-'); break;
            case R.id.b_addition: calculator.processOperator('+'); break;
            case R.id.b_equal: calculator.processOperator('='); break;
        }
        updateCalcUI();
    }

    //set the detail and the number Strings in the UI
    private void updateCalcUI() {
        tvDetails.setText(calculator.detailsString);
        if (calculator.numberString.length()>12){
            tvNumber.setText(calculator.numberString.substring(0,12));
        }else{
            tvNumber.setText(calculator.numberString);
        }

    }

    //process clear button
    public void clearClicked(View view) {
        calculator.clearClicked();
        updateCalcUI();
    }

    //process M+ button
    public void memPlusClicked(View view) {
        calculator.memPlusClicked();
        updateCalcUI();
    }

    //process M- button
    public void memMinusClicked(View view) {
        calculator.memMinusClicked();
        updateCalcUI();
    }

    //process MR button
    public void memReadClicked(View view) {
        calculator.memReadClicked();
        updateCalcUI();
    }

    //process MC button
    public void memClearClicked(View view) {
        calculator.memClearClicked();
        updateCalcUI();
    }

    //process e
    public void exponentialClicked(View view) {
        calculator.exponentialClicked();
        updateCalcUI();
    }

    //process pi
    public void piClicked(View view) {
        calculator.piClicked();
        updateCalcUI();
    }

    //process %
    public void percentClicked(View view) {
        calculator.percentClicked();
        updateCalcUI();
    }

    //process decimal point
    public void radixPointClicked(View view) {
        calculator.radixPointClicked();
        updateCalcUI();
    }
}