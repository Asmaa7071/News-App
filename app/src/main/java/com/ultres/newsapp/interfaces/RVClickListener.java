package com.ultres.newsapp.interfaces;

import android.net.Uri;

public interface RVClickListener {
    void openNewsDetailsPages(Uri uri);
    void categoryFiltration(String categoryName);
}
