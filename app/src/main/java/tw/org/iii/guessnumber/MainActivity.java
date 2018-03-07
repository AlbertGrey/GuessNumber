package tw.org.iii.guessnumber;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView log,mesg;
    private EditText input;
    private Button guess;
    private String answer;
    private Button reset,set;
    private int guesstime = 0;
    private int count = guesstime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = findViewById(R.id.log);
        input = findViewById(R.id.input);
        mesg = findViewById(R.id.mesg);
        guess = findViewById(R.id.guess);
        guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                doGuess();
                System.out.print(count);
            }
        });
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGame();
            }
        });
        set = findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceDialog();
            }
        });
        mesg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesg.setVisibility(View.GONE);
            }
        });

        initGame();
    }


    private void initGame(){
//        choiceDialog();
        answer = createAnswer(3);
        input.setText("");
        log.setText("");
//        answer = "123";
        Log.v("init", answer);
        System.out.print(answer);
    }

    private void doGuess(){
        String strInput = input.getText().toString();
        String result = checkAB(answer, strInput);
        if(count==9){
            input.setText("");
            log.append(strInput + ":" + result + "\n");
            Toast.makeText(MainActivity.this,"已經猜了9次,剩下最後一次機會",Toast.LENGTH_LONG).show();
            if (result.equals("3A0B")){
                showDialog();
            }
        }if(count==10){
            count = guesstime;
            loseDialog();
        }else{
            input.setText("");
            log.append(strInput + ":" + result + "\n");
            if (result.equals("3A0B")){
                showDialog();
            }
        }
    }

    private void showDialog(){
        //mesg.setVisibility(View.VISIBLE);
        AlertDialog alert = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WINNER");
        builder.setMessage("恭喜老爺,賀喜夫人");
        builder.setCancelable(false);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initGame();
            }
        });
        alert = builder.create();
        alert.show();
    }
    private void loseDialog(){
        AlertDialog losedialog = null;
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("LOSER");
        builder2.setCancelable(false);
        builder2.setPositiveButton("restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initGame();
            }
        });
        losedialog = builder2.create();
        losedialog.show();
    }
    private void choiceDialog(){
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        final View v = inflater.inflate(R.layout.diolog_rbutton,null);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("choice")
                .setView(v)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RadioButton radioButton3 = (RadioButton) (v.findViewById(R.id.threen));
                        RadioButton radioButton4 = (RadioButton) (v.findViewById(R.id.fourn));
                        RadioButton radioButton5 = (RadioButton) (v.findViewById(R.id.fiven));
                        RadioGroup radioGroup = (RadioGroup)(v.findViewById(R.id.radioGroup));
                            switch (radioGroup.getCheckedRadioButtonId()){
                                case R.id.threen:
                                    answer = createAnswer(3);
                                    input.setText("");
                                    log.setText("");
                                    Log.v("grey3",answer);
                                    System.out.print(answer);
                                    break;
                                case R.id.fourn:
                                    answer = createAnswer(4);
                                    input.setText("");
                                    log.setText("");
                                    Log.v("grey4",answer);
                                    System.out.print(answer);
                                    break;
                                case R.id.fiven:
                                    answer = createAnswer(5);
                                    input.setText("");
                                    log.setText("");
                                    Log.v("grey5",answer);
                                    System.out.print(answer);
                                    break;
                                default:
                                    return;
                            }
//                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(RadioGroup radioGroup, int radioButton) {
//                                switch (radioButton){
//                                    case radioButton3:
//                                        answer = createAnswer(3);
//                                        input.setText("");
//                                        log.setText("");
//                                        Log.v("grey3", answer);
//                                        System.out.print(answer);
//                                        break;
//                                    case R.id.fourn:
//                                        answer = createAnswer(4);
//                                        input.setText("");
//                                        log.setText("");
//                                        Log.v("grey4", answer);
//                                        System.out.print(answer);
//                                        break;
//                                    case R.id.fiven:
//                                        answer = createAnswer(5);
//                                        input.setText("");
//                                        log.setText("");
//                                        Log.v("grey5", answer);
//                                        System.out.print(answer);
//                                        break;
//                                }
//                            }
//                        });
                    }
                }).show();
    }
    static String checkAB(String a, String g) {
        int A, B; A = B = 0;

        for (int i=0 ;i<a.length(); i++) {
            if (g.charAt(i) == a.charAt(i)) {
                A++;
            }else if (a.indexOf(g.charAt(i)) != -1) {
                B++;
            }
        }
        return A + "A" + B +"B";
    }
    static String createAnswer(int d) {
        int[] poker = new int[10];
        for (int i=0; i<poker.length; i++) poker[i] = i;
        for (int i=poker.length; i>0; i--) {
            int rand = (int)(Math.random()*i);
            int temp = poker[rand];
            poker[rand] = poker[i-1];
            poker[i-1] = temp;
        }
        String ret = "";
        for (int i=0; i<d; i++) {
            ret += poker[i];
        }
        return ret;
    }
    public void end(View view) {
        finish();
    }
}
