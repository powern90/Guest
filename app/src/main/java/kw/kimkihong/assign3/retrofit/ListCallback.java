package kw.kimkihong.assign3.retrofit;

import kw.kimkihong.assign3.vo.PostVO;

import java.util.List;

//callback function for List<PostVO>
public interface ListCallback {
    void onSuccess(List<PostVO> retData);
    void onError();
}
