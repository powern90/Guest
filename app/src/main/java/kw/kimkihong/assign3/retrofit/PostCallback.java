package kw.kimkihong.assign3.retrofit;

import kw.kimkihong.assign3.vo.PostVO;

//callback for PostVO
public interface PostCallback {
    void onSuccess(PostVO retData);
    void onError();
}
