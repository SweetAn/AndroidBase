package com.commons.support.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.commons.support.R;
import com.commons.support.log.LogUtil;
import com.commons.support.util.ConnectUtil;

import java.util.Map;

/**
 * Created by qianjin on 2015/12/21.
 */
public class MWebView extends FrameLayout {

    public static final String HTTP_URI = "http://";
    public static final String HTTPS_URI = "https://";

    WebView webView;
    LoadingView loadingView;
    TextView tvError;
    Context context;
    TitleBar titleBar;
    boolean isError;

    public MWebView(Context context) {
        super(context);
        initView(context);
    }

    public MWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);

    }

    public MWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.web_view, this);
        loadingView = (LoadingView) view.findViewById(R.id.v_loading);
        webView = (WebView) view.findViewById(R.id.web_view);
        tvError = (TextView) view.findViewById(R.id.tv_error);
        //tvError.setText(Html.fromHtml("网页无法打开" + "<font color='#42bdfe'><br/>点击重试</font>"));

        tvError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goneAllView();
                loadingView.setVisibility(VISIBLE);
                isError = false;
                reload();
            }
        });


        initWebView();
    }

    public void setTitleBar(TitleBar titleBar) {
        this.titleBar = titleBar;
    }

    public WebView getWebView(){
        return webView;
    }

    private void initWebView() {

        if (isInEditMode()) return;
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setDomStorageEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setDisplayZoomControls(false);

        webView.setVisibility(View.INVISIBLE);
        webView.setEnabled(true);
        webView.requestFocus();
        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (titleBar != null) {
                    if (isError) {
                        titleBar.setTitle("加载失败");
                    } else {
                        titleBar.setTitle(title);
                    }
                }
                LogUtil.log("onReceivedTitle");
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100 && !isError) {
                    loadSuccessView();
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Ignore SSL certificate errors
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                LogUtil.log("onReceivedError:" + errorCode + " description:" + description);
                isError = true;
                setErrorView();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.log("shouldOverrideUrlLoading loadUrl");
                if (url.startsWith(HTTP_URI) || url.startsWith(HTTPS_URI)) {
                    //TODO 处理header的问题
                    view.loadUrl(url);
                }
                if (url.startsWith("tel:") || url.startsWith("sms:") || url.startsWith("mailto:")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                }
                if (webViewClientListener != null) {
                    webViewClientListener.shouldOverrideUrlLoading(view, url);
                }
                return true;
            }
        });
        if (!ConnectUtil.isConnected(context)) {
            setErrorView();
        }

    }

    public void setErrorView() {
        errorView();
        if (titleBar != null) {
            titleBar.setTitle("加载失败");
        }
    }


    private void goneAllView() {
        loadingView.setVisibility(GONE);
        webView.setVisibility(GONE);
        tvError.setVisibility(GONE);
    }

    private void loadingView() {
        goneAllView();
        loadingView.setVisibility(VISIBLE);
    }

    private void errorView() {
        goneAllView();
        tvError.setVisibility(VISIBLE);
    }

    private void loadSuccessView() {
        goneAllView();
        webView.setVisibility(VISIBLE);
    }

    public void loadUrl(String url) {
        loadingView();
        webView.loadUrl(url);
    }

    public void loadUrl(String url, Map<String, String> headers) {
        loadingView();
        webView.loadUrl(url, headers);
    }

    public void reload() {
        webView.reload();
    }

    public void goBack() {
        webView.goBack();
    }

    public boolean canGoBack() {
        return webView.canGoBack();
    }

    private WebViewClientListener webViewClientListener;

    public void setWebViewClientListener(WebViewClientListener l) {
        webViewClientListener = l;
    }

    public interface WebViewClientListener {
        public void shouldOverrideUrlLoading(WebView view, String url);
    }


}
