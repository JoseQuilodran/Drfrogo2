package josequilodran.drfrogo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class level_5 extends motor {
public level_5(){super(5,5555,5,400);}
    @Override
    void victoria() {
        if (nvirus == 0) {
            t2 = new Intent(level_5.this, MainActivity.class);
            fallo.setImageResource(R.drawable.win);
            fallo.setVisibility(View.VISIBLE);
            tiempo = 300000;
        }
    }
    void fallo() {
        if (casillas[14][3].tiene_algo || casillas[14][4].tiene_algo ) {
            t2 = new Intent(level_5.this, level_5.class);
            fallo.setVisibility(View.VISIBLE);
            tiempo = 300000;
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

 }