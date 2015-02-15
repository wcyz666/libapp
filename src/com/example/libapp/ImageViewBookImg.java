package com.example.libapp;

import android.widget.ImageView;

public class ImageViewBookImg {
	private ImageView view;
	private NormalBook book;
	public ImageViewBookImg(ImageView view, NormalBook book) {
		this.view = view;
		this.book = book;
	}
	public ImageView getImageView() {
		return this.view;
	}
	public NormalBook getNormalBook() {
		return this.book;
	}
}
