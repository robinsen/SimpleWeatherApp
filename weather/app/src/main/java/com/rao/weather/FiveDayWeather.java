package com.rao.weather;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.os.ConfigurationCompat;

import com.bumptech.glide.Glide;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.rao.weather.databinding.WeatherDayItemBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.objectbox.annotation.Id;
//import androidx.room.Entity;

//import com.github.bkhezry.weather.R;
//import com.github.bkhezry.weather.utils.AppUtil;
//import com.github.bkhezry.weather.utils.Constants;

public class FiveDayWeather extends AbstractItem<FiveDayWeather, FiveDayWeather.MyViewHolder> implements Parcelable {
  @Id
  private long id;
  private int dt;
  private double temp;
  private String minTemp;
  private String maxTemp;
  private int weatherId;
  private long timestampStart;
  private long timestampEnd;
  private String dateStr;
  private String dayWeather;
  private @ColorInt
  int color;
  private @ColorInt
  int colorAlpha;

  private List<HashMap<String,String>> hourList = new ArrayList<>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getDt() {
    return dt;
  }

  public void setDt(int dt) {
    this.dt = dt;
  }

  public double getTemp() {
    return temp;
  }

  public void setTemp(double temp) {
    this.temp = temp;
  }

  public String getMinTemp() {
    return minTemp;
  }

  public void setMinTemp(String minTemp) {
    this.minTemp = minTemp;
  }

  public String getMaxTemp() {
    return maxTemp;
  }

  public void setMaxTemp(String maxTemp) {
    this.maxTemp = maxTemp;
  }

  public int getWeatherId() {
    return weatherId;
  }

  public void setWeatherId(int weatherId) {
    this.weatherId = weatherId;
  }

  public long getTimestampStart() {
    return timestampStart;
  }

  public void setTimestampStart(long timestampStart) {
    this.timestampStart = timestampStart;
  }

  public long getTimestampEnd() {
    return timestampEnd;
  }

  public void setTimestampEnd(long timestampEnd) {
    this.timestampEnd = timestampEnd;
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public int getColorAlpha() {
    return colorAlpha;
  }

  public void setColorAlpha(int colorAlpha) {
    this.colorAlpha = colorAlpha;
  }
  public String getDateStr() {
    return this.dateStr;
  }
  public String getDayWeather() { return this.dayWeather; }
  @NonNull
  @Override
  public MyViewHolder getViewHolder(@NonNull View v) {
    return new MyViewHolder(v);
  }

  @Override
  public int getType() {
    return R.id.fastadapter_item_adapter;
  }

  @Override
  public int getLayoutRes() {
    return R.layout.weather_day_item;
  }

  protected static class MyViewHolder extends FastAdapter.ViewHolder<FiveDayWeather> {
    Context context;
    WeatherDayItemBinding binding;

    MyViewHolder(View view) {
      super(view);
      binding = WeatherDayItemBinding.bind(view);
      context = view.getContext();
    }

    @Override
    public void bindView(@NonNull FiveDayWeather item, @NonNull List<Object> payloads) {
      binding.cardView.setCardBackgroundColor(item.getColor());
      int[] colors = {
          Color.TRANSPARENT,
          item.getColorAlpha(),
          Color.TRANSPARENT
      };
      Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
      calendar.setTimeInMillis(item.getDt() * 1000L);
      binding.dayNameTextView.setText(item.getDateStr());
//      if (isRTL(context)) {
//        binding.dayNameTextView.setText(Constants.DAYS_OF_WEEK_PERSIAN[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
//      } else {
//        binding.dayNameTextView.setText(Constants.DAYS_OF_WEEK[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
//      }
//      if (item.maxTemp < 0 && item.maxTemp > -0.5) {
//        item.maxTemp = 0;
//      }
//      if (item.minTemp < 0 && item.minTemp > -0.5) {
//        item.minTemp = 0;
//      }
      if (item.temp < 0 && item.temp > -0.5) {
        item.temp = 0;
      }
      binding.tempTextView.setText(item.getDayWeather());
      binding.minTempTextView.setText(item.getMinTemp());
      binding.maxTempTextView.setText(item.getMaxTemp());
      setWeatherIcon(context, binding.weatherImageView, item.weatherId);
      GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors);
      shape.setShape(GradientDrawable.OVAL);
      binding.shadowView.setBackground(shape);
    }

    @Override
    public void unbindView(@NonNull FiveDayWeather item) {

    }

  }

  public static final Parcelable.Creator<FiveDayWeather> CREATOR = new Parcelable.Creator<FiveDayWeather>() {
    @Override
    public FiveDayWeather createFromParcel(Parcel source) {
      return new FiveDayWeather(source);
    }

    @Override
    public FiveDayWeather[] newArray(int size) {
      return new FiveDayWeather[size];
    }
  };

  public FiveDayWeather() {
  }

  protected FiveDayWeather(Parcel in) {
//    this.id = in.readLong();
//    this.dt = in.readInt();
//    this.temp = in.readDouble();
//    this.minTemp = in.readDouble();
//    this.maxTemp = in.readDouble();
//    this.weatherId = in.readInt();
//    this.timestampStart = in.readLong();
//    this.timestampEnd = in.readLong();
//    this.color = in.readInt();
//    this.colorAlpha = in.readInt();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
//    dest.writeLong(this.id);
//    dest.writeInt(this.dt);
//    dest.writeDouble(this.temp);
//    dest.writeDouble(this.minTemp);
//    dest.writeDouble(this.maxTemp);
//    dest.writeInt(this.weatherId);
//    dest.writeLong(this.timestampStart);
//    dest.writeLong(this.timestampEnd);
//    dest.writeInt(this.color);
//    dest.writeInt(this.colorAlpha);
  }

