package com.example.guests.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guests.viewmodel.GuestFormViewModel
import com.example.guests.R
import com.example.guests.databinding.ActivityGuestFormBinding
import com.example.guests.service.constants.GuestConstants

class GuestFormActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var mViewModel : GuestFormViewModel
    private var mGuestId: Int = 0
    private lateinit var binding : ActivityGuestFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        setListeners()
        loadData()
        observe()
        loadData()
    }

    private fun loadData(){
        val bundle = intent.extras
        if(bundle != null){
            mGuestId  = bundle.getInt(GuestConstants.GUESTID)
            mViewModel.load(mGuestId)
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.button_save){

            val name = binding.editName.text.toString()
            val presence = binding.radioPresence.isChecked

            mViewModel.save(mGuestId, name, presence)
        }
    }

    private fun setListeners(){
        binding.buttonSave.setOnClickListener(this)
    }


    private fun observe(){
        mViewModel.saveGuest.observe(this, Observer {
            if(it){
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mViewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if(it.presence){
                binding.radioPresence.isChecked = true
            }else{
                binding.radioAbsent.isChecked = true
            }
        })
    }
}