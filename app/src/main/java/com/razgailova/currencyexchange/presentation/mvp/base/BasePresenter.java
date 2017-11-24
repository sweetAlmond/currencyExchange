package com.razgailova.currencyexchange.presentation.mvp.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Катерина on 19.11.2017.
 */

public abstract class BasePresenter<T extends BaseView, S extends BaseState> {
    protected T view;

    public abstract void readState(@Nullable S state);

    public void bindView(@NonNull T view) {
        this.view = view;
    }

    public void unbindView() {
        this.view = null;
    }

    @NonNull public abstract S getState();
}
