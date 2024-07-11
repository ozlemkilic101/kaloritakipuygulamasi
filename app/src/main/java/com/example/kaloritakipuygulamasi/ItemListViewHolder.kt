import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kaloritakipuygulamasi.ItemModel
import com.example.kaloritakipuygulamasi.R

class ItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItems(itemModel: ItemModel) {
        val description = itemView.findViewById<TextView>(R.id.item_textName)
        val image = itemView.findViewById<ImageView>(R.id.item_image)
        val calories = itemView.findViewById<TextView>(R.id.item_textDescription) // Yeni TextView

        description.text = itemModel.ad

        calories.text = "${itemModel.kalori}kcal/100 gr"

        // Glide ile resmi yükleme
        Glide.with(itemView.context)
            .load(itemModel.foto) // URL'yi yükle
            .into(image) // Resmi ImageView'e yerleştir
    }
}