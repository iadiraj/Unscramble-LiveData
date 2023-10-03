package com.example.unscramble

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameFragment : Fragment() {
    private val viewModel: GameViewModel by activityViewModels()
    lateinit var gameScore : TextView
    lateinit var wordCounter : TextView
    lateinit var unScrambledWord : TextView
    lateinit var userInput : EditText
    lateinit var skipButton : Button
    lateinit var submitButton : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameScore = view.findViewById(R.id.game_score)
        wordCounter = view.findViewById(R.id.word_counter)
        unScrambledWord = view.findViewById(R.id.unscrambled_word_text)
        userInput = view.findViewById(R.id.word_input)
        skipButton = view.findViewById(R.id.skip_button)
        submitButton = view.findViewById(R.id.submit_button)
        submitButton.setOnClickListener{
            onSubmit()
        }
        skipButton.setOnClickListener{
            onSkip()
        }
        viewModel.score.observe(viewLifecycleOwner){
            newScore -> gameScore.text = getString(R.string.game_score, newScore)
        }
        viewModel.currentWordCount.observe(viewLifecycleOwner){
            newCount -> wordCounter.text = getString(R.string.count, newCount, MAX_NO_OF_WORDS)
        }
        viewModel.currentScrambleWord.observe(viewLifecycleOwner){
            newWord -> unScrambledWord.text = newWord
        }
        viewModel.getNextWord()
    }
    private fun onSubmit(){
        val userWord = userInput.text.toString()
        if(viewModel.isWordCorrect(userWord)){
            Toast.makeText(context, "CORRECT😊", Toast.LENGTH_SHORT).show()
            if(!viewModel.nextWord()){
                showFinalScore()
            }else{
                viewModel.getNextWord()
            }
        }else{
            Toast.makeText(context, "OOPS! WRONG ANSWER.", Toast.LENGTH_SHORT).show()
        }
        userInput.text.clear()
    }
    private fun onSkip(){
        if(!viewModel.nextWord()){
            showFinalScore()
        }
        viewModel.getNextWord()
        userInput.text.clear()
    }
    private fun showFinalScore(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, viewModel.score.value))
            .setCancelable(true)
            .setNegativeButton(getString(R.string.exit)){_, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)){_, _ ->
                restart()
            }
            .show()
        Toast.makeText(context, "${viewModel.bestScore.value}", Toast.LENGTH_SHORT).show()
    }
    private fun restart(){
        viewModel.updateBestScore()
        viewModel.restart()
        userInput.text.clear()
    }
    private fun exitGame(){
        viewModel.updateBestScore()
        viewModel.reset()
        activity?.supportFragmentManager?.popBackStack()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.updateBestScore()
    }
}