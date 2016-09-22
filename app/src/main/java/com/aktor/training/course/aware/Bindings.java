package com.aktor.training.course.aware;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONObject;

/**
 * Created by aktor on 21/09/16.
 */

public class Bindings {

    private void connect(WebView view){
        view.addJavascriptInterface(this,"ABindings");
    //var x =ABindings.doThings();
        // ABindingsPiu.doOther  = function(x){
        //  ABindings.doOther(JSON.stringify(x));
        // }
      // var y = ABindings.doOther({zyz:3});
    }

    @JavascriptInterface
    public boolean doOther(String xxx){
        //JSONObject o = new JSONObject(xxx);

        return true;
    }

    @JavascriptInterface
    public boolean doThings(){
        Log.e("CIAO", "doThings: ");
        return true;
    }
}
