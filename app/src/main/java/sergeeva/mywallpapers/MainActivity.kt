package sergeeva.mywallpapers

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {
    val categories = mutableListOf<String>("animals", "computer", "food", "science", "nature","education","industry","travel",)

    val imgcategories = mutableListOf(R.drawable.animals,R.drawable.computer,R.drawable.food,R.drawable.science,R.drawable.nature,R.drawable.education,R.drawable.industry,R.drawable.travel)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!isNetworkConnected()) {
            setContentView(R.layout.no_internet)
            return
        }
        val list:ArrayList<CategoriesData> = ArrayList()
        for(i in 0..categories.size-1){
            list.add(CategoriesData(categories[i],imgcategories[i]))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewId)
        val adapter = CategoriesAd(list,applicationContext)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


    }
    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    data class CategoriesData(val category:String,val resource:Int)
}