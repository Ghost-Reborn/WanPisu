package in.ghostreborn.wanpisu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.ui.KitsuLoginActivity;

public class KitsuUserAnimeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kitsu_user_anime, container, false);
        RecyclerView kitsuUserAnimeRecycler = view.findViewById(R.id.kitsu_user_anime_recycler);
        TextView kitsuLoginInfoButton = view.findViewById(R.id.kitsu_login_info_button);
        kitsuLoginInfoButton.setOnClickListener(view1 -> {
            if (!WanPisuConstants.preferences.contains(WanPisuConstants.KITSU_LOGIN_FINISHED)) {
                startActivity(new Intent(getContext(), KitsuLoginActivity.class));
            }
        });
        if (!WanPisuConstants.preferences.contains(WanPisuConstants.KITSU_LOGIN_FINISHED)) {
            kitsuLoginInfoButton.setVisibility(View.VISIBLE);
        }
        return view;
    }
}