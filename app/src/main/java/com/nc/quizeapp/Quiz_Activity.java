package com.nc.quizeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Quiz_Activity extends AppCompatActivity {
    TextView question_counter_show, count_down_text_view, question_show;
    public static final long COUNTDOWN_IN_MILLSECOND = 20000;
    RadioGroup radioGroup;
    RadioButton option1, option2, option3, option4;
    Button next;
    Drawable textcolorDefualt;
    List<Question> list;
    int questionCounter;
    int questioncounterTotal;
    Question currentQuestion;
    int score;
    boolean answered;

    private ColorStateList textColorDefualtCd;
    private CountDownTimer countDownTimer;
    private long timeleftInMinllis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        question_counter_show = findViewById(R.id.question_count_show);
        count_down_text_view = findViewById(R.id.countdown);
        question_show = findViewById(R.id.text_view_question);
        radioGroup = findViewById(R.id.radio_group);
        option1 = findViewById(R.id.radio_button1);
        option2 = findViewById(R.id.radio_button2);
        option3 = findViewById(R.id.radio_button3);
        option4 = findViewById(R.id.radio_button4);
        next = findViewById(R.id.button_next);
        textcolorDefualt = next.getBackground();
        list = new ArrayList<>();
        SetQuesttion();

        textColorDefualtCd = count_down_text_view.getTextColors();

        questioncounterTotal = list.size();
        Collections.shuffle(list);

        showNextQuestion();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answered) {
                    if (option1.isChecked() || option2.isChecked() || option3.isChecked() || option4.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Select An Answere", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void checkAnswer() {
        answered = true;

        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNr = radioGroup.indexOfChild(rbSelected) + 1;
        if (answerNr == currentQuestion.getAnswer_number()) {
            score++;
        }
        showNextQuestion();
    }

    private void showNextQuestion() {
        radioGroup.clearCheck();
        if (questionCounter < questioncounterTotal) {
            currentQuestion = list.get(questionCounter);
            question_show.setText(currentQuestion.getQuestion());
            option1.setText(currentQuestion.getOption1());
            option2.setText(currentQuestion.getOption2());
            option3.setText(currentQuestion.getOption3());
            option4.setText(currentQuestion.getOption4());

            questionCounter++;
            question_counter_show.setText("Question: " + questionCounter + " /" + questioncounterTotal);
            answered = false;
            timeleftInMinllis = COUNTDOWN_IN_MILLSECOND;
            startCountDown();

            if (questionCounter == list.size())
                next.setText("finish");
        } else {
            finish_Quiz();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeleftInMinllis, 1000) {
            @Override
            public void onTick(long l) {
                timeleftInMinllis = l;
                updadateCounDowinText();
            }

            @Override
            public void onFinish() {
                    timeleftInMinllis=0;
                    updadateCounDowinText();
                    checkAnswer();
            }
        }.start();
    }

    private void updadateCounDowinText() {
        int minutes=(int)(timeleftInMinllis/1000)/60;
        int second=(int)(timeleftInMinllis/1000)%60;
        String timeFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,second);
        count_down_text_view.setText(timeFormatted);

        if(timeleftInMinllis<10000)
        {
            count_down_text_view.setTextColor(Color.RED);
        }else
        {
            count_down_text_view.setTextColor(textColorDefualtCd);
        }
    }

    private void finish_Quiz() {
        //next.setText("Restart");
        next.setEnabled(false);
        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        bundle.putInt("total", questioncounterTotal);

        endQuiz endQuiz = new endQuiz();
        endQuiz.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.relativerlayout, endQuiz);
        transaction.commit();

        // finish();

    }

    private void SetQuesttion() {
        Question q1 = new Question(" Java Developed By ?", " Bjarne Stroustrup", " Jamse Gosling", " Dennis Ritchie", " Tony Stark", 2);
        Question q2 = new Question(" Which year C introduce ?", " 1970", " 1978", " 1972", " 1960", 3);
        Question q3 = new Question(" Servlet is a ?", " class", " interface", " Object", " None", 1);
        Question q4 = new Question(" DP stands for ?", " Density Picture", " Density Pixels", " Defualt Screen", " Density Independent Pixels", 4);
        Question q5 = new Question(" Android is  ?", " An Operating System", " A Web Server", " A Web Browser", " None", 1);
        list.add(q1);
        list.add(q2);
        list.add(q3);
        list.add(q4);
        list.add(q5);
    }


    public  void onDestroy()
    {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }
}