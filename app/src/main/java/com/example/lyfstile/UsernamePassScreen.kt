package com.example.lyfstile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UsernamePassScreen : AppCompatActivity(), View.OnClickListener, PassData{

    private var dataList = HashMap<String, Data>()
    private var emailEnterFragment : TextSubmitFragment ?= null
    private var nextButton : Button ?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username_pass_screen)

        //Replace the fragment container(s)
        //Each of these represents a single fragment, so be careful about duplicate tags
        emailEnterFragment = TextSubmitFragment()


        var passwordEnterFragment = TextSubmitFragment()
        var confirmPasswordEnterFragment = TextSubmitFragment()

        val fragtrans = supportFragmentManager.beginTransaction()

        fragtrans.replace(R.id.email_enter_box, emailEnterFragment!!,"Email_box")
        fragtrans.replace(R.id.password_enter_box,passwordEnterFragment,"Password_box")
        fragtrans.replace(R.id.confirm_password_enter_box,confirmPasswordEnterFragment,"Confirm_password_box")

        fragtrans.commit()

        nextButton = findViewById<Button>(R.id.next_button)
        nextButton?.setOnClickListener(this)
        nextButton?.isEnabled = false
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TextSubmitFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TextSubmitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(view: View?) {

        var message = ""

        if (dataList.size==3) {

            if(doPasswordsMatch()) {
                val fnPassScreen = Intent(this, FnPassScreen::class.java)
                this.startActivity(fnPassScreen)
            }else
            {
                Toast.makeText(this,"Passwords do not match!", Toast.LENGTH_SHORT).show()
            }
            }else
        {
            Toast.makeText(this,"Please enter all forms!", Toast.LENGTH_SHORT).show()
        }


    }

    private fun allBoxesEntered(): Boolean {
        var senders = ArrayList<String>()


        if (dataList != null) {
            for (entry in dataList) {
                var temp = entry as Data
                senders.add(temp.sender)

            }
        }

        if(senders.contains("Email_box") && senders.contains("Password_box") && senders.contains("Confirm_password_box"))
            return true

       return false
    }

    /*
        Warning: dataList is deprecated, should probably use a map
     */
    private fun doPasswordsMatch() : Boolean
    {
        var passField = dataList["Password_box"]?.data
        var confirmPassField = dataList["Confirm_password_box"]?.data

        if (passField == confirmPassField)
            return true

        return false
    }


    override fun onDataPass(_data: Data) {
        Toast.makeText(this, "Came from: " + _data.sender, Toast.LENGTH_SHORT).show()


        if(_data.data.isEmpty())
        {
            dataList.remove(_data.sender)
            nextButton?.isEnabled = false
        }
        else {
            dataList[_data.sender] = _data

            if (dataList.size == 3) {
                nextButton?.isEnabled = true
            }
        }
    }

        // not sure if this will be kept here, but ill use it to move to the next frag for now...
/*        val fnPassScreen = Intent(this, FnPassScreen::class.java)
        this.startActivity(fnPassScreen)*/
    }
