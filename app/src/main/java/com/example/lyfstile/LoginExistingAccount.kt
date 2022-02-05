package com.example.lyfstile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class LoginExistingAccount : AppCompatActivity(), View.OnClickListener, PassData {

    var dataList = ArrayList<Data>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_existing_account)

        var emailEnterFragment = TextSubmitFragment(false)
        var passwordEnterFragment = TextSubmitFragment(true)

        val fragtrans = supportFragmentManager.beginTransaction()

        fragtrans.replace(R.id.email_enter_box,emailEnterFragment,"Email_box")
        fragtrans.replace(R.id.password_enter_box,passwordEnterFragment,"Password_box")

        fragtrans.commit()

        val login = findViewById<Button>(R.id.Login_button)
        login.setOnClickListener(this)



    }


    private fun allBoxesEntered(): Boolean {
        var senders = ArrayList<String>()


        if (dataList != null) {
            for (entry in dataList) {
                var temp = entry as Data
                senders.add(temp.sender)

            }
        }

        if(senders.contains("Email_box") && senders.contains("Password_box"))
            return true

        return false
    }

    override fun onClick(view: View?) {
        var message = ""


       when(view?.id) {

          R.id.Login_button -> {
              //Make sure everything has been entered/initialized
              if (dataList != null && allBoxesEntered()) {

                  var username = dataList[0].getData("Email_box")
                  var password = dataList[1].getData("Password_box")

                  if(verifyCredentials(username,password)) {

                      for (entry in dataList) {
                          message += entry.getData(entry.sender)
                      }
                      Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                  }
                  else
                  {
                      Toast.makeText(this, "Incorrect Password!", Toast.LENGTH_SHORT).show()

                  }
              } else {
                  Toast.makeText(this, "Please enter all forms!", Toast.LENGTH_SHORT).show()
              }
          }
           R.id.forgot_pass ->
           {

               Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show()

           }

       }
    }

    /*
    *
    * Need to actually authorize data once a DB is established,
    * for now, test account credentials are 123:123
    *
    *
    * */
    private fun verifyCredentials(email : String, password : String) : Boolean
    {
        if (email == "123" && password == "123")
            return true

        return false
    }


    override fun onDataPass(_data: Data) {
        Toast.makeText(this, "Came from: " + _data.sender, Toast.LENGTH_SHORT).show()
        dataList.add(_data)
    }
}