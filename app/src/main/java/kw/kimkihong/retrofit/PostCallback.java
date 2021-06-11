package kw.kimkihong.retrofit;

import kw.kimkihong.vo.PostVO;

import java.util.List;


public interface PostCallback {
    void onSuccess(List<PostVO> retData);
    void onError();
}
