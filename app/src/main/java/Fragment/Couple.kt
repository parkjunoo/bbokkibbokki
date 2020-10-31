package Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bbokkibbokki.R
import kotlinx.android.synthetic.main.fragment_couple.view.*
import kotlinx.android.synthetic.main.fragment_general.*

class Couple : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_couple, container, false)
        view.textView.setText("바꼇지 슈발롬아?")
        return view
    }

}