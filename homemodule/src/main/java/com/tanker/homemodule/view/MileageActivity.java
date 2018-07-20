package com.tanker.homemodule.view;

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
import com.tanker.basemodule.model.home_model.MileageListModel;
import com.tanker.basemodule.model.home_model.MileageModel;
import com.tanker.homemodule.R;
import com.tanker.homemodule.contract.MileageContract;
import com.tanker.homemodule.presenter.MileagePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * author zhanglei
 * date 2018/7/16
 * description 里程列表界面
 **/
public class MileageActivity extends BaseActivity<MileagePresenter> implements MileageContract.View {
    private int page = 1;
    private int totalCount;
    private LinearLayout llLoaded;
    private ProgressBar pbLoading;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreWrapper loadMoreWrapper;
    private RecyclerView rvMileage;
    private CommonAdapter commonAdapter;

    private List<MileageModel> mileages;

    @Override
    protected void initView() {
        swipeRefreshLayout = findViewById(R.id.srl);
        rvMileage = findViewById(R.id.rv_mileage);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.text_red, R.color.text_yellow);
        swipeRefreshLayout.setRefreshing(true);
        rvMileage.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.getMileages(page = 1));
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new MileagePresenter(this);
        mileages = new ArrayList<>();

        commonAdapter = new CommonAdapter<MileageModel>(this, R.layout.homemodule_item_mileage, mileages) {
            @Override
            public void convert(ViewHolder holder, MileageModel mileageModel, int position) {

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
                mPresenter.getMileages(++page);
            } else if (totalCount <= 0) {
                llLoaded.setVisibility(View.GONE);
                pbLoading.setVisibility(View.GONE);
            } else {
                llLoaded.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.GONE);
            }
        });
        rvMileage.setAdapter(loadMoreWrapper);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getMileages(page);
    }

    @Override
    public int getContentView() {
        return R.layout.homemodule_activity_mileage;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setToolbarVisible(true).setTitle(getString(R.string.homemodule_title_mileage));
    }

    public void report(View view) {
        navigationTo(MileageAddActivity.class);
    }

    @Override
    public void refreshUI(int page, MileageListModel mileageListModel) {
        if (page == 1) {//下拉刷新
            totalCount = mileageListModel.getTotal();
            if (totalCount == 0) {
                showNoDataImgTip();
                llLoaded.setVisibility(View.GONE);
            } else {
                hideImgTip();
            }
            mileages.clear();
            mileages.addAll(mileageListModel.getMileageModelList());
            loadMoreWrapper.notifyDataSetChanged();
            return;
        }

        //上拉加载更多
        if (mileageListModel.getMileageModelList() != null
                && mileageListModel.getMileageModelList().size() > 0
                && this.page > 1) {//加载更多刷新
            int oldPosition = mileages.size() - 1;
            int insertCount = mileageListModel.getMileageModelList().size();
            mileages.addAll(mileageListModel.getMileageModelList());
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

    public void query(View view) {

    }
}
