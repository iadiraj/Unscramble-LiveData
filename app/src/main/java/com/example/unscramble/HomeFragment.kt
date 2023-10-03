package com.example.unscramble

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels

class HomeFragment : Fragment() {
    lateinit var activity: MainActivity
    private val viewModel: GameViewModel by activityViewModels()
    private lateinit var gameScore : TextView
    private lateinit var bestScore : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = requireActivity() as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val startButton : Button = view.findViewById(R.id.start_button)
        gameScore = view.findViewById(R.id.score)
        bestScore = view.findViewById(R.id.best_score)
        viewModel.score.observe(viewLifecycleOwner){
            newScore -> gameScore.text = getString(R.string.score, newScore)
        }
        viewModel.bestScore.observe(viewLifecycleOwner){
            newBestScore -> bestScore.text = getString(R.string.best_score, newBestScore)
        }
        val fragmentGame = GameFragment()
        val fragmentManager = activity.supportFragmentManager.beginTransaction()
        startButton.setOnClickListener {
            viewModel.resetScore()
            fragmentManager.apply {
                replace(R.id.fragment_container, fragmentGame)
                addToBackStack("game")
                commit()
            }
        }
    }
}