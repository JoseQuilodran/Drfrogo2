package josequilodran.drfrogo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton b=(ImageButton) findViewById(R.id.imageButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t= new Intent(MainActivity.this,levels_1p.class);
                startActivity(t);
                finish();
            }});

        ImageButton bayuda=(ImageButton) findViewById(R.id.imageButton3);       bayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t= new Intent(MainActivity.this,ayuda.class);
                startActivity(t);
                finish();
            }});

        ImageButton multi=(ImageButton) findViewById(R.id.imageButton2);
        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t=Toast.makeText(getApplicationContext(),
                        "Multijugador no implementado aun", Toast.LENGTH_LONG);
                t.setGravity(Gravity.BOTTOM|Gravity.LEFT,0,0);
                t.show();

            }});
    }
}
