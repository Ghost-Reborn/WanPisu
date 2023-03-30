package in.ghostreborn.wanpisu.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import in.ghostreborn.wanpisu.fragment.AnilistCurrentFragment;

public class AnilistAdapter extends FragmentStateAdapter {

    int TABS = 6;

    public AnilistAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new AnilistCurrentFragment();
    }

    @Override
    public int getItemCount() {
        return TABS;
    }
}
