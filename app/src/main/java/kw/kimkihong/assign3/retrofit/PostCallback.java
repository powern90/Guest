package kw.kimkihong.assign3.retrofit;

import kw.kimkihong.assign3.vo.PostVO;

import java.util.List;


public interface PostCallback {
    void onSuccess(List<PostVO> retData);
    void onError();
}
