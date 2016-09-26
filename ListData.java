package com.example.owner.hanieum_project.data;

import android.graphics.drawable.Drawable;

import java.text.Collator;
import java.util.Comparator;
/**
 * Created by Owner on 2016-09-14.
 */
public class ListData {

    public Drawable mIcon; // 디바이스 이미지들
    public String mTitle; // 디바이스 상태?

    /**
     * 알파벳 이름으로 정렬
     */
    public static final Comparator <ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData mListDate_1, ListData mListDate_2) {
            return sCollator.compare(mListDate_1.mTitle, mListDate_2.mTitle);
        }
    };
}
