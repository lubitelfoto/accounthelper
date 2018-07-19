package ru.shitsticks.accounthelper;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class CalculatorsActivity extends AppCompatActivity implements View.OnClickListener {

    CardView btnNDSCalc;
    CardView btnVacationCalc;
    CardView btnPenaltyCalc;
    TextView textNDSCalcHead;
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
        textNDSCalcHead = (TextView) findViewById(R.id.nds_calc_head);
}

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_nds_calc:
                Intent ndsCalcIntent = new Intent(this,NDSCalcActivity.class);
                ActivityOptionsCompat optionsNDSCalc = ActivityOptionsCompat.makeSceneTransitionAnimation(CalculatorsActivity.this, textNDSCalcHead, ViewCompat.getTransitionName(textNDSCalcHead));
                startActivity(ndsCalcIntent, optionsNDSCalc.toBundle());
                break;
            case R.id.btn_vacation_calc:
                break;
            case R.id.btn_penalty_calc:
                break;
            default:
                break;
        }

    }

}