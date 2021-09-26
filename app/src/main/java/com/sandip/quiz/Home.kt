package com.sandip.quiz

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sandip.quiz.Activity.QuestionActivity
import com.sandip.quiz.R
import com.sandip.quiz.adapters.homeadapter
import com.sandip.quiz.model.quiz
import com.sandip.quiz.utils.Colorpicker
import com.sandip.quiz.utils.IconPicker
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var recyclerHome: RecyclerView
    lateinit var layoutManager: GridLayoutManager
    lateinit var Homeadaptar: homeadapter
    lateinit var firestore: FirebaseFirestore


    private var quizlist = mutableListOf<quiz>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)


        recyclerHome = view.findViewById(R.id.recyclerviewhome)
        layoutManager = GridLayoutManager(activity,2)

        firestore = FirebaseFirestore.getInstance()
        val collectionReference:CollectionReference = firestore.collection("Quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null){
                Toast.makeText(activity,"Error fetching data",Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }else {
                Log.d("DATA", value.toObjects(quiz::class.java).toString())
                quizlist.addAll(value.toObjects(quiz::class.java))
                Homeadaptar.notifyDataSetChanged()
            }
        }

        Homeadaptar = homeadapter(activity as Context, quizlist)
        recyclerHome.adapter = Homeadaptar

        recyclerHome.layoutManager = layoutManager

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


