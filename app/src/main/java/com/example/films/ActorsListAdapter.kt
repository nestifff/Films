package com.example.films

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class ActorsListAdapter (
    context: Context,
    var actors : List<Actor>
) : RecyclerView.Adapter<ActorsListViewHolder>() {

    private val inflater : LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount() = actors.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsListViewHolder {
        return ActorsListViewHolder(inflater.inflate(R.layout.view_holder_actor, parent, false))
    }

    override fun onBindViewHolder(holder: ActorsListViewHolder, position: Int) {
        holder.bind(actors[position])
    }

}

class ActorsListViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val tvName : TextView = view.findViewById(R.id.tv_actors_list_actor_name)
    private val ivPhoto : ShapeableImageView = view.findViewById(R.id.iv_actors_list_actor_photo)

    fun bind(actor: Actor) {
        tvName.text = actor.name
        ivPhoto.setImageResource(actor.photoResource)
    }
}