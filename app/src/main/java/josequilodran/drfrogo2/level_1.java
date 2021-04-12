package josequilodran.drfrogo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class level_1 extends motor {
public level_1(){super(1,0,2,600);}
    @Override
    void victoria() {
        if (nvirus == 0) {
            t2 = new Intent(level_1.this, level_2.class);
            fallo.setImageResource(R.drawable.win);
            fallo.setVisibility(View.VISIBLE);
            tiempo = 300000;
        }
    }

    void fallo() {
        if (casillas[14][3].tiene_algo == true || casillas[14][4].tiene_algo == true) {
            t2 = new Intent(level_1.this, level_1.class);
            fallo.setVisibility(View.VISIBLE);
            tiempo = 300000;
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

 }