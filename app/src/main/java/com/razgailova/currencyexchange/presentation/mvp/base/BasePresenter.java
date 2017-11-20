package com.razgailova.currencyexchange.presentation.mvp.base;

import android.support.annotation.NonNull;

/**
 * Created by Катерина on 19.11.2017.
 */

public class BasePresenter<T extends BaseView> {
    protected T view;

    public void bindView(@NonNull T view) {
        this.view = view;
    }

    public void unbindView() {
        this.view = null;
    }
}
