package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.utils.AnilistUtils;

public class AnilistLoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anilist_login, container, false);
        Button anilistLoginButton = view.findViewById(R.id.anilist_login_button);
        if (!AnilistUtils.checkAnilist(requireContext())) {
            anilistLoginButton.setOnClickListener(v -> {
                AnilistUtils.checkAnilist(requireContext());
            });
        }else {
            anilistLoginButton.setVisibility(View.GONE);
        }
        return view;
    }
}