package jp.maeda.shohei.qa_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.ArrayList;

public class Favorites extends AppCompatActivity{

        private ListView mListView;
        private Question mQuestion;
        private QuestionDetailListAdapter mAdapter;
        private boolean mFavorite;

        private DatabaseReference mAnswerRef;
        private DatabaseReference mDatabaseReference;
        private ArrayList<Question> mQuestionArrayList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Firebase
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        // ListViewの準備
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new QuestionDetailListAdapter(this,mQuestionArrayList);
        mQuestionArrayList = new ArrayList<Question>();
        mAdapter.notifyDataSetChanged();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Questionのインスタンスを渡して質問詳細画面を起動する
                Intent intent = new Intent(getApplicationContext(), QuestionDetailActivity.class);
                intent.putExtra("question", mQuestionArrayList.get(position));
                startActivity(intent);
            }
        });

    }


    private ChildEventListener mEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap map = (HashMap) dataSnapshot.getValue();

                String answerUid = dataSnapshot.getKey();

                for (Answer answer : mQuestion.getAnswers()) {
                    // 同じAnswerUidのものが存在しているときは何もしない
                    if (answerUid.equals(answer.getAnswerUid())) {
                        return;
                    }
                }

                String body = (String) map.get("body");
                String name = (String) map.get("name");
                String uid = (String) map.get("uid");

                Answer answer = new Answer(body, name, uid, answerUid);
                mQuestion.getAnswers().add(answer);
                mAdapter.notifyDataSetChanged();

                Boolean favorite = (Boolean) map.get("favorite");

                Button button1 =(Button) findViewById(R.id.button1);
                Button button2 =(Button) findViewById(R.id.button1);

                if(favorite == true) {
                    button1.setVisibility(View.INVISIBLE);
                    button2.setVisibility(View.VISIBLE);
                }else if(favorite == false){
                    button1.setVisibility(View.INVISIBLE);
                    button2.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }
}
