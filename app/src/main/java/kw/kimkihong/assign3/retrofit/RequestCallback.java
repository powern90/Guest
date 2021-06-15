package kw.kimkihong.assign3.retrofit;

import java.util.Map;

//callback for Map<String, Object>
public interface RequestCallback {
    void onSuccess(Map<String, Object> retData);
    void onError();
}
