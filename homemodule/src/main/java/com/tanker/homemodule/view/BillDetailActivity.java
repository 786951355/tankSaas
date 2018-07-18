package com.tanker.homemodule.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.adapter.LoadMoreWrapper;
import com.tanker.basemodule.adapter.MultiItemTypeAdapter;
import com.tanker.basemodule.adapter.ViewHolder;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.model.home_model.BillDetailListModel;
import com.tanker.basemodule.model.home_model.BillDetailModel;
import com.tanker.homemodule.R;
import com.tanker.homemodule.adapter.BillDetailAdapter;
import com.tanker.homemodule.contract.BillDetailContract;
import com.tanker.homemodule.presenter.BillDetailPresenter;

import java.util.ArrayList;
import java.util.List;

/**
* @author lwj
* @ClassName: BillDetailActivity
* @Description:  账单详情
* @date 2018/7/16 13:45
*/
public class BillDetailActivity extends BaseActivity<BillDetailPresenter> implements BillDetailContract.View {

    private static final String TAG = BillDetailActivity.class.getName();
    //刷新控件
    private SwipeRefreshLayout srl_bills;
    //账单列表的RecyclerView控件
    private RecyclerView rv_bills;
    //账单列表数据集合
    private List<BillDetailModel> datas= new ArrayList<>();
    //账单列表适配器
    private BillDetailAdapter adapter;
    private int page = 1;
    private int totalCount;
    private LinearLayout ll_loaded;
    private ProgressBar pb_loading;
    private LoadMoreWrapper mLoadMoreWrapper;

    @Override
    protected void initView() {
        //刷新控件
        srl_bills = findViewById(R.id.srl_bills);
        srl_bills.setColorSchemeResources(R.color.colorAccent, R.color.text_red, R.color.text_yellow);
        srl_bills.setRefreshing(true);
        srl_bills.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getBillDetailInfoList(1);
            }
        });
        //账单列表的RecyclerView控件
        rv_bills = findViewById(R.id.rv_bills);
        final LinearLayoutManager lineLayoutManager = new LinearLayoutManager(mContext);
        rv_bills.setLayoutManager(lineLayoutManager);

        //账单列表适配器
        adapter = new BillDetailAdapter(mContext, R.layout.homemodul_recycle_bill_item, datas);
        mLoadMoreWrapper = new LoadMoreWrapper(adapter);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested(ViewHolder holder) {
                ll_loaded = holder.getView(R.id.ll_loaded);
                pb_loading = holder.getView(R.id.pb_loading);
                pb_loading.setVisibility(View.VISIBLE);
                ll_loaded.setVisibility(View.GONE);
                //上拉加载
                if (page * AppConstants.ROWS < totalCount) {
                    mPresenter.getBillDetailInfoList( ++page);
                } else if (totalCount <= 0) {
                    ll_loaded.setVisibility(View.GONE);
                    pb_loading.setVisibility(View.GONE);
                } else {
                    ll_loaded.setVisibility(View.VISIBLE);
                    pb_loading.setVisibility(View.GONE);
                }
            }
        });
        rv_bills.setAdapter(mLoadMoreWrapper);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    @Override
    public int getContentView() {
        return R.layout.homemodule_acticity_bill;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setTitle("2018-07账单");
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new BillDetailPresenter(this);
        mPresenter.getBillDetailInfoList(page);
    }

    @Override
    public void getBillDetailInfoList(int page,BillDetailListModel model) {
        if (page == 1) {
            totalCount = Integer.valueOf(model.getTotal());
            if (totalCount == 0) {
                showNoDataImgTip();
                ll_loaded.setVisibility(View.GONE);
            } else {
                hideImgTip();
            }
            datas.clear();
            datas.addAll(model.getBillDetailInfoList());
            mLoadMoreWrapper.notifyDataSetChanged();
        }

        if (model.getBillDetailInfoList() != null
                && model.getBillDetailInfoList().size() > 0
                && this.page > 1) {//加载更多
            int oldPosition = datas.size() - 1;
            int insertCount = model.getBillDetailInfoList().size();
            datas.addAll(model.getBillDetailInfoList());
            mLoadMoreWrapper.notifyItemRangeInserted(oldPosition, insertCount);
            if (this.page * AppConstants.ROWS < totalCount) {
                ll_loaded.setVisibility(View.VISIBLE);
                pb_loading.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void dismissSwipeRefresh() {
        if (srl_bills.isRefreshing()) {
            srl_bills.setRefreshing(false);
        }
    }
}