  public static boolean isRTL(Context context) {
    Locale locale = ConfigurationCompat.getLocales(context.getResources().getConfiguration()).get(0);
    final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
    return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
            directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
  }

  public static void setWeatherIcon(Context context, AppCompatImageView imageView, int weatherCode) {
    if (weatherCode == 0) {
      Glide.with(context).load(R.drawable.weather_00).into(imageView);
    } else if (weatherCode == 1) {
      Glide.with(context).load(R.drawable.weather_01).into(imageView);
    } else if (weatherCode == 2) {
      Glide.with(context).load(R.drawable.weather_02).into(imageView);
    } else if (weatherCode == 3) {
      Glide.with(context).load(R.drawable.weather_03).into(imageView);
    } else if (weatherCode == 4) {
      Glide.with(context).load(R.drawable.weather_04).into(imageView);
    } else if (weatherCode == 5) {
      Glide.with(context).load(R.drawable.weather_05).into(imageView);
    } else if (weatherCode == 6) {
      Glide.with(context).load(R.drawable.weather_06).into(imageView);
    } else if (weatherCode == 7) {
      Glide.with(context).load(R.drawable.weather_07).into(imageView);
    } else if (weatherCode == 8) {
      Glide.with(context).load(R.drawable.weather_08).into(imageView);
    } else if (weatherCode == 9) {
      Glide.with(context).load(R.drawable.weather_09).into(imageView);
    } else if (weatherCode == 10) {
      Glide.with(context).load(R.drawable.weather_10).into(imageView);
    } else if (weatherCode == 11) {
      Glide.with(context).load(R.drawable.weather_11).into(imageView);
    } else if (weatherCode == 12) {
      Glide.with(context).load(R.drawable.weather_12).into(imageView);
    } else if (weatherCode == 13) {
      Glide.with(context).load(R.drawable.weather_13).into(imageView);
    } else if (weatherCode == 14) {
      Glide.with(context).load(R.drawable.weather_14).into(imageView);
    } else if (weatherCode == 15) {
      Glide.with(context).load(R.drawable.weather_15).into(imageView);
    } else if (weatherCode == 16) {
      Glide.with(context).load(R.drawable.weather_16).into(imageView);
    } else if (weatherCode == 17) {
      Glide.with(context).load(R.drawable.weather_17).into(imageView);
    } else if (weatherCode == 18) {
      Glide.with(context).load(R.drawable.weather_18).into(imageView);
    } else if (weatherCode == 19) {
      Glide.with(context).load(R.drawable.weather_19).into(imageView);
    } else if (weatherCode == 20) {
      Glide.with(context).load(R.drawable.weather_20).into(imageView);
    }else if (weatherCode == 21) {
      Glide.with(context).load(R.drawable.weather_21).into(imageView);
    } else if (weatherCode == 22) {
      Glide.with(context).load(R.drawable.weather_22).into(imageView);
    } else if (weatherCode == 23) {
      Glide.with(context).load(R.drawable.weather_23).into(imageView);
    } else if (weatherCode == 24) {
      Glide.with(context).load(R.drawable.weather_24).into(imageView);
    } else if (weatherCode == 25) {
      Glide.with(context).load(R.drawable.weather_25).into(imageView);
    } else if (weatherCode == 26) {
      Glide.with(context).load(R.drawable.weather_26).into(imageView);
    } else if (weatherCode == 27) {
      Glide.with(context).load(R.drawable.weather_27).into(imageView);
    } else if (weatherCode == 28) {
      Glide.with(context).load(R.drawable.weather_28).into(imageView);
    } else if (weatherCode == 29) {
      Glide.with(context).load(R.drawable.weather_29).into(imageView);
    } else if (weatherCode == 30) {
      Glide.with(context).load(R.drawable.weather_30).into(imageView);
    }else if (weatherCode == 31) {
      Glide.with(context).load(R.drawable.weather_31).into(imageView);
    } else if (weatherCode == 32) {
      Glide.with(context).load(R.drawable.weather_32).into(imageView);
    }
  }

  public static String getTime(Calendar calendar, Context context) {
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    String hourString;
    if (hour < 10) {
      hourString = String.format(Locale.getDefault(), context.getString(R.string.zero_label), hour);
    } else {
      hourString = String.format(Locale.getDefault(), "%d", hour);
    }
    String minuteString;
    if (minute < 10) {
      minuteString = String.format(Locale.getDefault(), context.getString(R.string.zero_label), minute);
    } else {
      minuteString = String.format(Locale.getDefault(), "%d", minute);
    }
    return hourString + ":" + minuteString;
  }

  public void setWeather (HashMap<String, String> map) {
    this.color = Color.BLUE;
    this.colorAlpha = 100;
    this.minTemp = map.get("min_degree");
    this.maxTemp = map.get("max_degree");
    this.weatherId = Integer.parseInt(map.get("day_weather_code"));
    this.dateStr = map.get("time");
    this.dayWeather = map.get("day_weather_short");
  }

  public void setHourList (List<HashMap<String,String>> list) {
     this.hourList = list;
  }

  public List<HashMap<String,String>> getTodayHourWeather() {
      return this.hourList;
  }
}
