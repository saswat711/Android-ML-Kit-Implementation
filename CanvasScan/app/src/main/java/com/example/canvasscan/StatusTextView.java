package com.example.canvasscan;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.example.canvasscan.StrokeManager.StatusChangedListener;

/**
 * Status bar for the test app.
 *
 * <p>It is updated upon status changes announced by the StrokeManager.
 */
public class StatusTextView extends androidx.appcompat.widget.AppCompatTextView implements StatusChangedListener {

  private com.example.canvasscan.StrokeManager strokeManager;

  public StatusTextView(@NonNull Context context) {
    super(context);
  }

  public StatusTextView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
  }

  @Override
  public void onStatusChanged() {
    this.setText(this.strokeManager.getStatus());
  }

  void setStrokeManager(com.example.canvasscan.StrokeManager strokeManager) {
    this.strokeManager = strokeManager;
  }
}
