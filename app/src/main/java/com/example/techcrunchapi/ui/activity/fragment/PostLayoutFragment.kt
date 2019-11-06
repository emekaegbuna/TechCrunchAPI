package com.example.techcrunchapi.ui.activity.fragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.techcrunchapi.R
import com.example.techcrunchapi.data.model.postModel.TechCrunchModel
import com.example.techcrunchapi.data.remote.WebServices
import com.example.techcrunchapi.data.repository.TechCrunchRepositoryImpl
import com.example.techcrunchapi.ui.adapter.TechCrunchAdapter
import com.example.techcrunchapi.ui.adapter.listener.TechCrunchClickListener
import com.example.techcrunchapi.utils.Constant
import com.example.techcrunchapi.viewModel.MainViewModel
import com.example.techcrunchapi.viewModel.UserViewModel
import com.example.techcrunchapi.viewModel.factory.MainViewModelFactory
import com.example.techcrunchapi.viewModel.factory.UserViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_post_layout.*
import kotlinx.android.synthetic.main.fragment_tech_crunch.*

/**
 * A simple [Fragment] subclass.
 */
class PostLayoutFragment : Fragment() {

    lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var newFragData = arguments?.getInt(Constant.userID)

        viewModel = ViewModelProviders
            .of(this, UserViewModelFactory(TechCrunchRepositoryImpl(WebServices.instance), newFragData!!))
            .get(UserViewModel::class.java)
        viewModel.authorModel.observe(this, Observer{

            Picasso.get().load(it.cbAvatar).into(iv_post2)
            tv_author_name2.text = it.name
            tv_description2.text = it.cbDescription

        })

        viewModel.errorMessage.observe(this, Observer{
            tvMessage2.text = it
        })

        viewModel.loadingState.observe(this, Observer {
            when (it) {
                UserViewModel.LoadingState.LOADING -> displayProgressbar()
                UserViewModel.LoadingState.SUCCESS -> displayList()
                UserViewModel.LoadingState.ERROR -> displayMessageContainer()
                else -> displayMessageContainer()
            }
        })

        if (viewModel.lastFetchedTime == null){
            viewModel.fetchAuthorModel()
        }

    }

    private fun displayMessageContainer() {
        progressbar2.visibility =View.GONE
        postContainer.visibility = View.GONE
        llMessageContainer2.visibility = View.VISIBLE
    }

    private fun displayList() {
        progressbar2.visibility =View.GONE
        postContainer.visibility = View.VISIBLE
        llMessageContainer2.visibility = View.GONE
    }

    private fun displayProgressbar() {
        progressbar2.visibility =View.VISIBLE
        postContainer.visibility = View.GONE
        llMessageContainer2.visibility = View.GONE
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu_refresh, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){

            R.id.action_refresh ->{
                viewModel.fetchAuthorModel()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}