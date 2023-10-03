package com.example.unscramble

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel() : ViewModel() {
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord : String

    private val _score = MutableLiveData(0)
    val score : LiveData<Int>
        get() = _score

    private var _bestScore = MutableLiveData(0)
    val bestScore : LiveData<Int>
        get() = _bestScore

    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount : LiveData<Int>
        get() = _currentWordCount

    private val _currentScrambleWord = MutableLiveData<String>()
    val currentScrambleWord : LiveData<String>
        get() = _currentScrambleWord

    private fun increaseScore(){
        _score.value = _score.value?.plus(SCORE_INCREASE)
    }
    fun updateBestScore(){
        if(_score.value!! > _bestScore.value!!){
            _bestScore.value = score.value
        }
    }
    fun isWordCorrect(userWord : String) : Boolean{
        if(userWord.equals(currentWord, true)){
            increaseScore()
            return true
        }
        return false
    }
    fun restart(){
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }
    fun reset(){
        _currentWordCount.value = 0
        wordsList.clear()
    }
    fun resetScore(){
        _score.value = 0
    }
    fun resetCounter(){
        _currentWordCount.value = 0
    }
    fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while(String(tempWord).equals(currentWord, false)){
            tempWord.shuffle()
        }
        if(wordsList.contains(currentWord)){
            getNextWord()
        }else{
            _currentScrambleWord.value = String(tempWord)
            _currentWordCount.value = _currentWordCount.value?.plus(1)
            wordsList.add(currentWord)
        }

    }
    fun nextWord() : Boolean{
        if(currentWordCount.value!! < MAX_NO_OF_WORDS){
            return true
        }
        resetCounter()
        return false
    }

}