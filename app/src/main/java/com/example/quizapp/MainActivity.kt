package com.example.quizapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rightAnswer: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1
    private val QUIZ_COUNT = 5

    private val quizData = mutableListOf(
        mutableListOf("「コンテンツの意味を選べ」", "中身", "外側", "宣伝", "重鎮"),
        mutableListOf("「ハレーションの意味を選べ」", "悪影響", "良影響", "改善", "改悪"),
        mutableListOf("「コモディティ化の意味を選べ」", "一般化", "特別化", "義務化", "自由化"),
        mutableListOf("「ASAPの意味を選べ」", "できるだけ早く", "余裕をもって", "しっかり調査して", "積極的に"),
        mutableListOf("「バッファの意味を選べ」", "余裕", "強気", "振り分け", "拡張"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        quizData.shuffle()

        showNextQuiz()
    }

    // 解答ボタンが押されたら呼ばれる
    fun checkAnswer(view:View) {
        // どの解答ボタンが押されたか
        val answerBtn:Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()

        // ダイアログのタイトルを作成
        val alertTitle: String
        if (btnText == rightAnswer) {
            alertTitle = "正解！"
            rightAnswerCount++
        } else {
            alertTitle = "不正解..."
        }

        // ダイアログを作成
        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("答え : $rightAnswer")
            .setPositiveButton("OK") { dialogInterface, i ->
                checkQuizCount()
            }
            .setCancelable(false)
            .show()
    }

    // 出題数をチェックする
    fun checkQuizCount() {
        if (quizCount == QUIZ_COUNT) {
            // 結果画面を表示
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)

        } else {
            quizCount++
            showNextQuiz()
        }
    }

    fun showNextQuiz() {
        binding.countLabel.text = getString(R.string.count_label, quizCount)

        val quiz = quizData[0]

        binding.questionLabel.text = quiz[0]

        rightAnswer = quiz[1]

        quiz.removeAt(0)

        quiz.shuffle()

        binding.answerBtn1.text = quiz[0]
        binding.answerBtn2.text = quiz[1]
        binding.answerBtn3.text = quiz[2]
        binding.answerBtn4.text = quiz[3]

        quizData.removeAt(0)
    }
}