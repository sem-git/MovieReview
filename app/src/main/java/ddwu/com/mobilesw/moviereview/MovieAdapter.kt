package ddwu.com.mobilesw.moviereview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobilesw.moviereview.data.MovieDto
import ddwu.com.mobilesw.moviereview.databinding.ListItemBinding

class MovieAdapter (val movies : ArrayList<MovieDto>)
    : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    val TAG = "MovieAdapter"

    // 데이터 개수 확인
    override fun getItemCount(): Int = movies.size

    // 각 item view 의 view holder 생성 시 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    // 각 item view 의 항목에 데이터 결합 시 호출
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.itemBinding.ivPoster.setImageResource(movies[position].poster)
        holder.itemBinding.tvTitle.text = movies[position].title
        holder.itemBinding.tvYear.text = movies[position].year
        holder.itemBinding.tvDirector.text = movies[position].director
        holder.itemBinding.tvActors.text = movies[position].actors
    }

    inner class MovieViewHolder(val itemBinding: ListItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
            init {
                itemBinding.root.setOnClickListener{
                    listener?.onItemClick(it, adapterPosition)
                    Log.d(TAG, movies[adapterPosition].toString())
                }
                itemBinding.root.setOnLongClickListener {
                    Log.d(TAG, "long Click")
                    val result = longClickListener?.onItemLongClick(it, adapterPosition)
                    true
                }
            }
        }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int) : Boolean
    }
    var longClickListener: OnItemLongClickListener? = null

    fun setOnItemLongClickListener (listener: OnItemLongClickListener?) {
        this.longClickListener = listener
    }

    var listener : OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view : View, position : Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }
}



