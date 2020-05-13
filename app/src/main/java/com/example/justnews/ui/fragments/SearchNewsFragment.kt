import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justnews.R
import com.example.justnews.adapters.NewsAdapter
import com.example.justnews.ui.NewsActivity
import com.example.justnews.ui.NewsViewModel
import com.example.justnews.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.justnews.util.Resource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    lateinit var viewModel : NewsViewModel
    lateinit var newsAdapter :NewsAdapter
    val TAG = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        //Safeargs from the navigation components library, witch will enable me to pass arguments to transitions from one fragment to another.Article class is not a primitive data class like int or float,
        //then we need tyo mark that class as serializable, witch then tells kotlin that we want to be able to pass this class between several fragments with the navigation components.
        

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

//Adding delay when searching for news, it can be done easily with coroutines, we ll add a coroutine job in a variable
        var job : Job? = null
//whenever text changes in our edit text this following function will be called
        etSearch.addTextChangedListener {editable ->
//when we type something we want to cancel our current job
         job?.cancel()
//and start a new job
            job= MainScope().launch{
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let{
//we only want to search for news if there is something typed
                  if (  editable.toString().isNotEmpty()){
                      viewModel.searchNews(editable.toString())
                  }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {  message ->
                        Log.e(TAG, "An error occured : $message")
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }

        })
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}


