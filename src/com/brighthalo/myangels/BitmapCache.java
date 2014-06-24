package com.brighthalo.myangels;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

import android.graphics.Bitmap;

public abstract class BitmapCache {

	private static final int MAX_CACHE = 100;

	private static Hashtable<Long, Bitmap> hashtable = new Hashtable<Long, Bitmap>(
			MAX_CACHE);
	private static Queue<Long> queue = new LinkedList<Long>();

	public static void putImage(Long key, Bitmap bitmap) {

		if (queue.size() == MAX_CACHE) {

			Long obj = queue.poll();
			hashtable.remove(obj);

		}
		queue.add(key);
		hashtable.put(key, bitmap);

	}

	public static Bitmap getImage(Long key) {
		return hashtable.get(key);
	}
}
