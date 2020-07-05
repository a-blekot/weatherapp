package com.anadi.weatherinfo.addlocation;

import androidx.annotation.StringRes;

public interface AddLocationView {

    void loading();

    void onError(@StringRes int resId);

    void onAddedSuccess();
}
