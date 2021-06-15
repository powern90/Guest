package kw.kimkihong.assign3.retrofit;

import kw.kimkihong.assign3.vo.ReservationVO;

import java.util.List;

//callback for List<ReservationVO>
public interface rListCallback {
    void onSuccess(List<ReservationVO> retData);
    void onError();
}
