package com.example.iitbhustudentsapp.ui;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;


public class ScrollingFabBehavior extends FloatingActionButton.Behavior {
    private int toolbarHeight;

    public ScrollingFabBehavior(Context context, AttributeSet attrs) {
        super();
        this.toolbarHeight = ScrollFabUtils.getToolbarHeight(context);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton fab,
                                   View dependency) {
        return super.layoutDependsOn(parent, fab, dependency)
            || (dependency instanceof AppBarLayout);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton fab,
                                          View dependency) {
        boolean returnValue = super.onDependentViewChanged(parent, fab, dependency);
        if (dependency instanceof AppBarLayout) {
                CoordinatorLayout.LayoutParams lp
                    = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
                int fabBottomMargin = lp.bottomMargin;
                int distanceToScroll = fab.getHeight() + fabBottomMargin;
                float ratio = dependency.getY() / (float)toolbarHeight;
                fab.setTranslationY(-distanceToScroll * ratio);
        }
        return returnValue;
    }
}