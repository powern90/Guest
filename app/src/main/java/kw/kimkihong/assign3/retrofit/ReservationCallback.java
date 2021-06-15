package kw.kimkihong.assign3.retrofit;

import kw.kimkihong.assign3.vo.ReservationVO;

//callback for ReservationVO
public interface ReservationCallback {
    void onSuccess(ReservationVO retData);
    void onError();
}
