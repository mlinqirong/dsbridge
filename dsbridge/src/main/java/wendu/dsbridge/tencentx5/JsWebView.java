package wendu.dsbridge.tencentx5;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.util.Map;

import wendu.dsbridge.OnReturnValue;
import wendu.dsbridge.view.NumberProgressBar;


/**
 * Created by huangdiudiu on 2018/4/11.
 */

public class JsWebView extends FrameLayout {
    private static boolean isDebug = false;
    static final String TAG = JsWebView.class.getSimpleName();


    private DWebView mWebView;
    private NumberProgressBar mNumberProgressBar;

    public JsWebView(Context context) {
        super(context);
        init(context, null);
    }


    public JsWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public JsWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public JsWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public synchronized <T> void callHandler(String method, Object[] args, final OnReturnValue<T> handler) {
        mWebView.callHandler(method, args, handler);
    }


    /**
     * set a listener for javascript closing the current activity.
     */
    public void setJavascriptCloseWindowListener(DWebView.JavascriptCloseWindowListener listener) {
        mWebView.setJavascriptCloseWindowListener(listener);
    }

    public void callHandler(String method, Object[] args) {
        callHandler(method, args, null);
    }

    public <T> void callHandler(String method, OnReturnValue<T> handler) {
        callHandler(method, null, handler);
    }

    /**
     * Set debug mode. if in debug mode, some errors will be prompted by a dialog
     * and the exception caused by the native handlers will not be captured.
     *
     * @param enabled
     */
    public static void setWebContentsDebuggingEnabled(boolean enabled) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(enabled);
        }
        isDebug = enabled;
    }

    public void evaluateJavascript(final String script) {
        mWebView.evaluateJavascript(script);
    }

    /**
     * remove the javascript object with supplied namespace.
     *
     * @param namespace
     */
    public void removeJavascriptObject(String namespace) {
        if (namespace == null) {
            namespace = "";
        }
        mWebView.removeJavascriptObject(namespace);

    }

    /**
     * Test whether the handler exist in javascript
     *
     * @param handlerName
     * @param existCallback
     */
    public void hasJavascriptMethod(String handlerName, OnReturnValue<Boolean> existCallback) {
        callHandler("_hasJavascriptMethod", new Object[]{handlerName}, existCallback);
    }

    public WebSettings getSettings() {
        return mWebView.getSettings();
    }

    private void init(Context context, AttributeSet attrs) {
//        setOrientation(LinearLayout.VERTICAL);

        // 初始化webview
        if (mWebView == null) {
            mWebView = new DWebView(context);
        }

        mWebView.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });


        addView(mWebView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public void goBack() {
        mWebView.goBack();
    }

    public void destroy() {
        mWebView.destroy();
    }


    public void loadUrl(String Baseurl, String url) {
        if (Baseurl == null) {
            mWebView.loadUrl(url);
        } else {
            mWebView.loadUrl(Baseurl + url);
        }

    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public DWebView getWebView() {
        return mWebView;
    }

    public void loadUrl(String Baseurl, String url, Map<String, String> additionalHttpHeaders) {
        if (Baseurl == null) {
            mWebView.loadUrl(url, additionalHttpHeaders);
        } else {
            mWebView.loadUrl(Baseurl + url, additionalHttpHeaders);
        }

    }

    public void reload() {
        mWebView.reload();
    }

    public void setWebViewClient(CustomWebViewClient client) {
        mWebView.setWebViewClient(client);
    }

    public void setWebChromeClient(CustomWebChromeClient chromeClient) {
        if(chromeClient!=null) {
            if (mNumberProgressBar == null&&chromeClient.getProgressBar()==null) {
                mNumberProgressBar = new NumberProgressBar(getContext());
                mNumberProgressBar.setVisibility(View.GONE);
                addView(mNumberProgressBar, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            if (chromeClient.getProgressBar() == null) {
                chromeClient.setProgressBar(mNumberProgressBar);
            }
        }
        mWebView.setWebChromeClient(chromeClient);
    }

    public void addJavascriptObject(Object object, String namespace) {
        mWebView.addJavascriptObject(object, namespace);
    }
}
