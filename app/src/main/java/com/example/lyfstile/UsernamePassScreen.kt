package com.example.lyfstile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KProperty

class UsernamePassScreen : AppCompatActivity(), View.OnClickListener, PassData {

    private var dataList = HashMap<String, Data>()
    private var nextButton: Button? = null
    lateinit var viewModel : LyfViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username_pass_screen)
        viewModel = ViewModelProvider(this)[LyfViewModel::class.java]

        var user = User()
        user?.email="test"
        user?.password="testpass"

        viewModel?.insert(this,user)

        val emailEnterFragment = TextSubmitFragment()
        val passwordEnterFragment = TextSubmitFragment()
        val confirmPasswordEnterFragment = TextSubmitFragment()

        val fragtrans = supportFragmentManager.beginTransaction()

        fragtrans.replace(R.id.email_enter_box, emailEnterFragment, EMAIL)
        fragtrans.replace(R.id.password_enter_box, passwordEnterFragment, PASSWORD)
        fragtrans.replace(R.id.confirm_password_enter_box, confirmPasswordEnterFragment,PASSWORD_CONFIRMED)

        fragtrans.commit()

        nextButton = findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)
        nextButton?.isEnabled = false
    }

    override fun onClick(view: View?) {
        if (dataList.size == 3) {
            if (dataList[PASSWORD]?.data == dataList[PASSWORD_CONFIRMED]?.data) {
                startNextActivity()
            } else {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter all forms!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startNextActivity() {
        addToUserProfile()
        val fnPassScreen = Intent(this, NewUserInfoScreen::class.java)
        this.startActivity(fnPassScreen)
    }

    private fun addToUserProfile() {
      // viewModel.getFromDataList(dataList)

    }

    override fun onDataPass(data: Data) {
        if (data.data.toString().isEmpty() || data.data == "Not Provided") {
            dataList.remove(data.sender)
            nextButton?.isEnabled = false
        } else {
            dataList[data.sender] = data
            if (dataList.size == 3) {
                nextButton?.isEnabled = true
            }
        }
    }
}

operator fun Any.setValue(usernamePassScreen: UsernamePassScreen, property: KProperty<*>, lyfViewModel: LyfViewModel) {

}







