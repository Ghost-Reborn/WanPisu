package in.ghostreborn.wanpisu.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import in.ghostreborn.wanpisu.constants.WanPisuConstants;
import in.ghostreborn.wanpisu.fragment.AnilistCurrentFragment;

public class AnilistFragmentStateAdapter extends FragmentStateAdapter {

    int TABS = 6;

    public AnilistFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AnilistCurrentFragment(WanPisuConstants.ANIME_CURRENT);
            case 1:
                return new AnilistCurrentFragment(WanPisuConstants.ANIME_PLANNING);
            case 2:
                return new AnilistCurrentFragment(WanPisuConstants.ANIME_COMPLETED);
            case 3:
                return new AnilistCurrentFragment(WanPisuConstants.ANIME_DROPPED);
            case 4:
                return new AnilistCurrentFragment(WanPisuConstants.ANIME_PAUSED);
            case 5:
                return new AnilistCurrentFragment(WanPisuConstants.ANIME_REPEATING);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return TABS;
    }
}
