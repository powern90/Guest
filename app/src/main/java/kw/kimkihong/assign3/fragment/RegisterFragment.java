package kw.kimkihong.assign3.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.Manifest;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import kw.kimkihong.assign3.R;
import kw.kimkihong.retrofit.Request;
import kw.kimkihong.retrofit.RequestCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;


public class RegisterFragment extends Fragment {
    View view;
    String path;
    String product_Image_name;

    Uri originalUri = null;

    public static final int PICK_IMAGE_REQUEST=42;

    public static boolean AskPermissions(Context context, String[] permissions){
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    Bitmap mBitmap;
                    ImageView aaa = view.findViewById(R.id.qwerqwer);
                    try {
                        ParcelFileDescriptor pfd = requireActivity().getApplicationContext().getContentResolver().openFileDescriptor(uri, "r");
                        mBitmap = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                        aaa.setImageBitmap(mBitmap);
                        String thePath = "no-path-found";
                        String[] filePathColumn = {MediaStore.Images.Media.DISPLAY_NAME};
                        Cursor cursor = requireActivity().getApplicationContext().getContentResolver().query(uri, filePathColumn, null, null, null);
                        if(cursor.moveToFirst()){
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            thePath = cursor.getString(columnIndex);
                        }
                        cursor.close();
                        File imageFile = new File(thePath);
//                        String ext = thePath.substring(thePath.lastIndexOf(".") + 1);
                        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
//                        String name = UUID.randomUUID().toString() + "." + ext;
                        MultipartBody.Part partImage = MultipartBody.Part.createFormData("file", imageFile.getName(), reqBody);
                        Request.getInstance().uploadImg(partImage, new RequestCallback() {
                            @Override
                            public void onSuccess(Map<String, Object> retData) {

                            }

                            @Override
                            public void onError() {

                            }
                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        Button aaa = view.findViewById(R.id.asdfasdf);
        aaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });
        return view;
    }

//    //이미지 가져오기
//    public void getPickImageChooserIntent() {
//        List<Intent> allIntents = new ArrayList<>();
//        PackageManager packageManager = getActivity().getPackageManager();
//        ArrayList<Image> images = new ArrayList<>();
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        startActivityForResult(Intent.createChooser(intent, "이미지 다중 선택"), 1);
//
//    }
}