package com.tincio.capstoneproject.presentation.presenter;

public interface MvpPresenter<V> {
    void setView(V view);
    void detachView();
}
