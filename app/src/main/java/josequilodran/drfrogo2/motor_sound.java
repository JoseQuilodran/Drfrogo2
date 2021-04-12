package josequilodran.drfrogo2;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class motor_sound extends AppCompatActivity {
    motor_sound(int nivel, int puntaje, int virus, int tiempo){
        this.nnivel=nivel;
        this.npuntaje=puntaje;
        this.nvirus=virus;
        this.tiempo=tiempo;
    }
    motor_sound(){
        this.nnivel=0;
        this.npuntaje=664;
        this.nvirus=1;
        this.tiempo=600;}

    MediaPlayer mp, gameover, victory;
    ImageView a1;
    ImageButton vuelta;
    ImageButton mover_izq;
    ImageButton mover_der;
    ImageButton bajar_rapido;
    ImageButton fallo;
    ImageButton home;
    ImageView d1, d2;
    casilla casillas[][] = new casilla[16][8];
    pildora p, p2;
    int nnivel;
    int npuntaje;
    int nvirus;
    int psx = 3;
    int psy = 15;
    int psx2 = 4;
    int psy2 = 15;
    Handler handler, handler2, handler3;
    TextView virus, puntaje, nivel;
    int tiempo;
    Intent t2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_1);
        creador();
        t2= new Intent(motor_sound.this, level_1.class);
        mp = MediaPlayer.create(this, R.raw.drmario);
        gameover = MediaPlayer.create(this, R.raw.gameover);
        victory = MediaPlayer.create(this, R.raw.victory);
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });

        p = new pildora();
        p2 = new pildora();
        creador_virus(nvirus);
        nvirus = contador_virus();

        a1 = (ImageView) findViewById(R.id.imageView5);
        d1 = (ImageView) findViewById(R.id.imageViewrana1);
        d2 = (ImageView) findViewById(R.id.imageViewrana2);
        final Animation rotar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotar);
        a1.startAnimation(rotar);
        vuelta = (ImageButton) findViewById(R.id.imageButton12);
        vuelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotador(p);
            }
        });
        mover_izq = (ImageButton) findViewById(R.id.imageButton10);
        mover_izq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movedor_izquierda(p);
            }
        });
        home = (ImageButton) findViewById(R.id.imageButton13);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.release();
                gameover.release();
                victory.release();
                Intent t = new Intent(motor_sound.this, MainActivity.class);
                startActivity(t);
                finish();
            }
        });
        mover_der = (ImageButton) findViewById(R.id.imageButton9);
        mover_der.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movedor_derecha(p);
            }
        });
        bajar_rapido = (ImageButton) findViewById(R.id.imageButton11);
        bajar_rapido.setOnTouchListener(new View.OnTouchListener() {
            int copiatiempo;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    copiatiempo=tiempo;
                    tiempo = 100;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    tiempo = copiatiempo;
                }
                return true;}
        });
        fallo = (ImageButton) findViewById(R.id.fallo);
        fallo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mp.release();

                if(gameover.isPlaying()) {
                    gameover.stop();

                }
                if(victory.isPlaying()) {
                    victory.stop();

                }
                startActivity(t2);
                finish();
            }
        });

        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                victoria();
                fallo();
                movedor_pildora();
                handler.postDelayed(this, tiempo);
            }
        };
        handler.postDelayed(runnable, tiempo);
        handler2 = new Handler();
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 14; i++) {
                    for (int f = 0; f <= 7; f++) {
                        flotantes(i, f);
                    }
                }
                handler2.postDelayed(this, tiempo);
            }
        };
        handler2.postDelayed(runnable2, tiempo);

        handler3 = new Handler();
        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 14; i++) {
                    for (int f = 0; f <= 6; f++) {
                        flotante_pildora(i, f);
                    }
                }
                handler3.postDelayed(this, tiempo);
            }
        };
        handler3.postDelayed(runnable3, tiempo);


        nivel = (TextView) findViewById(R.id.textView);
        nivel.setText("" + nnivel);
        puntaje = (TextView) findViewById(R.id.textView2);
        puntaje.setText("" + npuntaje);
        virus = (TextView) findViewById(R.id.textView3);
        virus.setText("" + nvirus);
    }
    public void onDestroy() {
        super.onDestroy();    }

    class casilla {
        int id;
        ImageView v;
        int amigox;
        int amigoy;
        boolean tiene_amigo = false;
        int color = 0;//0=nada;1=rojo;2=azul;3=amarillo
        boolean tiene_algo = false;
        boolean virus = false;

        casilla(int id) {
            this.id = id;
            v = (ImageView) findViewById(id);
            v.setImageResource(R.drawable.vacio);
        }
    }

    class pildora {
        int orientacion = 1;//0 no;1=horizontal,2 vertical;
        int rand1 = (int) (Math.random() * (1 - 4) + 4);
        int rand2 = (int) (Math.random() * (1 - 4) + 4);
        int color1 = rand1;
        int color2 = rand2;
        int image1, image2;

        pildora() {
            image1 = imagen1(color1);
            image2 = imagen2(color2);
        }

        int imagen1(int color1) {
            int image1 = 0;
            if (color1 == 1) {
                if (orientacion == 1) {
                    image1 = R.drawable.rh1;
                } else if (orientacion == 2) {
                    image1 = R.drawable.rv2;
                }
            } else if (color1 == 2) {
                if (orientacion == 1) {
                    image1 = R.drawable.bh1;
                } else if (orientacion == 2) {
                    image1 = R.drawable.bv2;
                }
            } else if (color1 == 3) {
                if (orientacion == 1) {
                    image1 = R.drawable.yh1;
                } else if (orientacion == 2) {
                    image1 = R.drawable.yv2;
                }
            }
            return image1;
        }

        int imagen2(int color2) {
            int image2 = 0;
            if (color2 == 1) {
                if (orientacion == 1) {
                    image2 = R.drawable.rh2;
                } else if (orientacion == 2) {
                    image2 = R.drawable.rv1;
                }
            } else if (color2 == 2) {
                if (orientacion == 1) {
                    image2 = R.drawable.bh2;
                } else if (orientacion == 2) {
                    image2 = R.drawable.bv1;
                }
            } else if (color2 == 3) {
                if (orientacion == 1) {
                    image2 = R.drawable.yh2;
                } else if (orientacion == 2) {
                    image2 = R.drawable.yv1;
                }
            }
            return image2;
        }
    }

    void movedor_pildora() {
        if (psy == 0 || casillas[psy - 1][psx].tiene_algo == true || casillas[psy2 - 1][psx2].tiene_algo == true) {
            casillas[psy][psx].tiene_algo = true;
            casillas[psy2][psx2].tiene_algo = true;
            casillas[psy][psx].color = p.color1;
            casillas[psy2][psx2].color = p.color2;
            casillas[psy][psx].amigox = psx2;
            casillas[psy][psx].amigoy = psy2;
            casillas[psy2][psx2].amigox = psx;
            casillas[psy2][psx2].amigoy = psy;
            casillas[psy][psx].tiene_amigo = true;
            casillas[psy2][psx2].tiene_amigo = true;
            for (int i = 0; i <= 15; i++) {
                for (int f = 0; f <= 7; f++) {
                    int col = casillas[i][f].color;
                    eliminadorh(i, f, casillas[i][f].color);
                    eliminadorv(i, f, col);
                    int virus_ant = nvirus;
                    int cantidadvirus = 0;
                    if (casillas[i][f].virus == true && casillas[i][f].tiene_algo == false) {
                        cantidadvirus++;
                        casillas[i][f].virus = false;
                        nvirus = nvirus - cantidadvirus;
                        virus.setText("" + nvirus);
                        if (virus_ant - nvirus != 0) {
                            npuntaje = npuntaje + 500;
                            puntaje.setText("" + npuntaje);
                        }

                    }
                }
            }


            p = p2;
            p2 = new pildora();
            psx = 3;
            psy = 15;
            psx2 = 4;
            psy2 = 15;
            final Animation rotar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotar);
            a1.startAnimation(rotar);
        }
        if (psy > 0 && psy2 > 0) {
            casillas[psy][psx].v.setImageResource(R.drawable.vacio);
            casillas[psy2][psx2].v.setImageResource(R.drawable.vacio);
            psy--;
            psy2--;
            casillas[psy][psx].v.setImageResource(p.image1);
            casillas[psy2][psx2].v.setImageResource(p.image2);
            d1.setImageResource(p2.image1);
            d2.setImageResource(p2.image2);
        }
    }

    void movedor_izquierda(pildora p) {
        if (psx2 > 0 && psx > 0 && psy >= 0) {
            if (casillas[psy][psx - 1].tiene_algo == false && casillas[psy2][psx2 - 1].tiene_algo == false) {
                casillas[psy][psx].v.setImageResource(R.drawable.vacio);
                casillas[psy2][psx2].v.setImageResource(R.drawable.vacio);
                psx--;
                psx2--;
                casillas[psy][psx].v.setImageResource(p.image1);
                casillas[psy2][psx2].v.setImageResource(p.image2);
            }
        }
    }

    void movedor_derecha(pildora p) {
        if (psx2 < 7 && psx < 7) {
            if (casillas[psy][psx + 1].tiene_algo == false && casillas[psy2][psx2 + 1].tiene_algo == false) {
                casillas[psy][psx].v.setImageResource(R.drawable.vacio);
                casillas[psy2][psx2].v.setImageResource(R.drawable.vacio);
                psx++;
                psx2++;
                casillas[psy][psx].v.setImageResource(p.image1);
                casillas[psy2][psx2].v.setImageResource(p.image2);
            }
        }
    }

    void rotador(pildora p) {
        casillas[psy][psx].v.setImageResource(R.drawable.vacio);
        casillas[psy2][psx2].v.setImageResource(R.drawable.vacio);
        int cs1 = p.color1;
        int cs2 = p.color2;
        if (psx < 7 && psy >= 0) {
            if (p.orientacion == 1 && casillas[psy + 1][psx].tiene_algo == false) {
                p.orientacion = 2;
                psx2--;
                psy2++;
                p.image1 = p.imagen1(p.color1);
                p.image2 = p.imagen2(p.color2);
            } else if (p.orientacion == 2 && casillas[psy][psx + 1].tiene_algo == false) {
                p.orientacion = 1;
                psy2--;
                psx2++;
                p.color1 = cs2;
                p.color2 = cs1;
                p.image1 = p.imagen1(p.color1);
                p.image2 = p.imagen2(p.color2);
            }
            casillas[psy][psx].v.setImageResource(p.image1);
            casillas[psy2][psx2].v.setImageResource(p.image2);
        }
    }

    void creador() {
        casillas[0][0] = new casilla(R.id.imageView120);
        casillas[0][1] = new casilla(R.id.imageView119);
        casillas[0][2] = new casilla(R.id.imageView118);
        casillas[0][3] = new casilla(R.id.imageView117);
        casillas[0][4] = new casilla(R.id.imageView116);
        casillas[0][5] = new casilla(R.id.imageView115);
        casillas[0][6] = new casilla(R.id.imageView114);
        casillas[0][7] = new casilla(R.id.imageView113);

        casillas[1][0] = new casilla(R.id.imageView128);
        casillas[1][1] = new casilla(R.id.imageView127);
        casillas[1][2] = new casilla(R.id.imageView126);
        casillas[1][3] = new casilla(R.id.imageView125);
        casillas[1][4] = new casilla(R.id.imageView124);
        casillas[1][5] = new casilla(R.id.imageView123);
        casillas[1][6] = new casilla(R.id.imageView122);
        casillas[1][7] = new casilla(R.id.imageView121);

        casillas[2][0] = new casilla(R.id.imageView136);
        casillas[2][1] = new casilla(R.id.imageView135);
        casillas[2][2] = new casilla(R.id.imageView134);
        casillas[2][3] = new casilla(R.id.imageView133);
        casillas[2][4] = new casilla(R.id.imageView132);
        casillas[2][5] = new casilla(R.id.imageView131);
        casillas[2][6] = new casilla(R.id.imageView130);
        casillas[2][7] = new casilla(R.id.imageView129);

        casillas[3][0] = new casilla(R.id.imageView144);
        casillas[3][1] = new casilla(R.id.imageView143);
        casillas[3][2] = new casilla(R.id.imageView142);
        casillas[3][3] = new casilla(R.id.imageView141);
        casillas[3][4] = new casilla(R.id.imageView140);
        casillas[3][5] = new casilla(R.id.imageView139);
        casillas[3][6] = new casilla(R.id.imageView138);
        casillas[3][7] = new casilla(R.id.imageView137);

        casillas[4][0] = new casilla(R.id.imageView152);
        casillas[4][1] = new casilla(R.id.imageView151);
        casillas[4][2] = new casilla(R.id.imageView150);
        casillas[4][3] = new casilla(R.id.imageView149);
        casillas[4][4] = new casilla(R.id.imageView148);
        casillas[4][5] = new casilla(R.id.imageView147);
        casillas[4][6] = new casilla(R.id.imageView146);
        casillas[4][7] = new casilla(R.id.imageView145);

        casillas[5][0] = new casilla(R.id.imageView160);
        casillas[5][1] = new casilla(R.id.imageView159);
        casillas[5][2] = new casilla(R.id.imageView158);
        casillas[5][3] = new casilla(R.id.imageView157);
        casillas[5][4] = new casilla(R.id.imageView156);
        casillas[5][5] = new casilla(R.id.imageView155);
        casillas[5][6] = new casilla(R.id.imageView154);
        casillas[5][7] = new casilla(R.id.imageView153);

        casillas[6][0] = new casilla(R.id.imageView168);
        casillas[6][1] = new casilla(R.id.imageView167);
        casillas[6][2] = new casilla(R.id.imageView166);
        casillas[6][3] = new casilla(R.id.imageView165);
        casillas[6][4] = new casilla(R.id.imageView164);
        casillas[6][5] = new casilla(R.id.imageView163);
        casillas[6][6] = new casilla(R.id.imageView162);
        casillas[6][7] = new casilla(R.id.imageView161);

        casillas[7][0] = new casilla(R.id.imageView176);
        casillas[7][1] = new casilla(R.id.imageView175);
        casillas[7][2] = new casilla(R.id.imageView174);
        casillas[7][3] = new casilla(R.id.imageView173);
        casillas[7][4] = new casilla(R.id.imageView172);
        casillas[7][5] = new casilla(R.id.imageView171);
        casillas[7][6] = new casilla(R.id.imageView170);
        casillas[7][7] = new casilla(R.id.imageView169);

        casillas[8][0] = new casilla(R.id.imageView184);
        casillas[8][1] = new casilla(R.id.imageView183);
        casillas[8][2] = new casilla(R.id.imageView182);
        casillas[8][3] = new casilla(R.id.imageView181);
        casillas[8][4] = new casilla(R.id.imageView180);
        casillas[8][5] = new casilla(R.id.imageView179);
        casillas[8][6] = new casilla(R.id.imageView178);
        casillas[8][7] = new casilla(R.id.imageView177);

        casillas[9][0] = new casilla(R.id.imageView192);
        casillas[9][1] = new casilla(R.id.imageView191);
        casillas[9][2] = new casilla(R.id.imageView190);
        casillas[9][3] = new casilla(R.id.imageView189);
        casillas[9][4] = new casilla(R.id.imageView188);
        casillas[9][5] = new casilla(R.id.imageView187);
        casillas[9][6] = new casilla(R.id.imageView186);
        casillas[9][7] = new casilla(R.id.imageView185);

        casillas[10][0] = new casilla(R.id.imageView200);
        casillas[10][1] = new casilla(R.id.imageView199);
        casillas[10][2] = new casilla(R.id.imageView198);
        casillas[10][3] = new casilla(R.id.imageView197);
        casillas[10][4] = new casilla(R.id.imageView196);
        casillas[10][5] = new casilla(R.id.imageView195);
        casillas[10][6] = new casilla(R.id.imageView194);
        casillas[10][7] = new casilla(R.id.imageView193);

        casillas[11][0] = new casilla(R.id.imageView208);
        casillas[11][1] = new casilla(R.id.imageView207);
        casillas[11][2] = new casilla(R.id.imageView206);
        casillas[11][3] = new casilla(R.id.imageView205);
        casillas[11][4] = new casilla(R.id.imageView204);
        casillas[11][5] = new casilla(R.id.imageView203);
        casillas[11][6] = new casilla(R.id.imageView202);
        casillas[11][7] = new casilla(R.id.imageView201);

        casillas[12][0] = new casilla(R.id.imageView216);
        casillas[12][1] = new casilla(R.id.imageView215);
        casillas[12][2] = new casilla(R.id.imageView214);
        casillas[12][3] = new casilla(R.id.imageView213);
        casillas[12][4] = new casilla(R.id.imageView212);
        casillas[12][5] = new casilla(R.id.imageView211);
        casillas[12][6] = new casilla(R.id.imageView210);
        casillas[12][7] = new casilla(R.id.imageView209);

        casillas[13][0] = new casilla(R.id.imageView224);
        casillas[13][1] = new casilla(R.id.imageView223);
        casillas[13][2] = new casilla(R.id.imageView222);
        casillas[13][3] = new casilla(R.id.imageView221);
        casillas[13][4] = new casilla(R.id.imageView220);
        casillas[13][5] = new casilla(R.id.imageView219);
        casillas[13][6] = new casilla(R.id.imageView218);
        casillas[13][7] = new casilla(R.id.imageView217);

        casillas[14][0] = new casilla(R.id.imageView232);
        casillas[14][1] = new casilla(R.id.imageView231);
        casillas[14][2] = new casilla(R.id.imageView230);
        casillas[14][3] = new casilla(R.id.imageView229);
        casillas[14][4] = new casilla(R.id.imageView228);
        casillas[14][5] = new casilla(R.id.imageView227);
        casillas[14][6] = new casilla(R.id.imageView226);
        casillas[14][7] = new casilla(R.id.imageView225);

        casillas[15][0] = new casilla(R.id.imageView111);
        casillas[15][1] = new casilla(R.id.imageView239);
        casillas[15][2] = new casilla(R.id.imageView238);
        casillas[15][3] = new casilla(R.id.imageView237);
        casillas[15][4] = new casilla(R.id.imageView236);
        casillas[15][5] = new casilla(R.id.imageView235);
        casillas[15][6] = new casilla(R.id.imageView234);
        casillas[15][7] = new casilla(R.id.imageView233);
    }

    class virus {
        int color=0;
        int x, y;

        virus(int x, int y, int color) {
            this.x = x;
            this.y = y;
            this.color = color;
            casillas[y][x].color = color;
            casillas[y][x].virus = true;
            casillas[y][x].tiene_algo = true;
            if (color == 1) {
                casillas[y][x].v.setImageResource(R.drawable.virusr);
            } else if (color == 2) {
                casillas[y][x].v.setImageResource(R.drawable.virusb);
            } else if (color == 3) {
                casillas[y][x].v.setImageResource(R.drawable.virusy);
            }
        }
    }


    void flotantes(int i, int f) {
        if (casillas[casillas[i][f].amigoy][casillas[i][f].amigox].tiene_algo == false && casillas[i][f].virus == false && casillas[i][f].tiene_algo == true && casillas[i][f].tiene_amigo == true && casillas[i - 1][f].tiene_algo == false) {
            int colores = casillas[i][f].color;
            casillas[i][f].v.setImageResource(R.drawable.vacio);
            casillas[i][f].tiene_algo = false;
            casillas[i][f].tiene_amigo = false;
            casillas[i][f].color = 0;


            if (colores == 1) {
                casillas[i - 1][f].v.setImageResource(R.drawable.red);
            } else if (colores == 2) {
                casillas[i - 1][f].v.setImageResource(R.drawable.blue);
            } else if (colores == 3) {
                casillas[i - 1][f].v.setImageResource(R.drawable.yellow);
            }
            casillas[i - 1][f].color = colores;
            casillas[i - 1][f].tiene_algo = true;
            casillas[i - 1][f].tiene_amigo = false;
        } else if (casillas[i][f].virus == false && casillas[i][f].tiene_amigo == false && casillas[i][f].tiene_algo == true && casillas[i - 1][f].tiene_algo == false) {
            int colores = casillas[i][f].color;
            casillas[i][f].v.setImageResource(R.drawable.vacio);
            casillas[i][f].tiene_algo = false;
            casillas[i][f].color = 0;

            if (colores == 1) {
                casillas[i - 1][f].v.setImageResource(R.drawable.red);
            } else if (colores == 2) {
                casillas[i - 1][f].v.setImageResource(R.drawable.blue);
            } else if (colores == 3) {
                casillas[i - 1][f].v.setImageResource(R.drawable.yellow);
            }
            casillas[i - 1][f].color = colores;
            casillas[i - 1][f].tiene_algo = true;
            casillas[i - 1][f].tiene_amigo = false;
        }
    }

    void flotante_pildora(int i, int f) {
        if (casillas[i][f].tiene_algo && casillas[i][f + 1] == casillas[casillas[i][f].amigoy][casillas[i][f].amigox] && casillas[i][f + 1].tiene_algo && casillas[i - 1][f].tiene_algo == false && casillas[i - 1][f + 1].tiene_algo == false && casillas[i][f].tiene_amigo == true) {
            int colores = casillas[i][f].color;
            casillas[i][f].v.setImageResource(R.drawable.vacio);
            casillas[i][f].tiene_algo = false;
            casillas[i][f].tiene_amigo = false;
            casillas[i][f].color = 0;
            if (colores == 1) {
                casillas[i - 1][f].v.setImageResource(R.drawable.red);
            } else if (colores == 2) {
                casillas[i - 1][f].v.setImageResource(R.drawable.blue);
            } else if (colores == 3) {
                casillas[i - 1][f].v.setImageResource(R.drawable.yellow);
            }
            casillas[i - 1][f].color = colores;
            casillas[i - 1][f].tiene_algo = true;
            casillas[i - 1][f].tiene_amigo = true;

            int colores2 = casillas[i][f + 1].color;
            casillas[i][f + 1].v.setImageResource(R.drawable.vacio);
            casillas[i][f + 1].tiene_algo = false;
            casillas[i][f + 1].tiene_amigo = false;
            casillas[i][f + 1].color = 0;
            if (colores2 == 1) {
                casillas[i - 1][f + 1].v.setImageResource(R.drawable.red);
            } else if (colores2 == 2) {
                casillas[i - 1][f + 1].v.setImageResource(R.drawable.blue);
            } else if (colores2 == 3) {
                casillas[i - 1][f + 1].v.setImageResource(R.drawable.yellow);
            }
            casillas[i - 1][f + 1].color = colores;
            casillas[i - 1][f + 1].tiene_algo = true;
            casillas[i - 1][f + 1].tiene_amigo = true;
        } else if (i > 1) {
            if (casillas[i][f].tiene_algo && casillas[i - 1][f] == casillas[casillas[i][f].amigoy][casillas[i][f].amigox] && casillas[i - 1][f].tiene_algo && casillas[i - 2][f].tiene_algo == false && casillas[i][f].tiene_amigo == true) {
                int colores = casillas[i][f].color;
                int colores2 = casillas[i - 1][f].color;
                casillas[i][f].v.setImageResource(R.drawable.vacio);
                casillas[i][f].tiene_algo = false;
                casillas[i][f].tiene_amigo = false;
                casillas[i][f].color = 0;
                if (colores == 1) {
                    casillas[i - 1][f].v.setImageResource(R.drawable.red);
                } else if (colores == 2) {
                    casillas[i - 1][f].v.setImageResource(R.drawable.blue);
                } else if (colores == 3) {
                    casillas[i - 1][f].v.setImageResource(R.drawable.yellow);
                }
                casillas[i - 1][f].color = colores;
                casillas[i - 1][f].tiene_algo = true;
                casillas[i - 1][f].tiene_amigo = true;

                if (colores2 == 1) {
                    casillas[i - 2][f].v.setImageResource(R.drawable.red);
                } else if (colores2 == 2) {
                    casillas[i - 2][f].v.setImageResource(R.drawable.blue);
                } else if (colores2 == 3) {
                    casillas[i - 2][f].v.setImageResource(R.drawable.yellow);
                }
                casillas[i - 2][f].color = colores2;
                casillas[i - 2][f].tiene_algo = true;
                casillas[i - 2][f].tiene_amigo = true;
            }
        }
    }

    void eliminadorh(int i, int f, int color) {
        int lineash;
        lineash = 0;
        for (int h = 0; (f + h) <= 7; h++) {
            if (casillas[i][f + h].color == color && color != 0) {
                lineash++;
            } else break;
        }
        if (lineash >= 4) {
            if (lineash == 4) {
                npuntaje = npuntaje + 400;
                puntaje.setText("" + npuntaje);
            } else {
                npuntaje = npuntaje + 600;
                puntaje.setText("" + npuntaje);
            }
            for (int h = 0; (f + h) <= 7; h++) {
                if (casillas[i][f + h].color == color && color != 0) {
                    casillas[i][f + h].v.setImageResource(R.drawable.vacio);
                    casillas[i][f + h].tiene_algo = false;
                    casillas[i][f + h].color = 0;
                } else break;
            }
        }
    }

    void eliminadorv(int i, int f, int color) {
        int lineash;
        lineash = 0;
        for (int h = 0; (i + h) <= 15; h++) {
            if (casillas[i + h][f].color == color && color != 0) {
                lineash++;
            } else break;
        }
        if (lineash >= 4) {
            if (lineash == 4) {
                npuntaje = npuntaje + 400;
                puntaje.setText("" + npuntaje);
            } else {
                npuntaje = npuntaje + 600;
                puntaje.setText("" + npuntaje);
            }
            for (int h = 0; (i + h) <= 15; h++) {
                if (casillas[i + h][f].color == color && color != 0) {
                    casillas[i + h][f].v.setImageResource(R.drawable.vacio);
                    casillas[i + h][f].tiene_algo = false;
                    casillas[i + h][f].color = 0;
                } else break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about) {
            Toast t = Toast.makeText(getApplicationContext(),
                    "Proyecto final Dr FROGO.por Jose Quilodran 2017", Toast.LENGTH_SHORT);
            t.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
            t.show();
            return true;
        }
        if (id == R.id.select) {
            Intent t = new Intent(this, levels_1p.class);
            startActivity(t);
            finish();
            return true;
        }
        if (id == R.id.menu) {
            Intent t = new Intent(this, MainActivity.class);
            startActivity(t);
            finish();
            return true;
        }
        if (id == R.id.sonido) {
            if (mp.isPlaying() == true) {
                mp.pause();
            } else if (mp.isPlaying() == false) {
                mp.start();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void fallo() {
        if (casillas[14][3].tiene_algo == true || casillas[14][4].tiene_algo == true) {
            fallo.setVisibility(View.VISIBLE);

                mp.stop();
                gameover.start();

            tiempo = 300000;
        }
    }

    void victoria() {
        if (nvirus == 0) {
            t2 = new Intent(motor_sound.this, motor_sound.class);
            fallo.setImageResource(R.drawable.win);
            fallo.setVisibility(View.VISIBLE);
            if (mp.isPlaying()) {
                mp.stop();
                victory.start();
            }
            tiempo = 300000;
        }
    }


    void creador_virus(int numero) {

        while (numero != 0) {
            int randoy = (int) (Math.random() * (0 - 7) + 7);
            int randox = (int) (Math.random() * (0 - 8) + 8);
            int randov = (int) (Math.random() * (1 - 4) + 4);
            if (casillas[randox][randoy].virus != true && randov!=0 && randov !=4){
                virus v = new virus(randox, randoy, randov);
                numero--;
            }
        }
    }

    int contador_virus() {
        int cantidad = 0;
        for (int i = 0; i <= 15; i++) {
            for (int f = 0; f <= 7; f++) {
                if (casillas[i][f].virus == true) {
                    cantidad++;
                }

            }
        }
        return cantidad;
    }
}