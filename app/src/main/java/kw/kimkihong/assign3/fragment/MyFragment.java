package kw.kimkihong.assign3.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import kw.kimkihong.assign3.R;
import org.jetbrains.annotations.NotNull;

public class MyFragment extends Fragment {
    //declare fragment variable
    private Button signOut;

    private Context mContext;
    private Activity mActivity;

    private View view;

    //get context and activity
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
        //set variable
        this.view = inflater.inflate(R.layout.fragment_add, container, false);
        this.signOut = this.view.findViewById(R.id.mySignout);

        //assign onclick listener
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear shared preference
                SharedPreferences preferences = mContext.getSharedPreferences("user_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences.edit();
                editor.remove("id");
                editor.remove("password");
                editor.remove("token");
                editor.remove("isBusiness");
                editor.apply();
                //close app
                mActivity.finishAffinity();
                System.runFinalization();
                System.exit(0);
            }
        });

        return inflater.inflate(R.layout.fragment_my, container, false);
    }
}