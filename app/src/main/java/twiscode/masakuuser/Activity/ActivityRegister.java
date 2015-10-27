package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;

/**
 * Created by User on 10/27/2015.
 */
public class ActivityRegister extends Activity {

    private Activity mActivity;
    private EditText txtPhone,txtPassword;
    private Button btnLogin;
    private ModelUser userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mActivity = this;
        //db = new DatabaseHandler(mActivity);


        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ActivityHome.class);
                startActivity(i);
                finish();

            }
        });



    }


}
