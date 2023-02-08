package sergeeva.mywallpapers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CategoriesAd(val mainitems:List<MainActivity.CategoriesData>, val context: Context) : RecyclerView.Adapter<categoriesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item,parent,false)
        return categoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: categoriesViewHolder, position: Int) {

        holder.textview.text = mainitems[position].category
        Glide.with(context)
            .load(mainitems[position].resource)
            .into(holder.imgview)

        holder.imgview.setOnClickListener(View.OnClickListener {
            val intent = Intent(context?.applicationContext, SelectionWallpaper::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("categoryString",mainitems[position].category)
            context.startActivity(intent)
        })


    }

    override fun getItemCount(): Int {
        return mainitems.size
    }


}

class categoriesViewHolder(view: View): RecyclerView.ViewHolder(view){
    val textview = view.findViewById<TextView>(R.id.textViewId)
    val imgview = view.findViewById<ImageView>(R.id.imageViewId)

}