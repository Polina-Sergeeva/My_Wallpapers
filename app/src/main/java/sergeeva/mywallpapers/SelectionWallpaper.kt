package sergeeva.mywallpapers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SelectionWallpaper : AppCompatActivity() {
    val list = ArrayList<WallpaperData>()
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_wallpaper)

        recyclerView = findViewById<RecyclerView>(R.id.wallpaperRecycler)

        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val textView = findViewById<TextView>(R.id.textCategoryId)
        val string = intent.getStringExtra("categoryString")

        val key = "33106230-b104905cd7ff74ed17e2229af"
        var url: String = ""

        if (string != null) {
            url =
                "https://pixabay.com/api/?key=${key}&image_type=all&safesearch=true&orientation=all&per_page=200&&category=${string}"
            textView.setText(string?.toUpperCase())
        }


        CoroutineScope(Dispatchers.IO).launch {
            fetchwalls(url);
        }

    }

    fun fetchwalls(url:String) {

        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null, Response.Listener{ response ->
                val totalres = response.get("total")
                val totalhits = response.get("totalHits")
                Toast.makeText(
                    this,
                    "Wallpapers :${totalres}  Hits :${totalhits}",
                    Toast.LENGTH_SHORT
                ).show()

                val jsonArray = response.getJSONArray("hits")

                for (i in 0..jsonArray.length() - 1) {
                    val jsonObject = jsonArray.getJSONObject(i)

                    val previewurl = jsonObject.get("previewURL").toString()
                    val largeimage = jsonObject.get("largeImageURL").toString()
                    val downloads = jsonObject.get("downloads").toString()
                    val views = jsonObject.get("views").toString()

                    list.add(WallpaperData(previewurl, largeimage, downloads, views))
                }
                val adapterN = WallpaperAd(list, applicationContext)
                recyclerView.adapter = adapterN
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this,
                    "Error :${error}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        queue.add(jsonObjectRequest)

    }
    data class WallpaperData(val smallimg:String,val largeimg:String,val downloads:String,val views:String)
}