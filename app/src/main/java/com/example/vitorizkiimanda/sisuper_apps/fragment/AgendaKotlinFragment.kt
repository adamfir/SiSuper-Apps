package com.example.vitorizkiimanda.sisuper_apps.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vitorizkiimanda.sisuper_apps.R
import com.example.vitorizkiimanda.sisuper_apps.adapter.AgendaAdapter
import com.example.vitorizkiimanda.sisuper_apps.adapter.EventListAdapter
import com.example.vitorizkiimanda.sisuper_apps.database.AgendaEvent
import com.example.vitorizkiimanda.sisuper_apps.database.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AgendaKotlinFragment : Fragment() {


    private var events: MutableList<AgendaEvent> = mutableListOf()
    private lateinit var adapter: AgendaAdapter
    private lateinit var listMatch: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_event_list, container, false)

        //binding
        listMatch = view.findViewById(R.id.event_list)
        adapter = AgendaAdapter(events)
        listMatch.adapter = adapter

        //layout manager
        listMatch.layoutManager = LinearLayoutManager(context)


        activity!!.setTitle("Agenda")
        showFavorite()
        return view
    }

    private fun showFavorite(){
        events.clear()
        context?.database?.use {
            val result = select(AgendaEvent.TABLE_AGENDA_EVENT)
            val favorite = result.parseList(classParser<AgendaEvent>())
            events.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }


}
