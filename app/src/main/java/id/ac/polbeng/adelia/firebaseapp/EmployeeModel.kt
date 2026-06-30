package id.ac.polbeng.adelia.firebaseapp

import org.valiktor.functions.hasDigits
import org.valiktor.functions.hasSize
import org.valiktor.functions.isBetween
import org.valiktor.functions.isEmail
import org.valiktor.functions.isPositive
import org.valiktor.validate
data class EmployeeModel(
    val empID: String? = null,
    val empName: String? = null,
    val empEmail: String? = null,
    val empAge: Int? = null,
    val empSalary: Long? = null
){
    init {
        validate(this) {
            validate(EmployeeModel::empID)
            validate(EmployeeModel::empName).hasSize(min = 3, max = 30)
            validate(EmployeeModel::empEmail).isEmail()
            validate(EmployeeModel::empAge).isPositive().isBetween(17, 55)
            validate(EmployeeModel::empSalary).isPositive().hasDigits(min = 6, max = 10)
        }
    }
}