package me.daylight.ktzs.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import me.daylight.ktzs.R;

public class ChangePwdDialogBuilder extends QMUIDialog.CustomDialogBuilder {
    @BindView(R.id.dialog_password)
    EditText password;

    @BindView(R.id.pwd_input_layout)
    TextInputLayout pwdInputLayout;

    @BindView(R.id.dialog_check_password)
    EditText checkPassword;

    @BindView(R.id.check_pwd_input_layout)
    TextInputLayout checkPwdInputLayout;

    @BindView(R.id.dialog_old_password)
    EditText oldPassword;

    @BindView(R.id.old_pwd_input_layout)
    TextInputLayout oldPwdInputLayout;

    public ChangePwdDialogBuilder(Context context) {
        super(context);
    }

    @Override
    protected void onCreateContent(QMUIDialog dialog, ViewGroup parent, Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_change_pwd, parent);
        ButterKnife.bind(this,view);
    }

    public String getOldPassword(){
        return oldPassword.getText().toString();
    }

    public String getPassword(){
        return password.getText().toString();
    }

    private void showError(TextInputLayout textInputLayout,EditText editText,String error){
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(error);
        editText.requestFocus();
    }

    public boolean validPassword(){
        if (oldPassword.getText().toString().equals("")){
            showError(oldPwdInputLayout,oldPassword,"请输入原密码");
            return false;
        }
        if (password.getText().toString().equals("")){
            showError(pwdInputLayout,password,"密码不能为空");
            return false;
        }
        if (!password.getText().toString().equals(checkPassword.getText().toString())){
            showError(checkPwdInputLayout,checkPassword,"密码不一致");
            return false;
        }
        return true;
    }

    @OnTextChanged(value = {R.id.dialog_old_password,R.id.dialog_password,R.id.dialog_check_password},callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onTextChange(){
        pwdInputLayout.setErrorEnabled(false);
        checkPwdInputLayout.setErrorEnabled(false);
    }
}
