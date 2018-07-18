package ru.shitsticks.accounthelper;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView btnPlanSchetov;
    CardView btnCalculators;
    CardView btnCalendar;
    TextView textCalcHead;
    TextView textPlanHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlanSchetov = (CardView) findViewById(R.id.btn_plan_classic);
        btnPlanSchetov.setOnClickListener(this);
        btnCalculators = (CardView) findViewById(R.id.btn_calculators);
        btnCalculators.setOnClickListener(this);
        btnCalendar = (CardView) findViewById(R.id.btn_calendar);
        btnCalendar.setOnClickListener(this);
        textCalcHead = (TextView) findViewById(R.id.calc_head);
        textPlanHead = (TextView) findViewById(R.id.plan_head);

    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.btn_plan_classic:
                Intent planIntent = new Intent(this, PlanClassicActivity.class);
                ActivityOptionsCompat optionsPlan = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, textPlanHead, ViewCompat.getTransitionName(textPlanHead));
                startActivity(planIntent, optionsPlan.toBundle());
                break;
            case R.id.btn_calculators:
                Intent calculatorsIntent = new Intent(this, CalculatorsActivity.class);
                ActivityOptionsCompat optionsCalculators = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, textCalcHead, ViewCompat.getTransitionName(textCalcHead));
                startActivity(calculatorsIntent, optionsCalculators.toBundle());
                break;
             default:
                 break;
        }
    }
}