package com.example.films.movieDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.films.R
import com.example.films.model.dataClasses.Actor

class ActorsListAdapter(
    context: Context,
    private var actors: List<Actor>
) : RecyclerView.Adapter<ActorsListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ActorsListViewHolder {

        return ActorsListViewHolder(
            inflater.inflate(
                R.layout.view_holder_actor,
                parent,
                false
            )
        )
    }

    fun updateActors(newActors: List<Actor>) {
        actors = newActors
        notifyDataSetChanged()
    }

    override fun getItemCount() = actors.size

    override fun onBindViewHolder(holder: ActorsListViewHolder, position: Int) {
        holder.bind(actors[position])
    }

}

class ActorsListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvName: TextView = view.findViewById(R.id.tv_actors_list_actor_name)
    private val ivPhoto: ImageView = view.findViewById(R.id.iv_actors_list_actor_photo)

    fun bind(actor: Actor) {

        tvName.text = actor.name

        if (actor.imageUrl != null) {
            Glide.with(itemView.context)
                .load(actor.imageUrl)
                .into(ivPhoto)
        }
    }
}