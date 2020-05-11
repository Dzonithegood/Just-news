import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.justnews.R
import com.example.justnews.ui.NewsActivity
import com.example.justnews.ui.NewsViewModel

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    lateinit var viewModel : NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
    }

}


