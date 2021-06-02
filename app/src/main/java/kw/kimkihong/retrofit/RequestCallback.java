package kw.kimkihong.retrofit;

import java.util.Map;

public interface RequestCallback {
    void onSuccess(Map<String, Object> retData);
    void onError();
}
