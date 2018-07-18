package ru.shitsticks.accounthelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class CalculatorsActivity extends AppCompatActivity implements View.OnClickListener {

    CardView btnNDSCalc;
    CardView btnVacationCalc;
    CardView btnPenaltyCalc;
    TextView textNDSCalc;
    TextView textVacationCalc;
    TextView textPenaltyCalc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculators);

        btnNDSCalc = (CardView) findViewById(R.id.btn_nds_calc);
        btnNDSCalc.setOnClickListener(this);
        btnVacationCalc = (CardView) findViewById(R.id.btn_vacation_calc);
        btnVacationCalc.setOnClickListener(this);
        btnPenaltyCalc = (CardView) findViewById(R.id.btn_penalty_calc);
        btnPenaltyCalc.setOnClickListener(this);
}

    public void onClick(View v){

    }

}