package com.example.andro.mac;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
EditText n;
int x,l;
Button b;
    Process process;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n=findViewById(R.id.editText);
        b=findViewById(R.id.button);
        n.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                  x++;
                  l=n.length();
                if(x==l){
                    if(l==17)n.setEnabled(false);
                    if (l == 2||l==5||l==8||l==11||l==14) {
                        n.setText( n.getText()+":");
                        n.setSelection(n.getText().length());
                        x++;
                    }}
                    else {x=l;}
            }
               });

b.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        try {
            process=Runtime.getRuntime().exec("su");
            process.getOutputStream().write("rm efs/wifi/.mac.info\n".getBytes());
            process.getOutputStream().write(("echo "+n.getText().toString().toUpperCase()+" >> efs/wifi/.mac.info\n").getBytes());
            new AlertDialog.Builder(MainActivity.this).setMessage("You need restart phone").setTitle("Restart")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        process.getOutputStream().write("reboot \n".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).setNegativeButton("No",null).create().show();

        } catch (IOException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
});


    }
}
