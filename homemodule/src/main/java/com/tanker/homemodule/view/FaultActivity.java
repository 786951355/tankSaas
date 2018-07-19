package com.tanker.homemodule.view;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.adapter.CommonAdapter;
import com.tanker.basemodule.adapter.LoadMoreWrapper;
import com.tanker.basemodule.adapter.ViewHolder;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.model.home_model.FaultListModel;
import com.tanker.basemodule.model.home_model.FaultModel;
import com.tanker.homemodule.R;
import com.tanker.homemodule.contract.FaultContract;
import com.tanker.homemodule.presenter.FaultPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * author zhanglei
 * date 2018/7/16
 * description 故障列表界面
 **/
public class FaultActivity extends BaseActivity<FaultPresenter> implements FaultContract.View {
    private int page = 1;
    private int totalCount;
    private LinearLayout llLoaded;
    private ProgressBar pbLoading;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreWrapper loadMoreWrapper;
    private RecyclerView rvFault;
    private CommonAdapter commonAdapter;

    private List<FaultModel> faults;

    @Override
    protected void initView() {
        swipeRefreshLayout = findViewById(R.id.srl);
        rvFault = findViewById(R.id.rv_fault);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.text_red, R.color.text_yellow);
        swipeRefreshLayout.setRefreshing(true);
        rvFault.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.getFaults(page = 1));
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new FaultPresenter(this);
        faults = new ArrayList<>();

        commonAdapter = new CommonAdapter<FaultModel>(this, R.layout.homemodule_item_fault, faults) {
            @Override
            public void convert(ViewHolder holder, FaultModel faultModel, int position) {

            }
        };

        loadMoreWrapper = new LoadMoreWrapper(commonAdapter);
        loadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        loadMoreWrapper.setOnLoadMoreListener(holder -> {
            llLoaded = holder.getView(R.id.ll_loaded);
            pbLoading = holder.getView(R.id.pb_loading);
            pbLoading.setVisibility(View.VISIBLE);
            llLoaded.setVisibility(View.GONE);
            //上拉加载
            if (page * AppConstants.ROWS < totalCount) {
                mPresenter.getFaults(++page);
            } else if (totalCount <= 0) {
                llLoaded.setVisibility(View.GONE);
                pbLoading.setVisibility(View.GONE);
            } else {
                llLoaded.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.GONE);
            }
        });
        rvFault.setAdapter(loadMoreWrapper);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getFaults(page);
    }

    @Override
    public int getContentView() {
        return R.layout.homemodule_activity_fault;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setToolbarVisible(true).setTitle(getString(R.string.homemodule_title_fault));
    }

    public void report(View view) {
        navigationTo(FaultDetailActivity.class);
    }

    @Override
    public void refreshUI(int page, FaultListModel faultListModel) {
        if (page == 1) {//下拉刷新
            totalCount = faultListModel.getTotal();
            if (totalCount == 0) {
                showNoDataImgTip();
                llLoaded.setVisibility(View.GONE);
            } else {
                hideImgTip();
            }
            faults.clear();
            faults.addAll(faultListModel.getFaultModelList());
            loadMoreWrapper.notifyDataSetChanged();
            return;
        }

        //上拉加载更多
        if (faultListModel.getFaultModelList() != null
                && faultListModel.getFaultModelList().size() > 0
                && this.page > 1) {//加载更多刷新
            int oldPosition = faults.size() - 1;
            int insertCount = faultListModel.getFaultModelList().size();
            faults.addAll(faultListModel.getFaultModelList());
            loadMoreWrapper.notifyItemChanged(oldPosition, insertCount);
            if (this.page * AppConstants.ROWS < totalCount) {
                llLoaded.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void dismissSwipeRefresh() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
