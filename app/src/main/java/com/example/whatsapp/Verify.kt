package com.example.whatsapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.internal.InternalTokenProvider
import kotlinx.android.synthetic.main.activity_verify.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class Verify : AppCompatActivity() {
    var verificationCode:String="123456"
    var mAuthCredential = FirebaseAuth.getInstance()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
        setSupportActionBar(toolbar)
//
//        stopWatch1.text=GeneRateText(63)
//        stopWatch2.text=GeneRateText(63)
        GlobalScope.launch(Dispatchers.IO) {
            for (i in 63 downTo 0 ) {
                delay(1000)
                Log.i("MAIN",i.toString())
                GlobalScope.launch(Dispatchers.Main) {
                    stopWatch1.text = GeneRateText(i)
                    stopWatch2.text = GeneRateText(i)
                }
            }
            stopWatchText1.isEnabled=true
            stopWatchText1.setTextColor(Color.parseColor("#00574B"))
            imageView1.setImageResource(R.drawable.ic_message_black_24dp)
            stopWatchText2.isEnabled=true
            stopWatchText2.setTextColor(Color.parseColor("#00574B"))
            imageView2.setImageResource(R.drawable.ic_local_phone_black2_24dp)
        }

        val phoneNumberOfVerify=intent.getStringExtra("phoneNumber")!!
        val countryCodeOfVerify= intent.getStringExtra("countryCode")!!
        verify_phoneNumber.text="Verify $countryCodeOfVerify $phoneNumberOfVerify"
        verify_phoneNumber2.text="$countryCodeOfVerify $phoneNumberOfVerify"
        val mCallbacks=object:PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                val code=p0.smsCode
                if(code!=null)
                {
                    verifyCode(code)
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@Verify,p0.message,Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                verificationCode=p0
            }

        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            countryCodeOfVerify+phoneNumberOfVerify,
            63,
            TimeUnit.SECONDS,
            this,
            mCallbacks
        )

        verification_code.addTextChangedListener {
            if(verification_code.text.length==6)
            {
                verifyCode(verification_code.text.toString())
            }
        }
    }
    private fun verifyCode(code:String)
    {
        val credential=PhoneAuthProvider.getCredential(verificationCode,code)
        mAuthCredential.signInWithCredential(credential)
            .addOnCompleteListener {
                if(it.isSuccessful)
                {
                    val intent=Intent(this,ProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this,it.exception!!.message,Toast.LENGTH_LONG).show()
                }
            }
        wrongNumber.setOnClickListener {
            val intent=Intent(this,EnterYourPhoneNumber::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }
    private fun GeneRateText(time: Int): String {
        return if(time/60>=1 ||  time/10==0) {
            "${time/60}:0${time%60}"
        } else {
            "${time/60}:${time%60}"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(R.id.Help==item.itemId)
        {
            startActivity(Intent(this,HelpActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}
