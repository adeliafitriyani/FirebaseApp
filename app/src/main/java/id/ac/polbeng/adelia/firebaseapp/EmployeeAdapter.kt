package id.ac.polbeng.adelia.firebaseapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.polbeng.adelia.firebaseapp.databinding.EmployeeItemBinding
class EmployeeAdapter(private val listEmployee: ArrayList<EmployeeModel>)
    : RecyclerView.Adapter<EmployeeAdapter.CardViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback)
    {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: EmployeeModel)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CardViewHolder {
        val binding =
            EmployeeItemBinding.inflate(LayoutInflater.from(parent.context), parent,
                false)
        return CardViewHolder(binding)
    }
    override fun onBindViewHolder(holder: CardViewHolder, position: Int)
    {
        val employee = listEmployee[position]
        Glide.with(holder.itemView.context)
            .load(R.drawable.photo)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.binding.imgLogo)
        holder.binding.tvName.text = employee.empName
        holder.binding.tvEmail.text = "Email : \n${employee.empEmail}"
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(employee)
        }
    }
    override fun getItemCount(): Int = listEmployee.size
    class CardViewHolder (val binding: EmployeeItemBinding):
        RecyclerView.ViewHolder(binding.root)
}