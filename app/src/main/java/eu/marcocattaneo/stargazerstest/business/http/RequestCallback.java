package eu.marcocattaneo.stargazerstest.business.http;

import com.android.volley.VolleyError;

public interface RequestCallback {

    void onResult(int statusCode, String result);

    void onError(VolleyError error);

}
