package com.example.catapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.catapp.viewmodels.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentSecond : Fragment() {

    val sharedViewModel: MainViewModel by activityViewModels()

 //kreiranje layout-a za fragment: prosledi se ime layout-a u konstruktor ili se overajduje funkcija onCreateView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        var email = view.findViewById<TextView>(R.id.email)
        val someEmail = view.findViewById<TextView>(R.id.some_email)
        sharedViewModel.comment.observe({lifecycle}){
            email.text = it.email
            someEmail.isVisible=true
        }
       return view
    }
}