package com.example.notes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.models.Note
import kotlin.random.Random

class NotesAdapter(private val context:Context,val listener: NotesClickListener):RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val NotesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return NotesList.size
    }

    fun updateList(newList :List<Note>){
        fullList.clear()
        fullList.addAll(newList)
        NotesList.clear()
        NotesList.addAll(fullList)
        notifyDataSetChanged()

    }

    fun filterList(search:String){
        NotesList.clear()
        for (item in fullList){
            if(item.title?.lowercase()?.contains(search.lowercase())==true ||
               item.note?.lowercase()?.contains(search.lowercase())==true){
                NotesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun RandomColor():Int{
        val list = ArrayList<Int>()
        list.add(R.color.notecolor1)
        list.add(R.color.notecolor2)
        list.add(R.color.notecolor3)
        list.add(R.color.notecolor4)
        list.add(R.color.notecolor5)
        list.add(R.color.notecolor6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       val currentNote = NotesList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true
        holder.note_tv.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true
        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(RandomColor(),null))
        holder.notes_layout.setOnClickListener{
            listener.onItemCLicked(NotesList[holder.adapterPosition])
        }
        holder.notes_layout.setOnLongClickListener {
            listener.onLongItemClicked(NotesList[holder.adapterPosition],holder.notes_layout)
            true
        }
    }

    inner class NoteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val notes_layout = itemView.findViewById<CardView>(R.id.card_view)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val note_tv = itemView.findViewById<TextView>(R.id.tv_note)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
    }

    interface NotesClickListener{
        fun onItemCLicked(note: Note)
        fun onLongItemClicked(note: Note,cardView: CardView)
    }
}