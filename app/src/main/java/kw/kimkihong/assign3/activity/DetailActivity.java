package kw.kimkihong.assign3.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.Glide;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import kw.kimkihong.assign3.R;
import kw.kimkihong.assign3.retrofit.PostCallback;
import kw.kimkihong.assign3.retrofit.Request;
import kw.kimkihong.assign3.util.DateActivity;
import kw.kimkihong.assign3.vo.PostVO;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DetailActivity extends AppCompatActivity {
    //Declare Activity variable
    private ImageView image;

    private TextView name;
    private TextView price;
    private TextView content;
    private TextView startDate;
    private TextView endDate;
    private TextView enterTime;
    private TextView exitTime;
    private TextView address;

    private Button reservation;

    //Declare special variable
    private FragmentManager fm;
    private MapFragment mapFragment;
    private int postId;
    private PostVO post;

    //Set code
    private final static int START_DATE_SELECT_REQUEST = 500;
    private final static int END_DATE_SELECT_REQUEST = 550;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //get intent data
        Intent intent = getIntent();
        this.postId = intent.getIntExtra("postID", 0);

        //set activity variable
        this.image = findViewById(R.id.detailImage);
        this.name = findViewById(R.id.detailName);
        this.price = findViewById(R.id.detailPrice);
        this.content = findViewById(R.id.detailContent);
        this.startDate = findViewById(R.id.detailStartDate);
        this.endDate = findViewById(R.id.detailEndDate);
        this.enterTime = findViewById(R.id.detailEnterTime);
        this.exitTime = findViewById(R.id.detailExitTime);
        this.address = findViewById(R.id.detailAddress);
        this.reservation = findViewById(R.id.detailReservation);

        //set onclick listener
        this.startDate.setOnClickListener(new startDateOnClickListener());
        this.endDate.setOnClickListener(new endDateOnClickListener());
        this.reservation.setOnClickListener(new reservationOnClickListener());

        //set Naver Map fragment
        this.fm = getSupportFragmentManager();
        this.mapFragment = (MapFragment) this.fm.findFragmentById(R.id.map);
        if (this.mapFragment == null) {
            this.mapFragment = MapFragment.newInstance();
            this.fm.beginTransaction().add(R.id.map, this.mapFragment).commit();
        }
        Request.getInstance().getPost(this.postId, new getPostCallback());
    }

    //declare activity result for select date
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && post != null && data != null) {
            switch (requestCode) {
                //when date is selected
                case START_DATE_SELECT_REQUEST:
                    //format date and set for view
                    post.setStartDate(LocalDate.of(data.getIntExtra("mYear", 0), (data.getIntExtra("mMonth", 0)+1), data.getIntExtra("mDay", 0)));
                    this.startDate.setText(post.getStartDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
                    return;
                case END_DATE_SELECT_REQUEST:
                    post.setEndDate(LocalDate.of(data.getIntExtra("mYear", 0), (data.getIntExtra("mMonth", 0)+1), data.getIntExtra("mDay", 0)));
                    this.endDate.setText(post.getEndDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
                    return;
                default:
            }
        }
    }

    //declare get post API callback
    class getPostCallback implements PostCallback {
        @Override
        public void onSuccess(PostVO retData) {
            //set each data to view
            post = retData;
            Glide.with(getApplicationContext()).load("http://10.0.2.2/public/images/" + post.getImg())
                    .error(R.drawable.no_image)
                    .into(image);
            name.setText(post.getName());
            price.setText(post.getPrice());
            content.setText(post.getContent());
            startDate.setText(post.getStartDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
            endDate.setText(post.getEndDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
            enterTime.setText(post.getEnterTime().format(DateTimeFormatter.ofPattern("HH시 mm분")));
            exitTime.setText(post.getExitTime().format(DateTimeFormatter.ofPattern("HH시 mm분")));
            address.setText(post.getAddress());
            mapFragment.getMapAsync(new onMapReady());
        }

        //when got error for API show toast message
        @Override
        public void onError() {
            Toast.makeText(getApplicationContext(), "로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //declare on map ready for Naver API
    class onMapReady implements OnMapReadyCallback {
        @Override
        public void onMapReady(@NonNull @NotNull NaverMap naverMap) {
            //setup map
            naverMap.setMapType(NaverMap.MapType.Basic);
            naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);
            naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true);
            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(Float.parseFloat(post.getLatitude()), Float.parseFloat(post.getAltitude())));
            //setup marker
            Marker marker = new Marker();
            marker.setPosition(new LatLng(Float.parseFloat(post.getLatitude()), Float.parseFloat(post.getAltitude())));
            naverMap.moveCamera(cameraUpdate);
            marker.setMap(naverMap);
        }
    }

    //declare start date on click listener
    class startDateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //start select date activity and set start and end date
            Intent intent = new Intent(getApplicationContext(), DateActivity.class);
            intent.putExtra("startDate", post.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            intent.putExtra("endDate", post.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            startActivityForResult(intent, START_DATE_SELECT_REQUEST);
        }
    }

    //declare end date on click listener and same as upper class
    class endDateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), DateActivity.class);
            intent.putExtra("startDate", post.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            intent.putExtra("endDate", post.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            startActivityForResult(intent, END_DATE_SELECT_REQUEST);
        }
    }

    //declare reservation on click listener for submit
    class reservationOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //set intent for Reservation activity
            Intent intent = new Intent(getApplicationContext(), ReservationActivity.class);
            intent.putExtra("startDate", startDate.getText().toString().trim());
            intent.putExtra("endDate", endDate.getText().toString().trim());
            intent.putExtra("postId", postId);
            intent.putExtra("postName", post.getName());
            intent.putExtra("price", Integer.parseInt(post.getPrice()));
            startActivity(intent);
        }
    }
}