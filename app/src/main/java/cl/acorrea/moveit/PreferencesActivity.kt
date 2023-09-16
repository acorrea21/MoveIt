package cl.acorrea.moveit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch

class PreferencesActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        val nameEditText = findViewById<EditText>(R.id.prefNameInput)
        val introSwitch = findViewById<Switch>(R.id.prefIntro)
        val close = findViewById<ImageView>(R.id.prefClose)
        val pref = getSharedPreferences(getString(R.string.SharedPref), MODE_PRIVATE)
        val prefEditor = pref.edit()

        val userkey = getString(R.string.p_name)
        val introkey = getString(R.string.p_intro)

        Log.i("PreferencesActivity","Switch initial value " + pref.getBoolean(introkey, true))
        Log.i("PreferencesActivity","InputName initial value " + pref.getString(userkey,"Default"))

        //Valores iniciales
        nameEditText.setText(pref.getString(userkey, "Default"))
        introSwitch.isChecked = pref.getBoolean(introkey, true)

        //Forma de salir
        close.setOnClickListener{
            finish()
        }

        introSwitch.setOnCheckedChangeListener { compoundButton, b ->
            prefEditor.putBoolean(introkey,introSwitch.isChecked)
            prefEditor.apply()
            Log.i("PreferencesActivity","Switch value " + introSwitch.isChecked)
        }

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()

                if (text.isNotEmpty())
                {
                    prefEditor.putString(userkey, text)
                    prefEditor.apply()
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }


}