package com.example.owner.hanieum_project.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.owner.hanieum_project.R;
import com.example.owner.hanieum_project.data.MySQLite;


public class MainActivity extends Activity {

    Button next_signup; //회원가입 버튼.
    Button Login;
    SQLiteDatabase db;
    String ID_Input;
    String PasswordInput;
    EditText edit_ID;
    EditText edit_Password;
    public static MySQLite DB;
    boolean Login_Correct = false;
    String member = "member";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login = (Button) findViewById(R.id.login_Button);
        edit_ID = (EditText) findViewById(R.id.ID_Input);
        edit_Password = (EditText) findViewById(R.id.passwordInput);
        ID_Input = edit_ID.getText().toString();
        PasswordInput = edit_Password.getText().toString();
        next_signup = (Button) findViewById(R.id.signupButton);

        DB = new MySQLite(this, "member.db", null, 1); // dbinsert 초기화.


        next_signup.setOnClickListener(new Button.OnClickListener() { //로그인 화면에서 회원가입 화면으로 넘어가는 리스너
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, membership.class);
                startActivity(intent);

            }
        });

        Login.setOnClickListener(new Button.OnClickListener() { //로그인 화면에서 로그인된 후의 화면으로 넘어가는 리스너
            public void onClick(View view) {
                try {
                    if (searchTable(member)) {
                        Intent intent = new Intent(MainActivity.this, Next_MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception ex) {

                }
            }
        });

    }

    public boolean searchTable(String searchName) { //커서를 이용하여 테이블에 저장된 아이디와 비밀번호 확인.
        db = DB.getReadableDatabase();
        ID_Input = edit_ID.getText().toString();
        PasswordInput = edit_Password.getText().toString();

        Cursor c = db.rawQuery("select ID,Password from " + searchName, null);

        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex("ID"));
            String password = c.getString(c.getColumnIndex("Password"));

            if ((id.equals(ID_Input)) && (password.equals(PasswordInput))) Login_Correct = true;
        }
        Log.e("비밀번호",String.valueOf(Login_Correct));
        return Login_Correct;
    }
}


