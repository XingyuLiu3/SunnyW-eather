package com.example.sunnyweather.ui.place

import android.R.attr.editable
import android.os.Bundle
import kotlin.Result // 添加 Result 类的导入
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.sunnyweather.R
import com.example.sunnyweather.databinding.FragmentPlaceBinding
import com.example.sunnyweather.logic.model.Place

class PlaceFragment: Fragment() {
    private lateinit var binding: FragmentPlaceBinding
     val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }
    private lateinit var adapter: PlaceAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = FragmentPlaceBinding.inflate(layoutInflater)
        val layoutManager= LinearLayoutManager(activity)
        binding.recyclerView.layoutManager=layoutManager
        adapter = PlaceAdapter(this,viewModel.placeList)
        binding.recyclerView.adapter=adapter
        binding.searchPlaceEdit.addTextChangedListener{editable->
            val content=editable.toString()
            if (content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }else{
                binding.recyclerView.visibility=View.GONE
                binding.bgImageView.visibility= View.VISIBLE
                viewModel.placeList.clear()
                    adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(this, Observer<Result<List<Place>>>{ result->
            val places: List<Place>?=result.getOrNull()
            if(!places.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.VISIBLE
                binding.bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places as Collection<Place>)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity,"未查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        }
     }
