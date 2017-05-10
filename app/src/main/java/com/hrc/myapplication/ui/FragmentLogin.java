package com.hrc.myapplication.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hrc.myapplication.R;
import com.hrc.myapplication.common.LogUtil;
import com.hrc.myapplication.model.biz.UserManager;

/**
 * 登录界面
 */

public class FragmentLogin extends Fragment {
    private View view;
    private EditText editTextNickname,editTextPwd;
    private Button btn_register,btn_login,btn_forgetPass;
    private UserManager userManager;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_login,container,false);
        editTextNickname= (EditText) view.findViewById(R.id.editText_nickname);
        editTextPwd= (EditText) view.findViewById(R.id.editText_pwd);
        btn_register= (Button) view.findViewById(R.id.button_register);
        btn_forgetPass= (Button) view.findViewById(R.id.button_forgerPass);
        btn_login= (Button) view.findViewById(R.id.button_login);

        btn_register.setOnClickListener(clickListener);
        btn_forgetPass.setOnClickListener(clickListener);
        btn_login.setOnClickListener(clickListener);
        return view;
    }

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_login:
                    String name=editTextNickname.getText().toString().trim();
                    String pwd=editTextPwd.getText().toString().trim();
                    if (TextUtils.isEmpty(name)){
                        Toast.makeText(getActivity(),"请输入用户名",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (TextUtils.isEmpty(pwd)){
                        Toast.makeText(getActivity(),"密码不能为空",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (pwd.length()<6||pwd.length()>16){
                        Toast.makeText(getContext(),"密码长度错误",Toast.LENGTH_LONG).show();
                        editTextPwd.setText("");
                        editTextPwd.requestFocus();
                        return;
                    }
                    //UserManger处理事件
                    if (userManager==null)
                        userManager=UserManager.getInstance(getActivity());
                    userManager.login(name,"头像默认",pwd,"手机端");
                    break;
                case R.id.button_register:
                    ((ActivityMain)getActivity()).showFragmentRegister();
                    break;
                case R.id.button_forgerPass:
                    ((ActivityMain)getActivity()).showFragmentForgetPass();
                    break;
            }
        }
    };


}
