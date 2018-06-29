package ru.shitsticks.accounthelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.math.BigDecimal;

public class CalculatorsActivity extends AppCompatActivity {

    EditText etxSumNoNDS;
    EditText etxTaxNDS;
    EditText etxValueNDS;
    EditText etxSumNDS;
    int sourceNDS; //ресурс заполненный пользователем, что б понять какое значение вычислять при изменении ндс
    int mOnFocusNDS; //проверка фокуса на едиттекст, иначе срабатыевает onchangelistener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculators);

        etxSumNoNDS = (EditText) findViewById(R.id.NDS_calc_sum_no_tax);
        etxTaxNDS = (EditText) findViewById(R.id.NDS_calc_tax);
        etxValueNDS = (EditText) findViewById(R.id.NDS_calc_tax_value);
        etxSumNDS = (EditText) findViewById(R.id.NDS_calc_sum);

        etxTaxNDS.setText(R.string.NDS);

        final Toast toast = Toast.makeText( this, "Заполните одно из полей", Toast.LENGTH_LONG);

        View.OnFocusChangeListener mOnFocusListener = new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus){
                    if(hasFocus){
                        try {
                            switch (v.getId()){
                                case R.id.NDS_calc_sum:
                                    mOnFocusNDS = R.id.NDS_calc_sum;
                                    PercentCalc.noEmptyFocus(etxTaxNDS, v);
                                    break;
                                case R.id.NDS_calc_sum_no_tax:
                                    mOnFocusNDS = R.id.NDS_calc_sum_no_tax;
                                    PercentCalc.noEmptyFocus(etxTaxNDS, v);
                                    break;
                                case R.id.NDS_calc_tax:
                                    mOnFocusNDS = R.id.NDS_calc_tax;
                                    break;
                                case R.id.NDS_calc_tax_value:
                                    mOnFocusNDS = R.id.NDS_calc_tax_value;
                                    break;
                                default:
                                   toast.show();
                                    break;
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
        };

        etxTaxNDS.setOnFocusChangeListener(mOnFocusListener);
        etxSumNDS.setOnFocusChangeListener(mOnFocusListener);
        etxSumNoNDS.setOnFocusChangeListener(mOnFocusListener);
        etxValueNDS.setOnFocusChangeListener(mOnFocusListener);

        etxSumNoNDS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {}
                else {
                    if (mOnFocusNDS == etxSumNoNDS.getId()) {
                        sourceNDS = R.id.NDS_calc_sum_no_tax;
                        float sumNoNDS = Float.parseFloat(s.toString());
                        float nds = Float.parseFloat(etxTaxNDS.getText().toString());
                        float sumNDS = PercentCalc.getSumWithPercent(sumNoNDS, nds);
                        float valueNDS = PercentCalc.getPercentValue(sumNDS,sumNoNDS);
                        etxSumNDS.setText(String.valueOf(sumNoNDS));
                        etxValueNDS.setText(String.valueOf(valueNDS));
                    }
                }
            }
        });

        etxTaxNDS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                    if (!s.toString().isEmpty()) {
                        float nds = Float.parseFloat(s.toString());
                        switch (sourceNDS) {
                            case R.id.NDS_calc_sum:
                                float sumNDS = Float.parseFloat(etxSumNDS.getText().toString());
                                etxSumNoNDS.setText(String.valueOf(PercentCalc.getSumWithoutPercent(sumNDS, nds)));
                                break;
                            case R.id.NDS_calc_sum_no_tax:
                                float noNDS = Float.parseFloat(etxSumNoNDS.getText().toString());
                                etxSumNDS.setText(String.valueOf(PercentCalc.getSumWithPercent(noNDS, nds)));
                                break;
                            case R.id.NDS_calc_tax_value:
                                float valueNDS = Float.parseFloat(etxValueNDS.getText().toString());
                                float sumNDSfromValueNDS = PercentCalc.gerSumWithPercentFromPercentValue(valueNDS, nds);
                                float sumNoValueNDS = PercentCalc.getSumWithoutPercentFromPercentValue(valueNDS, nds);
                                etxSumNDS.setText(String.valueOf(sumNoValueNDS));
                                etxSumNoNDS.setText(String.valueOf(sumNDSfromValueNDS));
                                etxValueNDS.setText(String.valueOf(PercentCalc.getPercentValue(sumNDSfromValueNDS, sumNoValueNDS)));
                            default:
                                break;
                        }
                    }
            }
        });
        etxSumNDS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty() & mOnFocusNDS == etxSumNDS.getId()) {
                    sourceNDS = R.id.NDS_calc_sum;
                    float nds = Float.parseFloat(etxTaxNDS.getText().toString());
                    float sumNDS = Float.parseFloat(s.toString());
                    float sumNoNDS = PercentCalc.getSumWithoutPercent(sumNDS, nds);
                    float valueNDS = PercentCalc.getPercentValue(sumNDS,sumNoNDS);
                    etxSumNoNDS.setText(String.valueOf(sumNoNDS));
                    etxValueNDS.setText(String.valueOf(valueNDS));

                }
            }
        });

        etxValueNDS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.toString().isEmpty() & mOnFocusNDS == etxValueNDS.getId()){
                    sourceNDS = R.id.NDS_calc_tax_value;
                    float nds = Float.parseFloat(etxTaxNDS.getText().toString());
                    float valueNDS = Float.parseFloat(s.toString());
                    etxSumNoNDS.setText(String.valueOf(PercentCalc.getSumWithoutPercentFromPercentValue(valueNDS, nds)));
                    etxSumNDS.setText(String.valueOf(PercentCalc.gerSumWithPercentFromPercentValue(valueNDS, nds)));
                }

            }
        });
    }

    private static class PercentCalc {

        static float getPercentValue(float sumWithPercent ,float sumWithoutPecent){
            return sumWithPercent - sumWithoutPecent;
        }

        static float getSumWithPercent(float sumWithoutPercent, float percent) {
            return sumWithoutPercent + (sumWithoutPercent * percent / 100);
        }

        static float getSumWithoutPercent(float sumWithPercent, float percent) {
            return (sumWithPercent * 100) / (100 + percent);
        }

        static float getSumWithoutPercentFromPercentValue(float value, float percent){
            return (value / ( percent / 100 ) * 100);
        }

        static float gerSumWithPercentFromPercentValue(float value, float percent){
            return (value / ( percent / 100 ) * ( 100 + percent));
        }

        static void noEmptyFocus(EditText text, View textOnFocus) {
            if (text.getText().length() <= 0) {
                text.setText("0");
                textOnFocus.clearFocus();
                text.requestFocus();
            }
        }
    }
}
