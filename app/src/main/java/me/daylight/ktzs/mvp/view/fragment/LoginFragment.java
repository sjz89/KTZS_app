package me.daylight.ktzs.mvp.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import me.daylight.ktzs.R;
import me.daylight.ktzs.activity.MainActivity;
import me.daylight.ktzs.utils.DialogUtil;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.SharedPreferencesUtil;
import me.daylight.ktzs.mvp.presenter.LoginPresenter;
import me.daylight.ktzs.mvp.view.LoginView;

public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginView {
    @BindView(R.id.account)
    MaterialAutoCompleteTextView account;
    @BindView(R.id.password)
    MaterialEditText password;
    @BindView(R.id.account_input_layout)
    TextInputLayout accountInputLayout;
    @BindView(R.id.password_input_layout)
    TextInputLayout passwordInputLayout;
    @BindView(R.id.main_btn_login)
    Button mBtnLogin;
    @BindView(R.id.layout_progress)
    View progress;
    @BindView(R.id.input_layout)
    View mInputLayout;
    @BindView(R.id.input_layout_name)
    LinearLayout mName;
    @BindView(R.id.input_layout_psw)
    LinearLayout mPsw;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void doAfterView() {

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showProgress() {
        loginAnim();
    }

    @Override
    public void hideProgress() {
        recovery();
    }

    @Override
    public void showInfo(String info) {
        DialogUtil.showTipDialog(getBaseFragmentActivity(), QMUITipDialog.Builder.ICON_TYPE_INFO,info,true);
    }

    @Override
    public void showErrorMsg(String msg) {
        DialogUtil.showTipDialog(getBaseFragmentActivity(), QMUITipDialog.Builder.ICON_TYPE_FAIL,msg,true);
    }

    @Override
    public void toMain() {
        Intent intent = new Intent(getBaseFragmentActivity(), MainActivity.class);
        startActivity(intent);
        getBaseFragmentActivity().finish();
    }

    @Override
    public void setAccount(String phone) {
        account.setText(phone);
    }


    @OnClick({R.id.main_btn_login,R.id.sign_up,R.id.forget_password})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.main_btn_login:
                if (mBtnLogin.getText().equals(getResources().getString(R.string.login))) {
                    if (validInput()) {
                        getPresenter().login(account.getText().toString(),password.getText().toString());
                    }
                } else if (mBtnLogin.getText().equals(getResources().getString(R.string.cancel))) {
                    recovery();
                    mBtnLogin.setText(R.string.login);
                    mBtnLogin.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.alpha));
                }
                break;
            case R.id.sign_up:
//                startFragment(new RegisterFragment());
                break;
        }
    }

    @OnClick(R.id.forget_password)
    public void setUrl(){
        QMUIDialog.EditTextDialogBuilder builder=new QMUIDialog.EditTextDialogBuilder(getCurContext());
        builder.setTitle("Url")
                .setPlaceholder("请输入服务器的Url")
                .addAction("取消",((dialog, index) -> dialog.dismiss()))
                .addAction("确定",((dialog, index) -> {
                    SharedPreferencesUtil.putValue(getCurContext(),
                            GlobalField.SETTING,GlobalField.URL,builder.getEditText().getText().toString());
                    GlobalField.url="http://"+builder.getEditText().getText().toString()+"/";
                    dialog.dismiss();
                }))
                .show();
        builder.getEditText().setText(SharedPreferencesUtil.getString(getCurContext(),GlobalField.SETTING,GlobalField.URL));
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    private void showError(TextInputLayout textInputLayout, EditText editText, String error) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(error);
        editText.requestFocus();
    }

    private boolean validInput() {
        if (password.getText().length() == 0) {
            showError(passwordInputLayout, password, "请输入密码");
            return false;
        }
        return true;
    }

    @OnTextChanged(value = {R.id.account,R.id.password},callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onTextChanged() {
        accountInputLayout.setErrorEnabled(false);
        passwordInputLayout.setErrorEnabled(false);
    }


    /**
     * 输入框的动画效果
     */
    private void inputAnimator(final View view, float w) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(animation -> {
            float value = (Float) animation.getAnimatedValue();
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                    .getLayoutParams();
            params.leftMargin = (int) value;
            params.rightMargin = (int) value;
            view.setLayoutParams(params);
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(500);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /*
                  动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

    }

    /**
     * 出现进度动画
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }

    private void loginAnim() {
        // 计算出控件的高与宽
        float mWidth = mBtnLogin.getMeasuredWidth();
        // 隐藏输入框
        mName.setVisibility(View.INVISIBLE);
        mPsw.setVisibility(View.INVISIBLE);
        inputAnimator(mInputLayout, mWidth);
        mBtnLogin.setText(R.string.cancel);
        mBtnLogin.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.alpha));
    }

    /**
     * 恢复初始状态
     */
    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        mPsw.setVisibility(View.VISIBLE);
        mBtnLogin.setText(R.string.login);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f, 1f);
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }


    private class JellyInterpolator extends LinearInterpolator {
        private float factor;

        JellyInterpolator() {
            this.factor = 0.15f;
        }

        @Override
        public float getInterpolation(float input) {
            return (float) (Math.pow(2, -10 * input)
                    * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
        }
    }
}
