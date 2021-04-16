package com.rao.weather;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.rao.weather.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private WeatherScrawl ws;
    private Typeface typeface;
    private List<FiveDayWeather> listData = new ArrayList<>();
    private FastAdapter<FiveDayWeather> mFastAdapter;
    private ItemAdapter<FiveDayWeather> mItemAdapter;
    private FiveDayWeather todayFiveDayWeather;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarLayout.toolbar);
        initValues();
        initSearchView();
        setupTextSwitchers();
        initRecyclerView();

        initPython();
        initWeatherData();
    }

    private void showTodayWeather() {
        binding.contentMainLayout.tempTextView.setText(ws.getDegree());
        binding.contentMainLayout.descriptionTextView.setText(ws.getWeather());
    }

    private void  initValues() {
        typeface = Typeface.createFromAsset(getAssets(), "fonts/Vazir.ttf");
        binding.contentMainLayout.todayMaterialCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todayFiveDayWeather != null) {
                    Intent intent = new Intent(MainActivity.this, HourlyActivity.class);
                    intent.putExtra(Constants.FIVE_DAY_WEATHER_ITEM, todayFiveDayWeather);
                    startActivity(intent);
                }
//                Intent intent = new Intent(v.getContext(), DisplayMessageActivity.class);
//                EditText editText = (EditText) findViewById(R.id.edit_message);
//                String message = editText.getText().toString();
//                intent.putExtra(EXTRA_MESSAGE, message);
//                startActivity(intent);
            }
        });
    }

    private void initSearchView() {
        binding.toolbarLayout.searchView.setVoiceSearch(false);
        binding.toolbarLayout.searchView.setHint(getString(R.string.search_label));
        binding.toolbarLayout.searchView.setCursorDrawable(R.drawable.custom_curosr);
        binding.toolbarLayout.searchView.setEllipsize(true);
        binding.toolbarLayout.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //requestWeather(query, true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        binding.toolbarLayout.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.toolbarLayout.searchView.showSearch();
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.contentMainLayout.recyclerView.setLayoutManager(layoutManager);
        mItemAdapter = new ItemAdapter<>();
        mFastAdapter = FastAdapter.with(mItemAdapter);
        binding.contentMainLayout.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.contentMainLayout.recyclerView.setAdapter(mFastAdapter);
        binding.contentMainLayout.recyclerView.setFocusable(false);
//        mFastAdapter.withOnClickListener(new OnClickListener<FiveDayWeather>() {
//            @Override
//            public boolean onClick(@Nullable View v, @NonNull IAdapter<FiveDayWeather> adapter, @NonNull FiveDayWeather item, int position) {
//                Intent intent = new Intent(MainActivity.this, HourlyActivity.class);
//                intent.putExtra(Constants.FIVE_DAY_WEATHER_ITEM, item);
//                startActivity(intent);
//                return true;
//            }
//        });
    }

    private  void initFiveDayWeather() {
        List<HashMap<String,String>> data = ws.getSevenDayWeather();
        for (int i=0; i<data.size(); i++) {
            FiveDayWeather f = new FiveDayWeather();
            f.setWeather(data.get(i));
            listData.add(f);
        }
    }

    private void showStoredFiveDayWeather() {
        if (listData.size()> 0) {
            mItemAdapter.clear();
            mItemAdapter.add(listData);
            todayFiveDayWeather = listData.get(1);
            todayFiveDayWeather.setHourList(ws.getTodayHourWeather());
        }
    }

    private void setupTextSwitchers() {
        binding.contentMainLayout.tempTextView.setFactory(new TextViewFactory(MainActivity.this, R.style.TempTextView, true, typeface));
        binding.contentMainLayout.tempTextView.setInAnimation(MainActivity.this, R.anim.slide_in_right);
        binding.contentMainLayout.tempTextView.setOutAnimation(MainActivity.this, R.anim.slide_out_left);
        binding.contentMainLayout.descriptionTextView.setFactory(new TextViewFactory(MainActivity.this, R.style.DescriptionTextView, true, typeface));
        binding.contentMainLayout.descriptionTextView.setInAnimation(MainActivity.this, R.anim.slide_in_right);
        binding.contentMainLayout.descriptionTextView.setOutAnimation(MainActivity.this, R.anim.slide_out_left);
        binding.contentMainLayout.humidityTextView.setFactory(new TextViewFactory(MainActivity.this, R.style.HumidityTextView, false, typeface));
        binding.contentMainLayout.humidityTextView.setInAnimation(MainActivity.this, R.anim.slide_in_bottom);
        binding.contentMainLayout.humidityTextView.setOutAnimation(MainActivity.this, R.anim.slide_out_top);
        binding.contentMainLayout.windTextView.setFactory(new TextViewFactory(MainActivity.this, R.style.WindSpeedTextView, false, typeface));
        binding.contentMainLayout.windTextView.setInAnimation(MainActivity.this, R.anim.slide_in_bottom);
        binding.contentMainLayout.windTextView.setOutAnimation(MainActivity.this, R.anim.slide_out_top);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        binding.toolbarLayout.searchView.setMenuItem(item);
        return true;
    }

//    public void showAboutFragment() {
//        AppUtil.showFragment(new AboutFragment(), getSupportFragmentManager(), true);
//    }

    @Override
    public void onBackPressed() {
        if (binding.toolbarLayout.searchView.isSearchOpen()) {
            binding.toolbarLayout.searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    // 初始化Python环境
    private void initPython(){
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initWeatherData() {
        ws = new WeatherScrawl();
        if(ws.scrawlWeather(Python.getInstance())) {
            showTodayWeather();
            initFiveDayWeather();
            showStoredFiveDayWeather();
        }
    }
}