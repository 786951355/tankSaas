package com.tanker.homemodule.view;

import android.content.Intent;
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
import com.tanker.basemodule.model.home_model.BillListModel;
import com.tanker.basemodule.model.home_model.BillModel;
import com.tanker.homemodule.R;
import com.tanker.homemodule.adapter.BillAdapter;
import com.tanker.homemodule.constants.HomeConstants;
import com.tanker.homemodule.contract.BillContract;
import com.tanker.homemodule.presenter.BillPresenter;

import java.util.ArrayList;
import java.util.List;

/**
* @author lwj
* @ClassName: BillActivity
* @Description:  账单列表
* @date 2018/7/16 13:45
*/
public class BillActivity extends BaseActivity<BillPresenter> implements BillContract.View {

    private static final String TAG = BillActivity.class.getName();
    //刷新控件
    private SwipeRefreshLayout srl_bills;
    //账单列表的RecyclerView控件
    private RecyclerView rv_bills;
    //账单列表数据集合
    private List<BillModel> datas= new ArrayList<>();
    //账单列表适配器
    private BillAdapter adapter;
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
                mPresenter.getBillInfoList(1);
            }
        });
        //账单列表的RecyclerView控件
        rv_bills = findViewById(R.id.rv_bills);
        final LinearLayoutManager lineLayoutManager = new LinearLayoutManager(mContext);
        rv_bills.setLayoutManager(lineLayoutManager);

        //账单列表适配器
        adapter = new BillAdapter(mContext, R.layout.homemodul_recycle_bill_item, datas);
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
                    mPresenter.getBillInfoList( ++page);
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
                Intent intent = new Intent(mContext, BillDetailActivity.class);
                intent.putExtra(HomeConstants.ORDER_NO,datas.get(position).getBillTime());
                navigationTo(intent);
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
        rToolbar.setTitle(getString(R.string.homemodule_bills_title));
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new BillPresenter(this);
        mPresenter.getBillInfoList(page);
    }

    @Override
    public void getBillInfoList(int page,BillListModel model) {
        if (page == 1) {
            totalCount = Integer.valueOf(model.getTotal());
            if (totalCount == 0) {
                showNoDataImgTip();
                ll_loaded.setVisibility(View.GONE);
            } else {
                hideImgTip();
            }
            datas.clear();
            datas.addAll(model.getBillInfoList());
            mLoadMoreWrapper.notifyDataSetChanged();
        }

        if (model.getBillInfoList() != null
                && model.getBillInfoList().size() > 0
                && this.page > 1) {//加载更多
            int oldPosition = datas.size() - 1;
            int insertCount = model.getBillInfoList().size();
            datas.addAll(model.getBillInfoList());
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
