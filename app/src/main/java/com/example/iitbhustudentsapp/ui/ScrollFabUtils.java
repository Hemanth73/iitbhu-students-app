package com.example.iitbhustudentsapp.ui;

import android.content.Context;
import android.content.res.TypedArray;

import com.example.iitbhustudentsapp.R;

public class ScrollFabUtils {

    /**
     * Gets toolbar height.
     *
     * @param context   Activity context
     * @return          toolbar height
     */
    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    /**
     * Gets tab height.
     *
     * @param context   Activity context
     * @return          Tabs height
     */
    public static int getTabsHeight(Context context) {
        return (int) context.getResources().getDimension(R.dimen.tabsHeight);
    }
}
