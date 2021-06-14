package kw.kimkihong.assign3.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import kw.kimkihong.assign3.R;
import kw.kimkihong.assign3.retrofit.Request;
import kw.kimkihong.assign3.retrofit.RequestCallback;
import kw.kimkihong.assign3.util.DateActivity;
import kw.kimkihong.assign3.util.TimeActivity;
import kw.kimkihong.assign3.util.WebViewActivity;
import kw.kimkihong.assign3.vo.PostVO;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;


public class AddFragment extends Fragment {
    private View view;

    private ImageView imageInput;

    private TextView imageMsg;
    private TextView nameInput;
    private TextView priceInput;
    private TextView countInput;
    private EditText contentInput;
    private TextView startDateInput;
    private TextView endDateInput;
    private TextView startTimeInput;
    private TextView endTimeInput;
    private TextView addressInput_1;
    private TextView addressInput_2;

    private Button startDateSelect;
    private Button endDateSelect;
    private Button startTimeSelect;
    private Button endTimeSelect;
    private Button searchAddress;
    private Button submit;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private List<String> permissionsToRequest;
    private List<String> permissions;

    private Bitmap mBitmap;
    private String imgFileName;

    private boolean isSubmitted;

    private Context mContext;
    private Activity mActivity;

    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_SELECT_REQUEST = 1;
    private final static int START_DATE_SELECT_REQUEST = 500;
    private final static int END_DATE_SELECT_REQUEST = 550;
    private final static int START_TIME_SELECT_REQUEST = 600;
    private final static int END_TIME_SELECT_REQUEST = 660;
    private static final int SEARCH_ADDRESS_REQUEST = 700;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_add, container, false);

        this.imageInput = this.view.findViewById(R.id.addImageInput);
        this.imageMsg = this.view.findViewById(R.id.addImageMsg);
        this.nameInput = this.view.findViewById(R.id.addNameInput);
        this.priceInput = this.view.findViewById(R.id.addPriceInput);
        this.countInput = this.view.findViewById(R.id.addCountInput);
        this.contentInput = this.view.findViewById(R.id.addContentInput);
        this.startDateInput = this.view.findViewById(R.id.addStartDateInput);
        this.endDateInput = this.view.findViewById(R.id.addEndDateInput);
        this.startTimeInput = this.view.findViewById(R.id.addStartTimeInput);
        this.endTimeInput = this.view.findViewById(R.id.addEndTimeInput);
        this.addressInput_1 = this.view.findViewById(R.id.addAddressInput_1);
        this.addressInput_2 = this.view.findViewById(R.id.addAddressInput_2);
        this.startDateSelect = this.view.findViewById(R.id.addStartDateSelect);
        this.endDateSelect = this.view.findViewById(R.id.addEndDateSelect);
        this.startTimeSelect = this.view.findViewById(R.id.addStartTimeSelect);
        this.endTimeSelect = this.view.findViewById(R.id.addEndTimeSelect);
        this.searchAddress = this.view.findViewById(R.id.addSearchAddress);
        this.submit = this.view.findViewById(R.id.addSubmit);

        this.imageInput.setOnClickListener(new imageOnClickListener());
        this.startDateSelect.setOnClickListener(new startDateOnClickListener());
        this.endDateSelect.setOnClickListener(new endDateOnClickListener());
        this.startTimeSelect.setOnClickListener(new startTimeOnClickListener());
        this.endTimeSelect.setOnClickListener(new endTimeOnClickListener());
        this.searchAddress.setOnClickListener(new addressOnClickListener());
        this.submit.setOnClickListener(new submitOnClickListener());

        this.permissions = new ArrayList<>();
        this.isSubmitted = false;

        askPermission();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            SimpleDateFormat format = new SimpleDateFormat();
            assert data != null;
            switch (requestCode) {
                case IMAGE_SELECT_REQUEST:
                    if (data.getData() == null) {
                        Toast.makeText(getActivity(), "이미지를 선택하세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            UUID uuid = UUID.randomUUID();
                            this.imgFileName = uuid.toString() + ".png";
                            this.mBitmap = BitmapFactory.decodeStream(this.mActivity.getContentResolver().openInputStream(data.getData()));
                            this.imageInput.setImageBitmap(this.mBitmap);
                            this.imageMsg.setVisibility(View.GONE);
                            this.imageInput.setMaxHeight(200);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case START_DATE_SELECT_REQUEST:
                    this.startDate = LocalDate.of(data.getIntExtra("mYear", 0), (data.getIntExtra("mMonth", 0)+1), data.getIntExtra("mDay", 0));
                    String start_date = this.startDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
                    this.startDateInput.setText(start_date);
                    break;
                case END_DATE_SELECT_REQUEST:
                    this.endDate = LocalDate.of(data.getIntExtra("mYear", 0), (data.getIntExtra("mMonth", 0)+1), data.getIntExtra("mDay", 0));
                    String end_date = this.endDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
                    this.endDateInput.setText(end_date);
                    break;
                case START_TIME_SELECT_REQUEST:
                    this.startTime = LocalTime.of(data.getIntExtra("mHour", 0), data.getIntExtra("mMinute", 0));
                    String start_time = this.startTime.format(DateTimeFormatter.ofPattern("HH시 mm분"));
                    this.startTimeInput.setText(start_time);
                    break;
                case END_TIME_SELECT_REQUEST:
                    this.endTime = LocalTime.of(data.getIntExtra("mHour", 0), data.getIntExtra("mMinute", 0));
                    String end_time = this.endTime.format(DateTimeFormatter.ofPattern("HH시 mm분"));
                    this.endTimeInput.setText(end_time);
                    break;
                case SEARCH_ADDRESS_REQUEST:
                    String address = data.getStringExtra("data");
                    this.addressInput_1.setText(address);
                    break;
                default:
            }
        }
    }

    class imageOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            startActivityForResult(Intent.createChooser(intent, "이미지 선택"), IMAGE_SELECT_REQUEST);
        }
    }

    class startDateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), DateActivity.class);
            startActivityForResult(intent, START_DATE_SELECT_REQUEST);
        }
    }

    class endDateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), DateActivity.class);
            startActivityForResult(intent, END_DATE_SELECT_REQUEST);
        }
    }

    class startTimeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), TimeActivity.class);
            startActivityForResult(intent, START_TIME_SELECT_REQUEST);
        }
    }

    class endTimeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), TimeActivity.class);
            startActivityForResult(intent, END_TIME_SELECT_REQUEST);
        }
    }

    class addressOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            startActivityForResult(intent, SEARCH_ADDRESS_REQUEST);
        }
    }

    class submitOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(isSubmitted) {
                Toast.makeText(mActivity, "이미 등록되었습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(mBitmap == null) {
                Toast.makeText(getActivity(), "이미지를 선택해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(nameInput.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "상호명을 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(priceInput.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "가격을 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(countInput.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "인원수를 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(contentInput.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "설명을 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(startDateInput.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "시작일을 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(endDateInput.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "종료일을 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(startTimeInput.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "체크인 시간을 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(endTimeInput.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "체크아웃 시간을 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(startTimeInput.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "체크인 시간을 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(addressInput_1.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "주소를 입력해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            PostVO reqData = new PostVO();
            reqData.setName(nameInput.getText().toString().trim());
            reqData.setPrice(priceInput.getText().toString().trim());
            reqData.setContent(contentInput.getText().toString().trim());
            reqData.setCount(Integer.parseInt(countInput.getText().toString().trim()));
            reqData.setStartDate(startDate);
            reqData.setEndDate(endDate);
            reqData.setEnterTime(startTime);
            reqData.setExitTime(endTime);
            reqData.setAddress(addressInput_1.getText().toString().trim()+" "+addressInput_2.getText().toString().trim());
            reqData.setImg(imgFileName);
            multipartImageUpload();
            Request.getInstance().addPost(reqData, new RequestCallback() {
                @Override
                public void onSuccess(Map<String, Object> retData) {
                    Toast.makeText(mActivity, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                    isSubmitted = true;
                }

                @Override
                public void onError() {
                    Toast.makeText(mActivity, "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void askPermission() {
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);
        if (permissionsToRequest.size() > 0)
            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
    }

    private List<String> findUnAskedPermissions(List<String> wanted) {
        List<String> result = new ArrayList<>();
        for (String perm : wanted) {
            if (hasPermissions(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermissions(String permissions) {
        return (ContextCompat.checkSelfPermission(mActivity, permissions) != PackageManager.PERMISSION_GRANTED);
    }



    private void multipartImageUpload() {
        try {
            File filesDir = mContext.getFilesDir();
            File file = new File(filesDir, this.imgFileName);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");

            Request.getInstance().uploadImg(body, name, new RequestCallback() {
                @Override
                public void onSuccess(Map<String, Object> retData) {}

                @Override
                public void onError() {
                    Toast.makeText(getContext(), "req fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}