import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.justnews.R
import com.example.justnews.ui.NewsActivity
import com.example.justnews.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel : NewsViewModel

    //After we have defined in every fragment that goes to this Article, now we have to receive the article that we passed from 3 diff fragments.
    //First we get the argument as a global variable , below :
    val args : ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel

        //Now we get the current article
        val article = args.article
        //Now when we have that article , we want to display in that webView that is inside of this article fragment
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        //Call floating action button

       fab.setOnClickListener{
           viewModel.saveArticle(article)
           //adding snackBar to notify the user that that is saved
           Snackbar.make(view, "Article saved successfully",Snackbar.LENGTH_SHORT).show()
       }
        //now we should observe our changes in the database in our SavedNewsFragment, we go there

    }
}


