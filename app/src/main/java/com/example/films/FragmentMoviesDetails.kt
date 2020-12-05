package com.example.films

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class FragmentMoviesDetails : Fragment() {

    private var listener: TransactionsFragmentMDClicks? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)

        view.findViewById<TextView>(R.id.tv_back).apply {
            setOnClickListener{
                listener?.backOnClick()
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TransactionsFragmentMDClicks){
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface TransactionsFragmentMDClicks {
        fun backOnClick()
    }

}