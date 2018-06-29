package ru.shitsticks.accounthelper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;

import java.io.IOException;


public class PlanClassicActivity extends AppCompatActivity {

    private WorkWithDB mWorkWDB;
    private SQLiteDatabase mDB;
    BuhCursorAdapter mBuhCurAdapter;
    ExpandableListView mExpListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_classic);
        mWorkWDB = new WorkWithDB(this);

        try {
            mWorkWDB.updateDataBase();
        } catch (IOException mIOExeption) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDB = mWorkWDB.getWritableDatabase();
        } catch (SQLException mSQLExeption) {
            throw mSQLExeption;
        }

        Cursor cursor = mDB.rawQuery("SELECT _id, num, name FROM Scheta", null);

        mBuhCurAdapter = new BuhCursorAdapter(this, cursor, R.layout.plan_classic_item , new String[]{"num","name"},new int[]{R.id.sh_num ,R.id.sh_name}, R.layout.plan_classic_item_description, new String[]{"type", "description"}, new int[]{R.id.sh_type, R.id.sh_description}) {
        };
        mExpListView = (ExpandableListView) findViewById(R.id.exp_list_plan_classic);

        mExpListView.setAdapter(mBuhCurAdapter);
    }

    protected void onDestroy(){
        super.onDestroy();
        mDB.close();
    }

        /* значение полей
Context context - конткст (this)
Cursor cursor - курсор с данными по группам, название
int groupLayout - лэйаут для отображения группы
String[] groupFrom - поле курсора
int[] groupTo - то куда это поле отображается
int childLayout  лэйаут для отображения элемента
String[] childFrom - тоже самое что и groupto
int[] childTo - тоже самое что и groupto
*/

    class BuhCursorAdapter extends SimpleCursorTreeAdapter {

        public BuhCursorAdapter(Context context, Cursor cursor, int groupLayout, String[] groupFrom, int[] groupTo, int childLayout,
                                String[] childFrom, int[] childTo){
            super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom, childTo);
        }


        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            int mIdColumn = groupCursor.getColumnIndex("_id");
            int mCurrentNumber = groupCursor.getInt(mIdColumn);
            String mQuery = "SELECT * FROM Scheta WHERE _id = " + mCurrentNumber;
            Cursor mAnswer = mDB.rawQuery( mQuery , null);
            return mAnswer;
        }
    }
}