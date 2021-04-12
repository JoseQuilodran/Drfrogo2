package josequilodran.drfrogo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class level_2 extends motor {
public level_2(){super(2,2222,2,600);}
    @Override
    void victoria() {
        if (nvirus == 0) {
            t2 = new Intent(level_2.this, level_3.class);
            fallo.setImageResource(R.drawable.win);
            fallo.setVisibility(View.VISIBLE);
            tiempo = 300000;
        }
    }
    void fallo() {
        if (casillas[14][3].tiene_algo || casillas[14][4].tiene_algo) {
            t2 = new Intent(level_2.this, level_2.class);
            fallo.setVisibility(View.VISIBLE);
            tiempo = 300000;
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

 }