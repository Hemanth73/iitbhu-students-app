/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.iitbhustudentsapp.customui;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iitbhustudentsapp.R;

/**
 * <p>
 * To be used with ViewPager to provide a tab indicator component which give constant feedback as to
 * the user's scroll progress.

 * To use the component, simply add it to your view hierarchy. Then in your
 * {@link android.app.Activity} or {@link android.support.v4.app.Fragment} call
 * {@link #setViewPager(android.support.v4.view.ViewPager)} providing it the ViewPager this layout
 * is being used for.
 * The colors can be customized in two ways. The first and simplest is to provide an array of colors
 * via {@link #setSelectedIndicatorColors(int...)}. The
 * alternative is via the {@link com.example.iitbhustudentsapp.customui.SlidingTabLayout.TabColorizer}
 * interface which provides you complete control over
 * which color is used for any individual position.
 * The views used as tabs can be customized by calling {@link #setCustomTabView(int, int)},
 * providing the layout ID of your custom layout.
 * </p>
 */
public class SlidingTabLayout extends HorizontalScrollView {
    private static final int TITLE_OFFSET_DIPS = 24;
    private static final int TAB_VIEW_PADDING_DIPS = 16;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;

    private int titleOffset;

    private int tabViewLayoutId;
    private int tabViewTextViewId;
    private boolean distributeEvenly;

    private ViewPager viewPager;
    private SparseArray<String> contentDescriptions = new SparseArray<>();
    private ViewPager.OnPageChangeListener viewPagerPageChangeListener;

    private final SlidingTabStrip tabStrip;

    /**
     * Allows complete control over the colors drawn in the tab layout. Set with
     * {@link #setCustomTabColorizer(
     *  com.example.iitbhustudentsapp.customui.SlidingTabLayout.TabColorizer)}.
     */
    public interface TabColorizer {

        /**
         * Gets indicator color.
         *
         * @return return the color of the indicator used when {@code position} is selected.
         */
        int getIndicatorColor(int position);

    }

    public SlidingTabLayout(Context context) {
        this(context, null);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor.
     *
     * @param context   Activity context
     * @param attrs     Attributes
     * @param defStyle  Defined style
     */
    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);

        titleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

        tabStrip = new SlidingTabStrip(context);
        addView(tabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * Set the custom {@link com.example.iitbhustudentsapp.customui.SlidingTabLayout.TabColorizer}
     * to be used.
     * If you only require simple custmisation then you can use
     * {@link #setSelectedIndicatorColors(int...)} to achieve
     * similar effects.
     */
    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        tabStrip.setCustomTabColorizer(tabColorizer);
    }

    public void setDistributeEvenly(boolean distributeEvenly) {
        this.distributeEvenly = distributeEvenly;
    }

    /**
     * Sets the colors to be used for indicating the selected tab. These colors are treated as a
     * circular array.Providing one color will mean that all tabs are indicated with the same color.
     */
    public void setSelectedIndicatorColors(int... colors) {
        tabStrip.setSelectedIndicatorColors(colors);
    }

    /**
     * Set the {@link android.support.v4.view.ViewPager.OnPageChangeListener}. When using
     * {@link com.example.iitbhustudentsapp.customui.SlidingTabLayout} you are
     * required to set any {@link android.support.v4.view.ViewPager.OnPageChangeListener}
     * through this method. This is so
     * that the layout can update it's scroll position correctly.
     * @see android.support.v4.view.ViewPager#setOnPageChangeListener(
     * android.support.v4.view.ViewPager.OnPageChangeListener)
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        viewPagerPageChangeListener = listener;
    }

    /**
     * Set the custom layout to be inflated for the tab views.
     *
     * @param layoutResId Layout id to be inflated
     * @param textViewId id of the {@link android.widget.TextView} in the inflated view
     */
    public void setCustomTabView(int layoutResId, int textViewId) {
        tabViewLayoutId = layoutResId;
        tabViewTextViewId = textViewId;
    }

    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        tabStrip.removeAllViews();

        this.viewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * {@link #setCustomTabView(int, int)}.
     */
    protected TextView createDefaultTabView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                outValue, true);
        textView.setBackgroundResource(outValue.resourceId);
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        textView.setAllCaps(true);

        int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
        textView.setPadding(padding, padding, padding, padding);

        return textView;
    }

