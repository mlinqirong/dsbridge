package wendu.dsbridge.tencentx5;

import android.view.View;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import wendu.dsbridge.view.NumberProgressBar;


/**
 * Class description
 *
 * @author YEZHENNAN220
 * @date 2016-07-08 14:05
 */

public class CustomWebChromeClient extends WebChromeClient {

    private NumberProgressBar mProgressBar;
    private final static int DEF = 95;
    private boolean isshowmProgressBar;
    public CustomWebChromeClient(NumberProgressBar progressBar,boolean isshowmProgressBar) {
        this.mProgressBar = progressBar;
        this.isshowmProgressBar=isshowmProgressBar;
    }

    public CustomWebChromeClient() {
        super();
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if(mProgressBar!=null) {
            if (newProgress >= DEF) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                if (mProgressBar.getVisibility() == View.GONE && isshowmProgressBar == true) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }

                mProgressBar.setProgress(newProgress);
            }
        }
        super.onProgressChanged(view, newProgress);
    }

    public void setProgressBar(NumberProgressBar mProgressBar) {
        this.mProgressBar = mProgressBar;
    }

    public NumberProgressBar getProgressBar() {
        return mProgressBar;
    }
}
