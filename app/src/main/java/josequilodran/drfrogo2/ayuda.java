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


public class ayuda extends AppCompatActivity {
ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        back = (ImageButton) findViewById(R.id.homehelp);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(ayuda.this, MainActivity.class);
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
                    "Dr FROGO2 .por Jose Quilodran 2021, Basado en DrFrogo creado en 2017", Toast.LENGTH_SHORT);
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
