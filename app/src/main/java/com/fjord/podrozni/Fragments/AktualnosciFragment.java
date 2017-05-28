package com.fjord.podrozni.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fjord.podrozni.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AktualnosciFragment extends Fragment {


    public AktualnosciFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aktualnosci, container, false);

        final WebView webView = (WebView) view.findViewById(R.id.webview);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.setVisibility(View.VISIBLE);
            }
        });
        webView.loadUrl("http://festiwalpodrozni.pl/news/");

        return view;

    }

}
