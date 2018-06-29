package ru.shitsticks.accounthelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPlanSchetov;
    Button btnCalculators;
    Button btnCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlanSchetov = (Button) findViewById(R.id.btn_plan_classic);
        btnPlanSchetov.setOnClickListener(this);
        btnCalculators = (Button) findViewById(R.id.btn_calculators);
        btnCalculators.setOnClickListener(this);
        btnCalendar = (Button) findViewById(R.id.btn_calendar);
        btnCalendar.setOnClickListener(this);

    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.btn_plan_classic:
                Intent planIntent = new Intent(this, PlanClassicActivity.class);
                startActivity(planIntent);
                break;
            case R.id.btn_calculators:
                Intent calculatorsIntent = new Intent(this, CalculatorsActivity.class);
                startActivity(calculatorsIntent);
                break;
             default:
                 break;
        }

    }
}
