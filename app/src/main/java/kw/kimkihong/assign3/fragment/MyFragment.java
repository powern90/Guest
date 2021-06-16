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
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        this.signOut = (Button) view.findViewById(R.id.Signout);
        this.signOut.setOnClickListener(new signOutOnClickListener());
        //then return in on create view.
        return view;
    }

    class signOutOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            SharedPreferences preferences = mContext.getSharedPreferences("user_data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= preferences.edit();
            editor.remove("id");
            editor.remove("password");
            editor.remove("token");
            editor.remove("isBusiness");
            editor.commit();
            //close app
            mActivity.finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }
}