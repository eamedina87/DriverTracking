package tech.medina.drivertracking.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import tech.medina.drivertracking.ui.navigation.Navigator
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var navigator: Navigator

    abstract val viewModel : ViewModel?

    protected abstract fun getBindingRoot(inflater: LayoutInflater, container: ViewGroup?): View
    protected abstract fun initView(savedInstanceState: Bundle?)

    protected val baseActivity : BaseActivity by lazy {
        activity as BaseActivity
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getBindingRoot(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
    }

    protected fun showMessage(message:String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    protected fun onError(error: Any? = null) {
        showMessage(error.toString())
    }

    protected fun showLoader() {
        baseActivity.showLoader()
    }

    protected fun hideLoader() {
        baseActivity.hideLoader()
    }

    protected fun finish() {
        baseActivity.supportFragmentManager.popBackStack()
    }

}