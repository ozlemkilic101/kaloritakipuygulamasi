import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kaloritakipuygulamasi.ItemModel
import com.example.kaloritakipuygulamasi.ItemModel2
import com.example.kaloritakipuygulamasi.R

class ItemListViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItems(itemModel2: ItemModel2) {
        val description = itemView.findViewById<TextView>(R.id.item_textName)
        val image = itemView.findViewById<ImageView>(R.id.item_image)
        val calories = itemView.findViewById<TextView>(R.id.item_textDescription)

        description.text = itemModel2.ad
        calories.text="${itemModel2.kalori}kcal/1 saat"

        // Glide ile resmi yükleme
        Glide.with(itemView.context)
            .load(itemModel2.foto) // URL'yi yükle
            .into(image) // Resmi ImageView'e yerleştir
    }
}