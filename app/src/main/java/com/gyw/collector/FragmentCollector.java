package com.gyw.collector;

import androidx.fragment.app.Fragment;

import com.gyw.application.fragment.MusicFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentCollector {
    public static List<MusicFragment> fragments = new ArrayList<>();

    public static void addFragment(MusicFragment fragment){
        fragments.add(fragment);
    }

}
