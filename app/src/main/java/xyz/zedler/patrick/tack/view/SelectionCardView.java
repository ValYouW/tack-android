package xyz.zedler.patrick.tack.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.LayerDrawable;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.color.ColorRoles;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.SurfaceColors;
import xyz.zedler.patrick.tack.R;
import xyz.zedler.patrick.tack.util.ResUtil;
import xyz.zedler.patrick.tack.util.UiUtil;
import xyz.zedler.patrick.tack.util.ViewUtil;

public class SelectionCardView extends MaterialCardView {

  private final MaterialCardView innerCard;

  public SelectionCardView(Context context) {
    super(context);

    final int outerRadius = UiUtil.dpToPx(context, 16);
    final int outerPadding = UiUtil.dpToPx(context, 16);
    final int innerSize = UiUtil.dpToPx(context, 48);

    // OUTER CARD (this)

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
    );
    if (UiUtil.isLayoutRtl(context)) {
      params.leftMargin = UiUtil.dpToPx(context, 4);
    } else {
      params.rightMargin = UiUtil.dpToPx(context, 4);
    }
    setLayoutParams(params);
    setContentPadding(outerPadding, outerPadding, outerPadding, outerPadding);
    setRadius(outerRadius);
    setCardElevation(0);
    setCardForegroundColor(null);
    super.setCardBackgroundColor(SurfaceColors.SURFACE_1.getColor(context));
    setRippleColor(ColorStateList.valueOf(ResUtil.getColorHighlight(context)));
    setStrokeWidth(0);
    setCheckable(true);
    setCheckedIconResource(R.drawable.shape_selection_check);
    setCheckedIconTint(null);
    setCheckedIconSize(innerSize);
    setCheckedIconMargin(outerPadding);

    // INNER CARD

    ViewGroup.LayoutParams innerParams = new ViewGroup.LayoutParams(innerSize, innerSize);
    innerCard = new MaterialCardView(context);
    innerCard.setLayoutParams(innerParams);
    innerCard.setRadius(innerSize / 2f);
    innerCard.setStrokeWidth(UiUtil.dpToPx(context, 1));
    innerCard.setStrokeColor(ResUtil.getColorAttr(context, R.attr.colorOutline));
    innerCard.setCheckable(false);
    addView(innerCard);
  }

  @Override
  public void setCardBackgroundColor(int color) {
    if (innerCard != null) {
      innerCard.setCardBackgroundColor(color);
    }
  }

  @NonNull
  @Override
  public ColorStateList getCardBackgroundColor() {
    if (innerCard != null) {
      return innerCard.getCardBackgroundColor();
    } else {
      return super.getCardBackgroundColor();
    }
  }

  public void startCheckedIcon() {
    try {
      LayerDrawable layers = (LayerDrawable) getCheckedIcon();
      if (layers != null) {
        ViewUtil.startIcon(layers.findDrawableByLayerId(R.id.icon_selection_check));
      }
    } catch (ClassCastException ignored) {
      // For API 21 it will be a androidx.core.graphics.drawable.WrappedDrawableApi21
    }
  }

  public void setEnsureContrast(boolean ensureContrast) {
    if (ensureContrast) {
      int bg = getCardBackgroundColor().getDefaultColor();
      ColorRoles roles = MaterialColors.getColorRoles(bg, MaterialColors.isColorLight(bg));
      setCheckedIconTint(ColorStateList.valueOf(roles.getOnAccentContainer()));
    } else {
      setCheckedIconTint(
          ColorStateList.valueOf(ResUtil.getColorAttr(getContext(), R.attr.colorOnPrimaryContainer))
      );
    }
  }
}