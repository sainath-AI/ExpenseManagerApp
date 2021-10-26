package com.masai.sainath.expensemanager.views.recyclerviews



import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moneylover.views.recyclerviews.OnCategoryClickListener
import com.masai.sainath.expensemanager.databinding.CustomListBinding
import com.masai.sainath.expensemanager.views.AddActivity


class CustomListItemAdapter(
    private val activity: Activity,
    private val listItems: List<String>,
    private val imageList:List<Int>,
    private val selection: String,
    private val onCategoryClickListener: OnCategoryClickListener
) :
    RecyclerView.Adapter<CustomListItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CustomListBinding =
            CustomListBinding.inflate(LayoutInflater.from(activity), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listItems[position]

        holder.tvText.text = item
        val image=imageList[position]
        Glide.with(holder.image.context).load(image).into(holder.image)


        holder.itemView.setOnClickListener {

          onCategoryClickListener.selectedListItem(item,image,selection)


        }
        // END
    }


    override fun getItemCount(): Int {
        return listItems.size
    }


    class ViewHolder(view: CustomListBinding) : RecyclerView.ViewHolder(view.root) {

        val tvText = view.tvText
        val image=view.ivCategory
    }
}