package josequilodran.drfrogo2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;



public class levels_1p extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_1p);
        ImageButton n1 = (ImageButton) findViewById(R.id.imageButton4);
        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(levels_1p.this, motor.class);
                t.putExtra("nivel",1);
                startActivity(t);
                finish();
            }
        });

        ImageButton n2 = (ImageButton) findViewById(R.id.imageButton5);
        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(levels_1p.this, motor.class);
                t.putExtra("nivel",2);
                startActivity(t);
                finish();
            }
        });

        ImageButton n3 = (ImageButton) findViewById(R.id.imageButton6);
        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(levels_1p.this, motor.class);

                startActivity(t);
                finish();
            }
        });

        ImageButton n4 = (ImageButton) findViewById(R.id.imageButton7);
        n4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(levels_1p.this, motor.class);
                t.putExtra("nivel",4);
                startActivity(t);
                finish();
            }
        });

        ImageButton n5 = (ImageButton) findViewById(R.id.imageButton8);
        n5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(levels_1p.this, motor.class);
                t.putExtra("nivel",5);
                startActivity(t);
                finish();
            }
        });
        ImageButton back = (ImageButton) findViewById(R.id.homel1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(levels_1p.this, MainActivity.class);
                startActivity(t);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.about) {

            Toast t= Toast.makeText(getApplicationContext(),
                    "Proyecto final Dr FROGO.por Jose Quilodran 2017", Toast.LENGTH_SHORT);
            t.setGravity(Gravity.BOTTOM| Gravity.LEFT,0,0);
            t.show();
            return true;
        }
        if (id == R.id.select) {

            Intent t= new Intent(this,levels_1p.class);
            startActivity(t);
            finish();
            return true;
        }
        if (id == R.id.menu) {

            Intent t= new Intent(this,MainActivity.class);
            startActivity(t);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

