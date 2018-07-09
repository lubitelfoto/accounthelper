package ru.shitsticks.accounthelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.math.BigDecimal;

public class CalculatorsActivity extends AppCompatActivity {

    public final static String TAG = "AccLogger";
    private static final BigDecimal BigDecHundred = new BigDecimal(100);
    EditText etxSumNoNDS;
    EditText etxTaxNDS;
    EditText etxValueNDS;
    EditText etxSumNDS;
    static int moneyRound = BigDecimal.ROUND_UP;
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
        final Toast toastZero = Toast.makeText(this, "Процент должен быть положительным числом отличным от ноля", Toast.LENGTH_LONG);

        View.OnFocusChangeListener mOnFocusListener = new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus){
                    if(hasFocus){
                        try {
                            switch (v.getId()){
                                case R.id.NDS_calc_sum:
                                    mOnFocusNDS = R.id.NDS_calc_sum;
                                    PercentCalc.noEmptyFocus(etxTaxNDS, v, toastZero);
                                    break;
                                case R.id.NDS_calc_sum_no_tax:
                                    mOnFocusNDS = R.id.NDS_calc_sum_no_tax;
                                    PercentCalc.noEmptyFocus(etxTaxNDS, v, toastZero);
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



                if (!s.toString().isEmpty()){
                    if (mOnFocusNDS == etxSumNoNDS.getId()) {
                        sourceNDS = R.id.NDS_calc_sum_no_tax;
                        BigDecimal sumNoNDS = new BigDecimal(s.toString());
                        sumNoNDS.setScale(2, moneyRound);
                        BigDecimal nds = new BigDecimal(etxTaxNDS.getText().toString());
                        nds.setScale(2, moneyRound);
                        BigDecimal sumNDS = PercentCalc.getSumWithPercent(sumNoNDS, nds);
                        sumNDS.setScale(2, moneyRound);
                        BigDecimal valueNDS = PercentCalc.getPercentValue(sumNDS,sumNoNDS);
                        valueNDS.setScale(2, moneyRound);
                        etxSumNDS.setText(String.valueOf(sumNDS));
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
                EditText sourceText = findViewById(sourceNDS);
                if (sourceText != null){
                    if (!s.toString().isEmpty() & !sourceText.getText().toString().isEmpty()) {
                        BigDecimal nds = new BigDecimal(s.toString());
                        switch (sourceNDS) {
                            case R.id.NDS_calc_sum:
                                BigDecimal sumNDS = new BigDecimal(etxSumNDS.getText().toString());
                                BigDecimal sumNoNDS = new BigDecimal(PercentCalc.getSumWithoutPercent(sumNDS, nds).floatValue());
                                etxSumNoNDS.setText(String.valueOf(sumNoNDS));
                                etxValueNDS.setText(String.valueOf(PercentCalc.getPercentValue(sumNDS, sumNoNDS)));
                                break;
                            case R.id.NDS_calc_sum_no_tax:
                                BigDecimal noNDS = new BigDecimal(etxSumNoNDS.getText().toString());
                                BigDecimal sumNDS1 = new BigDecimal(PercentCalc.getSumWithPercent(noNDS, nds).floatValue());
                                etxSumNDS.setText(String.valueOf(sumNDS1));
                                etxValueNDS.setText(String.valueOf(PercentCalc.getPercentValue(sumNDS1, noNDS)));
                                break;
                            case R.id.NDS_calc_tax_value:
                                BigDecimal valueNDS = new BigDecimal(etxValueNDS.getText().toString());
                                BigDecimal sumNDSfromValueNDS = PercentCalc.getSumWithPercentFromPercentValue(valueNDS, nds);
                                BigDecimal sumNoValueNDS = PercentCalc.getSumWithoutPercentFromPercentValue(valueNDS, nds);
                                etxSumNDS.setText(String.valueOf(sumNoValueNDS));
                                etxSumNoNDS.setText(String.valueOf(sumNDSfromValueNDS));
                                etxValueNDS.setText(String.valueOf(PercentCalc.getPercentValue(sumNDSfromValueNDS, sumNoValueNDS)));
                            default:
                                break;
                        }
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
                    BigDecimal nds = new BigDecimal(etxTaxNDS.getText().toString());
                    nds.setScale(2, moneyRound);
                    BigDecimal sumNDS = new BigDecimal(s.toString());
                    sumNDS.setScale(2, moneyRound);
                    BigDecimal sumNoNDS = PercentCalc.getSumWithoutPercent(sumNDS, nds);
                    sumNoNDS.setScale(2, moneyRound);
                    BigDecimal valueNDS = PercentCalc.getPercentValue(sumNDS,sumNoNDS);
                    valueNDS.setScale(2, moneyRound);
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
                    BigDecimal nds = new BigDecimal(etxTaxNDS.getText().toString());
                    nds.setScale(2, moneyRound);
                    BigDecimal valueNDS = new BigDecimal(s.toString());
                    valueNDS.setScale(2, moneyRound);
                    etxSumNoNDS.setText(String.valueOf(PercentCalc.getSumWithoutPercentFromPercentValue(valueNDS, nds)));
                    etxSumNDS.setText(String.valueOf(PercentCalc.getSumWithPercentFromPercentValue(valueNDS, nds)));
                }

            }
        });
    }

    private static class PercentCalc {

        static BigDecimal getPercentValue(BigDecimal sumWithPercent ,BigDecimal sumWithoutPercent){

            if (BuildConfig.DEBUG){
                Log.i(TAG,"getPercentValue: sumWithPercent " + sumWithPercent.toString() + ", sumWithoutPercent " + sumWithoutPercent.toString());
            }

            return sumWithPercent.subtract(sumWithoutPercent);

        }

        static BigDecimal getPercentValueFromSumWithoutPercent(BigDecimal sumWithoutPercent,BigDecimal percent){

            BigDecimal sumWP = new BigDecimal(sumWithoutPercent.floatValue());
            sumWP.setScale(2, moneyRound);

            if (BuildConfig.DEBUG){
                Log.i(TAG,"getPercentValueFromSumWithoutPercent: sumWithoutPercent " + sumWithoutPercent.toString() + ", percent " + percent.toString() + ", sumWP, " + sumWP.toString());
            }

            return sumWP.multiply(percent).divide(BigDecHundred, moneyRound);
        }

        static BigDecimal getSumWithPercent(BigDecimal sumWithoutPercent, BigDecimal percent) {

            BigDecimal percentValue = getPercentValueFromSumWithoutPercent(sumWithoutPercent, percent);
            percentValue.setScale(2, moneyRound);

            if (BuildConfig.DEBUG){
                Log.i(TAG,"getSumWithPercent: sumWithoutPercent " + sumWithoutPercent.toString() + ", percent " + percent.toString() + ", percentValue, " + percentValue.toString());
            }

            return sumWithoutPercent.add(percentValue);

        }

        static BigDecimal getSumWithoutPercent(BigDecimal sumWithPercent, BigDecimal percent) {

            if (BuildConfig.DEBUG){
                Log.i(TAG,"getSumWithoutPercent: sumWithPercent " + sumWithPercent.toString() + ", percent " + percent.toString());
            }

            return (sumWithPercent.multiply(BigDecHundred)).divide(percent.add(BigDecHundred), moneyRound);

        }

        static BigDecimal getSumWithoutPercentFromPercentValue(BigDecimal value, BigDecimal percent){

            BigDecimal val = new BigDecimal(value.floatValue());
            val.setScale(2, moneyRound);

            if (BuildConfig.DEBUG){
                Log.i(TAG,"\n getSumWithoutPercentFromPercentValue: value " + value.toString() + ", percent " + percent.toString() + ", val " + val.toString());
            }

            BigDecimal answer = new BigDecimal(val.multiply(BigDecHundred).floatValue());

            if (BuildConfig.DEBUG){
                Log.i(TAG,"answer " + answer.toString());
            }

            answer = answer.divide(percent, moneyRound);

            if (BuildConfig.DEBUG){
                Log.i(TAG,"answer " + answer.toString());
            }

            return answer;

        }

        static BigDecimal getSumWithPercentFromPercentValue(BigDecimal value, BigDecimal percent){

            if (BuildConfig.DEBUG){
                Log.i(TAG,"\n getSumWithPercentFromPercentValue: value " + value.toString() + ", percent " + percent.toString());
            }

            BigDecimal divider = new BigDecimal(percent.floatValue());
            divider.setScale(2, moneyRound);

            if (BuildConfig.DEBUG){
                Log.i(TAG,"percentForComp " + divider.toString());
            }

            BigDecimal multiplier = new BigDecimal(percent.add(BigDecHundred).floatValue());
            multiplier.setScale(2, moneyRound);

            if (BuildConfig.DEBUG){
                Log.i(TAG,"multiplier " + multiplier.toString());
            }

            value = value.multiply(multiplier);

            if (BuildConfig.DEBUG){
                Log.i(TAG,"value " + value.toString());
            }

            return value.divide(divider, moneyRound);

        }

        static void noEmptyFocus(EditText text, View textOnFocus, Toast toast) {
            if (text.getText().length() <= 0) {
                toast.show();
                text.setText("1");
                textOnFocus.clearFocus();
                text.requestFocus();
            }
        }
    }
}