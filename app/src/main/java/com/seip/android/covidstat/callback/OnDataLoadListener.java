package com.seip.android.covidstat.callback;
import com.seip.android.covidstat.models.CountryInfo;

public interface OnDataLoadListener {
    void onDataLoad(CountryInfo country);
}
