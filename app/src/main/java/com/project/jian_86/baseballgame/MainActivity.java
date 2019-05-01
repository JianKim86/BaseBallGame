package com.project.jian_86.baseballgame;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tv_ball00, tv_ball01,tv_ball02; //tv_ball
    TextView tv_result;//resultArea
    ScrollView scrollView;
    ImageView[] nums = new ImageView[10];//numButton
    ImageView del,restart;

    ImageView hit;//hitButton
    int count;//check
    Random rand = new Random();
    int com1, com2, com3;
    String str=""; //input tv_result
    int hitcount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setcomNum();
        scrollView = findViewById(R.id.scrollresult);
        tv_ball00 = findViewById(R.id.tv_ball00);
        tv_ball01 = findViewById(R.id.tv_ball01);
        tv_ball02 = findViewById(R.id.tv_ball02);
        tv_result = findViewById(R.id.tv_result);
        hit = findViewById(R.id.hitbtn);
        str = tv_result.getText().toString();
        for(int i =0; i<10; i++){
            nums[i] = findViewById(R.id.num00+i);
        }//for
        del = findViewById(R.id.del);
        restart = findViewById(R.id.restart);

    }//onCreate

    public void setcomNum(){
        com1 = rand.nextInt(10);
        while (true){
            com2 = rand.nextInt(10);
            if (com1!= com2)break;
        }
        while(true) {
            com3 = rand.nextInt(10);
            if (com3 != com1 && com3 != com2) break;
        }
    }//setcomNum

    public void inputNum(ImageView iv, int count){
        for (int i = 0; i < 10; i++) {
            if (iv.equals(nums[i])) {
                switch (count){
                    case 0:
                        tv_ball00.setText(i + ""); break;
                    case 1:
                        tv_ball01.setText(i + ""); break;
                    case 2:
                        tv_ball02.setText(i + ""); break;
                }//switch
            }//if
        }//for
    }//inputNum

    public void gussNum(ImageView iv) {
        String b1 = tv_ball00.getText().toString();
        String b2 = tv_ball01.getText().toString();
        String b3 = tv_ball02.getText().toString();
        for (int i = 0; i < 10; i++) {
            if (iv.equals(nums[i])) {
                String eb = i+"";
                if(b1.equals(eb)||b2.equals(eb)||b3.equals(eb)){
                    Toast.makeText(MainActivity.this, "중복된 숫자야..! \n다시 선택하는게 어때 ~ ", Toast.LENGTH_SHORT).show();
                    del.setImageResource(R.drawable.delred);
                }
            }
        }
    }//gussNum
    public void clicknum(View view) {
        ImageView iv = (ImageView) view;
        gussNum(iv);

        count= 0;
        if(tv_ball00.getText().length()<= 0) {
            inputNum(iv, count);
        }else if(tv_ball01.getText().length()<= 0){
            count = 1;
            inputNum(iv, count);
        }else if(tv_ball02.getText().length()<= 0) {
            count = 2;
            inputNum(iv, count);
            hit.setClickable(true);
            hit.setImageResource(R.drawable.hitbtn01);
            offNumkey();
        }
    }//clicknum

    public void settingInit(){
        tv_ball00.setText("");
        tv_ball01.setText("");
        tv_ball02.setText("");
        hit.setImageResource(R.drawable.hitbtn00);
        hit.setClickable(false);
        count = 0;
        onNumkey();
        del.setClickable(true);
        restart.setImageResource(R.drawable.restartball);
    }//settingInit

    public void onNumkey(){
        for(int i =0; i<10; i++){
            nums[i].setClickable(true);
        }
    }//onnumky
    public void offNumkey(){
        for(int i =0; i<10; i++){
            nums[i].setClickable(false);
        }
    }//onnumky
    public void clickhit(View view) {

        if(count == 2){
            del.setImageResource(R.drawable.del);
            hitcount++;

            String num1 = tv_ball00.getText().toString();
            String num2 = tv_ball01.getText().toString();
            String num3 = tv_ball02.getText().toString();
            String nt = hitcount +" 회 \n"+"입력 : [ " +num1 +" , "+num2+" , "+num3+" ]";

            //정답 확인
            int strick= 0, ball = 0;
            int u1 = Integer.parseInt(num1);
            int u2 = Integer.parseInt(num2);
            int u3 = Integer.parseInt(num3);
            String result = "";
            if(com1==u1) strick ++;
            else if(com1==u2 || com1==u3) ball++;
            if(com2==u2) strick ++;
            else if(com2==u1 || com2==u3) ball++;
            if(com3==u3) strick ++;
            else if(com3==u1 || com3==u2) ball++;
            result +="결과 : [ "+strick+" 스트라이크 , "+ball+" 볼  ] 입니다.";
                    //+com1+com2+com3

            str += nt+"\n"+ result+"\n\n";
            if(strick == 3) {//str += "축하합니다!!! 정답입니다 !\n다시 게임을 즐기려면 restart 해주세요.\n";
                winDiallog();
            }
            else if(hitcount == 9) {
                loseDiallog();
                //str += "모든 턴이 끝났습니다. restart 해주세요.\n";
            }

            tv_result.setText(str);
            tv_result.setFocusableInTouchMode(true);
            tv_result.requestFocus();

            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });

            if (hitcount< 9 && strick < 3 ) settingInit();
            else {offNumkey(); del.setClickable(false); hit.setClickable(false);hit.setImageResource(R.drawable.hitbtn00); restart.setImageResource(R.drawable.restartballred);}
        }//if

    }//clickhit

    public void clickdel(View view) {
        del.setImageResource(R.drawable.del);
        onNumkey();
        del.setClickable(true);
        hit.setClickable(false);
        hit.setImageResource(R.drawable.hitbtn00);
        if(tv_ball02.getText().length()> 0) {
            tv_ball02.setText("");
        }else if(tv_ball01.getText().length()> 0){
            tv_ball01.setText("");
        }else if(tv_ball00.getText().length()> 0) {
            tv_ball00.setText("");

        }
    }//clickdel

    public void clicrestart(View view) {
        del.setImageResource(R.drawable.del);
        setcomNum();
        settingInit();
        str="게임을 시작합니다.\n\n";
        tv_result.setText("게임을 시작합니다.\n\n");
        hitcount = 0;

    }
    public void loseDiallog(){
        del.setImageResource(R.drawable.del);
        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
        ad.setTitle("LOSE");
        ad.setMessage("저런.. 정답은 "+com1+" , "+com2+" , "+com3 +"입니다! "+ hitcount+"회로 모든 기회가 끝났군요\n"+
                "다시 한번 도전해봐요!! Restart 버튼을 클릭클릭!!");
        ad.setPositiveButton("확인",null);
        ad.show();
    }
    public void winDiallog(){
        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
        ad.setTitle("WIN");
        ad.setMessage("♬축하합니다♬  " + hitcount+"회로 승리하셨군요!!\n"+
                "게임을 다시 즐기고 싶다면 Restart 버튼을 클릭클릭!!");
        ad.setPositiveButton("확인",null);
        ad.show();
    }
    public void clickhint(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
        ad.setTitle("게임설명");
        ad.setMessage(" 이 게임은 랜덤의 세자리 수를 9회 안에 맞추는 게임입니다.\n\n" +
                "랜덤 숫자에 각 자리의 수들은 서로 중복되지 않으며 " +
                "\n내가 선택한 수와 비교해 " +
                "\n자리와 숫자 모두 맞추면 스트라이크♬ 자리는 다르지만 숫자가 있다면 볼♬\n\n" +
                "이해하셨죠 ~ 주어진 기회는 총 9번 !! " +
                "\n번호판에서 3개의 숫자를 클릭하면 HIT 버튼이 활성화 됩니다.(클릭클릭!)" +
                "\n\nTip !! restart 를 클릭하면 " +
                "\n언제든 재시작이 가능합니다! (진행중 이번판은 영~ 잘못됬다 싶을때! 게임 스코어가 세상 맘에 안들때! 계속해서 게임하고 싶을때! 클릭클릭!)" +
                "\n\n\n그럼 모두 \nHAVE FUN TIME ♬");
        ad.setPositiveButton("확인",null);
        ad.show();
    }//clickhint
}//MainActivity
