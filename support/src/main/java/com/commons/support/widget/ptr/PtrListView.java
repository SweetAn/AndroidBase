package com.commons.support.widget.ptr;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.commons.support.R;
import com.commons.support.log.LogUtil;


/**
 * Created by qianjin on 2015/5/26.
 */
public class PtrListView extends LinearLayout {

    Context context;
    PtrClassicFrameLayout ptrFrame;
    ListView listView;
    View footer;
//    View footerLoading, footerEnd;
//    boolean isDefaultFooter;
    View vLoading;
    boolean mLastItemVisible;
    boolean isLoading = false;


    public PtrListView(final Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.m_ptr, this);
        footer = inflater.inflate(R.layout.footer_view, null);
//        isDefaultFooter = true;
//        footerLoading = footer.findViewById(R.id.tv_ptr_footer_loading);
//        footerEnd = footer.findViewById(R.id.tv_ptr_footer_end);
        footer.setVisibility(View.GONE);
        footer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });

        this.context = context;
        initViews(view);

    }

    public void setTransparentBg(){
        if(listView!=null) listView.setBackgroundResource(R.color.transparent);
    }

    private void initViews(View view){


        vLoading = view.findViewById(R.id.loading);
        vLoading.setVisibility(GONE);

        ptrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame);
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (onRefresh != null) {
//                    if(isDefaultFooter()) {
//                        footerEnd.setVisibility(GONE);
//                    }
                    onRefresh.onRefresh();
                }
            }
        });
        ptrFrame.setLastUpdateTimeRelateObject(this);
        ptrFrame.disableWhenHorizontalMove(true);
        ptrFrame.setResistance(1.7f);
        ptrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrame.setDurationToClose(200);
        ptrFrame.setDurationToCloseHeader(800);
        listView = (ListView) view.findViewById(R.id.v_listview);
        listView.addFooterView(footer);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && null != onLoadMore && mLastItemVisible) {
                    if (!isLoading) {
                        LogUtil.log("footer set visible!");
//                        if(isDefaultFooter()) {
//                            footerLoading.setVisibility(VISIBLE);
//                            footerEnd.setVisibility(GONE);
//                        } else {
                            footer.setVisibility(VISIBLE);
//                        }
                        onLoadMore.onLoadMore();
                        isLoading = true;
                    }
                }

                if (mOnScrollListener != null) {
                    mOnScrollListener.onScrollStateChanged(view,scrollState);
                }

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (null != onLoadMore) {
                    mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
                }
                if (mOnScrollListener != null){
                    mOnScrollListener.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount);
                }

            }
        });
    }

//    private boolean isDefaultFooter(){
//        if(isDefaultFooter && footerLoading!=null && footerEnd!=null)
//            return true;
//        return false;
//    }

    private AbsListView.OnScrollListener mOnScrollListener;
    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener){
        this.mOnScrollListener = onScrollListener;
    }

    private OnRefresh onRefresh ;
    public void setRefresh(OnRefresh onRefresh) {
        this.onRefresh = onRefresh;
    }

    private OnLoadMore onLoadMore ;
    public void setLoadMore(OnLoadMore onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    public void refreshComplete(){
        ptrFrame.refreshComplete();
    }

    public void loadDataComplete() {
        LogUtil.log("==footer set gone!==");
//        if(!isDefaultFooter())
            footer.setVisibility(GONE);
        refreshComplete();
        isLoading = false;
//        if(isDefaultFooter()) {
//            footerLoading.setVisibility(GONE);
//            footerEnd.setVisibility(VISIBLE);
//        }
    }

    public void showLoadingView(boolean isShow){
        if (isShow) {
            vLoading.setVisibility(VISIBLE);
            ptrFrame.setVisibility(GONE);
        } else {
            vLoading.setVisibility(GONE);
            ptrFrame.setVisibility(VISIBLE);
        }
    }



    public void addHeaderView(View header){
        if (listView != null) {
            listView.addHeaderView(header);
        }
    }

    public void setFooterDividersEnabled(boolean enable){
        if (listView != null) {
            listView.setFooterDividersEnabled(enable);
        }
    }

    public void addFooterView(View footer){
        if (listView != null) {
            listView.removeFooterView(this.footer);
            listView.addFooterView(footer);
//            isDefaultFooter = false;
        }
    }
    public void removeFooterView(){
        if (listView != null) {
            listView.removeFooterView(this.footer);
//            isDefaultFooter = false;
        }
    }

    public void setAdapter(BaseAdapter adapter){
        listView.setAdapter(adapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
        listView.setOnItemClickListener(listener);
    }

    public void setNoDivider(){
        listView.setDivider(new ColorDrawable(Color.TRANSPARENT));
    }

    public void setNoSelector(){
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }


    //定义接口
    public interface OnRefresh {
        void onRefresh();
    }
    public interface OnLoadMore {
        void onLoadMore();
    }

//    public View getEndFootView(){
//        return getEndFootView(-1);
//    }
//
//    /**
//     * 加载到最后一页底部显示一个提示的view
//     * @param viewId footview的id，缺省为一个TextView
//     * @return
//     */
//    public View getEndFootView(int viewId){
//        if(viewId==-1) {
//            TextView tvFootView = new TextView(context);
//            tvFootView.setText("\n—— 没有了 ——\n");
//            tvFootView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
//            tvFootView.setGravity(Gravity.CENTER_HORIZONTAL);
//            tvFootView.setTextColor(getResources().getColor(R.color.txt_label_gray));
//            tvFootView.setVisibility(View.GONE);
//            return tvFootView;
//        } else {
//            View footView = LayoutInflater.from(getContext()).inflate(viewId, null);
//            footView.setVisibility(View.GONE);
//            return footView;
//        }
//    }

}
