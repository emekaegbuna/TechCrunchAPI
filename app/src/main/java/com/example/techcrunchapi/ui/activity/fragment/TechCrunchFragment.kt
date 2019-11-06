package com.example.techcrunchapi.ui.activity.fragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techcrunchapi.R
import com.example.techcrunchapi.ui.adapter.TechCrunchAdapter
import com.example.techcrunchapi.ui.adapter.listener.TechCrunchClickListener
import com.example.techcrunchapi.data.remote.WebServices
import com.example.techcrunchapi.data.repository.TechCrunchRepositoryImpl
import com.example.techcrunchapi.data.model.postModel.TechCrunchModel
import com.example.techcrunchapi.utils.Constant
import com.example.techcrunchapi.viewModel.MainViewModel
import com.example.techcrunchapi.viewModel.factory.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_tech_crunch.*

/**
 * A simple [Fragment] subclass.
 */
class TechCrunchFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var techCrunchAdapter: TechCrunchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tech_crunch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel = ViewModelProviders
            .of(this, MainViewModelFactory(TechCrunchRepositoryImpl(WebServices.instance)))
            .get(MainViewModel::class.java)

        viewModel.techCrunchModel.observe(this, Observer{

            techCrunchAdapter.techCrunchModel.clear()
            techCrunchAdapter.techCrunchModel.addAll(it)
            techCrunchAdapter.notifyDataSetChanged()

        })

        viewModel.errorMessage.observe(this, Observer{
            tvMessage.text = it
        })

        viewModel.loadingState.observe(this, Observer {
            when (it) {
                MainViewModel.LoadingState.LOADING -> displayProgressbar()
                MainViewModel.LoadingState.SUCCESS -> displayList()
                MainViewModel.LoadingState.ERROR -> displayMessageContainer()
                else -> displayMessageContainer()
            }
        })

        if (viewModel.lastFetchedTime == null){
            viewModel.fetchTechCrunchModel()
        }

        btnRetry.setOnClickListener{
            viewModel.fetchTechCrunchModel()
        }
    }

    private fun displayMessageContainer() {
        progressbar.visibility =View.GONE
        rv_tech_crunch.visibility = View.GONE
        llMessageContainer.visibility = View.VISIBLE
    }

    private fun displayList() {
        progressbar.visibility =View.GONE
        rv_tech_crunch.visibility = View.VISIBLE
        llMessageContainer.visibility = View.GONE
    }

    private fun displayProgressbar() {
        progressbar.visibility =View.VISIBLE
        rv_tech_crunch.visibility = View.GONE
        llMessageContainer.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        rv_tech_crunch.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        techCrunchAdapter = TechCrunchAdapter(
            mutableListOf(),
            object : TechCrunchClickListener {
                override fun onClick(techCrunchModel: TechCrunchModel) {

                    nxtFragment(techCrunchModel.author)
                }

            })
        rv_tech_crunch.adapter = techCrunchAdapter
    }

    private fun nxtFragment(userID: Int){

        var fragmentmanager = activity?.supportFragmentManager
        var transaction = fragmentmanager?.beginTransaction()
        var context = activity?.applicationContext
        var args = Bundle()
        args.putInt(Constant.userID, userID)
        var postLayoutFragment = PostLayoutFragment()
        postLayoutFragment.arguments = args


        transaction?.replace(R.id.fl_container, postLayoutFragment)?.addToBackStack(null)?.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu_refresh, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){

            R.id.action_refresh ->{
                viewModel.fetchTechCrunchModel()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
