package com.example.guests.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guests.databinding.FragmentAllBinding
import com.example.guests.service.constants.GuestConstants
import com.example.guests.view.adapter.GuestAdapter
import com.example.guests.view.listener.GuestListener
import com.example.guests.viewmodel.GuestsViewModel

class PresentFragment : Fragment() {

    private lateinit var mViewModel: GuestsViewModel
    private val mAdapter : GuestAdapter = GuestAdapter()
    private lateinit var mListener: GuestListener
    private var _binding: FragmentAllBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)

        _binding = FragmentAllBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recycler : RecyclerView = binding.recyclerAllGuests

        recycler.layoutManager = LinearLayoutManager(context)

        recycler.adapter = mAdapter
        mListener = object: GuestListener{
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(GuestConstants.GUESTID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load(GuestConstants.FILTER.PRESENT)
            }


        }
        mAdapter.attachListener(mListener)

        observer()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load(GuestConstants.FILTER.PRESENT)
    }

    private fun observer(){
        mViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuest(it)
        })
    }
}