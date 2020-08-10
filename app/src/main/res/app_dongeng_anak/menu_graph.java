package com.example.namasteh.app_dongeng_anak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class menu_graph extends AppCompatActivity implements
        OnChartGestureListener, OnChartValueSelectedListener {

    private String stringtahun, stringsuka;
    //private TextView txtid;
    private float suka;
    private int tahun;
    private String JSON_STRING;
    private int resultcount;
    private static int idArray;
    private LineChart mChart;
    private ArrayList<String> allsuka;
    private ArrayList<String> alltahun;
    private JSONArray result;
    private ArrayList<String> xVals, xValstosetdata;
    private ArrayList<Entry> yVals, yValstosetdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_graph);

        mChart = (LineChart) findViewById(R.id.linechart);
        //txtid = (TextView) findViewById(R.id.graphid);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);

        getJSON();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

    }

    public void getJSON(){

        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_graph.this, "Fetching Data", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showGraph();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_TAMPIL_GRAPH_LIKE);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();

    }

    private void setData(ArrayList<String> xArray,ArrayList<Entry> yArray) {

        ArrayList<String> xValues = xArray;

        ArrayList<Entry> yValues = yArray;

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yValues, "Data Tipe Cerita Terbanyak Diurutkan Dari Kiri Ke Kanan");
        set1.setFillAlpha(110);
        set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xValues,dataSets);

        // set data
        mChart.setData(data);

    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            // or highlightTouch(null) for callback to onNothingSelected(...)
            mChart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: "+ velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex()+ ", high: " + mChart.getHighestVisibleXIndex());

        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin()
                + ", xmax: " + mChart.getXChartMax()
                + ", ymin: " + mChart.getYChartMin()
                + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    private void showGraph(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        alltahun = new ArrayList<String>();
        allsuka = new ArrayList<String>();
        resultcount = 0;


        try {
            jsonObject = new JSONObject(JSON_STRING);
            result = jsonObject.getJSONArray(konfigurasi.TAG_POSTING_CERITA_JSON_ARRAY);
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                stringtahun = jo.getString(konfigurasi.TAG_POSTING_CERITA_TAHUN);
                stringsuka = jo.getString(konfigurasi.TAG_POSTING_CERITA_TIPE_CERITA);

                alltahun.add(stringtahun);
                allsuka.add(stringsuka);
                resultcount++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        xValstosetdata = setXAxisValues(resultcount, allsuka);
        yValstosetdata = setYAxisValues(resultcount, alltahun);
        setData(xValstosetdata,yValstosetdata);
    }

    private ArrayList<String> setXAxisValues(int count, ArrayList<String> stringListx) {
        this.xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++){
            xVals.add(stringListx.get(i));

        }
        return xVals;
    }

    private ArrayList<Entry> setYAxisValues(int cnt, ArrayList<String> stringListy){
        this.yVals = new ArrayList<Entry>();

        for(int j=0; j<cnt;j++) {
            int y = Integer.valueOf(stringListy.get(j));
            yVals.add(new Entry(y,j));
        }
        return yVals;
    }


}
