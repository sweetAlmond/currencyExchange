package com.razgailova.currencyexchange.presentation.mvp;

import android.os.Bundle;

import com.razgailova.currencyexchange.presentation.mvp.base.BasePresenter;
import com.razgailova.currencyexchange.presentation.mvp.base.BaseView;

import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Катерина on 19.11.2017.
 */

public class PresenterCache {
    private static final String KEY_PRESENTER_ID = "presenter_id";
    private static PresenterCache instance;

    private final AtomicLong currentId;

    private final TreeMap<Long, BasePresenter<? extends BaseView>> presenters;

    private PresenterCache() {
        currentId = new AtomicLong();

        presenters = new TreeMap<>();
    }

    public static synchronized PresenterCache getInstance() {
        if (instance == null) {
            instance = new PresenterCache();
        }
        return instance;
    }

    public <P extends BasePresenter<? extends BaseView>> P restorePresenter(Bundle savedInstanceState) {
        Long presenterId = savedInstanceState.getLong(KEY_PRESENTER_ID);
        P presenter = (P) presenters.get(presenterId);
        presenters.remove(presenterId);
        return presenter;
    }

    public void savePresenter(BasePresenter<? extends BaseView> presenter, Bundle outState) {
        long presenterId = currentId.incrementAndGet();
        presenters.put(presenterId, presenter);
        outState.putLong(KEY_PRESENTER_ID, presenterId);
    }
}
