package com.tv.remote.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.tv.remote.R;
import com.tv.remote.net.NetUtils;
import com.tv.remote.utils.DrawerLayoutInstaller;
import com.tv.remote.utils.Utils;
import com.tv.remote.view.GlobalMenu;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 凯阳 on 2015/8/12.
 */
public abstract class BaseActivity extends AppCompatActivity
        implements GlobalMenu.OnHeaderClickListener, AdapterView.OnItemClickListener{

    public static final String AGR_DRAWING_START_LOCATION = "arg_drawing_start_location";

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private DrawerLayout drawerLayout;

    private static List<Activity> mActivities = new ArrayList<>();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
        initToolbar();
        initDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void add(Activity activity) {
        Log.i("gky","add "+activity);
        mActivities.add(activity);
        if (!NetUtils.getInstance().isConnectToClient()) {
            NetUtils.getInstance().init(new NetHandler(this));
        }
    }

    protected void remove(Activity activity) {
        Log.i("gky","remove "+activity);
        mActivities.remove(activity);
        Log.i("gky","size "+mActivities.size());
        if (mActivities.size() == 0) {
            NetUtils.getInstance().release();
        }
    }

    public abstract Activity getActivityBySuper();

    private void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setSubtitle(null);
            toolbar.setNavigationIcon(R.drawable.btn_option);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    private void initDrawer() {
        GlobalMenu menuView = new GlobalMenu(this);
        menuView.setOnHeaderClickListener(this);
        menuView.setOnItemClickListener(this);

        drawerLayout = DrawerLayoutInstaller.from(this)
                .drawerRoot(R.layout.drawer_root)
                .drawerLeftView(menuView)
                .drawerLeftWidth(Utils.dpToPx(300))
                .withNavigationIconToggler(getToolbar())
                .build();
    }

    @Override
    public void onGlobalMenuHeaderClick(View v) {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("gky",getClass().getSimpleName()+"::onItemClick position:"+position);
        if (position == 1) {/*TvRemoteActivity*/
            drawerLayout.closeDrawer(Gravity.LEFT);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getActivityBySuper(), TvRemoteActivity.class);
                    getActivityBySuper().startActivity(intent);
                    getActivityBySuper().overridePendingTransition(0, 0);
                    getActivityBySuper().finish();
                }
            },200);
        }else if (position == 2) {/*TvCommentsActivity*/
            drawerLayout.closeDrawer(Gravity.LEFT);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getActivityBySuper(), TvCommentsActivity.class);
                    getActivityBySuper().startActivity(intent);
                    getActivityBySuper().overridePendingTransition(0, 0);
                    getActivityBySuper().finish();
                }
            },200);
        }

    }

    public static class NetHandler extends Handler {

        private WeakReference<BaseActivity> mActivity;

        public NetHandler(BaseActivity activity) {
            mActivity = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(mActivity.get(), "初始化完成，等待链接...",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(mActivity.get(),"连接到电视，可以使用！",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mActivity.get(),"连接已经断开，App不可用。",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(mActivity.get(),"未连接到TV，App不可用。",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
