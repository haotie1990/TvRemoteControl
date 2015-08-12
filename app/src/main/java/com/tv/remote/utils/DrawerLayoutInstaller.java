package com.tv.remote.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by 凯阳 on 2015/7/13.
 */
public class DrawerLayoutInstaller {

    public static final int DEFAULT_LEFT_DRAWER_WIDTH_DP = 240;

    public static DrawerBuilder from(Activity activity){
        return new DrawerBuilder(activity);
    }

    public static class DrawerBuilder{

        private Activity activity;
        private int drawerRootResId;
        private Toolbar toolbar;
        private DrawerLayout.DrawerListener drawerListener;
        private View drawerLeftView;
        private int drawerLeftWidth;

        public DrawerBuilder(Activity activity) {
            this.activity = activity;
        }


        @SuppressWarnings("unused")
        private DrawerBuilder() {
            throw  new RuntimeException("Not supported. Use DrawerBuilder(Activity activity) instead");
        }

        /**
         * Root resource used as a base for DrawerLayout. The only supported structure built from
         * DrawerLayout as root element and two children
         * @param drawerRootResId
         * @return DrawerBuilder
         */
        public DrawerBuilder drawerRoot(int drawerRootResId){
            this.drawerRootResId = drawerRootResId;
            return this;
        }

        /**
         *Add toggler for open/close DrawerLayout. It takes navigation icon from
         * {@link Toolbar}
         * @param toolbar
         * @return DrawerBuilder
         */
        public DrawerBuilder withNavigationIconToggler(Toolbar toolbar){
            this.toolbar = toolbar;
            return this;
        }

        /**
         * Add listener for DrawerLayout operations.
         * @param drawerListener
         * @return DrawerBuilder
         */
        public DrawerBuilder drawerListener(DrawerLayout.DrawerListener drawerListener){
            this.drawerListener = drawerListener;
            return this;
        }

        /**
         * Add custom view which will be put inside root of left panel in DrawerLayout
         * @param drawerLeftView
         * @return DrawerBuilder
         */
        public DrawerBuilder drawerLeftView(View drawerLeftView){
            this.drawerLeftView = drawerLeftView;
            return this;
        }

        /**
         * Set custom width of left panel. By default 240dp translated to px is used.
         * @param width
         * @return DrawerBuilder
         */
        public DrawerBuilder drawerLeftWidth(int width){
            this.drawerLeftWidth = width;
            return this;
        }

        /**
         * Build DrawerLayout and inject it into given {@link Activity}
         * @return DrawerLayout
         */
        public DrawerLayout build(){
            DrawerLayout drawerLayout = createDrawerLayout();
            addDrawerToActivity(drawerLayout);
            setupToggler(drawerLayout);
            setupDrawerLeftView(drawerLayout);
            drawerLayout.setDrawerListener(drawerListener);
            return drawerLayout;
        }

        private DrawerLayout createDrawerLayout(){
            if (drawerRootResId != 0){
                return (DrawerLayout) LayoutInflater.from(activity).inflate(drawerRootResId, null);
            }else {
                DrawerLayout drawerLayout = new DrawerLayout(activity);

                FrameLayout contentView = new FrameLayout(activity);
                drawerLayout.addView(contentView, new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                FrameLayout leftDrawer = new FrameLayout(activity);
                int drawerWidth = drawerLeftWidth != 0 ? drawerLeftWidth : DEFAULT_LEFT_DRAWER_WIDTH_DP;

                final ViewGroup.LayoutParams leftDrawerParams = new DrawerLayout.LayoutParams(
                        (int)(drawerWidth * Resources.getSystem().getDisplayMetrics().density),
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        Gravity.START
                );
                drawerLayout.addView(leftDrawer, leftDrawerParams);
                return drawerLayout;
            }
        }

        private void addDrawerToActivity(DrawerLayout drawerLayout){
            ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content);
            ViewGroup drawerContentRoot = (ViewGroup) drawerLayout.getChildAt(0);/*vContentFrame*/
            View contentView = rootView.getChildAt(0);

            rootView.removeView(contentView);

            drawerContentRoot.addView(contentView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));

            rootView.addView(drawerLayout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
        }

        private void setupToggler(final DrawerLayout drawerLayout){
            if (toolbar != null){
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("gky", "Toolbar::NavigationOnClickListener");
                        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                            drawerLayout.closeDrawer(Gravity.LEFT);
                        } else {
                            drawerLayout.openDrawer(Gravity.LEFT);
                        }
                    }
                });
            }
        }

        private void setupDrawerLeftView(DrawerLayout drawerLayout) {
            if (drawerLeftView != null) {
                ((ViewGroup) drawerLayout.getChildAt(1))/*vLeftDrawer*/
                        .addView(drawerLeftView, new DrawerLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        ));
            }
        }
    }
}