    private void populateTabStrip() {
        final PagerAdapter adapter = viewPager.getAdapter();
        final OnClickListener tabClickListener = new TabClickListener();

        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = null;
            TextView tabTitleView = null;

            if (tabViewLayoutId != 0) {
                // If there is a custom tab view layout id set, try and inflate it
                tabView = LayoutInflater.from(getContext()).inflate(tabViewLayoutId, tabStrip,
                        false);
                ImageView tabImage = tabView.findViewById(R.id.subscribe_switch);
                switch (i) {
                    case 0: tabImage.setImageResource(R.drawable.newspaper);
                        break;
                    case 1:tabImage.setImageResource(R.drawable.newspaper);
                        break;
                    case 2:tabImage.setImageResource(R.drawable.newspaper);
                        break;
                    case 3:tabImage.setImageResource(R.drawable.newspaper);
                        break;
                    default:tabImage.setImageResource(R.drawable.newspaper);
                }


                tabTitleView = tabView.findViewById(tabViewTextViewId);
            }

            if (tabView == null) {
                tabView = createDefaultTabView(getContext());
            }

            if (tabTitleView == null && TextView.class.isInstance(tabView)) {
                tabTitleView = (TextView) tabView;
            }

            if (distributeEvenly) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)tabView.getLayoutParams();
                lp.width = 0;
                lp.weight = 1;
            }

            if (!adapter.getPageTitle(i).toString().contentEquals("0")) {
                tabTitleView.setVisibility(VISIBLE);
            } else {
                tabTitleView.setVisibility(GONE);
            }
            tabTitleView.setText(adapter.getPageTitle(i));
            tabView.setOnClickListener(tabClickListener);
            String desc = contentDescriptions.get(i, null);
            if (desc != null) {
                tabView.setContentDescription(desc);
            }

            tabStrip.addView(tabView);
            if (i == viewPager.getCurrentItem()) {
                tabView.setSelected(true);
            }


            tabTitleView.setTextSize(12);

        }


    }

    public void setContentDescription(int i, String desc) {
        contentDescriptions.put(i, desc);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (viewPager != null) {
            scrollToTab(viewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = tabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedChild = tabStrip.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= titleOffset;
            }

            scrollTo(targetScrollX, 0);
            /*
             * Try to increase the text style
             *
             */

            //Revert styles of other children
//            for(int i=0;i<tabStrip.getChildCount();i++){
//                //((TextView)tabStrip.getChildAt(i)).setTypeface(null,Typeface.NORMAL);
//   ((TextView)tabStrip.getChildAt(i)).setTextColor(getResources().getColor(R.color.light_indigo));
//
//            }
//            //((TextView)selectedChild).setTypeface(null,Typeface.BOLD);
//         ((TextView)selectedChild).setTextColor(getResources().getColor(R.color.tabsScrollColor));
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        private int scrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = tabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }

            tabStrip.onViewPagerPageChanged(position, positionOffset);

            View selectedTitle = tabStrip.getChildAt(position);
            int extraOffset = (selectedTitle != null)
                    ? (int) (positionOffset * selectedTitle.getWidth())
                    : 0;
            scrollToTab(position, extraOffset);

            if (viewPagerPageChangeListener != null) {
                viewPagerPageChangeListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            scrollState = state;

            if (viewPagerPageChangeListener != null) {
                viewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
                tabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
            }
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                tabStrip.getChildAt(i).setSelected(position == i);
            }
            if (viewPagerPageChangeListener != null) {
                viewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    private class TabClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                if (v == tabStrip.getChildAt(i)) {
                    viewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }

}
