package com.example.owner.hanieum_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.owner.hanieum_project.R;

/**
 * Created by samsung on 2016-08-03.
 */
public class membership extends AppCompatActivity {

    Button button_cancel;
    Button button_ok;
    Switch switch_cooler;
    Switch switch_heater;
    Switch switch_dryer;
    Switch switch_humidifier;
    EditText edit_ID;
    EditText edit_Password;
    EditText edit_Email;
    EditText edit_CP;
    EditText edit_Address;
    String member = "member";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_membership);

        switch_cooler = (Switch) findViewById(R.id.cooler_switch);
        switch_heater = (Switch) findViewById(R.id.heater_switch);
        switch_dryer = (Switch) findViewById(R.id.dehumidifier_switch);
        switch_humidifier = (Switch) findViewById(R.id.humidifier_switch);
        edit_ID = (EditText) findViewById(R.id.ID_edit);
        edit_Password = (EditText) findViewById(R.id.Password_edit);
        edit_Email = (EditText) findViewById(R.id.Email_edit);
        edit_CP = (EditText) findViewById(R.id.CellPhone_edit);
        edit_Address = (EditText) findViewById(R.id.Address_edit);
        button_ok = (Button) findViewById(R.id.member_ok);
        button_cancel = (Button) findViewById(R.id.member_cancel);

        button_cancel.setOnClickListener(new View.OnClickListener() { //취소 버튼이 눌렸을 경우 메소드
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(membership.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = edit_ID.getText().toString();
                String Password = edit_Password.getText().toString();
                String CP = edit_CP.getText().toString();
                String Email = edit_Email.getText().toString();
                String Address = edit_Address.getText().toString();
                boolean cooler = switch_cooler.isChecked();
                boolean heater = switch_heater.isChecked();
                boolean dryer = switch_dryer.isChecked();
                boolean humidifier = switch_humidifier.isChecked();

                if ((edit_ID.getText().toString().length() != 0) && (edit_Email.getText().toString().length() != 0) && (edit_Password.getText().toString().length() != 0) && (edit_CP.getText().toString().length() != 0) && (edit_Address.getText().toString().length() != 0)) {
                    try {
                        MainActivity.DB.insertDB(member, ID, Password, CP, Email, Address, cooler, heater, dryer, humidifier);
                        Toast dbtoast = Toast.makeText(membership.this, "회원가입이 완료되었습니다.", Toast.LENGTH_LONG);
                        dbtoast.show();

                        Intent intent = new Intent(membership.this, MainActivity.class); //회원가입 완료 후 로그인창으로 다시 되돌아가기
                        startActivity(intent);
                        finish();

                    } catch (Exception ex) {
                        Log.e("DB", "삽입실패");
                        Log.e("확인", ID);
                    }
                }
            }
        });
    }
}

/*

 */