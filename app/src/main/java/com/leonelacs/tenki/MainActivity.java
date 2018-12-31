package com.leonelacs.tenki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    List<String> cityItems = new ArrayList<>();
//    List<String> districtItems = new ArrayList<>();
//    Spinner ProvinceSpinner;
//    Spinner CitySpinner;
//    Spinner DistrictSpinner;

    LinearLayout mainMiddle;
    LinearLayout aqiSquare;

    ChiikiLocal chiikiLocal = new ChiikiLocal();
//    ArrayAdapter<String> CityAdapter; // = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityItems);
//    ArrayAdapter<String> DistrictAdapter; // = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, districtItems);
    TenkiAPIO questAPI = new TenkiAPIO();

    TextView tenkiUpdateTime;
    TextView mokuzenTenkiEmoji;
    TextView mokuzenTemperature;
    TextView mokuzenWindAndHumidity;
    TextView todayTemperature;
    TextView todayTenki;
    TextView todayClothes;

    TextView youbi_1;
    TextView youbi_2;
    TextView youbi_3;
    TextView youbi_4;
    TextView youbi_5;
    TextView youbi_6;

    TextView youbi_1Temp;
    TextView youbi_2Temp;
    TextView youbi_3Temp;
    TextView youbi_4Temp;
    TextView youbi_5Temp;
    TextView youbi_6Temp;

    TextView youbi_1Tenki;
    TextView youbi_2Tenki;
    TextView youbi_3Tenki;
    TextView youbi_4Tenki;
    TextView youbi_5Tenki;
    TextView youbi_6Tenki;

    TextView aqiUpdateTime;
    TextView mokuzenAQIValue;
    TextView mokuzenAQIWord;
    TextView pm_25;
    TextView pm_10;
    TextView coGas;
    TextView no2Gas;
    TextView o3Gas;
    TextView so2Gas;

    SwipeRefreshLayout swipeRefresh;

    TenkiInfo newInfo;
    private Handler handler;

    LocationManager locationManager; // = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
    List<String> providers; // = locationManager.getProviders(true);
    String locationProvider; // = null

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //Left
                    findViewById(R.id.MokuzenPage).setVisibility(View.VISIBLE);
                    findViewById(R.id.YohouPage).setVisibility(View.INVISIBLE);
                    findViewById(R.id.OsenPage).setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    //Middle
                    findViewById(R.id.MokuzenPage).setVisibility(View.INVISIBLE);
                    findViewById(R.id.YohouPage).setVisibility(View.VISIBLE);
                    findViewById(R.id.OsenPage).setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    //Right
                    findViewById(R.id.MokuzenPage).setVisibility(View.INVISIBLE);
                    findViewById(R.id.YohouPage).setVisibility(View.INVISIBLE);
                    findViewById(R.id.OsenPage).setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final List<String> cityItems = new ArrayList<>();
        final List<String> districtItems = new ArrayList<>();

        mainMiddle = findViewById(R.id.MainMiddle);
        aqiSquare = findViewById(R.id.AQISquare);

        final Spinner ProvinceSpinner = (Spinner)findViewById(R.id.ProvinceSelection);
        final Spinner CitySpinner = (Spinner)findViewById(R.id.CitySelection);
        final Spinner DistrictSpinner = (Spinner)findViewById(R.id.DistrictSelection);

        final ArrayAdapter<String> CityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityItems);
        final ArrayAdapter<String> DistrictAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, districtItems);
        CityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DistrictAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        CitySpinner.setAdapter(CityAdapter);
        DistrictSpinner.setAdapter(DistrictAdapter);

        handler = new Handler();

        tenkiUpdateTime = findViewById(R.id.TenkiUpdateTime);
        mokuzenTenkiEmoji = findViewById(R.id.MokuzenTenkiEmoji);
        mokuzenTemperature = findViewById(R.id.MokuzenTemperature);
        mokuzenWindAndHumidity = findViewById(R.id.MokuzenWindAndHumidity);
        todayTemperature = findViewById(R.id.TodayTemperature);
        todayTenki = findViewById(R.id.TodayTenki);
        todayClothes = findViewById(R.id.TodayClothes);

        youbi_1 = findViewById(R.id.Youbi1);
        youbi_2 = findViewById(R.id.Youbi2);
        youbi_3 = findViewById(R.id.Youbi3);
        youbi_4 = findViewById(R.id.Youbi4);
        youbi_5 = findViewById(R.id.Youbi5);
        youbi_6 = findViewById(R.id.Youbi6);

        youbi_1Temp = findViewById(R.id.Youbi1Temp);
        youbi_2Temp = findViewById(R.id.Youbi2Temp);
        youbi_3Temp = findViewById(R.id.Youbi3Temp);
        youbi_4Temp = findViewById(R.id.Youbi4Temp);
        youbi_5Temp = findViewById(R.id.Youbi5Temp);
        youbi_6Temp = findViewById(R.id.Youbi6Temp);

        youbi_1Tenki = findViewById(R.id.Youbi1Tenki);
        youbi_2Tenki = findViewById(R.id.Youbi2Tenki);
        youbi_3Tenki = findViewById(R.id.Youbi3Tenki);
        youbi_4Tenki = findViewById(R.id.Youbi4Tenki);
        youbi_5Tenki = findViewById(R.id.Youbi5Tenki);
        youbi_6Tenki = findViewById(R.id.Youbi6Tenki);

        aqiUpdateTime = findViewById(R.id.AQIUpdateTime);
        mokuzenAQIValue = findViewById(R.id.MokuzenAQIValue);
        mokuzenAQIWord = findViewById(R.id.MokuzenAQIWord);
        pm_25 = findViewById(R.id.PM25);
        pm_10 = findViewById(R.id.PM10);
        coGas = findViewById(R.id.CO);
        no2Gas = findViewById(R.id.NO2);
        o3Gas = findViewById(R.id.O3);
        so2Gas = findViewById(R.id.SO2);

        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            this.startActivity(i);
        }

        ProvinceSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String provinceSelected = ProvinceSpinner.getSelectedItem().toString().trim();
                //cityItems = chiikiLocal.getShiByShou(provinceSelected);
                List<String> items = chiikiLocal.getShiByShou(provinceSelected);
                cityItems.clear();
                cityItems.addAll(items);
                CityAdapter.notifyDataSetChanged();
                try {
                    String citySelected = CitySpinner.getSelectedItem().toString().trim();
                    items = chiikiLocal.getKuByShi(citySelected);
                    districtItems.clear();
                    districtItems.addAll(items);
                    DistrictAdapter.notifyDataSetChanged();
                }
                catch (Exception e) {
                    districtItems.clear();
                    DistrictAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CitySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String citySelected = CitySpinner.getSelectedItem().toString().trim();
                //districtItems = chiikiLocal.getKuByShi(citySelected);
                List<String> items = chiikiLocal.getKuByShi(citySelected);
                districtItems.clear();
                districtItems.addAll(items);
                DistrictAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                districtItems.clear();
                DistrictAdapter.notifyDataSetChanged();
            }
        });

        DistrictSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String districtSelected = DistrictSpinner.getSelectedItem().toString().trim();
