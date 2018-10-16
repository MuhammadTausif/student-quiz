package com.tausif.abdulqadeer.a935studentcontactsqliteLND.Practice;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.view.View;
import android.widget.TableRow;

/**
 * Created by Muhammad Tausif on 10/11/2018.
 */

public class RadioGridGroup extends TableLayout implements View.OnClickListener {

    private static final String TAG = "ToggleButtonGroupTableLayout";
    private int checkedButtonID = -1;

    public RadioGridGroup(Context context) {
        super(context);
    }

    public RadioGridGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        setChildrenOnClickListener((TableRow) child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        setChildrenOnClickListener((TableRow) child);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof RadioButton) {
            int id = v.getId();
            check(id);
        }
    }

    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = findViewById(viewId);
        if (checkedView != null && checkedView instanceof RadioButton) {
            ((RadioButton) checkedView).setChecked(checked);
        }
    }

    private void setChildrenOnClickListener(TableRow tr) {
        final int c = tr.getChildCount();
        for (int i = 0; i < c; i++) {
            final View v = tr.getChildAt(i);
            if (v instanceof RadioButton) {
                v.setOnClickListener(this);
            }
        }
    }

    /**
     * @return the checked button Id
     */
    public int getCheckedRadioButtonId() {
        return checkedButtonID;
    }

    /**
     * Check the id
     *
     * @param id
     */
    public void check(@IdRes int id) {
        // don't even bother
        if (id != -1 && (id == checkedButtonID)) {
            return;
        }
        if (checkedButtonID != -1) {
            setCheckedStateForView(checkedButtonID, false);
        }
        if (id != -1) {
            setCheckedStateForView(id, true);
        }
        setCheckedId(id);
    }

    /**
     * set the checked button Id
     *
     * @param id
     */
    private void setCheckedId(int id) {
        this.checkedButtonID = id;
    }

    public void clearCheck() {
        check(-1);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        this.checkedButtonID = ss.buttonId;
        setCheckedStateForView(checkedButtonID, true);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.buttonId = checkedButtonID;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int buttonId;

        /**
         * Constructor used when reading from a parcel. Reads the state of the superclass.
         *
         * @param source
         */
        public SavedState(Parcel source) {
            super(source);
            buttonId = source.readInt();
        }

        /**
         * Constructor called by derived classes when creating their SavedState objects
         *
         * @param superState The state of the superclass of this view
         */
        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(buttonId);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
