package in.college.safety247;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.andexert.library.RippleView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    LoginDataBaseAdapter loginDataBaseAdapter;
    EditText fnameEdit, lnameEdit, emailEdit, userEdit, passEdit, confirmEdit, phEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fnameEdit = (EditText) findViewById(R.id.fname_editText);
        lnameEdit = (EditText) findViewById(R.id.lname_editText);
        emailEdit = (EditText) findViewById(R.id.email_editText);
        userEdit = (EditText) findViewById(R.id.sign_user_editText);
        passEdit = (EditText) findViewById(R.id.sign_pass_editText);
        confirmEdit = (EditText) findViewById(R.id.confirm_pass_editText);
        phEdit = (EditText) findViewById(R.id.ph_editText);
        TelephonyManager tmanager=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phEdit.setText(tmanager.getLine1Number());

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

    }

    public void onSignupClicked(View v) {
        final RippleView rippleView = (RippleView) findViewById(R.id.signupRipple);
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {


                String fname = fnameEdit.getText().toString();
                String lname = lnameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String Expn =

                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"

                                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."

                                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"

                                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
                String user = userEdit.getText().toString();
                String pass = passEdit.getText().toString();
                String confirmPass = confirmEdit.getText().toString();
                String phone = phEdit.getText().toString();


                if (fname.equals("") || lname.equals("") || email.equals("") || user.equals("") || pass.equals("") || confirmPass.equals("") || phone.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (!pass.equals(confirmPass)) {
                    Toast.makeText(getApplicationContext(), "Please enter same password", Toast.LENGTH_SHORT).show();
                    return;
                }else if(pass.length() < 8  ){
                    Toast.makeText(getApplicationContext(), "Password must contain atleast 8 characters", Toast.LENGTH_SHORT).show();
                    return;
                } else if(phone.length()<10 || phone.length()>10){
                    Toast.makeText(getApplicationContext(), "Enter correct phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!email.matches(Expn))

                {
                    Toast.makeText(getApplicationContext(), "Enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    Contact contact = new Contact();
                    contact.setFname(fname);
                    contact.setLname(lname);
                    contact.setEmail(email);
                    contact.setUser(user);
                    contact.setPass(pass);
                    contact.setCpass(confirmPass);
                    contact.setPhone(phone);

                    loginDataBaseAdapter.insertEntry(contact);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}

