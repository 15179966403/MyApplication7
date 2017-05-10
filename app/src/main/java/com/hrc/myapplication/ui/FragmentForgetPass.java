package com.hrc.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hrc.myapplication.R;
import com.hrc.myapplication.common.CommonUtil;
import com.hrc.myapplication.common.LogUtil;
import com.hrc.myapplication.model.biz.UserManager;

/**
 * 忘记密码
 */

public class FragmentForgetPass extends Fragment {
    /**邮箱编辑框*/
    private EditText editEmail;
    /**确认按钮*/
    private Button btnCommit;
    /**用户管理器*/
    private UserManager userManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_forgetpass,container,false);
        editEmail= (EditText) view.findViewById(R.id.edit_email);
        btnCommit= (Button) view.findViewById(R.id.btn_commit);
        btnCommit.setOnClickListener(listener);
        return view;
    }

    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.btn_commit){
                String email=editEmail.getText().toString();
                if (!CommonUtil.verifyEmail(email)){
                    Toast.makeText(getActivity(),"请输入正确的邮箱格式",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userManager==null)
                    userManager=UserManager.getInstance(getActivity());
                //执行忘记密码操作，向服务器发送忘记密码请求
                LogUtil.d("忘记密码服务");
            }
        }
    };
}
