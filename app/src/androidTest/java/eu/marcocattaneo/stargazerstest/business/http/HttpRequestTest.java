package eu.marcocattaneo.stargazerstest.business.http;

import android.support.test.runner.AndroidJUnit4;

import com.android.volley.VolleyError;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class HttpRequestTest {

    @Test
    public void testValidRepo() throws Exception {
        String url = String.format("https://api.github.com/repos/%s/%s/stargazers", "mcollina", "mosca");

        final CountDownLatch signal = new CountDownLatch(1);

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.get(url, new RequestCallback() {
            @Override
            public void onResult(int statusCode, String result) {
                assertEquals(200, statusCode);
                assertNotNull(result);
                signal.countDown();
            }

            @Override
            public void onError(VolleyError error) {
                error.printStackTrace();
            }
        });
        httpRequest.execute();

        signal.await();
    }

    @Test
    public void testNotValidRepo() throws Exception {
        String url = String.format("https://api.github.com/repos/%s/%s/stargazers", "not", "exist");

        final CountDownLatch signal = new CountDownLatch(1);

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.get(url, new RequestCallback() {
            @Override
            public void onResult(int statusCode, String result) {

            }

            @Override
            public void onError(VolleyError error) {
                assertEquals(404, error.networkResponse.statusCode);
                signal.countDown();
            }
        });
        httpRequest.execute();

        signal.await();
    }

}