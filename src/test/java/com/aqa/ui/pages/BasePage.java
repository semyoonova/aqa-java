package com.aqa.ui.pages;


public abstract class BasePage<T extends BasePage<T>> {

    public abstract T isLoaded();

    public abstract T open();
}
