package com.example.systemscoreinc.repawn;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * A host (typically an {@code Activity}} that can display fragments and knows how to respond to
 * navigation events.
 */
public interface NavigationHost {
    /**
     * Trigger a navigation to the specified fragment, optionally adding a transaction to the back
     * stack to make this navigation reversible.
     */
    void navigateTo(Fragment fragment, boolean addToBackstack);
    //  void navigateToAct(Activity activity,boolean addToBackstack);
}
