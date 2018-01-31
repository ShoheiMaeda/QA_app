package jp.maeda.shohei.qa_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class Favorites {


    public class QuestionDetailActivity extends AppCompatActivity {

        private ListView mListView;
        private Question mQuestion;
        private QuestionDetailListAdapter mAdapter;

        private DatabaseReference mAnswerRef;

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
