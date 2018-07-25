package jjoseph12.nait.ca.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity
{
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.webview_b);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.realclear.com/living/2016/03/28/the_history_of_tictactoe_is_pretty_fascinating_stuff_13106.html");
    }
}
