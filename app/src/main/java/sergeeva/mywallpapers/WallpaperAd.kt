package sergeeva.mywallpapers

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class WallpaperAd(val list: List<SelectionWallpaper.WallpaperData>,val context: Context) : RecyclerView.Adapter<viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.img_dis,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        Glide.with(context)
            .load(list[position].smallimg).transition(DrawableTransitionOptions.withCrossFade(500))
            .listener(object :RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(holder.previmgview11)

        holder.downloadtext.text = list[position].downloads
        holder.viewtext.text = list[position].views

        holder.previmgview11.setOnClickListener(View.OnClickListener {
            val intent = Intent(context?.applicationContext,SelectionImage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("largeurl",list[position].largeimg)
            context.startActivity(intent)
        })
    }
    override fun getItemCount(): Int {
        return list.size
    }

}

class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val previmgview11 = itemView.findViewById<ImageView>(R.id.prevImg)
    val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
    val downloadtext = itemView.findViewById<TextView>(R.id.downloadtv)
    val viewtext = itemView.findViewById<TextView>(R.id.viewstv)
}
