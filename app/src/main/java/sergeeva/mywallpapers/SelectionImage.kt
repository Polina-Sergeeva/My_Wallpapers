package sergeeva.mywallpapers

import android.app.WallpaperManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SelectionImage : AppCompatActivity() {
    lateinit var bitmap:Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_image)


        val button = findViewById<Button>(R.id.setButton)
        val imageview = findViewById<ShapeableImageView>(R.id.imageView)
        val largeurl = intent.getStringExtra("largeurl").toString()

        Glide.with(applicationContext).load(largeurl).transition(DrawableTransitionOptions.withCrossFade(200)).into(imageview)


        fun setwallpaper(){
            button.setClickable(false);
            val drawable = imageview.drawable
            var bitmap = drawable.toBitmap()
            val wallpaperManager = WallpaperManager.getInstance(baseContext)
            wallpaperManager.setBitmap(bitmap)
        }

        button.setOnClickListener(View.OnClickListener {
            if(!this::bitmap.isInitialized) {
                CoroutineScope(Dispatchers.IO).launch {
                    setwallpaper()
                }
                Toast.makeText(this, "Обои успешно обновлены!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
