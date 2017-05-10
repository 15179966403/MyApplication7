package com.hrc.myapplication.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hrc.myapplication.R;
import com.hrc.myapplication.common.CommonUtil;
import com.hrc.myapplication.model.biz.UserManager;

/**
 * 注册界面
 */

public class FragmentRegister extends Fragment {
    private View view;
    private EditText editTextEmail,editTextName,editTextPwd;
    private Button but_register;
    private CheckBox checkBox;
    private UserManager userManager;
    private String email,name,pwd;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register,container,false);
        editTextEmail= (EditText) view.findViewById(R.id.editText_email);
        editTextName= (EditText) view.findViewById(R.id.editText_name);
        editTextPwd= (EditText) view.findViewById(R.id.editText_pwd);
        but_register= (Button) view.findViewById(R.id.button_register);
        checkBox= (CheckBox) view.findViewById(R.id.checkBox1);

        but_register.setOnClickListener(clickListener);

        return view;
    }

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!checkBox.isChecked()){
                Toast.makeText(getActivity(),"没有同意协议条款",Toast.LENGTH_SHORT).show();
                return;
            }
            String email=editTextEmail.getText().toString();
            String name=editTextName.getText().toString();
            String pwd=editTextPwd.getText().toString();
            if (!CommonUtil.verifyEmail(email)){
                Toast.makeText(getActivity(),"请输入正确的邮箱格式",Toast.LENGTH_SHORT).show();
                editTextEmail.setText("");
                editTextEmail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(name)){
                Toast.makeText(getActivity(),"请输入用户昵称",Toast.LENGTH_SHORT).show();
                editTextName.setText("");
                editTextName.requestFocus();
                return;
            }
            if (pwd.length()<6||pwd.length()>16){
                Toast.makeText(getActivity(), "密码长度错误", Toast.LENGTH_SHORT).show();
                editTextPwd.setText("");
                editTextPwd.requestFocus();
                return;
            }
            if (userManager==null){
                userManager=UserManager.getInstance(getActivity());
            }
            //将版本，用户昵称，密码，邮箱，手机imei号，上传到服务器
        }
    };
}
