package com.example.hackintosh.scanner;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView resultListView;
    private DataListAdapter dataListAdapter;
    private DataBase dataBase;
    private List<String> mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanBarcodeButton = (Button) findViewById(R.id.start_scan);
        scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CodeCapture.class);
                startActivityForResult(intent, 1);
            }
        });
        dataBase = new DataBase(this);
        mResult = dataBase.getResults();
        if(mResult != null) {
            initRecycleList();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(CodeCapture.BarcodeObject);
                    Point[] p = barcode.cornerPoints;
                    if(mResult == null) {
                        mResult = new ArrayList<>();
                        initRecycleList();
                    }
                    mResult.add(barcode.displayValue);
                    dataBase.addResult(DataBaseHelper.ResultModel.TABLE_NAME,barcode.displayValue);
                    dataListAdapter.notifyDataSetChanged();
                } //else mResultTextView.setText("No Bar Code Captured");
            }
//            } else Log.e("ERROR", "Activity Result Error",
//                    CommonStatusCodes.getStatusCodeString(resultCode)));
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    public void initRecycleList() {
        resultListView = (RecyclerView) findViewById(R.id.result);
        resultListView.setLayoutManager(new LinearLayoutManager(this));
        dataListAdapter = new DataListAdapter(mResult);
        resultListView.setAdapter(dataListAdapter);
    }
}
