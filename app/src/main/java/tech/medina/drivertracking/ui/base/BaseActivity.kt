package tech.medina.drivertracking.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import tech.medina.drivertracking.ui.navigation.Navigator
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity: AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator
    protected abstract val viewModel: ViewModel?

    protected abstract fun getBindingRoot(): View
    protected abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getBindingRoot())
        initView(savedInstanceState)
    }

    fun showLoader() {
        //todo
    }

    fun hideLoader() {
        //todo
    }

    fun replaceFragment(
        containerViewId: Int,
        fragment: Fragment,
        tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(containerViewId, fragment, tag)
        fragmentTransaction.commit()
    }

    fun addFragment(
        containerViewId: Int,
        fragment: Fragment,
        tag: String,
        addToBackStack: Boolean = false) {
        val fragmentTransaction = this.supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag)
        }
        fragmentTransaction.add(containerViewId, fragment, tag)
        fragmentTransaction.commit()
    }

    protected fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    protected fun onError(error: Any? = null) {
        showMessage(error.toString())
    }

}