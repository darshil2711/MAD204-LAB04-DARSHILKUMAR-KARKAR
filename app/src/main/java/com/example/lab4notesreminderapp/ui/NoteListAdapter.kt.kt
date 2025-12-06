<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:orientation="vertical"
android:padding="16dp"
android:layout_margin="8dp"
android:background="@drawable/item_background"> <!-- Create this drawable later -->

<TextView
android:id="@+id/textViewTitle"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:text="Note Title"
android:textAppearance="@style/TextAppearance.AppCompat.Large"
android:textStyle="bold" />

<TextView
android:id="@+id/textViewContent"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:text="Note content preview..."
android:layout_marginTop="4dp" />
</LinearLayout>
