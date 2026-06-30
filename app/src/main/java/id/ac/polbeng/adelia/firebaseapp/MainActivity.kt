package id.ac.polbeng.adelia.firebaseapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import id.ac.polbeng.adelia.firebaseapp.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var employeeList: ArrayList<EmployeeModel>
    companion object {
        const val EXTRA_EMPLOYEE = "EXTRA_EMPLOYEE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        val layoutManager = LinearLayoutManager(this)
        binding.rvEmployee.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this,
            layoutManager.orientation)
        binding.rvEmployee.addItemDecoration(itemDecoration)
        employeeList = arrayListOf()
        getEmployeeList()
        binding.swipeRefresh.setOnRefreshListener {
            getEmployeeList()
        }
        binding.btnAddUser.setOnClickListener {
            val insertIntent = Intent(this,
                AddEmployeeActivity::class.java)
            startActivity(insertIntent)
        }
    }
    private fun getEmployeeList() {
        showLoading(true)
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                employeeList.clear()
                if(snapshot.exists()){
                    for(empSnapshot in snapshot.children){
                        val empData =
                            empSnapshot.getValue(EmployeeModel::class.java)
                        if (empData != null) {
                            employeeList.add(empData)
                        }
                    }
                    setEmployeeData()
                }
                showLoading(false)
            }
            override fun onCancelled(p0: DatabaseError) {
                showLoading(false)
            }
        })
    }
    private fun setEmployeeData(){
        val employeeAdapter = EmployeeAdapter(employeeList)
        binding.rvEmployee.adapter = employeeAdapter
        employeeAdapter.setOnItemClickCallback(object :
            EmployeeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: EmployeeModel) {
                val json = Gson().toJson(data)
                val moveWithObjectIntent = Intent(
                    this@MainActivity,
                    EmployeeDetailActivity::class.java
                )
                moveWithObjectIntent.putExtra(EXTRA_EMPLOYEE, json)
                startActivity(moveWithObjectIntent)
            }
        })
    }
    private fun showLoading(isLoading: Boolean) {
        if (!binding.swipeRefresh.isRefreshing) {
            binding.progressBar.visibility = if (isLoading) View.VISIBLE
            else View.GONE
        }
        if (!isLoading) {
            binding.swipeRefresh.isRefreshing = false
        }
    }
}