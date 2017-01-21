package eu.marcocattaneo.stargazerstest.business.http;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

import eu.marcocattaneo.stargazerstest.ui.general.BaseActivity;

public class HttpRequest {

    public static final String TAG = HttpRequest.class.getSimpleName();

    private RequestQueue mQueue;

    public HttpRequest(BaseActivity baseActivity) {
        mQueue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()));
    }

    /**
     * Make GET request
     * @param url
     * @param callback
     */
    public void get(String url, final RequestCallback callback) {

        CustomStringRequest stringRequest = new CustomStringRequest(Request.Method.GET, url, new CustomStringRequest.OnSuccess() {
            @Override
            public void onResponse(int status, String response) {
                callback.onResult(status, response);
            }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.onError(error);
                }
            }
        );

        // prevent http cache
        mQueue.add(stringRequest);
    }

    public void execute() {
        mQueue.start();
    }

    /**
     * Custom Volley Request to manage result code and response
     */
    private static class CustomStringRequest extends Request<String> {

        private int statusCode = -1;

        private String mRequestBody = null;

        private OnSuccess successCallback;

        public CustomStringRequest(int method, String url, OnSuccess onSuccess,  Response.ErrorListener listener) {
            super(method, url, listener);

            this.successCallback = onSuccess;
        }

        @Override
        protected void deliverResponse(String response) {
            successCallback.onResponse(statusCode, response);
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            try {
                String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                statusCode = response.statusCode;
                Log.d(TAG, "Response: " + json);
                Log.d(TAG, "Response status: " + statusCode);
                return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                Log.d(TAG, "Response err: " + e.getMessage());
                return Response.error(new ParseError(e));
            } catch (JsonSyntaxException e) {
                Log.d(TAG, "Response err: " + e.getMessage());
                return Response.error(new ParseError(e));
            }
        }

        @Override
        public byte[] getBody() throws AuthFailureError {
            try {
                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
            } catch (UnsupportedEncodingException uee) {
                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                return null;
            }
        }

        public interface OnSuccess {

            void onResponse(int status, String response);

        }
    }

}