//                String bigCity = CitySpinner.getSelectedItem().toString().trim();

//                TenkiInfo newInfo = questAPI.refreshByCityName(districtSelected, bigCity);
//                tenkiInfoChanged(newInfo);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String districtSelected = DistrictSpinner.getSelectedItem().toString().trim();
                        String bigCity = CitySpinner.getSelectedItem().toString().trim();
                        newInfo = questAPI.refreshByCityName(districtSelected, bigCity);
                        handler.post(runnable);
                    }
                }).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location;
                try {
                    location = locationManager.getLastKnownLocation(locationProvider);
                    final String lon = String.valueOf(location.getLongitude());
                    final String lat = String.valueOf(location.getLatitude());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            newInfo = questAPI.refreshByCoordinate(lon, lat);
                            handler.post(runnable);
                        }
                    }).start();
                }
                catch (SecurityException e) {
                    Snackbar.make(view, "未获得定位权限", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                catch (NullPointerException e) {}
            }
        });

        swipeRefresh = findViewById(R.id.SwipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String districtSelected = DistrictSpinner.getSelectedItem().toString().trim();
                        String bigCity = CitySpinner.getSelectedItem().toString().trim();
                        newInfo = questAPI.refreshByCityName(districtSelected, bigCity);
                        handler.post(runnable);
                    }
                }).start();
                swipeRefresh.setRefreshing(false);
            }
        });

    }

    void tenkiInfoChanged(TenkiInfo info) {
        tenkiUpdateTime.setText(info.tenkiUpdateTime);
        mokuzenTenkiEmoji.setText(info.mokuzenTenkiEmoji);
        mokuzenTemperature.setText(info.mokuzenTemperature);
        mokuzenWindAndHumidity.setText(info.mokuzenWindAndHumidity);
        todayTemperature.setText(info.todayTemperature);
        todayTenki.setText(info.todayTenki);
        todayClothes.setText(info.todayClothes);

        youbi_1.setText(info.youbi1Youbi);
        youbi_2.setText(info.youbi2Youbi);
        youbi_3.setText(info.youbi3Youbi);
        youbi_4.setText(info.youbi4Youbi);
        youbi_5.setText(info.youbi5Youbi);
        youbi_6.setText(info.youbi6Youbi);

        youbi_1Temp.setText(info.youbi1Temp);
        youbi_2Temp.setText(info.youbi2Temp);
        youbi_3Temp.setText(info.youbi3Temp);
        youbi_4Temp.setText(info.youbi4Temp);
        youbi_5Temp.setText(info.youbi5Temp);
        youbi_6Temp.setText(info.youbi6Temp);

        youbi_1Tenki.setText(info.youbi1Tenki);
        youbi_2Tenki.setText(info.youbi2Tenki);
        youbi_3Tenki.setText(info.youbi3Tenki);
        youbi_4Tenki.setText(info.youbi4Tenki);
        youbi_5Tenki.setText(info.youbi5Tenki);
        youbi_6Tenki.setText(info.youbi6Tenki);

        aqiUpdateTime.setText(info.aqiUpdateTime);
        mokuzenAQIValue.setText(info.mokuzenAQIValue);
        mokuzenAQIWord.setText(info.mokuzenAQIWord);
        pm_25.setText(info.pm25Value);
        pm_10.setText(info.pm10Value);
        coGas.setText(info.coValue);
        no2Gas.setText(info.no2Value);
        o3Gas.setText(info.o3Value);
        so2Gas.setText(info.so2Value);

        int ima = info.imaOndo;
        if (ima >= 28) { mainMiddle.setBackgroundColor(Color.parseColor("#F05E1C")); }
        else if (ima <= 0) { mainMiddle.setBackgroundColor(Color.parseColor("#0F2540")); }
        else { mainMiddle.setBackgroundColor(Color.parseColor("#26453D")); }

        String aqi = info.mokuzenAQIWord;
        if (aqi.equals("优")) { aqiSquare.setBackgroundColor(Color.parseColor("#1B813E")); }
        else if (aqi.equals("良")) { aqiSquare.setBackgroundColor(Color.parseColor("#F7D94C")); }
        else if (aqi.equals("轻度污染")) { aqiSquare.setBackgroundColor(Color.parseColor("#F05E1C")); }
        else if (aqi.equals("中度污染")) { aqiSquare.setBackgroundColor(Color.parseColor("#C73E3A")); }
        else if (aqi.equals("重度污染")) { aqiSquare.setBackgroundColor(Color.parseColor("#4A225D")); }
        else if (aqi.equals("严重污染")) { aqiSquare.setBackgroundColor(Color.parseColor("#562E37")); }
        else { aqiSquare.setBackgroundColor(Color.parseColor("#1C1C1C")); }

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tenkiInfoChanged(newInfo);
        }
    };

}
